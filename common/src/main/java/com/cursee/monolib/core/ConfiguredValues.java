package com.cursee.monolib.core;

import com.cursee.monolib.core.config.SimpleConfigEntry;

@Deprecated(since = "2.1.0", forRemoval = true)
public class ConfiguredValues {

    @Deprecated(since = "2.1.0", forRemoval = true)
    public static final SimpleConfigEntry<Boolean> ENABLE_DEBUGGING = new SimpleConfigEntry<Boolean>("enable_debugging", false);

    @Deprecated(since = "2.1.0", forRemoval = true)
    public static final SimpleConfigEntry<Boolean> ENABLE_JAR_VERIFICATION = new SimpleConfigEntry<Boolean>("enable_jar_verification", true);
}
