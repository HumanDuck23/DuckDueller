package best.spaghetcodes.duckdueller.bot.player;

import net.minecraft.client.Minecraft;

public class Inventory {
    private static final Minecraft mc = Minecraft.getMinecraft();

    /**
     * Sets the players current item to the item passed
     * @param item The display name of the item
     * @return true on success, false on fail
     */
    public static boolean setInvItem(String item) {
        item = item.toLowerCase();
        for (int i = 0; i < 9; i++) {
            try {
                if (mc.thePlayer.inventory.getCurrentItem().getDisplayName().toLowerCase().contains(item)) {
                    return true;
                } else {
                    mc.thePlayer.inventory.changeCurrentItem(-1);
                }
            } catch (Exception e) {
                mc.thePlayer.inventory.changeCurrentItem(-1);
            }
        }
        return false;
    }

    public static void setInvSlot(int slot) {
        if (!(slot < 0 || slot > 9)) {
            for (int i = 0; i < 9; i++) {
                if (mc.thePlayer.inventory.currentItem > slot) {
                    mc.thePlayer.inventory.changeCurrentItem(-1);
                }
            }
        }
        // bruh
    }
}
