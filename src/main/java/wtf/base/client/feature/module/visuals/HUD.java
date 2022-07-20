package wtf.base.client.feature.module.visuals;

import me.bush.eventbus.annotation.EventListener;
import wtf.base.client.Client;
import wtf.base.client.event.render.RenderHUDEvent;
import wtf.base.client.feature.module.Module;
import wtf.base.client.feature.module.ModuleCategory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// An insanely simple HUD
public class HUD extends Module {
    public HUD() {
        super("HUD", ModuleCategory.VISUALS);

        drawn.setValue(false);
        setToggled(true);
    }

    @EventListener
    public void onRenderHUD(RenderHUDEvent event) {
        // our client watermark
        String name = Client.NAME + " v" + Client.VERSION + "-" + Client.ENV.displayName;
        mc.fontRendererObj.drawStringWithShadow(name, 4.0f, 4.0f, -1);

        // the array list of modules
        List<Module> enabled = Client.moduleManager.modules
                .stream()
                .filter((m) -> m.isActive() && m.drawn.getValue())
                .collect(Collectors.toList());

        if (!enabled.isEmpty()) {
            enabled.sort(Comparator.comparingInt((m) -> -mc.fontRendererObj.getStringWidth(m.getName())));

            float y = 4.0f;
            for (Module module : enabled) {
                mc.fontRendererObj.drawStringWithShadow(
                        module.getName(),
                        event.getResolution().getScaledWidth() - mc.fontRendererObj.getStringWidth(module.getName()) - 4.0f,
                        y,
                        -1);

                y += mc.fontRendererObj.FONT_HEIGHT + 2.0f;
            }
        }
    }
}
