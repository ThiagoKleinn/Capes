public class CapeRenderer {

    @SubscribeEvent
    public void onRenderPlayer(RenderPlayerEvent.Pre event) {
        // Não faz nada aqui — o hook é no LayerCape
    }

    // O melhor ponto de hook é substituindo o LayerCape
    // Registre isso no init do mod:
    public static void injectCapeLayer(RenderManager rm) {
        RenderPlayer renderer = (RenderPlayer) rm.getEntityRenderObject(
                Minecraft.getMinecraft().thePlayer
        );

        // Remove o layer original de capa
        renderer.removeLayer(LayerCape.class);

        // Adiciona o seu
        renderer.addLayer(new CustomCapeLayer(renderer));
    }
}