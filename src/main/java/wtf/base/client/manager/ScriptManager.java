package wtf.base.client.manager;

import com.google.common.io.Files;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import jdk.nashorn.api.scripting.ScriptUtils;
import me.bush.eventbus.annotation.EventListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wtf.base.client.Client;
import wtf.base.client.event.client.ClientTickEvent;
import wtf.base.client.feature.ToggleFeature;
import wtf.base.client.feature.scripting.Script;
import wtf.base.client.util.common.ClientImpl;
import wtf.base.client.util.io.FileUtil;

import javax.script.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Manages scripts that are loaded using the nashorn engine
 */
public class ScriptManager extends ToggleFeature implements ClientImpl {

    // Our script engine. If this fails, it will crash the client.
    private static ScriptEngine engine;
    public static final Logger log = LogManager.getLogger("ScriptManager");

    public static final Map<String, Script> scripts = new HashMap<>();

    public ScriptManager() {
        super("ScriptManager");

        engine = new NashornScriptEngineFactory().getScriptEngine("--language=es6");

        // subscribe ourselves to the event bus
        Client.BUS.subscribe(this);

        Bindings bindings = new SimpleBindings();

        // our bindings - anything we want to be able to access in our script
        bindings.put("mc", mc);
        bindings.put("moduleManager", Client.moduleManager);
        bindings.put("rotationManager", Client.rotationManager);

        // functions
        bindings.put("createScript", (Function<JSObject, Script>) jsObject -> {
            if (!jsObject.hasMember("name") || !jsObject.hasMember("authors")) {
                return null;
            }

            String name = (String) jsObject.getMember("name");
            String[] authors = (String[]) ScriptUtils.convert(jsObject.getMember("authors"), StaticClass.forClass(String[].class));

            return new Script(name, authors);
        });

        bindings.put("println", (Function<String, Void>) s -> {
            System.out.println(s);
            return null;
        });

        engine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);

        // load our scripts from the scripts folder
        log.info("Loading scripts from {}", FileUtil.SCRIPTS);

        setToggled(true);
    }

    @Override
    protected void onActivated() {
        super.onActivated();

        loadScripts();
        scripts.forEach((name, script) -> script.callEvent("enable", null));

        log.info("Loaded {} script(s)", scripts.size());
    }

    @Override
    protected void onDeactivated() {
        super.onDeactivated();

        scripts.forEach((name, script) -> {
            script.callEvent("disable", null);
            scripts.remove(script);
        });
    }

    private void loadScripts() {
        File[] files = FileUtil.getFilesFromDirectory(FileUtil.SCRIPTS);
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (!file.canRead()) {
                    continue;
                }

                String ext = Files.getFileExtension(file.getName());
                if (!ext.equals("js") && !ext.equals("javascript")) {
                    continue;
                }

                String pt = FileUtil.readFile(file.toPath());
                if (pt != null && !pt.isEmpty()) {
                    log.debug("Found script file at {}", file.toPath());
                    loadScript(pt);
                }
            }
        }
    }

    private void loadScript(String pt) {
        try {
            engine.eval(pt);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public void loadScript(Script script) {
        scripts.put(script.getName(), script);
    }

    public void unloadScript(String name) {
        Script script = scripts.get(name);
        if (script == null) {
            return;
        }

        script.callEvent("disable", null);

        if (script.module != null) {
            Client.moduleManager.remove(script.module);
        }
    }

    @EventListener
    public void onClientTick(ClientTickEvent event) {
        scripts.forEach((name, script) -> script.callEvent("tick", event));
    }
}
