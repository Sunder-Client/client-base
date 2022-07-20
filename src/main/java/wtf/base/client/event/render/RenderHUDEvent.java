package wtf.base.client.event.render;

import me.bush.eventbus.event.Event;
import net.minecraft.client.gui.ScaledResolution;

/**
 * Posted when the HUD is rendered
 *
 * See GuiIngame#renderGameOverlay
 */
public class RenderHUDEvent extends Event {
    private final ScaledResolution resolution;

    public RenderHUDEvent(ScaledResolution resolution) {
        this.resolution = resolution;
    }

    public ScaledResolution getResolution() {
        return resolution;
    }

    @Override
    protected boolean isCancellable() {
        return false;
    }
}
