package com.thiago.capes;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CapeSelectionGui extends GuiScreen {

    private List<String> capeNames;
    private int selected = -1;
    private int scroll = 0;

    private static final int ITEM_HEIGHT = 36;
    private static final int LIST_WIDTH = 160;

    @Override
    public void initGui() {
        capeNames = new ArrayList<>(CapeManager.AVAILABLE_CAPES.keySet());

        // Botão confirmar
        buttonList.add(new GuiButton(0, width / 2 - 80, height - 35, 75, 20, "Select"));

        // Botão remover capa
        buttonList.add(new GuiButton(1, width / 2 + 5, height - 35, 75, 20, "Remove Cape"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        // Título
        drawCenteredString(fontRendererObj, "Select Cape", width / 2, 10, 0xFFFFFF);

        // Lista de capas
        int listX = width / 2 - LIST_WIDTH / 2;
        int listY = 30;

        for (int i = scroll; i < capeNames.size(); i++) {
            int y = listY + (i - scroll) * ITEM_HEIGHT;
            if (y + ITEM_HEIGHT > height - 45) break;

            String name = capeNames.get(i);
            boolean isSelected = i == selected;

            // Fundo do item
            drawRect(listX, y, listX + LIST_WIDTH, y + ITEM_HEIGHT - 2,
                    isSelected ? 0xFF4488FF : 0xFF333333);

            // Preview da capa (quadrado colorido por enquanto)
            ResourceLocation cape = CapeManager.AVAILABLE_CAPES.get(name);
            mc.getTextureManager().bindTexture(cape);
            GlStateManager.color(1f, 1f, 1f, 1f);
            drawTexturedModalRect(listX + 4, y + 4, 0, 0, 28, 28);

            // Nome da capa
            drawString(fontRendererObj, name, listX + 38, y + 13, 0xFFFFFF);

            // Detecta clique na linha
            if (mouseX >= listX && mouseX <= listX + LIST_WIDTH
                    && mouseY >= y && mouseY <= y + ITEM_HEIGHT - 2) {
                drawRect(listX, y, listX + LIST_WIDTH, y + ITEM_HEIGHT - 2, 0x33FFFFFF);
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        int listX = width / 2 - LIST_WIDTH / 2;
        int listY = 30;

        for (int i = scroll; i < capeNames.size(); i++) {
            int y = listY + (i - scroll) * ITEM_HEIGHT;
            if (y + ITEM_HEIGHT > height - 45) break;

            if (mouseX >= listX && mouseX <= listX + LIST_WIDTH
                    && mouseY >= y && mouseY <= y + ITEM_HEIGHT - 2) {
                selected = i;
                break;
            }
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        int wheel = org.lwjgl.input.Mouse.getEventDWheel();
        if (wheel < 0 && scroll < capeNames.size() - 1) scroll++;
        if (wheel > 0 && scroll > 0) scroll--;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0 && selected >= 0) {
            CapeManager.selectCape(capeNames.get(selected));
            mc.displayGuiScreen(null);
        } else if (button.id == 1) {
            CapeManager.clearCape();
            mc.displayGuiScreen(null);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}