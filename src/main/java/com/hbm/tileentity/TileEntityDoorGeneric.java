package com.hbm.tileentity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.generic.BlockDoorGeneric;
import com.hbm.handler.RadiationSystemNT;
import com.hbm.interfaces.IAnimatedDoor;
import com.hbm.interfaces.IDoor.DoorState;
import com.hbm.inventory.control_panel.ControlEvent;
import com.hbm.inventory.control_panel.ControlEventSystem;
import com.hbm.inventory.control_panel.DataValueFloat;
import com.hbm.inventory.control_panel.IControllable;
import com.hbm.lib.ForgeDirection;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEDoorAnimationPacket;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.machine.TileEntityLockableBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityDoorGeneric extends TileEntityLockableBase implements ITickable, IAnimatedDoor, IControllable {

	public DoorState state = DoorState.CLOSED;
	public DoorDecl doorType;
	public int openTicks = 0;
	public long animStartTime = 0;
	public int redstonePower;
	public boolean shouldUseBB = false;
	public Set<BlockPos> activatedBlocks = new HashSet<>(4);

	private AudioWrapper audio;
	private AudioWrapper audio2;

	// if a control panel sends a toggle from 0->1, it masters the door until toggled again.
	private boolean toggledByPanel = false;


	public TileEntityDoorGeneric(){
	}
	
	@Override
	public void update(){
		if(doorType == null && this.getBlockType() instanceof BlockDoorGeneric)
			doorType = ((BlockDoorGeneric)this.getBlockType()).type;
		if(state == DoorState.OPENING) {
			openTicks++;
			if(openTicks >= doorType.timeToOpen()) {
				openTicks = doorType.timeToOpen();
			}
		} else if(state == DoorState.CLOSING) {
			openTicks--;
			if(openTicks <= 0) {
				openTicks = 0;
			}
		}

		if(!world.isRemote) {
			int[][] ranges = doorType.getDoorOpenRanges();
			ForgeDirection dir = ForgeDirection.getOrientation(getBlockMetadata() - BlockDummyable.offset);
			if(state == DoorState.OPENING) {
				for(int i = 0; i < ranges.length; i++) {
					int[] range = ranges[i];
					BlockPos startPos = new BlockPos(range[0], range[1], range[2]);
					float time = doorType.getDoorRangeOpenTime(openTicks, i);
					for(int j = 0; j < Math.abs(range[3]); j++) {
						if((float)j / (Math.abs(range[3] - 1)) > time)
							break;
						for(int k = 0; k < range[4]; k++) {
							BlockPos add = new BlockPos(0, 0, 0);
							switch(EnumFacing.Axis.values()[range[5]]){
							case X:
								add = new BlockPos(0, k, Math.signum(range[3])*j);
								break;
							case Y:
								add = new BlockPos(k, Math.signum(range[3])*j, 0);
								break;
							case Z:
								add = new BlockPos(Math.signum(range[3])*j, k, 0);
								break;
							}
							Rotation r = dir.getBlockRotation();
							if(dir.toEnumFacing().getAxis() == EnumFacing.Axis.X)
								r = r.add(Rotation.CLOCKWISE_180);
							BlockPos finalPos = startPos.add(add).rotate(r).add(pos);
							if(finalPos.equals(this.pos)) {
								this.shouldUseBB = true;
							} else {
								((BlockDummyable)getBlockType()).makeExtra(world, finalPos.getX(), finalPos.getY(), finalPos.getZ());
							}
						}
					}
				}
			} else if(state == DoorState.CLOSING){
				for(int i = 0; i < ranges.length; i++) {
					int[] range = ranges[i];
					BlockPos startPos = new BlockPos(range[0], range[1], range[2]);
					float time = doorType.getDoorRangeOpenTime(openTicks, i);
					for(int j = Math.abs(range[3])-1; j >= 0; j--) {
						if((float)j / (Math.abs(range[3] - 1)) < time)
							break;
						for(int k = 0; k < range[4]; k++) {
							BlockPos add = new BlockPos(0, 0, 0);
							switch(EnumFacing.Axis.values()[range[5]]){
							case X:
								add = new BlockPos(0, k, Math.signum(range[3])*j);
								break;
							case Y:
								add = new BlockPos(k, Math.signum(range[3])*j, 0);
								break;
							case Z:
								add = new BlockPos(Math.signum(range[3])*j, k, 0);
								break;
							}
							Rotation r = dir.getBlockRotation();
							if(dir.toEnumFacing().getAxis() == EnumFacing.Axis.X)
								r = r.add(Rotation.CLOCKWISE_180);
							BlockPos finalPos = startPos.add(add).rotate(r).add(pos);
							if(finalPos.equals(this.pos)) {
								this.shouldUseBB = false;
							} else {
								((BlockDummyable)getBlockType()).removeExtra(world, finalPos.getX(), finalPos.getY(), finalPos.getZ());
							}
						}
					}
				}
			}
			if(state == DoorState.OPENING && openTicks == doorType.timeToOpen()) {
				state = DoorState.OPEN;
				broadcastControlEvt();
			}
			if(state == DoorState.CLOSING && openTicks == 0) {
				state = DoorState.CLOSED;
				broadcastControlEvt();

				// With door finally closed, mark chunk for rad update since door is now rad resistant
				// No need to update when open as well, as opening door should update
				RadiationSystemNT.markChunkForRebuild(world, pos);
			}
			PacketDispatcher.wrapper.sendToAllAround(new TEDoorAnimationPacket(pos, (byte) state.ordinal(), (byte)(shouldUseBB ? 1 : 0)), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 100));
			
			if(redstonePower > 0){
				tryOpen(-1);
			} else {
				tryClose(-1);
			}
			if(redstonePower == -1){
				redstonePower = 0;
			}
			if (toggledByPanel) {
				tryOpen(-1);
			}
		}
	}

	@Override
	public void onChunkUnload() {
		if(audio != null) {
			audio.stopSound();
			audio = null;
    	}
		if(audio2 != null) {
			audio2.stopSound();
			audio2 = null;
    	}
	}
	
	@Override
	public void onLoad(){
		doorType = ((BlockDoorGeneric)this.getBlockType()).type;
	}

	public boolean tryToggle(EntityPlayer player){
		if(state == DoorState.CLOSED) {
			if(!world.isRemote) {
				open();
				broadcastControlEvt();
			}
			return true;
		} else if(state == DoorState.OPEN) {
			if(!world.isRemote) {
				close();
				broadcastControlEvt();
			}
			return true;
		}
		return false;
	}
	
	public boolean tryOpen(int passcode){
		if(this.isLocked() && passcode != this.lock)
			return false;
		if(state == DoorState.CLOSED) {
			if(!world.isRemote) {
				open();
				broadcastControlEvt();
			}
			return true;
		}
		return false;
	}

	public boolean tryToggle(int passcode){
		if(state == DoorState.CLOSED) {
			return tryOpen(passcode);
		} else if(state == DoorState.OPEN) {
			return tryClose(passcode);
		}
		return false;
	}

	public boolean tryClose(int passcode){
		if(toggledByPanel)
			return false;
		if(this.isLocked() && passcode != lock)
			return false;
		if(state == DoorState.OPEN) {
			if(!world.isRemote) {
				close();
				broadcastControlEvt();
			}
			return true;
		}
		return false;
	}

	@Override
	public void open() {
		if(state == DoorState.CLOSED)
			toggle();
	}

	@Override
	public void close(){
		if(state == DoorState.OPEN)
			toggle();
	}

	@Override
	public DoorState getState(){
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
	@SideOnly(Side.CLIENT)
	public void handleNewState(DoorState newState){
		if(this.state != newState) {
			if(this.state == DoorState.CLOSED && newState == DoorState.OPENING){
				if(audio == null){
					audio = MainRegistry.proxy.getLoopedSoundStartStop(world, doorType.getOpenSoundLoop(), doorType.getOpenSoundStart(), doorType.getOpenSoundEnd(), SoundCategory.BLOCKS, pos.getX(), pos.getY(), pos.getZ(), doorType.getSoundVolume(), 1);
					audio.startSound();
				}
				if(audio2 == null && doorType.getSoundLoop2() != null){
					audio2 = MainRegistry.proxy.getLoopedSoundStartStop(world, doorType.getSoundLoop2(), null, null, SoundCategory.BLOCKS, pos.getX(), pos.getY(), pos.getZ(), doorType.getSoundVolume(), 1);
					audio2.startSound();
				}
			}
			if(this.state == DoorState.OPEN && newState == DoorState.CLOSING){
				if(audio == null){
					audio = MainRegistry.proxy.getLoopedSoundStartStop(world, doorType.getCloseSoundLoop(), doorType.getCloseSoundStart(), doorType.getCloseSoundEnd(), SoundCategory.BLOCKS, pos.getX(), pos.getY(), pos.getZ(), doorType.getSoundVolume(), 1);
					audio.startSound();
				}
				if(audio2 == null && doorType.getSoundLoop2() != null){
					audio2 = MainRegistry.proxy.getLoopedSoundStartStop(world, doorType.getSoundLoop2(), null, null, SoundCategory.BLOCKS, pos.getX(), pos.getY(), pos.getZ(), doorType.getSoundVolume(), 1);
					audio2.startSound();
				}
			}
			if(this.state.isMovingState() && newState.isStationaryState()){
				if(audio != null){
					audio.stopSound();
					audio = null;
				}
				if(audio2 != null){
					audio2.stopSound();
					audio2 = null;
				}
			}

			this.state = newState;
			if(newState.isMovingState())
				animStartTime = System.currentTimeMillis();
		}
	}

	//Ah yes piggy backing on this packet
	@Override
	public void setTextureState(byte tex){
		if(tex > 0)
			shouldUseBB = true;
		else
			shouldUseBB = false;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox(){
		return INFINITE_EXTENT_AABB;
	}

	@Override
	public double getMaxRenderDistanceSquared(){
		return 65536D;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag){
		this.state = DoorState.values()[tag.getByte("state")];
		this.openTicks = tag.getInteger("openTicks");
		this.animStartTime = tag.getInteger("animStartTime");
		this.redstonePower = tag.getInteger("redstoned");
		this.shouldUseBB = tag.getBoolean("shouldUseBB");
		NBTTagCompound activatedBlocks = tag.getCompoundTag("activatedBlocks");
		this.activatedBlocks.clear();
		for(int i = 0; i < activatedBlocks.getKeySet().size()/3; i ++){
			this.activatedBlocks.add(new BlockPos(activatedBlocks.getInteger("x"+i), activatedBlocks.getInteger("y"+i), activatedBlocks.getInteger("z"+i)));
		}
		super.readFromNBT(tag);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag){
		tag.setByte("state", (byte) state.ordinal());
		tag.setInteger("openTicks", openTicks);
		tag.setLong("animStartTime", animStartTime);
		tag.setInteger("redstoned", redstonePower);
		tag.setBoolean("shouldUseBB", shouldUseBB);
		NBTTagCompound activatedBlocks = new NBTTagCompound();
		int i = 0;
		for(BlockPos p : this.activatedBlocks){
			activatedBlocks.setInteger("x"+i, p.getX());
			activatedBlocks.setInteger("y"+i, p.getY());
			activatedBlocks.setInteger("z"+i, p.getZ());
			i++;
		}
		tag.setTag("activatedBlocks", activatedBlocks);
		return super.writeToNBT(tag);
	}

	public void broadcastControlEvt(){
		ControlEventSystem.get(world).broadcastToSubscribed(this, ControlEvent.newEvent("door_open_state").setVar("state", new DataValueFloat(state.ordinal())));
	}
	
	@Override
	public void receiveEvent(BlockPos from, ControlEvent e){
		if (e.name.equals("door_toggle")) {
			if (!isLocked() || (isLocked() && e.vars.get("passcode").getNumber() == lock)) {
				toggledByPanel = !toggledByPanel;
			}
		}
	}
	
	@Override
	public List<String> getInEvents(){
		return Arrays.asList("door_toggle");
	}
	
	@Override
	public List<String> getOutEvents(){
		return Arrays.asList("door_open_state");
	}
	
	@Override
	public void validate(){
		super.validate();
		ControlEventSystem.get(world).addControllable(this);
	}
	
	@Override
	public void invalidate(){
		super.invalidate();
		if(audio != null) {
			audio.stopSound();
			audio = null;
    	}
		if(audio2 != null) {
			audio2.stopSound();
			audio2 = null;
    	}
		ControlEventSystem.get(world).removeControllable(this);
	}

	@Override
	public BlockPos getControlPos(){
		return getPos();
	}

	@Override
	public World getControlWorld(){
		return getWorld();
	}

	public void updateRedstonePower(BlockPos pos){
		//Drillgon200: Best I could come up with without having to use dummy tile entities
		boolean powered = world.isBlockIndirectlyGettingPowered(pos) > 0;
		boolean contained = activatedBlocks.contains(pos);
		if(!contained && powered){
			activatedBlocks.add(pos);
			if(redstonePower == -1){
				redstonePower = 0;
			}
			redstonePower++;
		} else if(contained && !powered){
			activatedBlocks.remove(pos);
			redstonePower--;
			if(redstonePower == 0){
				redstonePower = -1;
			}
		}
	}

}
