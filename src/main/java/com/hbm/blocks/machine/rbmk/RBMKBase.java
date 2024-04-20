package com.hbm.blocks.machine.rbmk;

import javax.annotation.Nullable;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemRBMKLid;
import com.hbm.lib.ForgeDirection;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.tileentity.machine.rbmk.RBMKDials;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBase;

import api.hbm.block.IToolable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public abstract class RBMKBase extends BlockDummyable implements IToolable, ITooltipProvider {

	public static boolean dropLids = true;
	public static boolean digamma = false;
	public ResourceLocation coverTexture;
	public ResourceLocation columnTexture;
	
	public RBMKBase(String s, String columnTexture){
		super(Material.IRON, s);
		coverTexture = new ResourceLocation(RefStrings.MODID, "textures/blocks/rbmk/" + s + ".png");
		this.columnTexture = new ResourceLocation(RefStrings.MODID, "textures/blocks/rbmk/" + columnTexture +".png");
	}
	
	@Override
	public int[] getDimensions() {
		return new int[] {3, 0, 0, 0, 0, 0};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        this.addStandardInfo(tooltip);
    }
	
	public boolean openInv(World world, int x, int y, int z, EntityPlayer player, int gui, EnumHand hand) {
		
		if(world.isRemote) {
			return true;
		}
		
		int[] pos = this.findCore(world, x, y, z);
		
		if(pos == null)
			return false;
		
		TileEntity te = world.getTileEntity(new BlockPos(pos[0], pos[1], pos[2]));
		
		if(!(te instanceof TileEntityRBMKBase))
			return false;
		
		TileEntityRBMKBase rbmk = (TileEntityRBMKBase) te;
		
		if(player.getHeldItem(hand) != null && player.getHeldItem(hand).getItem() instanceof ItemRBMKLid) {
			
			if(!rbmk.hasLid())
				return false;
		}
		
		if(!player.isSneaking()) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, gui, world, pos[0], pos[1], pos[2]);
			return true;
		} else {
			return true;
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos bpos){
		float height = 0.0F;
		
		int[] pos = this.findCore(source, bpos.getX(), bpos.getY(), bpos.getZ());
		
		if(pos != null) {
			TileEntity te = source.getTileEntity(new BlockPos(pos[0], pos[1], pos[2]));
			
			if(te instanceof TileEntityRBMKBase) {
				
				TileEntityRBMKBase rbmk = (TileEntityRBMKBase) te;
				
				if(rbmk.hasLid()) {
					height += 0.25F;
				}
			}
		}
		return new AxisAlignedBB(0, 0, 0, 1, 1+height, 1);
	}
	
	/*
	 * NORTH: no cover
	 * EAST: concrete cover
	 * SOUTH: lead glass cover
	 * WEST: UNUSED
	 */

	public static final ForgeDirection DIR_NO_LID = ForgeDirection.NORTH;
	public static final ForgeDirection DIR_NORMAL_LID = ForgeDirection.EAST;
	public static final ForgeDirection DIR_GLASS_LID = ForgeDirection.SOUTH;
	
	@Override
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o){
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, getDimensions(world), this, dir);
		this.makeExtra(world, x, y + RBMKDials.getColumnHeight(world), z);
	}
	
	@Override
	protected ForgeDirection getDirModified(ForgeDirection dir) {
		return DIR_NO_LID;
	}
	
	public int[] getDimensions(World world) {
		return new int[] {RBMKDials.getColumnHeight(world), 0, 0, 0, 0, 0};
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state){
		if(!world.isRemote && dropLids) {
			int i = state.getValue(META);
			if(i == DIR_NORMAL_LID.ordinal() + offset) {
				world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5 + RBMKDials.getColumnHeight(world), pos.getZ() + 0.5, new ItemStack(ModItems.rbmk_lid)));
			}
			if(i == DIR_GLASS_LID.ordinal() + offset) {
				world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5 + RBMKDials.getColumnHeight(world), pos.getZ() + 0.5, new ItemStack(ModItems.rbmk_lid_glass)));
			}
		}
		
		super.breakBlock(world, pos, state);
	}
	
	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, EnumFacing side, float fX, float fY, float fZ, EnumHand hand, ToolType tool){
		if(tool != ToolType.SCREWDRIVER)
			return false;
		
		int[] pos = this.findCore(world, x, y, z);
		
		if(pos != null) {
			TileEntity te = world.getTileEntity(new BlockPos(pos[0], pos[1], pos[2]));
			
			if(te instanceof TileEntityRBMKBase) {
				
				TileEntityRBMKBase rbmk = (TileEntityRBMKBase) te;
				int i = rbmk.getBlockMetadata();
				
				if(rbmk.hasLid() && rbmk.isLidRemovable()) {
					
					if(!world.isRemote) {
						if(i == DIR_NORMAL_LID.ordinal() + offset) {
							world.spawnEntity(new EntityItem(world, pos[0] + 0.5, pos[1] + 0.5 + RBMKDials.getColumnHeight(world), pos[2] + 0.5, new ItemStack(ModItems.rbmk_lid)));
						}
						if(i == DIR_GLASS_LID.ordinal() + offset) {
							world.spawnEntity(new EntityItem(world, pos[0] + 0.5, pos[1] + 0.5 + RBMKDials.getColumnHeight(world), pos[2] + 0.5, new ItemStack(ModItems.rbmk_lid_glass)));
						}
						
						world.setBlockState(new BlockPos(pos[0], pos[1], pos[2]), this.getDefaultState().withProperty(META, DIR_NO_LID.ordinal() + BlockDummyable.offset), 3);
						NBTTagCompound nbt = rbmk.writeToNBT(new NBTTagCompound());
						world.getTileEntity(new BlockPos(pos[0], pos[1], pos[2])).readFromNBT(nbt);
					}
					
					return true;
				}
			}
		}
		
		return false;
	}
}
