package com.hbm.blocks.bomb;

import java.util.List;

import com.hbm.util.I18nUtil;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityCloudSolinium;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.interfaces.IBomb;
import com.hbm.lib.InventoryHelper;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityNukeSolinium;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NukeSolinium extends BlockContainer implements IBomb {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public NukeSolinium(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityNukeSolinium();
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		InventoryHelper.dropInventoryItems(worldIn, pos, worldIn.getTileEntity(pos));
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			TileEntityNukeSolinium entity = (TileEntityNukeSolinium) world.getTileEntity(pos);
			if(entity != null) {
				player.openGui(MainRegistry.instance, ModBlocks.guiID_nuke_solinium, world, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		TileEntityNukeSolinium entity = (TileEntityNukeSolinium) worldIn.getTileEntity(pos);
		if(worldIn.isBlockIndirectlyGettingPowered(pos) > 0 && !worldIn.isRemote) {
			if(entity.isReady()) {
				this.onBlockDestroyedByPlayer(worldIn, pos, worldIn.getBlockState(pos));
				entity.clearSlots();
				worldIn.setBlockToAir(pos);
				igniteTestBomb(worldIn, pos.getX(), pos.getY(), pos.getZ(), BombConfig.soliniumRadius);
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()));
	}

	public boolean igniteTestBomb(World world, int x, int y, int z, int r) {
		if(!world.isRemote) {
			world.playSound(null, x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.0f, world.rand.nextFloat() * 0.1F + 0.9F);

			EntityNukeExplosionMK3 entity = new EntityNukeExplosionMK3(world);
			entity.posX = x;
			entity.posY = y;
			entity.posZ = z;
			entity.destructionRange = r;
			entity.speed = BombConfig.blastSpeed;
			entity.coefficient = 1.0F;
			entity.waste = false;
			entity.extType = 1;

			world.spawnEntity(entity);

			EntityCloudSolinium cloud = new EntityCloudSolinium(world, r);
			cloud.posX = x;
			cloud.posY = y;
			cloud.posZ = z;
			world.spawnEntity(cloud);
		}

		return false;
	}

	@Override
	public void explode(World world, BlockPos pos) {
		TileEntityNukeSolinium entity = (TileEntityNukeSolinium) world.getTileEntity(pos);
		if(entity.isReady()) {
			this.onBlockDestroyedByPlayer(world, pos, world.getBlockState(pos));
			entity.clearSlots();
			world.setBlockToAir(pos);
			igniteTestBomb(world, pos.getX(), pos.getY(), pos.getZ(), BombConfig.soliniumRadius);
		}
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
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
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{FACING});
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
	}
	
	
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
	   return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}

	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add("§3["+ I18nUtil.resolveKey("trait.soliniumbomb")+"]§r");
		tooltip.add(" §e"+I18nUtil.resolveKey("desc.radius", BombConfig.soliniumRadius)+"§r");
		tooltip.add("");
		tooltip.add("§d"+I18nUtil.resolveKey("desc.nukesolinium1")+"§r");
		tooltip.add("§d"+I18nUtil.resolveKey("desc.nukesolinium2")+"§r");
	}
}
