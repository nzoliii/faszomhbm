package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineITER;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.inventory.BreederRecipes;
import com.hbm.inventory.BreederRecipes.BreederRecipe;
import com.hbm.inventory.FusionRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemFusionShield;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.lib.ForgeDirection;
import com.hbm.main.AdvancementManager;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.FluidTypePacketTest;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.saveddata.RadiationSavedData;

import api.hbm.energy.IEnergyUser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityITER extends TileEntityMachineBase implements ITickable, IEnergyUser, IFluidHandler, ITankPacketAcceptor {

	public long power;
	public static final long maxPower = 1000000000;
	public static final int powerReq = 500000;
	public int age = 0;
	public FluidTank[] tanks;
	public Fluid[] types;
	public FluidTank plasma;
	public Fluid plasmaType;
	
	public int progress;
	public static final int duration = 100;

	@SideOnly(Side.CLIENT)
	public int blanket;

	public float rotor;
	public float lastRotor;
	public boolean isOn;

	public TileEntityITER() {
		super(5);
		tanks = new FluidTank[2];
		types = new Fluid[2];
		tanks[0] = new FluidTank(12800000);
		types[0] = FluidRegistry.WATER;
		tanks[1] = new FluidTank(1280000);
		types[1] = ModForgeFluids.ultrahotsteam;
		plasma = new FluidTank(16000);
	}

	@Override
	public String getName() {
		return "container.machineITER";
	}

	@Override
	public void update() {
		if(!world.isRemote) {

			age++;
			if(age >= 20) {
				age = 0;
			}

			if(age == 9 || age == 19)
				fillFluidInit(tanks[1]);

			this.updateConnections();
			power = Library.chargeTEFromItems(inventory, 0, power, maxPower);

			/// START Processing part ///

			if(!isOn) {
				plasma.setFluid(null); //jettison plasma if the thing is turned off
			}

			//explode either if there's plasma that is too hot or if the reactor is turned on but the magnets have no power
			if(plasma.getFluidAmount() > 0 && (this.plasmaType.getTemperature() >= this.getShield() || (this.isOn && this.power < powerReq))) {
				this.disassemble();
				Vec3 vec = Vec3.createVectorHelper(5.5, 0, 0);
				vec.rotateAroundY(world.rand.nextFloat() * (float) Math.PI * 2F);

				world.newExplosion(null, pos.getX() + 0.5 + vec.xCoord, pos.getY() + 0.5 + world.rand.nextGaussian() * 1.5D, pos.getZ() + 0.5 + vec.zCoord, 2.5F, true, true);
				RadiationSavedData.incrementRad(world, pos, 2000F, 10000F);
			}

			if(isOn && power >= powerReq) {
				power -= powerReq;

				if(plasma.getFluidAmount() > 0) {

					int chance = FusionRecipes.getByproductChance(plasmaType);

					if(chance > 0 && world.rand.nextInt(chance) == 0)
						produceByproduct();
				}

				if(plasma.getFluidAmount() > 0 && this.getShield() != 0) {

					ItemFusionShield.setShieldDamage(inventory.getStackInSlot(3), ItemFusionShield.getShieldDamage(inventory.getStackInSlot(3)) + 1);

					if(ItemFusionShield.getShieldDamage(inventory.getStackInSlot(3)) > ((ItemFusionShield)inventory.getStackInSlot(3).getItem()).maxDamage){
						inventory.setStackInSlot(3, ItemStack.EMPTY);
						world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.shutdown, SoundCategory.BLOCKS, 5F, 1F);
						this.isOn = false;
						this.markDirty();
					}
				}

				int prod = FusionRecipes.getSteamProduction(plasmaType);
				
				for(int i = 0; i < 20; i++) {

					if(plasma.getFluidAmount() > 0) {

						if(tanks[0].getFluidAmount() >= prod * 10) {
							tanks[0].drain(prod * 10, true);
							tanks[1].fill(new FluidStack(types[1], prod), true);

						}

						plasma.drain(1, true);
					}
				}
			}
			doBreederStuff();
			/// END Processing part ///

			/// START Notif packets ///
			PacketDispatcher.wrapper.sendToAllAround(new FluidTankPacket(pos, new FluidTank[] { tanks[0], tanks[1], plasma }), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 120));
			PacketDispatcher.wrapper.sendToAllAround(new FluidTypePacketTest(pos.getX(), pos.getY(), pos.getZ(), new Fluid[]{plasmaType}), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 20));
			/// END Notif packets ///
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("isOn", isOn);
			data.setLong("power", power);
			data.setInteger("progress", progress);

			if(inventory.getStackInSlot(3).getItem() == ModItems.fusion_shield_tungsten) {
				data.setInteger("blanket", 1);
			} else if(inventory.getStackInSlot(3).getItem() == ModItems.fusion_shield_desh) {
				data.setInteger("blanket", 2);
			} else if(inventory.getStackInSlot(3).getItem() == ModItems.fusion_shield_chlorophyte) {
				data.setInteger("blanket", 3);
			} else if(inventory.getStackInSlot(3).getItem() == ModItems.fusion_shield_vaporwave) {
				data.setInteger("blanket", 4);
			} else {
				data.setInteger("blanket", 0);
			}

			this.networkPack(data, 250);
		} else {

			this.lastRotor = this.rotor;

			if(this.isOn && this.power >= powerReq) {

				this.rotor += 15F;

				if(this.rotor >= 360) {
					this.rotor -= 360;
					this.lastRotor -= 360;
				}
			}
		}
	}

	private void updateConnections() {
		this.trySubscribe(world, pos.add(0, 3, 0), ForgeDirection.UP);
		this.trySubscribe(world, pos.add(0, -3, 0), ForgeDirection.DOWN);
	}
	
	private void doBreederStuff() {

		if(plasma.getFluidAmount() == 0) {
			this.progress = 0;
			return;
		}

		BreederRecipe out = BreederRecipes.getOutput(inventory.getStackInSlot(1));
		
		if(inventory.getStackInSlot(1).getItem() == ModItems.meteorite_sword_irradiated)
			out = new BreederRecipe(ModItems.meteorite_sword_fused, 1);

		if(inventory.getStackInSlot(1).getItem() == ModItems.meteorite_sword_fused)
			out = new BreederRecipe(ModItems.meteorite_sword_baleful, 4);

		if(out == null) {
			this.progress = 0;
			return;
		}

		if(!inventory.getStackInSlot(2).isEmpty() && inventory.getStackInSlot(2).getCount() >= inventory.getStackInSlot(2).getMaxStackSize()) {
			this.progress = 0;
			return;
		}

		int level = FusionRecipes.getBreedingLevel(plasmaType);

		if(out.heat > level) {
			this.progress = 0;
			return;
		}	

		progress++;

		if(progress > duration) {

			if(!inventory.getStackInSlot(2).isEmpty()) {
				inventory.getStackInSlot(2).grow(1);
			} else {
				inventory.setStackInSlot(2, out.output.copy());
			}

			inventory.getStackInSlot(1).shrink(1);

			if(inventory.getStackInSlot(1).isEmpty())
				inventory.setStackInSlot(1, ItemStack.EMPTY);

			this.markDirty();
		}
	}

	private void produceByproduct() {

		ItemStack by = FusionRecipes.getByproduct(plasmaType);

		if(by == null)
			return;

		if(inventory.getStackInSlot(4).isEmpty()) {
			inventory.setStackInSlot(4, by);
			return;
		}

		if(inventory.getStackInSlot(4).getItem() == by.getItem() && inventory.getStackInSlot(4).getItemDamage() == by.getItemDamage() && inventory.getStackInSlot(4).getCount() < inventory.getStackInSlot(4).getMaxStackSize()) {
			inventory.getStackInSlot(4).grow(1);
		}
	}

	public int getShield() {

		if(inventory.getStackInSlot(3).isEmpty() || !(inventory.getStackInSlot(3).getItem() instanceof ItemFusionShield))
			return 0;

		return ((ItemFusionShield) inventory.getStackInSlot(3).getItem()).maxTemp + 273;
	}

	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.isOn = data.getBoolean("isOn");
		this.power = data.getLong("power");
		this.blanket = data.getInteger("blanket");
		this.progress = data.getInteger("progress");
	}

	@Override
	public void handleButtonPacket(int value, int meta) {

		if(meta == 0) {
			this.isOn = !this.isOn;
		}
	}

	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public long getProgressScaled(long i) {
		return (progress * i) / duration;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		tanks[0].readFromNBT(compound.getCompoundTag("water"));
		tanks[1].readFromNBT(compound.getCompoundTag("steam"));
		plasma.readFromNBT(compound.getCompoundTag("plasma"));
		plasmaType = FluidRegistry.getFluid(compound.getString("plasma_type"));
		this.power = compound.getLong("power");
		this.isOn = compound.getBoolean("isOn");
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("water", tanks[0].writeToNBT(new NBTTagCompound()));
		compound.setTag("steam", tanks[1].writeToNBT(new NBTTagCompound()));
		compound.setTag("plasma", plasma.writeToNBT(new NBTTagCompound()));
		if(plasmaType != null)
			compound.setString("plasma_type", plasmaType.getName());
		compound.setLong("power", this.power);
		compound.setBoolean("isOn", isOn);
		return super.writeToNBT(compound);
	}

	public void fillFluidInit(FluidTank type) {
		fillFluid(pos.getX(), pos.getY() - 3, pos.getZ(), type);
		fillFluid(pos.getX(), pos.getY() + 3, pos.getZ(), type);
	}

	public void fillFluid(int x, int y, int z, FluidTank type) {
		FFUtils.fillFluid(this, type, world, new BlockPos(x, y, z), 1280000);
	}

	public void disassemble() {
		
		MachineITER.drop = false;

		int[][][] layout = TileEntityITERStruct.layout;

		for(int y = 0; y < 5; y++) {
			for(int x = 0; x < layout[0].length; x++) {
				for(int z = 0; z < layout[0][0].length; z++) {

					int ly = y > 2 ? 4 - y : y;

					int width = 7;

					if(x == width && y == 0 && z == width)
						continue;

					int b = layout[ly][x][z];

					switch(b) {
					case 1:
						world.setBlockState(new BlockPos(pos.getX() - width + x, pos.getY() + y - 2, pos.getZ() - width + z), ModBlocks.fusion_conductor.getDefaultState());
						break;
					case 2:
						world.setBlockState(new BlockPos(pos.getX() - width + x, pos.getY() + y - 2, pos.getZ() - width + z), ModBlocks.fusion_center.getDefaultState());
						break;
					case 3:
						world.setBlockState(new BlockPos(pos.getX() - width + x, pos.getY() + y - 2, pos.getZ() - width + z), ModBlocks.fusion_motor.getDefaultState());
						break;
					case 4:
						world.setBlockState(new BlockPos(pos.getX() - width + x, pos.getY() + y - 2, pos.getZ() - width + z), ModBlocks.reinforced_glass.getDefaultState());
						break;
					}
				}
			}
		}

		world.setBlockState(new BlockPos(pos.getX(), pos.getY() - 2, pos.getZ()), ModBlocks.struct_iter_core.getDefaultState());
		
		MachineITER.drop = true;
		
		List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class,
				new AxisAlignedBB(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5).grow(50, 10, 50));

		for(EntityPlayer player : players) {
			AdvancementManager.grantAchievement(player, AdvancementManager.achMeltdown);
		}
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[] { tanks[0].getTankProperties()[0], tanks[1].getTankProperties()[0], plasma.getTankProperties()[0] };
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(resource != null) {
			if(resource.getFluid() == types[0]) {
				return tanks[0].fill(resource, doFill);
			} else if(resource.getFluid() == types[1]) {
				return tanks[1].fill(resource, doFill);
			}
		}
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if(resource != null && resource.getFluid() == ModForgeFluids.ultrahotsteam) {
			return tanks[1].drain(resource, doDrain);
		}
		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return tanks[1].drain(maxDrain, doDrain);
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int amount) {
		return true;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(EnumFacing e) {
		return new int[] { 2, 4 };
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if(bb == null) {
			bb = new AxisAlignedBB(pos.getX() + 0.5 - 8, pos.getY() + 0.5 - 3, pos.getZ() + 0.5 - 8, pos.getX() + 0.5 + 8, pos.getY() + 0.5 + 3, pos.getZ() + 0.5 + 8);
		}

		return bb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public void recievePacket(NBTTagCompound[] tags) {
		if(tags.length == 3) {
			tanks[0].readFromNBT(tags[0]);
			tanks[1].readFromNBT(tags[1]);
			plasma.readFromNBT(tags[2]);
		}
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return true;
		}
		return super.hasCapability(capability, facing);
	}
}
