package best.spaghetcodes.duckdueller.bot.player;

import best.spaghetcodes.duckdueller.Utils;
import best.spaghetcodes.duckdueller.bot.BotBase;

public class SumoBot extends BotBase {
    public SumoBot() {
        super("Opponent:", "Accuracy", "/play duels_sumo_duel", 10);
    }

    @Override
    public void onFoundOpponent() {
        Utils.info("Opponent found! Name: " + opponent.getDisplayNameString());
    }
}
