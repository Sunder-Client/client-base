package wtf.base.client.event.input;

import me.bush.eventbus.event.Event;

/**
 * A simple event that will be posted to the event bus when a key is pressed
 *
 * See Minecraft#runTick
 */
public class KeyInputEvent extends Event {
    private final int keyCode;
    private final boolean keyState;

    public KeyInputEvent(int keyCode, boolean keyState) {
        this.keyCode = keyCode;
        this.keyState = keyState;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public boolean isKeyState() {
        return keyState;
    }

    @Override
    protected boolean isCancellable() {
        return false;
    }
}
