package wtf.base.client.util.render.component;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import wtf.base.client.feature.BaseFeature;

import java.util.ArrayList;
import java.util.List;

public abstract class Component extends BaseFeature {
    public final List<Component> components = new ArrayList<>();

    protected double x, y;
    protected double width, height;

    public Component(String name) {
        super(name);
    }

    public abstract void render(int mouseX, int mouseY, float partialTicks);

    public abstract void mouseClick(int mouseX, int mouseY, int mouseButton);

    public abstract void mouseReleased(int mouseX, int mouseY, int state);

    public abstract void keyTyped(char typedChar, int keyCode);

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public boolean isVisible() {
        return true;
    }

    public boolean isMouseInBounds(int mouseX, int mouseY) {
        return isMouseWithinBounds(mouseX, mouseY, x, y, width, height);
    }

    public static boolean isMouseWithinBounds(int mouseX, int mouseY, double x, double y, double w, double h) {
        return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
    }

    public static void playClickSound() {
        mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("minecraft:gui.button.press"), 1.0f));
    }
}
