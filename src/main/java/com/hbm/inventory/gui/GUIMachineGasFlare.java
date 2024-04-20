package com.hbm.inventory.gui;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.FluidCombustionRecipes;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.util.I18nUtil;
import com.hbm.forgefluid.FFUtils;
import com.hbm.inventory.container.ContainerMachineGasFlare;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.oil.TileEntityMachineGasFlare;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GUIMachineGasFlare extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/generators/gui_flare_stack.png");
	private TileEntityMachineGasFlare flare;
	
	public GUIMachineGasFlare(InventoryPlayer invPlayer, TileEntityMachineGasFlare tedf) {
		super(new ContainerMachineGasFlare(invPlayer, tedf));
		flare = tedf;
		
		this.xSize = 176;
		this.ySize = 203;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 79, guiTop + 16, 35, 10, mouseX, mouseY, I18nUtil.resolveKeyArray("flare.valve"));
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 79, guiTop + 50, 35, 14, mouseX, mouseY, I18nUtil.resolveKeyArray("flare.ignition"));

		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 35, guiTop + 69 - 52, 16, 52, flare.tank, flare.tankType);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 143, guiTop + 69 - 52, 16, 52, flare.power, TileEntityMachineGasFlare.maxPower);
		super.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void mouseClicked(int x, int y, int mouseButton) throws IOException {
		super.mouseClicked(x, y, mouseButton);

		if(guiLeft + 89 <= x && guiLeft + 89 + 16 > x && guiTop + 16 < y && guiTop + 16 + 10 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("valve", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, flare.getPos()));

		} else if(guiLeft + 89 <= x && guiLeft + 89 + 16 > x && guiTop + 50 < y && guiTop + 50 + 14 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("dial", true);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, flare.getPos()));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int j = (int)flare.getPowerScaled(52);
		drawTexturedModalRect(guiLeft + 143, guiTop + 69 - j, 176, 94 - j, 16, j);

		if(flare.isOn) {
			drawTexturedModalRect(guiLeft + 79, guiTop + 15, 176, 0, 35, 10);
		}
		if(flare.doesBurn) {
			drawTexturedModalRect(guiLeft + 79, guiTop + 49, 176, 10, 35, 14);
		}

		if(flare.isOn && flare.doesBurn && flare.tank.getFluidAmount() > 0 && FluidCombustionRecipes.hasFuelRecipe(flare.tankType))
			drawTexturedModalRect(guiLeft + 88, guiTop + 29, 176, 24, 18, 18);

		FFUtils.drawLiquid(flare.tank, guiLeft, guiTop, zLevel, 16, 52, 35, 97);
	}
}
