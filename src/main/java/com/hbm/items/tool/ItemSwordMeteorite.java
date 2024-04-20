package com.hbm.items.tool;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemSwordMeteorite extends ItemSwordAbility {

	public ItemSwordMeteorite(float damage, double movement, ToolMaterial material, String s) {
		super(damage, movement, material, s);
		this.setMaxDamage(0);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, list, flagIn);
		if(this == ModItems.meteorite_sword) {
    		list.add(TextFormatting.ITALIC + "Forged from a fallen star");
    		list.add(TextFormatting.ITALIC + "Sharper than most terrestrial blades");
    	}

    	if(this == ModItems.meteorite_sword_seared) {
    		list.add(TextFormatting.ITALIC + "Fire strengthens the blade");
    		list.add(TextFormatting.ITALIC + "Making it even more powerful");
    	}

    	if(this == ModItems.meteorite_sword_reforged) {
    		list.add(TextFormatting.ITALIC + "The sword has been reforged");
    		list.add(TextFormatting.ITALIC + "To rectify past imperfections");
    	}

    	if(this == ModItems.meteorite_sword_hardened) {
    		list.add(TextFormatting.ITALIC + "Extremely high pressure has been used");
    		list.add(TextFormatting.ITALIC + "To harden the blade further");
    	}

    	if(this == ModItems.meteorite_sword_alloyed) {
    		list.add(TextFormatting.ITALIC + "Cobalt fills the fissures");
    		list.add(TextFormatting.ITALIC + "Strengthening the sword");
    	}

    	if(this == ModItems.meteorite_sword_machined) {
    		list.add(TextFormatting.ITALIC + "Advanced machinery was used");
    		list.add(TextFormatting.ITALIC + "To refine the blade even more");
    	}

    	if(this == ModItems.meteorite_sword_treated) {
    		list.add(TextFormatting.ITALIC + "Chemicals have been applied");
    		list.add(TextFormatting.ITALIC + "Making the sword more powerful");
    	}

    	if(this == ModItems.meteorite_sword_etched) {
    		list.add(TextFormatting.ITALIC + "Acids clean the material");
    		list.add(TextFormatting.ITALIC + "To make this the perfect sword");
    	}
    	
    	if(this == ModItems.meteorite_sword_bred) {
    		list.add(TextFormatting.ITALIC + "Immense heat and radiation");
    		list.add(TextFormatting.ITALIC + "Compress the material");
    	}

    	if(this == ModItems.meteorite_sword_irradiated) {
    		list.add(TextFormatting.ITALIC + "The power of the Atom");
    		list.add(TextFormatting.ITALIC + "Gives the sword might");
    	}

    	if(this == ModItems.meteorite_sword_fused) {
    		list.add(TextFormatting.ITALIC + "This blade has met");
    		list.add(TextFormatting.ITALIC + "With the forces of the stars");
    	}

    	if(this == ModItems.meteorite_sword_baleful) {
    		list.add(TextFormatting.ITALIC + "This sword has met temperatures");
    		list.add(TextFormatting.ITALIC + "Far beyond what normal material can endure");
    	}

    	if(this == ModItems.meteorite_sword_warped) {
    		list.add(TextFormatting.ITALIC + "This sword experienced warping of reality");
    		list.add(TextFormatting.ITALIC + "It was stretched to a length of 10^10^187 ly");
    		list.add(TextFormatting.ITALIC + "and is now older than this universe");
    	}

    	if(this == ModItems.meteorite_sword_demonic) {
    		list.add(TextFormatting.ITALIC + "This sword has met §f§oGOD§7§o and the §4§oDEVIL§r");
    		list.add(TextFormatting.ITALIC + "It was transported to §4§o§kdemoniclove§7");
    		list.add(TextFormatting.ITALIC + "and came in contact with §4§o§ktheevilandthegood§r");
    		list.add("§0[Infohazard]§r");
    	}
	}

}
