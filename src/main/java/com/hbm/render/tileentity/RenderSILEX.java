package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntitySILEX;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderSILEX extends TileEntitySpecialRenderer<TileEntitySILEX> {
	
	@Override
	public void render(TileEntitySILEX te, double x, double y, double z, float partialTicks, int destroyStage, float alpha){
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GlStateManager.enableLighting();
		GlStateManager.enableCull();
		
		switch(te.getBlockMetadata() - BlockDummyable.offset) {
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		}
		
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.silex_tex);
		ResourceManager.silex.renderAll();
		GlStateManager.shadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}
}
