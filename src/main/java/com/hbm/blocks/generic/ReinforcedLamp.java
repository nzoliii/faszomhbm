package com.hbm.blocks.generic;

import java.util.Random;
import java.util.List;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.RadiationSystemNT;
import com.hbm.interfaces.IRadResistantBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ReinforcedLamp extends Block implements IRadResistantBlock {

	private final boolean isOn;
	
	public ReinforcedLamp(Material materialIn, boolean b, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		isOn = b;
		if(b){
			this.setLightLevel(1.0F);
		}
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote)
        {
            if (this.isOn && !(worldIn.isBlockIndirectlyGettingPowered(pos) > 0))
            {
            	worldIn.scheduleUpdate(pos, this, 4);
            }
            else if (!this.isOn && worldIn.isBlockIndirectlyGettingPowered(pos) > 0)
            {
            	worldIn.setBlockState(pos, ModBlocks.reinforced_lamp_on.getDefaultState(), 2);
            }
        }
        RadiationSystemNT.markChunkForRebuild(worldIn, pos);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!worldIn.isRemote)
        {
            if (this.isOn && !(worldIn.isBlockIndirectlyGettingPowered(pos) > 0))
            {
            	worldIn.scheduleUpdate(pos, this, 4);
            }
            else if (!this.isOn && worldIn.isBlockIndirectlyGettingPowered(pos) > 0)
            {
            	worldIn.setBlockState(pos, ModBlocks.reinforced_lamp_on.getDefaultState(), 2);
            }
        }
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote && this.isOn && !(worldIn.isBlockIndirectlyGettingPowered(pos) > 0))
        {
			worldIn.setBlockState(pos, ModBlocks.reinforced_lamp_off.getDefaultState(), 2);
        }
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.reinforced_lamp_off);
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(ModBlocks.reinforced_lamp_off);
	}

	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		float hardness = this.getExplosionResistance(null);
		tooltip.add("§2[Radiation Shielding]§r");
		if(hardness > 50){
			tooltip.add("§6Blast Resistance: "+hardness+"§r");
		}
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		RadiationSystemNT.markChunkForRebuild(worldIn, pos);
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean isRadResistant(World worldIn, BlockPos blockPos){
		return true;
	}
}
