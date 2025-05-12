package com.cursee.monolib.core.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;

public class FabricModBrewingStandEvents {

    public static final Event<BrewedPotionEvent> BREWED_POTION = EventFactory.createArrayBacked(BrewedPotionEvent.class, events -> (brewingStand, level, blockPos, items) -> {
        for (BrewedPotionEvent event : events) {
            event.onBrewedPotion(brewingStand, level, blockPos, items);
        }
    });

    public static final Event<PlayerBrewedPotionEvent> PLAYER_BREWED_POTION = EventFactory.createArrayBacked(PlayerBrewedPotionEvent.class, events -> (slot, player, itemStack) -> {
       for (PlayerBrewedPotionEvent event : events) {
           event.onPlayerBrewedPotion(slot, player, itemStack);
       }
    });

    @FunctionalInterface
    public interface BrewedPotionEvent {
        void onBrewedPotion(BrewingStandBlockEntity brewingStand, Level level, BlockPos blockPos, NonNullList<ItemStack> items);
    }

    @FunctionalInterface
    public interface PlayerBrewedPotionEvent {
        void onPlayerBrewedPotion(Slot slot, Player player, ItemStack itemStack);
    }
}
