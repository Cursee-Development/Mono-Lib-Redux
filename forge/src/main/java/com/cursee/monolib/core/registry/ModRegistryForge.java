package com.cursee.monolib.core.registry;

import com.cursee.monolib.MonoLibForge;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.RegisterEvent;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ModRegistryForge {

    public static void register() {
        bind(Registries.BLOCK, ModBlocks::register);
        bind(Registries.ITEM, ModItems::register);
    }

    private static <T> void bind(ResourceKey<Registry<T>> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {
//        MonoLibForge.EVENT_BUS.addListener((RegisterEvent event) -> {
//            if (registry.equals(event.getRegistryKey())) {
//                source.accept((t, rl) -> event.register(registry, rl, () -> t));
//            }
//        });

        RegisterEvent.getBus(MonoLibForge.MOD_BUS_GROUP).addListener(event -> {
            if (registry.equals(event.getRegistryKey())) {
                source.accept((t, rl) -> event.register(registry, rl, () -> t));
            }
        });
    }
}
