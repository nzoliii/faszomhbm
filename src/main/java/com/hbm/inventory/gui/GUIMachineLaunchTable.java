package com.hbm.inventory.gui;

import java.io.IOException;

import com.hbm.util.I18nUtil;
import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.FFUtils;
import com.hbm.inventory.container.ContainerLaunchTable;
import com.hbm.items.weapon.ItemCustomMissile;
import com.hbm.items.weapon.ItemMissile.PartSize;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.misc.MissileMultipart;
import com.hbm.render.misc.MissilePronter;
import com.hbm.tileentity.bomb.TileEntityLaunchTable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUIMachineLaunchTable extends GuiInfoContainer {

	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_launch_table.png");
	private TileEntityLaunchTable launcher;
	
	public GUIMachineLaunchTable(InventoryPlayer invPlayer, TileEntityLaunchTable tedf) {
		super(new ContainerLaunchTable(invPlayer, tedf));
		launcher = tedf;
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 116, guiTop + 36, 16, 34, launcher.tanks[0], launcher.tankTypes[0]);
		FFUtils.renderTankInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 36, 16, 34, launcher.tanks[1], launcher.tankTypes[1]);

		String [] text2 = I18nUtil.resolveKeyArray("desc.guimacheltable1");
		String [] text3 = I18nUtil.resolveKeyArray("desc.guimacheltable2");
		String [] text4 = I18nUtil.resolveKeyArray("desc.guimacheltable3");
		String [] text5 = I18nUtil.resolveKeyArray("desc.solidfuellaunch", launcher.solid);
		
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 152, guiTop + 88 - 52, 16, 52, text5);
		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 134, guiTop + 113, 34, 6, launcher.power, TileEntityLaunchTable.maxPower);
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 7, guiTop + 98, 18, 18, text2);
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 25, guiTop + 98, 18, 18, text3);
		this.drawCustomInfo(this, mouseX, mouseY, guiLeft + 43, guiTop + 98, 18, 18, text4);

		String[] text = I18nUtil.resolveKeyArray("desc.guimacheltable4");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36, 16, 16, guiLeft - 8, guiTop + 36 + 16, text);
		
		String[] text1 = I18nUtil.resolveKeyArray("desc.guimachcomplauncher2");
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 16, guiTop + 36 + 16, 16, 16, guiLeft - 8, guiTop + 36 + 16, text1);
		super.renderHoveredToolTip(mouseX, mouseY);
	}

	protected void mouseClicked(int x, int y, int i) throws IOException {
    	super.mouseClicked(x, y, i);
		
    	if(guiLeft + 7 <= x && guiLeft + 7 + 18 > x && guiTop + 98 < y && guiTop + 98 + 18 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(launcher.getPos(), PartSize.SIZE_10.ordinal(), 0));
    	}
		
    	if(guiLeft + 25 <= x && guiLeft + 25 + 18 > x && guiTop + 98 < y && guiTop + 98 + 18 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(launcher.getPos(), PartSize.SIZE_15.ordinal(), 0));
    	}
		
    	if(guiLeft + 43 <= x && guiLeft + 43 + 18 > x && guiTop + 98 < y && guiTop + 98 + 18 >= y) {
    		
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    		PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(launcher.getPos(), PartSize.SIZE_20.ordinal(), 0));
    	}
    }

	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = this.launcher.hasCustomInventoryName() ? this.launcher.getInventoryName() : I18n.format(this.launcher.getInventoryName());
		
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int i = (int)launcher.getPowerScaled(34);
		drawTexturedModalRect(guiLeft + 134, guiTop + 113, 176, 96, i, 6);
		
		int j = (int)launcher.getSolidScaled(52);
		drawTexturedModalRect(guiLeft + 152, guiTop + 88 - j, 176, 96 - j, 16, j);
		
		if(launcher.isMissileValid())
			drawTexturedModalRect(guiLeft + 25, guiTop + 35, 176, 26, 18, 18);
		
		if(launcher.hasDesignator())
			drawTexturedModalRect(guiLeft + 25, guiTop + 71, 176, 26, 18, 18);
		
		if(launcher.liquidState() == 1)
			drawTexturedModalRect(guiLeft + 121, guiTop + 23, 176, 0, 6, 8);
		if(launcher.liquidState() == 0)
			drawTexturedModalRect(guiLeft + 121, guiTop + 23, 182, 0, 6, 8);
		
		if(launcher.oxidizerState() == 1)
			drawTexturedModalRect(guiLeft + 139, guiTop + 23, 176, 0, 6, 8);
		if(launcher.oxidizerState() == 0)
			drawTexturedModalRect(guiLeft + 139, guiTop + 23, 182, 0, 6, 8);
		
		if(launcher.solidState() == 1)
			drawTexturedModalRect(guiLeft + 157, guiTop + 23, 176, 0, 6, 8);
		if(launcher.solidState() == 0)
			drawTexturedModalRect(guiLeft + 157, guiTop + 23, 182, 0, 6, 8);
		
		switch(launcher.padSize) {
		
		case SIZE_10:
			drawTexturedModalRect(guiLeft + 7, guiTop + 98, 176, 8, 18, 18);
			break;
		case SIZE_15:
			drawTexturedModalRect(guiLeft + 25, guiTop + 98, 194, 8, 18, 18);
			break;
		case SIZE_20:
			drawTexturedModalRect(guiLeft + 43, guiTop + 98, 212, 8, 18, 18);
			break;
		default:
			break;
		
		}
		
		this.drawInfoPanel(guiLeft - 16, guiTop + 36, 16, 16, 2);
		this.drawInfoPanel(guiLeft - 16, guiTop + 36 + 16, 16, 16, 11);
		
		FFUtils.drawLiquid(launcher.tanks[0], guiLeft, guiTop, zLevel, 16, 34, 116, 98);
		FFUtils.drawLiquid(launcher.tanks[1], guiLeft, guiTop, zLevel, 16, 34, 134, 98);
		
		/// DRAW MISSILE START
		GL11.glPushMatrix();

		MissileMultipart missile;
		
		if(launcher.isMissileValid()) {
			ItemStack custom = launcher.inventory.getStackInSlot(0);
			
			missile = new MissileMultipart();
			
			missile = MissileMultipart.loadFromStruct(ItemCustomMissile.getStruct(custom));
		
			GL11.glTranslatef(guiLeft + 88, guiTop + 115, 100);
			
			double size = 5 * 18;
			double scale = size / Math.max(missile.getHeight(), 6);

			GL11.glRotatef(90, 0, 1, 0);
			GL11.glTranslated(missile.getHeight() / 2D * scale, 0, 0);
			GL11.glScaled(scale, scale, scale);
			
			GL11.glScalef(-1, -1, -1);
			
			MissilePronter.prontMissile(missile, Minecraft.getMinecraft().getTextureManager());
		}
		
		GL11.glPopMatrix();
		/// DRAW MISSILE END
	}
}
