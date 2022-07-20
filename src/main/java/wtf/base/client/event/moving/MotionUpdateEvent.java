package wtf.base.client.event.moving;

import wtf.base.client.event.Era;
import wtf.base.client.event.EventCancelable;

/**
 * Posted when EntityPlayerSP#onUpdateWalkingPlayer is called
 *
 * PRE will allow you to edit whatever is being sent to the server
 * POST is after sending movement/player state packets
 *
 * Sprinting and Sneaking are sent BEFORE position/rotation packets, and will flag
 * on some anti-cheats if you send sprint/sneak packets after movement packets are sent
 */
public class MotionUpdateEvent extends EventCancelable {
    public final Era era;

    public double x, y, z;
    public float yaw, pitch;
    public boolean onGround;

    public MotionUpdateEvent() {
        this.era = Era.POST;
    }

    public MotionUpdateEvent(double x, double y, double z, float yaw, float pitch, boolean onGround) {
        this.era = Era.PRE;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }
}
