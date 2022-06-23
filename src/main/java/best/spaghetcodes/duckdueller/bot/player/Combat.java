package best.spaghetcodes.duckdueller.bot.player;

import best.spaghetcodes.duckdueller.DuckDueller;
import best.spaghetcodes.duckdueller.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class Combat {
    private static final Minecraft mc = Minecraft.getMinecraft();

    private static boolean lookingLoop = false; // used to start/stop looking at the opponent
    private static int lookingInterval = 150;

    public static void startLooking(int lookingInterval) {
        if (DuckDueller.INSTANCE.BOT.toggled) {
            Combat.lookingInterval = lookingInterval;
            lookingLoop = true;
        }
    }

    private static void updateLooking() {
        if (DuckDueller.INSTANCE.BOT.opponent != null) {
            // TODO: make smooth transitions between positions
            float[] rotations = getRotations(DuckDueller.INSTANCE.BOT.opponent);
            mc.thePlayer.rotationYaw = rotations[0];
            mc.thePlayer.rotationPitch = rotations[1];
        } else {
            Utils.error("Error looking at opponent: NULL");
        }
        if (lookingLoop) {
            Utils.runAfterTimeout(Combat::updateLooking, lookingInterval);
        }
    }

    /**
     * Get the rotations needed to look at an entity
     * <br>
     * <i>Not originally my code, but I forgot where I found it.</i>
     * @param q entity
     * @return float[] - {yaw, pitch}
     */
    public static float[] getRotations(Entity q) {
        if (q == null) {
            return null;
        } else {
            double diffX = q.posX - mc.thePlayer.posX;
            double diffY;
            if (q instanceof EntityLivingBase) {
                EntityLivingBase en = (EntityLivingBase) q;
                diffY = en.posY + (double) en.getEyeHeight() * 0.9D - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
            } else {
                diffY = (q.getEntityBoundingBox().minY + q.getEntityBoundingBox().maxY) / 2.0D - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
            }

            double diffZ = q.posZ - mc.thePlayer.posZ;
            double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
            float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
            float pitch = (float) (-(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D));
            return new float[]{mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw), mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch)};
        }
    }
}
