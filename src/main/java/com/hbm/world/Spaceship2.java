package com.hbm.world;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public class Spaceship2
{
	Block Block1 = ModBlocks.deco_tungsten;
	Block Block2 = ModBlocks.fusion_conductor;
	Block Block3 = ModBlocks.deco_steel;
	Block Block4 = ModBlocks.fusion_heater;
	Block Block5 = ModBlocks.block_meteor;
	Block Block6 = ModBlocks.reactor_element;
	Block Block7 = ModBlocks.fusion_core;
	Block Block8 = ModBlocks.fusion_hatch;
	Block Block9 = ModBlocks.reinforced_light;
	Block Block10 = ModBlocks.reinforced_glass;
	
	public boolean generate_r00(World world, Random rand, int x, int y, int z)
    {
		MutableBlockPos pos = new BlockPos.MutableBlockPos();
		world.setBlockState(pos.setPos(x + 9, y + 0, z + 17), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 0, z + 17), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 18), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 18), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 18), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 18), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 18), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 18), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 18), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 0, z + 18), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 0, z + 18), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 19), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 19), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 19), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 19), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 19), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 19), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 19), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 0, z + 19), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 0, z + 19), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 20), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 20), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 20), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 20), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 20), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 20), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 20), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 0, z + 20), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 0, z + 20), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 21), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 21), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 21), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 21), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 21), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 21), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 21), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 0, z + 21), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 0, z + 21), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 22), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 22), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 22), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 22), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 22), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 22), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 22), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 0, z + 22), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 0, z + 22), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 23), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 23), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 23), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 23), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 23), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 23), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 23), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 0, z + 23), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 0, z + 23), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 24), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 24), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 24), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 24), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 24), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 24), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 24), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 0, z + 24), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 0, z + 24), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 25), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 25), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 25), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 25), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 25), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 25), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 25), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 0, z + 25), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 0, z + 25), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 26), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 26), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 26), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 26), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 26), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 26), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 26), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 0, z + 26), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 0, z + 26), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 27), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 27), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 27), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 27), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 27), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 27), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 27), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 0, z + 27), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 0, z + 27), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 28), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 28), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 28), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 28), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 28), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 28), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 28), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 0, z + 28), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 0, z + 28), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 29), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 29), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 29), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 29), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 29), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 29), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 29), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 0, z + 29), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 0, z + 29), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 0, z + 30), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 30), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 30), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 30), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 30), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 30), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 30), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 0, z + 30), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 0, z + 30), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 0, z + 30), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 0, z + 31), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 31), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 31), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 31), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 31), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 31), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 0, z + 31), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 0, z + 31), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 32), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 32), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 32), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 32), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 33), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 33), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 33), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 33), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 34), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 34), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 34), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 34), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 35), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 35), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 35), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 35), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 36), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 36), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 36), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 36), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 37), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 37), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 37), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 37), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 37), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 38), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 38), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 38), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 38), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 38), Block3.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 0, z + 38), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 39), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 39), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 39), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 39), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 39), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 0, z + 39), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 40), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 40), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 40), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 40), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 40), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 41), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 41), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 41), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 42), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 42), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 42), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 42), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 0, z + 43), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 43), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 43), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 43), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 43), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 0, z + 43), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 44), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 44), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 44), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 44), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 0, z + 44), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 0, z + 45), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 0, z + 45), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 0, z + 45), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 0, z + 45), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 1, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 1, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 1, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 1, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 2, y + 1, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 1, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 1, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 11, y + 1, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 1, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 10, y + 1, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 1, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 3, y + 1, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 1, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 1, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 10), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 10), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 10), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 10), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 10), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 1, z + 10), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 11), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 11), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 11), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 11), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 11), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 1, z + 11), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 12), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 12), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 12), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 12), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 12), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 1, z + 12), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 13), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 13), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 13), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 13), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 13), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 1, z + 13), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 14), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 14), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 14), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 14), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 14), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 1, z + 14), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 1, z + 15), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 15), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 15), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 15), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 15), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 1, z + 15), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 16), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 16), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 16), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 16), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 1, z + 16), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 17), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 17), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 17), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 17), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 18), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 18), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 18), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 18), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 19), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 19), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 19), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 19), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 1, z + 20), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 20), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 20), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 20), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 21), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 21), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 22), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 41), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 42), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 42), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 43), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 43), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 43), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 44), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 1, z + 44), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 1, z + 44), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 1, z + 45), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 2, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 2, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 2, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 2, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 2, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 2, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 2, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 2, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 2, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 2, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 2, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 9, y + 2, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 4, y + 2, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 2, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 2, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 2, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 2, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 2, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 2, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 10), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 10), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 2, z + 10), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 2, z + 10), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 11), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 11), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 2, z + 11), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 2, z + 11), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 12), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 12), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 2, z + 12), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 2, z + 12), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 13), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 13), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 2, z + 13), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 2, z + 13), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 14), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 14), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 2, z + 14), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 2, z + 14), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 2, z + 15), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 15), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 2, z + 15), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 2, z + 16), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 3, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 3, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 3, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 3, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 3, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 3, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 3, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 3, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 3, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 3, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 3, z + 7), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 3, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 3, z + 8), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 3, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 3, z + 9), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 10), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 10), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 3, z + 10), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 3, z + 10), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 3, z + 11), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 11), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 3, z + 11), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 3, z + 12), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 4, z + 0), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 4, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 4, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 4, z + 1), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 4, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 4, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 4, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 4, z + 2), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 4, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 4, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 4, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 4, z + 3), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 5, y + 4, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 4, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 4, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 4, z + 4), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 6, y + 4, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 4, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 4, z + 5), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 4, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 8, y + 4, z + 6), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(pos.setPos(x + 7, y + 4, z + 7), Blocks.AIR.getDefaultState(), 3);
		if(GeneralConfig.enableDebugMode)
			System.out.print("[Debug] Successfully spawned spaceship at " + x + " " + y +" " + z + "\n");
		return true;

	}

}