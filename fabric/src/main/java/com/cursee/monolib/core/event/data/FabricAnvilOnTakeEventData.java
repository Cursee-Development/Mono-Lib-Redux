package com.cursee.monolib.core.event.data;

import java.util.Optional;
import java.util.UUID;
import java.util.WeakHashMap;
import net.minecraft.world.entity.player.Player;

public class FabricAnvilOnTakeEventData {

    private static final ThreadLocal<Player> threadLocalPlayer = new ThreadLocal<>();
    private static final WeakHashMap<UUID, Optional<Float>> breakChances = new WeakHashMap<>();

    public static void set(Player player, Float value) {
        threadLocalPlayer.set(player);
        breakChances.put(player.getUUID(), Optional.ofNullable(value));
    }

    public static Optional<Float> get(Player player) {
        return breakChances.getOrDefault(player.getUUID(), Optional.of(0.12F));
    }

    public static Player getThreadLocalPlayer() {
        return threadLocalPlayer.get();
    }

    public static void clear(Player player) {
        threadLocalPlayer.remove();
        Optional<Float> stored = breakChances.get(player.getUUID());
        breakChances.remove(player.getUUID(), stored); // remove full entry
    }
}
