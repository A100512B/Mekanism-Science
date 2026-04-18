package com.fxd927.mekanismscience.common.block.attribute;

import com.fxd927.mekanismscience.common.MSLang;
import mekanism.api.IIncrementalEnum;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.api.math.MathUtils;
import mekanism.api.text.EnumColor;
import mekanism.api.text.IHasTextComponent;
import mekanism.api.text.ILangEntry;
import mekanism.common.block.attribute.Attribute;
import mekanism.common.block.attribute.AttributeState;
import mekanism.generators.common.block.attribute.AttributeStateFissionPortMode;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AttributeStateAntiExtractingPortMode implements AttributeState {

    public static final EnumProperty<AntiExtractingPortMode> modeProperty = EnumProperty.create("mode", AntiExtractingPortMode.class);

    @Override
    public BlockState copyStateData(BlockState oldState, BlockState newState) {
        if (Attribute.has(newState, AttributeStateFissionPortMode.class)) {
            newState = newState.setValue(modeProperty, oldState.getValue(modeProperty));
        }
        return newState;
    }

    @Override
    public BlockState getDefaultState(@NotNull BlockState state) {
        return state.setValue(modeProperty, AntiExtractingPortMode.INPUT_ANTI_EXTRACTANT);
    }

    @Override
    public void fillBlockStateContainer(Block block, List<Property<?>> properties) {
        properties.add(modeProperty);
    }

    @NothingNullByDefault
    public enum AntiExtractingPortMode implements StringRepresentable, IHasTextComponent, IIncrementalEnum<AntiExtractingPortMode> {
        INPUT_ANTI_EXTRACTANT("input_extractant", MSLang.ANTI_EXTRACTING_PLANT_PORT_MODE_INPUT_ANTI_EXTRACTANT, EnumColor.BRIGHT_GREEN),
        INPUT_EXTRACT("input_extract", MSLang.ANTI_EXTRACTING_PLANT_PORT_MODE_INPUT_EXTRACT, EnumColor.YELLOW),
        OUTPUT_EXTRACTANT("output_extractant", MSLang.ANTI_EXTRACTING_PLANT_PORT_MODE_OUTPUT_EXTRACTANT, EnumColor.PINK),
        OUTPUT_CONCENTRATE("output_concentrate", MSLang.ANTI_EXTRACTING_PLANT_PORT_MODE_OUTPUT_CONCENTRATE, EnumColor.AQUA);

        private static final AntiExtractingPortMode[] MODES = values();

        private final String name;
        private final ILangEntry langEntry;
        private final EnumColor color;

        AntiExtractingPortMode(String name, ILangEntry langEntry, EnumColor color) {
            this.name = name;
            this.langEntry = langEntry;
            this.color = color;
        }

        @Override
        public String getSerializedName() {
            return name;
        }

        @Override
        public Component getTextComponent() {
            return langEntry.translateColored(color);
        }

        public static AntiExtractingPortMode byIndexStatic(int index) {
            return MathUtils.getByIndexMod(MODES, index);
        }

        @Override
        public AntiExtractingPortMode byIndex(int index) {
            return byIndexStatic(index);
        }
    }
}
