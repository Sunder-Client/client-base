package wtf.base.client.feature.gui;

import net.minecraft.client.gui.GuiScreen;
import wtf.base.client.Client;
import wtf.base.client.feature.module.Module;
import wtf.base.client.feature.module.ModuleCategory;
import wtf.base.client.feature.module.visuals.ClickGUI;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClickGUIScreen extends GuiScreen {

    public static final Color COMPONENT_COLOR = new Color(57, 105, 192);

    private static ClickGUIScreen instance;

    private final List<CategoryComponent> panels = new ArrayList<>();

    private ClickGUIScreen() {
        double x = 6.0;

        for (ModuleCategory category : ModuleCategory.values()) {
            List<Module> modules = Client.moduleManager.modules
                    .stream()
                    .filter((module) -> module.getCategory().equals(category))
                    .collect(Collectors.toList());

            if (modules.isEmpty()) {
                continue;
            }

            CategoryComponent component = new CategoryComponent(category.displayName, modules);
            component.setX(x);
            component.setY(26.0);
            component.setWidth(110.0);
            component.setHeight(16.0);

            panels.add(component);

            x += component.getWidth() + 5.0;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        panels.forEach((panel) -> panel.render(mouseX, mouseY, partialTicks));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        panels.forEach((panel) -> panel.mouseClick(mouseX, mouseY, mouseButton));
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        panels.forEach((panel) -> panel.keyTyped(typedChar, keyCode));
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();

        Client.moduleManager.moduleMap.get(ClickGUI.class).setToggled(false);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public static ClickGUIScreen getInstance() {
        if (instance == null) {
            instance = new ClickGUIScreen();
        }

        return instance;
    }
}
