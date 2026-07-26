package com.thiago.capes;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.ResourceLocation;

public class CustomCapeLayer implements LayerRenderer<AbstractClientPlayer> {

    private final RenderPlayer playerRenderer;

    public CustomCapeLayer(RenderPlayer playerRenderer) {
        this.playerRenderer = playerRenderer;
    }

    @Override
    public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float limbSwingAmount,
                              float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

        if (!player.hasPlayerInfo()) return;
        if (!player.isWearing(EnumPlayerModelParts.CAPE)) return;

        ResourceLocation cape = null;

        if (CapeManager.hasCape(player)) {
            cape = CapeManager.getCape(player);
        }

        if (cape == null) return;

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.playerRenderer.bindTexture(cape);

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.0F, 0.125F);

        double dx = player.prevChasingPosX + (player.chasingPosX - player.prevChasingPosX) * partialTicks
                - (player.prevPosX + (player.posX - player.prevPosX) * partialTicks);
        double dy = player.prevChasingPosY + (player.chasingPosY - player.prevChasingPosY) * partialTicks
                - (player.prevPosY + (player.posY - player.prevPosY) * partialTicks);
        double dz = player.prevChasingPosZ + (player.chasingPosZ - player.prevChasingPosZ) * partialTicks
                - (player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks);

        float yaw = player.prevRenderYawOffset
                + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;
        double sinYaw = Math.sin(yaw * Math.PI / 180.0D);
        double cosYaw = Math.cos(yaw * Math.PI / 180.0D);

        float swingX = (float)(dx * sinYaw - dz * cosYaw) * 10.0F;
        swingX = Math.max(-6.0F, Math.min(swingX, 32.0F));

        float swingY = (float)(dy * 10.0F);
        float swingZ = (float)(dx * cosYaw + dz * sinYaw) * 10.0F;

        if (swingX < 0.0F) swingX = 0.0F;

        float pitch = player.prevCameraYaw + (player.cameraYaw - player.prevCameraYaw) * partialTicks;
        swingY += Math.sin((player.prevDistanceWalkedModified
                + (player.distanceWalkedModified - player.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * pitch;

        if (player.isSneaking()) swingY += 25.0F;

        GlStateManager.rotate(6.0F + swingX / 2.0F + swingY, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(swingZ / 2.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(-swingZ / 2.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);

        this.playerRenderer.getMainModel().bipedBody.postRender(0.0625F);

        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        GlStateManager.translate(0.0F, -1.6F, 0.0F);

        ModelBiped model = (ModelBiped) this.playerRenderer.getMainModel();
        model.bipedBody.render(scale);

        GlStateManager.popMatrix();
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}