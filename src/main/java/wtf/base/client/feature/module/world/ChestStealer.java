package wtf.base.client.feature.module.world;

import com.google.common.collect.Lists;
import me.bush.eventbus.annotation.EventListener;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import wtf.base.client.event.client.ClientTickEvent;
import wtf.base.client.feature.module.Module;
import wtf.base.client.feature.module.ModuleCategory;
import wtf.base.client.feature.setting.Setting;
import wtf.base.client.util.player.inventory.InventoryUtil;

import java.util.List;

public class ChestStealer extends Module {
    private static final List<String> VALID_NAMES = Lists.newArrayList("Chest", "Large Chest");

    public ChestStealer() {
        super("Chest Stealer", ModuleCategory.WORLD);
        setBind(Keyboard.KEY_M);
    }

    public final Setting<Double> delay = doubleSetting("Delay", 0.1, 0.0, 1.5);
    public final Setting<Boolean> smart = boolSetting("Smart", true);
    public final Setting<Boolean> ignoreJunk = boolSetting("Ignore Junk", true);

    private int slot = 0;
    private long time = 0L;

    @Override
    protected void onDeactivated() {
        super.onDeactivated();
        resetValues();
    }

    @EventListener
    public void onClientTick(ClientTickEvent event) {
        if (mc.currentScreen instanceof GuiChest) {

            if (smart.getValue() && InventoryUtil.isInventoryFull()) {
                resetValues();
                return;
            }

            Container container = mc.thePlayer.openContainer;
            if (container instanceof ContainerChest) {
                IInventory lower = ((ContainerChest) container).getLowerChestInventory();

                if (smart.getValue() && !VALID_NAMES.contains(lower.getName())) {
                    return;
                }

                if (System.currentTimeMillis() - time >= (long) (delay.getValue() * 1000.0)) {
                    ItemStack itemStack = lower.getStackInSlot(slot);

                    if (ignoreJunk.getValue() ? !InventoryUtil.isJunk(itemStack) : itemStack != null) {
                        time = System.currentTimeMillis();
                        mc.playerController.windowClick(container.windowId, slot, 0, 1, mc.thePlayer);
                    }

                    ++slot;
                }
            }
        } else resetValues();
    }

    private void resetValues() {
        slot = 0;
        time = 0L;
    }
}
