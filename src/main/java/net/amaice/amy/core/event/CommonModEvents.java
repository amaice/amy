package net.amaice.amy.core.event;

import net.amaice.amy.AmyMain;
import net.amaice.amy.common.entity.AmyEntity;
import net.amaice.amy.common.entity.MitoEntity;
import net.amaice.amy.core.init.EntityInit;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = AmyMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
            SpawnPlacements.register(EntityInit.AMY_ENTITY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, AmyEntity::canSpawn);
        });
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(EntityInit.AMY_ENTITY.get(), AmyEntity.createAttributes().build());
        event.put(EntityInit.MITO_ENTITY.get(), MitoEntity.createAttributes().build());
    }



}
