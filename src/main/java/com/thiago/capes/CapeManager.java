package com.thiago.capes;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CapeManager {

    private static final Map<UUID, ResourceLocation> capes = new HashMap<>();

    public static void init() {
        // Adicione seu UUID e capa aqui
        register("SEU-UUID-AQUI", new ResourceLocation("customcapes", "textures/capes/minha_capa.png"));
    }

    public static void register(String uuid, ResourceLocation cape) {
        capes.put(UUID.fromString(uuid), cape);
    }

    public static boolean hasCape(AbstractClientPlayer player) {
        return capes.containsKey(player.getUniqueID());
    }

    public static ResourceLocation getCape(AbstractClientPlayer player) {
        return capes.get(player.getUniqueID());
    }
}