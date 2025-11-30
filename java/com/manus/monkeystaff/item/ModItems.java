package com.manus.monkeystaff.item;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.DeferredItem;

import com.manus.monkeystaff.MonkeyStaffMod;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MonkeyStaffMod.MOD_ID);

    public static final DeferredItem<Item> MONKEY_STAFF = ITEMS.register("monkey_staff",
            () -> new MonkeyStaffItem(new Item.Properties()));
}
