package net.amaice.amy.core.init;

import net.amaice.amy.AmyMain;
import net.amaice.amy.common.tile.AmySpawnerTile;
import net.amaice.amy.common.tile.AmySpawnerTileII;
import net.amaice.amy.common.tile.AmySpawnerTileIII;
import net.amaice.amy.common.tile.AmySpawnerTileIV;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TileEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AmyMain.MODID);

    public static final RegistryObject<BlockEntityType<AmySpawnerTile>> AMY_SPAWNER = TILE_ENTITY_TYPES.register("amy_spawner",
            () -> BlockEntityType.Builder.of(AmySpawnerTile::new, BlockInit.BLOCK_OF_AMY.get()).build(null));
    public static final RegistryObject<BlockEntityType<AmySpawnerTileII>> AMY_SPAWNER_II = TILE_ENTITY_TYPES.register("amy_spawner_ii",
            () -> BlockEntityType.Builder.of(AmySpawnerTileII::new, BlockInit.BLOCK_OF_AMY_II.get()).build(null));
    public static final RegistryObject<BlockEntityType<AmySpawnerTileIII>> AMY_SPAWNER_III = TILE_ENTITY_TYPES.register("amy_spawner_iii",
            () -> BlockEntityType.Builder.of(AmySpawnerTileIII::new, BlockInit.BLOCK_OF_AMY_III.get()).build(null));
    public static final RegistryObject<BlockEntityType<AmySpawnerTileIV>> AMY_SPAWNER_IV = TILE_ENTITY_TYPES.register("amy_spawner_iv",
            () -> BlockEntityType.Builder.of(AmySpawnerTileIV::new, BlockInit.BLOCK_OF_AMY_IV.get()).build(null));


}
