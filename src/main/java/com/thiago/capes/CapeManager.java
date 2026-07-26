package com.thiago.capes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CapeManager {

    public static final List<Cape> AVAILABLE_CAPES = new ArrayList<>();
    private static Cape selectedCape = null;

    public static void init() {
        AVAILABLE_CAPES.add(new Cape("Default", "assets/customcapes/textures/capes/default.png"));
        AVAILABLE_CAPES.add(new Cape("Fire",    "assets/customcapes/textures/capes/fire.png"));
        AVAILABLE_CAPES.add(new Cape("Galaxy",  "assets/customcapes/textures/capes/galaxy.png"));

        loadConfig();
    }

    public static ResourceLocation getCape(AbstractClientPlayer player) {
        if (isLocalPlayer(player) && selectedCape != null) {
            return selectedCape.resource;
        }
        return null;
    }

    public static boolean hasCape(AbstractClientPlayer player) {
        return getCape(player) != null;
    }

    public static void selectCape(Cape cape) {
        selectedCape = cape;
        saveConfig(cape.name);
    }

    public static void clearCape() {
        selectedCape = null;
        saveConfig("");
    }

    public static Cape getSelectedCape() {
        return selectedCape;
    }

    private static boolean isLocalPlayer(AbstractClientPlayer player) {
        Minecraft mc = Minecraft.getMinecraft();
        return mc.thePlayer != null &&
                player.getUniqueID().equals(mc.thePlayer.getUniqueID());
    }

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

    private static void loadConfig() {
        try {
            File file = new File(Minecraft.getMinecraft().mcDataDir, "customcapes.txt");
            if (!file.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String name = br.readLine();
            br.close();

            if (name != null && !name.isEmpty()) {
                for (Cape cape : AVAILABLE_CAPES) {
                    if (cape.name.equals(name)) {
                        selectedCape = cape;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}