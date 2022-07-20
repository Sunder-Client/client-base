package wtf.base.client.util.player.motion;

import wtf.base.client.util.common.ClientImpl;

public class MotionUtil implements ClientImpl {
    public static void setMotion(double speed) {
        float yaw = getMovementYaw();

        double sin = -Math.sin(yaw);
        double cos = Math.cos(yaw);

        mc.thePlayer.motionX = sin * speed;
        mc.thePlayer.motionZ = cos * speed;
    }

    public static void resetMotion() {
        mc.thePlayer.motionX = 0.0;
        mc.thePlayer.motionZ = 0.0;
    }

    public static float getMovementYaw() {
        float rotationYaw = mc.thePlayer.rotationYaw;
        float n = 1.0f;

        if (mc.thePlayer.movementInput.moveForward < 0.0f) {
            rotationYaw += 180.0f;
            n = -0.5f;
        } else if (mc.thePlayer.movementInput.moveForward > 0.0f) {
            n = 0.5f;
        }

        if (mc.thePlayer.movementInput.moveStrafe > 0.0f) {
            rotationYaw -= 90.0f * n;
        }

        if (mc.thePlayer.movementInput.moveStrafe < 0.0f) {
            rotationYaw += 90.0f * n;
        }

        return rotationYaw * 0.017453292f;
    }

    public static boolean isMoving() {
        return mc.thePlayer.movementInput.moveForward != 0.0f || mc.thePlayer.movementInput.moveStrafe != 0.0f;
    }
}
