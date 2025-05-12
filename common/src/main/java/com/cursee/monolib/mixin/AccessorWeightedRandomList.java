package com.cursee.monolib.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.random.WeightedRandomList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/** Adapted from Darkhax's <a href="https://github.com/Darkhax-Minecraft/Bookshelf">Bookshelf</a>. */
@Mixin(WeightedRandomList.class)
public interface AccessorWeightedRandomList<E> {

    @Accessor("totalWeight")
    int monolib$getTotalWeight();

    @Accessor("items")
    ImmutableList<E> monolib$getEntries();
}
