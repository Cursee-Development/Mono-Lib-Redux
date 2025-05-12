package com.cursee.monolib;

import com.cursee.monolib.core.command.MonoLibCommands;
import com.cursee.monolib.core.registry.ModRegistryFabric;
import com.cursee.monolib.core.sailing.Sailing;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;

public class MonoLibFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        MonoLib.init();
        // no event bus to define

        ModRegistryFabric.register();
        // client class initialized through fabric

        MonoLibFabric.createEventListeners();
    }

    private static void createEventListeners() {
        ServerEntityEvents.ENTITY_LOAD.register(Sailing::onEntityJoinLevel);
        CommandRegistrationCallback.EVENT.register(MonoLibCommands::defineCommands);
    }
}
