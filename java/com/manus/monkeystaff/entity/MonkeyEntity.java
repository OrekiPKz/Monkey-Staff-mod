package com.manus.monkeystaff.entity;

import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.MobSpawnType;

import java.util.UUID;

public class MonkeyEntity extends Animal {
    private static final EntityDataAccessor<Boolean> IS_JUMPING = SynchedEntityData.defineId(MonkeyEntity.class, EntityDataSerializers.BOOLEAN);
    private UUID ownerUUID;
    private int jumpCooldown = 0;

    public MonkeyEntity(Level level) {
        super(ModEntities.MONKEY.get(), level);
        this.setHealth(this.getMaxHealth());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, (itemStack) -> itemStack.getItem().toString().contains("banana"), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    protected void defineSyncedData(SynchedEntityData.Builder builder) {
        super.defineSyncedData(builder);
        builder.define(IS_JUMPING, false);
    }

    @Override
    public void tick() {
        super.tick();
        
        if (this.jumpCooldown > 0) {
            this.jumpCooldown--;
        }

        // Fazer o macaco pular aleatoriamente
        if (this.level().isClientSide == false && this.jumpCooldown == 0 && this.random.nextInt(100) < 5) {
            this.setJumping(true);
            this.jumpCooldown = 20;
        }

        // Seguir o dono se houver
        if (this.ownerUUID != null && this.level() instanceof Level) {
            Player owner = this.level().getPlayerByUUID(this.ownerUUID);
            if (owner != null && this.distanceTo(owner) > 10) {
                this.getNavigation().moveTo(owner, 1.2D);
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (this.ownerUUID != null) {
            tag.putUUID("OwnerUUID", this.ownerUUID);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("OwnerUUID")) {
            this.ownerUUID = tag.getUUID("OwnerUUID");
        }
    }

    public void setOwnerUUID(UUID uuid) {
        this.ownerUUID = uuid;
    }

    public UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    @Override
    public double getMyRidingOffset() {
        return 0.0D;
    }

    @Override
    public Animal getBreedOffspring(net.minecraft.world.entity.AgeableMob otherParent) {
        return new MonkeyEntity(this.level());
    }

    @Override
    public float getWalkTargetValue(net.minecraft.core.BlockPos pos, net.minecraft.world.level.LevelReader level) {
        return 0.0F;
    }

    @Override
    public int getMaxHeadXRot() {
        return 1;
    }

    @Override
    public void setJumping(boolean jumping) {
        super.setJumping(jumping);
        this.entityData.set(IS_JUMPING, jumping);
    }

    @Override
    public boolean isJumping() {
        return this.entityData.get(IS_JUMPING);
    }
}
