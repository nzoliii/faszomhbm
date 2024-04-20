package com.hbm.world.dungeon;

import java.util.Arrays;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.main.MainRegistry;
import com.hbm.world.generator.DungeonToolbox;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class ArcticVault {

	public void trySpawn(World world, int x, int y, int z) {

		y--;

		Biome biome = world.getBiome(new BlockPos(x, y, z));

		if(biome.getTemperature(new BlockPos(x, y, z)) < 0.2 && world.getBlockState(new BlockPos(x, y, z)).getMaterial() == Material.ROCK) {
			build(world, x, y, z);
		}

	}

	private void build(World world, int x, int y, int z) {

		List<IBlockState> brick = Arrays.asList(Blocks.STONEBRICK.getDefaultState(), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CRACKED));
		List<IBlockState> web = Arrays.asList(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), Blocks.WEB.getDefaultState());
		List<IBlockState> crates = Arrays.asList(ModBlocks.crate.getDefaultState(), ModBlocks.crate_metal.getDefaultState(), ModBlocks.crate_ammo.getDefaultState(), ModBlocks.crate_can.getDefaultState(), ModBlocks.crate_jungle.getDefaultState());

		DungeonToolbox.generateBox(world, x - 5, y, z - 5, 11, 1, 11, brick);
		DungeonToolbox.generateBox(world, x - 5, y + 6, z - 5, 11, 1, 11, brick);
		DungeonToolbox.generateBox(world, x - 5, y + 1, z - 5, 11, 5, 1, brick);
		DungeonToolbox.generateBox(world, x - 5, y + 1, z + 5, 11, 5, 1, brick);
		DungeonToolbox.generateBox(world, x - 5, y + 1, z - 5, 1, 5, 11, brick);
		DungeonToolbox.generateBox(world, x + 5, y + 1, z - 5, 1, 5, 11, brick);
		DungeonToolbox.generateBox(world, x - 4, y + 1, z - 4, 9, 3, 9, Blocks.AIR.getDefaultState());
		DungeonToolbox.generateBox(world, x - 4, y + 1, z - 4, 9, 1, 9, Blocks.SNOW_LAYER.getDefaultState());
		DungeonToolbox.generateBox(world, x - 2, y + 1, z - 2, 5, 2, 1, ModBlocks.tape_recorder.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.SOUTH));
		DungeonToolbox.generateBox(world, x - 2, y + 3, z - 2, 5, 1, 1, Blocks.SNOW_LAYER.getDefaultState());
		DungeonToolbox.generateBox(world, x - 2, y + 1, z + 2, 5, 2, 1, ModBlocks.tape_recorder.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.NORTH));
		DungeonToolbox.generateBox(world, x - 2, y + 3, z + 2, 5, 1, 1, Blocks.SNOW_LAYER.getDefaultState());
		DungeonToolbox.generateBox(world, x - 4, y + 4, z - 4, 9, 2, 9, web);

		for(int i = 0; i < 15; i++) {
			int ix = x - 4 + world.rand.nextInt(10);
			int iz = z - 4 + world.rand.nextInt(10);

			if(world.getBlockState(new BlockPos(ix, y + 1, iz)).getBlock() == Blocks.SNOW_LAYER) {
				IBlockState b = DungeonToolbox.getRandom(crates, world.rand);
				world.setBlockState(new BlockPos(ix, y + 1, iz), b, 2);
				world.setBlockState(new BlockPos(ix, y + 2, iz), Blocks.SNOW_LAYER.getDefaultState());
			}
		}

		int iy = world.getHeight(x, z);

		if(world.getBlockState(new BlockPos(x, iy - 1, z)).isSideSolid(world, new BlockPos(x, iy - 1, z), EnumFacing.UP)) {
			world.setBlockState(new BlockPos(x, iy, z), ModBlocks.tape_recorder.getDefaultState());
		}

		if(GeneralConfig.enableDebugMode)
			MainRegistry.logger.info("[Debug] Successfully spawned arctic code vault at " + x + " " + y + " " + z);
	}
}
