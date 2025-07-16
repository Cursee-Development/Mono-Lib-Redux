package com.cursee.monolib;

import com.cursee.monolib.core.command.MonoLibCommands;
import com.cursee.monolib.core.event.ForgeModAnvilEvents;
import com.cursee.monolib.core.registry.ModRegistryForge;
import com.cursee.monolib.core.sailing.Sailing;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
// import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.bus.EventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.function.Consumer;

@Mod(Constants.MOD_ID)
public class MonoLibForge {

    public static BusGroup MOD_BUS_GROUP = BusGroup.create(Constants.MOD_ID);

    // public static IEventBus EVENT_BUS;
    public static EventBus<ForgeModAnvilEvents.AnvilOnLandEvent> ANVIL_ON_LAND_BUS = EventBus.create(ForgeModAnvilEvents.AnvilOnLandEvent.class);
    public static EventBus<ForgeModAnvilEvents.AnvilOnBrokenAfterFallEvent> ANVIL_ON_BROKEN_AFTER_FALL_BUS = EventBus.create(ForgeModAnvilEvents.AnvilOnBrokenAfterFallEvent.class);

    public MonoLibForge(FMLJavaModLoadingContext context) {
        MonoLib.init();
        // EVENT_BUS = context.getModEventBus();

        ModRegistryForge.register();
        if (FMLEnvironment.dist == Dist.CLIENT) new MonoLibClientForge();

        MonoLibForge.createEventListeners();
    }

    private static void createEventListeners() {

//        MinecraftForge.EVENT_BUS.addListener((Consumer<EntityJoinLevelEvent>) event -> {
//            Sailing.onEntityJoinLevel(event.getEntity(), event.getLevel());
//        });

        EntityJoinLevelEvent.BUS.addListener(event -> {
            Sailing.onEntityJoinLevel(event.getEntity(), event.getLevel());
        });

//        MinecraftForge.EVENT_BUS.addListener((Consumer<RegisterCommandsEvent>) event -> {
//            MonoLibCommands.defineCommands(event.getDispatcher(), event.getBuildContext(), event.getCommandSelection());
//        });

        RegisterCommandsEvent.BUS.addListener(event -> {
            MonoLibCommands.defineCommands(event.getDispatcher(), event.getBuildContext(), event.getCommandSelection());
        });
    }

    @SuppressWarnings("removal")
    public MonoLibForge() {
        this(FMLJavaModLoadingContext.get());
    }
}