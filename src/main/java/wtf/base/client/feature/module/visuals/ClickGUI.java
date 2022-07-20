package wtf.base.client.feature.module.visuals;

import org.lwjgl.input.Keyboard;
import wtf.base.client.feature.gui.ClickGUIScreen;
import wtf.base.client.feature.module.Module;
import wtf.base.client.feature.module.ModuleCategory;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", ModuleCategory.VISUALS);
        setBind(Keyboard.KEY_RSHIFT);
    }

    @Override
    protected void onActivated() {
        super.onActivated();

        mc.displayGuiScreen(ClickGUIScreen.getInstance());
    }
}
