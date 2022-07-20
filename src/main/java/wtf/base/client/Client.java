package wtf.base.client;

import me.bush.eventbus.bus.EventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wtf.base.client.manager.CommandManager;
import wtf.base.client.manager.ModuleManager;
import wtf.base.client.manager.RotationManager;
import wtf.base.client.manager.ScriptManager;

public class Client {

    public static final String NAME = "ClientBase";
    public static final String VERSION = "1.0.0";
    public static final ClientEnvironment ENV = ClientEnvironment.RELEASE;

    /**
     * The client logger. Used to print things to stdout
     */
    public static final Logger logger = LogManager.getLogger(NAME);

    /**
     * An event bus for posting and receiving events
     *
     * You can use whatever event bus you'd like, but I like bush's, so I'll
     * be using this event bus.
     */
    public static final EventBus BUS = new EventBus(logger::info);

    // below, is all of our managers we will use
    public static ModuleManager moduleManager;
    public static CommandManager commandManager;
    public static RotationManager rotationManager;
    public static ScriptManager scriptManager;

    /**
     * The main method we invoke to start the client
     *
     * In Minecraft#startGame()
     */
    public static void init() {

        // debugging purposes, make sure this method was called in startGame
        logger.info("{} is loading...", NAME);

        // create manager instances
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        rotationManager = new RotationManager();
        scriptManager = new ScriptManager();
    }

    public enum ClientEnvironment {
        /**
         * A stable release, this is the state the client should be in when you
         * release the client wherever
         */
        RELEASE("rel"),

        /**
         * The client is in a beta testing phase, you'd give this to
         * client testers
         */
        BETA("beta"),

        /**
         * A release only the developers should have access to
         */
        DEVELOPER("dev");

        public final String displayName;

        ClientEnvironment(String displayName) {
            this.displayName = displayName;
        }
    }
}
