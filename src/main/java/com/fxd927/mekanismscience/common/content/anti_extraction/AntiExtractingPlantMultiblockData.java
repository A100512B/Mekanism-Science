package com.fxd927.mekanismscience.common.content.anti_extraction;

import com.fxd927.mekanismscience.api.MSNBTConstants;
import com.fxd927.mekanismscience.api.recipes.FluidChemicalToFluidChemicalRecipe;
import com.fxd927.mekanismscience.api.recipes.FluidChemicalToFluidChemicalRecipe.FluidChemicalOutput;
import com.fxd927.mekanismscience.common.config.MSConfig;
import com.fxd927.mekanismscience.common.recipe.MSRecipeType;
import com.fxd927.mekanismscience.common.tile.multiblock.anti_extraction.TileEntityAntiExtractingPlantCasing;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.NBTConstants;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasTank;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.functions.ConstantPredicates;
import mekanism.api.math.MathUtils;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.cache.TwoInputCachedRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient.GasStackIngredient;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.inputs.InputHelper;
import mekanism.api.recipes.outputs.IOutputHandler;
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

public class AntiExtractingPlantMultiblockData
        extends MultiblockData
        implements IValveHandler, FluidChemicalRecipeLookupHandler<Gas, GasStack, FluidChemicalToFluidChemicalRecipe<Gas, GasStack, GasStackIngredient>> {

    public static final RecipeError NOT_ENOUGH_GAS_INPUT = RecipeError.create();
    public static final RecipeError NOT_ENOUGH_FLUID_INPUT = RecipeError.create();
    public static final RecipeError NOT_ENOUGH_GAS_OUTPUT_SPACE = RecipeError.create();
    public static final RecipeError NOT_ENOUGH_FLUID_OUTPUT_SPACE = RecipeError.create();
    private static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            NOT_ENOUGH_GAS_INPUT,
            NOT_ENOUGH_FLUID_INPUT,
            NOT_ENOUGH_GAS_OUTPUT_SPACE,
            NOT_ENOUGH_FLUID_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT
    );

    @ContainerSync
    private long antiExtractantTankCapacity;
    @ContainerSync
    private int extractTankCapacity;

    @ContainerSync
    public IGasTank antiExtractantTank;
    @ContainerSync
    public VariableCapacityFluidTank extractTank;
    @ContainerSync
    public IGasTank extractantTank;
    @ContainerSync
    public VariableCapacityFluidTank concentrateTank;

    @ContainerSync
    public long lastGain;
    private long expectToAntiExtract = 0;

    private final RecipeCacheLookupMonitor<FluidChemicalToFluidChemicalRecipe<Gas, GasStack, GasStackIngredient>> recipeCacheLookupMonitor;
    private final BooleanSupplier recheckAllRecipeErrors;
    @ContainerSync
    private final boolean[] trackedErrors = new boolean[TRACKED_ERROR_TYPES.size()];

    private final IOutputHandler<@NotNull FluidChemicalOutput<Gas, GasStack>> outputHandler;
    private final IInputHandler<@NotNull GasStack> antiExtractantInputHandler;
    private final IInputHandler<@NotNull FluidStack> extractInputHandler;

    public float prevAntiExtractantScale;
    public float prevExtractScale;
    public float prevExtractantScale;
    public float prevConcentrateScale;

    public AntiExtractingPlantMultiblockData(TileEntityAntiExtractingPlantCasing tile) {
        super(tile);
        recipeCacheLookupMonitor = new RecipeCacheLookupMonitor<>(this);
        recheckAllRecipeErrors = TileEntityRecipeMachine.shouldRecheckAllErrors(tile);
        gasTanks.add(antiExtractantTank = MultiblockChemicalTankBuilder.GAS.input(this, () -> antiExtractantTankCapacity,
                this::containsRecipeB, this));
        fluidTanks.add(extractTank = VariableCapacityFluidTank.input(this, () -> extractTankCapacity,
                this::containsRecipeA, this));
        gasTanks.add(extractantTank = MultiblockChemicalTankBuilder.GAS.output(this, () -> antiExtractantTankCapacity,
                gas -> true, this));
        fluidTanks.add(VariableCapacityFluidTank.output(this, () -> extractTankCapacity,
                fluid -> true, this));
        antiExtractantInputHandler = InputHelper.getInputHandler(antiExtractantTank, NOT_ENOUGH_GAS_INPUT);
        extractInputHandler = InputHelper.getInputHandler(extractTank, NOT_ENOUGH_FLUID_INPUT);
        outputHandler = new IOutputHandler<FluidChemicalOutput<Gas, GasStack>>() {

            @Override
            public void handleOutput(@NotNull FluidChemicalOutput<Gas, GasStack> toOutput, int operations) {
                handleOutput(concentrateTank, toOutput.fluidOutput(), operations);
                handleOutput(extractantTank, toOutput.chemicalOutput(), operations);
            }

            @Override
            public void calculateOperationsCanSupport(@NotNull OperationTracker tracker, @NotNull FluidChemicalOutput<Gas, GasStack> toOutput) {
                calculateOperationsCanSupport(tracker, NOT_ENOUGH_FLUID_OUTPUT_SPACE, concentrateTank, toOutput.fluidOutput());
                if (tracker.shouldContinueChecking())
                    calculateOperationsCanSupport(tracker, NOT_ENOUGH_GAS_OUTPUT_SPACE, extractantTank, toOutput.chemicalOutput());
            }

            // Copied from OutputHandler - why are they package-private :(
            private static <STACK extends ChemicalStack<?>> void handleOutput(IChemicalTank<?, STACK> tank, STACK toOutput, int operations) {
                if (operations == 0) {
                    //This should not happen
                    return;
                }
                STACK output = tank.createStack(toOutput, toOutput.getAmount() * operations);
                tank.insert(output, Action.EXECUTE, AutomationType.INTERNAL);
            }

            private static void handleOutput(IExtendedFluidTank fluidTank, FluidStack toOutput, int operations) {
                if (operations == 0) {
                    //This should not happen
                    return;
                }
                fluidTank.insert(new FluidStack(toOutput, toOutput.getAmount() * operations), Action.EXECUTE, AutomationType.INTERNAL);
            }
            static <STACK extends ChemicalStack<?>> void calculateOperationsCanSupport(OperationTracker tracker, RecipeError notEnoughSpace, IChemicalTank<?, STACK> tank,
                                                                                       STACK toOutput) {
                //If our output is empty, we have nothing to add, so we treat it as being able to fit all
                if (!toOutput.isEmpty()) {
                    //Copy the stack and make it be max size
                    STACK maxOutput = tank.createStack(toOutput, Long.MAX_VALUE);
                    //Divide the amount we can actually use by the amount one output operation is equal to, capping it at the max we were told about
                    STACK remainder = tank.insert(maxOutput, Action.SIMULATE, AutomationType.INTERNAL);
                    long amountUsed = maxOutput.getAmount() - remainder.getAmount();
                    //Divide the amount we can actually use by the amount one output operation is equal to, capping it at the max we were told about
                    int operations = MathUtils.clampToInt(amountUsed / toOutput.getAmount());
                    tracker.updateOperations(operations);
                    if (operations == 0) {
                        if (amountUsed == 0 && tank.getNeeded() > 0) {
                            tracker.addError(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);
                        } else {
                            tracker.addError(notEnoughSpace);
                        }
                    }
                }
            }

            private static void calculateOperationsCanSupport(OperationTracker tracker, RecipeError notEnoughSpace, IExtendedFluidTank tank, FluidStack toOutput) {
                //If our output is empty, we have nothing to add, so we treat it as being able to fit all
                if (!toOutput.isEmpty()) {
                    //Copy the stack and make it be max size
                    FluidStack maxOutput = new FluidStack(toOutput, Integer.MAX_VALUE);
                    //Then simulate filling the fluid tank, so we can see how much actually can fit
                    FluidStack remainder = tank.insert(maxOutput, Action.SIMULATE, AutomationType.INTERNAL);
                    int amountUsed = maxOutput.getAmount() - remainder.getAmount();
                    //Divide the amount we can actually use by the amount one output operation is equal to, capping it at the max we were told about
                    int operations = amountUsed / toOutput.getAmount();
                    tracker.updateOperations(operations);
                    if (operations == 0) {
                        if (amountUsed == 0 && tank.getNeeded() > 0) {
                            tracker.addError(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);
                        } else {
                            tracker.addError(notEnoughSpace);
                        }
                    }
                }
            }
        };
    }

    @Override
    public boolean tick(Level world) {
        boolean needsPacket = super.tick(world);
        // Always anti-extract 5% of max capacity
        expectToAntiExtract = (long) Math.max(extractTankCapacity * 0.05, extractTank.getFluidAmount());
        recipeCacheLookupMonitor.updateAndProcess();

        float antiExtractantScale = MekanismUtils.getScale(prevAntiExtractantScale, antiExtractantTank);
        float extractScale = MekanismUtils.getScale(prevExtractScale, extractTank);
        float extractantScale = MekanismUtils.getScale(prevExtractantScale, extractantTank);
        float concentrateScale = MekanismUtils.getScale(prevConcentrateScale, concentrateTank);
        if (antiExtractantScale != prevAntiExtractantScale || extractScale != prevExtractScale
                || extractantScale != prevExtractantScale || concentrateScale != prevConcentrateScale) {
            needsPacket = true;
            prevAntiExtractantScale = antiExtractantScale;
            prevExtractScale = extractScale;
            prevExtractantScale = extractantScale;
            prevConcentrateScale = concentrateScale;
        }
        return needsPacket;
    }

    @Override
    public void readUpdateTag(CompoundTag tag) {
        super.readUpdateTag(tag);
        NBTUtils.setFloatIfPresent(tag, NBTConstants.SCALE, scale -> prevAntiExtractantScale = scale);
        NBTUtils.setFloatIfPresent(tag, NBTConstants.SCALE_ALT, scale -> prevExtractScale = scale);
        NBTUtils.setFloatIfPresent(tag, NBTConstants.SCALE_ALT_2, scale -> prevExtractantScale = scale);
        NBTUtils.setFloatIfPresent(tag, NBTConstants.SCALE_ALT_3, scale -> prevConcentrateScale = scale);
        NBTUtils.setIntIfPresent(tag, NBTConstants.VOLUME, this::setVolume);
        NBTUtils.setGasStackIfPresent(tag, NBTConstants.GAS_STORED, stack -> antiExtractantTank.setStack(stack));
        NBTUtils.setFluidStackIfPresent(tag, NBTConstants.FLUID_STORED, stack -> extractTank.setStack(stack));
        NBTUtils.setGasStackIfPresent(tag, NBTConstants.GAS_STORED_ALT, stack -> extractantTank.setStack(stack));
        NBTUtils.setFluidStackIfPresent(tag, MSNBTConstants.FLUID_STORED_ALT, stack -> concentrateTank.setStack(stack));
        readValves(tag);
    }

    @Override
    public void writeUpdateTag(CompoundTag tag) {
        super.writeUpdateTag(tag);
        tag.putFloat(NBTConstants.SCALE, prevAntiExtractantScale);
        tag.putFloat(NBTConstants.SCALE_ALT, prevExtractScale);
        tag.putFloat(NBTConstants.SCALE_ALT_2, prevExtractantScale);
        tag.putFloat(NBTConstants.SCALE_ALT_3, prevConcentrateScale);
        tag.putInt(NBTConstants.VOLUME, getVolume());
        tag.put(NBTConstants.GAS_STORED, antiExtractantTank.getStack().write(new CompoundTag()));
        tag.put(NBTConstants.FLUID_STORED, extractTank.getFluid().writeToNBT(new CompoundTag()));
        tag.put(NBTConstants.GAS_STORED_ALT, extractantTank.getStack().write(new CompoundTag()));
        tag.put(MSNBTConstants.FLUID_STORED_ALT, concentrateTank.getFluid().writeToNBT(new CompoundTag()));
        writeValves(tag);
    }

    @Override
    public void setVolume(int volume) {
        if (getVolume() != volume) {
            super.setVolume(volume);
            extractTankCapacity = volume * MSConfig.generalConfig.antiExtractionExtractPerTank.get();
        }
    }

    public void setAntiExtractantTankCapacity(long capacity) {
        this.antiExtractantTankCapacity = capacity;
    }

    public boolean handlesSound(TileEntityAntiExtractingPlantCasing tile) {
        return getBounds().isOnCorner(tile.getBlockPos());
    }

    @Override
    @NotNull
    public IMekanismRecipeTypeProvider<FluidChemicalToFluidChemicalRecipe<Gas, GasStack, GasStackIngredient>, FluidChemical<Gas, GasStack, FluidChemicalToFluidChemicalRecipe<Gas, GasStack, GasStackIngredient>>> getRecipeType() {
        return MSRecipeType.ANTI_EXTRACTION;
    }

    @Override
    @Nullable
    public FluidChemicalToFluidChemicalRecipe<Gas, GasStack, GasStackIngredient> getRecipe(int cacheIndex) {
        return findFirstRecipe(extractInputHandler, antiExtractantInputHandler);
    }

    @Override
    @NotNull
    public CachedRecipe<FluidChemicalToFluidChemicalRecipe<Gas, GasStack, GasStackIngredient>> createNewCachedRecipe(@NotNull FluidChemicalToFluidChemicalRecipe<Gas, GasStack, GasStackIngredient> recipe, int cacheIndex) {
        return new TwoInputCachedRecipe<>(recipe, recheckAllRecipeErrors, extractInputHandler, antiExtractantInputHandler, outputHandler,
                recipe::getFluidInput, recipe::getChemicalInput, recipe::getOutput, FluidStack::isEmpty, ChemicalStack::isEmpty,
                ConstantPredicates.alwaysFalse()) {}
                .setErrorsChanged(errors -> {
                    for (int i = 0; i < trackedErrors.length; i++) {
                        trackedErrors[i] = errors.contains(TRACKED_ERROR_TYPES.get(i));
                    }
                })
                .setActive(active -> lastGain = active ? expectToAntiExtract : 0)
                .setRequiredTicks(() -> 1)
                .setBaselineMaxOperations(() -> Math.toIntExact(expectToAntiExtract));
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
