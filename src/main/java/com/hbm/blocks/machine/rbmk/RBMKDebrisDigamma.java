package com.hbm.blocks.machine.rbmk;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.ForgeDirection;
import com.hbm.util.ContaminationUtil;
import net.minecraft.entity.EntityLivingBase;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RBMKDebrisDigamma extends RBMKDebris {

	public RBMKDebrisDigamma(String s){
		super(s);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand){
		if(!world.isRemote) {
			
			ContaminationUtil.radiate(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 32, 0, 200F, 0, 0, 0);
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				Block b = world.getBlockState(new BlockPos(pos.getX() + dir.offsetX, pos.getY() + dir.offsetY, pos.getZ() + dir.offsetZ)).getBlock();
				if((b instanceof RBMKDebris  && b != this) || b == ModBlocks.corium_block || b == ModBlocks.block_corium)
					world.setBlockState(new BlockPos(pos.getX() + dir.offsetX, pos.getY() + dir.offsetY, pos.getZ() + dir.offsetZ), this.getDefaultState());
			}
			world.scheduleUpdate(pos, this, this.tickRate(world));
		}
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state){
		super.onBlockAdded(worldIn, pos, state);
		worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
	}
	
	@Override
	public int tickRate(World world) {

		return 20 + world.rand.nextInt(20);
	}
}
