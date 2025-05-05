package com.cursee.monolib;

import com.cursee.monolib.core.registry.ModRegistryFabric;
import net.fabricmc.api.ModInitializer;

public class MonoLibFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        MonoLib.init();
        ModRegistryFabric.register();
    }
}
