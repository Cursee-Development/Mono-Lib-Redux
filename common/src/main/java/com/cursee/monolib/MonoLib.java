package com.cursee.monolib;

import com.cursee.monolib.core.CommonConfigHandler;
import com.cursee.monolib.core.sailing.Sailing;
import net.minecraft.resources.ResourceLocation;

public class MonoLib {

    public static void init() {
        CommonConfigHandler.onLoad();
        Sailing.register(Constants.MOD_ID, Constants.MOD_NAME, Constants.MOD_VERSION, Constants.MOD_PUBLISHER, Constants.MOD_URL);
    }

    public static ResourceLocation identifier(String path) {
        return new ResourceLocation(Constants.MOD_ID, path);
    }
}