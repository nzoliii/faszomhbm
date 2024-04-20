package com.hbm.items.tool;

import java.util.List;

import com.google.common.collect.Multimap;
import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKCraneConsole;
import com.hbm.util.I18nUtil;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemRBMKTool extends Item {

	public ItemRBMKTool(String s){
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos bpos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		Block b = world.getBlockState(bpos).getBlock();
		ItemStack stack = player.getHeldItem(hand);
		if(b instanceof RBMKBase) {
			
			int[] pos = ((BlockDummyable)b).findCore(world, bpos.getX(), bpos.getY(), bpos.getZ());
			
			if(pos != null && !world.isRemote) {
				if(!stack.hasTagCompound())
					stack.setTagCompound(new NBTTagCompound());

				stack.getTagCompound().setInteger("posX", pos[0]);
				stack.getTagCompound().setInteger("posY", pos[1]);
				stack.getTagCompound().setInteger("posZ", pos[2]);
				
				player.sendMessage(new TextComponentTranslation(this.getUnlocalizedName() + ".linked").setStyle(new Style().setColor(TextFormatting.YELLOW)));
			}
			
			return EnumActionResult.SUCCESS;
		}
		
		if(b == ModBlocks.rbmk_console && stack.hasTagCompound()) {
			
			if(!world.isRemote) {
				
				int[] pos = ((BlockDummyable)b).findCore(world, bpos.getX(), bpos.getY(), bpos.getZ());
				
				TileEntityRBMKConsole console = (TileEntityRBMKConsole)world.getTileEntity(new BlockPos(pos[0], pos[1], pos[2]));
				int tx = stack.getTagCompound().getInteger("posX");
				int ty = stack.getTagCompound().getInteger("posY");
				int tz = stack.getTagCompound().getInteger("posZ");
				console.setTarget(tx, ty, tz);
				console.setupDisplays();
				console.markDirty();
				player.sendMessage(new TextComponentTranslation(this.getUnlocalizedName() + ".set").setStyle(new Style().setColor(TextFormatting.YELLOW)));
			}
			
			return EnumActionResult.SUCCESS;
		}

		if(b == ModBlocks.rbmk_crane_console && stack.hasTagCompound()) {
			
			if(!world.isRemote) {
				
				int[] pos = ((BlockDummyable)b).findCore(world, bpos.getX(), bpos.getY(), bpos.getZ());
				
				TileEntityRBMKCraneConsole console = (TileEntityRBMKCraneConsole)world.getTileEntity(new BlockPos(pos[0], pos[1], pos[2]));
				int tx = stack.getTagCompound().getInteger("posX");
				int ty = stack.getTagCompound().getInteger("posY");
				int tz = stack.getTagCompound().getInteger("posZ");
				console.setTarget(tx, ty, tz);
				player.sendMessage(new TextComponentTranslation(this.getUnlocalizedName() + ".set").setStyle(new Style().setColor(TextFormatting.YELLOW)));
			}
			
			return EnumActionResult.SUCCESS;
		}
		
		return EnumActionResult.PASS;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn){
		for(String s : I18nUtil.resolveKeyArray(this.getUnlocalizedName() + ".desc"))
			tooltip.add(TextFormatting.YELLOW + s);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack){
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
		multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 2, 0));
		return super.getAttributeModifiers(slot, stack);
	}
}
