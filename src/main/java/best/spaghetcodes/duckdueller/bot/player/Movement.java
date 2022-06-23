package best.spaghetcodes.duckdueller.bot.player;

import best.spaghetcodes.duckdueller.DuckDueller;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class Movement {
    private static final Minecraft mc = Minecraft.getMinecraft();

    // Flags for external use
    public static boolean forward = false;
    public static boolean backward = false;
    public static boolean left = false;
    public static boolean right = false;
    public static boolean jumping = false;
    public static boolean sprinting = false;

    public static void startForward() {
        if (DuckDueller.INSTANCE.BOT.toggled) {
            forward = true;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
        }
    }

    public static void startBackward() {
        if (DuckDueller.INSTANCE.BOT.toggled) {
            backward = true;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
        }
    }

    public static void startLeft() {
        if (DuckDueller.INSTANCE.BOT.toggled) {
            left = true;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), true);
        }
    }

    public static void startRight() {
        if (DuckDueller.INSTANCE.BOT.toggled) {
            right = true;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), true);
        }
    }

    public static void startSprinting() {
        if (DuckDueller.INSTANCE.BOT.toggled) {
            sprinting = true;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
        }
    }

    public static void startJumping() {
        if (DuckDueller.INSTANCE.BOT.toggled) {
            jumping = true;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), true);
        }
    }

    public static void stopForward() {
        forward = false;
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
    }

    public static void stopBackward() {
        backward = false;
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), false);
    }

    public static void stopLeft() {
        left = false;
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), false);
    }

    public static void stopRight() {
        right = false;
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), false);
    }

    public static void stopSprinting() {
        sprinting = false;
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
    }


    public static void stopJumping() {
        jumping = false;
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
    }
}