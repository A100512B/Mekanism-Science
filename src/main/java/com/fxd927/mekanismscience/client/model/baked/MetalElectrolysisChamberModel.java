package com.fxd927.mekanismscience.client.model.baked;

import mekanism.api.annotations.NothingNullByDefault;
import mekanism.client.model.baked.ExtensionBakedModel.TransformedBakedModel;
import mekanism.client.render.lib.QuadTransformation;
import mekanism.common.Mekanism;
import mekanism.common.base.HolidayManager;
import mekanism.common.config.MekanismConfig;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.Nullable;

@NothingNullByDefault
public class MetalElectrolysisChamberModel extends TransformedBakedModel<Void> {

    @Nullable
    private static TextureAtlasSprite AFD_SAD, AFD_TEXT, MAY_4TH;

    @SubscribeEvent
    public static void onStitch(TextureStitchEvent.Post event) {
        TextureAtlas atlas = event.getAtlas();
        AFD_SAD = atlas.getSprite(Mekanism.rl("block/models/digital_miner_screen_afd_sad"));
        AFD_TEXT = atlas.getSprite(Mekanism.rl("block/models/digital_miner_screen_afd_text"));
        MAY_4TH = atlas.getSprite(Mekanism.rl("block/models/digital_miner_screen_may4th"));
    }

    private final Lazy<QuadTransformation> NORMAL_TRANSFORM = Lazy.of(() -> QuadTransformation.translate(0, 1, 0));
    private final Lazy<QuadTransformation> APRIL_FOOLS_TRANSFORM = Lazy.of(() -> QuadTransformation.list(
            QuadTransformation.TextureFilteredTransformation.of(QuadTransformation.texture(AFD_SAD), s -> s.getPath().contains("screen_hello") || s.getPath().contains("screen_cmd")),
            QuadTransformation.TextureFilteredTransformation.of(QuadTransformation.texture(AFD_TEXT), s -> s.getPath().contains("screen_logo"))
    ));
    private final Lazy<QuadTransformation> MAY_4TH_TRANSFORM = Lazy.of(() -> QuadTransformation.TextureFilteredTransformation.of(QuadTransformation.texture(MAY_4TH),
            s -> s.getPath().contains("screen_hello")));

    public MetalElectrolysisChamberModel(BakedModel original) {
        super(original, QuadTransformation.translate(0, 1, 0));
    }

    @Nullable
    @Override
    protected QuadsKey<Void> createKey(QuadsKey<Void> key, ModelData data) {
        if (MekanismConfig.client.holidays.get()) {
            if (HolidayManager.MAY_4.isToday()) {
                return key.transform(MAY_4TH_TRANSFORM.get()).transform(NORMAL_TRANSFORM);
            } else if (HolidayManager.APRIL_FOOLS.isToday()) {
                return key.transform(APRIL_FOOLS_TRANSFORM).transform(NORMAL_TRANSFORM);
            }
        }
        return super.createKey(key, data);
    }

    @Override
    protected TransformedBakedModel<Void> wrapModel(BakedModel model) {
        return new MetalElectrolysisChamberModel(model);
    }
}
