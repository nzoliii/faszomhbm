package com.hbm.tileentity.bomb;

import com.hbm.entity.item.EntityFireworks;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class TileEntityFireworks extends TileEntity implements ITickable {

	public int color = 0xff0000;
	public String message = "EAT MY ASS";
	public int charges;

	int index;
	int delay;
	
	@Override
	public void update() {
		if(!world.isRemote) {

			if(world.isBlockIndirectlyGettingPowered(pos) > 0 && !message.isEmpty() && charges > 0) {

				delay--;

				if(delay <= 0) {
					delay = 30;

					int c = (int)(message.charAt(index));

					int mod = index % 9;

					double offX = (mod / 3 - 1) * 0.3125;
					double offZ = (mod % 3 - 1) * 0.3125;

					EntityFireworks fireworks = new EntityFireworks(world, pos.getX() + 0.5 + offX, pos.getY() + 1.5, pos.getZ() + 0.5 + offZ, color, c);
					world.spawnEntity(fireworks);

					world.playSound(null, fireworks.posX, fireworks.posY, fireworks.posZ, HBMSoundHandler.rocketFlame, SoundCategory.BLOCKS, 3.0F, 1.0F);

					charges--;
					this.markDirty();

					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "vanillaExt");
					data.setString("mode", "flame");
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, pos.getX() + 0.5 + offX, pos.getY() + 1.125, pos.getZ() + 0.5 + offZ), new TargetPoint(this.world.provider.getDimension(), pos.getX() + 0.5 + offX, pos.getY() + 1.125, pos.getZ() + 0.5 + offZ, 100));

					index++;

					if(index >= message.length()) {
						index = 0;
						delay = 100;
					}
				}

			} else {
				delay = 0;
				index = 0;
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.charges = compound.getInteger("charges");
		this.color = compound.getInteger("color");
		this.message = compound.getString("message");
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("charges", charges);
		compound.setInteger("color", color);
		compound.setString("message", message);
		return super.writeToNBT(compound);
	}

}
