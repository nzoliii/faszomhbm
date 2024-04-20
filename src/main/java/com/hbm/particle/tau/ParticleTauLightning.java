package com.hbm.particle.tau;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.particle.ParticleFirstPerson;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleTauLightning extends ParticleFirstPerson {

	public float workingAlpha;
	public float rotationOverLife;
	
	public ParticleTauLightning(World worldIn, double posXIn, double posYIn, double posZIn, float scale, float rot) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.particleScale = scale;
		this.rotationOverLife = rot;
		this.particleAngle = rand.nextFloat()*360;
	}
	
	public ParticleTauLightning color(float colR, float colG, float colB, float colA){
		this.particleRed = colR;
		this.particleGreen = colG;
		this.particleBlue = colB;
		this.particleAlpha = colA;
		workingAlpha = colA;
		return this;
	}
	
	public ParticleTauLightning lifetime(int lifetime){
		this.particleMaxAge = lifetime;
		return this;
	}
	
	@Override
	public void onUpdate() {
		this.particleAge ++;
		if(this.particleAge >= this.particleMaxAge){
			this.setExpired();
		}
	}
	
	@Override
	public boolean shouldDisableDepth() {
		return true;
	}
	
	@Override
	public int getFXLayer() {
		return 3;
	}
	
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		GL11.glPushMatrix();
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.tau_lightning);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GlStateManager.disableAlpha();
		GlStateManager.depthMask(false);
		GlStateManager.enableBlend();
		GlStateManager.disableCull();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
		float timeScale = (this.particleAge+partialTicks)/(float)this.particleMaxAge;
		this.workingAlpha = MathHelper.clamp(1-BobMathUtil.remap((float)MathHelper.clamp(timeScale, 0, 1), 0.6F, 1F, 0.6F, 1F), 0, 1)*particleAlpha;
		workingAlpha *= MathHelper.clamp(BobMathUtil.remap((float)MathHelper.clamp(timeScale, 0, 0.2), 0F, 0.2F, 0F, 1F), 0, 1);
		
		float f4 = 0.1F * (this.particleScale+timeScale*5F);
		
        float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks);
        float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks);
        float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks);
        GL11.glTranslated(f5, f6, f7);
        GL11.glScalef(f4, f4, f4);
		GL11.glRotated(this.particleAngle+timeScale*rotationOverLife, 1, 0, 0);
        Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
        
        buffer.pos(0, 0.5, 0.5).tex(1, 1).color(this.particleRed, this.particleGreen, this.particleBlue, this.workingAlpha).lightmap(240, 240).endVertex();
        buffer.pos(0, 0.5, -0.5).tex(1, 0).color(this.particleRed, this.particleGreen, this.particleBlue, this.workingAlpha).lightmap(240, 240).endVertex();
        buffer.pos(0, -0.5, -0.5).tex(0, 0).color(this.particleRed, this.particleGreen, this.particleBlue, this.workingAlpha).lightmap(240, 240).endVertex();
        buffer.pos(0, -0.5, 0.5).tex(0, 1).color(this.particleRed, this.particleGreen, this.particleBlue, this.workingAlpha).lightmap(240, 240).endVertex();
	
        Tessellator.getInstance().draw();
        
        GlStateManager.enableAlpha();
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
	}
	
	@Override
	public ParticleType getType() {
		return ParticleType.TAU;
	}

}