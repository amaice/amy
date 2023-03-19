package net.amaice.amy.client.renderer.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.amaice.amy.AmyMain;
import net.amaice.amy.common.entity.AmyEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AmyEntityModel extends AnimatedGeoModel<AmyEntity> {
    @Override
    public ResourceLocation getModelResource(AmyEntity object) {
        return new ResourceLocation(AmyMain.MODID, "geo/amy_entity.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AmyEntity object) {
        return new ResourceLocation(AmyMain.MODID, "textures/entities/amy_entity.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AmyEntity animatable) {
        return new ResourceLocation(AmyMain.MODID, "animations/amy_entity.animation.json");
    }
}