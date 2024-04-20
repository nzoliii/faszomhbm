package com.hbm.blocks.machine;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.inventory.FluidCombustionRecipes;
import com.hbm.items.tool.ItemTooling;
import com.hbm.lib.ForgeDirection;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityHeaterRadioThermal;
import com.hbm.util.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HeaterRadioThermal extends BlockDummyable implements ITooltipProvider, ILookOverlay {
    public HeaterRadioThermal(Material mat, String s) {
        super(mat, s);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        if (meta >= 12)
            return new TileEntityHeaterRadioThermal();

        if (hasExtra(meta) && meta - extra > 1)
            return new TileEntityProxyCombo(true, false, false);

        if (hasExtra(meta))
            return new TileEntityProxyCombo(false, false, false, true);

        return null;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return this.standardOpenBehavior(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn, 0);
    }

    @Override
    public int[] getDimensions() {
        return new int[]{1, 0, 1, 1, 1, 1};
    }

    @Override
    public int getOffset() {
        return 1;
    }

    @Override
    protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
        super.fillSpace(world, x, y, z, dir, o);

        x = x + dir.offsetX * o;
        z = z + dir.offsetZ * o;

        this.makeExtra(world, x + 1, y, z);
        this.makeExtra(world, x - 1, y, z);
        this.makeExtra(world, x, y, z + 1);
        this.makeExtra(world, x, y, z - 1);
        this.makeExtra(world, x, y + 1, z);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        this.addStandardInfo(tooltip);
    }

    @Override
    public void printHook(Pre event, World world, int x, int y, int z) {
        int[] pos = this.findCore(world, x, y, z);

        if (pos == null)
            return;

        TileEntity te = world.getTileEntity(new BlockPos(pos[0], pos[1], pos[2]));

        if (!(te instanceof TileEntityHeaterRadioThermal))
            return;

        TileEntityHeaterRadioThermal heater = (TileEntityHeaterRadioThermal) te;

        List<String> text = new ArrayList();
        text.add(String.format("%,d", heater.heatEnergy) + " TU");
        text.add("§c<- §r"+String.format("%,d", heater.heatGen) + " TU/t");

        ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
    }
}
