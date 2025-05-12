package com.cursee.monolib.core.command.hand;

import com.cursee.monolib.core.command.CommandHelper;
import com.cursee.monolib.core.command.IEnumCommand;
import com.cursee.monolib.core.serialization.codecs.map.MapCodecs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Locale;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.BiFunction;

/**
 * Adapted from Darkhax's <a href="https://github.com/Darkhax-Minecraft/Bookshelf">Bookshelf</a>
 */
public enum HandCommand implements IEnumCommand {

    ID((stack, level) -> {
//        final String text = Objects.requireNonNull(level.registryAccess().registryOrThrow(Registries.ITEM).getKey(stack.getItem())).toString();
        final String text = Objects.requireNonNull(level.registryAccess().get(Registries.ITEM).get().value().getKey(stack.getItem())).toString();
        return Component.literal(text).withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, text)));
    }),
    STRING((stack, level) -> {
        final String text = stack.toString();
        return Component.literal(text).withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, text)));
    }),
    INGREDIENT(fromCodec(MapCodecs.INGREDIENT.get(), (stack, level) -> Ingredient.of(stack.getItem()))),
    STACK_JSON(fromCodec(MapCodecs.ITEM_STACK.get(), (stack, level) -> stack)),
    SNBT((stack, level) -> {
        final StringJoiner joiner = new StringJoiner("\n");
        stack.getComponents().forEach(typedDataComponent -> joiner.add(typedDataComponent.toString()));
        return Component.literal(joiner.toString());
    }),
    TAGS(((stack, level) -> {
        final StringJoiner joiner = new StringJoiner("\n");
        stack.getTags().forEach(itemTagKey -> joiner.add(itemTagKey.location().toString()));
        return Component.literal(joiner.toString());
    }));

    private final ItemFormat format;

    HandCommand(ItemFormat format) {
        this.format = format;
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        final CommandSourceStack source = context.getSource();
        if (source.getEntity() instanceof LivingEntity living) {
            context.getSource().sendSuccess(() -> this.format.formatItem(living.getMainHandItem(), source.getLevel()), false);
        }
        return Command.SINGLE_SUCCESS;
    }

    private static <T> ItemFormat fromCodec(Codec<T> codec, BiFunction<ItemStack, ServerLevel, T> mapper) {
        return (stack, level) -> {
            if (stack.isEmpty()) {
                return Component.literal("Item must not be empty or air!").withStyle(ChatFormatting.RED);
            }
            final T value = mapper.apply(stack, level);
            final JsonElement json = codec.encodeStart(RegistryOps.create(JsonOps.INSTANCE, level.registryAccess()), value).getOrThrow();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return Component.literal(gson.toJson(json)).withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, gson.toJson(json))));
        };
    }

    @Override
    public String getCommandName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public static LiteralArgumentBuilder<CommandSourceStack> build(CommandBuildContext context) {
        return CommandHelper.buildFromEnum("hand", HandCommand.class);
    }

    interface ItemFormat {
        Component formatItem(ItemStack stack, ServerLevel level);
    }
}
