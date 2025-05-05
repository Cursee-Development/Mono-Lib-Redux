package com.cursee.monolib;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Constants.MOD_ID)
public class MonoLibForge {

    public static IEventBus EVENT_BUS;

    public MonoLibForge(FMLJavaModLoadingContext context) {
        MonoLib.init();
        EVENT_BUS = context.getModEventBus();
        if (FMLEnvironment.dist == Dist.CLIENT) new MonoLibClientForge(EVENT_BUS);
    }

    @SuppressWarnings("removal")
    public MonoLibForge() {
        this(FMLJavaModLoadingContext.get());
    }
}