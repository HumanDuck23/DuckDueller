package best.spaghetcodes.duckdueller;

import best.spaghetcodes.duckdueller.interfaces.VoidNoArgFuncInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Duck Dueller utility class
 */
public class Utils {
    private static final Minecraft mc = Minecraft.getMinecraft();

    /**
     * Run a function after x ms
     * @param func - Function to be run
     * @param delay - Delay to run the function after (ms)
     */
    public static void runAfterTimeout(final VoidNoArgFuncInterface func, int delay) {
        try {
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            func.operation();
                        }
                    }, delay);
        } catch (Exception e) {
            System.out.println("Error running timer after " + delay + "ms: " + e.getMessage());
        }
    }

    /**
     * Run a function every x ms
     * @param func Function to be run
     * @param delay Initial delay
     * @param interval Interval between function calls
     * @return Timer
     */
    public static Timer setInterval(final VoidNoArgFuncInterface func, int delay, int interval) {
        try {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    func.operation();
                }
            }, delay, interval);
            return timer;
        } catch (Exception e) {
            System.out.println("Error running interval after " + delay + "ms: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get a random integer in a certain range
     * @param min
     * @param max
     * @return int
     */
    public static int randomIntInRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    /**
     * Get a random double in a certain range
     * @param min
     * @param max
     * @return double
     */
    public static double randomDoubleInRange(double min, double max) {
        Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }

    /**
     * Get a random boolean value
     * @return bool
     */
    public static boolean randomBool() {
        Random r = new Random();
        return r.nextBoolean();
    }

    /**
     * Calculate the distance to an entity
     * @param entity the entity you want to calculate the distance to
     * @return double
     */
    public static double getDistanceToEntity(Entity entity) {
        if (mc.thePlayer != null && entity != null)  {
            double deltaX = entity.posX - mc.thePlayer.posX;
            double deltaY = entity.posY - mc.thePlayer.posY;
            double deltaZ = entity.posZ - mc.thePlayer.posZ;

            return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY) + (deltaZ * deltaZ));
        }
        return -1;
    }

    public static boolean isAirInFront(BlockPos playerPos) {
        for(int x = -2; x < 3; ++x) {
            for(int z = -2; z < 3; ++z) {
                if (x != -1 || x != 0 || x != 1 || z != -1 || z != 0 || z != 1) {
                    BlockPos pos = new BlockPos((double)(playerPos.getX() + x), 64.5, (double)(playerPos.getZ() + z));
                    if (mc.theWorld.getBlockState(pos).getBlock() == Blocks.air) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Send an info message to the player
     * @param text
     */
    public static void info(String text) {
        makeMessage(text, EnumChatFormatting.BLUE);
    }

    /**
     * Send an error message to the player
     * @param text
     */
    public static void error(String text) {
        makeMessage(text, EnumChatFormatting.RED);
    }

    public static void makeMessage(String text, EnumChatFormatting color) {
        try {
            DuckDueller.INSTANCE.mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.BOLD + "" + EnumChatFormatting.WHITE + "[" + color + DuckDueller.MOD_NAME + EnumChatFormatting.WHITE + "] " + text));
        } catch (Exception ignored) {
            // ---
        }
    }
}
