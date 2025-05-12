package com.cursee.monolib.core.registry;

import com.cursee.monolib.MonoLib;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.BiConsumer;

public class ModBlocks {

    public static final Block DEBUG_BLOCK = RegistryHelper.blockDefinition("debug_block", Block::new, BlockBehaviour.Properties.of().isSuffocating((blockState, blockGetter, blockPos) -> false));

    public static void register(BiConsumer<Block, ResourceLocation> consumer) {
        consumer.accept(DEBUG_BLOCK, MonoLib.identifier("debug_block"));
    }
}
