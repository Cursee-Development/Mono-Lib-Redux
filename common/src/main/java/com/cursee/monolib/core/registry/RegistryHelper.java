package com.cursee.monolib.core.registry;

import com.cursee.monolib.MonoLib;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class RegistryHelper {

    public static Block blockDefinition(String s, Function<BlockBehaviour.Properties, Block> blockConstructor, BlockBehaviour.Properties blockProperties) {
        return blockConstructor.apply(blockProperties.setId(ResourceKey.create(Registries.BLOCK, MonoLib.identifier(s))));
    }

    public static Item itemDefintion(String s, Function<Item.Properties, Item> itemConstructor, Item.Properties itemProperties) {
        return itemConstructor.apply(itemProperties.setId(ResourceKey.create(Registries.ITEM, MonoLib.identifier(s))));
    }
}
