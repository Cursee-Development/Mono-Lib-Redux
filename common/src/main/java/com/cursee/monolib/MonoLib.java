package com.cursee.monolib;

import com.cursee.monolib.core.CommonConfigHandler;
import com.cursee.monolib.core.sailing.Sailing;
import com.cursee.monolib.core.sailing.warden.SailingWarden;
import com.cursee.monolib.platform.Services;
import net.minecraft.resources.ResourceLocation;

import java.io.File;

public class MonoLib {

    public static void init() {
        SailingWarden.processDirectoryOrFilePathStrings(Services.PLATFORM.getGameDirectory() + File.separator + "mods");
        Sailing.register(Constants.MOD_ID, Constants.MOD_NAME, Constants.MOD_VERSION, Constants.MOD_PUBLISHER, Constants.MOD_URL);
        CommonConfigHandler.onLoad();
    }

    public static ResourceLocation identifier(String path) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path);
    }
}