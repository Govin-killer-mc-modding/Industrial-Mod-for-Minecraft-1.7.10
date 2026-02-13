package com.govinkiller.industrialmod.client.gui;

import com.govinkiller.industrialmod.inventory.ContainerCoalGenerator;
import com.govinkiller.industrialmod.tileentity.TileEntityCoalGenerator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class GuiCoalGenerator extends GuiContainer {
    private static final ResourceLocation guiTexture = new ResourceLocation("industrialmod", "textures/gui/coalGenerator.png");
    private TileEntityCoalGenerator tile;

    // --- КООРДИНАТЫ ОТРИСОВКИ (Layout) ---
    private static final int BURN_X = 25;
    private static final int BURN_Y = 52;
    private static final int ENERGY_X = 86;
    private static final int ENERGY_Y = 21;

    // Размеры
    private static final int BURN_W = 16;
    private static final int BURN_H = 16; // Шкала нагрева
    private static final int ENERGY_W = 10;
    private static final int ENERGY_H = 41;// Шкала энергии


    // --- UV КООРДИНАТЫ (Assets Map) ---
    private static final int UV_BURN_U = 177;
    private static final int UV_BURN_V = 0;
    private static final int UV_ENERGY_U = 200;
    private static final int UV_ENERGY_V = 0;

    public GuiCoalGenerator(InventoryPlayer playerInv, TileEntityCoalGenerator tile) {
        super(new ContainerCoalGenerator(playerInv, tile));
        this.tile = tile;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = I18n.format("tile.coalGenerator.name");
        this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);

        // Tooltip logic
        int relX = mouseX - (this.width - this.xSize) / 2;
        int relY = mouseY - (this.height - this.ySize) / 2;

        if (relX >= ENERGY_X && relX <= ENERGY_X + ENERGY_W && relY >= ENERGY_Y && relY <= ENERGY_Y + ENERGY_H) {
            List<String> tooltip = new ArrayList<String>();
            tooltip.add(tile.storage.getEnergyStored() + " / " + tile.storage.getMaxEnergyStored() + " RF");
            this.drawHoveringText(tooltip, relX, relY, fontRendererObj);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(guiTexture);

        int guiX = (this.width - this.xSize) / 2;
        int guiY = (this.height - this.ySize) / 2;

        // 1. BACKGROUND (Z-index: 0)
        this.drawTexturedModalRect(guiX, guiY, 0, 0, this.xSize, this.ySize);

        // 2. BURN ANIMATION (Fill: Bottom to Top)
        int burnScaled = tile.getBurnTimeRemainingScaled(BURN_H);
        if (burnScaled > 0) {
            int offset = BURN_H - burnScaled;
            this.drawTexturedModalRect(guiX + BURN_X, guiY + BURN_Y + offset, UV_BURN_U, UV_BURN_V + offset, BURN_W, burnScaled);
        }

        // 3. ENERGY TANK (Fill: Bottom to Top)
        int energyScaledTank = tile.getEnergyScaled(ENERGY_H);
        if (energyScaledTank > 0) {
            int offset = ENERGY_H - energyScaledTank;
            this.drawTexturedModalRect(guiX + ENERGY_X, guiY + ENERGY_Y + offset, UV_ENERGY_U, UV_ENERGY_V + offset, ENERGY_W, energyScaledTank);
        }

        }
    }