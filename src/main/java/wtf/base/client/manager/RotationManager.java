package wtf.base.client.manager;

import me.bush.eventbus.annotation.EventListener;
import wtf.base.client.Client;
import wtf.base.client.event.Era;
import wtf.base.client.event.moving.MotionUpdateEvent;
import wtf.base.client.util.common.ClientImpl;
import wtf.base.client.util.player.rotation.Rotation;

/**
 * Manages the client's rotations
 */
public class RotationManager implements ClientImpl {
    private final Rotation rotation = Rotation.INVALID;
    private long lastRotation = 0L;

    public RotationManager() {
        Client.BUS.subscribe(this);
    }

    @EventListener
    public void onMotionUpdate(MotionUpdateEvent event) {
        if (rotation.isValid()) {

            if (event.era.equals(Era.PRE)) {

                // see if we should invalidate our rotations
                if (System.currentTimeMillis() - lastRotation >= 100L) {
                    rotation.invalidate();

                    return;
                } else {
                    event.yaw = rotation.getYaw();
                    event.pitch = rotation.getPitch();
                }
            }

            mc.thePlayer.rotationYawHead = rotation.getYaw();
            mc.thePlayer.renderYawOffset = rotation.getYaw();
        }
    }

    public void setRotation(Rotation rot) {
        lastRotation = System.currentTimeMillis();
        rotation.setYaw(rot.getYaw());
        rotation.setPitch(rot.getPitch());
    }

    public Rotation getRotation() {
        return rotation;
    }
}
