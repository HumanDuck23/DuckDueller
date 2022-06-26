package best.spaghetcodes.duckdueller.bot.player;

import best.spaghetcodes.duckdueller.DuckDueller;
import best.spaghetcodes.duckdueller.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import java.util.Timer;

public class Mouse {
    private static final Minecraft mc = Minecraft.getMinecraft();

    // Flags for external use
    public static boolean rightClick = false;

    /**
     * Left Click
     */
    public static void leftClick() {
        if (DuckDueller.INSTANCE.BOT.toggled) {
            KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());
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
            Utils.setInterval(() -> {
                int minDelay = 1000/cps/3;
                int maxDelay = minDelay * 2;
                Utils.runAfterTimeout(Mouse::leftClick, Utils.randomIntInRange(minDelay, maxDelay));
            }, 0, 1000/cps);
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
                rightClick(1000/cps/2); // have the mouse button held for half the time
            }, delay, 1000/cps);
        }
        return null;
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
