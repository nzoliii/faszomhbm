package com.hbm.blocks.machine.rbmk;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.BossSpawnHandler;
import com.hbm.tileentity.TileEntityProxyInventory;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKRod;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RBMKRod extends RBMKBase {

	public boolean moderated = false;
	
	public RBMKRod(boolean moderated, String s, String c) {
		super(s, c);
		this.moderated = moderated;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= offset)
			return new TileEntityRBMKRod();
		
		if(hasExtra(meta))
			return new TileEntityProxyInventory();
		
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		BossSpawnHandler.markFBI(playerIn);
		return openInv(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn, ModBlocks.guiID_rbmk_rod, hand);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state){
		return EnumBlockRenderType.MODEL;
	}
	
}
