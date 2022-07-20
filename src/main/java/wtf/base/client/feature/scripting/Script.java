package wtf.base.client.feature.scripting;

import jdk.nashorn.api.scripting.JSObject;
import me.bush.eventbus.event.Event;
import wtf.base.client.Client;
import wtf.base.client.feature.ToggleFeature;
import wtf.base.client.feature.module.ModuleCategory;
import wtf.base.client.manager.ScriptManager;
import wtf.base.client.util.common.ClientImpl;

import java.util.LinkedHashMap;
import java.util.Map;

public class Script extends ToggleFeature implements ClientImpl {
    private final String name;
    private final String[] authors;

    private final Map<String, JSObject> events = new LinkedHashMap<>();

    public ScriptModule module = null;

    public Script(String name, String[] authors) {
        super(name);

        this.name = name;
        this.authors = authors;

        ScriptManager.log.info("Loading script named \"{}\" by {}", name, String.join(", ", authors));
        ScriptManager.scripts.put(name, this);
    }

    public ScriptModule createModule(String name, String category) {
        ModuleCategory cat = ModuleCategory.MISCELLANEOUS;
        try {
            cat = ModuleCategory.valueOf(category.toUpperCase());
        } catch (Exception ignored) {

        }

        module = new ScriptModule(name, cat);
        Client.moduleManager.add(module);

        return module;
    }

    public void onEvent(String name, JSObject handler) {

        // similar to how a lot of js event handlers are done
        // script.onEvent("name", (event) => { console.log(event); });

        events.put(name, handler);
    }

    public void callEvent(String name, Event event) {

        if (!isToggled()) {
            return;
        }

        if (name.equalsIgnoreCase("enable")) {
            setToggled(true);
        } else if (name.equalsIgnoreCase("disable")) {
            setToggled(false);

            if (module != null) {
                module.setToggled(false);
            }
        }

        JSObject object = events.get(name);
        if (object != null) {
            object.call(this, event);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public String[] getAuthors() {
        return authors;
    }
}
