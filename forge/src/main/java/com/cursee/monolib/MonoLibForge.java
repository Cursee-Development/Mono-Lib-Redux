package com.cursee.monolib;

import com.cursee.monolib.core.command.MonoLibCommands;
import com.cursee.monolib.core.registry.ModRegistryForge;
import com.cursee.monolib.core.sailing.Sailing;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.function.Consumer;

@Mod(Constants.MOD_ID)
public class MonoLibForge {

    public static IEventBus EVENT_BUS;

    public MonoLibForge(FMLJavaModLoadingContext context) {
        MonoLib.init();
        EVENT_BUS = context.getModEventBus();

        ModRegistryForge.register(EVENT_BUS);
        if (FMLEnvironment.dist == Dist.CLIENT) new MonoLibClientForge(EVENT_BUS);

        MonoLibForge.createEventListeners(MonoLibForge.EVENT_BUS);
    }

    private static void createEventListeners(final IEventBus modEventBus) {

        MinecraftForge.EVENT_BUS.addListener((Consumer<EntityJoinLevelEvent>) event -> {
            Sailing.onEntityJoinLevel(event.getEntity(), event.getLevel());
        });

        MinecraftForge.EVENT_BUS.addListener((Consumer<RegisterCommandsEvent>) event -> {
            MonoLibCommands.defineCommands(event.getDispatcher(), event.getBuildContext(), event.getCommandSelection());
        });
    }

    @SuppressWarnings("removal")
    public MonoLibForge() {
        this(FMLJavaModLoadingContext.get());
    }
}