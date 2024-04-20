package com.hbm.inventory.gui;

import com.hbm.inventory.container.ContainerNukeBoy;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.bomb.TileEntityNukeBoy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUINukeBoy extends GuiContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/lilBoySchematic.png");
	private TileEntityNukeBoy testNuke;

	public GUINukeBoy(InventoryPlayer invPlayer, TileEntityNukeBoy tedf) {
		super(new ContainerNukeBoy(invPlayer, tedf));
		testNuke = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.testNuke.hasCustomInventoryName() ? this.testNuke.getInventoryName() : I18n.format(this.testNuke.getInventoryName());
		
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		super.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(testNuke.isReady())
		{
			drawTexturedModalRect(guiLeft + 142, guiTop + 90, 176, 0, 16, 16);
		}

		if(testNuke.inventory.getStackInSlot(0).getItem() == ModItems.boy_shielding)
			drawTexturedModalRect(guiLeft + 27, guiTop + 87, 176, 16, 21, 22);
		if(testNuke.inventory.getStackInSlot(1).getItem() == ModItems.boy_target)
			drawTexturedModalRect(guiLeft + 27, guiTop + 89, 176, 38, 21, 18);
		if(testNuke.inventory.getStackInSlot(2).getItem() == ModItems.boy_bullet)
			drawTexturedModalRect(guiLeft + 74, guiTop + 94, 176, 57, 19, 8);
		if(testNuke.inventory.getStackInSlot(3).getItem() == ModItems.boy_propellant)
			drawTexturedModalRect(guiLeft + 92, guiTop + 95, 176, 66, 12, 6);
		if(testNuke.inventory.getStackInSlot(4).getItem() == ModItems.boy_igniter)
			drawTexturedModalRect(guiLeft + 107, guiTop + 91, 176, 75, 16, 14);
	}

}