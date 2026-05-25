package com.fxd927.mekanismscience.client.jei;

import com.fxd927.mekanismscience.api.recipes.*;
import com.fxd927.mekanismscience.common.registries.MSBlocks;
import mekanism.api.recipes.GasToGasRecipe;
import mekanism.client.jei.MekanismJEIRecipeType;

public class MSJEIRecipeType {

    public static final MekanismJEIRecipeType<GasToGasRecipe> PRESSURIZED_POLYMERIZING = new MekanismJEIRecipeType<>(MSBlocks.PRESSURIZED_POLYMERIZING_CHAMBER, GasToGasRecipe.class);
    public static final MekanismJEIRecipeType<ItemStackGasToFluidRecipe> ACID_LEACHING = new MekanismJEIRecipeType<>(MSBlocks.ACID_LEACHER, ItemStackGasToFluidRecipe.class);
    public static final MekanismJEIRecipeType<FluidGasToFluidRecipe> EXTRACTION = new MekanismJEIRecipeType<>(MSBlocks.EXTRACTING_PLANT_CASING, FluidGasToFluidRecipe.class);
    public static final MekanismJEIRecipeType<FluidGasToFluidGasRecipe> ANTI_EXTRACTION = new MekanismJEIRecipeType<>(MSBlocks.ANTI_EXTRACTING_PLANT_CASING, FluidGasToFluidGasRecipe.class);
    public static final MekanismJEIRecipeType<MetalElectrolysisRecipe> METAL_ELECTROLYSIS = new MekanismJEIRecipeType<>(MSBlocks.METAL_ELECTROLYSIS_CHAMBER_CASING, MetalElectrolysisRecipe.class);
    public static final MekanismJEIRecipeType<AdsorptionRecipe> ADSORPTION = new MekanismJEIRecipeType<>(MSBlocks.ADSORPTION_SEPARATOR, AdsorptionRecipe.class);
    public static final MekanismJEIRecipeType<IrradiatingRecipe> IRRADIATING = new MekanismJEIRecipeType<>(MSBlocks.IRRADIATOR, IrradiatingRecipe.class);

    private MSJEIRecipeType() {
    }
}
