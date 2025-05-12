package com.cursee.monolib;

import com.cursee.monolib.core.command.MonoLibCommands;
import com.cursee.monolib.core.registry.ModRegistryNeoForge;
import com.cursee.monolib.core.sailing.Sailing;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

import java.util.function.Consumer;

@Mod(Constants.MOD_ID)
public class MonoLibNeoForge {

    public static IEventBus EVENT_BUS;

    public MonoLibNeoForge(final FMLModContainer container) {
        MonoLib.init();
        EVENT_BUS = container.getEventBus();

        ModRegistryNeoForge.register(EVENT_BUS);
        if (FMLEnvironment.dist == Dist.CLIENT) new MonoLibClientNeoForge(EVENT_BUS);

        MonoLibNeoForge.createEventListeners(MonoLibNeoForge.EVENT_BUS);
    }

    private static void createEventListeners(final IEventBus modEventBus) {

        NeoForge.EVENT_BUS.addListener((Consumer<EntityJoinLevelEvent>) event -> {
            Sailing.onEntityJoinLevel(event.getEntity(), event.getLevel());
        });

        NeoForge.EVENT_BUS.addListener((Consumer<RegisterCommandsEvent>) event -> {
            MonoLibCommands.defineCommands(event.getDispatcher(), event.getBuildContext(), event.getCommandSelection());
        });
    }
}