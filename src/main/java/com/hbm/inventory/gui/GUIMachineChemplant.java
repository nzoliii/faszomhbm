package com.hbm.inventory.gui;

import com.hbm.util.I18nUtil;
import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FFUtils;
import com.hbm.inventory.container.ContainerMachineChemplant;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineChemplant;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIMachineChemplant extends GuiInfoContainer {

	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_chemplant.png");
	private TileEntityMachineChemplant chemplant;
	
	public GUIMachineChemplant(InventoryPlayer invPlayer, TileEntityMachineChemplant tedf) {
		super(new ContainerMachineChemplant(invPlayer, tedf));
		chemplant = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		this.renderHoveredToolTip(mouseX, mouseY);
		
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 52 - 34, 16, 34, chemplant.tanks[0], chemplant.tankTypes[0]);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 26, guiTop + 52 - 34, 16, 34, chemplant.tanks[1], chemplant.tankTypes[1]);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 52 - 34, 16, 34, chemplant.tanks[2], chemplant.tankTypes[2]);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 52 - 34, 16, 34, chemplant.tanks[3], chemplant.tankTypes[3]);
		
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 44, guiTop + 70 - 52, 16, 52, chemplant.power, TileEntityMachineChemplant.maxPower);
		
		if(chemplant.getStackInSlot(4) == null || chemplant.getStackInSlot(4).isEmpty() || chemplant.getStackInSlot(4).getItem()!= ModItems.chemistry_template) {

			String[] text = new String[] { "Error: This machine requires a chemistry template!" };
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36, 16, 16, guiLeft - 8, guiTop + 36 + 16, text);
		}

		String[] text = I18nUtil.resolveKeyArray("desc.guiacceptupgrades1");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 105, guiTop + 40, 8, 8, guiLeft + 105, guiTop + 40 + 16, text);
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.chemplant.hasCustomInventoryName() ? this.chemplant.getInventoryName() : I18n.format(this.chemplant.getInventoryName());
		
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		this.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = (int)chemplant.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 44, guiTop + 70 - i, 176, 52 - i, 16, i);
		
		if(chemplant.isProgressing){
		int j = chemplant.getProgressScaled(90);
		drawTexturedModalRect(guiLeft + 43, guiTop + 89, 0, 222, j, 18);
		} else {
			drawTexturedModalRect(guiLeft + 43, guiTop + 89, 0, 222, 0, 18);
		}

		this.drawInfoPanel(guiLeft + 105, guiTop + 40, 8, 8, 8);
		
		if(chemplant.getStackInSlot(4) == null || chemplant.getStackInSlot(4).getItem()!= ModItems.chemistry_template) {

			this.drawInfoPanel(guiLeft - 16, guiTop + 36, 16, 16, 6);
		}
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		
		FFUtils.drawLiquid(chemplant.tanks[0], guiLeft, guiTop, zLevel, 16, 34, 8, 80);
		FFUtils.drawLiquid(chemplant.tanks[1], guiLeft, guiTop, zLevel, 16, 34, 26, 80);
		FFUtils.drawLiquid(chemplant.tanks[2], guiLeft, guiTop, zLevel, 16, 34, 134, 80);
		FFUtils.drawLiquid(chemplant.tanks[3], guiLeft, guiTop, zLevel, 16, 34, 152, 80);
	}
}
