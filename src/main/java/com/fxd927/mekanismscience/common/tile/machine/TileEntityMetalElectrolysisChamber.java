package com.fxd927.mekanismscience.common.tile.machine;

import com.fxd927.mekanismscience.api.ITileEntityMekanismAccessor;
import com.fxd927.mekanismscience.api.recipes.MetalElectrolysisRecipe;
import com.fxd927.mekanismscience.common.capabilities.holder.energy.SidedEnergyContainerHelper;
import com.fxd927.mekanismscience.common.capabilities.holder.fluid.SidedFluidTankHelper;
import com.fxd927.mekanismscience.common.config.MSConfig;
import com.fxd927.mekanismscience.common.recipe.MSRecipeType;
import com.fxd927.mekanismscience.common.recipe.lookup.monitor.WorldReadyRecipeCacheLookupMonitor;
import com.fxd927.mekanismscience.common.registries.MSBlocks;
import lombok.Getter;
import mekanism.api.Action;
import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.Upgrade;
import mekanism.api.math.FloatingLong;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.cache.OneInputCachedRecipe;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.inputs.InputHelper;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.api.recipes.outputs.OutputHelper;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.fluid.BasicFluidTank;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.capabilities.holder.fluid.IFluidTankHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.integration.energy.EnergyCompatUtils;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.inventory.container.sync.SyncableFloatingLong;
import mekanism.common.inventory.slot.BasicInventorySlot;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.FluidInventorySlot;
import mekanism.common.lib.inventory.TransitRequest;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.lookup.ISingleRecipeLookupHandler.FluidRecipeLookupHandler;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleFluid;
import mekanism.common.recipe.lookup.monitor.RecipeCacheLookupMonitor;
import mekanism.common.tile.component.TileComponentConfig;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.tile.component.config.slot.EnergySlotInfo;
import mekanism.common.tile.component.config.slot.FluidSlotInfo;
import mekanism.common.tile.component.config.slot.InventorySlotInfo;
import mekanism.common.tile.interfaces.IBoundingBlock;
import mekanism.common.tile.prefab.TileEntityRecipeMachine;
import mekanism.common.tile.transmitter.TileEntityLogisticalTransporterBase;
import mekanism.common.util.InventoryUtils;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class TileEntityMetalElectrolysisChamber extends TileEntityRecipeMachine<MetalElectrolysisRecipe>
        implements IBoundingBlock, FluidRecipeLookupHandler<MetalElectrolysisRecipe> {

    private static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            RecipeError.NOT_ENOUGH_ENERGY,
            RecipeError.NOT_ENOUGH_ENERGY_REDUCED_RATE,
            RecipeError.NOT_ENOUGH_INPUT,
            RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT
    );
    private static final int MAX_FLUID = 10 * FluidType.BUCKET_VOLUME * FluidType.BUCKET_VOLUME;
    private static final int MAX_ITEM = 16 * Item.MAX_STACK_SIZE;
    private static final int BASE_BASELINE_MAX_OPERATION = MSConfig.generalConfig.electrolysisRecipeMultiplier.get();

    public BasicFluidTank inputTank;
    public BasicInventorySlot outputSlot;

    private final IInputHandler<@NotNull FluidStack> inputHandler;
    private final IOutputHandler<@NotNull ItemStack> outputHandler;

    @Getter
    private MachineEnergyContainer<TileEntityMetalElectrolysisChamber> energyContainer;
    private FluidInventorySlot fluidInputSlot;
    private EnergyInventorySlot energyInputSlot;

    private int baselineMaxOperation = BASE_BASELINE_MAX_OPERATION;
    private FloatingLong clientUsage = FloatingLong.ZERO;

    public TileEntityMetalElectrolysisChamber(BlockPos pos, BlockState state) {
        super(MSBlocks.METAL_ELECTROLYSIS_CHAMBER, pos, state, TRACKED_ERROR_TYPES);
        configComponent = new TileComponentConfig(this, TransmissionType.ITEM, TransmissionType.FLUID, TransmissionType.ENERGY);

        Optional.ofNullable(configComponent.getConfig(TransmissionType.ITEM))
                .ifPresent(itemConfig -> {
                    itemConfig.addSlotInfo(DataType.EXTRA, new InventorySlotInfo(true, true, fluidInputSlot));
                    itemConfig.addSlotInfo(DataType.OUTPUT, new InventorySlotInfo(false, true, outputSlot));
                    itemConfig.addSlotInfo(DataType.ENERGY, new InventorySlotInfo(true, true, energyInputSlot));
                });
        Optional.ofNullable(configComponent.getConfig(TransmissionType.FLUID))
                .ifPresent(fluidConfig -> fluidConfig.addSlotInfo(DataType.INPUT, new FluidSlotInfo(true, false, inputTank)));
        Optional.ofNullable(configComponent.getConfig(TransmissionType.ENERGY))
                .ifPresent(energyConfig -> energyConfig.addSlotInfo(DataType.INPUT, new EnergySlotInfo(true, false, energyContainer)));

        ejectorComponent = new TileComponentEjector(this);
        ejectorComponent.setOutputData(configComponent, TransmissionType.ITEM, TransmissionType.FLUID)
                .setCanEject(type -> type == TransmissionType.ITEM);

        inputHandler = InputHelper.getInputHandler(inputTank, RecipeError.NOT_ENOUGH_INPUT);
        outputHandler = OutputHelper.getOutputHandler(outputSlot, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
    }

    @Override
    @NotNull
    protected IFluidTankHolder getInitialFluidTanks(IContentsListener listener, IContentsListener recipeCacheListener) {
        SidedFluidTankHelper helper = SidedFluidTankHelper.forSide(this::getDirection, side -> side == RelativeSide.RIGHT, side -> false);
        helper.addTank(inputTank = BasicFluidTank.input(MAX_FLUID, this::containsRecipe, recipeCacheListener));
        return helper.build();
    }

    @Override
    @NotNull
    protected IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener, IContentsListener recipeCacheListener) {
        SidedEnergyContainerHelper helper = SidedEnergyContainerHelper.forSide(this::getDirection, side -> side == RelativeSide.LEFT, side -> false);
        helper.addContainer(energyContainer = MachineEnergyContainer.input(this, listener));
        return helper.build();
    }

    @Override
    @NotNull
    protected IInventorySlotHolder getInitialInventory(IContentsListener listener, IContentsListener recipeCacheListener) {
        InventorySlotHelper helper = InventorySlotHelper.forSide(this::getDirection, side -> side == RelativeSide.LEFT || side == RelativeSide.RIGHT, side -> side == RelativeSide.BACK);
        helper.addSlot(outputSlot = new BasicInventorySlot(MAX_ITEM, BasicInventorySlot.alwaysTrueBi, BasicInventorySlot.internalOnly,
                BasicInventorySlot.alwaysTrue, recipeCacheListener, 116, 36) {
        });
        helper.addSlot(fluidInputSlot = FluidInventorySlot.fill(inputTank, listener, 21, 56));
        helper.addSlot(energyInputSlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, listener, 144, 35));
        fluidInputSlot.setSlotOverlay(SlotOverlay.MINUS);
        return helper.build();
    }

    @Override
    protected void onUpdateServer() {
        super.onUpdateServer();
        if (!canLookupRecipes()) {
            recipeCacheLookupMonitor.onChange();
            return;
        }
        fluidInputSlot.fillTank();
        energyInputSlot.fillContainerOrConvert();
        clientUsage = recipeCacheLookupMonitor.updateAndProcess(energyContainer);
        handleEject();
    }

    @Override
    protected RecipeCacheLookupMonitor<MetalElectrolysisRecipe> createNewCacheMonitor() {
        return new WorldReadyRecipeCacheLookupMonitor<>(this, this::canLookupRecipes);
    }

    private boolean canLookupRecipes() {
        return getHandlerWorld() != null;
    }

    private void handleEject() {
        if (MekanismUtils.canFunction(this)) {
            Direction ejectionDirection = RelativeSide.RIGHT.getDirection(getDirection());
            BlockEntity dest = WorldUtils.getTileEntity(level, worldPosition.above().relative(ejectionDirection, 2));
            BlockEntity src = WorldUtils.getTileEntity(level, worldPosition.above().relative(ejectionDirection));
            if (dest != null && src != null) {
                TransitRequest ejectMap = InventoryUtils.getEjectItemMap(src, ejectionDirection, List.of(outputSlot));
                if (!ejectMap.isEmpty()) {
                    TransitRequest.TransitResponse response;
                    if (dest instanceof TileEntityLogisticalTransporterBase transporter) {
                        response = transporter.getTransmitter().insert(src, ejectMap, transporter.getTransmitter().getColor(), true, 0);
                    } else {
                        response = ejectMap.addToInventory(dest, ejectionDirection, 0, false);
                    }
                    if (!response.isEmpty()) {
                        int amount = response.getSendingAmount();
                        MekanismUtils.logMismatchedStackSize(outputSlot.shrinkStack(amount, Action.EXECUTE), amount);
                    }
                }
            }
        }
    }

    @Override
    @NotNull
    public IMekanismRecipeTypeProvider<MetalElectrolysisRecipe, SingleFluid<MetalElectrolysisRecipe>> getRecipeType() {
        return MSRecipeType.METAL_ELECTROLYSIS;
    }

    @Override
    @Nullable
    public MetalElectrolysisRecipe getRecipe(int cacheIndex) {
        return findFirstRecipe(inputHandler);
    }

    @Override
    @NotNull
    public CachedRecipe<MetalElectrolysisRecipe> createNewCachedRecipe(@NotNull MetalElectrolysisRecipe recipe, int cacheIndex) {
        return new OneInputCachedRecipe<>(recipe, recheckAllRecipeErrors, inputHandler, outputHandler,
                recipe::getInput, recipe::getOutput, FluidStack::isEmpty, ItemStack::isEmpty) {
        }
                .setErrorsChanged(this::onErrorsChanged)
                .setCanHolderFunction(() -> MekanismUtils.canFunction(this))
                .setActive(this::setActive)
                .setOnFinish(this::markForSave)
                .setRequiredTicks(() -> 1)
                .setBaselineMaxOperations(() -> baselineMaxOperation);
    }

    @Override
    public void recalculateUpgrades(Upgrade upgrade) {
        super.recalculateUpgrades(upgrade);
        if (upgrade == Upgrade.SPEED) {
            baselineMaxOperation = BASE_BASELINE_MAX_OPERATION * (1 << upgradeComponent.getUpgrades(Upgrade.SPEED));
        }
    }

    @Override
    public void addContainerTrackers(MekanismContainer container) {
        super.addContainerTrackers(container);
        container.track(SyncableFloatingLong.create(() -> clientUsage, value -> clientUsage = value));
    }

    @Override
    @NotNull
    public <T> LazyOptional<T> getOffsetCapabilityIfEnabled(@NotNull Capability<T> capability, Direction side, @NotNull Vec3i offset) {
        if (capability == ForgeCapabilities.FLUID_HANDLER) {
            return ((ITileEntityMekanismAccessor) this).getFluidHandlerManager().resolve(capability, side);
        } else if (capability == ForgeCapabilities.ITEM_HANDLER) {
            return itemHandlerManager.resolve(capability, side);
        } else if (EnergyCompatUtils.isEnergyCapability(capability)) {
            return ((ITileEntityMekanismAccessor) this).getEnergyHandlerManager().resolve(capability, side);
        }
        return getCapability(capability, side);
    }

    @Override
    public boolean isOffsetCapabilityDisabled(@NotNull Capability<?> capability, Direction side, @NotNull Vec3i offset) {
        if (capability == ForgeCapabilities.ITEM_HANDLER) {
            return notItemPort(side, offset);
        } else if (capability == ForgeCapabilities.FLUID_HANDLER) {
            return notFluidPort(side, offset);
        } else if (EnergyCompatUtils.isEnergyCapability(capability)) {
            return notEnergyPort(side, offset);
        } else if (canEverResolve(capability) && IBoundingBlock.super.isOffsetCapabilityDisabled(capability, side, offset)) {
            // If we are not an item handler or energy capability, and it is a capability that we can support,
            // but it is one that normally should be disabled for offset capabilities, then expose it but only do so
            // via our ports for things like computer integration capabilities, then we treat the capability as
            // disabled if it is not against one of our ports
            return notItemPort(side, offset);
        }
        return false;
    }

    private boolean notItemPort(Direction side, Vec3i offset) {
        // Every port can interact with item ports
        Direction back = getOppositeDirection();
        switch (getDirection()) {
            case NORTH, SOUTH -> {
                if (offset.equals(new Vec3i(0, 1, back.getStepZ())))
                    return side != back;
            }
            case EAST, WEST -> {
                if (offset.equals(new Vec3i(back.getStepX(), 1, 0)))
                    return side != back;
            }
        }
        return notFluidPort(side, offset) && notEnergyPort(side, offset);
    }

    private boolean notFluidPort(Direction side, Vec3i offset) {
        Direction right = getRightSide();
        switch (getDirection()) {
            case NORTH, SOUTH -> {
                if (offset.equals(new Vec3i(right.getStepX(), 1, 0)))
                    return side != right;
            }
            case EAST, WEST -> {
                if (offset.equals(new Vec3i(0, 1, right.getStepZ())))
                    return side != right;
            }
        }
        return true;
    }

    private boolean notEnergyPort(Direction side, Vec3i offset) {
        Direction left = getLeftSide();
        switch (getDirection()) {
            case NORTH, SOUTH -> {
                if (offset.equals(new Vec3i(left.getStepX(), 1, 0)))
                    return side != left;
            }
            case EAST, WEST -> {
                if (offset.equals(new Vec3i(0, 1, left.getStepZ())))
                    return side != left;
            }
        }
        return true;
    }
}
