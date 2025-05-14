package com.cursee.monolib.platform;

import com.cursee.monolib.platform.services.IPlatformHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public String getGameDirectory() {

        return FabricLoader.getInstance().getGameDir().toString();
    }

    @Override
    public boolean isClientSide() {

        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    private static final Map<String, String> OFFICIAL_FROM_INTERMEDIARY = new HashMap<>();

    static {
        OFFICIAL_FROM_INTERMEDIARY.put("class_1814", "Rarity");
        OFFICIAL_FROM_INTERMEDIARY.put("class_2561", "Component");
        OFFICIAL_FROM_INTERMEDIARY.put("class_2960", "ResourceLocation");
        OFFICIAL_FROM_INTERMEDIARY.put("class_3902", "Unit");
        OFFICIAL_FROM_INTERMEDIARY.put("class_4174", "FoodProperties");
        OFFICIAL_FROM_INTERMEDIARY.put("class_6538", "AdventureModePredicate");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9279", "CustomData");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9280", "CustomModelData");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9285", "ItemAttributeModifiers");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9290", "ItemLore");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9304", "ItemEnchantments");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9424", "Tool");
        OFFICIAL_FROM_INTERMEDIARY.put("class_10124", "Consumable");
        OFFICIAL_FROM_INTERMEDIARY.put("class_10130", "UseCooldown");
        OFFICIAL_FROM_INTERMEDIARY.put("class_10131", "UseRemainder");
        OFFICIAL_FROM_INTERMEDIARY.put("class_10215", "DamageResistant");
        OFFICIAL_FROM_INTERMEDIARY.put("class_10712", "TooltipDisplay");

        // sort these later, just speeding up the process
        OFFICIAL_FROM_INTERMEDIARY.put("class_1329", "RangedAttribute");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9282", "DyedItemColor");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9209", "MapId");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9278", "ChargedProjectiles");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9276", "BundleContents");
        OFFICIAL_FROM_INTERMEDIARY.put("class_1844", "PotionContents");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9298", "SuspiciousStewEffects");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9301", "WritableBookContent");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9302", "WrittenBookContent");
        OFFICIAL_FROM_INTERMEDIARY.put("class_8053", "ArmorTrim");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9281", "DebugStickState");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9792", "JukeboxPlayable");
        OFFICIAL_FROM_INTERMEDIARY.put("class_6862", "TagKey");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9291", "LodestoneTracker");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9283", "FireworkExplosion");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9284", "Fireworks");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9296", "ResolvableProfile");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9307", "BannerPatternLayers");
        OFFICIAL_FROM_INTERMEDIARY.put("class_1767", "DyeColor");
        OFFICIAL_FROM_INTERMEDIARY.put("class_8526", "PotDecorations");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9288", "ItemContainerContents");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9275", "BlockItemStateProperties");
        OFFICIAL_FROM_INTERMEDIARY.put("class_1273", "LockCode");
        OFFICIAL_FROM_INTERMEDIARY.put("class_3414", "SoundEvent");
        OFFICIAL_FROM_INTERMEDIARY.put("class_5149", "Variant");
        OFFICIAL_FROM_INTERMEDIARY.put("class_6880", "Holder");

        // begin to sort
        OFFICIAL_FROM_INTERMEDIARY.put("class_9287", "Entry");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9791", "EitherHolder");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9889", "Enchantable");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9890", "Repairable");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9292", "MapDecorations");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9294", "MapItemColor");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9295", "MapPostProcessing");
        OFFICIAL_FROM_INTERMEDIARY.put("class_9297", "SeededContainerLoot");
        OFFICIAL_FROM_INTERMEDIARY.put("class_10129", "OminousBottleAmplifier");
        OFFICIAL_FROM_INTERMEDIARY.put("class_10132", "ApplyStatusEffectsConsumeEffect");
        OFFICIAL_FROM_INTERMEDIARY.put("class_10192", "Equippable");
        OFFICIAL_FROM_INTERMEDIARY.put("class_10216", "DeathProtection");
        OFFICIAL_FROM_INTERMEDIARY.put("class_10590", "Weapon");
        OFFICIAL_FROM_INTERMEDIARY.put("class_10706", "Bees");
        OFFICIAL_FROM_INTERMEDIARY.put("class_10707", "BlocksAttacks");
        OFFICIAL_FROM_INTERMEDIARY.put("class_10710", "InstrumentComponent");
        OFFICIAL_FROM_INTERMEDIARY.put("class_10711", "ProvidesTrimMaterial");
    }

    @Override
    public CharSequence replaceIntermediaryWithOfficial(String string) {

        // "must be final or effectively final" / we can't assign new values to string parameter of this method
        AtomicReference<String> reference = new AtomicReference<>(string);

        OFFICIAL_FROM_INTERMEDIARY.forEach((intermediary, official) -> {
            if (string.contains(intermediary)) reference.set(reference.get().replace(intermediary, official));
        });

        return reference.get();
    }
}
