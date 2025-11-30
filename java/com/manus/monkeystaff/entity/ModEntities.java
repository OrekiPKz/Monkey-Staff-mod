package com.manus.monkeystaff.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

import com.manus.monkeystaff.MonkeyStaffMod;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MonkeyStaffMod.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<MonkeyEntity>> MONKEY = ENTITIES.register("monkey",
            () -> EntityType.Builder.of(MonkeyEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 1.8f)
                    .build("monkey"));
}
