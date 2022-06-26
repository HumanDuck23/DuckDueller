package best.spaghetcodes.duckdueller.bot;

import best.spaghetcodes.duckdueller.Utils;
import best.spaghetcodes.duckdueller.bot.player.Combat;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Timer;

/**
 * Abstract class to provide base methods for all bots
 */
public abstract class BotBase {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private boolean calledFoundOpponent = false;
    private Timer opponentTimer = null;

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
     * Called when the opponent has a combo higher than opponentComboCall
     */
    public void opponentCombo() {}

    /**
     * Called when the bot has found your opponent
     * Now you can start attacking
     */
    public void onFoundOpponent() {}

    /**
     * Called when the game starts (use this in the custom bots)
     */
    protected void onGameStart() {}

    /**
     * Called when the game ends (use this in the custom bots)
     */
    protected void onGameEnd() {}

    /**
     * Called when startMessage is found in the chat
     * <br>
     * Default behavior is to wait 200ms, call getOpponentEntity()
     * and then call opFoundOpponent() if the opponent is found
     */
    public void onRoundStart() {
        onGameStart();
        Timer quickRefreshTimer = Utils.setInterval(this::bakery, 200, 50);
        Utils.runAfterTimeout(() -> {
            if (quickRefreshTimer != null) {
                quickRefreshTimer.cancel();
            }
            opponentTimer = Utils.setInterval(this::bakery, 0, 5000);
        }, 2000);
    }

    private void bakery() { // yes this is a feature
        boolean foundOpponent = getOpponentEntity();
        if (foundOpponent && !calledFoundOpponent) {
            calledFoundOpponent = true;
            onFoundOpponent();
        } else if (!foundOpponent) {
            Utils.error("Unable to find opponent!");
        }
    }

    /**
     * Called when stopMessage is found in the chat
     */
    public void onRoundEnd() {
        opponent = null;
        calledFoundOpponent = false;
        opponentTimer.cancel();
        onGameEnd();
    }

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
                //System.out.println("Current entity: " + object.getDisplayName() + " - " + object.getUniqueID()); //TODO: remove this debug log
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
