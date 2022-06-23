package best.spaghetcodes.duckdueller.bot;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Abstract class to provide base methods for all bots
 */
public abstract class BotBase {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public String startMessage;
    public String stopMessage;
    public String queueCommand;
    public int opponentComboCall;

    public boolean toggled = false;
    public boolean attacking = false;

    public EntityPlayer opponent;
    public static int maxDistanceLook = 200; // from what distance to start looking at the opponent
    public static int maxDistanceAttack = 15; // from what distance to start attacking the opponent
    public static int opponentCombo = 0;
    public static int botCombo = 0;

    public BotBase(String startMessage, String stopMessage, String queueCommand, int opponentComboCall) {
        this.startMessage = startMessage;
        this.stopMessage = stopMessage;
        this.queueCommand = queueCommand;
        this.opponentComboCall = opponentComboCall;
    }

    // Override these methods

    /**
     * Called when the bot lands a hit
     */
    public void onHit() {}

    /**
     * Called when the bot gets hit
     */
    public void onDamageTaken() {}

    /**
     * Called when the opponent has a combo higher than `opponentComboCall`
     */
    public void opponentCombo() {}

    /**
     * Called when the bot has found your opponent
     * Now you can start attacking
     */
    public void onFoundOpponent() {}

    // Base methods
    public void toggle() {
        toggled = !toggled;
    }

    public void stopAttacking() {
        attacking = false;
    }

    public boolean getOpponentEntity() {
        opponent = findEntity();
        return opponent != null;
    }

    private EntityPlayer findEntity() {
        if (mc.theWorld != null) {
            for (EntityPlayer object : mc.theWorld.playerEntities) {
                System.out.println("Current entity: " + object.getDisplayName() + " - " + object.getUniqueID());
                if (!object.getDisplayName().equals(mc.thePlayer.getDisplayName()) && this.shouldTarget(object)) {
                    return object;
                }
            }
        }
        return null;
    }

    private boolean shouldTarget(EntityPlayer entity) {
        if (entity == null) {
            return false;
        } else if (mc.thePlayer.isEntityAlive() && entity.isEntityAlive()) {
            if (!entity.isInvisible() /*&& !entity.isInvisibleToPlayer(mc.thePlayer)*/) {
                if (mc.thePlayer.getDistanceToEntity(entity) > 64.0F) {
                    return false;
                } else {
                    return mc.thePlayer.canEntityBeSeen(entity);
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
