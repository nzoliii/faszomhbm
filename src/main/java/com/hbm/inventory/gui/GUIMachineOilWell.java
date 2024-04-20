package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FFUtils;
import com.hbm.inventory.container.ContainerMachineOilWell;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.oil.TileEntityMachineOilWell;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineOilWell extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_well_large.png");
	private TileEntityMachineOilWell derrick;
	
	public GUIMachineOilWell(InventoryPlayer invPlayer, TileEntityMachineOilWell tedf) {
		super(new ContainerMachineOilWell(invPlayer, tedf));
		derrick = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 70 - 52, 34, 52, derrick.tanks[0], derrick.tankTypes[0]);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 80, guiTop + 124 - 52, 34, 52, derrick.tanks[1], derrick.tankTypes[1]);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 70 - 52, 16, 52, derrick.power, derrick.getMaxPower());
		super.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.derrick.hasCustomInventoryName() ? this.derrick.getInventoryName() : I18n.format(this.derrick.getInventoryName());
		
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = (int)derrick.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 8, guiTop + 70 - i, 176, 52 - i, 16, i);
		
		int k = derrick.warning;
		if(k == 2)
			drawTexturedModalRect(guiLeft + 43, guiTop + 17, 176, 52, 18, 18);
		if(k == 1)
			drawTexturedModalRect(guiLeft + 43, guiTop + 17, 194, 52, 18, 18);
		
		int l = derrick.warning2;
		if(l == 1)
			drawTexturedModalRect(guiLeft + 43, guiTop + 89, 212, 52, 18, 18);
		if(l == 2)
			drawTexturedModalRect(guiLeft + 43, guiTop + 89, 230, 52, 18, 18);
		
		FFUtils.drawLiquid(derrick.tanks[0], guiLeft, guiTop, zLevel, 34, 52, 80, 98);
		FFUtils.drawLiquid(derrick.tanks[1], guiLeft, guiTop, zLevel, 34, 52, 80, 152);
	}
}
