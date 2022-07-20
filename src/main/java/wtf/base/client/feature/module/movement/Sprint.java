package wtf.base.client.feature.module.movement;

import me.bush.eventbus.annotation.EventListener;
import org.lwjgl.input.Keyboard;
import wtf.base.client.event.client.ClientTickEvent;
import wtf.base.client.feature.module.Module;
import wtf.base.client.feature.module.ModuleCategory;
import wtf.base.client.feature.setting.Setting;

public class Sprint extends Module {

    public Sprint() {
        super("Sprint", ModuleCategory.MOVEMENT);

        bind.setValue(Keyboard.KEY_N);
    }

    public final Setting<Mode> mode = setting("Mode", Mode.LEGIT);

    @Override
    protected void onDeactivated() {
        if (!mc.gameSettings.keyBindSprint.isKeyDown()) {
            mc.thePlayer.setSprinting(false);
        }
    }

    @EventListener
    public void onClientTick(ClientTickEvent event) {

        // we should not sprint
        if (mc.thePlayer.isSprinting()) {
            return;
        }

        if (mode.getValue().equals(Mode.RAGE)) {
            mc.thePlayer.setSprinting(true);
        } else {
            mc.thePlayer.setSprinting(!mc.thePlayer.isCollidedHorizontally &&
                    mc.thePlayer.getFoodStats().getFoodLevel() > 6 &&
                    !mc.thePlayer.isSneaking() &&
                    !mc.thePlayer.isUsingItem() &&
                    mc.thePlayer.movementInput.moveForward > 0.0f);
        }
    }

    public enum Mode {
        RAGE, LEGIT
    }
}
