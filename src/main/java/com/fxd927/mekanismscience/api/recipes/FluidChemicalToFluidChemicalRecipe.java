package com.fxd927.mekanismscience.api.recipes;

import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

@SuppressWarnings("unchecked")
@NothingNullByDefault
public abstract class FluidChemicalToFluidChemicalRecipe<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>,
        INGREDIENT extends ChemicalStackIngredient<CHEMICAL, STACK>> extends MekanismRecipe implements BiPredicate<@NotNull FluidStack, @NotNull STACK> {

    private final FluidStackIngredient fluidInput;
    private final INGREDIENT chemicalInput;
    protected final FluidStack fluidOutput;
    protected final STACK chemicalOutput;

    public FluidChemicalToFluidChemicalRecipe(ResourceLocation id, FluidStackIngredient fluidInput, INGREDIENT chemicalInput,
                                              FluidStack fluidOutput, STACK chemicalOutput) {
        super(id);
        this.fluidInput = Objects.requireNonNull(fluidInput, "Fluid input cannot be null.");
        this.chemicalInput = Objects.requireNonNull(chemicalInput, "Chemical input cannot be null.");
        Objects.requireNonNull(fluidOutput, "Fluid output cannot be null.");
        if (fluidOutput.isEmpty())
            throw new IllegalArgumentException("Fluid output cannot be empty.");
        this.fluidOutput = fluidOutput.copy();
        Objects.requireNonNull(chemicalOutput, "Chemical output cannot be null.");
        if (chemicalOutput.isEmpty())
            throw new IllegalArgumentException("Chemical output cannot be empty.");
        this.chemicalOutput = (STACK) chemicalOutput.copy();
    }

    @Override
    public boolean test(FluidStack fluidStack, STACK chemicalStack) {
        return fluidInput.test(fluidStack) && chemicalInput.test(chemicalStack);
    }

    /**
     * Gets the input fluid ingredient.
     */
    public FluidStackIngredient getFluidInput() {
        return fluidInput;
    }

    /**
     * Gets the input chemical ingredient.
     */
    public INGREDIENT getChemicalInput() {
        return chemicalInput;
    }

    /**
     * For JEI, gets the output representations to display.
     *
     * @return Representation of the output, <strong>MUST NOT</strong> be modified.
     */
    public List<FluidChemicalOutput<CHEMICAL, STACK>> getOutputDefinition() {
        return Collections.singletonList(new FluidChemicalOutput<>(fluidOutput, chemicalOutput));
    }

    /**
     * Gets a new output based on the given inputs.
     *
     * @param fluidStack    Specific fluid input.
     * @param chemicalStack Specific chemical input.
     *
     * @return New output.
     *
     * @apiNote While Mekanism does not currently make use of the inputs, it is important to support it and pass the proper value in case any addons define input based
     * outputs where things like NBT may be different.
     * @implNote The passed in inputs should <strong>NOT</strong> be modified.
     */
    @Contract(value = "_, _ -> new", pure = true)
    public FluidChemicalOutput<CHEMICAL, STACK> getOutput(FluidStack fluidStack, STACK chemicalStack) {
        return new FluidChemicalOutput<>(fluidOutput.copy(), (STACK) chemicalOutput.copy());
    }

    @Override
    public boolean isIncomplete() {
        return fluidInput.hasNoMatchingInstances() || chemicalInput.hasNoMatchingInstances();
    }

    @Override
    public void logMissingTags() {
        fluidInput.logMissingTags();
        chemicalInput.logMissingTags();
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        fluidInput.write(buffer);
        chemicalInput.write(buffer);
        fluidOutput.writeToPacket(buffer);
        chemicalOutput.writeToPacket(buffer);
    }

    public record FluidChemicalOutput<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>>(FluidStack fluidOutput, STACK chemicalOutput) {

        public FluidChemicalOutput {
            Objects.requireNonNull(fluidOutput, "Fluid output cannot be null.");
            if (fluidOutput.isEmpty())
                throw new IllegalArgumentException("Fluid output cannot be empty.");
            Objects.requireNonNull(chemicalOutput, "Chemical output cannot be null.");
            if (chemicalOutput.isEmpty())
                throw new IllegalArgumentException("Chemical output cannot be empty.");
        }
    }
}
