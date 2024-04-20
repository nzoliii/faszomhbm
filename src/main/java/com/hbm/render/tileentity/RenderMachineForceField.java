package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.tileentity.machine.TileEntityForceField;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class RenderMachineForceField extends TileEntitySpecialRenderer<TileEntityForceField> {

	@Override
	public boolean isGlobalRenderer(TileEntityForceField te) {
		return true;
	}
	
	@Override
	public void render(TileEntityForceField te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
		GL11.glRotatef(180, 0F, 1F, 0F);
        bindTexture(ResourceManager.radar_base_tex);
        ResourceManager.radar.renderPart("Base");
        
        TileEntityForceField ff = (TileEntityForceField)te;

        GL11.glTranslated(0, 0.5D, 0);
        
        //double rot = (System.currentTimeMillis() / 10D) % 360;
        
        int segments = (int)(16 + ff.radius * 0.125);
        
        bindTexture(ResourceManager.forcefield_top_tex);

        if(ff.isOn && ff.health > 0 && ff.power > 0 && ff.cooldown == 0) {
        	generateSphere(segments, segments * 2, ff.radius, ff.color);
            
            double rot = (System.currentTimeMillis() * 0.5D) % 360;
    		GL11.glRotated(-rot, 0F, 1F, 0F);
        }

        GL11.glTranslated(0, 0.5, 0);
    	ResourceManager.forcefield_top.renderAll();
        GL11.glTranslated(0, -0.5, 0);
        
        GlStateManager.enableCull();
        GL11.glPopMatrix();
	}
	
	private void generateSphere(int l, int s, float rad, int hex) {

		float r = (hex >> 16 & 255)/255F;
		float g = (hex >> 8 & 255)/255F;
		float b = (hex & 255)/255F;
		
        GL11.glPushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        
        float sRot = 360F / s;
        float lRot = (float)Math.PI / l;
        
        Tessellator tes = Tessellator.getInstance();
        BufferBuilder buf = tes.getBuffer();
        
        
        for(int k = 0; k < s; k++) {
        	buf.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
    		GL11.glRotatef(sRot, 0F, 1F, 0F);
    		
	        Vec3 vec = Vec3.createVectorHelper(0, rad, 0);
	        
	        for(int i = 0; i < l; i++) {

	            buf.pos(vec.xCoord, vec.yCoord, vec.zCoord).color(r, g, b, 1.0F).endVertex();
	        	vec.rotateAroundX(lRot);
	            buf.pos(vec.xCoord, vec.yCoord, vec.zCoord).color(r, g, b, 1.0F).endVertex();
	        }
	        tes.draw();
        }
        

        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GL11.glPopMatrix();
	}
}
