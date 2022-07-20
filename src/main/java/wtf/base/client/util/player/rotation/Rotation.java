package wtf.base.client.util.player.rotation;

public class Rotation {
    public static final Rotation INVALID = new Rotation(Float.NaN, Float.NaN);

    private float yaw, pitch;

    public Rotation(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public boolean isValid() {
        return !Float.isNaN(yaw) && !Float.isNaN(pitch);
    }

    public void invalidate() {
        yaw = Float.NaN;
        pitch = Float.NaN;
    }
}
