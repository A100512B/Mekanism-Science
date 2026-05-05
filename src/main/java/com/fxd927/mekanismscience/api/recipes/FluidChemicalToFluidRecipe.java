package com.fxd927.mekanismscience.api.recipes;

import lombok.Getter;
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

@NothingNullByDefault
public abstract class FluidChemicalToFluidRecipe<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>,
        INGREDIENT extends ChemicalStackIngredient<CHEMICAL, STACK>> extends MekanismRecipe implements BiPredicate<@NotNull FluidStack, @NotNull STACK> {

    @Getter
    private final FluidStackIngredient fluidInput;
    @Getter
    private final INGREDIENT chemicalInput;
    protected final FluidStack output;

    public FluidChemicalToFluidRecipe(ResourceLocation id, FluidStackIngredient fluidInput, INGREDIENT chemicalInput,
                                              FluidStack fluidOutput) {
        super(id);
        this.fluidInput = Objects.requireNonNull(fluidInput, "Fluid input cannot be null.");
        this.chemicalInput = Objects.requireNonNull(chemicalInput, "Chemical input cannot be null.");
        Objects.requireNonNull(fluidOutput, "Fluid output cannot be null.");
        if (fluidOutput.isEmpty())
            throw new IllegalArgumentException("Fluid output cannot be empty.");
        this.output = fluidOutput.copy();
    }

    @Override
    public boolean test(FluidStack fluidStack, STACK chemicalStack) {
        return fluidInput.test(fluidStack) && chemicalInput.test(chemicalStack);
    }

    /**
     * For JEI, gets the output representations to display.
     *
     * @return Representation of the output, <strong>MUST NOT</strong> be modified.
     */
    public List<FluidStack> getOutputDefinition() {
        return Collections.singletonList(output);
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
    public FluidStack getOutput(FluidStack fluidStack, STACK chemicalStack) {
        return output.copy();
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
        output.writeToPacket(buffer);
    }
}
