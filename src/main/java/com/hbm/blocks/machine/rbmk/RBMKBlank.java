package com.hbm.blocks.machine.rbmk;

import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBlank;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

public class RBMKBlank extends RBMKBase {

	public RBMKBlank(String s, String c){
		super(s, c);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= offset)
			return new TileEntityRBMKBlank();
		return null;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state){
		return EnumBlockRenderType.MODEL;
	}
}