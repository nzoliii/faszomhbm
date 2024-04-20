package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntitySolarMirror;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class RenderSolarMirror extends TileEntitySpecialRenderer<TileEntitySolarMirror> {

	@Override
	public boolean isGlobalRenderer(TileEntitySolarMirror te) {
		return true;
	}
	
	@Override
	public void render(TileEntitySolarMirror te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
        
        TileEntitySolarMirror mirror = (TileEntitySolarMirror)te;

        bindTexture(ResourceManager.solar_mirror_tex);
        ResourceManager.solar_mirror.renderPart("Base");

        GL11.glTranslated(0, 1, 0);

    	int dx = mirror.tX - mirror.getPos().getX();
    	int dy = mirror.tY - mirror.getPos().getY();
    	int dz = mirror.tZ - mirror.getPos().getZ();

    	double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);

        if(mirror.tY >= mirror.getPos().getY()) {

        	double pitch = Math.toDegrees(-Math.asin((dy + 0.5) / dist)) + 90;
        	double yaw = Math.toDegrees(-Math.atan2(dz, dx)) + 180;

        	GL11.glRotated(yaw, 0, 1, 0);
        	GL11.glRotated(pitch, 0, 0, 1);
        }

        GL11.glTranslated(0, -1, 0);
        ResourceManager.solar_mirror.renderPart("Mirror");

        if(mirror.isOn) {
			float min = 0.008F;
	        float max = 0.008F;

	        Tessellator tess = Tessellator.getInstance();
	        BufferBuilder buf = tess.getBuffer();
	        GlStateManager.disableTexture2D();
	        GlStateManager.disableLighting();
	        GlStateManager.enableBlend();
	        GlStateManager.shadeModel(GL11.GL_SMOOTH);
	        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
			GlStateManager.alphaFunc(GL11.GL_GREATER, 0);
			GlStateManager.depthMask(false);


	        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
	        buf.pos(0.5, 1.0625, 0.5).color(1F, 1F, 1F, max).endVertex();
	        buf.pos(0.5, 1.0625, -0.5).color(1F, 1F, 1F, max).endVertex();
	        buf.pos(0.5, dist, -0.5).color(1F, 1F, 1F, min).endVertex();
	        buf.pos(0.5, dist, 0.5).color(1F, 1F, 1F, min).endVertex();

	        buf.pos(-0.5, 1.0625, 0.5).color(1F, 1F, 1F, max).endVertex();
	        buf.pos(-0.5, 1.0625, -0.5).color(1F, 1F, 1F, max).endVertex();
	        buf.pos(-0.5, dist, -0.5).color(1F, 1F, 1F, min).endVertex();
	        buf.pos(-0.5, dist, 0.5).color(1F, 1F, 1F, min).endVertex();

	        buf.pos(0.5, 1.0625, 0.5).color(1F, 1F, 1F, max).endVertex();
	        buf.pos(-0.5, 1.0625, 0.5).color(1F, 1F, 1F, max).endVertex();
	        buf.pos(-0.5, dist, 0.5).color(1F, 1F, 1F, min).endVertex();
	        buf.pos(0.5, dist, 0.5).color(1F, 1F, 1F, min).endVertex();

	        buf.pos(0.5, 1.0625, -0.5).color(1F, 1F, 1F, max).endVertex();
	        buf.pos(-0.5, 1.0625, -0.5).color(1F, 1F, 1F, max).endVertex();
	        buf.pos(-0.5, dist, -0.5).color(1F, 1F, 1F, min).endVertex();
	        buf.pos(0.5, dist, -0.5).color(1F, 1F, 1F, min).endVertex();
	        tess.draw();

	        GlStateManager.depthMask(true);
	        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
	        GlStateManager.shadeModel(GL11.GL_FLAT);
	        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
	        GlStateManager.disableBlend();
	        GlStateManager.enableLighting();
	        GlStateManager.enableTexture2D();
        }

        GL11.glPopMatrix();
	}
}
