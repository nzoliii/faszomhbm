package com.hbm.world.generator.room;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.TrappedBrick;
import com.hbm.blocks.generic.TrappedBrick.Trap;
import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.TimedGenerator;
import com.hbm.world.generator.TimedGenerator.ITimedJob;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class JungleDungeonRoomArrowFire extends JungleDungeonRoom {

	public JungleDungeonRoomArrowFire(CellularDungeon parent) {
		super(parent);
	}

	public void generateMain(final World world, final int x, final int y, final int z) {
		super.generateMain(world, x, y, z);

		ITimedJob job = new ITimedJob() {

			@Override
			public void work() {

				ITimedJob job = new ITimedJob() {

					@Override
					public void work() {

						Block bl;

						bl = world.getBlockState(new BlockPos(x + parent.width / 2, y + 2, z)).getBlock();
						if(bl == ModBlocks.brick_jungle || bl == ModBlocks.brick_jungle_cracked || bl == ModBlocks.brick_jungle_lava) {
							world.setBlockState(new BlockPos(x + parent.width / 2, y + 2, z), ModBlocks.brick_jungle_trap.getDefaultState().withProperty(TrappedBrick.TYPE, Trap.FLAMING_ARROW.ordinal()), 3);
						}

						bl = world.getBlockState(new BlockPos(x + parent.width / 2, y + 2, z + parent.width - 1)).getBlock();
						if(bl == ModBlocks.brick_jungle || bl == ModBlocks.brick_jungle_cracked || bl == ModBlocks.brick_jungle_lava) {
							world.setBlockState(new BlockPos(x + parent.width / 2, y + 2, z + parent.width - 1), ModBlocks.brick_jungle_trap.getDefaultState().withProperty(TrappedBrick.TYPE, Trap.FLAMING_ARROW.ordinal()), 3);
						}

						bl = world.getBlockState(new BlockPos(x, y + 2, z + parent.width / 2)).getBlock();
						if(bl == ModBlocks.brick_jungle || bl == ModBlocks.brick_jungle_cracked || bl == ModBlocks.brick_jungle_lava) {
							world.setBlockState(new BlockPos(x, y + 2, z + parent.width / 2), ModBlocks.brick_jungle_trap.getDefaultState().withProperty(TrappedBrick.TYPE, Trap.FLAMING_ARROW.ordinal()), 3);
						}

						bl = world.getBlockState(new BlockPos(x + parent.width - 1, y + 2, z + parent.width / 2)).getBlock();
						if(bl == ModBlocks.brick_jungle || bl == ModBlocks.brick_jungle_cracked || bl == ModBlocks.brick_jungle_lava) {
							world.setBlockState(new BlockPos(x + parent.width - 1, y + 2, z + parent.width / 2), ModBlocks.brick_jungle_trap.getDefaultState().withProperty(TrappedBrick.TYPE, Trap.FLAMING_ARROW.ordinal()), 3);
						}
					}
				};

				TimedGenerator.addOp(world, job);
			}
		};

		TimedGenerator.addOp(world, job);
	}
}