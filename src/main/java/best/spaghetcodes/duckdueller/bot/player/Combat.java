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

    private static boolean lookingLoop = false; // used to start/stop looking at the opponent
    private static int lookingInterval = 150;

    // player tracking system: bot rotates with a fixed speed (will stutter a bit, not constant)
    // every tick it will increase/decrease the yaw/pitch by lookSpeed depending on the two bools
    private static float lookSpeedYaw = 0.1f;
    private static float lookSpeedPitch = 0.1f;
    private static float[] rotationsNeeded;

    /**
     * Start looking at the opponent
     * @param lookingInterval Time between position updates
     * @param lookSpeed How fast the bot will look around
     */
    public static void startLooking(int lookingInterval, float lookSpeed) {
        if (DuckDueller.INSTANCE.BOT.toggled) {
            Combat.lookingInterval = lookingInterval;
            Combat.lookSpeedYaw = lookSpeed;
            Combat.lookSpeedPitch = 0.1f;
            lookingLoop = true;
            Utils.runAfterTimeout(Combat::updateLooking, lookingInterval);
        }
    }

    private static void updateLooking() {
        if (DuckDueller.INSTANCE.BOT.opponent != null) {
            // TODO: make smooth transitions between positions
            rotationsNeeded = getRotations(DuckDueller.INSTANCE.BOT.opponent, true);
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
        if (mc.thePlayer != null && DuckDueller.INSTANCE.BOT.toggled && lookingLoop && rotationsNeeded != null && rotationsNeeded.length == 2) {
            if (Math.abs(rotationsNeeded[0]) > 40) {
                if (rotationsNeeded[0] > 0) {
                    mc.thePlayer.rotationYaw += lookSpeedYaw;
                } else {
                    mc.thePlayer.rotationYaw -= lookSpeedYaw;
                }
            } else if (Math.abs(rotationsNeeded[0]) > 20) {
                if (rotationsNeeded[0] > 0) {
                    mc.thePlayer.rotationYaw += 2.5;
                } else {
                    mc.thePlayer.rotationYaw -= 2.5;
                }
            } else if (Math.abs(rotationsNeeded[0]) > 5) {
                if (rotationsNeeded[0] > 0) {
                    mc.thePlayer.rotationYaw += 1.75;
                } else {
                    mc.thePlayer.rotationYaw -= 1.75;
                }
            }


            if (rotationsNeeded[1] > 0) {
                mc.thePlayer.rotationPitch += lookSpeedPitch;
            } else {
                mc.thePlayer.rotationPitch -= lookSpeedPitch;
            }
        }
    }
}
