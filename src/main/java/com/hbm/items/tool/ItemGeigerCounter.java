package com.hbm.items.tool;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.Library;
import com.hbm.items.ModItems;
import com.hbm.items.gear.ArmorFSB;
import com.hbm.items.weapon.ItemGunEgon;
import com.hbm.render.misc.RenderScreenOverlay;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.saveddata.RadiationSavedData;
import com.hbm.util.ContaminationUtil;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;

@Optional.InterfaceList({@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles")})
public class ItemGeigerCounter extends Item implements IBauble {
	
	public ItemGeigerCounter(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {

		if(!(entity instanceof EntityLivingBase) || world.isRemote)
			return;
		
		if(entity instanceof EntityPlayer) {
			
			if(ArmorFSB.hasFSBArmor((EntityPlayer)entity) && ((ArmorFSB)((EntityPlayer)entity).inventory.armorInventory.get(2).getItem()).geigerSound)
				return;
			
			playGeiger(world, (EntityPlayer)entity);
		}
	}

	public static void playGeiger(World world, EntityPlayer player){
		double x = ContaminationUtil.getActualPlayerRads(player);
		
		if(world.getTotalWorldTime() % 5 == 0) {

			if(x > 0.001) {
				List<Integer> list = new ArrayList<Integer>();

				if(x < 1){
					list.add(0);
				}
				if(x < 5){
					list.add(0);
				}
				if(2 < x && x < 10){
					list.add(1);
				}
				if(5 < x && x < 20){
					list.add(2);
				}
				if(15 < x && x < 40){
					list.add(3);
				}
				if(30 < x && x < 80){
					list.add(4);
				}
				if(60 < x && x < 160){
					list.add(5);
				}
				if(120 < x && x < 320){
					list.add(6);
				}
				if(240 < x && x < 640){
					list.add(7);
				}
				if(480 < x){
					list.add(8);
				}
				if(list.size() > 0){
					int r = list.get(world.rand.nextInt(list.size()));
					
					if(r > 0){
						world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.geigerSounds[r-1], SoundCategory.PLAYERS, 1.0F, 1.0F);
					}
				}
			} else if(world.rand.nextInt(100) == 0) {
				world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.geigerSounds[(world.rand.nextInt(1))], SoundCategory.PLAYERS, 1.0F, 1.0F);
			}
		}
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.getBlockState(pos).getBlock() == ModBlocks.block_red_copper) {
    		Library.consumeInventoryItem(player.inventory, ModItems.geiger_counter);
    		player.inventory.addItemStackToInventory(new ItemStack(ModItems.survey_scanner));
    		return EnumActionResult.SUCCESS;
    	}
    	
    	return EnumActionResult.PASS;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
		if(!world.isRemote) {
	    	world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.techBoop, SoundCategory.PLAYERS, 1.0F, 1.0F);

	    	ContaminationUtil.printGeigerData(player);
		}
		
		return super.onItemRightClick(world, player, handIn);
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return !ItemStack.areItemsEqual(oldStack, newStack);
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack){
		return BaubleType.TRINKET;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		onUpdate(itemstack, player.world, player, 0, true);
	}
}
