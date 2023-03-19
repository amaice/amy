package net.amaice.amy.client.renderer.model;

import net.amaice.amy.AmyMain;
import net.amaice.amy.common.entity.AmyEntity;
import net.amaice.amy.common.entity.MitoEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MitoEntityModel extends AnimatedGeoModel<MitoEntity> {
    @Override
    public ResourceLocation getModelResource(MitoEntity object) {
        return new ResourceLocation(AmyMain.MODID, "geo/mito_entity.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MitoEntity object) {
        return new ResourceLocation(AmyMain.MODID, "textures/entities/mito_entity.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MitoEntity animatable) {
        return new ResourceLocation(AmyMain.MODID, "animations/mito_entity.animation.json");
    }
}