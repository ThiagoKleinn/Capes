package com.thiago.capes;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
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
            // Pega o NetworkPlayerInfo do player
            Field playerInfoField = AbstractClientPlayer.class.getDeclaredField("field_175157_a");
            playerInfoField.setAccessible(true);
            NetworkPlayerInfo info = (NetworkPlayerInfo) playerInfoField.get(player);

            // Pega os campos do NetworkPlayerInfo
            Field hasCapeField = NetworkPlayerInfo.class.getDeclaredField("field_178864_d");
            Field capeTextureField = NetworkPlayerInfo.class.getDeclaredField("field_178865_e");
            hasCapeField.setAccessible(true);
            capeTextureField.setAccessible(true);

            // Salva os valores originais
            boolean originalHasCape = (boolean) hasCapeField.get(info);
            Object originalTexture = capeTextureField.get(info);

            // Injeta nossa capa
            hasCapeField.set(info, true);
            capeTextureField.set(info, CapeManager.getCape(player));

            super.doRenderLayer(player, limbSwing, limbSwingAmount, partialTicks,
                    ageInTicks, netHeadYaw, headPitch, scale);

            // Restaura
            hasCapeField.set(info, originalHasCape);
            capeTextureField.set(info, originalTexture);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}