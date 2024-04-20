package com.hbm.inventory.gui;

import com.hbm.forgefluid.FFUtils;
import com.hbm.inventory.container.ContainerHeaterHeatex;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityHeaterHeatex;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.Arrays;

public class GUIHeaterHeatex extends GuiInfoContainer {
    private final static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_heatex.png");
    private final TileEntityHeaterHeatex heater;
    private GuiTextField fieldCycles;
    private GuiTextField fieldDelay;

    public GUIHeaterHeatex(InventoryPlayer invPlayer, TileEntityHeaterHeatex tedf) {
        super(new ContainerHeaterHeatex(invPlayer, tedf));
        heater = tedf;

        this.xSize = 176;
        this.ySize = 204;
    }

    @Override
    public void initGui() {
        super.initGui();

        Keyboard.enableRepeatEvents(true);
        this.fieldCycles = new GuiTextField(0, this.fontRenderer, guiLeft + 74, guiTop + 31, 28, 10);
        initText(this.fieldCycles);
        this.fieldCycles.setText(String.valueOf(heater.amountToCool));

        this.fieldDelay = new GuiTextField(1, this.fontRenderer, guiLeft + 74, guiTop + 49, 28, 10);
        initText(this.fieldDelay);
        this.fieldDelay.setText(String.valueOf(heater.tickDelay));
    }

    protected void initText(GuiTextField field) {
        field.setTextColor(0x00ff00);
        field.setDisabledTextColour(0x00ff00);
        field.setEnableBackgroundDrawing(false);
        field.setMaxStringLength(4);
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);

        FFUtils.renderTankInfo(this, x, y, guiLeft + 44, guiTop + 36, 16, 52, heater.tanks[0], heater.tankTypes[0]);
        FFUtils.renderTankInfo(this, x, y, guiLeft + 116, guiTop + 36, 16, 52, heater.tanks[1], heater.tankTypes[1]);

        if (guiLeft + 70 <= x && guiLeft + 70 + 36 > x && guiTop + 26 < y && guiTop + 26 + 18 >= y) {
            drawHoveringText(Arrays.asList("Amount per cycle"), x, y);
        }
        if (guiLeft + 70 <= x && guiLeft + 70 + 36 > x && guiTop + 44 < y && guiTop + 44 + 18 >= y) {
            drawHoveringText(Arrays.asList("Cycle tick delay"), x, y);
        }

        super.renderHoveredToolTip(x, y);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j) {
        String name = I18n.format(this.heater.getInventoryName());
        this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float interp, int x, int y) {
        super.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        FFUtils.drawLiquid(heater.tanks[0], guiLeft, guiTop, this.zLevel, 16, 52, 44, 116);
        FFUtils.drawLiquid(heater.tanks[1], guiLeft, guiTop, this.zLevel, 16, 52, 116, 116);

        this.fieldCycles.drawTextBox();
        this.fieldDelay.drawTextBox();
    }

    @Override
    protected void mouseClicked(int x, int y, int i) throws IOException {
        super.mouseClicked(x, y, i);

        this.fieldCycles.mouseClicked(x, y, i);
        this.fieldDelay.mouseClicked(x, y, i);
    }

    @Override
    protected void keyTyped(char c, int i) throws IOException {
        BlockPos heaterPos = heater.getPos();

        if (this.fieldCycles.textboxKeyTyped(c, i)) {
            int cyc = Math.max(NumberUtils.toInt(this.fieldCycles.getText()), 1);
            NBTTagCompound data = new NBTTagCompound();
            data.setInteger("toCool", cyc);
            PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, heaterPos.getX(), heaterPos.getY(), heaterPos.getZ()));
            return;
        }
        if (this.fieldDelay.textboxKeyTyped(c, i)) {
            int delay = Math.max(NumberUtils.toInt(this.fieldDelay.getText()), 1);
            NBTTagCompound data = new NBTTagCompound();
            data.setInteger("delay", delay);
            PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, heaterPos.getX(), heaterPos.getY(), heaterPos.getZ()));
            return;
        }

        super.keyTyped(c, i);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
}
