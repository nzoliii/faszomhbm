package com.hbm.inventory.gui;

import com.hbm.inventory.container.ContainerCraneGrabber;
import com.hbm.lib.RefStrings;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.network.TileEntityCraneGrabber;
import com.hbm.util.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.Arrays;

public class GUICraneGrabber extends GuiInfoContainer {
    private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/storage/gui_crane_grabber.png");
    public TileEntityCraneGrabber grabber;

    public GUICraneGrabber(InventoryPlayer invPlayer, TileEntityCraneGrabber tedf) {
        super(new ContainerCraneGrabber(invPlayer, tedf));
        grabber = tedf;

        this.xSize = 176;
        this.ySize = 185;
    }

    @Override
    public void drawScreen(int x, int y, float interp) {
        super.drawScreen(x, y, interp);

        if(this.mc.player.getHeldItemMainhand().isEmpty()) {
            for(int i = 0; i < 9; ++i) {
                Slot slot = (Slot) this.inventorySlots.inventorySlots.get(i);

                if(this.isMouseOverSlot(slot, x, y) && grabber.matcher.modes[i] != null) {

                    String label = TextFormatting.YELLOW + "";

                    switch(grabber.matcher.modes[i]) {
                        case "exact": label += I18nUtil.resolveKey("desc.exact"); break;
                        case "wildcard": label += I18nUtil.resolveKey("desc.wildcard"); break;
                        default: label += I18nUtil.resolveKey("desc.oredictmatch")+" " + grabber.matcher.modes[i]; break;
                    }

                    this.drawHoveringText(Arrays.asList(new String[] { TextFormatting.RED + I18nUtil.resolveKey("desc.rcchange"), label }), x, y - 30);
                }
            }
        }
        this.renderHoveredToolTip(x, y);
    }

    @Override
    protected void mouseClicked(int x, int y, int i) throws IOException {
        super.mouseClicked(x, y, i);

        if(guiLeft + 97 <= x && guiLeft + 97 + 14 > x && guiTop + 30 < y && guiTop + 30 + 26 >= y) {

            mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            NBTTagCompound data = new NBTTagCompound();
            data.setBoolean("isWhitelist", true);
            PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(data, grabber.getPos()));
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int i, int j) {
        String name = this.grabber.hasCustomInventoryName() ? this.grabber.getInventoryName() : I18n.format(this.grabber.getInventoryName());
        this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        super.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        if(grabber.isWhitelist) {
            drawTexturedModalRect(guiLeft + 108, guiTop + 33, 176, 0, 3, 6);
        } else {
            drawTexturedModalRect(guiLeft + 108, guiTop + 47, 176, 0, 3, 6);
        }
    }
}
