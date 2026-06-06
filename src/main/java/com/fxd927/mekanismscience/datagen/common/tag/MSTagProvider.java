package com.fxd927.mekanismscience.datagen.common.tag;

import com.fxd927.mekanismscience.common.MekanismScience;
import com.fxd927.mekanismscience.common.registries.MSBlocks;
import com.fxd927.mekanismscience.common.registries.MSFluids;
import com.fxd927.mekanismscience.common.registries.MSItems;
import com.fxd927.mekanismscience.common.registries.MSTileEntityTypes;
import com.fxd927.mekanismscience.common.tags.MSTags;
import mekanism.common.tags.MekanismTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MSTagProvider extends BaseTagProvider {

    public MSTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper helper) {
        super(output, lookupProvider, MekanismScience.MODID, helper);
    }

    @Override
    protected void registerTags(HolderLookup.Provider registries) {
        addBoxBlacklist();
        addFluids();
        addItems();
    }

    private void addBoxBlacklist() {
        addToTag(MekanismTags.Blocks.RELOCATION_NOT_SUPPORTED, MSBlocks.ACID_LEACHER);
        addToTag(MekanismTags.TileEntityTypes.IMMOVABLE, MSTileEntityTypes.ACID_LEACHER);
        addToTag(MekanismTags.TileEntityTypes.RELOCATION_NOT_SUPPORTED, MSTileEntityTypes.ACID_LEACHER);
    }

    private void addFluids() {
        addToTag(MSTags.Fluids.AMMONIA, MSFluids.AMMONIA);
        addToTag(MSTags.Fluids.BERYLLIUM, MSFluids.BERYLLIUM);
        addToTag(MSTags.Fluids.BROMINE, MSFluids.BROMINE);
        addToTag(MSTags.Fluids.CHLOROMETHANE, MSFluids.CHLOROMETHANE);
        addToTag(MSTags.Fluids.CONCENTRATED_SEAWATER, MSFluids.CONCENTRATED_SEAWATER);
        addToTag(MSTags.Fluids.ETHANOL, MSFluids.ETHANOL);
        addToTag(MSTags.Fluids.HELIUM, MSFluids.HELIUM);
        addToTag(MSTags.Fluids.HYDRAZINE, MSFluids.HYDRAZINE);
        addToTag(MSTags.Fluids.IODINE, MSFluids.IODINE);
        addToTag(MSTags.Fluids.LACTOSE, MSFluids.LACTOSE);
        addToTag(MSTags.Fluids.METHANOL, MSFluids.METHANOL);
        addToTag(MSTags.Fluids.MILK, MSFluids.MILK);
        addToTag(MSTags.Fluids.NITRIC_ACID, MSFluids.NITRIC_ACID);
        addToTag(MSTags.Fluids.NITRIC_OXIDE, MSFluids.NITRIC_OXIDE);
        addToTag(MSTags.Fluids.NITROGEN, MSFluids.NITROGEN);
        addToTag(MSTags.Fluids.NITROGEN_DIOXIDE, MSFluids.NITROGEN_DIOXIDE);
        addToTag(MSTags.Fluids.PROPYLENE, MSFluids.PROPYLENE);
        addToTag(MSTags.Fluids.SEAWATER, MSFluids.SEAWATER);
        addToTag(MSTags.Fluids.SUPERHEATED_HELIUM, MSFluids.SUPERHEATED_HELIUM);
    }

    private void addItems() {
        addToTag(Tags.Items.INGOTS, MSItems.REFINED_CALIFORNIUM_INGOT);
        addToTag(Tags.Items.DUSTS, MSItems.BONE_ASHES, MSItems.BONE_ASHES_WITH_CARBON,
                MSItems.DUST_CALCIUM_CHLORIDE, MSItems.DUST_CALCIUM_OXIDE);
    }
}
