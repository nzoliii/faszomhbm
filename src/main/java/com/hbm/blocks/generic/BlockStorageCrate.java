package com.hbm.blocks.generic;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemLock;
import com.hbm.lib.InventoryHelper;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.config.MachineConfig;
import com.hbm.tileentity.machine.TileEntityLockableBase;
import com.hbm.tileentity.machine.TileEntityCrateIron;
import com.hbm.tileentity.machine.TileEntityCrateSteel;
import com.hbm.tileentity.machine.TileEntityCrateTungsten;
import com.hbm.tileentity.machine.TileEntityCrateDesh;
import com.hbm.tileentity.machine.TileEntitySafe;

import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStorageCrate extends BlockContainer {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	private static boolean dropInv = true;

	public BlockStorageCrate(Material materialIn, String s){
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setSoundType(SoundType.METAL);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta){
		if(this == ModBlocks.crate_iron)
			return new TileEntityCrateIron();
		if(this == ModBlocks.crate_steel)
			return new TileEntityCrateSteel();
		if(this == ModBlocks.crate_tungsten)
			return new TileEntityCrateTungsten();
		if(this == ModBlocks.crate_desh)
			return new TileEntityCrateDesh();
		if(this == ModBlocks.safe)
			return new TileEntitySafe();
		return null;
	}

	public int getSlots(){
		if(this == ModBlocks.crate_iron)
			return 36;
		if(this == ModBlocks.crate_steel)
			return 54;
		if(this == ModBlocks.crate_tungsten)
			return 27;
		if(this == ModBlocks.crate_desh)
			return 104;
		if(this == ModBlocks.safe)
			return 15;
		return 0;
	}


	@Override
	public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player){
		return true;
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest){

		if(!player.capabilities.isCreativeMode && !world.isRemote && willHarvest) {
			
			ItemStack drop = new ItemStack(this);
			TileEntity te = world.getTileEntity(pos);
			
			NBTTagCompound nbt = new NBTTagCompound();
			
			if(te != null) {
				IItemHandler inventory;
				if(te instanceof TileEntitySafe){

					inventory = ((TileEntitySafe)te).getPackingCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
				}
				else{
					inventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
				}

				for(int i = 0; i < inventory.getSlots(); i++) {
					
					ItemStack stack = inventory.getStackInSlot(i);
					if(stack.isEmpty())
						continue;
					
					NBTTagCompound slot = new NBTTagCompound();
					stack.writeToNBT(slot);
					nbt.setTag("slot" + i, slot);
				}
			}
			
			if(te instanceof TileEntityLockableBase) {
				TileEntityLockableBase lockable = (TileEntityLockableBase) te;
				
				if(lockable.isLocked()) {
					nbt.setInteger("lock", lockable.getPins());
					nbt.setDouble("lockMod", lockable.getMod());
				}
			}
			
			
			if(!nbt.hasNoTags()) {
				drop.setTagCompound(nbt);
								
				if(nbt.toString().length() > MachineConfig.crateByteSize * 1000) {
					player.sendMessage(new TextComponentString("§cWarning: Container NBT exceeds "+MachineConfig.crateByteSize+"kB, contents will be ejected!"));
					InventoryHelper.dropInventoryItems(world, pos, world.getTileEntity(pos));
					InventoryHelper.spawnItemStack(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(Item.getItemFromBlock(this)));
					return world.setBlockToAir(pos);
				}
			}
			
			InventoryHelper.spawnItemStack(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop);
		}

		this.dropInv = false;
		boolean flag = world.setBlockToAir(pos);
		this.dropInv = true;
		
		return flag;
	}

	@Override
	public Block setSoundType(SoundType sound){
		return super.setSoundType(sound);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state){
		if(this.dropInv){
			InventoryHelper.dropInventoryItems(worldIn, pos, worldIn.getTileEntity(pos));
		}
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if(world.isRemote) {
			return true;
		} else if(player.getHeldItemMainhand() != null && (player.getHeldItemMainhand().getItem() instanceof ItemLock || player.getHeldItemMainhand().getItem() == ModItems.key_kit)) {
			return false;

		} else if(!player.isSneaking()) {
			TileEntity entity = world.getTileEntity(pos);
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			if(entity instanceof TileEntityCrateIron && ((TileEntityCrateIron)entity).canAccess(player)) {
				player.openGui(MainRegistry.instance, ModBlocks.guiID_crate_iron, world, x, y, z);
			}
			if(entity instanceof TileEntityCrateSteel && ((TileEntityCrateSteel)entity).canAccess(player)) {
				player.openGui(MainRegistry.instance, ModBlocks.guiID_crate_steel, world, x, y, z);
			}
			if(entity instanceof TileEntityCrateTungsten && ((TileEntityCrateTungsten)entity).canAccess(player)) {
				player.openGui(MainRegistry.instance, ModBlocks.guiID_crate_tungsten, world, x, y, z);
			}
			if(entity instanceof TileEntityCrateDesh && ((TileEntityCrateDesh)entity).canAccess(player)) {
				player.openGui(MainRegistry.instance, ModBlocks.guiID_crate_desh, world, x, y, z);
			}
			if(entity instanceof TileEntitySafe && ((TileEntitySafe)entity).canAccess(player)) {
				player.openGui(MainRegistry.instance, ModBlocks.guiID_safe, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){

		TileEntity te = world.getTileEntity(pos);

		if(te != null && stack.hasTagCompound()) {
			IItemHandler inventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

			NBTTagCompound nbt = stack.getTagCompound();
			for(int i = 0; i < inventory.getSlots(); i++) {
				inventory.insertItem(i, new ItemStack(nbt.getCompoundTag("slot" + i)), false);
			}
			
			if(te instanceof TileEntityLockableBase) {
				TileEntityLockableBase lockable = (TileEntityLockableBase) te;
				
				if(nbt.hasKey("lock")) {
					lockable.setPins(nbt.getInteger("lock"));
					lockable.setMod(nbt.getDouble("lockMod"));
					lockable.lock();
				}
			}
		}

		if(this != ModBlocks.safe)
			super.onBlockPlacedBy(world, pos, state, placer, stack);
		else
			world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand){
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return null;
	}

	@Override
	protected BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	@Override
	public int getMetaFromState(IBlockState state){
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}

	@Override
	public IBlockState getStateFromMeta(int meta){
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if(enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot){
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn){
		return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state){
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, list, flagIn);
		int totalSlots = getSlots();
		if(stack.hasTagCompound()){
			NBTTagCompound nbt = stack.getTagCompound();
			int slotCount = 0;
			for(int i=0; i<totalSlots; i++){
				if(nbt.hasKey("slot"+i)){
					slotCount++;
				}
			}
			float percent = Library.roundFloat(slotCount * 100F/totalSlots, 1);
			String color = "§e";
			String color2 = "§6"; 
			if(percent >= 75){
				color = "§c";
				color2 = "§4";
			}else if(percent < 25){
				color = "§a";
				color2 = "§2";
			}
			list.add(color+slotCount+color2+"/"+totalSlots+" Slots used "+color+"("+percent+"%)§r");

		}else{
			list.add("§a0§2/" + totalSlots + " Slots used §a(0.0%)§r");
		}
	}
}
