package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.HeatRecipes;
import com.hbm.lib.ForgeDirection;
import com.hbm.lib.Library;
import com.hbm.packet.FluidTankPacket;
import com.hbm.tileentity.INBTPacketReceiver;

import api.hbm.tile.IHeatSource;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityHeatBoiler extends TileEntity implements INBTPacketReceiver, ITickable, IFluidHandler {

    public FluidTank[] tanks;
    public Fluid[] types = new Fluid[2];
    public int heat;
    public static int maxHeat = 12_800_000; //the heat required to turn 64k of water into steam
    public static final double diffusion = 0.1D;

    public TileEntityHeatBoiler() {
        super();
        tanks = new FluidTank[2];

        tanks[0] = new FluidTank(FluidRegistry.WATER, 0, 640000);
        types[0] = FluidRegistry.WATER;

        tanks[1] = new FluidTank(ModForgeFluids.steam, 0, 64000000);
        types[1] = ModForgeFluids.steam;

    }

    public void setTankType(int idx, Fluid type){
        if(types[idx] != type){
            types[idx] = type;
            if(type != null){
                tanks[idx].setFluid(new FluidStack(type, 0));
            }else {
                tanks[idx].setFluid(null);
            }
        }
    }

    @Override
    public void update() {

        if(!world.isRemote) {
            setupTanks();
            tryPullHeat();
            tryConvert();
            
            fillFluidInit(tanks[1]);

            networkPack();
        }
    }

    public void fillFluidInit(FluidTank tank) {
        ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getRotation(ForgeDirection.UP);
        fillFluid(tank, pos.add(dir.offsetX * 2, 0, dir.offsetZ * 2));
        fillFluid(tank, pos.add(dir.offsetX * -2, 0, dir.offsetZ * -2));
        fillFluid(tank, pos.add(0, 4, 0));
    }

    public void fillFluid(FluidTank tank, BlockPos pos){
        FFUtils.fillFluid(this, tank, world, pos, 64000000);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing){
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing){
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[] {tanks[0].getTankProperties()[0], tanks[1].getTankProperties()[0]};
    }

    @Override
    public int fill(FluidStack resource, boolean doFill){
        if(resource != null && resource.getFluid() == types[0]){
            return tanks[0].fill(resource, doFill);
        }
        return 0;
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain){
        FluidStack drain = null;
        if(resource.getFluid() == types[1]){
            drain = tanks[1].drain(resource, doDrain);
        }
        return drain;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain){
        FluidStack drain = tanks[1].drain(maxDrain, doDrain);
        return drain;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        FFUtils.deserializeTankArray(nbt.getTagList("tanks", 10), tanks);
        for(int i=0; i<tanks.length; i++){
            if(tanks[i].getFluid() != null){
                types[i] = tanks[i].getFluid().getFluid();
            } else {
                types[i] = null;
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        for(int i=0; i<tanks.length; i++){
            if(types[i] != null){
                tanks[i].setFluid(new FluidStack(types[i], tanks[i].getFluidAmount()));
            } else {
                tanks[i].setFluid(null);
            }
        }
        nbt.setTag("tanks", FFUtils.serializeTankArray(tanks));
        return nbt;
    }


    public void networkPack(){
        NBTTagCompound data = new NBTTagCompound();
        for(int i=0; i<tanks.length; i++){
            if(types[i] != null){
                tanks[i].setFluid(new FluidStack(types[i], tanks[i].getFluidAmount()));
            } else {
                tanks[i].setFluid(null);
            }
        }
        data.setTag("tanks", FFUtils.serializeTankArray(tanks));
        data.setInteger("heat", heat);
        INBTPacketReceiver.networkPack(this, data, 25);
    }

    @Override
    public void networkUnpack(NBTTagCompound nbt) {
        FFUtils.deserializeTankArray(nbt.getTagList("tanks", 10), tanks);
        for(int i=0; i<tanks.length; i++){
            if(tanks[i].getFluid() != null){
                types[i] = tanks[i].getFluid().getFluid();
            } else {
                types[i] = null;
            }
        }
        this.heat = nbt.getInteger("heat");
    }

    private void setupTanks() {
        Fluid fluid = HeatRecipes.getBoilFluid(types[0]);
        if (fluid != null) {
            setTankType(0, types[0]);
            setTankType(1, fluid);
        } else {
            setTankType(0, null);
            setTankType(1, null);
        }
    }

    private void tryConvert() {
        if(HeatRecipes.hasBoilRecipe(types[0])) {
            Fluid hotFluid = HeatRecipes.getBoilFluid(types[0]);
            int heatReq = HeatRecipes.getRequiredHeat(types[0]);
            int inputAmount = HeatRecipes.getInputAmountHot(types[0]);
            int outputAmount = HeatRecipes.getOutputAmountHot(types[0]);
            
            int inputOps = tanks[0].getFluidAmount() / inputAmount;
            int outputOps = (tanks[1].getCapacity() - tanks[1].getFluidAmount()) / outputAmount;
            int tempOps = (int) Math.floor(this.heat / heatReq);
            int ops = Math.min(inputOps, Math.min(outputOps, tempOps));
            
            tanks[0].drain(inputAmount * ops, true);
            tanks[1].fill(new FluidStack(types[1], outputAmount * ops), true);
            this.heat -= heatReq * ops;
        }
    }
    
    protected void tryPullHeat() {

        if(this.heat >= TileEntityHeatBoiler.maxHeat) return;
        BlockPos blockBelow = pos.down();
        TileEntity con = world.getTileEntity(blockBelow);

        if(con instanceof IHeatSource) {
            IHeatSource source = (IHeatSource) con;
            int diff = source.getHeatStored() - this.heat;

            if(diff == 0) {
                return;
            }

            if(diff > 0) {
                diff = (int) Math.ceil(diff * diffusion);
                source.useUpHeat(diff);
                this.heat += diff;
                if(this.heat > this.maxHeat)
                    this.heat = this.maxHeat;
                return;
            }
        }

        this.heat = Math.max(this.heat - Math.max(this.heat / 1000, 1), 0);
    }

    AxisAlignedBB bb = null;

    @Override
    public AxisAlignedBB getRenderBoundingBox() {

        if(bb == null) {
            bb = new AxisAlignedBB(
                    pos.getX() - 1,
                    pos.getY(),
                    pos.getZ() - 1,
                    pos.getX() + 2,
                    pos.getY() + 4,
                    pos.getZ() + 2
            );
        }

        return bb;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }

}