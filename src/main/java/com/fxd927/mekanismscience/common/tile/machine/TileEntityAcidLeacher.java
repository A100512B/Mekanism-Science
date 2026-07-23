package com.fxd927.mekanismscience.common.tile.machine;

import com.fxd927.mekanismscience.api.ITileEntityMekanismAccessor;
import com.fxd927.mekanismscience.api.recipes.ItemStackGasToFluidRecipe;
import com.fxd927.mekanismscience.common.capabilities.holder.chemical.SidedChemicalTankHelper;
import com.fxd927.mekanismscience.common.capabilities.holder.fluid.SidedFluidTankHelper;
import com.fxd927.mekanismscience.common.config.MSConfig;
import com.fxd927.mekanismscience.common.recipe.MSRecipeType;
import com.fxd927.mekanismscience.common.recipe.lookup.monitor.WorldReadyRecipeCacheLookupMonitor;
import com.fxd927.mekanismscience.common.registries.MSBlocks;
import lombok.Getter;
import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.Upgrade;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.ChemicalTankBuilder;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasTank;
import mekanism.api.math.FloatingLong;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.cache.TwoInputCachedRecipe;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.inputs.InputHelper;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.api.recipes.outputs.OutputHelper;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.fluid.BasicFluidTank;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.capabilities.holder.fluid.IFluidTankHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.integration.energy.EnergyCompatUtils;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.container.slot.ContainerSlotType;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.inventory.container.sync.SyncableFloatingLong;
import mekanism.common.inventory.slot.BasicInventorySlot;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.FluidInventorySlot;
import mekanism.common.inventory.slot.chemical.GasInventorySlot;
import mekanism.common.inventory.warning.WarningTracker.WarningType;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.lookup.IDoubleRecipeLookupHandler.ItemChemicalRecipeLookupHandler;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.ItemChemical;
import mekanism.common.recipe.lookup.monitor.RecipeCacheLookupMonitor;
import mekanism.common.tile.component.TileComponentConfig;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.tile.component.config.slot.ChemicalSlotInfo.GasSlotInfo;
import mekanism.common.tile.component.config.slot.EnergySlotInfo;
import mekanism.common.tile.component.config.slot.FluidSlotInfo;
import mekanism.common.tile.component.config.slot.InventorySlotInfo;
import mekanism.common.tile.interfaces.IBoundingBlock;
import mekanism.common.tile.prefab.TileEntityRecipeMachine;
import mekanism.common.util.FluidUtils;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TileEntityAcidLeacher extends TileEntityRecipeMachine<ItemStackGasToFluidRecipe>
        implements IBoundingBlock, ItemChemicalRecipeLookupHandler<Gas, GasStack, ItemStackGasToFluidRecipe> {

    private static final List<RecipeError> TRACKED_ERRORS = List.of(
            RecipeError.NOT_ENOUGH_ENERGY,
            RecipeError.NOT_ENOUGH_ENERGY_REDUCED_RATE,
            RecipeError.NOT_ENOUGH_INPUT,
            RecipeError.NOT_ENOUGH_SECONDARY_INPUT,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT
    );

    private static final long MAX_GAS = 18 * FluidType.BUCKET_VOLUME * FluidType.BUCKET_VOLUME;
    private static final int MAX_ITEM = 16 * 64;
    private static final int MAX_FLUID = 9 * FluidType.BUCKET_VOLUME * FluidType.BUCKET_VOLUME;
    private static final int BASE_BASELINE_MAX_OPERATION = MSConfig.generalConfig.acidLeacherRecipeMultiplier.get();

    private final IInputHandler<@NotNull ItemStack> itemInputHandler;
    private final IInputHandler<@NotNull GasStack> gasInputHandler;
    private final IOutputHandler<@NotNull FluidStack> outputHandler;

    public IGasTank gasInputTank;
    public BasicInventorySlot itemInputSlot;
    public BasicFluidTank outputTank;

    GasInventorySlot inputGasSlot;
    FluidInventorySlot outputFluidSlot;
    EnergyInventorySlot energySlot;

    private int baselineMaxOperation = BASE_BASELINE_MAX_OPERATION;
    private FloatingLong clientEnergyUsed;

    @Getter
    private MachineEnergyContainer<TileEntityAcidLeacher> energyContainer;

    public TileEntityAcidLeacher(BlockPos pos, BlockState state) {
        super(MSBlocks.ACID_LEACHER, pos, state, TRACKED_ERRORS);
        configComponent = new TileComponentConfig(this, TransmissionType.ITEM, TransmissionType.FLUID, TransmissionType.GAS, TransmissionType.ENERGY);

        Optional.ofNullable(configComponent.getConfig(TransmissionType.ITEM))
                .ifPresent(itemConfig -> {
                    itemConfig.addSlotInfo(DataType.INPUT, new InventorySlotInfo(true, false, itemInputSlot));
                    itemConfig.addSlotInfo(DataType.EXTRA, new InventorySlotInfo(true, true, inputGasSlot));
                    itemConfig.addSlotInfo(DataType.OUTPUT, new InventorySlotInfo(true, true, outputFluidSlot));
                    itemConfig.addSlotInfo(DataType.INPUT_OUTPUT, new InventorySlotInfo(true, true, inputGasSlot, outputFluidSlot));
                    itemConfig.addSlotInfo(DataType.ENERGY, new InventorySlotInfo(true, true, energySlot));
                });
        Optional.ofNullable(configComponent.getConfig(TransmissionType.FLUID))
                .ifPresent(fluidConfig -> fluidConfig.addSlotInfo(DataType.OUTPUT, new FluidSlotInfo(false, true, outputTank)));
        Optional.ofNullable(configComponent.getConfig(TransmissionType.GAS))
                .ifPresent(gasConfig -> gasConfig.addSlotInfo(DataType.INPUT, new GasSlotInfo(true, false, gasInputTank)));
        Optional.ofNullable(configComponent.getConfig(TransmissionType.ENERGY))
                .ifPresent(energyConfig -> energyConfig.addSlotInfo(DataType.INPUT, new EnergySlotInfo(true, false, energyContainer)));

        ejectorComponent = new TileComponentEjector(this);
        ejectorComponent.setOutputData(configComponent, TransmissionType.ITEM, TransmissionType.FLUID, TransmissionType.GAS)
                .setCanEject(type -> type != TransmissionType.GAS);

        itemInputHandler = InputHelper.getInputHandler(itemInputSlot, RecipeError.NOT_ENOUGH_INPUT);
        gasInputHandler = InputHelper.getInputHandler(gasInputTank, RecipeError.NOT_ENOUGH_SECONDARY_INPUT);
        outputHandler = OutputHelper.getOutputHandler(outputTank, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
    }

    @Override
    @NotNull
    protected IChemicalTankHolder<Gas, GasStack, IGasTank> getInitialGasTanks(IContentsListener listener, IContentsListener recipeCacheListener) {
        SidedChemicalTankHelper<Gas, GasStack, IGasTank> builder = SidedChemicalTankHelper.forSideGas(this::getDirection, side -> side == RelativeSide.LEFT, side -> false);
        builder.addTank(gasInputTank = ChemicalTankBuilder.GAS.input(MAX_GAS, gas -> containsRecipeBA(itemInputSlot.getStack(), gas),
                this::containsRecipeB, recipeCacheListener));
        return builder.build();
    }

    @Override
    @NotNull
    protected IFluidTankHolder getInitialFluidTanks(IContentsListener listener, IContentsListener recipeCacheListener) {
        SidedFluidTankHelper builder = SidedFluidTankHelper.forSide(this::getDirection, side -> false, side -> side == RelativeSide.BACK);
        builder.addTank(outputTank = BasicFluidTank.output(MAX_FLUID, this));
        return builder.build();
    }

    @Override
    @NotNull
    protected IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener, IContentsListener recipeCacheListener) {
        EnergyContainerHelper builder = EnergyContainerHelper.forSide(this::getDirection);
        builder.addContainer(energyContainer = MachineEnergyContainer.input(this, listener), RelativeSide.LEFT);
        return builder.build();
    }

    @Override
    @NotNull
    protected IInventorySlotHolder getInitialInventory(IContentsListener listener, IContentsListener recipeCacheListener) {
        InventorySlotHelper builder = InventorySlotHelper.forSide(this::getDirection, side -> side == RelativeSide.LEFT, side -> side == RelativeSide.LEFT);
        itemInputSlot = new BasicInventorySlot(MAX_ITEM, BasicInventorySlot.notExternal,
                (stack, automationType) -> containsRecipeAB(stack, gasInputTank.getStack()),
                this::containsRecipeA, recipeCacheListener, 7, 36) {
            {
                obeyStackLimit = false;
            }
        };
        itemInputSlot.setSlotType(ContainerSlotType.INPUT);
        itemInputSlot.tracksWarnings(slot -> slot.warning(WarningType.NO_MATCHING_RECIPE, getWarningCheck(RecipeError.NOT_ENOUGH_INPUT)));
        builder.addSlot(itemInputSlot, RelativeSide.LEFT);
        builder.addSlot(inputGasSlot = GasInventorySlot.fillOrConvert(gasInputTank, this::getLevel, listener, 7, 55), RelativeSide.LEFT);
        builder.addSlot(outputFluidSlot = FluidInventorySlot.drain(outputTank, this, 152, 55), RelativeSide.BACK);
        builder.addSlot(energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, listener, 152, 14));
        inputGasSlot.setSlotOverlay(SlotOverlay.MINUS);
        outputFluidSlot.setSlotOverlay(SlotOverlay.PLUS);
        return builder.build();
    }

    @Override
    protected void onUpdateServer() {
        super.onUpdateServer();
        if (!canLookupRecipes()) {
            recipeCacheLookupMonitor.onChange();
            return;
        }
        inputGasSlot.fillTankOrConvert();
        outputFluidSlot.drainTank(outputFluidSlot);
        clientEnergyUsed = recipeCacheLookupMonitor.updateAndProcess(energyContainer);
        handleEject();
    }

    @Override
    protected RecipeCacheLookupMonitor<ItemStackGasToFluidRecipe> createNewCacheMonitor() {
        return new WorldReadyRecipeCacheLookupMonitor<>(this, this::canLookupRecipes);
    }

    private boolean canLookupRecipes() {
        return getHandlerWorld() != null;
    }

    private void handleEject() {
        if (MekanismUtils.canFunction(this)) {
            Direction emitSide = getOppositeDirection();
            Optional.ofNullable(WorldUtils.getTileEntity(getLevel(), worldPosition.relative(emitSide)))
                    .ifPresent(tile -> FluidUtils.emit(Collections.singleton(emitSide), outputTank, tile, outputTank.getCapacity()));
            Optional.ofNullable(WorldUtils.getTileEntity(getLevel(), worldPosition.relative(emitSide).relative(getRightSide())))
                    .ifPresent(tile -> FluidUtils.emit(Collections.singleton(emitSide), outputTank, tile, outputTank.getCapacity()));
        }
    }

    @Override
    @NotNull
    public IMekanismRecipeTypeProvider<ItemStackGasToFluidRecipe, ItemChemical<Gas, GasStack, ItemStackGasToFluidRecipe>> getRecipeType() {
        return MSRecipeType.ACID_LEACHING;
    }

    @Override
    @Nullable
    public ItemStackGasToFluidRecipe getRecipe(int cacheIndex) {
        return findFirstRecipe(itemInputHandler, gasInputHandler);
    }

    @Override
    @NotNull
    public CachedRecipe<ItemStackGasToFluidRecipe> createNewCachedRecipe(@NotNull ItemStackGasToFluidRecipe recipe, int cacheIndex) {
        return new TwoInputCachedRecipe<>(recipe, recheckAllRecipeErrors, itemInputHandler, gasInputHandler,
                outputHandler, recipe::getItemInput, recipe::getChemicalInput, recipe::getOutput,
                ItemStack::isEmpty, ChemicalStack::isEmpty, FluidStack::isEmpty) {
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
        container.track(SyncableFloatingLong.create(() -> clientEnergyUsed, value -> clientEnergyUsed = value));
    }

    @Override
    @NotNull
    public <T> LazyOptional<T> getOffsetCapabilityIfEnabled(@NotNull Capability<T> capability, Direction side, @NotNull Vec3i offset) {
        if (capability == Capabilities.GAS_HANDLER) {
            return ((ITileEntityMekanismAccessor) this).getGasHandlerManager().resolve(capability, side);
        } else if (capability == ForgeCapabilities.FLUID_HANDLER) {
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
        } else if (capability == Capabilities.GAS_HANDLER) {
            return notGasPort(side, offset);
        } else if (canEverResolve(capability) && IBoundingBlock.super.isOffsetCapabilityDisabled(capability, side, offset)) {
            // If we are not an item handler or energy capability, and it is a capability that we can support,
            // but it is one that normally should be disabled for offset capabilities, then expose it but only do so
            // via our ports for things like computer integration capabilities, then we treat the capability as
            // disabled if it is not against one of our ports
            return notAnyPort(side, offset);
        }
        return false;
    }

    private boolean notItemPort(Direction side, Vec3i offset) {
        Direction left = getLeftSide();
        Direction back = getOppositeDirection();
        switch (getDirection()) {
            case NORTH, SOUTH -> {
                if (offset.equals(new Vec3i(left.getStepX(), 0, back.getStepZ())))
                    return side != left;
            }
            case EAST, WEST -> {
                if (offset.equals(new Vec3i(back.getStepX(), 0, left.getStepZ())))
                    return side != left;
            }
        }
        return true;
    }

    private boolean notAnyPort(Direction side, Vec3i offset) {
        return notItemPort(side, offset) && notFluidPort(side, offset) && notGasPort(side, offset) && notEnergyPort(side, offset);
    }

    private boolean notGasPort(Direction side, Vec3i offset) {
        Direction left = getLeftSide();
        switch (getDirection()) {
            case NORTH, SOUTH -> {
                if (offset.equals(new Vec3i(left.getStepX(), 0, 0)))
                    return side != left;
            }
            case EAST, WEST -> {
                if (offset.equals(new Vec3i(0, 0, left.getStepZ())))
                    return side != left;
            }
        }
        return true;
    }

    private boolean notFluidPort(Direction side, Vec3i offset) {
        Direction back = getOppositeDirection();
        Direction right = getRightSide();
        switch (getDirection()) {
            case NORTH, SOUTH -> {
                if (offset.equals(new Vec3i(0, 0, back.getStepZ())) || offset.equals(new Vec3i(right.getStepX(), 0, back.getStepZ())))
                    return side != back;
            }
            case EAST, WEST -> {
                if (offset.equals(new Vec3i(back.getStepX(), 0, 0)) || offset.equals(new Vec3i(back.getStepX(), 0, right.getStepZ())))
                    return side != back;
            }
        }
        return true;
    }

    private boolean notEnergyPort(Direction side, Vec3i offset) {
        Direction left = getLeftSide();
        Direction front = getDirection();
        switch (front) {
            case NORTH, SOUTH -> {
                if (offset.equals(new Vec3i(left.getStepX(), 0, front.getStepZ())))
                    return side != left;
            }
            case EAST, WEST -> {
                if (offset.equals(new Vec3i(front.getStepX(), 0, left.getStepZ())))
                    return side != left;
            }
        }
        return true;
    }
}
