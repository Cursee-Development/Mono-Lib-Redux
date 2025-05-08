package com.cursee.monolib.core.command.hand;

import com.cursee.monolib.core.serialization.Serializers;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.StringJoiner;
import java.util.function.Function;

/** Adapted from Darkhax's <a href="https://github.com/Darkhax-Minecraft/Bookshelf">Bookshelf</a>. */
public class HandArgumentHelper {

    public static int printHeldStack(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {

        final OutputType type = HandArgument.get(context);
        final Entity sender = context.getSource().getEntity();

        if (sender instanceof LivingEntity living) {

            final String stackOutput = type.converter.apply(living.getMainHandItem());
            context.getSource().sendSuccess(() -> Component.literal(stackOutput).withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, stackOutput))), false);
        }

        return 1;
    }

    public enum OutputType {

        string(OutputType::getAsString),
        ingredient(OutputType::getAsIngredient),
        stack_json(OutputType::getAsStackJson),
        snbt(OutputType::getAsSNBT),
        id(OutputType::getAsID),
        tags(OutputType::getTags);

        private final Function<ItemStack, String> converter;

        OutputType(Function<ItemStack, String> converter) {

            this.converter = converter;
        }

        private static String getAsString(ItemStack stack) {

            return stack.toString();
        }

        private static String getAsIngredient(ItemStack stack) {

            return Serializers.INGREDIENT.toJSON(Ingredient.of(stack)).toString();
        }

        private static String getAsStackJson(ItemStack stack) {

            return Serializers.ITEM_STACK.toJSON(stack).toString();
        }

        public static String getAsSNBT(ItemStack stack) {

            return stack.save(new CompoundTag()).getAsString();
        }

        public static String getAsID(ItemStack stack) {

            final ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
            return id != null ? id.toString() : null;
        }

        public static String getTags(ItemStack stack) {

            final StringJoiner joiner = new StringJoiner("\n");

            stack.getItem().builtInRegistryHolder().tags().forEach(tag -> joiner.add(tag.location().toString()));

            return joiner.toString();
        }
    }
}
