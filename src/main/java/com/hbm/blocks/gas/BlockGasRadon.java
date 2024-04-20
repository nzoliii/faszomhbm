package com.hbm.blocks.gas;

import java.util.Random;

import com.hbm.lib.ForgeDirection;
import com.hbm.capability.HbmLivingProps;
import com.hbm.util.ArmorRegistry;
import com.hbm.handler.ArmorUtil;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ArmorRegistry.HazardClass;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockGasRadon extends BlockGasBase {

	public BlockGasRadon(String s) {
		super(0.1F, 0.8F, 0.1F, s);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entity){
		if(!(entity instanceof EntityLivingBase))
			return;
		
		EntityLivingBase entityLiving = (EntityLivingBase) entity;
		
		if(ArmorRegistry.hasProtection(entityLiving, EntityEquipmentSlot.HEAD, HazardClass.RAD_GAS)) {
			ArmorUtil.damageGasMaskFilter(entityLiving, 1);
			ContaminationUtil.contaminate(entityLiving, HazardType.RADIATION, ContaminationType.CREATIVE, 0.05F);
		} else {
			ContaminationUtil.contaminate(entityLiving, HazardType.RADIATION, ContaminationType.RAD_BYPASS, 0.05F);
		}
	}
	
	@Override
	public ForgeDirection getFirstDirection(World world, int x, int y, int z){
		if(world.rand.nextInt(5) == 0)
			return ForgeDirection.UP;
		
		return ForgeDirection.DOWN;
	}
	
	@Override
	public ForgeDirection getSecondDirection(World world, int x, int y, int z) {
		return this.randomHorizontal(world);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand){
		if(!world.isRemote && rand.nextInt(20) == 0) {
			world.setBlockToAir(pos);
			return;
		}
		
		super.updateTick(world, pos, state, rand);
	}
}
