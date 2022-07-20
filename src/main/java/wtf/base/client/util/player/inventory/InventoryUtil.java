package wtf.base.client.util.player.inventory;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import wtf.base.client.util.common.ClientImpl;

import java.util.List;

/**
 * A utility for the player inventory
 */
public class InventoryUtil implements ClientImpl {
    private static final List<Block> NOT_JUNK_BLOCKS = Lists.newArrayList(
            Blocks.planks,
            Blocks.stone,
            Blocks.cobblestone,
            Blocks.brick_block,
            Blocks.tnt,
            Blocks.slime_block,
            Blocks.glass,
            Blocks.bookshelf,
            Blocks.dirt,
            Blocks.grass
    );

    public static boolean isInventoryFull() {
        for (int i = 0; i < 36; ++i) {
            ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
            if (stack == null || stack.stackSize != stack.getMaxStackSize()) {
                return false;
            }
        }

        return true;
    }

    public static boolean isJunk(ItemStack stack) {
        if (stack == null) {
            return false;
        }

        Item item = stack.getItem();
        if (item instanceof ItemBlock) {
            ItemBlock block = (ItemBlock) stack.getItem();
            return !NOT_JUNK_BLOCKS.contains(block.getBlock());
        } else {
            return !(item instanceof ItemSword ||
                    item instanceof ItemPickaxe ||
                    item instanceof ItemAxe ||
                    item instanceof ItemSpade ||
                    item instanceof ItemArmor);
        }
    }
}
