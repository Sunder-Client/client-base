package wtf.base.client.feature.gui;

import wtf.base.client.feature.module.Module;
import wtf.base.client.util.render.RenderUtil;
import wtf.base.client.util.render.component.Component;
import wtf.base.client.util.render.component.DraggableComponent;

import java.awt.*;
import java.util.List;

public class CategoryComponent extends DraggableComponent {
    private static final Color HEADER = new Color(23, 23, 23);

    // modified on a right click
    private boolean opened = true;

    public CategoryComponent(String name, List<Module> modules) {
        super(name);
        modules.forEach((module) -> components.add(new ModuleComponent(module)));
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRectangle(x, y, width, height, HEADER.getRGB());
        mc.fontRendererObj.drawStringWithShadow(
                name, (float) (x + 2.3), (float) (y + (height / 2.0) - (mc.fontRendererObj.FONT_HEIGHT / 2.0)), -1);

        if (opened) {
            double posY = y + height;
            RenderUtil.drawRectangle(x, posY, width, getHeightFromComponents(), HEADER.brighter().getRGB());
            posY += 0.5;

            for (Component component : components) {
                component.setX(x + 2.0);
                component.setY(posY);
                component.setWidth(width - 4.0);
                component.setHeight(14.0);

                component.render(mouseX, mouseY, partialTicks);

                posY += component.getHeight() + 1.0;
            }
        }
    }

    @Override
    public void mouseClick(int mouseX, int mouseY, int mouseButton) {
        if (isMouseInBounds(mouseX, mouseY)) {
            opened = !opened;

            // play a happy click sound :)
            playClickSound();
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

    private double getHeightFromComponents() {
        double h = 0.5;

        for (Component component : components) {
            h += component.getHeight() + 1.0;
        }

        return h + 0.5;
    }
}
