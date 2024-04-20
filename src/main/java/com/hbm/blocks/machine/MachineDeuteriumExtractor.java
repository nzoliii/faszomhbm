package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.ILookOverlay;
import com.hbm.lib.Library;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.tileentity.machine.TileEntityDeuteriumExtractor;
import com.hbm.util.I18nUtil;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class MachineDeuteriumExtractor extends BlockContainer implements ILookOverlay {

	public MachineDeuteriumExtractor(Material mat, String s) {
        super(mat);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
    }

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		meta = 0;
		return new TileEntityDeuteriumExtractor();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		
		if(!(te instanceof TileEntityDeuteriumExtractor))
			return;
		
		TileEntityDeuteriumExtractor extractor = (TileEntityDeuteriumExtractor) te;
		
		List<String> text = new ArrayList();
		text.add(Library.getShortNumber(extractor.power) + "/" + Library.getShortNumber(extractor.getMaxPower()) + " HE");
		
		if(extractor.tanks[0] != null)
			text.add("§a-> §r" + FluidRegistry.WATER.getLocalizedName(new FluidStack(FluidRegistry.WATER, 1)) + ": " + extractor.tanks[0].getFluidAmount() + "/" + extractor.tanks[0].getCapacity() + "mB");
		if(extractor.tanks[1] != null)
			text.add("§c<- §r" + ModForgeFluids.heavywater.getLocalizedName(new FluidStack(ModForgeFluids.heavywater, 1)) + ": " + extractor.tanks[1].getFluidAmount() + "/" + extractor.tanks[1].getCapacity() + "mB");
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getUnlocalizedName() + ".name"), 0xffff00, 0x404000, text);
	}
}