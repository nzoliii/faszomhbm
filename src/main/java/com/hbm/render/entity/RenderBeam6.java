package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityModBeam;
import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderBeam6 extends Render<EntityModBeam> {

	public static final IRenderFactory<EntityModBeam> FACTORY = (RenderManager man) -> {
		return new RenderBeam6(man);
	};

	private static ResourceLocation beam_rl = new ResourceLocation(RefStrings.MODID + ":textures/models/projectiles/PlasmaBeam.png");

	protected RenderBeam6(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(EntityModBeam rocket, double x, double y, double z, float entityYaw, float partialTicks) {
		// float radius = 0.12F;
		// float radius = 0.06F;
		GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT);
		float radius = 0.175F;
		int distance = 2;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buf = tessellator.getBuffer();

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glTranslatef((float) x, (float) y, (float) z);

		GL11.glRotatef(rocket.rotationYaw, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-rocket.rotationPitch, 1.0F, 0.0F, 0.0F);

		boolean red = System.currentTimeMillis() % 250 < 124;
		boolean green = false;
		boolean blue = !red;
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		for (float o = 0; o <= radius; o += radius / 8) {
			float color = 1f - (o * 8.333f);
			if (color < 0)
				color = 0;

			buf.pos(0 + o, 0 - o, 0).color(red ? 1 : color, green ? 1 : color, blue ? 1 : color, 1f).endVertex();
			buf.pos(0 + o, 0 + o, 0).color(red ? 1 : color, green ? 1 : color, blue ? 1 : color, 1f).endVertex();
			buf.pos(0 + o, 0 + o, 0 + distance).color(red ? 1 : color, green ? 1 : color, blue ? 1 : color, 1f).endVertex();
			buf.pos(0 + o, 0 - o, 0 + distance).color(red ? 1 : color, green ? 1 : color, blue ? 1 : color, 1f).endVertex();

			buf.pos(0 - o, 0 - o, 0).color(red ? 1 : color, green ? 1 : color, blue ? 1 : color, 1f).endVertex();
			buf.pos(0 + o, 0 - o, 0).color(red ? 1 : color, green ? 1 : color, blue ? 1 : color, 1f).endVertex();
			buf.pos(0 + o, 0 - o, 0 + distance).color(red ? 1 : color, green ? 1 : color, blue ? 1 : color, 1f).endVertex();
			buf.pos(0 - o, 0 - o, 0 + distance).color(red ? 1 : color, green ? 1 : color, blue ? 1 : color, 1f).endVertex();

			buf.pos(0 - o, 0 + o, 0).color(red ? 1 : color, green ? 1 : color, blue ? 1 : color, 1f).endVertex();
			buf.pos(0 - o, 0 - o, 0).color(red ? 1 : color, green ? 1 : color, blue ? 1 : color, 1f).endVertex();
			buf.pos(0 - o, 0 - o, 0 + distance).color(red ? 1 : color, green ? 1 : color, blue ? 1 : color, 1f).endVertex();
			buf.pos(0 - o, 0 + o, 0 + distance).color(red ? 1 : color, green ? 1 : color, blue ? 1 : color, 1f).endVertex();

			buf.pos(0 + o, 0 + o, 0).color(red ? 1 : color, green ? 1 : color, blue ? 1 : color, 1f).endVertex();
			buf.pos(0 - o, 0 + o, 0).color(red ? 1 : color, green ? 1 : color, blue ? 1 : color, 1f).endVertex();
			buf.pos(0 - o, 0 + o, 0 + distance).color(red ? 1 : color, green ? 1 : color, blue ? 1 : color, 1f).endVertex();
			buf.pos(0 + o, 0 + o, 0 + distance).color(red ? 1 : color, green ? 1 : color, blue ? 1 : color, 1f).endVertex();
		}
		tessellator.draw();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	@Override
	public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityModBeam entity) {
		return beam_rl;
	}

}
