package com.thiago.capes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class Cape {

    public final String name;
    public final ResourceLocation resource;

    public Cape(String name, String texturePath) {
        this.name = name;
        this.resource = new ResourceLocation("customcapes/cape/" + name.toLowerCase().replace(" ", "_"));

        try {
            InputStream is = Cape.class.getClassLoader().getResourceAsStream(texturePath);
            if (is == null) throw new RuntimeException("Texture not found: " + texturePath);
            BufferedImage image = ImageIO.read(is);

            Minecraft mc = Minecraft.getMinecraft();
            mc.addScheduledTask(() -> {
                mc.getTextureManager().loadTexture(resource, new DynamicTexture(image));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}