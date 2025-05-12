package com.cursee.monolib.mixin;

import com.cursee.monolib.core.event.FabricModBrewingStandEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.world.inventory.BrewingStandMenu$PotionSlot")
public class FabricBrewingStandMenuPotionSlotMixin {

    @Inject(method = "onTake", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/critereon/BrewedPotionTrigger;trigger(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/alchemy/Potion;)V"))
    private void monolib$onTakeINVOKE(Player player, ItemStack stack, CallbackInfo ci) {
        Slot instance = (Slot) (Object) this;
        FabricModBrewingStandEvents.PLAYER_BREWED_POTION.invoker().onPlayerBrewedPotion(instance, player, stack);
    }
}
