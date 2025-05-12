package com.cursee.monolib.core.registry;

import com.cursee.monolib.MonoLib;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.function.BiConsumer;

public class ModItems {

    public static final Item DEBUG_ITEM = new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));

    public static void register(BiConsumer<Item, ResourceLocation> consumer) {
        consumer.accept(DEBUG_ITEM, MonoLib.identifier("debug_item"));
        consumer.accept(new BlockItem(ModBlocks.DEBUG_BLOCK, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)), BuiltInRegistries.BLOCK.getKey(ModBlocks.DEBUG_BLOCK));
    }
}
