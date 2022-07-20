package wtf.base.client.feature.gui;

import wtf.base.client.feature.gui.setting.BindComponent;
import wtf.base.client.feature.module.Module;
import wtf.base.client.feature.setting.Bind;
import wtf.base.client.util.render.RenderUtil;
import wtf.base.client.util.render.component.Component;

public class ModuleComponent extends Component {
    private final Module module;

    private boolean opened = false;

    public ModuleComponent(Module module) {
        super(module.getName());
        this.module = module;

        module.settingsList.forEach((setting) -> {
            if (setting instanceof Bind) {
                components.add(new BindComponent((Bind) setting));
            }
        });
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        if (module.isActive()) {
            RenderUtil.drawRectangle(x, y, width, height, ClickGUIScreen.COMPONENT_COLOR.getRGB());
        }

        mc.fontRendererObj.drawStringWithShadow(
                name, (float) (x + 2.3), (float) (y + (height / 2.0) - (mc.fontRendererObj.FONT_HEIGHT / 2.0)), -1);

        if (opened) {
            double posY = y + 14.5;

            for (Component component : components) {
                component.setX(x + 2.0);
                component.setY(posY);
                component.setWidth(width - 4.0);
                component.setHeight(14.0);

                component.render(mouseX, mouseY, partialTicks);

                posY += component.getHeight() + 1.0;
            }
        } else height = 14.0;
    }

    @Override
    public void mouseClick(int mouseX, int mouseY, int mouseButton) {
        if (isMouseInBounds(mouseX, mouseY)) {
            playClickSound();

            if (mouseButton == 0) {
                module.setToggled(!module.isToggled());
            } else if (mouseButton == 1) {
                opened = !opened;
            }
        }

        if (opened) {
            components.forEach((comp) -> comp.mouseClick(mouseX, mouseY, mouseButton));
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (opened) {
            components.forEach((comp) -> comp.keyTyped(typedChar, keyCode));
        }
    }

    @Override
    public double getHeight() {
        double h = 14.5;

        if (opened) {
            for (Component component : components) {
                h += component.getHeight() + 1.0;
            }
        }

        return h + 0.5;
    }
}
