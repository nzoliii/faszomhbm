package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandler;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.lib.InventoryHelper;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.oil.TileEntityMachineFrackingTower;
import com.hbm.lib.ForgeDirection;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class MachineFrackingTower extends BlockDummyable {

    public MachineFrackingTower(Material materialIn, String s) {
        super(materialIn, s);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {

        if(meta >= 12) return new TileEntityMachineFrackingTower();
        if(meta >= 6) return new TileEntityProxyCombo(false, true, true);

        return null;

    }


    @Override
    public int[] getDimensions() {
        return new int[] {3, 0, 0, 0, 0, 0};
    }

    @Override
    public int getOffset() {
        return 0;
    }


    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(ModBlocks.machine_fracking_tower);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(ModBlocks.machine_fracking_tower);
    }

//    @Override
//    public EnumBlockRenderType getRenderType(IBlockState state) {
//        return EnumBlockRenderType.MODEL;
//    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos1, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(world.isRemote)
        {
            return true;
        } else if(!player.isSneaking())
        {
            int[] pos = this.findCore(world, pos1.getX(), pos1.getY(), pos1.getZ());

            if (pos == null)
                return false;

            TileEntityMachineFrackingTower frackingTower = (TileEntityMachineFrackingTower)world.getTileEntity(new BlockPos(pos[0], pos[1], pos[2]));

            if (frackingTower != null)
                player.openGui(MainRegistry.instance, ModBlocks.guiID_machine_fracking_tower, world, pos[0], pos[1], pos[2]);

            return true;
        } else {
            return false;
        }
    }

//    @Override
//    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
//        InventoryHelper.dropInventoryItems(worldIn, pos, worldIn.getTileEntity(pos));
//        super.breakBlock(worldIn, pos, state);
//    }


    @Override
    protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {

        if(!MultiblockHandlerXR.checkSpace(world, x, y + 2, z, new int[] {1, 0, 3, 3, 3, 3}, x, y, z, dir)) return false;

        if(!MultiblockHandlerXR.checkSpace(world, x - 2, y + 2, z - 2, new int[] {-1, 2, 0, 1, 0, 1}, x, y, z, ForgeDirection.NORTH)) return false;
        if(!MultiblockHandlerXR.checkSpace(world, x - 2, y + 2, z + 3, new int[] {-1, 2, 0, 1, 0, 1}, x, y, z, ForgeDirection.NORTH)) return false;
        if(!MultiblockHandlerXR.checkSpace(world, x + 3, y + 2, z - 2, new int[] {-1, 2, 0, 1, 0, 1}, x, y, z, ForgeDirection.NORTH)) return false;
        if(!MultiblockHandlerXR.checkSpace(world, x + 3, y + 2, z + 3, new int[] {-1, 2, 0, 1, 0, 1}, x, y, z, ForgeDirection.NORTH)) return false;

        if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {10, -4, 2, 2, 2, 2}, x, y, z, dir)) return false;
        if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {24, -9, 1, 1, 1, 1}, x, y, z, dir)) return false;

        if(!MultiblockHandlerXR.checkSpace(world, x, y + 15, z, new int[] {1, 0, 1, 1, -2, 3}, x, y, z, dir)) return false;

        return super.checkRequirement(world, x, y, z, dir, o);
    }

    @Override
    public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
        MultiblockHandlerXR.fillSpace(world, x, y, z, getDimensions(), this, dir);
        MultiblockHandlerXR.fillSpace(world, x, y + 2, z, new int[] {1, 0, 3, 3, 3, 3}, this, dir);

        MultiblockHandlerXR.fillSpace(world, x - 2, y + 2, z - 2, new int[] {-1, 2, 0, 1, 0, 1}, this, ForgeDirection.NORTH);
        MultiblockHandlerXR.fillSpace(world, x - 2, y + 2, z + 3, new int[] {-1, 2, 0, 1, 0, 1}, this, ForgeDirection.NORTH);
        MultiblockHandlerXR.fillSpace(world, x + 3, y + 2, z - 2, new int[] {-1, 2, 0, 1, 0, 1}, this, ForgeDirection.NORTH);
        MultiblockHandlerXR.fillSpace(world, x + 3, y + 2, z + 3, new int[] {-1, 2, 0, 1, 0, 1}, this, ForgeDirection.NORTH);

        MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {10, -4, 2, 2, 2, 2}, this, dir);
        MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {24, -9, 1, 1, 1, 1}, this, dir);

        MultiblockHandlerXR.fillSpace(world, x, y + 15, z, new int[] {1, 0, 1, 1, -2, 3}, this, ForgeDirection.WEST);
    }
}