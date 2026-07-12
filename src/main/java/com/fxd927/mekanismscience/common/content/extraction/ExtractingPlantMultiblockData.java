package com.fxd927.mekanismscience.common.content.extraction;

import com.fxd927.mekanismscience.api.MSNBTConstants;
import com.fxd927.mekanismscience.api.recipes.FluidGasToFluidRecipe;
import com.fxd927.mekanismscience.common.config.MSConfig;
import com.fxd927.mekanismscience.common.recipe.MSRecipeType;
import com.fxd927.mekanismscience.common.tile.multiblock.extraction.TileEntityExtractingPlantCasing;
import lombok.Setter;
import mekanism.api.NBTConstants;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasTank;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.cache.TwoInputCachedRecipe;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.inputs.InputHelper;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.api.recipes.outputs.OutputHelper;
import mekanism.common.capabilities.chemical.multiblock.MultiblockChemicalTankBuilder;
import mekanism.common.capabilities.fluid.VariableCapacityFluidTank;
import mekanism.common.inventory.container.sync.dynamic.ContainerSync;
import mekanism.common.lib.multiblock.IValveHandler;
import mekanism.common.lib.multiblock.MultiblockData;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.lookup.IDoubleRecipeLookupHandler.FluidChemicalRecipeLookupHandler;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.FluidChemical;
import mekanism.common.recipe.lookup.monitor.RecipeCacheLookupMonitor;
import mekanism.common.tile.prefab.TileEntityRecipeMachine;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.NBTUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BooleanSupplier;

public class ExtractingPlantMultiblockData
        extends MultiblockData
        implements IValveHandler, FluidChemicalRecipeLookupHandler<Gas, GasStack, FluidGasToFluidRecipe> {

    public static final RecipeError NOT_ENOUGH_GAS_INPUT = RecipeError.create();
    public static final RecipeError NOT_ENOUGH_FLUID_INPUT = RecipeError.create();
    private static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            NOT_ENOUGH_GAS_INPUT,
            NOT_ENOUGH_FLUID_INPUT,
            RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT
    );

    @Setter
    @ContainerSync
    private int extractantTankCapacity;
    @ContainerSync
    private int leachateTankCapacity;

    @ContainerSync
    public IGasTank extractantTank;
    @ContainerSync
    public VariableCapacityFluidTank leachateTank;
    @ContainerSync
    public VariableCapacityFluidTank outputTank;

    @ContainerSync
    public long lastGain;
    private int operations;

    private final RecipeCacheLookupMonitor<FluidGasToFluidRecipe> recipeCacheLookupMonitor;
    private final BooleanSupplier recheckAllRecipeErrors;
    @ContainerSync
    private final boolean[] trackedErrors = new boolean[TRACKED_ERROR_TYPES.size()];

    private final IOutputHandler<@NotNull FluidStack> outputHandler;
    private final IInputHandler<@NotNull GasStack> extractantInputHandler;
    private final IInputHandler<@NotNull FluidStack> leachateInputHandler;

    public float prevExtractantScale;
    public float prevLeachateScale;
    public float prevOutputScale;

    public ExtractingPlantMultiblockData(TileEntityExtractingPlantCasing tile) {
        super(tile);
        recipeCacheLookupMonitor = new RecipeCacheLookupMonitor<>(this);
        recheckAllRecipeErrors = TileEntityRecipeMachine.shouldRecheckAllErrors(tile);
        gasTanks.add(extractantTank = MultiblockChemicalTankBuilder.GAS.input(this, () -> extractantTankCapacity,
                gas -> containsRecipeBA(leachateTank.getFluid(), gas), this));
        fluidTanks.add(leachateTank = VariableCapacityFluidTank.input(this, () -> leachateTankCapacity,
                fluid -> containsRecipeAB(fluid, extractantTank.getStack()), this));
        fluidTanks.add(outputTank = VariableCapacityFluidTank.output(this, () -> extractantTankCapacity,
                fluid -> true, this));
        extractantInputHandler = InputHelper.getInputHandler(extractantTank, NOT_ENOUGH_GAS_INPUT);
        leachateInputHandler = InputHelper.getInputHandler(leachateTank, NOT_ENOUGH_FLUID_INPUT);
        outputHandler = OutputHelper.getOutputHandler(outputTank, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
    }

    @Override
    public boolean tick(Level world) {
        boolean needsPacket = super.tick(world);
        recipeCacheLookupMonitor.updateAndProcess();

        float extractantScale = MekanismUtils.getScale(prevExtractantScale, extractantTank);
        float leachateScale = MekanismUtils.getScale(prevLeachateScale, leachateTank);
        float outputScale = MekanismUtils.getScale(prevOutputScale, outputTank);
        if (extractantScale != prevExtractantScale || leachateScale != prevLeachateScale || outputScale != prevOutputScale) {
            needsPacket = true;
            prevExtractantScale = extractantScale;
            prevLeachateScale = leachateScale;
            prevOutputScale = outputScale;
        }
        return needsPacket;
    }

    @Override
    public void readUpdateTag(CompoundTag tag) {
        super.readUpdateTag(tag);
        NBTUtils.setFloatIfPresent(tag, NBTConstants.SCALE, scale -> prevExtractantScale = scale);
        NBTUtils.setFloatIfPresent(tag, NBTConstants.SCALE_ALT, scale -> prevLeachateScale = scale);
        NBTUtils.setFloatIfPresent(tag, NBTConstants.SCALE_ALT_2, scale -> prevOutputScale = scale);
        NBTUtils.setIntIfPresent(tag, NBTConstants.VOLUME, this::setVolume);
        NBTUtils.setGasStackIfPresent(tag, NBTConstants.GAS_STORED, stack -> extractantTank.setStack(stack));
        NBTUtils.setFluidStackIfPresent(tag, NBTConstants.FLUID_STORED, stack -> leachateTank.setStack(stack));
        NBTUtils.setFluidStackIfPresent(tag, MSNBTConstants.FLUID_STORED_ALT, stack -> outputTank.setStack(stack));
        readValves(tag);
    }

    @Override
    public void writeUpdateTag(CompoundTag tag) {
        super.writeUpdateTag(tag);
        tag.putFloat(NBTConstants.SCALE, prevExtractantScale);
        tag.putFloat(NBTConstants.SCALE_ALT, prevLeachateScale);
        tag.putFloat(NBTConstants.SCALE_ALT_2, prevOutputScale);
        tag.putInt(NBTConstants.VOLUME, getVolume());
        tag.put(NBTConstants.GAS_STORED, extractantTank.getStack().write(new CompoundTag()));
        tag.put(NBTConstants.FLUID_STORED, leachateTank.getFluid().writeToNBT(new CompoundTag()));
        tag.put(MSNBTConstants.FLUID_STORED_ALT, outputTank.getFluid().writeToNBT(new CompoundTag()));
        writeValves(tag);
    }

    @Override
    public void setVolume(int volume) {
        if (getVolume() != volume) {
            super.setVolume(volume);
            leachateTankCapacity = volume * MSConfig.generalConfig.extractionLeachatePerTank.get();
            operations = volume / 5;
        }
    }

    public boolean handlesSound(TileEntityExtractingPlantCasing tile) {
        return getBounds().isOnCorner(tile.getBlockPos());
    }

    @Override
    @NotNull
    public IMekanismRecipeTypeProvider<FluidGasToFluidRecipe, FluidChemical<Gas, GasStack, FluidGasToFluidRecipe>> getRecipeType() {
        return MSRecipeType.EXTRACTION;
    }

    @Override
    @Nullable
    public FluidGasToFluidRecipe getRecipe(int cacheIndex) {
        return findFirstRecipe(leachateInputHandler, extractantInputHandler);
    }

    @Override
    @NotNull
    public CachedRecipe<FluidGasToFluidRecipe> createNewCachedRecipe(@NotNull FluidGasToFluidRecipe recipe, int cacheIndex) {
        return new TwoInputCachedRecipe<>(recipe, recheckAllRecipeErrors, leachateInputHandler, extractantInputHandler,
                outputHandler, recipe::getFluidInput, recipe::getChemicalInput, recipe::getOutput, FluidStack::isEmpty,
                ChemicalStack::isEmpty, FluidStack::isEmpty) {}
                .setErrorsChanged(errors -> {
                    for (int i = 0; i < trackedErrors.length; i++) {
                        trackedErrors[i] = errors.contains(TRACKED_ERROR_TYPES.get(i));
                    }
                })
                .setActive(active -> lastGain = active ? (long) operations * recipe.getOutputDefinition().get(0).getAmount() : 0)
                .setRequiredTicks(() -> 1)
                .setBaselineMaxOperations(() -> operations);
    }

    public boolean hasWarning(RecipeError error) {
        int errorIndex = TRACKED_ERROR_TYPES.indexOf(error);
        if (errorIndex == -1) {
            //Something went wrong
            return false;
        }
        return trackedErrors[errorIndex];
    }

    @Override
    public Level getHandlerWorld() {
        return getWorld();
    }
}
