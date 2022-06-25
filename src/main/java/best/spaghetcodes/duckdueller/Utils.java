package best.spaghetcodes.duckdueller;

import best.spaghetcodes.duckdueller.interfaces.VoidNoArgFuncInterface;
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
