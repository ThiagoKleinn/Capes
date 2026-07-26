package com.thiago.capes;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.minecraft.entity.player.EnumPlayerModelParts;

import java.lang.reflect.Field;

public class CustomCapeLayer extends LayerCape {

    private final RenderPlayer playerRenderer;

    public CustomCapeLayer(RenderPlayer playerRenderer) {
        super(playerRenderer);
        this.playerRenderer = playerRenderer;
    }

    @Override
    public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float limbSwingAmount,
                              float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

        if (!player.hasPlayerInfo()) return;
        if (!player.isWearing(EnumPlayerModelParts.CAPE)) return;

        if (CapeManager.hasCape(player)) {
            try {
                Field capeField = AbstractClientPlayer.class.getDeclaredField("field_175157_a");
                capeField.setAccessible(true);
                Object original = capeField.get(player);
                capeField.set(player, CapeManager.getCape(player));
                super.doRenderLayer(player, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
                capeField.set(player, original);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            super.doRenderLayer(player, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }
}