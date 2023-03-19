package net.amaice.amy.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class MitoEntity extends Monster implements IAnimatable, RangedAttackMob {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private BlockPos lastPos;
    private int lastPosTick;

    public MitoEntity(EntityType<MitoEntity> entityType, Level level) {
        super(entityType, level);
        this.noCulling = true;
        this.lastPos = this.blockPosition();
        this.lastPosTick = this.tickCount;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RangedAttackGoal(this, 1.0D, 40, 20.0F));
        this.targetSelector.addGoal(1, new MeleeAttackGoal(this, 2.0F, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, 30.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.35F)
                .add(Attributes.MAX_HEALTH, 1100.0D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(ForgeMod.ATTACK_RANGE.get(), 2.0F)
                .add(Attributes.ATTACK_KNOCKBACK, 4F);
    }

    //      ANIMATION ANIMATION ANIMATION ANIMATION ANIMATION

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.mito.spin", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(
                        this, "controller", 0, this::predicate
                )
        );
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }


    @Override
    public SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.WITHER_DEATH; }

    // FOR BOSS STUFF
    // FOR BOSS STUFF FOR BOSS STUFF
    // FOR BOSS STUFF FOR BOSS STUFF FOR BOSS STUFF

    public void startSeenByPlayer(@NotNull ServerPlayer serverPlayer) {
        super.startSeenByPlayer(serverPlayer);
        this.bossEvent.addPlayer(serverPlayer);
    }

    public void stopSeenByPlayer(@NotNull ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
        this.bossEvent.removePlayer(serverPlayer);
    }

    private final ServerBossEvent bossEvent = (ServerBossEvent) (
            new ServerBossEvent(this.getDisplayName(),
                    BossEvent.BossBarColor.WHITE,
                    BossEvent.BossBarOverlay.PROGRESS)
    ).setDarkenScreen(true);

    protected void teleport() {
        if (!this.level.isClientSide() && this.isAlive()) {
            double d0 = this.getX() + (this.random.nextDouble() - 0.5D) * 32.0D;
            double d1 = this.getY() + (double) (this.random.nextInt(32) - 2);
            double d2 = this.getZ() + (this.random.nextDouble() - 0.5D) * 32.0D;
            this.teleport(d0, d1, d2);
        }
    }

    private void teleport(double teleport_x, double teleport_y, double teleport_z) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(teleport_x, teleport_y, teleport_z);

        // searches downwards until it finds bottom of world or "block that blocks motion "
        while(blockpos$mutableblockpos.getY() > this.level.getMinBuildHeight() && !this.level.getBlockState(blockpos$mutableblockpos).getMaterial().blocksMotion()) {
            blockpos$mutableblockpos.move(Direction.DOWN);
        }
        Vec3 vecPos = new Vec3(blockpos$mutableblockpos.getX(), blockpos$mutableblockpos.getY(), blockpos$mutableblockpos.getZ());
        BlockState blockstate = this.level.getBlockState(blockpos$mutableblockpos);
        boolean isWatery = blockstate.getFluidState().is(FluidTags.WATER);

        boolean isClear;
        isClear =
                //!blockstate.getMaterial().blocksMotion() &&
                !this.level.getBlockState(new BlockPos(vecPos.add(2, 1, 0))).getMaterial().blocksMotion()
                && !this.level.getBlockState(new BlockPos(vecPos.add(1, 1, 0))).getMaterial().blocksMotion()
                && !this.level.getBlockState(new BlockPos(vecPos.add(-1, 1, 0))).getMaterial().blocksMotion()
                && !this.level.getBlockState(new BlockPos(vecPos.add(-2, 1, 0))).getMaterial().blocksMotion()
                && !this.level.getBlockState(new BlockPos(vecPos.add(0, 1, 0))).getMaterial().blocksMotion()
                && !this.level.getBlockState(new BlockPos(vecPos.add(0, 2, 0))).getMaterial().blocksMotion()
                && !this.level.getBlockState(new BlockPos(vecPos.add(0, 1, 2))).getMaterial().blocksMotion()
                && !this.level.getBlockState(new BlockPos(vecPos.add(0, 1, 1))).getMaterial().blocksMotion()
                && !this.level.getBlockState(new BlockPos(vecPos.add(0, 1, -1))).getMaterial().blocksMotion()
                && !this.level.getBlockState(new BlockPos(vecPos.add(0, 1, -2))).getMaterial().blocksMotion()
        ;

        if (!isWatery && isClear) {
            if (!this.isSilent()) {
                this.level.playSound(null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0F, 1.0F);
                this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }
            this.moveTo(vecPos);
        }
    }


    private double getHeadX() { return this.getX(); }
    private double getHeadY() { return this.getY() + 3.0D; }
    private double getHeadZ() { return this.getZ(); }
    private void performRangedAttack(LivingEntity p_31459_) {
        this.performRangedAttack(p_31459_.getX(), p_31459_.getY() + (double)p_31459_.getEyeHeight() * 0.5D, p_31459_.getZ(), this.random.nextFloat() < 0.001F);
    }
    private void performRangedAttack(double p_31450_, double p_31451_, double p_31452_, boolean p_31453_) {
        if (!this.isSilent()) {
            this.level.levelEvent(null, 1024, this.blockPosition(), 0);
        }

        double d0 = this.getHeadX();
        double d1 = this.getHeadY();
        double d2 = this.getHeadZ();
        double d3 = p_31450_ - d0;
        double d4 = p_31451_ - d1;
        double d5 = p_31452_ - d2;
        WitherSkull witherskull = new WitherSkull(this.level, this, d3, d4, d5);
        witherskull.setOwner(this);
        if (p_31453_) {
            witherskull.setDangerous(true);
        }

        witherskull.setPosRaw(d0, d1, d2);
        this.level.addFreshEntity(witherskull);
    }

    public void performRangedAttack(@NotNull LivingEntity p_31468_, float p_31469_) {
        this.performRangedAttack(p_31468_);
    }

    int ticksSinceTeleport = 0;
    public void tick() {
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
        super.tick();

        this.ticksSinceTeleport++;
        if (this.ticksSinceTeleport > 4096) {
            this.ticksSinceTeleport = 100;
        }

        // Check if Mito has moved recently
        if (!level.isClientSide() && this.tickCount - this.lastPosTick > 10) {
            BlockPos currentPos = this.blockPosition();
            if (currentPos.equals(this.lastPos)
                    && Mth.nextInt(this.random, 0, 16) < 2
                    && this.ticksSinceTeleport > 60
            ) {
                teleport();
            }
            this.lastPos = currentPos;
            this.lastPosTick = this.tickCount;
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource p_21016_, float p_21017_) {
        if (this.isAlive()
                    && Mth.nextInt(this.random, 0, 8) == 0
        ){
            teleport();
        };
        return super.hurt(p_21016_, p_21017_);
    }

    protected boolean canRide(@NotNull Entity entity) {
        return false;
    }
    public boolean canChangeDimensions() {
        return false;
    }
    public boolean canBeAffected(MobEffectInstance instance) {
        return instance.getEffect() != MobEffects.WITHER && super.canBeAffected(instance);
    }
}