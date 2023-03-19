package net.amaice.amy.core.init;

import net.amaice.amy.AmyMain;
import net.amaice.amy.common.entity.AmyEntity;
import net.amaice.amy.common.entity.MitoEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit {
    private EntityInit() {}
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, AmyMain.MODID);
    public static final RegistryObject<EntityType<AmyEntity>> AMY_ENTITY =
            ENTITIES.register("amy_entity", () -> EntityType.Builder.of(
                    AmyEntity::new, MobCategory.CREATURE).sized(0.6F, 1.8F)
                        .build(new ResourceLocation(AmyMain.MODID, "amy_entity").toString())
            );

    public static final RegistryObject<EntityType<MitoEntity>> MITO_ENTITY =
            ENTITIES.register("mito_entity", () -> EntityType.Builder.of(
                            MitoEntity::new, MobCategory.MONSTER).sized(4, 4F)
                    .build(new ResourceLocation(AmyMain.MODID, "mito_entity").toString())
            );
}
