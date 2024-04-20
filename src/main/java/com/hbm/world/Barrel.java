package com.hbm.world;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.handler.WeightedRandomChestContentFrom1710;
import com.hbm.lib.HbmChestContents;
import com.hbm.lib.Library;
import com.hbm.tileentity.machine.TileEntityCrateSteel;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemDoor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class Barrel extends WorldGenerator {
	
	protected Block[] GetValidSpawnBlocks() {
		
		return new Block[] {
				Blocks.GRASS,
				Blocks.DIRT,
				Blocks.SAND,
				Blocks.STONE,
				Blocks.SANDSTONE
			};
	}

	public boolean LocationIsValidSpawn(World world, BlockPos pos) {

		IBlockState checkBlockState = world.getBlockState(pos.down());
		Block checkBlock = checkBlockState.getBlock();
		Block blockAbove = world.getBlockState(pos).getBlock();
		Block blockBelow = world.getBlockState(pos.down(2)).getBlock();

		for (Block i : GetValidSpawnBlocks()) {
			if (blockAbove != Blocks.AIR) {
				return false;
			}
			if (checkBlock == i) {
				return true;
			} else if (checkBlock == Blocks.SNOW_LAYER && blockBelow == i) {
				return true;
			} else if (checkBlockState.getMaterial() == Material.PLANTS && blockBelow == i) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {
		return generate(world, rand, pos, false);

	}
	
	public boolean generate(World world, Random rand, BlockPos pos, boolean force) {
		int i = rand.nextInt(1);

		if (i == 0) {
			generate_r0(world, rand, pos.getX(), pos.getY(), pos.getZ(), force);
		}

		return true;

	}
	
	Block Block1 = ModBlocks.reinforced_brick;
	Block Block2 = ModBlocks.sellafield_slaked;
	Block Block3 = ModBlocks.brick_concrete;
	Block Block4 = ModBlocks.sellafield_3;
	Block Block5 = ModBlocks.sellafield_4;
	Block Block6 = ModBlocks.sellafield_core;
	Block Block7 = ModBlocks.sellafield_2;
	Block Block8 = ModBlocks.sellafield_1;
	Block Block9 = ModBlocks.sellafield_0;
	Block Block10 = ModBlocks.deco_lead;
	Block Block11 = ModBlocks.reinforced_glass;
	Block Block12 = ModBlocks.toxic_block;

	public boolean generate_r0(World world, Random rand, int x, int y, int z, boolean force) {
		if (!force && (!LocationIsValidSpawn(world, new BlockPos(x, y, z)) || !LocationIsValidSpawn(world, new BlockPos(x + 4, y, z))
				|| !LocationIsValidSpawn(world, new BlockPos(x + 4, y, z + 6)) || !LocationIsValidSpawn(world, new BlockPos(x, y, z + 6)))) {
			return false;
		}
		MutableBlockPos pos = new BlockPos.MutableBlockPos();

		world.setBlockState(pos.setPos(x + 1, y + -1, z + 0), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + -1, z + 0), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + -1, z + 0), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + -1, z + 1), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + -1, z + 1), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + -1, z + 1), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + -1, z + 1), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + -1, z + 1), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + -1, z + 2), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + -1, z + 2), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + -1, z + 2), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + -1, z + 2), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + -1, z + 2), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + -1, z + 3), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + -1, z + 3), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + -1, z + 3), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + -1, z + 3), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + -1, z + 3), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + -1, z + 4), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + -1, z + 4), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + -1, z + 4), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + -1, z + 5), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + -1, z + 5), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + -1, z + 5), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + -1, z + 6), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + -1, z + 6), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + -1, z + 6), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 0, z + 0), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 0), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 0, z + 1), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 0, z + 1), Block4.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 1), Block5.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 1), Block4.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 1), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 0, z + 2), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 0, z + 2), Block5.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 2), Block6.getDefaultState(), 3);
		
		//Drillgon200: This tile entity is never actually used. Possibly a bug?
		/*if(world.getTileEntity(x + 2, y + 0, z + 2) instanceof TileEntitySellafield) {
			((TileEntitySellafield)world.getTileEntity(x + 2, y + 0, z + 2)).radius = 2.5;
		}*/
		
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 2), Block5.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 2), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 0, z + 3), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 0, z + 3), Block5.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 3), Block4.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 3), Block5.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 3), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 0, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 1, z + 0), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 0), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 1, z + 1), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 1, z + 1), Block7.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 1), Block4.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 1), Block4.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 1), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 1, z + 2), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 1, z + 2), Block4.getDefaultState(), 3);
		
		/*world.setBlock(x + 2, y + 1, z + 2, Blocks.chest, 3, 3);

		if(world.getBlock(x + 2, y + 1, z + 2) == Blocks.chest)
		{
			WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.getLoot(3), (TileEntityChest)world.getTileEntity(x + 2, y + 1, z + 2), 16);
		}*/

		world.setBlockState(pos.setPos(x + 2, y + 1, z + 2), ModBlocks.crate_steel.getDefaultState(), 3);

		if(world.getBlockState(pos.setPos(x + 2, y + 1, z + 2)).getBlock() == ModBlocks.crate_steel)
		{
			WeightedRandomChestContentFrom1710.generateChestContents(rand, HbmChestContents.getLoot(3), (TileEntityCrateSteel)world.getTileEntity(pos.setPos(x + 2, y + 1, z + 2)), 16);
		}
		
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 2), Block4.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 2), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 1, z + 3), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 1, z + 3), Block4.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 3), Block7.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 3), Block4.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 3), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 1, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 2, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 2, z + 0), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 2, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 2, z + 1), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 2, z + 1), Block8.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 2, z + 1), Block7.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 2, z + 1), Block7.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 1), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 2, z + 2), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 2, z + 2), Block7.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 2, z + 2), Block5.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 2, z + 2), Block7.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 2), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 2, z + 3), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 2, z + 3), Block7.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 2, z + 3), Block8.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 2, z + 3), Block7.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 3), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 2, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 2, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 2, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 3, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 3, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 3, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 3, z + 1), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 3, z + 1), Block8.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 3, z + 1), Block8.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 3, z + 1), Block8.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 3, z + 1), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 3, z + 2), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 3, z + 2), Block8.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 3, z + 2), Block4.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 3, z + 2), Block8.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 3, z + 2), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 3, z + 3), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 3, z + 3), Block8.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 3, z + 3), Block9.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 3, z + 3), Block8.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 3, z + 3), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 3, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 3, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 3, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 4, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 4, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 4, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 4, z + 1), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 4, z + 1), Block9.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 4, z + 1), Block8.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 4, z + 1), Block9.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 4, z + 1), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 4, z + 2), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 4, z + 2), Block9.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 4, z + 2), Block7.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 4, z + 2), Block8.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 4, z + 2), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 4, z + 3), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 4, z + 3), Block8.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 4, z + 3), Block9.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 4, z + 3), Block9.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 4, z + 3), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 4, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 4, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 4, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 5, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 5, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 5, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 5, z + 1), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 5, z + 1), Block9.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 5, z + 1), Block9.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 5, z + 1), Block9.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 5, z + 1), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 5, z + 2), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 5, z + 2), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 5, z + 2), Block8.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 5, z + 2), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 5, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 5, z + 3), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 5, z + 3), Block9.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 5, z + 3), Block9.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 5, z + 3), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 5, z + 3), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 5, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 5, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 5, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 6, z + 0), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 6, z + 0), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 6, z + 0), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 6, z + 1), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 6, z + 1), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 6, z + 1), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 6, z + 1), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 6, z + 1), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 6, z + 2), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 6, z + 2), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 6, z + 2), Block9.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 6, z + 2), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 6, z + 2), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 6, z + 3), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 6, z + 3), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 6, z + 3), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 6, z + 3), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 6, z + 3), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 6, z + 4), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 6, z + 4), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 6, z + 4), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 7, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 7, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 7, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 7, z + 1), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 7, z + 1), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 7, z + 1), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 7, z + 1), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 7, z + 1), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 7, z + 2), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 7, z + 2), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 7, z + 2), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 7, z + 2), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 7, z + 2), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 7, z + 3), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 7, z + 3), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 7, z + 3), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 7, z + 3), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 7, z + 3), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 7, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 7, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 7, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 8, z + 0), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 8, z + 0), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 8, z + 0), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 8, z + 1), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 8, z + 1), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 8, z + 1), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 8, z + 1), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 8, z + 1), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 8, z + 2), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 8, z + 2), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 8, z + 2), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 8, z + 2), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 8, z + 2), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 8, z + 3), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 8, z + 3), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 8, z + 3), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 8, z + 3), Block12.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 8, z + 3), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 8, z + 4), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 8, z + 4), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 8, z + 4), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 9, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 9, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 9, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 9, z + 1), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 9, z + 1), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 9, z + 2), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 9, z + 2), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 9, z + 3), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 9, z + 3), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 9, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 9, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 9, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 10, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 10, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 10, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 10, z + 1), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 10, z + 1), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 10, z + 2), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 10, z + 2), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 10, z + 3), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 10, z + 3), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 10, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		//world.setBlock(x + 2, y + 10, z + 4, Blocks.iron_door, 2, 3);
		world.setBlockState(pos.setPos(x + 3, y + 10, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 11, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 11, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 11, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 11, z + 1), Block11.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 11, z + 2), Block11.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 11, z + 2), Block11.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 11, z + 3), Block11.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 11, z + 3), Block11.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 11, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		//world.setBlock(x + 2, y + 11, z + 4, Blocks.iron_door, 8, 3);
        ItemDoor.placeDoor(world, pos.setPos(x + 2, y + 10, z + 4), EnumFacing.WEST, Blocks.IRON_DOOR, false);
		world.setBlockState(pos.setPos(x + 3, y + 11, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 12, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 12, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 12, z + 0), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 12, z + 1), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 12, z + 1), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 12, z + 2), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 12, z + 2), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 12, z + 3), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 12, z + 3), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 12, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 12, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 12, z + 4), Library.getRandomConcrete().getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 13, z + 0), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 13, z + 0), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 13, z + 0), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 13, z + 1), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 13, z + 1), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 13, z + 1), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 13, z + 1), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 13, z + 1), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 13, z + 2), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 13, z + 2), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 13, z + 2), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 13, z + 2), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 13, z + 3), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 13, z + 3), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 13, z + 3), Block10.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 13, z + 4), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 13, z + 4), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 13, z + 4), Block1.getDefaultState(), 3);

		generate_r02_last(world, rand, x, y, z, pos);
		return true;

	}

	public boolean generate_r02_last(World world, Random rand, int x, int y, int z, MutableBlockPos pos) {

		world.setBlockState(pos.setPos(x + 2, y + 0, z + 5), Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.SOUTH), 3);
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 5), Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.SOUTH), 3);
		world.setBlockState(pos.setPos(x + 2, y + 2, z + 5), Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.SOUTH), 3);
		world.setBlockState(pos.setPos(x + 2, y + 3, z + 5), Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.SOUTH), 3);
		world.setBlockState(pos.setPos(x + 2, y + 4, z + 5), Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.SOUTH), 3);
		world.setBlockState(pos.setPos(x + 2, y + 5, z + 5), Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.SOUTH), 3);
		world.setBlockState(pos.setPos(x + 2, y + 6, z + 5), Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.SOUTH), 3);
		world.setBlockState(pos.setPos(x + 2, y + 7, z + 5), Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.SOUTH), 3);
		world.setBlockState(pos.setPos(x + 2, y + 8, z + 5), Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.SOUTH), 3);
		world.setBlockState(pos.setPos(x + 2, y + 9, z + 5), Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.SOUTH), 3);
		
		if(GeneralConfig.enableDebugMode)
			System.out.print("[Debug] Successfully spawned waste tank at " + x + " " + y +" " + z + "\n");
		
		return true;

	}

}