package net.amaice.amy.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.amaice.amy.AmyMain;
import net.amaice.amy.client.renderer.model.AmyEntityModel;
import net.amaice.amy.common.entity.AmyEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class AmyEntityRenderer extends GeoEntityRenderer<AmyEntity> {
    public AmyEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new AmyEntityModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public ResourceLocation getTextureLocation(AmyEntity entity) {
        return new ResourceLocation(AmyMain.MODID, "textures/entities/amy_entity.png");
    }

    @Override
    public RenderType getRenderType(AmyEntity animatable, float partialTick, PoseStack poseStack,
                @Nullable MultiBufferSource bufferSource,
                @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        poseStack.scale(1f, 1f, 1f);
        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }
}
