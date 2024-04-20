package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.handler.RadiationSystemNT;
import com.hbm.interfaces.IDoor;
import com.hbm.interfaces.IBomb;
import com.hbm.interfaces.IDummy;
import com.hbm.interfaces.IRadResistantBlock;
import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemLock;
import com.hbm.tileentity.machine.TileEntityBlastDoor;
import com.hbm.tileentity.machine.TileEntityDummy;

import micdoodle8.mods.galacticraft.api.block.IPartialSealableBlock;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;

@Optional.InterfaceList({@Optional.Interface(iface = "micdoodle8.mods.galacticraft.api.block.IPartialSealableBlock", modid = "galacticraftcore")})
public class DummyBlockBlast extends BlockContainer implements IDummy, IBomb, IRadResistantBlock, IPartialSealableBlock {

	public static boolean safeBreak = false;

	public DummyBlockBlast(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	public boolean isSealed(World worldIn, BlockPos blockPos, EnumFacing direction){
		if (worldIn != null)
		{
			TileEntity te = worldIn.getTileEntity(blockPos);
			if(te != null && te instanceof TileEntityDummy && ((TileEntityDummy) te).target != null) {

				TileEntity actualTileEntity = worldIn.getTileEntity(((TileEntityDummy) te).target);
				if (actualTileEntity != null) {
					if (IDoor.class.isAssignableFrom(actualTileEntity.getClass())) {
						// Doors should be sealed only when closed
						return ((IDoor) actualTileEntity).getState() == IDoor.DoorState.CLOSED;
					}
				}
			}
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityDummy();
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		if(!safeBreak) {
    		TileEntity te = world.getTileEntity(pos);
    		if(te != null && te instanceof TileEntityDummy) {
    		
    			if(!world.isRemote)
    				world.destroyBlock(((TileEntityDummy)te).target, true);
    		}
    	}
    	world.removeTileEntity(pos);
		RadiationSystemNT.markChunkForRebuild(world, pos);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(player.getHeldItemMainhand().getItem() instanceof ItemLock || player.getHeldItemMainhand().getItem() == ModItems.key_kit) {
			return false;
			
		} else if(!player.isSneaking())
		{
			TileEntity til = world.getTileEntity(pos);
			if(til != null && til instanceof TileEntityDummy) {
						
				TileEntityBlastDoor entity = (TileEntityBlastDoor) world.getTileEntity(((TileEntityDummy)til).target);
				if(entity != null) {
					if(entity.canAccess(player)){
						entity.tryToggle();
						return true;
					}	
				}
			}

		}
		return false;
	}

	@Override
	public void explode(World world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if(te != null && te instanceof TileEntityDummy) {
			
			TileEntityBlastDoor entity = (TileEntityBlastDoor) world.getTileEntity(((TileEntityDummy)te).target);
			if(entity != null && !entity.isLocked())
			{
				entity.tryToggle();
			}
		}
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Items.AIR;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}
	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return false;
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(ModBlocks.blast_door);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		RadiationSystemNT.markChunkForRebuild(worldIn, pos);
		super.onBlockAdded(worldIn, pos, state);
	}

	@Override
	public boolean isRadResistant(World worldIn, BlockPos blockPos){
		// Door should be rad resistant only when closed
		if (worldIn != null) {
			TileEntity te = worldIn.getTileEntity(blockPos);
			if(te != null && te instanceof TileEntityDummy && ((TileEntityDummy) te).target != null) {

				TileEntity actualTileEntity = worldIn.getTileEntity(((TileEntityDummy) te).target);
				if (actualTileEntity != null) {
					if (IDoor.class.isAssignableFrom(actualTileEntity.getClass())) {
						return ((IDoor) actualTileEntity).getState() == IDoor.DoorState.CLOSED;
					}
				}
			}
		}

		return true;
	}
}
