package com.fxd927.mekanismscience.common.content.extraction;

import com.fxd927.mekanismscience.common.config.MSConfig;
import com.fxd927.mekanismscience.common.recipe.MSRecipeType;
import com.fxd927.mekanismscience.common.registries.MSGases.Extractant;
import com.fxd927.mekanismscience.common.tile.multiblock.extraction.TileEntityExtractingPlantCasing;
import mekanism.api.NBTConstants;
import mekanism.api.chemical.attribute.ChemicalAttributeValidator;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasTank;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.cache.TwoInputCachedRecipe;
import mekanism.api.recipes.chemical.FluidChemicalToChemicalRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient.GasStackIngredient;
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
import mekanism.common.recipe.lookup.cache.InputRecipeCache;
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
        implements IValveHandler, FluidChemicalRecipeLookupHandler<Gas, GasStack, FluidChemicalToChemicalRecipe<Gas, GasStack, GasStackIngredient>> {

    public static final RecipeError NOT_ENOUGH_GAS_INPUT = RecipeError.create();
    public static final RecipeError NOT_ENOUGH_FLUID_INPUT = RecipeError.create();
    private static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            NOT_ENOUGH_GAS_INPUT,
            NOT_ENOUGH_FLUID_INPUT,
            RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT
    );

    @ContainerSync
    private long extractantTankCapacity;
    @ContainerSync
    private int leachateTankCapacity;

    @ContainerSync
    public IGasTank extractantTank;
    @ContainerSync
    public VariableCapacityFluidTank leachateTank;
    @ContainerSync
    public IGasTank outputTank;

    @ContainerSync
    public long lastGain;
    private long expectToExtract = 0;

    private final RecipeCacheLookupMonitor<FluidChemicalToChemicalRecipe<Gas, GasStack, GasStackIngredient>> recipeCacheLookupMonitor;
    private final BooleanSupplier recheckAllRecipeErrors;
    @ContainerSync
    private final boolean[] trackedErrors = new boolean[TRACKED_ERROR_TYPES.size()];

    private final IOutputHandler<@NotNull GasStack> outputHandler;
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
                this::containsRecipeB, ChemicalAttributeValidator.create(Extractant.class), this));
        fluidTanks.add(leachateTank = VariableCapacityFluidTank.input(this, () -> leachateTankCapacity,
                this::containsRecipeA, this));
        gasTanks.add(outputTank = MultiblockChemicalTankBuilder.GAS.output(this, () -> extractantTankCapacity,
                gas -> true, this));
        extractantInputHandler = InputHelper.getInputHandler(extractantTank, NOT_ENOUGH_GAS_INPUT);
        leachateInputHandler = InputHelper.getInputHandler(leachateTank, NOT_ENOUGH_FLUID_INPUT);
        outputHandler = OutputHelper.getOutputHandler(outputTank, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
    }

    @Override
    public boolean tick(Level world) {
        boolean needsPacket = super.tick(world);
        long outputStored = outputTank.getStored();
        long total = outputStored + leachateTank.getFluidAmount();
        if ((double) outputStored / total < extractantTank.getType().get(Extractant.class).getExtractionEfficiency()) {
            long waterPhaseAmount = leachateTank.getFluidAmount();
            long organicPhaseAmount = extractantTank.getStored();
            // Calculate how much output we get for this operation based on the extracting efficiency
            double e = extractantTank.getType().get(Extractant.class).getExtractionEfficiency();
            // for each operation, we extract 75% of the available leachate
            // E = n_water / (n_water + n_organic)
            // it solves n_organic_max = (E/(1-E)) * n_water
            long maxOrganic = (long) (waterPhaseAmount * (e/(1-e)));
            expectToExtract = (long) ((maxOrganic - organicPhaseAmount) * 0.75);
            recipeCacheLookupMonitor.updateAndProcess();
        }

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
        NBTUtils.setGasStackIfPresent(tag, NBTConstants.GAS_STORED_ALT, stack -> outputTank.setStack(stack));
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
        tag.put(NBTConstants.GAS_STORED_ALT, outputTank.getStack().write(new CompoundTag()));
        writeValves(tag);
    }

    @Override
    public void setVolume(int volume) {
        if (getVolume() != volume) {
            super.setVolume(volume);
            leachateTankCapacity = volume * MSConfig.generalConfig.extractionLeachatePerTank.get();
        }
    }

    public void setExtractantTankCapacity(long extractantTankCapacity) {
        this.extractantTankCapacity = extractantTankCapacity;
    }

    @Override
    @Nullable
    public FluidChemicalToChemicalRecipe<Gas, GasStack, GasStackIngredient> getRecipe(int cacheIndex) {
        return findFirstRecipe(leachateInputHandler, extractantInputHandler);
    }

    @Override
    @NotNull
    public CachedRecipe<FluidChemicalToChemicalRecipe<Gas, GasStack, GasStackIngredient>> createNewCachedRecipe(@NotNull FluidChemicalToChemicalRecipe<Gas, GasStack, GasStackIngredient> recipe, int cacheIndex) {
        return TwoInputCachedRecipe.fluidChemicalToChemical(recipe, recheckAllRecipeErrors, leachateInputHandler, extractantInputHandler, outputHandler)
                .setErrorsChanged(errors -> {
                    for (int i = 0; i < trackedErrors.length; i++) {
                        trackedErrors[i] = errors.contains(TRACKED_ERROR_TYPES.get(i));
                    }
                })
                .setActive(active -> lastGain = active ? expectToExtract : 0)
                .setRequiredTicks(() -> 1)
                .setBaselineMaxOperations(() -> Math.toIntExact(expectToExtract));
    }

    @Override
    @NotNull
    public IMekanismRecipeTypeProvider<FluidChemicalToChemicalRecipe<Gas, GasStack, GasStackIngredient>, InputRecipeCache.FluidChemical<Gas, GasStack, FluidChemicalToChemicalRecipe<Gas, GasStack, GasStackIngredient>>> getRecipeType() {
        return MSRecipeType.EXTRACTING;
    }

    public boolean hasWarning(RecipeError error) {
        int errorIndex = TRACKED_ERROR_TYPES.indexOf(error);
        if (errorIndex == -1) {
            //Something went wrong
            return false;
        }
        return trackedErrors[errorIndex];
    }
}
