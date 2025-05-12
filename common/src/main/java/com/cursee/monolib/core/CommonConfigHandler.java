package com.cursee.monolib.core;

import com.cursee.monolib.Constants;
import com.cursee.monolib.core.util.config.SimpleConfig;
import com.cursee.monolib.platform.Services;

import java.io.File;

public class CommonConfigHandler {

    public static final String IDENTIFIER = Constants.MOD_ID + "-common";
    public static final String CONFIG_DIR_FILEPATH = Services.PLATFORM.getGameDirectory() + File.separator + "config";

    public static void onLoad() {

        final File CONFIG_DIR = new File(CONFIG_DIR_FILEPATH);
        if (!CONFIG_DIR.isDirectory() && !CONFIG_DIR.mkdirs()) {
            throw new RuntimeException("Unable to access or create directory: " + CONFIG_DIR_FILEPATH);
        }

        handle(new File(CONFIG_DIR_FILEPATH + File.separator + IDENTIFIER + ".toml"));
    }

    public static void handle(File file) {

        SimpleConfig config = create();

        if (!file.isFile()) {
            config.save(file.toPath());
        }
        else {
            config.load(file.toPath());
            loadSimpleConfigObject(config);
        }
    }

    private static SimpleConfig create() {

        SimpleConfig config = new SimpleConfig(IDENTIFIER);

        config.putEntry("# enable_debugging permits more console logs, default ", CommonConfigValues.enable_debugging, false);
        config.putEntry("enable_debugging", CommonConfigValues.enable_debugging, false);

        config.putEntry("# enable_jar_verification checks installed jar files against mod reposting websites, default ", CommonConfigValues.enable_jar_verification, false);
        config.putEntry("enable_jar_verification", CommonConfigValues.enable_jar_verification, false);

        return config;
    }

    private static void loadSimpleConfigObject(SimpleConfig config) {
        CommonConfigValues.enable_debugging = config.getBoolean("enable_debugging");
        CommonConfigValues.enable_jar_verification = config.getBoolean("enable_jar_verification");
    }
}
