package com.hbm.world.dungeon;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.world.generator.DungeonToolbox;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;

public class AncientTomb {

	
	public void build(World world, Random rand, int x, int y, int z) {

		List<IBlockState> concrete = Arrays.asList(ModBlocks.brick_concrete.getDefaultState(),
				ModBlocks.brick_concrete_broken.getDefaultState(),
				ModBlocks.brick_concrete_cracked.getDefaultState());
		
		y = 20;
		
		/// PYRAMID Y LOCATION ///
		int yOff = Math.max(world.getHeight(x, z), 35) - 5;
		
		int pySize = 15;
		
		/// PRINT PYRAMID ///
		for(int iy = pySize; iy > 0; iy--) {
			
			int range = (pySize - iy);
			
			for(int ix = -range; ix <= range; ix++) {
				for(int iz = -range; iz <= range; iz++) {
					
					if((ix <= -range + 1 || ix >= range - 1) && (iz <= -range + 1 || iz >= range - 1)) {
						world.setBlockState(new BlockPos(x + ix, yOff + iy, z + iz), ModBlocks.reinforced_stone.getDefaultState());
						continue;
					}
					
					if(iy == 1) {
						world.setBlockState(new BlockPos(x + ix, yOff + iy, z + iz), ModBlocks.concrete_smooth.getDefaultState());
						continue;
					}
					
					if((ix <= -range + 1 || ix >= range - 1) || (iz <= -range + 1 || iz >= range - 1)) {
						world.setBlockState(new BlockPos(x + ix, yOff + iy, z + iz), ModBlocks.concrete_smooth.getDefaultState());
						continue;
					}
					
					world.setBlockToAir(new BlockPos(x + ix, yOff + iy, z + iz));
				}
			}
		}
		
		DungeonToolbox.generateBox(world, x - 2, yOff + 2, z - 2, 5, 4, 5, concrete);
		world.setBlockState(new BlockPos(x + 2, yOff + 3, z), ModBlocks.brick_concrete_marked.getDefaultState());
		world.setBlockState(new BlockPos(x - 2, yOff + 3, z), ModBlocks.brick_concrete_marked.getDefaultState());
		world.setBlockState(new BlockPos(x, yOff + 3, z + 2), ModBlocks.brick_concrete_marked.getDefaultState());
		world.setBlockState(new BlockPos(x, yOff + 3, z - 2), ModBlocks.brick_concrete_marked.getDefaultState());

		DungeonToolbox.generateBox(world, x + 5, yOff + 2, z + 5, 1, 7, 1, ModBlocks.concrete_pillar.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Y));
		DungeonToolbox.generateBox(world, x + 5, yOff + 2, z - 5, 1, 7, 1, ModBlocks.concrete_pillar.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Y));
		DungeonToolbox.generateBox(world, x - 5, yOff + 2, z - 5, 1, 7, 1, ModBlocks.concrete_pillar.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Y));
		DungeonToolbox.generateBox(world, x - 5, yOff + 2, z + 5, 1, 7, 1, ModBlocks.concrete_pillar.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Y));
		
		/// PRINT SPIKES ///
		int spikeCount = 36 + rand.nextInt(15);
		
		Vec3 vec = Vec3.createVectorHelper(20, 0, 0);
		float rot = (float)Math.toRadians(360F / spikeCount);
		
		for(int i = 0; i < spikeCount; i++) {
			
			vec.rotateAroundY(rot);
			
			double variance = 1D + rand.nextDouble() * 0.4D;

			int ix = (int) (x + vec.xCoord * variance);
			int iz = (int) (z + vec.zCoord * variance);
			int iy = world.getHeight(ix, iz) - 3;
			
			for(int j = iy; j < iy + 7; j++) {
				world.setBlockState(new BlockPos(ix, j, iz), ModBlocks.deco_steel.getDefaultState());
			}
		}
		
		/// GENERATE TUNNEL ///
		Vec3 sVec = Vec3.createVectorHelper(10, 0, 0);
		float sRot = (float) Math.toRadians(360F / 32F);

		for(int i = y - 1; i < yOff + 2; i++) {

			int ix = (int) Math.floor(sVec.xCoord);
			int iz = (int) Math.floor(sVec.zCoord);
			
			int h = i < yOff ? 3 : 2;
			
			if(i > 40)
				DungeonToolbox.generateBox(world, x + ix - 1, i, z + iz - 1, 3, h, 3, Blocks.AIR.getDefaultState());
			else
				DungeonToolbox.generateBox(world, x + ix - 1, i, z + iz - 1, 3, h, 3, ModBlocks.gas_radon_tomb.getDefaultState());

			for(int dx = x + ix - 2; dx < x + ix + 3; dx++) {
				for(int dy = i - 1; dy < i + 4; dy++) {
					for(int dz = z + iz - 2; dz < z + iz + 3; dz++) {

						//if(dy >= yOff + 2)
						//	continue;

						Block b = world.getBlockState(new BlockPos(dx, dy, dz)).getBlock();

						if(b != Blocks.AIR && b != ModBlocks.gas_radon_tomb && b != ModBlocks.concrete && b != ModBlocks.concrete_smooth && b != ModBlocks.brick_concrete && b != ModBlocks.brick_concrete_cracked && b != ModBlocks.brick_concrete_broken) {

							IBlockState meta = DungeonToolbox.getRandom(concrete, rand);
							world.setBlockState(new BlockPos(dx, dy, dz), meta, 3);
						}
					}
				}
			}

			sVec.rotateAroundY(sRot);
		}
		
		for(int dx = x + 4; dx < x + 8; dx++) {
			for(int dy = y - 1; dy < y + 4; dy++) {
				for(int dz = z - 2; dz < z + 3; dz++) {
					
					Block b = world.getBlockState(new BlockPos(dx, dy, dz)).getBlock();
					
					if(b != Blocks.AIR && b != ModBlocks.gas_radon_tomb && b != ModBlocks.concrete &&
							b != ModBlocks.concrete_smooth && b != ModBlocks.brick_concrete &&
							b != ModBlocks.brick_concrete_cracked && b != ModBlocks.brick_concrete_broken) {
						
						IBlockState meta = DungeonToolbox.getRandom(concrete, rand);
						world.setBlockState(new BlockPos(dx, dy, dz), meta, 3);
					}
				}
			}
		}
		
		/// PRINT TOMB CHAMBER ///
		int size = 5;
		int cladding = size - 1;
		int core = size -2;

		int dimOuter = size * 2 + 1;
		int dimInner = cladding * 2 + 1;
		int dimCore = core * 2 + 1;

		DungeonToolbox.generateBox(world, x - size, y - size, z - size, 1, dimOuter, dimOuter, concrete);
		DungeonToolbox.generateBox(world, x - size, y - size, z - size, dimOuter, 1, dimOuter, concrete);
		DungeonToolbox.generateBox(world, x - size, y - size, z - size, dimOuter, dimOuter, 1, concrete);
		DungeonToolbox.generateBox(world, x + size, y - size, z - size, 1, dimOuter, dimOuter, concrete);
		DungeonToolbox.generateBox(world, x - size, y + size, z - size, dimOuter, 1, dimOuter, concrete);
		DungeonToolbox.generateBox(world, x - size, y - size, z + size, dimOuter, dimOuter, 1, concrete);
		
		DungeonToolbox.generateBox(world, x - cladding, y - cladding, z - cladding, 1, dimInner, dimInner, ModBlocks.brick_obsidian.getDefaultState());
		DungeonToolbox.generateBox(world, x - cladding, y - cladding, z - cladding, dimInner, 1, dimInner, ModBlocks.brick_obsidian.getDefaultState());
		DungeonToolbox.generateBox(world, x - cladding, y - cladding, z - cladding, dimInner, dimInner, 1, ModBlocks.brick_obsidian.getDefaultState());
		DungeonToolbox.generateBox(world, x + cladding, y - cladding, z - cladding, 1, dimInner, dimInner, ModBlocks.brick_obsidian.getDefaultState());
		DungeonToolbox.generateBox(world, x - cladding, y + cladding, z - cladding, dimInner, 1, dimInner, ModBlocks.brick_obsidian.getDefaultState());
		DungeonToolbox.generateBox(world, x - cladding, y - cladding, z + cladding, dimInner, dimInner, 1, ModBlocks.brick_obsidian.getDefaultState());

		DungeonToolbox.generateBox(world, x - core, y - core, z - core, dimCore, dimCore, dimCore, ModBlocks.ancient_scrap.getDefaultState());
		
		/// PRINT ACCESS ///
		DungeonToolbox.generateBox(world, x + 6, y - 2, z - 1, 2, 1, 3, concrete);
		DungeonToolbox.generateBox(world, x + 4, y - 1, z - 1, 5, 3, 3, ModBlocks.gas_radon_tomb.getDefaultState());
		DungeonToolbox.generateBox(world, x + 6, y + 2, z - 1, 2, 1, 3, concrete);
	}
}