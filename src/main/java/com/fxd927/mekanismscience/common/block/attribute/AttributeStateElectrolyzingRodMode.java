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

public class AttributeStateElectrolyzingRodMode implements AttributeState {

    public static final EnumProperty<MetalElectrolyzingRodMode> modeProperty = EnumProperty.create("mode", MetalElectrolyzingRodMode.class);

    @Override
    public BlockState copyStateData(BlockState oldState, BlockState newState) {
        if (Attribute.has(newState, AttributeStateFissionPortMode.class)) {
            newState = newState.setValue(modeProperty, oldState.getValue(modeProperty));
        }
        return newState;
    }

    @Override
    public BlockState getDefaultState(@NotNull BlockState state) {
        return state.setValue(modeProperty, MetalElectrolyzingRodMode.IDLE);
    }

    @Override
    public void fillBlockStateContainer(Block block, List<Property<?>> properties) {
        properties.add(modeProperty);
    }
    
    @NothingNullByDefault
    public enum MetalElectrolyzingRodMode implements StringRepresentable, IHasTextComponent, IIncrementalEnum<MetalElectrolyzingRodMode> {
        IDLE("idle", MSLang.METAL_ELECTROLYZING_ROD_MODE_IDLE, EnumColor.GRAY),
        ACTIVE("active", MSLang.METAL_ELECTROLYZING_ROD_MODE_ACTIVE, EnumColor.INDIGO),
        ACTIVE_LASER("active_laser", MSLang.METAL_ELECTROLYZING_ROD_MODE_ACTIVE_LASER, EnumColor.RED);
        
        private static final MetalElectrolyzingRodMode[] MODES = values();

        private final String name;
        private final ILangEntry langEntry;
        private final EnumColor color;

        MetalElectrolyzingRodMode(String name, ILangEntry langEntry, EnumColor color) {
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

        public static MetalElectrolyzingRodMode byIndexStatic(int index) {
            return MathUtils.getByIndexMod(MODES, index);
        }

        @Override
        public MetalElectrolyzingRodMode byIndex(int index) {
            return byIndexStatic(index);
        }
    }
}
