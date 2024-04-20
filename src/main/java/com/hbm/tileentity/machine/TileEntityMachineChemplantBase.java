package com.hbm.tileentity.machine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.hbm.energy.IEnergyUser;
import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.ChemplantRecipes;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.lib.ForgeDirection;
import com.hbm.handler.MultiblockHandler;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.InventoryUtil;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class TileEntityMachineChemplantBase extends TileEntityMachineBase implements IEnergyUser, ITickable, IFluidHandler {
	public long power;
	public int[] progress;
	public int[] maxProgress;
	public boolean isProgressing;

	public static class TypedFluidTank {
		protected Fluid type;
		protected final FluidTank tank;

		protected TypedFluidTank(Fluid type, FluidTank tank) {
			this.type = type;
			this.tank = tank;
		}

		public void setType(@Nullable Fluid type) {
			if(type == null) {
				this.tank.setFluid(null);
			}

			if(this.type == type) {
				return;
			}

			this.type = type;
			this.tank.setFluid(null);
		}

		public void writeToNBT(NBTTagCompound nbt) {
			if(this.type != null) {
				nbt.setString("type", this.type.getName());
			}

			this.tank.writeToNBT(nbt);
		}

		public void readFromNBT(NBTTagCompound nbt) {
			if(nbt.hasKey("type")) {
				this.type = FluidRegistry.getFluid(nbt.getString("type"));
			}
			this.tank.readFromNBT(nbt);
		}

		public FluidTank getTank() {
			return tank;
		}

		public Fluid getType() {
			return type;
		}
	}

	public TypedFluidTank[] tanks;

	int consumption = 100;
	int speed = 100;

	public TileEntityMachineChemplantBase(int scount) {
		super(scount);

		int count = this.getRecipeCount();

		progress = new int[count];
		maxProgress = new int[count];

		tanks = new TypedFluidTank[4 * count];
		for(int idx = 0; idx < tanks.length; ++idx) {
			tanks[idx] = new TypedFluidTank(null, new FluidTank(this.getTankCapacity()));
		}
	}

	@Override
	public void update() {
		if(!world.isRemote) {
			int count = this.getRecipeCount();

			this.isProgressing = false;
			this.power = Library.chargeTEFromItems(inventory, 0, this.power, this.getMaxPower());

			for(int idx = 0; idx < count; ++idx) {
				loadItems(idx);
				unloadItems(idx);
			}

			for(int i = 0; i < count; i++) {
				if(!canProcess(i)) {
					this.progress[i] = 0;
				} else {
					isProgressing = true;
					process(i);
				}
			}
		}
	}

	protected boolean canProcess(int index) {
		int templateIdx = getTemplateIndex(index);
		ItemStack templateStack = inventory.getStackInSlot(templateIdx);
		if(templateStack.isEmpty() || templateStack.getItem() != ModItems.chemistry_template) {
			return false;
		}

		if(!ChemplantRecipes.hasRecipe(templateStack)) {
			return false;
		}

		List<AStack> itemInputs = ChemplantRecipes.getChemInputFromTempate(templateStack);
		FluidStack[] fluidInputs = ChemplantRecipes.getFluidInputFromTempate(templateStack);
		ItemStack[] itemOutputs = ChemplantRecipes.getChemOutputFromTempate(templateStack);
		FluidStack[] fluidOutputs = ChemplantRecipes.getFluidOutputFromTempate(templateStack);

		setupTanks(fluidInputs, fluidOutputs, index);

		if(this.power < this.consumption) {
			return false;
		}

		if(!hasRequiredFluids(fluidInputs, index)) {
			return false;
		}

		if(!hasSpaceForFluids(fluidOutputs, index)) {
			return false;
		}

		if(!hasRequiredItems(itemInputs, index)) {
			return false;
		}

		if(!hasSpaceForItems(itemOutputs, index)) {
			return false;
		}

		return true;
	}

	private void setupTanks(@Nullable FluidStack[] inputs, @Nullable FluidStack[] outputs, int index) {
		if(inputs != null) {
			for(int i = 0; i < inputs.length; i++) {
				if(inputs[i] != null) {
					tanks[index * 4 + i].setType(inputs[i].getFluid());
				}
			}
		}

		if(outputs != null) {
			for(int i = 0; i < outputs.length; i++) {
				if(outputs[i] != null) {
					tanks[index * 4 + i + 2].setType(outputs[i].getFluid());
				}
			}
		}
	}

	private boolean hasRequiredFluids(@Nullable FluidStack[] inputs, int index) {
		if(inputs == null) {
			return true;
		}

		for(int i = 0; i < inputs.length; i++) {
			if(inputs[i] != null) {
				if(tanks[index * 4 + i].tank.getFluidAmount() < inputs[i].amount) {
					return false;
				}
			}
		}

		return true;
	}

	private boolean hasSpaceForFluids(@Nullable FluidStack[] inputs, int index) {
		if(inputs == null) {
			return true;
		}

		for(int i = 0; i < inputs.length; i++) {
			if(inputs[i] != null) {
				if(tanks[index * 4 + i + 2].tank.getFluidAmount() + inputs[i].amount > tanks[index * 4 + i + 2].tank.getCapacity()) {
					return false;
				}
			}
		}

		return true;
	}

	private boolean hasRequiredItems(@Nullable List<AStack> inputs, int index) {
		if(inputs == null) {
			return true;
		}

		int[] indices = getSlotIndicesFromIndex(index);
		return InventoryUtil.doesArrayHaveIngredients(inventory, indices[0], indices[1], inputs);
	}

	private boolean hasSpaceForItems(@Nullable ItemStack[] outputs, int index) {
		if(outputs == null) {
			return true;
		}

		int[] indices = getSlotIndicesFromIndex(index);

		return InventoryUtil.doesArrayHaveSpace(inventory, indices[2], indices[3], outputs);
	}

	protected void process(int index) {
		this.power -= this.consumption;
		this.progress[index]++;

		if(inventory.getStackInSlot(0).getItem() == ModItems.meteorite_sword_machined) {
			inventory.setStackInSlot(0, new ItemStack(ModItems.meteorite_sword_machined));
		}

		int templateIdx = getTemplateIndex(index);
		ItemStack templateStack = inventory.getStackInSlot(templateIdx);

		List<AStack> itemInputs = ChemplantRecipes.getChemInputFromTempate(templateStack);
		FluidStack[] fluidInputs = ChemplantRecipes.getFluidInputFromTempate(templateStack);
		ItemStack[] itemOutputs = ChemplantRecipes.getChemOutputFromTempate(templateStack);
		FluidStack[] fluidOutputs = ChemplantRecipes.getFluidOutputFromTempate(templateStack);

		this.maxProgress[index] = ChemplantRecipes.getProcessTime(templateStack) * this.speed / 100;

		if(this.progress[index] >= this.maxProgress[index]) {
			consumeFluids(fluidInputs, index);
			produceFluids(fluidOutputs, index);
			consumeItems(itemInputs, index);
			produceItems(itemOutputs, index);
			this.progress[index] = 0;
			this.markDirty();
		}
	}

	private void consumeFluids(@Nullable FluidStack[] inputs, int index) {
		if(inputs == null) {
			return;
		}

		for(int i = 0; i < inputs.length; i++) {
			if(inputs[i] != null) {
				tanks[index * 4 + i].tank.drain(inputs[i].amount, true);
			}
		}
	}

	private void produceFluids(@Nullable FluidStack[] outputs, int index) {
		if(outputs == null) {
			return;
		}

		for(int i = 0; i < outputs.length; i++) {
			if(outputs[i] != null) {
				tanks[index * 4 + i + 2].tank.fill(outputs[i], true);
			}
		}
	}

	private void consumeItems(@Nullable List<AStack> inputs, int index) {
		if(inputs == null) {
			return;
		}

		int[] indices = getSlotIndicesFromIndex(index);

		for(AStack in : inputs) {
			if(in != null)
				InventoryUtil.tryConsumeAStack(inventory, indices[0], indices[1], in);
		}
	}

	private void produceItems(@Nullable ItemStack[] outputs, int index) {
		if(outputs == null) {
			return;
		}

		int[] indices = getSlotIndicesFromIndex(index);

		for(ItemStack out : outputs) {
			if(out != null)
				InventoryUtil.tryAddItemToInventory(inventory, indices[2], indices[3], out.copy());
		}
	}

	private void loadItems(int index) {
		int templateIdx = getTemplateIndex(index);
		ItemStack templateStack = inventory.getStackInSlot(templateIdx);
		if(templateStack.isEmpty() || templateStack.getItem() != ModItems.chemistry_template) {
			return;
		}

		if(ChemplantRecipes.hasRecipe(templateStack)) {
			List<AStack> itemInputs = ChemplantRecipes.getChemInputFromTempate(templateStack);
			if(itemInputs == null) {
				return;
			}

			BlockPos[] positions = getInputPositions();
			int[] indices = getSlotIndicesFromIndex(index);

			for(BlockPos pos : positions) {
				TileEntity te = world.getTileEntity(pos);

				if(te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH)) {
					IItemHandler cap = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
					int[] slots;
					if(te instanceof TileEntityMachineBase) {
						ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
						slots = ((TileEntityMachineBase) te).getAccessibleSlotsFromSide(dir.toEnumFacing());
						tryFillAssemblerCap(cap, slots, (TileEntityMachineBase) te, indices[0], indices[1], itemInputs);
					} else {
						slots = new int[cap.getSlots()];
						for(int i = 0; i < slots.length; i++)
							slots[i] = i;
						tryFillAssemblerCap(cap, slots, null, indices[0], indices[1], itemInputs);
					}
				}	
			}
		}
	}

	private void unloadItems(int index) {
		BlockPos[] positions = getOutputPositions();
		int[] indices = getSlotIndicesFromIndex(index);

		for(BlockPos pos : positions) {
			TileEntity te = world.getTileEntity(pos);
			if(te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH)) {
				IItemHandler cap = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
				for(int i = indices[2]; i <= indices[3]; i++) {
					tryFillContainerCap(cap, i);
				}
			}
		}
	}

	//Unloads output into chests. Capability version.
	public boolean tryFillContainerCap(IItemHandler chest, int slot) {
		//Check if we have something to output
		if(inventory.getStackInSlot(slot).isEmpty())
			return false;

		for(int i = 0; i < chest.getSlots(); i++) {
			
			ItemStack outputStack = inventory.getStackInSlot(slot).copy();
			if(outputStack.isEmpty())
				return false;

			ItemStack chestItem = chest.getStackInSlot(i).copy();
			if(chestItem.isEmpty() || (Library.areItemStacksCompatible(outputStack, chestItem, false) && chestItem.getCount() < chestItem.getMaxStackSize())) {
				inventory.getStackInSlot(slot).shrink(1);

				outputStack.setCount(1);
				chest.insertItem(i, outputStack, false);

				return true;
			}
		}

		return false;
	}

	public boolean tryFillAssemblerCap(IItemHandler container, int[] allowedSlots, TileEntityMachineBase te, int minSlot, int maxSlot, List<AStack> recipeIngredients) {
		if(allowedSlots.length < 1)
			return false;

		if(recipeIngredients == null) //No recipe template found
			return false;
		else {
			Map<Integer, ItemStack> itemStackMap = new HashMap<Integer, ItemStack>();

			for(int slot : allowedSlots) {
				container.getStackInSlot(slot);
				if(container.getStackInSlot(slot).isEmpty()) { // check next slot in chest if it is empty
					continue;
				} else { // found an item in chest
					itemStackMap.put(slot, container.getStackInSlot(slot).copy());
				}
			}
			if(itemStackMap.size() == 0) {
				return false;
			}

			for(int ig = 0; ig < recipeIngredients.size(); ig++) {

				AStack nextIngredient = recipeIngredients.get(ig).copy(); // getting new ingredient

				int ingredientSlot = getValidSlot(nextIngredient, minSlot, maxSlot);


				if(ingredientSlot < minSlot)
					continue; // Ingredient filled or Assembler is full

				int possibleAmount = inventory.getStackInSlot(ingredientSlot).getMaxStackSize() - inventory.getStackInSlot(ingredientSlot).getCount(); // how many items do we need to fill the stack?

				if(possibleAmount == 0) { // full
					System.out.println("This should never happen method getValidSlot broke");
					continue;
				}
				// Ok now we know what we are looking for(nexIngredient) and where to put it (ingredientSlot) - So lets see if we find some of it in containers
				for(Map.Entry<Integer, ItemStack> set :
						itemStackMap.entrySet()) {
					ItemStack stack = set.getValue();
					int slot = set.getKey();
					ItemStack compareStack = stack.copy();
					compareStack.setCount(1);

					if(isItemAcceptable(nextIngredient.getStack(), compareStack)) { // bingo found something

						int foundCount = Math.min(stack.getCount(), possibleAmount);
						if(te != null && !te.canExtractItem(slot, stack, foundCount))
							continue;
						if(foundCount > 0) {
							possibleAmount -= foundCount;
							container.extractItem(slot, foundCount, false);
							inventory.getStackInSlot(ingredientSlot);
							if(inventory.getStackInSlot(ingredientSlot).isEmpty()) {

								stack.setCount(foundCount);
								inventory.setStackInSlot(ingredientSlot, stack);

							} else {
								inventory.getStackInSlot(ingredientSlot).grow(foundCount); // transfer complete
							}
						} else {
							break; // ingredientSlot filled
						}
					}
				}

			}
			return true;
		}
	}

	private int getValidSlot(AStack nextIngredient, int minSlot, int maxSlot) {
		int firstFreeSlot = -1;
		int stackCount = (int) Math.ceil(nextIngredient.count() / 64F);
		int stacksFound = 0;

		nextIngredient = nextIngredient.singulize();

		for(int k = minSlot; k <= maxSlot; k++) { //scaning inventory if some of the ingredients allready exist
			if(stacksFound < stackCount) {
				ItemStack assStack = inventory.getStackInSlot(k).copy();
				if(assStack.isEmpty()) {
					if(firstFreeSlot < minSlot)
						firstFreeSlot = k;
					continue;
				} else { // check if there are already enough filled stacks is full

					assStack.setCount(1);
					if(nextIngredient.isApplicable(assStack)) { // check if it is the right item

						if(inventory.getStackInSlot(k).getCount() < assStack.getMaxStackSize()) // is that stack full?
							return k; // found a not full slot where we already have that ingredient
						else
							stacksFound++;
					}
				}
			} else {
				return -1; // All required stacks are full
			}
		}
		if(firstFreeSlot < minSlot) // nothing free in assembler inventory anymore
			return -2;
		return firstFreeSlot;
	}

	public boolean isItemAcceptable(ItemStack stack1, ItemStack stack2) {

		if(stack1 != null && stack2 != null && stack1.getItem() != Items.AIR && stack1.getItem() != Items.AIR) {
			if(Library.areItemStacksCompatible(stack1, stack2))
				return true;

			int[] ids1 = OreDictionary.getOreIDs(stack1);
			int[] ids2 = OreDictionary.getOreIDs(stack2);

			if(ids1.length > 0 && ids2.length > 0) {
				for(int i = 0; i < ids1.length; i++)
					for(int j = 0; j < ids2.length; j++)
						if(ids1[i] == ids2[j])
							return true;
			}
		}

		return false;
	}


	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	protected List<TypedFluidTank> inTanks() {
		List<TypedFluidTank> inTanks = new ArrayList<>();

		for(int i = 0; i < tanks.length; ++i) {
			if(i % 4 < 2) {
				inTanks.add(tanks[i]);
			}
		}

		return inTanks;
	}

	public List<TypedFluidTank> outTanks() {
		List<TypedFluidTank> outTanks = new ArrayList<>();

		for(int i = 0; i < tanks.length; ++i) {
			if(i % 4 > 1) {
				outTanks.add(tanks[i]);
			}
		}

		return outTanks;
	}

	@Nullable
	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if(resource.amount <= 0) {
			return null;
		}
		List<TypedFluidTank> send = new ArrayList<>();
		for(TypedFluidTank tank : outTanks()) {
			if(tank.type == resource.getFluid()) {
				send.add(tank);
			}
		}

		if(send.isEmpty()) {
			return null;
		}

		int offer = 0;
		List<Integer> weight = new ArrayList<>();
		for(TypedFluidTank tank : send) {
			int drainWeight = tank.tank.getFluidAmount();
			if(drainWeight < 0) {
				drainWeight = 0;
			}

			offer += drainWeight;
			weight.add(drainWeight);
		}

		if(offer <= 0) {
			return null;
		}

		if(!doDrain) {
			return new FluidStack(resource.getFluid(), offer);
		}

		int needed = resource.amount;
		for(int i = 0; i < send.size(); ++i) {
			TypedFluidTank tank = send.get(i);
			int fillWeight = weight.get(i);
			int part = (int)(resource.amount * ((float)fillWeight / (float)offer));

			FluidStack drained = tank.tank.drain(part, true);
			if(drained != null) {
				needed -= drained.amount;
			}
		}

		for(int i = 0; i < 100 && needed > 0 && i < send.size(); i++) {
			TypedFluidTank tank = send.get(i);
			if(tank.tank.getFluidAmount() > 0) {
				int total = Math.min(tank.tank.getFluidAmount(), needed);
				tank.tank.drain(total, true);
				needed -= total;
			}
		}

		int drained = resource.amount - needed;
		if(drained > 0) {
			return new FluidStack(resource.getFluid(), drained);
		}

		return null;
	}

	@Nullable
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		for(TypedFluidTank tank : outTanks()) {
			if(tank.type != null && tank.tank.getFluidAmount() > 0) {
				return tank.tank.drain(maxDrain, doDrain);
			}
		}

		return null;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		int total = resource.amount;
		
		if(total <= 0) {
			return 0;
		}

		Fluid inType = resource.getFluid();
		List<TypedFluidTank> rec = new ArrayList<>();
		for(TypedFluidTank tank : inTanks()) {
			if(tank.type == inType) {
				rec.add(tank);
			}
		}

		if(rec.isEmpty()) {
			return 0;
		}

		int demand = 0;
		List<Integer> weight = new ArrayList<>();
		for(TypedFluidTank tank : rec) {
			int fillWeight = tank.tank.getCapacity() - tank.tank.getFluidAmount();
			if(fillWeight < 0) {
				fillWeight = 0;
			}

			demand += fillWeight;
			weight.add(fillWeight);
		}

		if(demand <= 0) {
			return 0;
		}

		if(!doFill) {
			return demand;
		}

		int fluidUsed = 0;

		for(int i = 0; i < rec.size(); ++i) {
			TypedFluidTank tank = rec.get(i);
			int fillWeight = weight.get(i);
			int part = (int) (Math.min(total, demand) * (float) fillWeight / (float) demand);
			fluidUsed += tank.tank.fill(new FluidStack(resource.getFluid(), part), true);
		}

		return fluidUsed;
	}

	protected NBTTagList serializeTanks() {
		NBTTagList tankList = new NBTTagList();
		for(int i = 0; i < tanks.length; ++i) {
			NBTTagCompound tank = new NBTTagCompound();
			tanks[i].writeToNBT(tank);
			tank.setByte("index", (byte) i);

			tankList.appendTag(tank);
		}

		return tankList;
	}

	protected void deserializeTanks(NBTTagList tankList) {
		for(int i = 0; i < tankList.tagCount(); ++i) {
			NBTTagCompound tank = tankList.getCompoundTagAt(i);
			int index = tank.getByte("index");

			tanks[index].readFromNBT(tank);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setLong("power", power);
		nbt.setIntArray("progress", progress);

		nbt.setTag("tanks", serializeTanks());

		return super.writeToNBT(nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("power");
		this.progress = nbt.getIntArray("progress");

		if(progress.length == 0) {
			progress = new int[getRecipeCount()];
		}

		NBTTagList tankList = nbt.getTagList("tanks", 10);
		deserializeTanks(tankList);
	}

	public abstract int getRecipeCount();

	public abstract int getTankCapacity();

	public abstract int getTemplateIndex(int index);

	/**
	 * @param index
	 * @return A size 4 int array containing min input, max input, min output and max output indices in that order.
	 */
	public abstract int[] getSlotIndicesFromIndex(int index);

	public abstract BlockPos[] getInputPositions();

	public abstract BlockPos[] getOutputPositions();
}
