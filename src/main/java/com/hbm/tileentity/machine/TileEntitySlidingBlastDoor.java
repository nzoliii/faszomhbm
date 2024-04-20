package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.RadiationSystemNT;
import com.hbm.interfaces.IAnimatedDoor;
import com.hbm.lib.ForgeDirection;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemKeyPin;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEDoorAnimationPacket;
import com.hbm.sound.AudioWrapper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntitySlidingBlastDoor extends TileEntityLockableBase implements ITickable, IAnimatedDoor {

	public DoorState state = DoorState.CLOSED;
	public byte texture = 0;
	public long sysTime;
	private int timer = 0;
	public boolean shouldUseBB = true;
	public boolean redstoned = false;
	public boolean keypadLocked = false;
	
	private AudioWrapper audio;

	@Override
	public void update() {
		if(!world.isRemote) {
			DoorState oldState = state;

			if(state.isStationaryState()) {
				timer = 0;
			} else {
				timer ++;
				if(state == DoorState.CLOSING){
					if(timer == 2){
						placeDummy(-2);
						placeDummy(2);
					} else if(timer == 6){
						placeDummy(-1);
						placeDummy(1);
					} else if(timer == 12){
						placeDummy(0);
					} if(timer > 24){
						state = DoorState.CLOSED;

						if (state != oldState)
						{
							// With door finally closed, mark chunk for rad update since door is now rad resistant
							// No need to update when open as well, as opening door should update
							RadiationSystemNT.markChunkForRebuild(world, pos);
						}
					}
				} else if(state == DoorState.OPENING){
					if(timer == 12){
						removeDummy(0);
					} else if(timer == 16){
						removeDummy(-1);
						removeDummy(1);
					} else if(timer == 20){
						removeDummy(-2);
						removeDummy(2);
					} else if(timer > 24){
						state = DoorState.OPEN;
					}
				}
			}
			PacketDispatcher.wrapper.sendToAllAround(new TEDoorAnimationPacket(pos, (byte) state.ordinal(), texture), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 200));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(pos, shouldUseBB == true ? 1 : 0, 0), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 200));
		}
	}

	public boolean tryOpen(EntityPlayer player) {
		if(state == DoorState.CLOSED) {
			if(!world.isRemote && canAccess(player)) {
				open();
			}
			return true;
		}
		return false;
	}

	public boolean tryToggle(EntityPlayer player){
		if(state == DoorState.CLOSED) {
			return tryOpen(player);
		} else if(state == DoorState.OPEN) {
			return tryClose(player);
		}
		return false;
	}

	public boolean tryClose(EntityPlayer player) {
		if(state == DoorState.OPEN) {
			if(!world.isRemote && canAccess(player)) {
				close();
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean canAccess(EntityPlayer player) {
		if(keypadLocked && player != null)
			return false;
		
		if(!this.isLocked()) {
			return true;
		} else {
			ItemStack stack = player.getHeldItemMainhand();
			
			if(stack.getItem() instanceof ItemKeyPin && ItemKeyPin.getPins(stack) == this.lock) {
	        	world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.lockOpen, SoundCategory.BLOCKS, 1.0F, 1.0F);
				return true;
			}
			
			if(stack.getItem() == ModItems.key_red) {
	        	world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.lockOpen, SoundCategory.BLOCKS, 1.0F, 1.0F);
				return true;
			}
			
			return this.tryPick(player);
		}
	}

	private void placeDummy(int offset){
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
		BlockPos placePos = null;
		switch(dir){
		case SOUTH:
			placePos = pos.add(offset, 0, 0);
			break;
		case NORTH:
			placePos = pos.add(-offset, 0, 0);
			break;
		case EAST:
			placePos = pos.add(0, 0, offset);
			break;
		case WEST:
			placePos = pos.add(0, 0, -offset);
			break;
		default:
			return;
		}
		if(offset == 0){
			shouldUseBB = true;
		} else {
			((BlockDummyable)getBlockType()).removeExtra(world, placePos.getX(), placePos.getY(), placePos.getZ());
		}
		((BlockDummyable)getBlockType()).removeExtra(world, placePos.getX(), placePos.getY()+1, placePos.getZ());
		((BlockDummyable)getBlockType()).removeExtra(world, placePos.getX(), placePos.getY()+2, placePos.getZ());
		((BlockDummyable)getBlockType()).removeExtra(world, placePos.getX(), placePos.getY()+3, placePos.getZ());
	}

	private void removeDummy(int offset){
		ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
		BlockPos placePos = null;
		switch(dir){
		case SOUTH:
			placePos = pos.add(offset, 0, 0);
			break;
		case NORTH:
			placePos = pos.add(-offset, 0, 0);
			break;
		case EAST:
			placePos = pos.add(0, 0, offset);
			break;
		case WEST:
			placePos = pos.add(0, 0, -offset);
			break;
		default:
			return;
		}
		BlockDummyable.safeRem = true;
		if(offset == 0){
			shouldUseBB = false;
		} else {
			((BlockDummyable)getBlockType()).makeExtra(world, placePos.getX(), placePos.getY(), placePos.getZ());
		}
		((BlockDummyable)getBlockType()).makeExtra(world, placePos.getX(), placePos.getY()+1, placePos.getZ());
		((BlockDummyable)getBlockType()).makeExtra(world, placePos.getX(), placePos.getY()+2, placePos.getZ());
		((BlockDummyable)getBlockType()).makeExtra(world, placePos.getX(), placePos.getY()+3, placePos.getZ());
		BlockDummyable.safeRem = false;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	@Override
	public double getMaxRenderDistanceSquared() {
		return 65536D;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		state = DoorState.values()[compound.getByte("state")];
		sysTime = compound.getLong("sysTime");
		timer = compound.getInteger("timer");
		redstoned = compound.getBoolean("redstoned");
		keypadLocked = compound.getBoolean("keypadLocked");
		shouldUseBB = compound.getBoolean("shouldUseBB");
		texture = compound.getByte("texture");
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setByte("state", (byte) state.ordinal());
		compound.setLong("sysTime", sysTime);
		compound.setInteger("timer", timer);
		compound.setBoolean("redstoned", redstoned);
		compound.setBoolean("keypadLocked", keypadLocked);
		compound.setBoolean("shouldUseBB", shouldUseBB);
		compound.setByte("texture", texture);
		return super.writeToNBT(compound);
	}

	@Override
	public void onChunkUnload(){
		if(audio != null) {
			audio.stopSound();
			audio = null;
    	}
	}
	
	@Override
	public void invalidate(){
		if(audio != null) {
			audio.stopSound();
			audio = null;
    	}
		super.invalidate();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void handleNewState(DoorState newState) {
		if(this.state != newState){
			if(this.state == DoorState.CLOSED && newState == DoorState.OPENING){
				if(audio == null){
					audio = MainRegistry.proxy.getLoopedSoundStartStop(world, HBMSoundHandler.qe_sliding_opening, null, HBMSoundHandler.qe_sliding_opened, SoundCategory.BLOCKS, pos.getX(), pos.getY(), pos.getZ(), 2, 1);
					audio.startSound();
				}
			}
			if(this.state == DoorState.OPEN && newState == DoorState.CLOSING){
				if(audio == null){
					audio = MainRegistry.proxy.getLoopedSoundStartStop(world, HBMSoundHandler.qe_sliding_opening, null, HBMSoundHandler.qe_sliding_shut, SoundCategory.BLOCKS, pos.getX(), pos.getY(), pos.getZ(), 2, 1);
					audio.startSound();
				}
			}
			if(this.state.isMovingState() && newState.isStationaryState()){
				if(audio != null){
					audio.stopSound();
					audio = null;
				}
			}
			if(this.state.isStationaryState() && newState.isMovingState()){
				sysTime = System.currentTimeMillis();
			}
			this.state = newState;
		}
	}

	@Override
	public void open() {
		if(state == DoorState.CLOSED)
			toggle();
	}

	@Override
	public void close() {
		if(state == DoorState.OPEN)
			toggle();
	}

	@Override
	public DoorState getState() {
		return state;
	}

	@Override
	public void toggle(){
		if(state == DoorState.CLOSED) {
			state = DoorState.OPENING;
			// With door opening, mark chunk for rad update
			RadiationSystemNT.markChunkForRebuild(world, pos);
		} else if(state == DoorState.OPEN) {
			state = DoorState.CLOSING;
			// With door closing, mark chunk for rad update
			RadiationSystemNT.markChunkForRebuild(world, pos);
		}
	}
	
	@Override
	public void setTextureState(byte tex){
		this.texture = tex;
	}
	
	@Override
	public boolean setTexture(String tex) {
		if(tex.equals("sliding_blast_door")){
			this.texture = 0;
			return true;
		} else if(tex.equals("sliding_blast_door_variant1")){
			this.texture = 1;
			return true;
		} else if(tex.equals("sliding_blast_door_variant2")){
			this.texture = 2;
			return true;
		}
		return false;
	}
}
