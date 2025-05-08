package com.cursee.monolib.core;

import com.cursee.monolib.Constants;
import com.cursee.monolib.core.util.config.SimpleConfig;
import com.cursee.monolib.core.util.config.v2.Config;
import com.cursee.monolib.platform.Services;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class CommonConfigHandler {

    public static final String CONFIG_DIR_FILEPATH = Services.PLATFORM.getGameDirectory() + File.separator + "config";

    public static void onLoad() {

        final File CONFIG_DIR = new File(CONFIG_DIR_FILEPATH);
        if (!CONFIG_DIR.isDirectory() && !CONFIG_DIR.mkdirs()) {
            throw new RuntimeException("Unable to access or create directory: " + CONFIG_DIR_FILEPATH);
        }

        handle(new File(CONFIG_DIR_FILEPATH + File.separator + Constants.MOD_ID + "-common.toml"));
    }

    public static void handle(File file) {

        SimpleConfig config = createSimpleConfigObject();

        if (!file.isFile()) {
            config.save(file.toPath());
        }
        else {
            config.load(file.toPath());
            loadSimpleConfigObject(config);
        }
    }

    private static @NotNull SimpleConfig createSimpleConfigObject() {

        Config cfg = Config.createCommon(Constants.MOD_ID);

//        MOD_ID, common
//        .set("enable_debugging") -> enable_debugging:MOD_ID:common
//        .set("enable_debugging") -> enable_debugging:MOD_ID:client
//        .set("enable_debugging") -> enable_debugging:MOD_ID:server
//        ["modid:client", {configObj}]
//        clientCfg = cfg.getById("client")
//        clientCfg.set("...")

//        cfg.set("id", "key","value")
        SimpleConfig config = new SimpleConfig(Constants.MOD_ID + "-common");

        config.set("# enable_debugging permits more console logs, default ", CommonConfigValues.enable_debugging, false);
        config.set("enable_debugging", CommonConfigValues.enable_debugging, false);


        config.set("# enable_jar_verification checks installed jar files against mod reposting websites, default ", CommonConfigValues.enable_jar_verification, false);
        config.set("enable_jar_verification", CommonConfigValues.enable_jar_verification, false);

        return config;
    }

    private static void loadSimpleConfigObject(@NotNull SimpleConfig config) {
        CommonConfigValues.enable_debugging = config.getBoolean("enable_debugging");
        CommonConfigValues.enable_jar_verification = config.getBoolean("enable_jar_verification");
    }

//    public static void handle(File file) {
//
//        SimpleConfig config = new SimpleConfig(Constants.MOD_NAME + "-common");
//        
//        config.set("# enable_debugging permits more console logs, default ", CommonConfigValues.enable_debugging, false);
//        config.set("enable_debugging", CommonConfigValues.enable_debugging, false);
//        
//        
//        config.set("# enable_jar_verification checks installed jar files against mod reposting websites, default ", CommonConfigValues.enable_jar_verification, false);
//        config.set("enable_jar_verification", CommonConfigValues.enable_jar_verification, false);
//
////        config.set("test_string", CommonConfigValues.test_string, false);
////        config.set("test_int", CommonConfigValues.test_int, false);
////        config.set("test_float", CommonConfigValues.test_float, false);
////        config.set("test_boolean", CommonConfigValues.test_boolean, false);
////
////        config.set("test_string_list", CommonConfigValues.test_string_list, false);
////        config.set("test_int_list", CommonConfigValues.test_int_list, false);
////        config.set("test_float_list", CommonConfigValues.test_float_list, false);
////        config.set("test_boolean_list", CommonConfigValues.test_boolean_list, false);
//
//        /// This is the same as the uncommented code:
//        /// final LinkedHashMap<String, Object> DEFAULT_CONFIG = new LinkedHashMap<>();
//        /// DEFAULT_CONFIG.put("enable_debugging", CommonConfigValues.enable_debugging);
//        /// DEFAULT_CONFIG.put("enable_jar_verification", CommonConfigValues.enable_jar_verification);
//        /// DEFAULT_CONFIG.put("boolean_list_test", CommonConfigValues.boolean_list_test);
//        /// SimpleConfig config = new SimpleConfig(DEFAULT_CONFIG);
//
//        if (!file.isFile()) config.save(file.toPath());
//        else {
//            config.load(file.toPath());
//            CommonConfigValues.enable_debugging = config.getBoolean("enable_debugging");
//            CommonConfigValues.enable_jar_verification = config.getBoolean("enable_jar_verification");
//
////            CommonConfigValues.test_string = config.getString("test_string");
////            CommonConfigValues.test_int = config.getInt("test_int");
////            CommonConfigValues.test_float = config.getFloat("test_float");
////            CommonConfigValues.test_boolean = config.getBoolean("test_boolean");
////            CommonConfigValues.test_string_list = config.getArrayOfString("test_string_list");
////            CommonConfigValues.test_int_list = config.getArrayOfInt("test_int_list");
////            CommonConfigValues.test_float_list = config.getArrayOfFloat("test_float_list");
////            CommonConfigValues.test_boolean_list = config.getArrayOfBoolean("test_boolean_list");
//        }
//    }
}
