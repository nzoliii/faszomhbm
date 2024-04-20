package com.hbm.inventory.gui;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.container.ContainerSILEX;
import com.hbm.items.machine.ItemFELCrystal.EnumWavelengths;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntitySILEX;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

public class GUISILEX extends GuiInfoContainer {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/processing/gui_silex.png");
	private TileEntitySILEX silex;

	public GUISILEX(InventoryPlayer invPlayer, TileEntitySILEX laser) {
		super(new ContainerSILEX(invPlayer, laser));
		this.silex = laser;

		this.xSize = 176;
		this.ySize = 222;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 42, 52, 7, silex.tank, ModForgeFluids.acid);
		
		if(silex.current != null) {
			this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 27, guiTop + 72, 16, 52, mouseX, mouseY, new String[] { silex.currentFill + "/" + TileEntitySILEX.maxFill + "mB", silex.current.toStack().getDisplayName() });
		}
		
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 10, guiTop + 92, 10, 10, mouseX, mouseY, new String[] { "Void contents" });
		super.renderHoveredToolTip(mouseX, mouseY);
	}

	protected void mouseClicked(int x, int y, int i) throws IOException {
		super.mouseClicked(x, y, i);

		if(guiLeft + 10 <= x && guiLeft + 10 + 12 > x && guiTop + 92 < y && guiTop + 92 + 12 >= y) {

			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(silex.getPos(), 0, 0));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = this.silex.hasCustomInventoryName() ? this.silex.getInventoryName() : I18n.format(this.silex.getInventoryName());

		this.fontRenderer.drawString(name, (this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2)-54, 8, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);

		if(silex.mode != EnumWavelengths.NULL) {
			this.fontRenderer.drawString(silex.mode.textColor + I18nUtil.resolveKey(silex.mode.name), 100 + (32 - this.fontRenderer.getStringWidth(I18nUtil.resolveKey(silex.mode.name)) / 2), 16, 0);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		if(silex.tank.getFluidAmount() > 0) {
			
			if(silex.getTankType() == ModForgeFluids.acid || TileEntitySILEX.fluidConversion.containsKey(silex.getTankType())) {
				drawTexturedModalRect(guiLeft + 7, guiTop + 41, 176, 118, 54, 9);
			} else {
				drawTexturedModalRect(guiLeft + 7, guiTop + 41, 176, 109, 54, 9);
			}
		}

		int p = silex.getProgressScaled(69);
		drawTexturedModalRect(guiLeft + 45, guiTop + 82, 176, 0, p, 43);

		int f = silex.getFillScaled(52);
		drawTexturedModalRect(guiLeft + 26, guiTop + 124 - f, 176, 109 - f, 16, f);

		int i = silex.getFluidScaled(52);
		drawTexturedModalRect(guiLeft + 8, guiTop + 42, 176, silex.getTankType() == ModForgeFluids.acid ? 43 : 50, i, 7);

		if(silex.mode != EnumWavelengths.NULL) {
			float freq = 0.0125F * (float)Math.pow(2, silex.mode.ordinal());
			int color = (silex.mode != EnumWavelengths.VISIBLE) ? silex.mode.guiColor : Color.HSBtoRGB(silex.getWorld().getTotalWorldTime() / 50.0F, 0.5F, 1F);// & 16777215;
			drawWave(81, 46, 16, 84, 0.5F, freq, color, 3F, 1F);
		}
	}

	private void drawWave(int x, int y, int height, int width, float resolution, float freq, int color, float thickness, float mult) {
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.color(((color >> 16) & 0xFF) / 255.0F, ((color >> 8) & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, 1.0F);
        GlStateManager.glLineWidth(thickness);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        BufferBuilder buf = Tessellator.getInstance().getBuffer();
        buf.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);
        
        float samples = ((float)width) / resolution;
        float scale = ((float)height)/2F;
        float offset = (float)((float)silex.getWorld().getTotalWorldTime() % (4*Math.PI/freq));
        double currentX = x;
        double currentY = y + scale * Math.sin((currentX  + offset) * freq);
        buf.pos(guiLeft + currentX, guiTop + currentY, this.zLevel).endVertex();
        for(int i = 1; i <= samples; i++) {
            currentX = x + i*resolution;
            currentY = y + scale*Math.sin((currentX + offset) * freq);
            buf.pos(guiLeft + currentX, guiTop + currentY, this.zLevel).endVertex();
        }
        
        Tessellator.getInstance().draw();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableTexture2D();
    }
}