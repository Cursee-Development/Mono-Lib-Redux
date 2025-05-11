package com.cursee.monolib;

import com.cursee.monolib.core.CommonConfigValues;
import com.cursee.monolib.core.command.MonoLibCommands;
import com.cursee.monolib.core.command.hand.HandArgument;
import com.cursee.monolib.core.event.FabricModAnvilEvents;
import com.cursee.monolib.core.registry.ModItems;
import com.cursee.monolib.core.registry.ModRegistryFabric;
import com.cursee.monolib.core.sailing.Sailing;
import com.cursee.monolib.platform.Services;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Optional;

public class MonoLibFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        MonoLib.init();
        // no event bus to define

        ModRegistryFabric.register();
        // client class initialized through fabric

        MonoLibFabric.createEventListeners();

        FabricModAnvilEvents.ON_TAKE.register(this::onTakeInvoker);
    }

    private Optional<Float> onTakeInvoker(AnvilMenu anvilMenu, Player player, ItemStack output, ItemStack left, ItemStack right) {

        if (output.is(ModItems.DEBUG_ITEM)) return Optional.of(1.0f); // always damage anvil when modifying debug_item

        return Optional.empty();
    }

    private static void createEventListeners() {
        ServerEntityEvents.ENTITY_LOAD.register(Sailing::onEntityJoinLevel);
        ArgumentTypeRegistry.registerArgumentType(MonoLib.identifier("item_output"), HandArgument.class, HandArgument.SERIALIZER);
        CommandRegistrationCallback.EVENT.register(MonoLibCommands::defineCommands);
    }
}
