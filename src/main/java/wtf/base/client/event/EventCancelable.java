package wtf.base.client.event;

import me.bush.eventbus.event.Event;

public class EventCancelable extends Event {

    @Override
    protected boolean isCancellable() {
        return true;
    }
}
