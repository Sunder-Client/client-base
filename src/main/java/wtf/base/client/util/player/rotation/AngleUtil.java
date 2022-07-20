package wtf.base.client.util.player.rotation;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import wtf.base.client.util.common.ClientImpl;

/**
 * Calculates rotations and does things with angles
 *
 * Fun math stuff...
 */
public class AngleUtil implements ClientImpl {
    public static Rotation calcRotationToEntity(Entity entity, Bone bone) {
        Vec3 targetEyes = getEyes(entity, bone);
        Vec3 localEyes = getEyes(mc.thePlayer, Bone.HEAD);

        Vec3 diff = diffIn(targetEyes, localEyes);

        double distance = MathHelper.sqrt_double(diff.xCoord * diff.xCoord + diff.zCoord * diff.zCoord);

        float yaw = (float) (Math.atan2(diff.zCoord, diff.xCoord) * 180.0 / Math.PI - 90.0);

        if (yaw < 0.0f) {
            yaw += 360.0f;
        }

        float pitch = (float) (Math.atan2(-diff.yCoord, distance) * 180.0f / Math.PI);

        return new Rotation(yaw, pitch);
    }

    public static Vec3 diffIn(Vec3 from, Vec3 to) {
        return new Vec3(from.xCoord - to.xCoord, from.yCoord - to.yCoord, from.zCoord - to.zCoord);
    }

    private static Vec3 getEyes(Entity entity, Bone bone) {
        double y = interpolate(entity.posY, entity.lastTickPosY);

        switch (bone) {
            case HEAD:
                y += entity.getEyeHeight();
                break;

            case CHEST:
                y += (entity.getEyeHeight() / 2.0f) + 0.35;
                break;

            case LEGS:
                y += entity.getEyeHeight() / 2.0f;
                break;

            case FEET:
                y += 0.5;
                break;
        }

        return new Vec3(
                interpolate(entity.posX, entity.lastTickPosX),
                y,
                interpolate(entity.posZ, entity.lastTickPosZ));
    }

    public static double interpolate(double start, double end) {
        float partialRenderTicks = mc.timer.renderPartialTicks;
        if (partialRenderTicks == 1.0f) {
            return start;
        }

        return end + (start - end) * partialRenderTicks;
    }
}
