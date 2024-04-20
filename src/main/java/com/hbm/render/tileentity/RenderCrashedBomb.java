package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.bomb.TileEntityCrashedBomb;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderCrashedBomb extends TileEntitySpecialRenderer<TileEntityCrashedBomb> {
    
    @Override
    public boolean isGlobalRenderer(TileEntityCrashedBomb te) {
    	return true;
    }
    
    @Override
    public void render(TileEntityCrashedBomb te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    	GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.disableCull();
        GlStateManager.enableLighting();
		switch(te.getBlockMetadata())
		{
		case 5:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 2:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(-90, 0F, 1F, 0F); break;
		}

		bindTexture(ResourceManager.dud_tex);
	    ResourceManager.dud.renderAll();

        GlStateManager.enableCull();
        GL11.glPopMatrix();
    }
}
