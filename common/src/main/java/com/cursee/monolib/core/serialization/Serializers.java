package com.cursee.monolib.core.serialization;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

/** Adapted from Darkhax's <a href="https://github.com/Darkhax-Minecraft/Bookshelf">Bookshelf</a>. */
public class Serializers {

    public static final ISerializer<String> STRING = SerializerString.SERIALIZER;
    public static final ISerializer<Ingredient> INGREDIENT = SerializerIngredient.SERIALIZER;
    public static final ISerializer<ItemStack> ITEM_STACK = SerializerItemStack.SERIALIZER;
    public static final ISerializer<Integer> INT = SerializerInteger.SERIALIZER;

    public static final ISerializer<CompoundTag> COMPOUND_TAG = SerializerCompoundTag.SERIALIZER;
    public static final ISerializer<Item> ITEM = new SerializerRegistryEntry<>(BuiltInRegistries.ITEM);
    public static final ISerializer<ResourceLocation> RESOURCE_LOCATION = SerializerResourceLocation.SERIALIZER;
}
