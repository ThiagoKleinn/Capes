package com.thiago.capes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class CapeManager {

    // Capas disponíveis no mod (nome → ResourceLocation)
    public static final Map<String, ResourceLocation> AVAILABLE_CAPES = new LinkedHashMap<>();

    // Capa selecionada pelo jogador local
    private static ResourceLocation selectedCape = null;

    public static void init() {
        // Registre aqui todas as capas do mod
        AVAILABLE_CAPES.put("Default",  new ResourceLocation("customcapes", "textures/capes/default.png"));
        AVAILABLE_CAPES.put("Fire",     new ResourceLocation("customcapes", "textures/capes/fire.png"));
        AVAILABLE_CAPES.put("Galaxy",   new ResourceLocation("customcapes", "textures/capes/galaxy.png"));

        loadConfig();
    }

    // Retorna a capa do jogador local, ou null se nenhuma selecionada
    public static ResourceLocation getCape(AbstractClientPlayer player) {
        if (isLocalPlayer(player)) {
            return selectedCape;
        }
        return null;
    }

    public static boolean hasCape(AbstractClientPlayer player) {
        return getCape(player) != null;
    }

    public static void selectCape(String name) {
        selectedCape = AVAILABLE_CAPES.get(name);
        saveConfig(name);
    }

    public static void clearCape() {
        selectedCape = null;
        saveConfig("");
    }

    private static boolean isLocalPlayer(AbstractClientPlayer player) {
        Minecraft mc = Minecraft.getMinecraft();
        return mc.thePlayer != null &&
                player.getUniqueID().equals(mc.thePlayer.getUniqueID());
    }

    // Salva a capa escolhida em .minecraft/customcapes.txt
    private static void saveConfig(String name) {
        try {
            File file = new File(Minecraft.getMinecraft().mcDataDir, "customcapes.txt");
            PrintWriter pw = new PrintWriter(new FileWriter(file));
            pw.println(name);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Carrega a capa salva
    private static void loadConfig() {
        try {
            File file = new File(Minecraft.getMinecraft().mcDataDir, "customcapes.txt");
            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String name = br.readLine();
            br.close();

            if (name != null && !name.isEmpty() && AVAILABLE_CAPES.containsKey(name)) {
                selectedCape = AVAILABLE_CAPES.get(name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}