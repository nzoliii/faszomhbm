package com.hbm.tileentity.network;

import api.hbm.block.IConveyorBelt;
import com.hbm.lib.Library;
import com.hbm.entity.item.EntityMovingItem;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerCraneExtractor;
import com.hbm.inventory.gui.GUICraneExtractor;
import com.hbm.items.ModItems;
import com.hbm.modules.ModulePatternMatcher;
import com.hbm.tileentity.IGUIProvider;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;

public class TileEntityCraneExtractor extends TileEntityCraneBase implements IGUIProvider, IControlReceiver {
    public boolean isWhitelist = false;

    private int tickCounter = 0;
    public ModulePatternMatcher matcher;

    public static int[] allowed_slots = {9, 10, 11, 12, 13, 14, 15, 16, 17};

    public TileEntityCraneExtractor() {
        super(20);
        this.matcher = new ModulePatternMatcher(9);
    }

    @Override
    public String getName() {
        return "container.craneExtractor";
    }

    @Override
    public void update() {
        super.update();
        if(!world.isRemote) {
            tickCounter++;

            int xCoord = pos.getX();
            int yCoord = pos.getY();
            int zCoord = pos.getZ();
            int delay = 20;
            if(inventory.getStackInSlot(19) != null && inventory.getStackInSlot(19) != ItemStack.EMPTY){
                if(inventory.getStackInSlot(19).getItem() == ModItems.upgrade_ejector_1) {
                    delay = 10;
                } else if(inventory.getStackInSlot(19).getItem() == ModItems.upgrade_ejector_2){
                    delay = 5;
                } else if(inventory.getStackInSlot(19).getItem() == ModItems.upgrade_ejector_3){
                    delay = 2;
                }
            }

            if(tickCounter >= delay && !this.world.isBlockPowered(pos)) {
                tickCounter = 0;
                int amount = 1;

                if(inventory.getStackInSlot(18)!=null && inventory.getStackInSlot(18) != ItemStack.EMPTY){
                    if(inventory.getStackInSlot(18).getItem() == ModItems.upgrade_stack_1) {
                        amount = 4;
                    } else if(inventory.getStackInSlot(18).getItem() == ModItems.upgrade_stack_2){
                        amount = 16;
                    } else if(inventory.getStackInSlot(18).getItem() == ModItems.upgrade_stack_3){
                        amount = 64;
                    }
                }

                EnumFacing inputSide = getInputSide(); // note the switcheroo!
                EnumFacing outputSide = getOutputSide();
                TileEntity te = world.getTileEntity(pos.offset(inputSide));
                Block b = world.getBlockState(pos.offset(outputSide)).getBlock();

                int[] access = null;
                ISidedInventory sided = null;

                if(te instanceof ISidedInventory && !(te instanceof TileEntityCraneExtractor)) {
                    sided = (ISidedInventory) te;
                    access = masquerade(sided, EnumFacing.getFront(inputSide.getOpposite().ordinal()));
                }

                //collect matching items
                if(te != null) {

                    /* try to send items from a connected inv, if present */
                    ICapabilityProvider capte = te;
                    if(capte.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inputSide)) {
                        IItemHandler inv = capte.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inputSide);

                        int size = access == null ? inv.getSlots() : access.length;

                        for(int i = 0; i < size; i++) {
                            int index = access == null ? i : access[i];
                            ItemStack stack = inv.getStackInSlot(index);

                            if(stack != ItemStack.EMPTY && (sided == null || sided.canExtractItem(index, stack, EnumFacing.getFront(inputSide.getOpposite().ordinal())))){

                                boolean match = this.matchesFilter(stack);

                                if((isWhitelist && match) || (!isWhitelist && !match)) {
                                    int toSend = stack.getCount();

                                    ItemStack excrated = inv.extractItem(i, toSend, true);
                                    if(excrated != null && !excrated.isEmpty()){
                                        int fill = tryInsertItemCap(inventory, excrated.copy(), allowed_slots);
                                        if(fill > 0 && fill <= toSend) inv.extractItem(i, fill, false);
                                    }
                                }
                            }
                        }
                    }
                }

                //send buffered items
                if(b instanceof IConveyorBelt) {

                    IConveyorBelt belt = (IConveyorBelt) b;

                    for(int index : allowed_slots) {
                        ItemStack stack = inventory.getStackInSlot(index);

                        if(stack != ItemStack.EMPTY && (sided == null || sided.canExtractItem(index, stack, EnumFacing.getFront(inputSide.getOpposite().ordinal())))){

                            boolean match = this.matchesFilter(stack);

                            if((isWhitelist && match) || (!isWhitelist && !match)) {
                                int toSend = Math.min(amount, stack.getCount());
                                ItemStack cStack = stack.copy();
                                stack.shrink(toSend);
                                if(stack.getCount() == 0)
                                    inventory.setStackInSlot(index, ItemStack.EMPTY);
                                cStack.setCount(toSend);

                                EntityMovingItem moving = new EntityMovingItem(world);
                                Vec3d pos = new Vec3d(xCoord + 0.5 + outputSide.getDirectionVec().getX() * 0.55, yCoord + 0.5 + outputSide.getDirectionVec().getY() * 0.55, zCoord + 0.5 + outputSide.getDirectionVec().getZ() * 0.55);
                                Vec3d snap = belt.getClosestSnappingPosition(world, new BlockPos(xCoord + outputSide.getDirectionVec().getX(), yCoord + outputSide.getDirectionVec().getY(), zCoord + outputSide.getDirectionVec().getZ()), pos);
                                moving.setPosition(snap.x, snap.y, snap.z);
                                moving.setItemStack(cStack);
                                world.spawnEntity(moving);
                                break;
                            }
                        }
                    }
                }
            }

            NBTTagCompound data = new NBTTagCompound();
            data.setBoolean("isWhitelist", isWhitelist);
            this.matcher.writeToNBT(data);
            this.networkPack(data, 15);
        }
    }

    //Unloads output into chests. Capability version.
    public int tryInsertItemCap(IItemHandler chest, ItemStack stack, int[] allowed_slots) {
        //Check if we have something to output
        if(stack.isEmpty())
            return 0;
        int filledAmount = 0;
        for(int i : allowed_slots) {
            
            if(stack.isEmpty() || stack.getCount() < 1)
                return filledAmount;
            ItemStack outputStack = stack.copy();
            
            ItemStack chestItem = chest.getStackInSlot(i).copy();
            if(chestItem.isEmpty() || (Library.areItemStacksCompatible(outputStack, chestItem, false) && chestItem.getCount() < chestItem.getMaxStackSize())) {
                int fillAmount = Math.min(chestItem.getMaxStackSize()-chestItem.getCount(), outputStack.getCount());
                
                outputStack.setCount(fillAmount);

                ItemStack rest = chest.insertItem(i, outputStack, true);
                stack.shrink(fillAmount-rest.getCount());
                filledAmount += fillAmount-rest.getCount();
                chest.insertItem(i, outputStack, false);
            }
        }

        return filledAmount;
    }

    public static int[] masquerade(ISidedInventory sided, EnumFacing side) {

        if(sided instanceof TileEntityFurnace) {
            return new int[] {2};
        }

        return sided.getSlotsForFace(side);
    }

    public void networkUnpack(NBTTagCompound nbt) {
        this.isWhitelist = nbt.getBoolean("isWhitelist");
        this.matcher.modes = new String[this.matcher.modes.length];
        this.matcher.readFromNBT(nbt);
    }

    public boolean matchesFilter(ItemStack stack) {

        for(int i = 0; i < 9; i++) {
            ItemStack filter = inventory.getStackInSlot(i);

            if(filter != null && this.matcher.isValidForFilter(filter, i, stack)) {
                return true;
            }
        }
        return false;
    }

    public void nextMode(int i) {
        this.matcher.nextMode(world, inventory.getStackInSlot(i), i);
    }

    public void initPattern(ItemStack stack, int index) {
        this.matcher.initPatternSmart(world, stack, index);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return i > 8 && i < 18;
    }

    @Override
    public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {

        return new ContainerCraneExtractor(player.inventory, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new GUICraneExtractor(player.inventory, this);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.isWhitelist = nbt.getBoolean("isWhitelist");
        this.matcher.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("isWhitelist", this.isWhitelist);
        this.matcher.writeToNBT(nbt);
        return nbt;
    }

    @Override
    public boolean hasPermission(EntityPlayer player) {
        int xCoord = pos.getX();
        int yCoord = pos.getY();
        int zCoord = pos.getZ();
        return new Vec3d(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 20;
    }

    @Override
    public void receiveControl(NBTTagCompound data) {
        if(data.hasKey("isWhitelist")) {
            this.isWhitelist = !this.isWhitelist;
        }
    }

    @Override
    public int[] getAccessibleSlotsFromSide(EnumFacing e) {
        return allowed_slots;
    }
    
    @Override
    public boolean canInsertItem(int slot, ItemStack itemStack, int amount) {
        return this.isItemValidForSlot(slot, itemStack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemStack, int amount) {
        return false;
    }
}
