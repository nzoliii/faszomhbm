package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.container.ContainerCrystallizer;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineCrystallizer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUICrystallizer extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_crystallizer_alt.png");
	private TileEntityMachineCrystallizer acidomatic;

	public GUICrystallizer(InventoryPlayer invPlayer, TileEntityMachineCrystallizer acidomatic) {
		super(new ContainerCrystallizer(invPlayer, acidomatic));
		this.acidomatic = acidomatic;

		this.xSize = 176;
		this.ySize = 204;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.acidomatic.hasCustomInventoryName() ? this.acidomatic.getInventoryName() : I18n.format(this.acidomatic.getInventoryName());

		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 17, 16, 52, acidomatic.power, TileEntityMachineCrystallizer.maxPower);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 35, guiTop + 17, 16, 70, acidomatic.tank);
		String[] text = new String[] { "Acceptable upgrades:",
				" -Speed (stacks to level 3)",
				" -Effectiveness (stacks to level 3)",
				" -Overdrive (stacks to level 3)"};
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 117, guiTop + 22, 8, 8, guiLeft + 200, guiTop + 45, text);
		super.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = (int)acidomatic.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 152, guiTop + 70 - i, 176, 64 - i, 16, i);

		int j = acidomatic.getProgressScaled(28);
		drawTexturedModalRect(guiLeft + 80, guiTop + 47, 176, 0, j, 12);

		this.drawInfoPanel(guiLeft + 117, guiTop + 22, 8, 8, 8);

		FFUtils.drawLiquid(acidomatic.tank, guiLeft, guiTop, zLevel, 16, 70, 35, 116);
	}
}