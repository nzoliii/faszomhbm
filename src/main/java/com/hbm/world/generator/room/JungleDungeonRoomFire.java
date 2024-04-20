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

public class JungleDungeonRoomFire extends JungleDungeonRoom {

	public JungleDungeonRoomFire(CellularDungeon parent) {
		super(parent);
	}

	public void generateMain(final World world, final int x, final int y, final int z) {
		super.generateMain(world, x, y, z);

		ITimedJob job = new ITimedJob() {

			@Override
			public void work() {

				for(int a = 0; a < 3; a++) {
					for(int b = 0; b < 3; b++) {

						if(a == 1 && b == 1)
							continue;

						Block bl = world.getBlockState(new BlockPos(x + 1 + a, y, z + 1 + b)).getBlock();

						if(bl == ModBlocks.brick_jungle || bl == ModBlocks.brick_jungle_cracked || bl == ModBlocks.brick_jungle_lava || bl == ModBlocks.brick_jungle_trap) {
							world.setBlockState(new BlockPos(x + 1 + a, y, z + 1 + b), ModBlocks.brick_jungle_trap.getDefaultState().withProperty(TrappedBrick.TYPE, Trap.FIRE.ordinal()), 3);
						}
					}
				}
			}
		};

		TimedGenerator.addOp(world, job);
	}
}