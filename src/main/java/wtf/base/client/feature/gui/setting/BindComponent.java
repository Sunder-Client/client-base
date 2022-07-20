package wtf.base.client.feature.gui.setting;

import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;
import wtf.base.client.feature.setting.Bind;
import wtf.base.client.util.render.component.Component;

public class BindComponent extends Component {
    private final Bind bind;
    private boolean listening = false;

    public BindComponent(Bind bind) {
        super(bind.getName());
        this.bind = bind;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        String text = listening ? "Listening..." : name + ": " + EnumChatFormatting.GRAY + Keyboard.getKeyName(bind.getValue());
        mc.fontRendererObj.drawStringWithShadow(
                text, (float) (x + 2.3), (float) (y + (height / 2.0) - (mc.fontRendererObj.FONT_HEIGHT / 2.0)), -1);
    }

    @Override
    public void mouseClick(int mouseX, int mouseY, int mouseButton) {
        if (isMouseInBounds(mouseX, mouseY)) {
            playClickSound();
            listening = !listening;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (listening) {
            listening = false;

            if (keyCode == Keyboard.KEY_ESCAPE) {
                bind.setValue(Keyboard.KEY_NONE);
            } else bind.setValue(keyCode);
        }
    }
}
