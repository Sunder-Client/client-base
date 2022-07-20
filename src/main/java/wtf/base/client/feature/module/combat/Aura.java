package wtf.base.client.feature.module.combat;

import me.bush.eventbus.annotation.EventListener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.lwjgl.input.Keyboard;
import wtf.base.client.Client;
import wtf.base.client.event.Era;
import wtf.base.client.event.client.ClientTickEvent;
import wtf.base.client.event.moving.MotionUpdateEvent;
import wtf.base.client.feature.module.Module;
import wtf.base.client.feature.module.ModuleCategory;
import wtf.base.client.feature.setting.Setting;
import wtf.base.client.util.common.MathUtil;
import wtf.base.client.util.player.rotation.AngleUtil;
import wtf.base.client.util.player.rotation.Bone;
import wtf.base.client.util.player.rotation.Rotation;

import java.util.Comparator;

public class Aura extends Module {
    public Aura() {
        super("Aura", ModuleCategory.COMBAT);
        setBind(Keyboard.KEY_K);
    }

    public final Setting<Mode> mode = setting("Mode", Mode.SINGLE);
    public final Setting<Priority> priority = setting("Priority", Priority.DISTANCE);

    public final Setting<Integer> min = intSetting("Min CPS", 9, 1, 20);
    public final Setting<Integer> max = intSetting("Max CPS", 14, 1, 20);

    public final Setting<Double> range = doubleSetting("Range", 4.0, 1.0, 6.0);
    public final Setting<Double> wallRange = doubleSetting("Wall Range", 2.5, 1.0, 6.0);
    public final Setting<AutoBlock> autoBlock = setting("Auto Block", AutoBlock.VANILLA);

    private EntityLivingBase target = null;
    private long lastAttack = 0L;
    private boolean blocking = false;

    @Override
    protected void onDeactivated() {
        target = null;
        lastAttack = 0L;

        if (blocking) {
            mc.playerController.onStoppedUsingItem(mc.thePlayer);
        }
    }

    @EventListener
    public void onClientTick(ClientTickEvent event) {
        if (mode.getValue().equals(Mode.SWITCH) || isEntityInvalid(target)) {

            Entity newTarget = mc.theWorld.loadedEntityList
                    .stream()
                    .filter((e) -> e instanceof EntityLivingBase && !isEntityInvalid((EntityLivingBase) e))
                    .min(Comparator.comparingDouble((s) -> {
                        double value = 0.0;

                        switch (priority.getValue()) {
                            case DISTANCE:
                                value = mc.thePlayer.getDistanceSqToEntity(s);
                                break;

                            case HEALTH:
                                value = ((EntityLivingBase) s).getHealth();
                                break;
                        }

                        return -value;
                    }))
                    .orElse(null);

            if (newTarget != null) {
                target = (EntityLivingBase) newTarget;
            } else target = null;
        }
    }

    @EventListener
    public void onMotionUpdate(MotionUpdateEvent event) {
        if (target != null) {
            Rotation rotation = AngleUtil.calcRotationToEntity(target, Bone.HEAD);
            if (rotation.isValid()) {
                event.yaw = rotation.getYaw();
                event.pitch = rotation.getPitch();

                Client.rotationManager.setRotation(rotation);
            }

            boolean holdingSword = mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;

            if (!autoBlock.getValue().equals(AutoBlock.NONE) && holdingSword) {
                mc.playerController.fakeBlock();
            }

            if (event.era.equals(Era.PRE)) {

                if (!blocking && !autoBlock.getValue().equals(AutoBlock.NONE)) {

                    // obviously you'll need to add more modes
                    switch (autoBlock.getValue()) {
                        case VANILLA:
                            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
                                    C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));

                            blocking = false;
                            break;
                    }
                }

                if (blocking && !holdingSword) {
                    mc.playerController.onStoppedUsingItem(mc.thePlayer);
                    blocking = false;
                }

                if (System.currentTimeMillis() - lastAttack >= 1000L / MathUtil.random(min.getValue(), max.getValue())) {
                    lastAttack = System.currentTimeMillis();

                    mc.thePlayer.swingItem();
                    mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, Action.ATTACK));
                }

            } else {
                if (!blocking && !autoBlock.getValue().equals(AutoBlock.NONE)) {
                    if (!holdingSword) {
                        blocking = false;
                        return;
                    }

                    switch (autoBlock.getValue()) {
                        case VANILLA:
                            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem());
                            blocking = true;
                            break;
                    }
                }
            }
        }
    }

    private boolean isEntityInvalid(EntityLivingBase t) {
        if (t == null || t.equals(mc.thePlayer) || t.isDead || t.getHealth() <= 0.0f) {
            return true;
        }

        double distance = mc.thePlayer.getDistanceSqToEntity(t);

        double ranges = range.getValue() * range.getValue();
        if (!mc.thePlayer.canEntityBeSeen(t)) {
            ranges = wallRange.getValue() * wallRange.getValue();
        }

        return distance > ranges;
    }

    public enum Mode {
        SINGLE, SWITCH
    }

    public enum Priority {
        DISTANCE, HEALTH
    }

    public enum AutoBlock {
        NONE, VANILLA, FAKE
    }
}
