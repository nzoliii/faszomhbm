package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FFUtils;
import com.hbm.inventory.container.ContainerRBMKOutgasser;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKOutgasser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIRBMKOutgasser extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_rbmk_outgasser.png");
	private TileEntityRBMKOutgasser rod;

	public GUIRBMKOutgasser(InventoryPlayer invPlayer, TileEntityRBMKOutgasser tedf) {
		super(new ContainerRBMKOutgasser(invPlayer, tedf));
		rod = tedf;
		
		this.xSize = 176;
		this.ySize = 186;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 143, guiTop + 23, 14, 58, rod.gas);
		
		super.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(this.rod.getName());
		
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
		this.fontRenderer.drawString("Flux", 21, 34, 4210752);
		String fluxNumbers = formatNumber((float)rod.progress)+"/"+formatNumber((float)rod.duration);
		this.fontRenderer.drawString(fluxNumbers, 123-this.fontRenderer.getStringWidth(fluxNumbers), 34, 0x46EA00);
	}

	protected String formatNumber(float number){
		if(number < 1000D)
			return String.format("%5.1f ", number);
		if(number < 1000000D)
			return String.format("%5.1fk", number/1000F);
		if(number < 1000000000D)
			return String.format("%5.1fM", number/1000000F);
		if(number < 1000000000000D)
			return String.format("%5.1fG", number/1000000000F);
		if(number < 1000000000000000D)
			return String.format("%5.1fT", number/1000000000000F);
		return "";
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int progress = (int) (rod.progress * 45 / rod.duration);
		drawTexturedModalRect(guiLeft + 66, guiTop + 58, 190, 0, progress, 6);
		
		int gas = (int) (rod.gas.getFluidAmount() * 58 / rod.gas.getCapacity());
		drawTexturedModalRect(guiLeft + 143, guiTop + 82 - gas, 176, 58 - gas, 14, gas);
	}
}