package net.amaice.amy.client.event;

import net.amaice.amy.AmyMain;
import net.amaice.amy.client.renderer.AmyEntityRenderer;
import net.amaice.amy.client.renderer.MitoEntityRenderer;
import net.amaice.amy.core.init.EntityInit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AmyMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(EntityInit.AMY_ENTITY.get(), AmyEntityRenderer::new);
        event.registerEntityRenderer(EntityInit.MITO_ENTITY.get(), MitoEntityRenderer::new);
    }
}
