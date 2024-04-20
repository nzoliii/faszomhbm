package com.hbm.world;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineBattery;
import com.hbm.config.GeneralConfig;
import com.hbm.handler.WeightedRandomChestContentFrom1710;
import com.hbm.lib.HbmChestContents;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class Vertibird extends WorldGenerator
{
	Block Block2 = ModBlocks.deco_steel;
	Block Block1 = ModBlocks.deco_tungsten;
	Block Block4 = ModBlocks.reinforced_glass;
	Block Block3 = ModBlocks.deco_titanium;
	
	protected Block[] GetValidSpawnBlocks()
	{
		return new Block[]
		{
			Blocks.SAND,
			Blocks.SANDSTONE,
		};
	}

	public boolean LocationIsValidSpawn(World world, BlockPos pos)
 {

		IBlockState checkBlockState = world.getBlockState(pos.down());
		Block checkBlock = checkBlockState.getBlock();
		Block blockAbove = world.getBlockState(pos).getBlock();
		Block blockBelow = world.getBlockState(pos.down(2)).getBlock();

		for (Block i : GetValidSpawnBlocks())
		{
			if (blockAbove != Blocks.AIR)
			{
				return false;
			}
			if (checkBlock == i)
			{
				return true;
			}
			else if (checkBlock == Blocks.SNOW_LAYER && blockBelow == i)
			{
				return true;
			}
			else if (checkBlockState.getMaterial() == Material.PLANTS && blockBelow == i)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos)
	{
		return generate(world, rand, pos, false);

	}
	
	public boolean generate(World world, Random rand, BlockPos pos, boolean force)
	{
		int i = rand.nextInt(1);

		if(i == 0)
		{
		    generate_r0(world, rand, pos.getX(), pos.getY(), pos.getZ(), force);
		}

       return true;

	}

	public boolean generate_r0(World world, Random rand, int x, int y, int z, boolean force)
	{
		int yOffset = 3 + rand.nextInt(4);
		
		if(!force && !LocationIsValidSpawn(world, new BlockPos(x + 13, y, z + 10)))
		{
			return false;
		}
		MutableBlockPos pos = new BlockPos.MutableBlockPos();

		world.setBlockState(pos.setPos(x + 13, y + 0 - yOffset, z + 2), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 0 - yOffset, z + 7), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 0 - yOffset, z + 7), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 0 - yOffset, z + 9), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 1 - yOffset, z + 1), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 1 - yOffset, z + 1), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 1 - yOffset, z + 1), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 1 - yOffset, z + 2), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 1 - yOffset, z + 2), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 1 - yOffset, z + 2), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 1 - yOffset, z + 3), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 1 - yOffset, z + 3), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 1 - yOffset, z + 3), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 1 - yOffset, z + 3), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 1 - yOffset, z + 3), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 1 - yOffset, z + 4), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 1 - yOffset, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 1 - yOffset, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 1 - yOffset, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 1 - yOffset, z + 4), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 1 - yOffset, z + 5), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 1 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 1 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 1 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 1 - yOffset, z + 5), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 1 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 1 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 1 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 1 - yOffset, z + 7), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 1 - yOffset, z + 7), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 1 - yOffset, z + 7), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 1 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 1 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 1 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 1 - yOffset, z + 9), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 1 - yOffset, z + 9), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 1 - yOffset, z + 9), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 2 - yOffset, z + 0), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 2 - yOffset, z + 0), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 2 - yOffset, z + 0), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 2 - yOffset, z + 1), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 2 - yOffset, z + 1), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 2 - yOffset, z + 2), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 2 - yOffset, z + 2), Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.BOTTOM).withProperty(BlockStairs.FACING, EnumFacing.SOUTH), 3);
		world.setBlockState(pos.setPos(x + 14, y + 2 - yOffset, z + 2), Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.BOTTOM).withProperty(BlockStairs.FACING, EnumFacing.SOUTH), 3);
		world.setBlockState(pos.setPos(x + 15, y + 2 - yOffset, z + 2), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 2 - yOffset, z + 3), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 2 - yOffset, z + 4), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 2 - yOffset, z + 5), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 2 - yOffset, z + 5), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 2 - yOffset, z + 6), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 2 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 2 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 2 - yOffset, z + 7), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 2 - yOffset, z + 7), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 2 - yOffset, z + 7), Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.NORTH), 3);
		if(world.getBlockState(pos.setPos(x + 14, y + 2 - yOffset, z + 7)).getBlock() == Blocks.CHEST)
		{
			WeightedRandomChestContentFrom1710.generateChestContents(rand, HbmChestContents.getLoot(6), (TileEntityChest)world.getTileEntity(pos.setPos(x + 14, y + 2 - yOffset, z + 7)), 8);
		}
		world.setBlockState(pos.setPos(x + 15, y + 2 - yOffset, z + 7), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 2 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 2 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 2 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 2 - yOffset, z + 9), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 2 - yOffset, z + 9), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 2 - yOffset, z + 9), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 2 - yOffset, z + 10), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 2 - yOffset, z + 10), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 2 - yOffset, z + 10), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 2 - yOffset, z + 11), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 2 - yOffset, z + 11), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 2 - yOffset, z + 11), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 2 - yOffset, z + 18), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 3 - yOffset, z + 0), Block4.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 3 - yOffset, z + 0), Block4.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 3 - yOffset, z + 0), Block4.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 3 - yOffset, z + 1), Block4.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 3 - yOffset, z + 1), Block4.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 3 - yOffset, z + 2), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 3 - yOffset, z + 2), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 3 - yOffset, z + 3), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 3 - yOffset, z + 3), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 3 - yOffset, z + 4), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 3 - yOffset, z + 5), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 3 - yOffset, z + 6), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 3 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 3 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 3 - yOffset, z + 7), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 3 - yOffset, z + 7), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 3 - yOffset, z + 7), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 3 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 3 - yOffset, z + 8), ModBlocks.machine_battery.getDefaultState().withProperty(MachineBattery.FACING, EnumFacing.NORTH), 3);
		world.setBlockState(pos.setPos(x + 14, y + 3 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 3 - yOffset, z + 9), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 3 - yOffset, z + 9), ModBlocks.red_wire_coated.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 3 - yOffset, z + 9), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 3 - yOffset, z + 10), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 3 - yOffset, z + 10), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 3 - yOffset, z + 10), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 3 - yOffset, z + 11), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 3 - yOffset, z + 11), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 3 - yOffset, z + 11), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 3 - yOffset, z + 12), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 3 - yOffset, z + 12), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 3 - yOffset, z + 12), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 3 - yOffset, z + 17), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 3 - yOffset, z + 18), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 4 - yOffset, z + 0), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 4 - yOffset, z + 0), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 4 - yOffset, z + 0), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 4 - yOffset, z + 1), Block4.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 4 - yOffset, z + 1), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 4 - yOffset, z + 1), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 4 - yOffset, z + 1), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 4 - yOffset, z + 1), Block4.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 4 - yOffset, z + 2), Block4.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 4 - yOffset, z + 2), Block4.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 4 - yOffset, z + 3), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 4 - yOffset, z + 3), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 4 - yOffset, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 4 - yOffset, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 4 - yOffset, z + 5), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 4 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 4 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 22, y + 4 - yOffset, z + 5), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 4 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 4 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 4 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 4 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 4 - yOffset, z + 7), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 4 - yOffset, z + 7), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 4 - yOffset, z + 7), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 4 - yOffset, z + 7), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 4 - yOffset, z + 7), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 4 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 4 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 4 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 4 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 4 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 4 - yOffset, z + 9), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 4 - yOffset, z + 9), ModBlocks.machine_generator.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 4 - yOffset, z + 9), ModBlocks.red_wire_coated.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 4 - yOffset, z + 9), ModBlocks.machine_generator.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 4 - yOffset, z + 9), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 4 - yOffset, z + 10), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 4 - yOffset, z + 10), ModBlocks.machine_generator.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 4 - yOffset, z + 10), ModBlocks.red_wire_coated.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 4 - yOffset, z + 10), ModBlocks.machine_generator.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 4 - yOffset, z + 10), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 4 - yOffset, z + 11), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 4 - yOffset, z + 11), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 4 - yOffset, z + 11), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 4 - yOffset, z + 11), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 4 - yOffset, z + 11), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 4 - yOffset, z + 12), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 4 - yOffset, z + 12), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 4 - yOffset, z + 12), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 4 - yOffset, z + 12), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 4 - yOffset, z + 12), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 4 - yOffset, z + 13), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 4 - yOffset, z + 13), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 4 - yOffset, z + 13), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 4 - yOffset, z + 14), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 4 - yOffset, z + 15), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 4 - yOffset, z + 16), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 4 - yOffset, z + 17), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 4 - yOffset, z + 18), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 5 - yOffset, z + 1), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 5 - yOffset, z + 1), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 5 - yOffset, z + 1), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 5 - yOffset, z + 2), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 5 - yOffset, z + 2), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 5 - yOffset, z + 2), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 5 - yOffset, z + 3), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 5 - yOffset, z + 3), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 5 - yOffset, z + 3), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 5 - yOffset, z + 3), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 5 - yOffset, z + 3), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 5 - yOffset, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 5 - yOffset, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 5 - yOffset, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 5 - yOffset, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 5 - yOffset, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 5 - yOffset, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 22, y + 5 - yOffset, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 5 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 5 - yOffset, z + 5), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 5 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 5 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 5 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 5 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 5 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 5 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 5 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 5 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 5 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 16, y + 5 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 17, y + 5 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 18, y + 5 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 21, y + 5 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 22, y + 5 - yOffset, z + 5), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 23, y + 5 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 5 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 5 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 5 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 5 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 5 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 5 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 22, y + 5 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 5 - yOffset, z + 7), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 5 - yOffset, z + 7), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 5 - yOffset, z + 7), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 5 - yOffset, z + 7), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 5 - yOffset, z + 7), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 5 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 5 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 5 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 5 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 5 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 5 - yOffset, z + 9), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 5 - yOffset, z + 9), ModBlocks.machine_generator.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 5 - yOffset, z + 9), ModBlocks.red_wire_coated.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 5 - yOffset, z + 9), ModBlocks.machine_generator.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 5 - yOffset, z + 9), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 5 - yOffset, z + 10), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 5 - yOffset, z + 10), ModBlocks.machine_generator.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 5 - yOffset, z + 10), ModBlocks.red_wire_coated.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 5 - yOffset, z + 10), ModBlocks.machine_generator.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 5 - yOffset, z + 10), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 5 - yOffset, z + 11), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 5 - yOffset, z + 11), ModBlocks.red_wire_coated.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 5 - yOffset, z + 11), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 5 - yOffset, z + 12), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 5 - yOffset, z + 12), ModBlocks.red_wire_coated.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 5 - yOffset, z + 12), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 5 - yOffset, z + 13), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 5 - yOffset, z + 13), ModBlocks.red_wire_coated.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 5 - yOffset, z + 13), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 5 - yOffset, z + 14), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 5 - yOffset, z + 14), ModBlocks.red_wire_coated.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 5 - yOffset, z + 14), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 5 - yOffset, z + 15), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 5 - yOffset, z + 15), ModBlocks.red_wire_coated.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 5 - yOffset, z + 15), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 5 - yOffset, z + 16), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 5 - yOffset, z + 16), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 5 - yOffset, z + 16), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 5 - yOffset, z + 16), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 5 - yOffset, z + 16), ModBlocks.red_wire_coated.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 5 - yOffset, z + 16), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 5 - yOffset, z + 16), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 16, y + 5 - yOffset, z + 16), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 17, y + 5 - yOffset, z + 16), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 5 - yOffset, z + 17), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 5 - yOffset, z + 17), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 5 - yOffset, z + 17), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 5 - yOffset, z + 17), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 5 - yOffset, z + 17), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 5 - yOffset, z + 17), ModBlocks.red_wire_coated.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 5 - yOffset, z + 17), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 5 - yOffset, z + 17), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 16, y + 5 - yOffset, z + 17), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 17, y + 5 - yOffset, z + 17), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 18, y + 5 - yOffset, z + 17), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 5 - yOffset, z + 18), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 5 - yOffset, z + 18), ModBlocks.red_wire_coated.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 5 - yOffset, z + 18), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 5 - yOffset, z + 19), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 6 - yOffset, z + 1), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 6 - yOffset, z + 1), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 6 - yOffset, z + 3), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 6 - yOffset, z + 3), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 6 - yOffset, z + 3), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 6 - yOffset, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 6 - yOffset, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 6 - yOffset, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 6 - yOffset, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 6 - yOffset, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 6 - yOffset, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 22, y + 6 - yOffset, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 6 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 6 - yOffset, z + 5), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 6 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 6 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 6 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 6 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 6 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 6 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 6 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 6 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 6 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 6 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 6 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 16, y + 6 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 17, y + 6 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 18, y + 6 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 19, y + 6 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 20, y + 6 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 21, y + 6 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 22, y + 6 - yOffset, z + 5), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 23, y + 6 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 6 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 6 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 6 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 6 - yOffset, z + 6), Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.NORTH), 3);
		if(world.getBlockState(pos.setPos(x + 13, y + 6 - yOffset, z + 6)).getBlock() == Blocks.CHEST)
		{
			WeightedRandomChestContentFrom1710.generateChestContents(rand, HbmChestContents.getLoot(3), (TileEntityChest)world.getTileEntity(pos.setPos(x + 13, y + 6 - yOffset, z + 6)), 8);
		}
		world.setBlockState(pos.setPos(x + 14, y + 6 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 6 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 22, y + 6 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 6 - yOffset, z + 7), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 6 - yOffset, z + 7), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 6 - yOffset, z + 7), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 6 - yOffset, z + 7), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 6 - yOffset, z + 7), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 6 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 6 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 6 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 6 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 15, y + 6 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 6 - yOffset, z + 9), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 6 - yOffset, z + 9), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 6 - yOffset, z + 9), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 6 - yOffset, z + 10), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 6 - yOffset, z + 10), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 6 - yOffset, z + 10), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 6 - yOffset, z + 11), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 6 - yOffset, z + 11), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 6 - yOffset, z + 11), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 6 - yOffset, z + 12), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 6 - yOffset, z + 12), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 6 - yOffset, z + 12), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 6 - yOffset, z + 13), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 6 - yOffset, z + 14), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 6 - yOffset, z + 17), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 6 - yOffset, z + 18), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 7 - yOffset, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 7 - yOffset, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 22, y + 7 - yOffset, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 7 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 7 - yOffset, z + 5), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 7 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 7 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 7 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 7 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 21, y + 7 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 22, y + 7 - yOffset, z + 5), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 23, y + 7 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 7 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 7 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 7 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 7 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 22, y + 7 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 7 - yOffset, z + 7), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 7 - yOffset, z + 7), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 7 - yOffset, z + 7), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 12, y + 7 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 7 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 14, y + 7 - yOffset, z + 8), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 7 - yOffset, z + 9), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 7 - yOffset, z + 10), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 13, y + 7 - yOffset, z + 11), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 8 - yOffset, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 22, y + 8 - yOffset, z + 4), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 8 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 8 - yOffset, z + 5), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 8 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 21, y + 8 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 22, y + 8 - yOffset, z + 5), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 23, y + 8 - yOffset, z + 5), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 8 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 22, y + 8 - yOffset, z + 6), Block2.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 9 - yOffset, z + 5), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 22, y + 9 - yOffset, z + 5), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 10 - yOffset, z + 1), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 22, y + 10 - yOffset, z + 1), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 10 - yOffset, z + 2), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 22, y + 10 - yOffset, z + 2), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 10 - yOffset, z + 3), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 22, y + 10 - yOffset, z + 3), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 10 - yOffset, z + 4), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 22, y + 10 - yOffset, z + 4), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 0, y + 10 - yOffset, z + 5), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 1, y + 10 - yOffset, z + 5), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 10 - yOffset, z + 5), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 10 - yOffset, z + 5), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 10 - yOffset, z + 5), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 10 - yOffset, z + 5), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 10 - yOffset, z + 5), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 10 - yOffset, z + 5), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 10 - yOffset, z + 5), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 18, y + 10 - yOffset, z + 5), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 19, y + 10 - yOffset, z + 5), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 20, y + 10 - yOffset, z + 5), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 21, y + 10 - yOffset, z + 5), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 22, y + 10 - yOffset, z + 5), Block1.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 23, y + 10 - yOffset, z + 5), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 24, y + 10 - yOffset, z + 5), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 25, y + 10 - yOffset, z + 5), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 26, y + 10 - yOffset, z + 5), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 10 - yOffset, z + 6), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 22, y + 10 - yOffset, z + 6), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 10 - yOffset, z + 7), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 22, y + 10 - yOffset, z + 7), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 10 - yOffset, z + 8), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 22, y + 10 - yOffset, z + 8), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 10 - yOffset, z + 9), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 22, y + 10 - yOffset, z + 9), Block3.getDefaultState(), 3);

		generate_r02_last(world, rand, x, y, z, yOffset, pos);
		return true;

	}
	public boolean generate_r02_last(World world, Random rand, int x, int y, int z, int yOffset, MutableBlockPos pos)
	{

		world.setBlockState(pos.setPos(x + 12, y + 2 - yOffset, z + 1), Blocks.LEVER.getDefaultState().withProperty(BlockLever.POWERED, false).withProperty(BlockLever.FACING, BlockLever.EnumOrientation.SOUTH), 3);
		world.setBlockState(pos.setPos(x + 14, y + 2 - yOffset, z + 1), Blocks.LEVER.getDefaultState().withProperty(BlockLever.POWERED, false).withProperty(BlockLever.FACING, BlockLever.EnumOrientation.SOUTH), 3);
		if(GeneralConfig.enableDebugMode)
			System.out.print("[Debug] Successfully spawned Vertibird at " + x + " " + y +" " + z + "\n");
		return true;

	}

}
