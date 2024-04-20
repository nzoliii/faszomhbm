package com.hbm.render.entity.mob;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.mob.EntityRADBeast;
import com.hbm.lib.RefStrings;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.render.misc.BeamPronter;
import com.hbm.render.misc.BeamPronter.EnumBeamType;
import com.hbm.render.misc.BeamPronter.EnumWaveType;
import com.hbm.render.model.ModelM65Blaze;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderRADBeast extends RenderLiving<EntityRADBeast> {

	public static final IRenderFactory<EntityRADBeast> FACTORY = man -> new RenderRADBeast(man);
	
	private static final ResourceLocation blazeTextures = new ResourceLocation(RefStrings.MODID, "textures/entity/radbeast.png");
    private static final ResourceLocation mask = new ResourceLocation(RefStrings.MODID, "textures/armor/ModelM65Blaze.png");
	
	public RenderRADBeast(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelBlaze(), 0.5F);
	}

	@Override
	public void doRender(EntityRADBeast entity, double x, double y, double z, float entityYaw, float partialTicks) {
		Entity victim = entity.getUnfortunateSoul();
		
		if(victim != null) {

			GL11.glPushMatrix();
			
            GL11.glTranslated(x, y + 1.25, z);
            
	        double sx = entity.posX;
	        double sy = entity.posY + 1.25;
	        double sz = entity.posZ;

	        double tX = victim.posX;
	        double tY = victim.posY + victim.height / 2;
	        double tZ = victim.posZ;
	        
	        if(victim == Minecraft.getMinecraft().player)
	        	tY -= 1.5;
	        
	    	double length = Math.sqrt(Math.pow(tX - sx, 2) + Math.pow(tY - sy, 2) + Math.pow(tZ - sz, 2));
	        BeamPronter.prontBeam(Vec3.createVectorHelper(tX - sx, tY - sy, tZ - sz), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x004000, 0x004000, (int) (entity.world.getTotalWorldTime() % 1000 + 1), (int) (length * 5), 0.125F, 2, 0.03125F);
			
	        GL11.glPopMatrix();
		}
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityRADBeast entity) {
		return blazeTextures;
	}
	
	private ModelM65Blaze modelM65;
	
	@Override
	protected void renderModel(EntityRADBeast entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		this.bindTexture(mask);
			
		if(this.modelM65 == null){
			this.modelM65 = new ModelM65Blaze();
		}
		modelM65.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
		
		super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
	}
	
}
