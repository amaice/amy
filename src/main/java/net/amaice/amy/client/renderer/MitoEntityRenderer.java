package net.amaice.amy.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.amaice.amy.AmyMain;
import net.amaice.amy.client.renderer.model.MitoEntityModel;
import net.amaice.amy.common.entity.MitoEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MitoEntityRenderer extends GeoEntityRenderer<MitoEntity> {
    public MitoEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new MitoEntityModel());
        this.shadowRadius = 1f;
    }

    @Override
    public ResourceLocation getTextureLocation(MitoEntity entity) {
        return new ResourceLocation(AmyMain.MODID, "textures/entities/mito_entity.png");
    }

    @Override
    public RenderType getRenderType(MitoEntity animatable, float partialTick, PoseStack poseStack,
                @Nullable MultiBufferSource bufferSource,
                @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        poseStack.scale(6f, 6f, 6f);
        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }
}
