package com.manus.monkeystaff.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;

import com.manus.monkeystaff.entity.MonkeyEntity;

public class MonkeyStaffItem extends Item {
    public MonkeyStaffItem(Item.Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide && player.isShiftKeyDown()) {
            // Invocar um macaco
            MonkeyEntity monkey = new MonkeyEntity(level);
            
            // Posicionar o macaco na frente do jogador
            double distance = 2.0;
            double x = player.getX() + player.getLookAngle().x * distance;
            double y = player.getY();
            double z = player.getZ() + player.getLookAngle().z * distance;
            
            monkey.setPos(x, y, z);
            monkey.setOwnerUUID(player.getUUID());
            
            level.addFreshEntity(monkey);
            
            // Consumir durabilidade
            itemStack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
            
            return InteractionResultHolder.success(itemStack);
        }

        return InteractionResultHolder.pass(itemStack);
    }
}
