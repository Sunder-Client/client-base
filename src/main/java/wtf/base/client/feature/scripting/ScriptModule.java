package wtf.base.client.feature.scripting;

import jdk.nashorn.api.scripting.JSObject;
import me.bush.eventbus.annotation.EventListener;
import me.bush.eventbus.event.Event;
import wtf.base.client.event.client.ClientTickEvent;
import wtf.base.client.feature.module.Module;
import wtf.base.client.feature.module.ModuleCategory;

import java.util.LinkedHashMap;
import java.util.Map;

public class ScriptModule extends Module {
    public ScriptModule(String name, ModuleCategory category) {
        super(name, category);
    }

    @Override
    protected void onActivated() {
        super.onActivated();
        callEvent("enable", null);
    }

    @Override
    protected void onDeactivated() {
        super.onDeactivated();
        callEvent("disable", null);
    }

    @EventListener
    public void onTick(ClientTickEvent event) {
        callEvent("tick", event);
    }

    private final Map<String, JSObject> events = new LinkedHashMap<>();

    public void onEvent(String name, JSObject handler) {

        // similar to how a lot of js event handlers are done
        // script.onEvent("name", (event) => { console.log(event); });

        events.put(name, handler);
    }

    public void callEvent(String name, Event event) {

        if (!isToggled() && !name.equals("disable")) {
            return;
        }

        JSObject object = events.get(name);
        if (object != null) {
            object.call(this, event);
        }
    }
}
