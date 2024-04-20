package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.lib.Library;
import com.hbm.lib.ForgeDirection;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityDeuteriumTower;
import com.hbm.util.I18nUtil;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class DeuteriumTower extends BlockDummyable implements ILookOverlay {

	public DeuteriumTower(Material mat, String s) {
		super(Material.IRON, s);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int meta) {

		if(meta >= 12)
			return new TileEntityDeuteriumTower();

		if(meta >= 8)
			return new TileEntityProxyCombo(false, true, true);

		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] { 9, 0, 1, 0, 0, 1 };
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x = x + dir.offsetX * o;
		z = z + dir.offsetZ * o;

		ForgeDirection dr2 = dir.getRotation(ForgeDirection.UP);

		this.makeExtra(world, x - dir.offsetX - dr2.offsetX, y, z - dir.offsetZ - dr2.offsetZ);
		this.makeExtra(world, x, y, z - dir.offsetZ - dr2.offsetZ);
		this.makeExtra(world, x - dir.offsetX - dr2.offsetX, y, z);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		int[] pos = this.findCore(world, x, y, z);

        if (pos == null)
            return;

        TileEntity te = world.getTileEntity(new BlockPos(pos[0], pos[1], pos[2]));
		
		if(!(te instanceof TileEntityDeuteriumTower))
			return;
		
		TileEntityDeuteriumTower extractor = (TileEntityDeuteriumTower) te;
		
		List<String> text = new ArrayList();
		text.add(Library.getShortNumber(extractor.power) + "/" + Library.getShortNumber(extractor.getMaxPower()) + " HE");
		
		if(extractor.tanks[0] != null)
			text.add("§a-> §r" + FluidRegistry.WATER.getLocalizedName(new FluidStack(FluidRegistry.WATER, 1)) + ": " + extractor.tanks[0].getFluidAmount() + "/" + extractor.tanks[0].getCapacity() + "mB");
		if(extractor.tanks[1] != null)
			text.add("§c<- §r" + ModForgeFluids.heavywater.getLocalizedName(new FluidStack(ModForgeFluids.heavywater, 1)) + ": " + extractor.tanks[1].getFluidAmount() + "/" + extractor.tanks[1].getCapacity() + "mB");
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}