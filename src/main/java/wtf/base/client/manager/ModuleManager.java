package wtf.base.client.manager;

import me.bush.eventbus.annotation.EventListener;
import org.lwjgl.input.Keyboard;
import wtf.base.client.Client;
import wtf.base.client.event.input.KeyInputEvent;
import wtf.base.client.feature.module.Module;
import wtf.base.client.feature.module.combat.Aura;
import wtf.base.client.feature.module.miscellaneous.ClientSpoofer;
import wtf.base.client.feature.module.movement.Sprint;
import wtf.base.client.feature.module.visuals.ClickGUI;
import wtf.base.client.feature.module.visuals.HUD;
import wtf.base.client.feature.module.world.ChestStealer;
import wtf.base.client.util.common.ClientImpl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Manages this client's modules
 *
 * This is where all module interactions are handled, and where you would register new modules
 */
public class ModuleManager implements ClientImpl {
    public final Map<Class<? extends Module>, Module> moduleMap = new LinkedHashMap<>();
    public final List<Module> modules = new LinkedList<>();

    public ModuleManager() {

        // add ourselves to the event bus, we will need this for key presses
        Client.BUS.subscribe(this);

        // Combat modules
        add(new Aura());

        // Miscellaneous
        add(new ClientSpoofer());

        // Movement modules
        add(new Sprint());

        // Visual modules
        add(new ClickGUI());
        add(new HUD());

        // World modules
        add(new ChestStealer());
    }

    protected void add(Module module) {
        moduleMap.put(module.getClass(), module);
        modules.add(module);
    }

    @EventListener
    public void onKeyInput(KeyInputEvent event) {

        // we do not want to check if the keyCode is KEY_NONE (the default keybind, if we do not have this check
        // so much shit will go wrong), we are not holding the key and we are in the normal game
        if (event.getKeyCode() == Keyboard.KEY_NONE || !event.isKeyState() || mc.currentScreen != null) {
            return;
        }

        modules.forEach((module) -> {

            // if the module has a bind of the one the event posted, we'll toggle
            if (module.getBind() == event.getKeyCode()) {
                module.setToggled(!module.isToggled());
            }
        });
    }
}
