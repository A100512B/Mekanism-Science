package com.fxd927.mekanismscience.common.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class MSTags {

    public static void init() {
        Fluids.init();
    }

    private MSTags() {
    }

    public static class Fluids {

        private static void init() {
        }

        private Fluids() {
        }

        public static final TagKey<Fluid> AMMONIA = forgeTag("ammonia");
        public static final TagKey<Fluid> BERYLLIUM = forgeTag("beryllium");
        public static final TagKey<Fluid> BROMINE = forgeTag("bromine");
        public static final TagKey<Fluid> CHLOROMETHANE = forgeTag("chloromethane");
        public static final TagKey<Fluid> CONCENTRATED_SEAWATER = forgeTag("concentrated_seawater");
        public static final TagKey<Fluid> ETHANOL = forgeTag("ethanol");
        public static final TagKey<Fluid> HELIUM = forgeTag("helium");
        public static final TagKey<Fluid> HYDRAZINE = forgeTag("hydrazine");
        public static final TagKey<Fluid> NITRIC_ACID = forgeTag("nitric_acid");
        public static final TagKey<Fluid> NITRIC_OXIDE = forgeTag("nitric_oxide");
        public static final TagKey<Fluid> NITROGEN = forgeTag("nitrogen");
        public static final TagKey<Fluid> NITROGEN_DIOXIDE = forgeTag("nitrogen_dioxide");
        public static final TagKey<Fluid> SUPERHEATED_HELIUM = forgeTag("superheated_helium");
        public static final TagKey<Fluid> IODINE = forgeTag("iodine");
        public static final TagKey<Fluid> LACTOSE = forgeTag("lactose");
        public static final TagKey<Fluid> METHANOL = forgeTag("methanol");
        public static final TagKey<Fluid> MILK = forgeTag("milk");
        public static final TagKey<Fluid> PROPYLENE = forgeTag("propylene");
        public static final TagKey<Fluid> SEAWATER = forgeTag("seawater");

        private static TagKey<Fluid> forgeTag(String name) {
            return FluidTags.create(ResourceLocation.fromNamespaceAndPath("forge", name));
        }
    }
}
