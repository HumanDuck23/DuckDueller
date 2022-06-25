package best.spaghetcodes.duckdueller.bot;

import best.spaghetcodes.duckdueller.Utils;
import best.spaghetcodes.duckdueller.bot.BotBase;
import best.spaghetcodes.duckdueller.bot.player.Combat;

public class SumoBot extends BotBase {
    public SumoBot() {
        super("Opponent:", "Accuracy", "/play duels_sumo_duel", 10);
    }

    @Override
    public void onFoundOpponent() {
        Utils.info("Opponent found! Name: " + opponent.getDisplayNameString());
        Combat.startLooking(50, 20f);
    }
}
