package best.spaghetcodes.duckdueller.bot.player;

import best.spaghetcodes.duckdueller.DuckDueller;
import best.spaghetcodes.duckdueller.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Combat {
    private static final Minecraft mc = Minecraft.getMinecraft();

    private static boolean looking = false;

    private static boolean changingYawPositive = false;
    private static int changedYaw = -1;
    private static int changedYawMax = -1;
    private static int changeYawBy = 1;


    /**
     * Start looking at the opponent
     */
    public static void startLooking() {
        if (DuckDueller.INSTANCE.BOT.toggled) {
            looking = true;
        }
    }

    /**
     * Stop looking at the opponent
     */
    public static void stopLooking() {
        looking = false;
    }


    /**
     * Get the rotations needed to look at an entity
     * <br>
     * <i>Not originally my code, but I forgot where I found it.</i>
     * @param q entity
     * @param raw If true, only returns difference in yaw and pitch instead of values needed
     * @return float[] - {yaw, pitch}
     */
    public static float[] getRotations(Entity q, boolean raw) {
        if (q == null) {
            return null;
        } else {
            double diffX = q.posX - mc.thePlayer.posX;
            double diffY;
            if (q instanceof EntityLivingBase) {
                EntityLivingBase en = (EntityLivingBase) q;
                diffY = en.posY + (double) en.getEyeHeight() - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
            } else {
                diffY = (q.getEntityBoundingBox().minY + q.getEntityBoundingBox().maxY) / 2.0D - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
            }

            double diffZ = q.posZ - mc.thePlayer.posZ;
            double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
            float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
            float pitch = (float) (-(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D));
            if (raw) {
                return new float[]{MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw), MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch)};
            }
            return new float[]{mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw), mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch)};
        }
    }

    public static void onClientTick(TickEvent.ClientTickEvent ev) {
        if (mc.thePlayer != null && DuckDueller.INSTANCE.BOT.toggled && looking && DuckDueller.INSTANCE.BOT.opponent != null) {
            float[] rotations = getRotations(DuckDueller.INSTANCE.BOT.opponent, false);

            if (changedYaw == -1 && !changingYawPositive) {
                changedYawMax = Utils.randomIntInRange(-5, 5);
                changeYawBy = changedYawMax > 0 ? 1 : -1;
                changedYaw = 0;
                changingYawPositive = true;
            } else if (changingYawPositive) {
                changedYaw += changeYawBy;
                if (Math.abs(changedYaw) >= Math.abs(changedYawMax)) {
                    changingYawPositive = false;
                }
            } else {
                changedYaw -= changeYawBy;
            }

            mc.thePlayer.rotationYaw = rotations[0] + changedYaw;
            mc.thePlayer.rotationPitch = rotations[1]; // pitch is perfect screw you
        }
    }
}
