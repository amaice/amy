package net.amaice.amy.common.block;

import net.amaice.amy.common.entity.MitoEntity;
import net.amaice.amy.core.init.BlockInit;
import net.amaice.amy.core.init.EntityInit;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockPredicate;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class MitoPartBlock extends Block {
    public MitoPartBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    private static BlockPattern mitoPatternFull;

    public static BlockPattern getOrCreateMitoFull() {
        if (mitoPatternFull == null) {
            //H for head, E for ears, B for back
            mitoPatternFull = BlockPatternBuilder.start().aisle("E ")
                                                         .aisle("HB")
                    .where('H', BlockInWorld.hasState(BlockPredicate.forBlock(BlockInit.HEAD_OF_MITO.get())))
                    .where('E', BlockInWorld.hasState(BlockPredicate.forBlock(BlockInit.EARS_OF_MITO.get())))
                    .where('B', BlockInWorld.hasState(BlockPredicate.forBlock(BlockInit.BACK_OF_MITO.get())))
                    .build();
        }
        return mitoPatternFull;
    }

    public void setPlacedBy(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState, @Nullable LivingEntity entity, ItemStack itemStack) {
        super.setPlacedBy(level, blockPos, blockState, entity, itemStack);
        checkSpawn(level, blockPos);
    }

    public static void checkSpawn(Level level, BlockPos blockPos) {
        if (!level.isClientSide) {
            if (blockPos.getY() >= level.getMinBuildHeight() && level.getDifficulty() != Difficulty.PEACEFUL) {
                BlockPattern blockpattern = getOrCreateMitoFull();
                BlockPattern.BlockPatternMatch blockpattern$blockpatternmatch = blockpattern.find(level, blockPos);
                if (blockpattern$blockpatternmatch != null) {
                    // removes blocks of structure from the level and plays a breaking sound effect
                    for(int i = 0; i < blockpattern.getWidth(); ++i) {
                        for(int j = 0; j < blockpattern.getHeight(); ++j) {
                            for(int l = 0; l < blockpattern.getDepth(); ++l) {
                                BlockInWorld blockinworld = blockpattern$blockpatternmatch.getBlock(i, j, l);
                                level.setBlock(blockinworld.getPos(), Blocks.AIR.defaultBlockState(), 2);
                                level.levelEvent(2001, blockinworld.getPos(), Block.getId(blockinworld.getState()));
                            }
                        }
                    }
                    MitoEntity mitoEntity = new MitoEntity(EntityInit.MITO_ENTITY.get(), level);
                    mitoEntity.setPos(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);

                    for(ServerPlayer serverplayer : level.getEntitiesOfClass(ServerPlayer.class, mitoEntity.getBoundingBox().inflate(50.0D))) {
                        CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayer, mitoEntity);
                    }
                    level.addFreshEntity(mitoEntity);
                }
            }
        }
    }
}