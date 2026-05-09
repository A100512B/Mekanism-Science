package com.fxd927.mekanismscience.api.recipes;

import lombok.Getter;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Contract;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;

@NothingNullByDefault
public abstract class ItemStackChemicalToFluidRecipe<CHEMICAL extends Chemical<CHEMICAL>, STACK extends ChemicalStack<CHEMICAL>,
        INGREDIENT extends ChemicalStackIngredient<CHEMICAL, STACK>> extends MekanismRecipe implements BiPredicate<ItemStack, STACK> {

    @Getter
    private final ItemStackIngredient itemInput;
    @Getter
    private final INGREDIENT chemicalInput;
    private final FluidStack output;

    public ItemStackChemicalToFluidRecipe(ResourceLocation id, ItemStackIngredient itemInput, INGREDIENT chemicalInput,
                                          FluidStack output) {
        super(id);
        this.itemInput = Objects.requireNonNull(itemInput, "Item input cannot be null.");
        this.chemicalInput = Objects.requireNonNull(chemicalInput, "Chemical input cannot be null.");
        Objects.requireNonNull(output, "Output cannot be null.");
        if (output.isEmpty())
            throw new IllegalArgumentException("Output cannot be empty.");
        this.output = output.copy();
    }

    @Override
    public boolean test(ItemStack itemStack, STACK chemicalStack) {
        return itemInput.test(itemStack) && chemicalInput.test(chemicalStack);
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
     * @param itemStack     Specific item input.
     * @param chemicalStack Specific chemical input.
     * @return New output.
     * @apiNote While Mekanism does not currently make use of the inputs, it is important to support it and pass the proper value in case any addons define input based
     * outputs where things like NBT may be different.
     * @implNote The passed in inputs should <strong>NOT</strong> be modified.
     */
    @Contract(value = "_, _ -> new", pure = true)
    public FluidStack getOutput(ItemStack itemStack, STACK chemicalStack) {
        return output.copy();
    }

    @Override
    public boolean isIncomplete() {
        return itemInput.hasNoMatchingInstances() || chemicalInput.hasNoMatchingInstances();
    }

    @Override
    public void logMissingTags() {
        itemInput.logMissingTags();
        chemicalInput.logMissingTags();
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        itemInput.write(buf);
        chemicalInput.write(buf);
        output.writeToPacket(buf);
    }
}
