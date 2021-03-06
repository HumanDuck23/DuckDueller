package best.spaghetcodes.duckdueller;

import best.spaghetcodes.duckdueller.bot.player.Combat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class EventHandler {
    private KeyBinding toggleBot = new KeyBinding("Toggle Bot", Keyboard.KEY_N, "key.categories.misc");
    private final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent ev) {
        Combat.onClientTick(ev);
        if (DuckDueller.INSTANCE.BOT.toggled) {
            DuckDueller.INSTANCE.BOT.onTick();
        }
        if (toggleBot.isPressed()) {
            DuckDueller.INSTANCE.BOT.toggle();
            if (DuckDueller.INSTANCE.BOT.toggled) {
                Utils.info("Duck Dueller has been toggled on!");
            } else {
                Utils.info("Duck Dueller has been toggled off!");
            }
        }

        if (mc.thePlayer != null && mc.thePlayer.maxHurtTime > 0 && mc.thePlayer.hurtTime == mc.thePlayer.maxHurtTime) {
            DuckDueller.INSTANCE.BOT.onDamageTaken();
        }
    }

    @SubscribeEvent
    public void onAttackEntityEvent(AttackEntityEvent ev) {
        if (ev.entity == mc.thePlayer) {
            DuckDueller.INSTANCE.BOT.onHit();
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
