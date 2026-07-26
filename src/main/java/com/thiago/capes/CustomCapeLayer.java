package com.thiago.capes;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.minecraft.entity.player.EnumPlayerModelParts;

import java.lang.reflect.Field;

public class CustomCapeLayer extends LayerCape {

    public CustomCapeLayer(RenderPlayer playerRenderer) {
        super(playerRenderer);
    }

    @Override
    public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float limbSwingAmount,
                              float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

        if (!player.hasPlayerInfo()) return;
        if (!player.isWearing(EnumPlayerModelParts.CAPE)) return;
        if (!CapeManager.hasCape(player)) return;

        try {
            // Injeta a textura no campo da capa
            Field capeField = AbstractClientPlayer.class.getDeclaredField("field_175157_a");
            capeField.setAccessible(true);
            Object original = capeField.get(player);
            capeField.set(player, CapeManager.getCape(player));

            // Força hasCape() a retornar true via field_175155_b
            Field hasCapeField = AbstractClientPlayer.class.getDeclaredField("field_175155_b");
            hasCapeField.setAccessible(true);
            boolean originalHasCape = (boolean) hasCapeField.get(player);
            hasCapeField.set(player, true);

            super.doRenderLayer(player, limbSwing, limbSwingAmount, partialTicks,
                    ageInTicks, netHeadYaw, headPitch, scale);

            // Restaura os valores originais
            capeField.set(player, original);
            hasCapeField.set(player, originalHasCape);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}