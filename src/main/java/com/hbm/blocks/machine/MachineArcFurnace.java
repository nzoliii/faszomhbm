package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.InventoryHelper;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityMachineArcFurnace;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MachineArcFurnace extends BlockContainer {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool RODS = PropertyBool.create("rods");

	private final boolean isActive;
	private static boolean keepInventory;

	public MachineArcFurnace(Material materialIn, boolean active, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		isActive = active;

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityMachineArcFurnace();
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.machine_arc_furnace_off);
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(ModBlocks.machine_arc_furnace_off);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);
		this.setDefaultFacing(worldIn, pos, state);
	}

	private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
		if(!worldIn.isRemote) {
			IBlockState iblockstate = worldIn.getBlockState(pos.north());
			IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
			IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
			IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
			EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

			if(enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock()) {
				enumfacing = EnumFacing.SOUTH;
			} else if(enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock()) {
				enumfacing = EnumFacing.NORTH;
			} else if(enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock()) {
				enumfacing = EnumFacing.EAST;
			} else if(enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock()) {
				enumfacing = EnumFacing.WEST;
			}

			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing).withProperty(RODS, false), 2);
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

		if(stack.hasDisplayName()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if(tileentity instanceof TileEntityMachineArcFurnace) {
				((TileEntityMachineArcFurnace) tileentity).setCustomName(stack.getDisplayName());
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			TileEntityMachineArcFurnace entity = (TileEntityMachineArcFurnace) world.getTileEntity(pos);
			if(entity != null) {
				player.openGui(MainRegistry.instance, ModBlocks.guiID_machine_arc, world, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		} else {
			return false;
		}
	}

	public static void updateBlockState(boolean isProcessing, World world, BlockPos pos) {
		EnumFacing e = world.getBlockState(pos).getValue(FACING);
		boolean b = world.getBlockState(pos).getValue(RODS);
		TileEntity entity = world.getTileEntity(pos);
		keepInventory = true;

		if(isProcessing && world.getBlockState(pos).getBlock() != ModBlocks.machine_arc_furnace_on) {
			world.setBlockState(pos, ModBlocks.machine_arc_furnace_on.getDefaultState().withProperty(FACING, e).withProperty(RODS, b));
		} else if(!isProcessing && world.getBlockState(pos).getBlock() != ModBlocks.machine_arc_furnace_off) {
			world.setBlockState(pos, ModBlocks.machine_arc_furnace_off.getDefaultState().withProperty(FACING, e).withProperty(RODS, b));
		}

		keepInventory = false;

		if(entity != null) {
			entity.validate();
			world.setTileEntity(pos, entity);
		}
	}

	//Drillgon200: Why does it work now.
	public static boolean updateBlockRods(boolean hasRods, World world, BlockPos pos) {
		boolean returnVal = false;
		boolean b = world.getBlockState(pos).getValue(RODS);
		EnumFacing e = world.getBlockState(pos).getValue(FACING);
		Block block = world.getBlockState(pos).getBlock();
		TileEntity entity = world.getTileEntity(pos);
		keepInventory = true;
		
		if(hasRods && !b) {
			world.setBlockState(pos, block.getDefaultState().withProperty(FACING, e).withProperty(RODS, hasRods));
			returnVal = true;
		} else if(!hasRods && b) {
			world.setBlockState(pos, block.getDefaultState().withProperty(FACING, e).withProperty(RODS, hasRods));
			returnVal = true;
		}

		keepInventory = false;
		if(entity != null && world.getTileEntity(pos) != entity) {
			entity.validate();
			world.setTileEntity(pos, entity);
		}
		return returnVal;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if(!keepInventory)
			InventoryHelper.dropInventoryItems(worldIn, pos, worldIn.getTileEntity(pos));
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if(isActive) {
			EnumFacing e = worldIn.getBlockState(pos).getValue(FACING);
			float f = pos.getX() + 0.5F;
			float f1 = pos.getY() + 0.0F + rand.nextFloat() * 6.0F / 16.0F;
			float f2 = pos.getZ() + 0.5F;
			float f3 = 0.52F;
			float f4 = rand.nextFloat() * 0.6F - 0.3F;

			if(e == EnumFacing.WEST) {
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
				worldIn.spawnParticle(EnumParticleTypes.FLAME, f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
			} else if(e == EnumFacing.EAST) {
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
				worldIn.spawnParticle(EnumParticleTypes.FLAME, f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
			} else if(e == EnumFacing.NORTH) {
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
				worldIn.spawnParticle(EnumParticleTypes.FLAME, f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
			} else if(e == EnumFacing.SOUTH) {
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
				worldIn.spawnParticle(EnumParticleTypes.FLAME, f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(RODS, false);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, RODS });
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = ((EnumFacing) state.getValue(FACING)).getIndex() << 1;
		meta += state.getValue(RODS) ? 1 : 0;
		return meta;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		boolean rods = (meta & 1) == 1 ? true : false;
		meta = meta >> 1;
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if(enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(RODS, rods);
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
	}

}
