public class CustomCapeLayer implements LayerRenderer<AbstractClientPlayer> {

    private final RenderPlayer playerRenderer;

    public CustomCapeLayer(RenderPlayer renderer) {
        this.playerRenderer = renderer;
    }

    @Override
    public void doRenderLayer(AbstractClientPlayer player, float limbSwing,
            float limbSwingAmount, float partialTicks, float ageInTicks,
            float netHeadYaw, float headPitch, float scale) {

        // Se o jogador tem capa no nosso sistema, usa a nossa
        if (CapeManager.hasCape(player)) {
            ResourceLocation capeTexture = CapeManager.getCape(player);
            renderCape(player, capeTexture, partialTicks);
            return;
        }

        // Senão, renderiza a capa padrão da Mojang (se tiver)
        if (player.hasCape() && player.isWearing(EnumPlayerModelParts.CAPE)) {
            renderCape(player, player.getLocationCape(), partialTicks);
        }
    }

    private void renderCape(AbstractClientPlayer player,
            ResourceLocation texture, float partialTicks) {

        GlStateManager.color(1f, 1f, 1f, 1f);
        this.playerRenderer.bindTexture(texture);

        GlStateManager.pushMatrix();

        // Posicionamento padrão da capa
        GlStateManager.translate(0.0F, 0.0F, 0.125F);

        // ... lógica de rotação igual ao LayerCape original da Mojang
        // (copie o doRenderLayer do LayerCape do Forge 1.8.9)

        this.playerRenderer.getMainModel().bipedBody.postRender(0.0625F);

        GlStateManager.popMatrix();
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}