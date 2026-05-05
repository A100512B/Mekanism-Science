package com.fxd927.mekanismscience.api.recipes;

import lombok.Getter;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.math.FloatingLong;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@NothingNullByDefault
public abstract class MetalElectrolysisRecipe extends MekanismRecipe implements Predicate<FluidStack> {

    @Getter
    private final FluidStackIngredient input;
    protected final ItemStack output;
    @Getter
    private final FloatingLong energyRequired;

    protected MetalElectrolysisRecipe(ResourceLocation id, FluidStackIngredient input, ItemStack output, FloatingLong energyRequired) {
        super(id);
        this.input = Objects.requireNonNull(input, "Input cannot be null.");
        Objects.requireNonNull(output, "Output cannot be null.");
        if (output.isEmpty())
            throw new IllegalArgumentException("Output cannot be empty.");
        this.output = output.copy();
        if (energyRequired.smallerOrEqual(FloatingLong.ZERO))
            throw new IllegalArgumentException("Energy required must be greater than 0.");
        this.energyRequired = energyRequired;
    }

    @Override
    public boolean test(FluidStack stack) {
        return input.test(stack);
    }

    /**
     * For JEI, gets the output representations to display.
     *
     * @return Representation of the output, <strong>MUST NOT</strong> be modified.
     */
    public List<ItemStack> getOutputDefinition() {
        return Collections.singletonList(output);
    }

    /**
     * Gets a new output based on the given inputs.
     *
     * @param stack Specific fluid input.
     *
     * @return New output.
     *
     * @apiNote While Mekanism does not currently make use of the inputs, it is important to support it and pass the proper value in case any addons define input based
     * outputs where things like NBT may be different.
     * @implNote The passed in inputs should <strong>NOT</strong> be modified.
     */
    public ItemStack getOutput(FluidStack stack) {
        return output;
    }

    @Override
    public boolean isIncomplete() {
        return input.hasNoMatchingInstances();
    }

    @Override
    public void logMissingTags() {
        input.logMissingTags();
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        energyRequired.writeToBuffer(buffer);
        input.write(buffer);
        buffer.writeItem(output);
    }
}
