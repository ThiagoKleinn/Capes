package com.thiago.capes;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;

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
        if (!CapeManager.hasCape(player)) return;

        ResourceLocation cape = CapeManager.getCape(player);
        if (cape == null) return;

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.playerRenderer.bindTexture(cape);

        GlStateManager.pushMatrix();
        this.playerRenderer.getMainModel().bipedBody.postRender(0.0625F);
        GlStateManager.translate(0.0F, 0.0F, 0.125F);

        double dx = player.prevChasingPosX + (player.chasingPosX - player.prevChasingPosX) * (double)partialTicks - (player.prevPosX + (player.posX - player.prevPosX) * (double)partialTicks);
        double dy = player.prevChasingPosY + (player.chasingPosY - player.prevChasingPosY) * (double)partialTicks - (player.prevPosY + (player.posY - player.prevPosY) * (double)partialTicks);
        double dz = player.prevChasingPosZ + (player.chasingPosZ - player.prevChasingPosZ) * (double)partialTicks - (player.prevPosZ + (player.posZ - player.prevPosZ) * (double)partialTicks);

        float yaw = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;
        double sinYaw = (double)MathHelper.sin(yaw * (float)Math.PI / 180.0F);
        double cosYaw = (double)(-MathHelper.cos(yaw * (float)Math.PI / 180.0F));

        float swingY = (float)dy * 10.0F;
        swingY = MathHelper.clamp_float(swingY, -6.0F, 32.0F);

        float swingX = (float)(dx * sinYaw + dz * cosYaw) * 100.0F;
        swingX = MathHelper.clamp_float(swingX, 0.0F, 150.0F);

        float swingZ = (float)(dx * cosYaw - dz * sinYaw) * 100.0F;
        swingZ = MathHelper.clamp_float(swingZ, -20.0F, 20.0F);

        if (swingX < 0.0F) swingX = 0.0F;

        float pitch = player.prevCameraYaw + (player.cameraYaw - player.prevCameraYaw) * partialTicks;
        swingY += MathHelper.sin((player.prevDistanceWalkedModified + (player.distanceWalkedModified - player.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * pitch;

        if (player.isSneaking()) swingY += 25.0F;

        GlStateManager.rotate(6.0F + swingX / 2.0F + swingY, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(swingZ / 2.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(-swingZ / 2.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);

        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        GlStateManager.translate(0.0F, -1.6F, 0.125F);  // <-- z era 0, agora 0.125

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer wr = tessellator.getWorldRenderer();
        wr.begin(7, DefaultVertexFormats.POSITION_TEX);
        wr.pos(-1.0D,  0.0D, 0.0D).tex(0.0D, 0.0D).endVertex();
        wr.pos( 1.0D,  0.0D, 0.0D).tex(1.0D, 0.0D).endVertex();
        wr.pos( 1.0D,  2.0D, 0.0D).tex(1.0D, 0.5D).endVertex();
        wr.pos(-1.0D,  2.0D, 0.0D).tex(0.0D, 0.5D).endVertex();
        tessellator.draw();

        GlStateManager.popMatrix();
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}