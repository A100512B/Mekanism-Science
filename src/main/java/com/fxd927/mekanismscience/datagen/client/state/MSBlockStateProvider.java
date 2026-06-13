package com.fxd927.mekanismscience.datagen.client.state;

import com.fxd927.mekanismscience.common.MekanismScience;
import com.fxd927.mekanismscience.common.block.attribute.AttributeStateAntiExtractingPortMode;
import com.fxd927.mekanismscience.common.block.attribute.AttributeStateExtractingPortMode;
import com.fxd927.mekanismscience.common.mixin.AttributeStateActiveAccessor;
import com.fxd927.mekanismscience.common.registries.MSBlocks;
import com.fxd927.mekanismscience.common.registries.MSFluids;
import mekanism.api.providers.IBlockProvider;
import mekanism.common.block.attribute.Attribute;
import mekanism.common.util.RegistryUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MSBlockStateProvider extends BlockStateProvider {

    public MSBlockStateProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, MekanismScience.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        MSFluids.FLUIDS.getAllFluids().forEach(fluidRO -> simpleBlock(fluidRO.getBlock(),
                models().getBuilder(RegistryUtils.getPath(fluidRO.getBlock())).texture("particle", fluidRO.getFluidType().stillTexture)));
        // Single block tiles
        existingActive(MSBlocks.ADSORPTION_SEPARATOR);
        existingActive(MSBlocks.AIR_COMPRESSOR);
        existingActive(MSBlocks.IRRADIATOR);
        existing(MSBlocks.PRESSURIZED_POLYMERIZING_CHAMBER);
        existingActive(MSBlocks.SEAWATER_PUMP);
        // Extracting Plant
        cubeAll(MSBlocks.EXTRACTING_PLANT_CASING);
        port(MSBlocks.EXTRACTING_PLANT_PORT, AttributeStateExtractingPortMode.modeProperty);
        existing(MSBlocks.EXTRACTING_PILLAR);
        // Anti-Extracting Plant
        cubeAll(MSBlocks.ANTI_EXTRACTING_PLANT_CASING);
        port(MSBlocks.ANTI_EXTRACTING_PLANT_PORT, AttributeStateAntiExtractingPortMode.modeProperty);
        existing(MSBlocks.ANTI_EXTRACTING_PILLAR);
        cubeAll(MSBlocks.METAL_ELECTROLYSIS_CHAMBER_CASING);
        port(MSBlocks.METAL_ELECTROLYSIS_CHAMBER_PORT, AttributeStateActiveAccessor.getActiveProperty());
        cubeAll(MSBlocks.METAL_ELECTROLYSIS_CHAMBER_LASER_ACCEPTOR);
    }

    private void existing(IBlockProvider block) {
        simpleBlock(block.getBlock(), models().withExistingParent(block.getName(), "block/" + block.getName()));
    }

    private void cubeAll(IBlockProvider block) {
        String name = block.getName();
        ModelFile model = models().cubeAll(name, modLoc("block/" + name));
        simpleBlock(block.getBlock(), model);
        simpleBlockItem(block.getBlock(), model);
    }

    private void existingActive(IBlockProvider block) {
        String name = block.getName();
        ModelFile off = models().withExistingParent(name, modLoc("block/" + name))
                .texture("particle", "block/" + name);
        ModelFile on = models().withExistingParent(name, modLoc("block/" + name + "_active"))
                .texture("particle", "block/" + name);
        getVariantBuilder(block.getBlock()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(Attribute.isActive(state) ? on : off)
                .rotationY((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot())
                .build());
        simpleBlockItem(block.getBlock(), off);
    }

    private void cubeMachine(IBlockProvider block) {
        String name = block.getName();
        ModelFile off = models().cube(name,
                        modLoc("block/" + name + "/bottom"),
                        modLoc("block/" + name + "/top"),
                        modLoc("block/" + name + "/front"),
                        modLoc("block/" + name + "/back"),
                        modLoc("block/" + name + "/left"),
                        modLoc("block/" + name + "/right"))
                .texture("particle", "block/" + name + "/bottom");
        ModelFile on = models().cube(name,
                        modLoc("block/" + name + "/bottom"),
                        modLoc("block/" + name + "/top"),
                        modLoc("block/" + name + "/front_active"),
                        modLoc("block/" + name + "/back"),
                        modLoc("block/" + name + "/left"),
                        modLoc("block/" + name + "/right"))
                .texture("particle", "block/" + name + "/bottom");
        getVariantBuilder(block.getBlock()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(Attribute.isActive(state) ? on : off)
                .rotationY((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot())
                .build());
        simpleBlockItem(block.getBlock(), off);
    }

    private <E extends Enum<E> & StringRepresentable> void port(IBlockProvider block, EnumProperty<E> modeProperty) {
        String name = block.getName();
        getVariantBuilder(block.getBlock()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(models().withExistingParent(name, modLoc("block/" + name + "/" + state.getValue(modeProperty).getSerializedName())))
                .build());
        simpleBlockItem(block.getBlock(), models().withExistingParent(name, modLoc("block/" + name + "/" + block.getBlock().defaultBlockState().getValue(modeProperty).getSerializedName())));
    }

    private void port(IBlockProvider block, BooleanProperty activeProperty) {
        String name = block.getName();
        getVariantBuilder(block.getBlock()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(models().cubeAll(name, modLoc("block/" + name + "/" + (state.getValue(activeProperty) ? "output" : "input"))))
                .build());
        simpleBlockItem(block.getBlock(), models().cubeAll(name, modLoc("block/" + name + "/input")));
    }
}
