public class CapeManager {

    // UUID do jogador → ResourceLocation da capa
    private static final Map<UUID, ResourceLocation> capes = new HashMap<>();
    private static ResourceLocation localCape;

    public static void init() {
        // Capa local do arquivo resources
        localCape = new ResourceLocation("capes", "textures/capes/minha_capa.png");

        // Registra sua capa para seu UUID
        String seuUUID = "seu-uuid-aqui"; // ex: "069a79f4-44e9-..."
        capes.put(UUID.fromString(seuUUID), localCape);
    }

    public static ResourceLocation getCape(AbstractClientPlayer player) {
        return capes.get(player.getUniqueID());
    }

    public static boolean hasCape(AbstractClientPlayer player) {
        return capes.containsKey(player.getUniqueID());
    }

    // Registra outro jogador que também tem o mod
    public static void registerPlayer(UUID uuid, ResourceLocation cape) {
        capes.put(uuid, cape);
    }
}