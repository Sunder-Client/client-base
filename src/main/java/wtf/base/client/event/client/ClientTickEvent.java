package wtf.base.client.event.client;

import me.bush.eventbus.event.Event;
import wtf.base.client.event.Era;

/**
 * Posted to the event bus when the minecraft client ticks
 *
 * See Minecraft#runTick
 */
public class ClientTickEvent extends Event {
    private final Era era;

    public ClientTickEvent(Era era) {
        this.era = era;
    }

    public Era getEra() {
        return era;
    }

    @Override
    protected boolean isCancellable() {
        return false;
    }
}
