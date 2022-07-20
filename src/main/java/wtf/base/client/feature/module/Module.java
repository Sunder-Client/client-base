package wtf.base.client.feature.module;

import org.lwjgl.input.Keyboard;
import wtf.base.client.feature.setting.Bind;
import wtf.base.client.feature.setting.Setting;
import wtf.base.client.feature.setting.SettingContainer;

public class Module extends SettingContainer {
    protected final ModuleCategory category;

    protected final Bind bind = bindSetting("Bind", Keyboard.KEY_NONE);
    public final Setting<Boolean> drawn = boolSetting("Drawn", true);

    public Module(String name, ModuleCategory category) {
        super(name);

        this.category = category;
    }

    public int getBind() {
        return bind.getValue();
    }

    public void setBind(int bindIn) {
        bind.setValue(bindIn);
    }

    public ModuleCategory getCategory() {
        return category;
    }
}
