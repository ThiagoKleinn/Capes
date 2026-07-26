@Mod(modid = "customcapes", name = "Custom Capes", version = "1.0",
        clientSideOnly = true)
public class CapesMod {

    public static final String MODID = "customcapes";

    @Mod.Instance
    public static CapesMod instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new CapeRenderer());
        CapeManager.init();
    }
}