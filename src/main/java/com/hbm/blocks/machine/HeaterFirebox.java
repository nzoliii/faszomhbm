package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityHeaterFirebox;
import com.hbm.util.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class HeaterFirebox extends BlockDummyable implements ITooltipProvider, ILookOverlay {

	public HeaterFirebox(Material mat, String s) {
		super(Material.IRON, s);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12)
			return new TileEntityHeaterFirebox();
		
		return new TileEntityProxyCombo(true, false, false);
	}
	
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return standardOpenBehavior(world, pos.getX(), pos.getY(), pos.getZ(), player, 0);
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 1, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		this.addStandardInfo(list);
		super.addInformation(stack, worldIn, list, flagIn);
	}

	@Override
    public void printHook(Pre event, World world, int x, int y, int z) {
        int[] pos = this.findCore(world, x, y, z);

        if (pos == null)
            return;

        TileEntity te = world.getTileEntity(new BlockPos(pos[0], pos[1], pos[2]));

        if (!(te instanceof TileEntityHeaterFirebox))
            return;

        TileEntityHeaterFirebox heater = (TileEntityHeaterFirebox) te;

        List<String> text = new ArrayList<>();
        text.add(String.format("%,d", heater.heatEnergy) + " TU");
        text.add("§c<- §r"+String.format("%,d", heater.burnHeat) + " TU/t");
        ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
    }
}
