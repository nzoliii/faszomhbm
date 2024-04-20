package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.loader.ModelRendererObj;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelArmorRPA extends ModelArmorBase {

    public ModelArmorRPA(int type) {
        super(type);

        head = new ModelRendererObj(ResourceManager.armor_RPA, "Head");
        body = new ModelRendererObj(ResourceManager.armor_RPA, "Body");
        leftArm = new ModelRendererObj(ResourceManager.armor_RPA, "LeftArm").setRotationPoint(-5.0F, 2.0F, 0.0F);
        rightArm = new ModelRendererObj(ResourceManager.armor_RPA, "RightArm").setRotationPoint(5.0F, 2.0F, 0.0F);
        leftLeg = new ModelRendererObj(ResourceManager.armor_RPA, "LeftLeg").setRotationPoint(1.9F, 12.0F, 0.0F);
        rightLeg = new ModelRendererObj(ResourceManager.armor_RPA, "RightLeg").setRotationPoint(-1.9F, 12.0F, 0.0F);
        leftFoot = new ModelRendererObj(ResourceManager.armor_RPA, "LeftBoot").setRotationPoint(1.9F, 12.0F, 0.0F);
        rightFoot = new ModelRendererObj(ResourceManager.armor_RPA, "RightBoot").setRotationPoint(-1.9F, 12.0F, 0.0F);
    }

    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {

        setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);

        GL11.glPushMatrix();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        if(this.isChild) {
            GL11.glScalef(0.75F, 0.75F, 0.75F);
            GL11.glTranslatef(0.0F, 16.0F * par7, 0.0F);
        }
        if(type == 3) {
            Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.rpa_helmet);
            head.render(par7*1.05F);
        }
        if(this.isChild) {
            GL11.glScalef(0.75F, 0.75F, 0.75F);
        }
        if(type == 2) {
            Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.rpa_chest);
            body.render(par7*1.05F);
            Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.rpa_arm);
            leftArm.render(par7*1.05F);
            rightArm.render(par7*1.05F);
        }
        if(type == 1) {
            Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.rpa_leg);
            leftLeg.render(par7*1.05F);
            rightLeg.render(par7*1.05F);
        }
        if(type == 0) {
            Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.rpa_leg);
            leftFoot.render(par7*1.05F);
            rightFoot.render(par7*1.05F);
        }

        GlStateManager.shadeModel(GL11.GL_FLAT);
        GL11.glPopMatrix();
    }
}