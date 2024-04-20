package com.hbm.particle.tau;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.util.BobMathUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleTauParticle extends Particle {

	public float workingAlpha;
	public int timeUntilChange = 0;
	public int timeUntilChangeRand = 6;
	public int timeUntilNextRand;
	public float randomness;
	public float mX, mY, mZ;
	
	public ParticleTauParticle(World worldIn, double posXIn, double posYIn, double posZIn, float scale, float randomness, int timeUntilNextRand, int timeUntilChangeRand, float grav) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.particleScale = scale;
		this.motionX = (rand.nextFloat()-0.5)*randomness;
		this.motionY = (rand.nextFloat()-0.5)*randomness;
		this.motionZ = (rand.nextFloat()-0.5)*randomness;
		this.timeUntilChangeRand = timeUntilChangeRand;
		this.timeUntilChange = rand.nextInt(timeUntilChangeRand)+timeUntilNextRand;
		this.particleGravity = grav;
		this.randomness = randomness;
		this.timeUntilNextRand = timeUntilNextRand;
	}
	
	public ParticleTauParticle motion(float mX, float mY, float mZ){
		this.motionX += mX;
		this.motionX += mX;
		this.motionX += mX;
		this.mX = mX;
		this.mY = mY;
		this.mZ = mZ;
		return this;
	}
	
	public ParticleTauParticle color(float colR, float colG, float colB, float colA){
		this.particleRed = colR;
		this.particleGreen = colG;
		this.particleBlue = colB;
		this.particleAlpha = colA;
		workingAlpha = colA;
		return this;
	}
	
	public ParticleTauParticle lifetime(int lifetime){
		this.particleMaxAge = lifetime;
		return this;
	}
	
	@Override
	public void onUpdate() {
		this.particleAge ++;
		timeUntilChange --;
		if(this.particleAge >= this.particleMaxAge){
			this.setExpired();
		}
		this.prevPosX = posX;
		this.prevPosY = posY;
		this.prevPosZ = posZ;
		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		this.mY -= particleGravity;
		this.mX*=0.90F;
		this.mY*=0.90F;
		this.mZ*=0.90F;
		if(timeUntilChange == 0){
			timeUntilChange = rand.nextInt(timeUntilChangeRand)+timeUntilNextRand;
			this.motionX = (rand.nextFloat()-0.5)*randomness+mX;
			this.motionY = (rand.nextFloat()-0.5)*randomness+mY;
			this.motionZ = (rand.nextFloat()-0.5)*randomness+mZ;
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
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.fresnel_ms);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GlStateManager.disableAlpha();
		GlStateManager.depthMask(false);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
		RenderHelper.enableStandardItemLighting();
		float timeScale = (this.particleAge+partialTicks)/(float)this.particleMaxAge;
		float shrink = MathHelper.clamp(1-BobMathUtil.remap((float)MathHelper.clamp(timeScale, 0, 1), 0.6F, 1F, 0.6F, 1F), 0, 1);
		this.workingAlpha = shrink*particleAlpha;
		
		float f4 = 0.1F * this.particleScale;
        
        float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX);
        float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY);
        float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ);
        Vec3d[] avec3d = new Vec3d[] {new Vec3d((double)(-rotationX * f4 - rotationXY * f4), (double)(-rotationZ * f4), (double)(-rotationYZ * f4 - rotationXZ * f4)), new Vec3d((double)(-rotationX * f4 + rotationXY * f4), (double)(rotationZ * f4), (double)(-rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double)(rotationX * f4 + rotationXY * f4), (double)(rotationZ * f4), (double)(rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double)(rotationX * f4 - rotationXY * f4), (double)(-rotationZ * f4), (double)(rotationYZ * f4 - rotationXZ * f4))};
        
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
        
        float a = workingAlpha;
        for(int i = 0; i < workingAlpha; i ++){
	        buffer.pos((double)f5 + avec3d[0].x, (double)f6 + avec3d[0].y, (double)f7 + avec3d[0].z).tex(1, 1).color(this.particleRed, this.particleGreen, this.particleBlue, Math.min(a, 1)).lightmap(240, 240).endVertex();
	        buffer.pos((double)f5 + avec3d[1].x, (double)f6 + avec3d[1].y, (double)f7 + avec3d[1].z).tex(1, 0).color(this.particleRed, this.particleGreen, this.particleBlue, Math.min(a, 1)).lightmap(240, 240).endVertex();
	        buffer.pos((double)f5 + avec3d[2].x, (double)f6 + avec3d[2].y, (double)f7 + avec3d[2].z).tex(0, 0).color(this.particleRed, this.particleGreen, this.particleBlue, Math.min(a, 1)).lightmap(240, 240).endVertex();
	        buffer.pos((double)f5 + avec3d[3].x, (double)f6 + avec3d[3].y, (double)f7 + avec3d[3].z).tex(0, 1).color(this.particleRed, this.particleGreen, this.particleBlue, Math.min(a, 1)).lightmap(240, 240).endVertex();
	        a-=1;
        }
        
        Tessellator.getInstance().draw();
        
        GlStateManager.enableAlpha();
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
	}

}