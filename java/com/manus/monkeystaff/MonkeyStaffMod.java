package com.manus.monkeystaff;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import com.manus.monkeystaff.item.ModItems;
import com.manus.monkeystaff.entity.ModEntities;

@Mod("monkeystaff")
public class MonkeyStaffMod {
    public static final String MOD_ID = "monkeystaff";

    public MonkeyStaffMod(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        
        // Registrar items e entities
        ModItems.ITEMS.register(modEventBus);
        ModEntities.ENTITIES.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Setup comum do mod
    }
}
