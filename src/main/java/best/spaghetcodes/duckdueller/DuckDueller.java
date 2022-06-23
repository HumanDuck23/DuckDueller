package best.spaghetcodes.duckdueller;

import best.spaghetcodes.duckdueller.bot.BotBase;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = DuckDueller.MOD_ID, name = DuckDueller.MOD_NAME, version = DuckDueller.MOD_VERSION)
public class DuckDueller {
    // Mod Info
    public static final String MOD_ID = "duckdueller";
    public static final String MOD_VERSION = "0.1.0";
    public static final String MOD_NAME = "Duck Dueller";

    // References and stuff
    public static DuckDueller INSTANCE = new DuckDueller();
    public final Minecraft mc = Minecraft.getMinecraft();
    public BotBase BOT;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("Duck Dueller v" + MOD_VERSION + " is initializing...");
    }
}
