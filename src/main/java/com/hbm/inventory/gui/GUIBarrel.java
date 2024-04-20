package com.hbm.inventory.gui;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FFUtils;
import com.hbm.inventory.container.ContainerBarrel;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityBarrel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

public class GUIBarrel extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_barrel.png");
	private TileEntityBarrel tank;

	public GUIBarrel(InventoryPlayer invPlayer, TileEntityBarrel tedf) {
		super(new ContainerBarrel(invPlayer, tedf));
		tank = tedf;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		//tank.tank.renderTankInfo(this, mouseX, mouseY, guiLeft + 71, guiTop + 69 - 52, 34, 52);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 71, guiTop + 68 - 52, 34, 52, tank.tank);
		super.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.tank.hasCustomInventoryName() ? this.tank.getInventoryName() : I18n.format(this.tank.getInventoryName());
		
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	protected void mouseClicked(int x, int y, int i) throws IOException {
    	super.mouseClicked(x, y, i);
		
    	if(guiLeft + 151 <= x && guiLeft + 151 + 18 > x && guiTop + 35 < y && guiTop + 35 + 18 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(tank.getPos(), 0, 0));
    	}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = tank.mode;
		drawTexturedModalRect(guiLeft + 151, guiTop + 34, 176, i * 18, 18, 18);
		
		FFUtils.drawLiquid(tank.tank, guiLeft, guiTop, zLevel, 34, 52, 71, 97);
		/*Minecraft.getMinecraft().getTextureManager().bindTexture(tank.tank.getSheet());
		tank.tank.renderTank(this, guiLeft + 71, guiTop + 69, tank.tank.getTankType().textureX() * FluidTank.x, tank.tank.getTankType().textureY() * FluidTank.y, 16, 52);
		tank.tank.renderTank(this, guiLeft + 71 + 16, guiTop + 69, tank.tank.getTankType().textureX() * FluidTank.x, tank.tank.getTankType().textureY() * FluidTank.y, 16, 52);
		tank.tank.renderTank(this, guiLeft + 71 + 32, guiTop + 69, tank.tank.getTankType().textureX() * FluidTank.x, tank.tank.getTankType().textureY() * FluidTank.y, 2, 52);*/
	}
}
