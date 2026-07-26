package com.thiago.capes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Map;

public class CapeRenderer {

    private boolean injected = false;

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (injected) return;

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null) return;

        injectCapeLayer(mc.getRenderManager());
        injected = true;
    }

    private void injectCapeLayer(RenderManager rm) {
        for (Map.Entry<String, RenderPlayer> entry : rm.getSkinMap().entrySet()) {
            RenderPlayer renderer = entry.getValue();

            try {
                java.lang.reflect.Field field = net.minecraft.client.renderer.entity.RendererLivingEntity.class
                        .getDeclaredField("layerRenderers");
                field.setAccessible(true);

                @SuppressWarnings("unchecked")
                java.util.List<LayerRenderer<?>> layers = (java.util.List<LayerRenderer<?>>) field.get(renderer);
                layers.removeIf(layer -> layer instanceof net.minecraft.client.renderer.entity.layers.LayerCape);
            } catch (Exception e) {
                e.printStackTrace();
            }

            renderer.addLayer(new CustomCapeLayer(renderer));
        }
    }
}