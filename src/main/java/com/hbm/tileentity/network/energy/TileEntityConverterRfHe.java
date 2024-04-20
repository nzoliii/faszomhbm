package com.hbm.tileentity.network.energy;

import com.hbm.config.GeneralConfig;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyGenerator;
import cofh.redstoneflux.api.IEnergyReceiver;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Optional;

@Optional.InterfaceList({@Optional.Interface(iface = "cofh.redstoneflux.api.IEnergyReceiver", modid = "redstoneflux")})
public class TileEntityConverterRfHe extends TileEntityLoadedBase implements IEnergyGenerator, IEnergyReceiver, IEnergyStorage {

	private long subBuffer;
	private boolean recursionBrake = false;

	//NTM HE
	@Override
	public void setPower(long power) {
		subBuffer = power;
	}

	@Override
	public long getPower() {
		return subBuffer;
	}

	@Override
	public long getMaxPower() {
		return subBuffer;
	}

	//RF
	@Override
	public int getEnergyStored(EnumFacing from) {
		return 0;
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		if(recursionBrake)
			return 0;
		
		if(simulate)
			return 0;
		
		recursionBrake = true;
		
		long capacity = (long)(maxReceive / GeneralConfig.conversionRateHeToRF);
		subBuffer = capacity;
		
		this.sendPower(world, pos);
		
		recursionBrake = false;
		
		return (int) ((capacity - subBuffer) * GeneralConfig.conversionRateHeToRF);
	}

	//FE
	@Override
	public boolean canExtract(){
		return false;
	}

	@Override
	public boolean canReceive(){
		return true;
	}

	@Override
	public int getMaxEnergyStored(){
		return Integer.MAX_VALUE;
	}

	@Override
	public int getEnergyStored(){
		return 0;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate){
		return 0;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate){
		if(recursionBrake)
			return 0;
		
		if(simulate)
			return maxReceive;
		
		recursionBrake = true;
		
		long capacity = (long)(maxReceive / GeneralConfig.conversionRateHeToRF);
		subBuffer = capacity;
		
		this.sendPower(world, pos);
		
		recursionBrake = false;
		
		return (int) ((capacity - subBuffer) * GeneralConfig.conversionRateHeToRF);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing){
		if(capability == CapabilityEnergy.ENERGY){
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing){
		if(capability == CapabilityEnergy.ENERGY){
			return (T) this;
		}
		return super.getCapability(capability, facing);
	}
}
