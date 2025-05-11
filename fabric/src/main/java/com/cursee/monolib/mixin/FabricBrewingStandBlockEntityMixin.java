package com.cursee.monolib.mixin;

import com.cursee.monolib.core.event.FabricModBrewingStandEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrewingStandBlockEntity.class)
public class FabricBrewingStandBlockEntityMixin {

    @Inject(method = "doBrew", at = @At(value = "TAIL"))
    private static void monolib$onTakeINVOKE(Level level, BlockPos pos, NonNullList<ItemStack> items, CallbackInfo ci) {
        BlockEntity entity = level.getBlockEntity(pos);
        if (!(entity instanceof BrewingStandBlockEntity instance)) return;
        FabricModBrewingStandEvents.BREWED_POTION.invoker().onBrewedPotion(instance, level, pos, items);
    }
}
