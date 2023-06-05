package net.amaice.amy.common.block;

import net.amaice.amy.common.tile.AmySpawnerTile;
import net.amaice.amy.core.init.TileEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
public class BlockOfAmy extends SpawnerBlock {
    public BlockOfAmy(Properties props) {
        super(props);
    }

    // why did i even keep it as a SpawnerBlock, it's just a custom block that works like a SpawnerBlock lmao
    @Override
    public int getExpDrop(@NotNull BlockState state, @NotNull LevelReader world, @NotNull Random randomSource, @NotNull BlockPos pos, int fortune, int silkTouch) {
        return 0;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return TileEntityInit.AMY_SPAWNER.get().create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level world, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return type == TileEntityInit.AMY_SPAWNER.get() ? AmySpawnerTile::tick : null;
    }
}