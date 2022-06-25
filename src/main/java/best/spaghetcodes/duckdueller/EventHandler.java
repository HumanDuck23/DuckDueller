package best.spaghetcodes.duckdueller;

import best.spaghetcodes.duckdueller.bot.player.Combat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class EventHandler {
    private KeyBinding toggleBot = new KeyBinding("Toggle Bot", Keyboard.KEY_N, "key.categories.misc");

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent ev) {
        Combat.onClientTick(ev);
        if (toggleBot.isPressed()) {
            DuckDueller.INSTANCE.BOT.toggle();
        }
    }

    @SubscribeEvent
    public void onClientChatReceivedEvent(ClientChatReceivedEvent event) {
        String unformatted = event.message.getUnformattedText();
        if (unformatted.contains(DuckDueller.INSTANCE.BOT.startMessage)) {
            DuckDueller.INSTANCE.BOT.onRoundStart();
        } else if (unformatted.contains(DuckDueller.INSTANCE.BOT.stopMessage)) {
            DuckDueller.INSTANCE.BOT.onRoundEnd();
        }
    }
}
