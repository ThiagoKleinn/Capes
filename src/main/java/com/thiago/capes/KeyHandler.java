package com.thiago.capes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class KeyHandler {

    public static final KeyBinding OPEN_GUI = new KeyBinding(
            "Open Cape Menu", Keyboard.KEY_P, "Custom Capes"
    );

    public static void register() {
        ClientRegistry.registerKeyBinding(OPEN_GUI);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        if (OPEN_GUI.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new CapeSelectionGui());
        }
    }
}