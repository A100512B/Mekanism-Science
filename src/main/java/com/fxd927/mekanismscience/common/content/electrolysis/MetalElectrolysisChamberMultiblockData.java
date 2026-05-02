package com.fxd927.mekanismscience.common.content.electrolysis;

import com.fxd927.mekanismscience.api.MSNBTConstants;
import com.fxd927.mekanismscience.api.recipes.MetalElectrolysisRecipe;
import com.fxd927.mekanismscience.common.config.MSConfig;
import com.fxd927.mekanismscience.common.recipe.MSRecipeType;
import com.fxd927.mekanismscience.common.tile.multiblock.electrolysis.TileEntityMetalElectrolysisChamberCasing;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mekanism.api.NBTConstants;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.math.FloatingLong;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.cache.OneInputCachedRecipe;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.inputs.InputHelper;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.api.recipes.outputs.OutputHelper;
import mekanism.common.capabilities.fluid.VariableCapacityFluidTank;
import mekanism.common.inventory.container.sync.dynamic.ContainerSync;
import mekanism.common.inventory.slot.BasicInventorySlot;
import mekanism.common.lib.multiblock.IValveHandler;
import mekanism.common.lib.multiblock.MultiblockData;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.lookup.ISingleRecipeLookupHandler.FluidRecipeLookupHandler;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleFluid;
import mekanism.common.recipe.lookup.monitor.RecipeCacheLookupMonitor;
import mekanism.common.tile.prefab.TileEntityRecipeMachine;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.BooleanSupplier;

public class MetalElectrolysisChamberMultiblockData
        extends MultiblockData
        implements FluidRecipeLookupHandler<MetalElectrolysisRecipe>, IValveHandler {

    private static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            RecipeError.NOT_ENOUGH_INPUT,
            RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT
    );

    public static final int OUTPUT_CAPACITY = 65536;
    @ContainerSync
    private int inputCapacity;
    @ContainerSync
    public boolean active;

    public List<RodData> rodsList = new ObjectArrayList<>();
    public int parallel;
    private FloatingLong baseEnergyRequired;

    @ContainerSync
    public VariableCapacityFluidTank inputTank;
    @ContainerSync
    public BasicInventorySlot outputSlot;
    @ContainerSync
    public IEnergyContainer energyContainer;

    private final RecipeCacheLookupMonitor<MetalElectrolysisRecipe> recipeCacheLookupMonitor;
    private final BooleanSupplier recheckAllRecipeErrors;
    @ContainerSync
    private final boolean[] trackedErrors = new boolean[TRACKED_ERROR_TYPES.size()];

    private final IOutputHandler<ItemStack> outputHandler;
    private final IInputHandler<FluidStack> inputHandler;

    private float prevScale;

    public MetalElectrolysisChamberMultiblockData(TileEntityMetalElectrolysisChamberCasing tile) {
        super(tile);
        recipeCacheLookupMonitor = new RecipeCacheLookupMonitor<>(this);
        recheckAllRecipeErrors = TileEntityRecipeMachine.shouldRecheckAllErrors(tile);
        fluidTanks.add(inputTank = VariableCapacityFluidTank.input(this, () -> inputCapacity, this::containsRecipe, this));
        inventorySlots.add(new BasicInventorySlot(OUTPUT_CAPACITY, BasicInventorySlot.alwaysTrueBi, BasicInventorySlot.internalOnly,
                BasicInventorySlot.alwaysTrue, this, 12, 24) {
        });
        inputHandler = InputHelper.getInputHandler(inputTank, RecipeError.NOT_ENOUGH_INPUT);
        outputHandler = OutputHelper.getOutputHandler(outputSlot, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
    }

    @Override
    public boolean tick(Level world) {
        boolean needsPacket = super.tick(world);
        parallel = 0;
        rodsList.stream().filter(rod -> rod.active)
                        .forEach(rod -> parallel += rod.laser ? 2 : 1);
        recipeCacheLookupMonitor.updateAndProcess(energyContainer);
        float scale = MekanismUtils.getScale(prevScale, inputTank);
        if (scale != prevScale) {
            prevScale = scale;
            needsPacket = true;
        }
        return needsPacket;
    }

    @Override
    public void remove(Level world) {
        super.remove(world);
        rodsList.clear();
    }

    @Override
    public void readUpdateTag(CompoundTag tag) {
        super.readUpdateTag(tag);
        NBTUtils.setFloatIfPresent(tag, NBTConstants.SCALE, value -> prevScale = value);
        NBTUtils.setIntIfPresent(tag, NBTConstants.VOLUME, this::setVolume);
        NBTUtils.setFluidStackIfPresent(tag, NBTConstants.FLUID_STORED, inputTank::setStack);
        NBTUtils.setItemStackIfPresent(tag, NBTConstants.ITEM, outputSlot::setStack);
        NBTUtils.setIntIfPresent(tag, MSNBTConstants.PARALLEL, value -> parallel = value);
        readValves(tag);
        if (tag.contains(MSNBTConstants.RODS, Tag.TAG_LIST)) {
            ListTag list = tag.getList(MSNBTConstants.RODS, Tag.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                rodsList.add(RodData.read(list.getCompound(i)));
            }
        }
    }

    @Override
    public void writeUpdateTag(CompoundTag tag) {
        super.writeUpdateTag(tag);
        tag.putFloat(NBTConstants.SCALE, prevScale);
        tag.putInt(NBTConstants.VOLUME, getVolume());
        tag.put(NBTConstants.FLUID_STORED, inputTank.getFluid().writeToNBT(new CompoundTag()));
        tag.put(NBTConstants.ITEM, outputSlot.getStack().serializeNBT());
        tag.putInt(MSNBTConstants.PARALLEL, parallel);
        writeValves(tag);
        ListTag rods = new ListTag();
        rodsList.forEach(rod -> rods.add(rod.write()));
        tag.put(MSNBTConstants.RODS, rods);
    }

    @Override
    public void setVolume(int volume) {
        if (getVolume() != volume) {
            super.setVolume(volume);
            inputCapacity = volume * MSConfig.generalConfig.electrolysisInputPerTank.get();
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
                .setErrorsChanged(errors -> {
                    for (int i = 0; i < trackedErrors.length; i++) {
                        trackedErrors[i] = errors.contains(TRACKED_ERROR_TYPES.get(i));
                    }
                })
                .setActive(active -> this.active = active)
                .setEnergyRequirements(() -> this.baseEnergyRequired, energyContainer)
                .setBaselineMaxOperations(() -> parallel);
    }

    @Override
    public void onCachedRecipeChanged(@Nullable CachedRecipe<MetalElectrolysisRecipe> cachedRecipe, int cacheIndex) {
        FluidRecipeLookupHandler.super.onCachedRecipeChanged(cachedRecipe, cacheIndex);
        if (cachedRecipe == null) {
            baseEnergyRequired = FloatingLong.ZERO;
        } else {
            baseEnergyRequired = cachedRecipe.getRecipe().getEnergyRequired();
        }
    }

    public boolean handlesSound(TileEntityMetalElectrolysisChamberCasing tile) {
        return getBounds().isOnCorner(tile.getBlockPos());
    }

    public static final class RodData {

        public final BlockPos minPos;
        public final BlockPos maxPos;
        public final boolean laser;
        public boolean active;

        public RodData(BlockPos minPos, BlockPos maxPos, boolean laser, boolean active) {
            this.minPos = minPos;
            this.maxPos = maxPos;
            this.laser = laser;
            this.active = active;
        }

        public CompoundTag write() {
            CompoundTag tag = new CompoundTag();
            tag.put(NBTConstants.MIN, NbtUtils.writeBlockPos(minPos));
            tag.put(NBTConstants.MAX, NbtUtils.writeBlockPos(maxPos));
            tag.putBoolean(MSNBTConstants.LASER, laser);
            tag.putBoolean(NBTConstants.ACTIVE, active);
            return tag;
        }

        public static RodData read(CompoundTag tag) {
            return new RodData(NbtUtils.readBlockPos(tag.getCompound(NBTConstants.MIN)),
                    NbtUtils.readBlockPos(tag.getCompound(NBTConstants.MAX)),
                    tag.getBoolean(MSNBTConstants.LASER),
                    tag.getBoolean(NBTConstants.ACTIVE));
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (RodData) obj;
            return Objects.equals(this.minPos, that.minPos) &&
                    Objects.equals(this.maxPos, that.maxPos) &&
                    this.laser == that.laser &&
                    this.active == that.active;
        }

        @Override
        public int hashCode() {
            return Objects.hash(minPos, maxPos, laser, active);
        }

        @Override
        public String toString() {
            return "RodData[" +
                    "minPos=" + minPos + ", " +
                    "maxPos=" + maxPos + ", " +
                    "laser=" + laser + ", " +
                    "active=" + active + ']';
        }

    }
}
