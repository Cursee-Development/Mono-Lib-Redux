package net.jason13.monolib.methods;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/** Still being used by MobDropsRecipes: Overworld/End, AutoMessage, TimeOnDisplay, and MoreBeautifulTorches. */
@Deprecated(since = "2.0.0", forRemoval = true)
public class BlockMethods {

    @Deprecated(since = "2.0.0", forRemoval = true)
    public static boolean compareBlockToBlock(Block pBlock0, Block pBlock1) {
        return false;
    }

    @Deprecated(since = "2.0.0", forRemoval = true)
    public static boolean compareBlockToItem(Block pBlock, Item pItem) {
        return false;
    }

    @Deprecated(since = "2.0.0", forRemoval = true)
    public static boolean compareBlockToItemStack(Block pBlock, ItemStack pItemStack) {
        return false;
    }

    @Deprecated(since = "2.0.0", forRemoval = true)
    public static boolean compareBlockToBlockState(Block pBlock, BlockState pBlockState) {
        return false;
    }

    @Deprecated(since = "2.0.0", forRemoval = true)
    public static boolean compareBlockToLevelPosition(Block pBlock, Level pLevel, BlockPos pBlockPos) {
        return false;
    }
}