package com.hbm.render.tileentity;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineChemfac;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

public class RenderChemfac extends TileEntitySpecialRenderer<TileEntityMachineChemfac> {
	@Override
	public boolean isGlobalRenderer(TileEntityMachineChemfac te) {
		return true;
	}

	@Override
	public void render(TileEntityMachineChemfac chemfac, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);

		switch(chemfac.getBlockMetadata() - BlockDummyable.offset) {
			case 5: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 2: GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 4: GL11.glRotatef(0, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(90, 0F, 1F, 0F); break;
		}

		GL11.glTranslated(0.5D, 0.0D, -0.5D);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.chemfac_tex);
		ResourceManager.chemfac.renderPart("Main");

		float rot = chemfac.prevRot + (chemfac.rot - chemfac.prevRot) * partialTicks;

		GL11.glPushMatrix();
		GL11.glTranslated(1, 0, 0);
		GL11.glRotated(rot, 0, -1, 0);
		GL11.glTranslated(-1, 0, 0);
		ResourceManager.chemfac.renderPart("Fan1");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(-1, 0, 0);
		GL11.glRotated(rot, 0, -1, 0);
		GL11.glTranslated(1, 0, 0);
		ResourceManager.chemfac.renderPart("Fan2");
		GL11.glPopMatrix();

		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}
}
