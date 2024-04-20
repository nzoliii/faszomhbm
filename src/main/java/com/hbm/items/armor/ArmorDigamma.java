package com.hbm.items.armor;

import com.hbm.render.model.ModelArmorDigamma;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ArmorDigamma extends ArmorFSBPowered {

	public ArmorDigamma(ArmorMaterial material, int layer, EntityEquipmentSlot slot, String texture, long maxPower, long chargeRate, long consumption, long drain, String s) {
		super(material, layer, slot, texture, maxPower, chargeRate, consumption, drain, s);
	}

	@SideOnly(Side.CLIENT)
	ModelArmorDigamma[] models;

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default){
		if(models == null) {
			models = new ModelArmorDigamma[4];
			
			for(int i = 0; i < 4; i++)
				models[i] = new ModelArmorDigamma(i);
		}
		
		return models[armorSlot.getIndex()];
	}
	
}