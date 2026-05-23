package com.fxd927.mekanismscience.common.registries;

import com.fxd927.mekanismscience.common.MekanismScience;
import mekanism.common.registration.impl.FluidDeferredRegister;
import mekanism.common.registration.impl.FluidDeferredRegister.MekanismFluidType;
import mekanism.common.registration.impl.FluidRegistryObject;
import mekanism.common.resource.IResource;
import mekanism.common.resource.MiscResource;
import mekanism.common.resource.PrimaryResource;
import mekanism.common.util.EnumUtils;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraftforge.fluids.ForgeFlowingFluid.Flowing;
import net.minecraftforge.fluids.ForgeFlowingFluid.Source;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

@SuppressWarnings("unused")
public class MSFluids {

    public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(MekanismScience.MODID);

    public static final Map<IResource, FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem>> PROCESSED_LEACHATE_RESOURCES = new LinkedHashMap<>();
    public static final Map<IResource, FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem>> PROCESSED_EXTRACT_RESOURCES = new LinkedHashMap<>();
    public static final Map<IResource, FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem>> PROCESSED_CONCENTRATE_RESOURCES = new LinkedHashMap<>();

    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> AMMONIA = FLUIDS.registerLiquidChemical(MSChemicalConstants.AMMONIA);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> BENZODIAZEPINE = FLUIDS.registerLiquidChemical(MSChemicalConstants.BENZODIAZEPINE);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> BERYLLIUM = FLUIDS.registerLiquidChemical(MSChemicalConstants.BERYLLIUM);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> BROMINE = FLUIDS.registerLiquidChemical(MSChemicalConstants.BROMINE);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> CHLOROMETHANE = FLUIDS.registerLiquidChemical(MSChemicalConstants.CHLOROMETHANE);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> CONCENTRATED_SEAWATER = FLUIDS.registerLiquidChemical(MSChemicalConstants.CONCENTRATED_SEAWATER);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> ETHANOL = FLUIDS.registerLiquidChemical(MSChemicalConstants.ETHANOL);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> HELIUM = FLUIDS.registerLiquidChemical(MSChemicalConstants.HELIUM);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> SUPERHEATED_HELIUM = FLUIDS.registerLiquidChemical(MSChemicalConstants.SUPERHEATED_HELIUM);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> IODINE = FLUIDS.registerLiquidChemical(MSChemicalConstants.IODINE);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> LACTOSE = FLUIDS.registerLiquidChemical(MSChemicalConstants.LACTOSE);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> METHANOL = FLUIDS.registerLiquidChemical(MSChemicalConstants.METHANOL);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> METHYLAMINE = FLUIDS.registerLiquidChemical(MSChemicalConstants.METHYLAMINE);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> METHYLAMMONIUM_LEAD_IODIDE = FLUIDS.registerLiquidChemical(MSChemicalConstants.METHYLAMMONIUM_LEAD_IODIDE);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> MILK = FLUIDS.registerLiquidChemical(MSChemicalConstants.MILK);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> SEAWATER = FLUIDS.registerLiquidChemical(MSChemicalConstants.SEAWATER);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> STRONTIUM = FLUIDS.registerLiquidChemical(MSChemicalConstants.STRONTIUM);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> TETRODOTOXIN = FLUIDS.registerLiquidChemical(MSChemicalConstants.TETRODOTOXIN);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> WHEY = FLUIDS.registerLiquidChemical(MSChemicalConstants.WHEY);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> YTTRIUM = FLUIDS.registerLiquidChemical(MSChemicalConstants.YTTRIUM);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> P204 = FLUIDS.register("p204", UnaryOperator.identity(), props -> props.tint(0xFF9A6414));
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> PHOSPHORYL_CHLORIDE = FLUIDS.registerLiquidChemical(MSChemicalConstants.PHOSPHORYL_CHLORIDE);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> IMPURE_PHOSPHORYL_CHLORIDE = FLUIDS.register("impure_phosphoryl_chloride", UnaryOperator.identity(), props -> props.tint(0xFF46F675));
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> ISOOCTANOL = FLUIDS.registerLiquidChemical(MSChemicalConstants.ISOOCTANOL);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> _2_ETHYL_2_HEXENAL = FLUIDS.registerLiquidChemical(MSChemicalConstants._2_ETHYL_2_HEXENAL);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> N_BUTYRALDEHYDE = FLUIDS.registerLiquidChemical(MSChemicalConstants.N_BUTYRALDEHYDE);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> ISOBUTYRALDEHYDE = FLUIDS.registerLiquidChemical(MSChemicalConstants.ISOBUTYRALDEHYDE);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> BUTYRALDEHYDE_MIXTURE = FLUIDS.register("butyraldehyde_mixture", UnaryOperator.identity(), props -> props.tint(0xFFEC724D));
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> PROPYLENE = FLUIDS.registerLiquidChemical(MSChemicalConstants.PROPYLENE);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> _2_BUTENE = FLUIDS.registerLiquidChemical(MSChemicalConstants._2_BUTENE);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> HYDRAZINE = FLUIDS.registerLiquidChemical(MSChemicalConstants.HYDRAZINE);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> WATER_GAS = FLUIDS.register("water_gas", UnaryOperator.identity(), props -> props.tint(0xFF002E57));
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> SODIUM_HYDROXIDE = FLUIDS.registerLiquidChemical(MSChemicalConstants.SODIUM_HYDROXIDE);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> COMPRESSED_AIR = FLUIDS.registerLiquidChemical(MSChemicalConstants.COMPRESSED_AIR);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> NITROGEN = FLUIDS.registerLiquidChemical(MSChemicalConstants.NITROGEN);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> NITRIC_OXIDE = FLUIDS.registerLiquidChemical(MSChemicalConstants.NITRIC_OXIDE);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> NITROGEN_DIOXIDE = FLUIDS.registerLiquidChemical(MSChemicalConstants.NITROGEN_DIOXIDE);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> NITRIC_ACID = FLUIDS.registerLiquidChemical(MSChemicalConstants.NITRIC_ACID);
    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> AQUA_REGIA = FLUIDS.register("aqua_regia", UnaryOperator.identity(), props -> props.tint(0xFFEB5414));

    static {
        for (PrimaryResource resource : EnumUtils.PRIMARY_RESOURCES) {
            PROCESSED_LEACHATE_RESOURCES.put(resource, FLUIDS.register(resource.getRegistrySuffix() + "_leachate", UnaryOperator.identity(), props -> props.tint(resource.getTint())));
            PROCESSED_EXTRACT_RESOURCES.put(resource, FLUIDS.register(resource.getRegistrySuffix() + "_p204_extract", UnaryOperator.identity(), props -> props.tint(resource.getTint())));
            PROCESSED_CONCENTRATE_RESOURCES.put(resource, FLUIDS.register(resource.getRegistrySuffix() + "_concentrate", UnaryOperator.identity(), props -> props.tint(resource.getTint())));
        }
        PROCESSED_LEACHATE_RESOURCES.put(MiscResource.NETHERITE, FLUIDS.register(MiscResource.NETHERITE.getRegistrySuffix() + "_leachate", UnaryOperator.identity(), props -> props.tint(0xFF513600)));
        PROCESSED_EXTRACT_RESOURCES.put(MiscResource.NETHERITE, FLUIDS.register(MiscResource.NETHERITE.getRegistrySuffix() + "_p204_extract", UnaryOperator.identity(), props -> props.tint(0xFF513600)));
        PROCESSED_CONCENTRATE_RESOURCES.put(MiscResource.NETHERITE, FLUIDS.register(MiscResource.NETHERITE.getRegistrySuffix() + "_concentrate", UnaryOperator.identity(), props -> props.tint(0xFF513600)));
        PROCESSED_LEACHATE_RESOURCES.put(MiscResource.REDSTONE, FLUIDS.register(MiscResource.REDSTONE.getRegistrySuffix() + "_leachate", UnaryOperator.identity(), props -> props.tint(0xFFC01A1A)));
        PROCESSED_EXTRACT_RESOURCES.put(MiscResource.REDSTONE, FLUIDS.register(MiscResource.REDSTONE.getRegistrySuffix() + "_p204_extract", UnaryOperator.identity(), props -> props.tint(0xFFC01A1A)));
        PROCESSED_CONCENTRATE_RESOURCES.put(MiscResource.REDSTONE, FLUIDS.register(MiscResource.REDSTONE.getRegistrySuffix() + "_concentrate", UnaryOperator.identity(), props -> props.tint(0xFFC01A1A)));
    }

    private MSFluids(){
    }
}
