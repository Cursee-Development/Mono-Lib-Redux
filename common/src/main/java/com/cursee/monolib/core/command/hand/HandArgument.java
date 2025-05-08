package com.cursee.monolib.core.command.hand;

import com.cursee.monolib.core.command.arg.EnumArgument;
import com.cursee.monolib.core.command.arg.SingletonArgumentInfo;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

/** Adapted from Darkhax's <a href="https://github.com/Darkhax-Minecraft/Bookshelf">Bookshelf</a>. */
public class HandArgument extends EnumArgument<HandArgumentHelper.OutputType> {

    public static final HandArgument ARGUMENT = new HandArgument();
    public static final SingletonArgumentInfo<HandArgument> SERIALIZER = SingletonArgumentInfo.of(ARGUMENT);

    private HandArgument() {

        super(HandArgumentHelper.OutputType.class);
    }

    public static HandArgumentHelper.OutputType get(CommandContext<CommandSourceStack> context) {

        return context.getArgument("output_type", HandArgumentHelper.OutputType.class);
    }

    public static RequiredArgumentBuilder<CommandSourceStack, HandArgumentHelper.OutputType> arg() {

        return Commands.argument("output_type", ARGUMENT);
    }
}
