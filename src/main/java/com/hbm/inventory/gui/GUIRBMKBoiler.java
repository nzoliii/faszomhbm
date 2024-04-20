package com.hbm.inventory.gui;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.container.ContainerRBMKBoiler;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBoiler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GUIRBMKBoiler extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_rbmk_boiler.png");
	private TileEntityRBMKBoiler boiler;

	public GUIRBMKBoiler(InventoryPlayer invPlayer, TileEntityRBMKBoiler tedf) {
		super(new ContainerRBMKBoiler(invPlayer, tedf));
		boiler = tedf;
		
		this.xSize = 176;
		this.ySize = 186;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 126, guiTop + 24, 16, 56, boiler.feed);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 89, guiTop + 39, 8, 28, boiler.steam);
		
		super.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void mouseClicked(int x, int y, int i) throws IOException {
		super.mouseClicked(x, y, i);

		if(guiLeft + 33 <= x && guiLeft + 33 + 20 > x && guiTop + 21 < y && guiTop + 21 + 64 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("compression", true); //we only need to send on bit, so boolean it is
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, boiler.getPos()));
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format(this.boiler.getName());
		
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = boiler.feed.getFluidAmount() * 58 / boiler.feed.getCapacity();
		drawTexturedModalRect(guiLeft + 126, guiTop + 82 - i, 176, 58 - i, 14, i);
		
		int j = boiler.steam.getFluidAmount() * 22 / boiler.steam.getCapacity();

		if(j > 0) j++;
		if(j > 22) j++;
		
		drawTexturedModalRect(guiLeft + 91, guiTop + 65 - j, 190, 24 - j, 4, j);
		
		if(boiler.steamType == ModForgeFluids.steam){
			drawTexturedModalRect(guiLeft + 36, guiTop + 24, 194, 0, 14, 58);
		} else if(boiler.steamType == ModForgeFluids.hotsteam){
			drawTexturedModalRect(guiLeft + 36, guiTop + 24, 208, 0, 14, 58);
		} else if(boiler.steamType == ModForgeFluids.superhotsteam){
			drawTexturedModalRect(guiLeft + 36, guiTop + 24, 222, 0, 14, 58);
		} else if(boiler.steamType == ModForgeFluids.ultrahotsteam){
			drawTexturedModalRect(guiLeft + 36, guiTop + 24, 236, 0, 14, 58);
		}
	}
}