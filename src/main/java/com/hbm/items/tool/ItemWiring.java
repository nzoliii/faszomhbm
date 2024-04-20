package com.hbm.items.tool;

import java.util.List;

import com.hbm.util.I18nUtil;
import com.hbm.blocks.BlockDummyable;
import com.hbm.items.ModItems;
import com.hbm.tileentity.network.energy.TileEntityPylonBase;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemWiring extends Item {

	public ItemWiring(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
	
		Block b = world.getBlockState(pos).getBlock();
		BlockPos core = pos;
		if(b instanceof BlockDummyable) {
			int[] corePos = ((BlockDummyable)b).findCore(world, pos.getX(), pos.getY(), pos.getZ());
		
			if(corePos != null) {
				core = new BlockPos(corePos[0], corePos[1], corePos[2]);
			}
		}

		TileEntity te = world.getTileEntity(core);
		ItemStack stack = player.getHeldItem(hand);
		if (te != null && te instanceof TileEntityPylonBase) {
			if(player.isSneaking()) { //if sneak then set a other wise connect to b
				if (!stack.hasTagCompound())
					stack.setTagCompound(new NBTTagCompound());

				stack.getTagCompound().setInteger("x", pos.getX());
				stack.getTagCompound().setInteger("y", pos.getY());
				stack.getTagCompound().setInteger("z", pos.getZ());

				if (world.isRemote)
					player.sendMessage(new TextComponentTranslation("chat.wiring.start", pos.getX(), pos.getY(), pos.getZ()));
			} else {
				if (stack.hasTagCompound()) {
					int x1 = stack.getTagCompound().getInteger("x");
					int y1 = stack.getTagCompound().getInteger("y");
					int z1 = stack.getTagCompound().getInteger("z");
					
					TileEntityPylonBase thisPylon = (TileEntityPylonBase)te;
					BlockPos newPos = new BlockPos(x1, y1, z1);
					if(!this.isLengthValid(pos.getX(), pos.getY(), pos.getZ(), x1, y1, z1, thisPylon.getMaxWireLength())){
						if (world.isRemote){
							BlockPos vector = newPos.subtract(pos);
							int distance = (int)MathHelper.sqrt(vector.getX() * vector.getX() + vector.getY() * vector.getY() + vector.getZ() * vector.getZ());
							player.sendMessage(new TextComponentTranslation("chat.wiring.tofar", distance, thisPylon.getMaxWireLength()));
						}
					} else if(pos == newPos){
						if (world.isRemote)
							player.sendMessage(new TextComponentTranslation("chat.wiring.noself"));
					} else{
						Block a = world.getBlockState(newPos).getBlock();
						BlockPos coreB = newPos;
						if(a instanceof BlockDummyable) {
							int[] corePosB = ((BlockDummyable)a).findCore(world, newPos.getX(), newPos.getY(), newPos.getZ());
						
							if(corePosB != null) {
								coreB = new BlockPos(corePosB[0], corePosB[1], corePosB[2]);
							}
						}
						TileEntity target = world.getTileEntity(coreB);
						if(target instanceof TileEntityPylonBase) {

							TileEntityPylonBase targetPylon = (TileEntityPylonBase) target;

							if(TileEntityPylonBase.canConnect(thisPylon, targetPylon)){
								thisPylon.addConnection(targetPylon.getPos());
								targetPylon.addConnection(thisPylon.getPos());

								if (world.isRemote)
									player.sendMessage(new TextComponentTranslation("chat.wiring.connected"));
							}else{
								if(thisPylon.getConnectionType() != targetPylon.getConnectionType()){
									if (world.isRemote)
										player.sendMessage(new TextComponentTranslation("chat.wiring.notcompatible"));
								}
							}
						}
					}
				}
			}
		} else { // Say distance if not on pylon
			if(player.isSneaking()){
				if(stack.hasTagCompound()) {
					stack.setTagCompound(null);
					if (world.isRemote)
						player.sendMessage(new TextComponentTranslation("chat.wiring.cleared"));
				}
			} else {
				if(stack.hasTagCompound() && world.isRemote) {
					int x1 = stack.getTagCompound().getInteger("x");
					int y1 = stack.getTagCompound().getInteger("y");
					int z1 = stack.getTagCompound().getInteger("z");

					BlockPos vector = new BlockPos(x1, y1, z1).subtract(pos);
					int distance = (int)MathHelper.sqrt(vector.getX() * vector.getX() + vector.getY() * vector.getY() + vector.getZ() * vector.getZ());
					
					player.sendMessage(new TextComponentTranslation("chat.wiring.measure", distance));
				}
			}
		}
		player.swingArm(hand);
		return EnumActionResult.SUCCESS;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (stack.getTagCompound() != null) {
			int x1 = stack.getTagCompound().getInteger("x");
			int y1 = stack.getTagCompound().getInteger("y");
			int z1 = stack.getTagCompound().getInteger("z");
			tooltip.add(I18nUtil.resolveKey("desc.wiring.start", x1, y1, z1));
		} else {
			tooltip.add(I18nUtil.resolveKey("desc.wiring.1"));
			tooltip.add(I18nUtil.resolveKey("desc.wiring.2"));
			tooltip.add(I18nUtil.resolveKey("desc.wiring.3"));
			tooltip.add(I18nUtil.resolveKey("desc.wiring.4"));
			
		}
	}
	
	public boolean isLengthValid(int x1, int y1, int z1, int x2, int y2, int z2, int length) {
		double l = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
		
		return l <= length;
	}
}
