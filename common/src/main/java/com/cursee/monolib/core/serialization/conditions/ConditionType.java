package com.cursee.monolib.core.serialization.conditions;

import com.mojang.serialization.MapCodec;
import net.minecraft.resources.ResourceLocation;

/**
 * Adapted from Darkhax's <a href="https://github.com/Darkhax-Minecraft/Bookshelf">Bookshelf</a>
 * Represents a type of load condition that Bookshelf can process and test.
 *
 * @param id    The ID of the condition type.
 * @param codec The codec used to serialize the condition from data.
 */
public record ConditionType(ResourceLocation id, MapCodec<? extends ILoadCondition> codec) {
}
