package com.hbm.items.armor;

import java.util.List;

import com.hbm.util.I18nUtil;
import org.lwjgl.opengl.GL11;
import com.google.common.collect.Multimap;
import com.hbm.handler.ArmorModHandler;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemCustomLore;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArmorMod extends ItemCustomLore {

	public final int type;
	public final boolean helmet;
	public final boolean chestplate;
	public final boolean leggings;
	public final boolean boots;
	
	public ItemArmorMod(int type, boolean helmet, boolean chestplate, boolean leggings, boolean boots, String s) {
		super(s);
		this.type = type;
		this.helmet = helmet;
		this.chestplate = chestplate;
		this.leggings = leggings;
		this.boots = boots;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, list, flagIn);
		list.add(TextFormatting.DARK_PURPLE + I18nUtil.resolveKey("desc.applicable"));
		
		if(helmet && chestplate && leggings && boots) {
			list.add("  " + I18nUtil.resolveKey("desc.applicableall"));
		} else {

			if(helmet)
				list.add("  " + I18nUtil.resolveKey("desc.applicableh"));
			if(chestplate)
				list.add("  " + I18nUtil.resolveKey("desc.applicablec"));
			if(leggings)
				list.add("  " + I18nUtil.resolveKey("desc.applicablel"));
			if(boots)
				list.add("  " + I18nUtil.resolveKey("desc.applicableb"));
		}
		list.add(TextFormatting.DARK_PURPLE + I18nUtil.resolveKey("desc.applicableslot"));
		
		switch(this.type) {
		case ArmorModHandler.helmet_only: list.add("  " + I18nUtil.resolveKey("desc.applicableh1")); break;
		case ArmorModHandler.plate_only: list.add("  " + I18nUtil.resolveKey("desc.applicablec1")); break;
		case ArmorModHandler.legs_only: list.add("  " + I18nUtil.resolveKey("desc.applicablel1")); break;
		case ArmorModHandler.boots_only: list.add("  " + I18nUtil.resolveKey("desc.applicableb1")); break;
		case ArmorModHandler.servos: list.add("  " + I18nUtil.resolveKey("desc.applicableservo")); break;
		case ArmorModHandler.cladding: list.add("  " + I18nUtil.resolveKey("desc.applicablecladding")); break;
		case ArmorModHandler.kevlar: list.add("  " + I18nUtil.resolveKey("desc.applicableinsert")); break;
		case ArmorModHandler.extra: list.add("  " + I18nUtil.resolveKey("desc.applicableextra")); break;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void addDesc(List<String> list, ItemStack stack, ItemStack armor) {
		list.add(stack.getDisplayName());
	}
	
	public void modUpdate(EntityLivingBase entity, ItemStack armor) { }
	
	public void modDamage(LivingHurtEvent event, ItemStack armor) { }
	
	public Multimap<String, AttributeModifier> getModifiers(EntityEquipmentSlot slot, ItemStack armor) { return null; }
	
	@SideOnly(Side.CLIENT)
	public void modRender(RenderPlayerEvent.Pre event, ItemStack armor) { }

	public void offset(EntityPlayer player, EntityPlayer me, float interp){
		float px = (float)(player.prevPosX + (player.posX - player.prevPosX) * interp);
		float py = (float)(player.prevPosY + (player.posY - player.prevPosY) * interp);
		float pz = (float)(player.prevPosZ + (player.posZ - player.prevPosZ) * interp);
		float mx = (float)(me.prevPosX + (me.posX - me.prevPosX) * interp);
		float my = (float)(me.prevPosY + (me.posY - me.prevPosY) * interp);
		float mz = (float)(me.prevPosZ + (me.posZ - me.prevPosZ) * interp);
		GL11.glTranslatef(mx-px, my-py, pz-mz);
	}

	public void copyRot(ModelBiped model, ModelBiped body){
	 	model.bipedHead.rotationPointX = body.bipedHead.rotationPointX;
        model.bipedHead.rotationPointY = body.bipedHead.rotationPointY;
        model.bipedHead.rotationPointY = body.bipedHead.rotationPointY;
        model.bipedHead.rotateAngleY = body.bipedHead.rotateAngleY;
        model.bipedHead.rotateAngleY = body.bipedHead.rotateAngleY;
        model.bipedHead.rotateAngleX = body.bipedHead.rotateAngleX;
        model.bipedHead.offsetX = body.bipedHead.offsetX;
        model.bipedHead.offsetY = body.bipedHead.offsetY;
        model.bipedHead.offsetZ = body.bipedHead.offsetZ;

        model.bipedBody.rotationPointX = body.bipedBody.rotationPointX;
        model.bipedBody.rotationPointY = body.bipedBody.rotationPointY;
        model.bipedBody.rotationPointZ = body.bipedBody.rotationPointZ;
        model.bipedBody.rotateAngleX = body.bipedBody.rotateAngleX;
        model.bipedBody.rotateAngleY = body.bipedBody.rotateAngleY;
        model.bipedBody.rotateAngleZ = body.bipedBody.rotateAngleZ;
        model.bipedBody.offsetX = body.bipedBody.offsetX;
        model.bipedBody.offsetY = body.bipedBody.offsetY;
        model.bipedBody.offsetZ = body.bipedBody.offsetZ;

        model.isSneak = body.isSneak;
		model.isChild = body.isChild;
    }
}
