package best.spaghetcodes.duckdueller.bot.player;

import best.spaghetcodes.duckdueller.DuckDueller;
import best.spaghetcodes.duckdueller.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import java.util.Timer;

public class Mouse {
    private static final Minecraft mc = Minecraft.getMinecraft();

    // Flags for external use
    public static boolean leftClick = false;
    public static boolean rightClick = false;

    /**
     * Hold left click for duration ms
     * @param duration
     */
    public static void leftClick(int duration) {
        if (DuckDueller.INSTANCE.BOT.toggled) {
            lClickDown();
            Utils.runAfterTimeout(Mouse::lClickUp, duration);
        }
    }

    /**
     * Hold right click for duration ms
     * @param duration
     */
    public static void rightClick(int duration) {
        if (DuckDueller.INSTANCE.BOT.toggled) {
            rClickDown();
            Utils.runAfterTimeout(Mouse::rClickUp, duration);
        }
    }

    /**
     * Start lmb autoclicking after delay ms, with cps CPS
     * @param delay
     * @param cps
     */
    public static Timer leftClickAutoClicker(int delay, int cps) {
        if (DuckDueller.INSTANCE.BOT.toggled) {
            return Utils.setInterval(() -> {
                leftClick(1000/cps/2); // have the mouse button held for half the time
            }, delay, 1000/cps);
        }
        return null;
    }

    /**
     * Start rmb autoclicking after delay ms, with cps CPS
     * @param delay
     * @param cps
     */
    public static Timer rightClickAutoClicker(int delay, int cps) {
        if (DuckDueller.INSTANCE.BOT.toggled) {
            return Utils.setInterval(() -> {
                leftClick(1000/cps/2); // have the mouse button held for half the time
            }, delay, 1000/cps);
        }
        return null;
    }

    private static void lClickDown() {
        leftClick = true;
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
    }

    private static void lClickUp() {
        leftClick = false;
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
    }

    private static void rClickDown() {
        rightClick = true;
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
    }

    private static void rClickUp() {
        rightClick = false;
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
    }
}
