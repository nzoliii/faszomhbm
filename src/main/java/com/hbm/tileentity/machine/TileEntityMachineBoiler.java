package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineBoiler;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.inventory.HeatRecipes;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class TileEntityMachineBoiler extends TileEntityMachineBase implements ITickable, IFluidHandler, ITankPacketAcceptor {

	public int burnTime;
	public int heat = 2000;
	public static final int maxHeat = 50000;
	public int age = 0;
	public FluidTank[] tanks;

	private static final int[] slots_top = new int[] {4};
	private static final int[] slots_bottom = new int[] {6};
	private static final int[] slots_side = new int[] {4};

	private boolean needsUpdate = false;

	public TileEntityMachineBoiler() {
		super(7);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(8000);
		tanks[1] = new FluidTank(8000);
	}

	@Override
	public String getName(){
		return "container.machineBoiler";
	}
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		if (world.getTileEntity(pos) != this) {
			return false;
		} else {
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64;
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(EnumFacing e){
		int i = e.ordinal();
		return i == 0 ? slots_bottom : (i == 1 ? slots_top : slots_side);
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack itemStack, int amount){
		return this.isItemValidForSlot(slot, itemStack);
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int amount){
		return false;
	}
	
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		if(i == 4)
			if(TileEntityFurnace.getItemBurnTime(stack) > 0)
				return true;
		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		heat = nbt.getInteger("heat");
		burnTime = nbt.getInteger("burnTime");
		if (nbt.hasKey("tanks"))
			FFUtils.deserializeTankArray(nbt.getTagList("tanks", 10), tanks);
		super.readFromNBT(nbt);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("heat", heat);
		nbt.setInteger("burnTime", burnTime);
		nbt.setTag("tanks", FFUtils.serializeTankArray(tanks));
		return super.writeToNBT(nbt);
	}

	public int getHeatScaled(int i) {
		return (heat * i) / maxHeat;
	}

	@Override
	public void update() {
		if (!world.isRemote) {
			age++;
			if (age >= 20) {
				age = 0;
			}
			if (needsUpdate) {
				needsUpdate = false;
			}
			PacketDispatcher.wrapper.sendToAllAround(new FluidTankPacket(pos.getX(), pos.getY(), pos.getZ(), new FluidTank[] { tanks[0], tanks[1] }), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
			if (age == 9 || age == 19)
				fillFluidInit(tanks[1]);

			Object[] outs;
			if (tanks[0].getFluid() != null) {
				outs = HeatRecipes.getBoilerOutput(tanks[0].getFluid().getFluid());
			} else {
				outs = HeatRecipes.getBoilerOutput(null);
			}
			if (this.inputValidForTank(0, 2))
				if (FFUtils.fillFromFluidContainer(inventory, tanks[0], 2, 3))
					needsUpdate = true;

			if (FFUtils.fillFluidContainer(inventory, tanks[1], 5, 6))
				needsUpdate = true;

			if (heat > 2000) {
				heat -= 15;
			}

			if (burnTime > 0) {
				burnTime--;
				heat += 50;
			}

			if (burnTime <= 0 && world.getBlockState(pos).getBlock() == ModBlocks.machine_boiler_on)
				MachineBoiler.updateBlockState(false, world, pos);

			if (heat > maxHeat)
				heat = maxHeat;

			if (burnTime == 0 && TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(4)) > 0) {
				burnTime = (int) (TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(4)) * 0.25);
				Item containerItem = inventory.getStackInSlot(4).getItem().getContainerItem();
				inventory.getStackInSlot(4).shrink(1);
				

				if (inventory.getStackInSlot(4).isEmpty()) {

					if (containerItem != null)
						inventory.setStackInSlot(4, new ItemStack(containerItem));
					else
						inventory.setStackInSlot(4, ItemStack.EMPTY);
				}

			}

			if (burnTime > 0 && world.getBlockState(pos).getBlock() == ModBlocks.machine_boiler_off)
				MachineBoiler.updateBlockState(true, world, pos);

			if (outs != null) {

				for (int i = 0; i < (heat / ((Integer) outs[3]).intValue()); i++) {
					if(tanks[0].getFluidAmount() >= ((Integer) outs[2]).intValue()*5 && tanks[1].getFluidAmount() + ((Integer) outs[1]).intValue()*5 <= tanks[1].getCapacity()) {
						tanks[0].drain(((Integer) outs[2])*5, true);
						tanks[1].fill(new FluidStack((Fluid) outs[0], ((Integer) outs[1]*5)), true);
						needsUpdate = true;
						if (i == 0)
							heat -= 25;
						else
							heat -= 40;
					}
				}
			}

			if (heat < 2000) {
				heat = 2000;
			}

			
			detectAndSendChanges();
		}

	}
	
	

	public void fillFluidInit(FluidTank tank) {
		boolean update = needsUpdate;
		
		update = FFUtils.fillFluid(this, tank, world, pos.west(), 8000) || update;
		update = FFUtils.fillFluid(this, tank, world, pos.east(), 8000) || update;
		update = FFUtils.fillFluid(this, tank, world, pos.north(), 8000) || update;
		update = FFUtils.fillFluid(this, tank, world, pos.south(), 8000) || update;
		update = FFUtils.fillFluid(this, tank, world, pos.up(), 8000) || update;
		update = FFUtils.fillFluid(this, tank, world, pos.down(), 8000) || update;

		
		needsUpdate = update;
	}

	protected boolean inputValidForTank(int tank, int slot) {
		if(inventory.getStackInSlot(slot).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null) && isValidFluid(FluidUtil.getFluidContained(inventory.getStackInSlot(slot)))) {
			return true;
		}
		return false;
	}
	
	private boolean isValidFluid(FluidStack stack) {
		if(stack == null)
			return false;
		return HeatRecipes.hasBoilRecipe(stack.getFluid());
	}

	@Override
	public void recievePacket(NBTTagCompound[] tags) {
		if(tags.length != 2) {
			return;
		} else {
			tanks[0].readFromNBT(tags[0]);
			tanks[1].readFromNBT(tags[1]);
		}
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{tanks[0].getTankProperties()[0], tanks[1].getTankProperties()[0]};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if (isValidFluid(resource)) {
			return tanks[0].fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if (resource == null || !resource.isFluidEqual(tanks[1].getFluid())) {
			return null;
		}
		return tanks[1].drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return tanks[1].drain(maxDrain, doDrain);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		} else {
			return super.getCapability(capability, facing);
		}
	}
	
	private int detectHeat;
	private int detectBurnTime;
	private FluidTank[] detectTanks = new FluidTank[]{null, null};
	
	private void detectAndSendChanges() {
		boolean mark = false;
		if(detectHeat != heat){
			detectHeat = heat;
			mark = true;
		}
		if(detectBurnTime != burnTime){
			detectBurnTime = burnTime;
			mark = true;
		}
		if(!FFUtils.areTanksEqual(tanks[0], detectTanks[0])){
			needsUpdate = true;
			detectTanks[0] = FFUtils.copyTank(tanks[0]);
			mark = true;
		}
		if(!FFUtils.areTanksEqual(tanks[1], detectTanks[1])){
			needsUpdate = true;
			detectTanks[1] = FFUtils.copyTank(tanks[1]);
			mark = true;
		}
		PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(pos.getX(), pos.getY(), pos.getZ(), heat, 0), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
		PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(pos.getX(), pos.getY(), pos.getZ(), burnTime, 1), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
		if(mark)
			markDirty();
	}

}
