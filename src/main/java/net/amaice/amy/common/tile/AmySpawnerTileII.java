package net.amaice.amy.common.tile;

import net.amaice.amy.common.entity.AmyEntity;
import net.amaice.amy.core.init.EntityInit;
import net.amaice.amy.core.init.TileEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class AmySpawnerTileII extends BlockEntity {
    public AmySpawnerTileII(BlockPos pos, BlockState state) {
        super(TileEntityInit.AMY_SPAWNER_II.get(), pos, state);
    }
    int timer = 0;
    int seconds = 10;

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T be) {
        AmySpawnerTileII tile = (AmySpawnerTileII) be;

        if (!level.isClientSide()){
            tile.timer++;
            // 20 ticks is a second (do this every tile.seconds)
            if (tile.timer > 20 * tile.seconds){
                tile.timer = 0;

                double randomValue = Math.random();

                // If the random number is less than 0.025, spawn an amy
                // 0.025 every 10 seconds is ~9 amys an hour
                // 0.0125 every 10 seconds is ~4.5 amys an hour
                if (randomValue < 0.025) {
                    tile.spawnAmy();
                }
            }
        }
    }

    public void spawnAmy() {
        BlockPos spawnPoint = this.worldPosition.above();

        int maxNearbyEntities = 10;
        double checkRadius = 40.0D;

        List<AmyEntity> amys = this.level.getEntitiesOfClass(AmyEntity.class,
                new AABB(spawnPoint).inflate(checkRadius),
                entity -> true
        );

        int numNearbyEntities = amys.size();
        LevelReader world = this.getLevel();

        if (numNearbyEntities < maxNearbyEntities && world.isEmptyBlock(spawnPoint)){
            AmyEntity amyEntity = new AmyEntity(EntityInit.AMY_ENTITY.get(), this.level);
            amyEntity.setPos(spawnPoint.getX() + 0.5, spawnPoint.getY() + 0.5, spawnPoint.getZ() + 0.5);
            this.level.addFreshEntity(amyEntity);
        }
    }
}