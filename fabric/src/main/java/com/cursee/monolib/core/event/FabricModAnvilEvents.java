package com.cursee.monolib.core.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import oshi.util.tuples.Triplet;

import java.util.Optional;

/**
 * These events occur on the server and client simultaneously,
 * which should be handled via {@link Level#isClientSide()} checks to the player's level if needed.
 */
public class FabricModAnvilEvents {

    public static final Event<AnvilCreateResultEvent> CREATE_RESULT = EventFactory.createArrayBacked(AnvilCreateResultEvent.class, events -> (anvilMenu, leftStack, rightStack, output, itemName, baseCost, player) -> {

        for (AnvilCreateResultEvent event : events) {
            var result = event.createResult(anvilMenu, leftStack, rightStack, output, itemName, baseCost, player);
            if (result != null) return result;
        }

        return null;
    });

    public static final Event<AnvilOnTakeEvent> ON_TAKE = EventFactory.createArrayBacked(AnvilOnTakeEvent.class, events -> (anvilMenu, player, output, left, right) -> {

        for (AnvilOnTakeEvent event : events) {
            var result = event.onTake(anvilMenu, player, output, left, right);
            if (result.isPresent()) return result; // return original vanilla value by default
        }

        return Optional.of(0.12f);
    });

    public static final Event<AnvilOnLandEvent> ON_LAND = EventFactory.createArrayBacked(AnvilOnLandEvent.class, events -> (anvilBlock, level, pos, state, replaceableState, fallingBlock) -> {
        for (AnvilOnLandEvent event : events) {
            event.onLand(anvilBlock, level, pos, state, replaceableState, fallingBlock);
        }
    });

    public static final Event<AnvilOnBrokenAfterFallEvent> ON_BROKEN_AFTER_FALL = EventFactory.createArrayBacked(AnvilOnBrokenAfterFallEvent.class, events -> (anvilBlock, level, pos, fallingBlock) -> {
        for (AnvilOnBrokenAfterFallEvent event : events) {
            event.onBrokenAfterFall(anvilBlock, level, pos, fallingBlock);
        }
    });

    @FunctionalInterface
    public interface AnvilCreateResultEvent {
        Triplet<Integer, Integer, ItemStack> createResult(AnvilMenu anvilMenu, ItemStack leftStack, ItemStack rightStack, ItemStack output, String itemName, int baseCost, Player player);
    }

    @FunctionalInterface
    public interface AnvilOnTakeEvent {
        Optional<Float> onTake(AnvilMenu anvilMenu, Player player, ItemStack output, ItemStack left, ItemStack right);
    }

    @FunctionalInterface
    public interface AnvilOnLandEvent {
        void onLand(AnvilBlock anvilBlock, Level level, BlockPos pos, BlockState state, BlockState replaceableState, FallingBlockEntity fallingBlock);
    }

    @FunctionalInterface
    public interface AnvilOnBrokenAfterFallEvent {
        void onBrokenAfterFall(AnvilBlock anvilBlock, Level level, BlockPos pos, FallingBlockEntity fallingBlock);
    }
}
