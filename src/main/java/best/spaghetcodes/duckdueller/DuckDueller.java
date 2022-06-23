package best.spaghetcodes.duckdueller;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = DuckDueller.MOD_ID, name = DuckDueller.MOD_NAME, version = DuckDueller.MOD_VERSION)
public class DuckDueller {
    // Mod Info
    public static final String MOD_ID = "duckdueller";
    public static final String MOD_VERSION = "0.1.0";
    public static final String MOD_NAME = "Duck Dueller";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("Duck Dueller v" + MOD_VERSION + " is initializing...");
    }
}
