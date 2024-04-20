package com.hbm.tileentity.machine;

import java.util.HashSet;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.gas.BlockGasBase;
import com.hbm.blocks.generic.BlockBedrockOreTE.TileEntityBedrockOre;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerMachineExcavator;
import com.hbm.inventory.gui.GUIMachineExcavator;
import com.hbm.inventory.ShredderRecipes;
import com.hbm.inventory.BedrockOreRegistry;
import com.hbm.items.machine.ItemForgeFluidIdentifier;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemDrillbit;
import com.hbm.items.machine.ItemDrillbit.EnumDrillType;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.lib.DirPos;
import com.hbm.lib.ForgeDirection;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.InventoryUtil;
import com.hbm.util.ItemStackUtil;

import api.hbm.energy.IEnergyUser;
import api.hbm.block.IDrillInteraction;
import api.hbm.block.IMiningDrill;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class TileEntityMachineExcavator extends TileEntityMachineBase implements IEnergyUser, IFluidHandler, ITickable, ITankPacketAcceptor, IControlReceiver, IGUIProvider, IMiningDrill {

	public static final long maxPower = 10_000_000;
	public long power;
	public boolean operational = false;
	
	public boolean enableDrill = false;
	public boolean enableCrusher = false;
	public boolean enableWalling = false;
	public boolean enableVeinMiner = false;
	public boolean enableSilkTouch = false;
	public boolean hasNullifier = false;
	
	protected int ticksWorked = 0;
	protected int targetDepth = 0; //0 is the first block below null position
	protected boolean bedrockDrilling = false;

	public float drillRotation = 0F;
	public float prevDrillRotation = 0F;
	public float drillExtension = 0F;
	public float prevDrillExtension = 0F;
	public float crusherRotation = 0F;
	public float prevCrusherRotation = 0F;
	public int chuteTimer = 0;
	
	public double speed = 1.0D;
	public final long baseConsumption = 10_000L;
	public long consumption = baseConsumption;
	protected int drillRating = 0;
	
	public FluidTank tank;
	public Fluid fluidType;

	private final UpgradeManager upgradeManager = new UpgradeManager();

	public TileEntityMachineExcavator() {
		super(14);
		this.fluidType = null;
        this.tank = new FluidTank(16_000);
	}

	@Override
	public String getName() {
		return "container.machineExcavator";
	}

	public boolean hasEnoughPower(){
		if(bedrockDrilling)
			return this.power >= this.getPowerConsumption() * 20;
		return this.power >= this.getPowerConsumption();
	}

	@Override
	public void update() {
		
		//needs to happen on client too for GUI rendering
		upgradeManager.eval(inventory, 2, 3);
		int speedLevel = Math.min(upgradeManager.getLevel(UpgradeType.SPEED), 10);
		int powerLevel = Math.min(upgradeManager.getLevel(UpgradeType.POWER), 3);
		hasNullifier =  upgradeManager.getLevel(UpgradeType.NULLIFIER) > 0;

		consumption = baseConsumption * (1 + speedLevel);
		consumption /= (1 + powerLevel);
		
		if(!world.isRemote) {
			
			updateTankType();
			
			if(world.getTotalWorldTime() % 20 == 0) {
				tryEjectBuffer();
				
				for(DirPos posDir : getConPos()) {
					this.trySubscribe(world, posDir.getPos(), posDir.getDir());
				}
			}
			
			if(chuteTimer > 0) chuteTimer--;
			
			this.power = Library.chargeTEFromItems(inventory, 0, this.getPower(), this.getMaxPower());
			this.operational = false;
			int radiusLevel = Math.min(upgradeManager.getLevel(UpgradeType.EFFECT), 3);
			
			EnumDrillType type = this.getInstalledDrill();
			if(this.enableDrill && type != null && hasEnoughPower()) {
				
				this.drillRating = (int)(type.speed * 80);
				operational = true;
				if(bedrockDrilling)
					this.power -= this.getPowerConsumption() * 10;
				else
					this.power -= this.getPowerConsumption();
				
				this.speed = type.speed;
				this.speed *= (1 + speedLevel / 2D);
				
				int maxDepth = this.pos.getY() - 4;

				if((bedrockDrilling || targetDepth <= maxDepth) && tryDrill(1 + radiusLevel * 2)) {
					targetDepth++;
					
					if(targetDepth > maxDepth) {
						this.enableDrill = false;
					}
				}
			} else {
				this.targetDepth = 0;
				this.drillRating = 0;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			tank.writeToNBT(data);
			if(fluidType != null)
				data.setString("f", fluidType.getName());
            data.setBoolean("d", enableDrill);
			data.setBoolean("c", enableCrusher);
			data.setBoolean("w", enableWalling);
			data.setBoolean("v", enableVeinMiner);
			data.setBoolean("s", enableSilkTouch);
			data.setBoolean("o", operational);
			data.setInteger("t", targetDepth);
			data.setInteger("g", chuteTimer);
			data.setLong("p", power);
			this.networkPack(data, 150);
			
		} else {
			
			this.prevDrillExtension = this.drillExtension;
			
			if(this.drillExtension != this.targetDepth) {
				float diff = Math.abs(this.drillExtension - this.targetDepth);
				float speed = Math.max(0.15F, diff / 10F);
				
				if(diff <= speed) {
					this.drillExtension = this.targetDepth;
				} else {
					float sig = Math.signum(this.drillExtension - this.targetDepth);
					this.drillExtension -= sig * speed;
				}
			}

			this.prevDrillRotation = this.drillRotation;
			this.prevCrusherRotation = this.crusherRotation;
			
			if(this.operational) {
				this.drillRotation += 5F;
				
				if(this.enableCrusher) {
					this.crusherRotation += 10F;
				}
			}
			
			if(this.drillRotation >= 360F) {
				this.drillRotation -= 360F;
				this.prevDrillRotation -= 360F;
			}
			
			if(this.crusherRotation >= 360F) {
				this.crusherRotation -= 360F;
				this.prevCrusherRotation -= 360F;
			}
		}
	}

	private void updateTankType() {
        ItemStack slotStack = inventory.getStackInSlot(1);
        if(slotStack.getItem() == ModItems.forge_fluid_identifier) {
            Fluid fluid = ItemForgeFluidIdentifier.getType(slotStack);

            if(fluidType != fluid) {
                fluidType = fluid;
                tank.setFluid(null);

                this.markDirty();
            }
        }
    }
	
	protected DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				new DirPos(pos.getX() + dir.offsetX * 4 + rot.offsetX, pos.getY() + 1, pos.getZ() + dir.offsetZ * 4 + rot.offsetZ, dir),
				new DirPos(pos.getX() + dir.offsetX * 4 - rot.offsetX, pos.getY() + 1, pos.getZ() + dir.offsetZ * 4 - rot.offsetZ, dir),
				new DirPos(pos.getX() + rot.offsetX * 4, pos.getY() + 1, pos.getZ() + rot.offsetZ * 4, rot),
				new DirPos(pos.getX() - rot.offsetX * 4, pos.getY() + 1, pos.getZ() - rot.offsetZ * 4, rot.getOpposite())
		};
	}
	
	public void networkUnpack(NBTTagCompound nbt) {
		this.tank.readFromNBT(nbt);
		if(nbt.hasKey("f")) {
            this.fluidType = FluidRegistry.getFluid(nbt.getString("f"));
        }
		this.enableDrill = nbt.getBoolean("d");
		this.enableCrusher = nbt.getBoolean("c");
		this.enableWalling = nbt.getBoolean("w");
		this.enableVeinMiner = nbt.getBoolean("v");
		this.enableSilkTouch = nbt.getBoolean("s");
		this.operational = nbt.getBoolean("o");
		this.targetDepth = nbt.getInteger("t");
		this.chuteTimer = nbt.getInteger("g");
		this.power = nbt.getLong("p");
	}
	
	protected int getY() {
		return pos.getY() - targetDepth - 4;
	}
	
	/** Works outwards and tries to break a ring, returns true if all rings are broken (or ignorable) and the drill should extend. */
	protected boolean tryDrill(int radius) {
		int y = getY();

		if(targetDepth == 0 || y == 0) {
			radius = 1;
		}
		int installedTier = this.getInstalledDrill().tier;
		for(int ring = 1; ring <= radius; ring++) {
			
			boolean ignoreAll = true;
			float combinedHardness = 0F;
			BlockPos bedrockOre = null;
			bedrockDrilling = false;
			
			//Scanning to chgeck how much time it would take to break all of them at once
			for(int x = pos.getX() - ring; x <= pos.getX() + ring; x++) {
				for(int z = pos.getZ() - ring; z <= pos.getZ() + ring; z++) {
					
					/* Process blocks either if we are in the inner ring (1 = 3x3) or if the target block is on the outer edge */
					if(ring == 1 || (x == pos.getX() - ring || x == pos.getX() + ring || z == pos.getZ() - ring || z == pos.getZ() + ring)) {
						
						BlockPos drillPos = new BlockPos(x, y, z);
						IBlockState bState = world.getBlockState(drillPos);
						Block b = bState.getBlock();
						
						if(b == ModBlocks.ore_bedrock_block) {
							double tierDiff = ((TileEntityBedrockOre)world.getTileEntity(drillPos)).tier / (double)installedTier;
							combinedHardness = (int)(2 * 60 * 20 * tierDiff);
							bedrockOre = new BlockPos(x, y, z);
							bedrockDrilling = true;
							enableCrusher = false;
							enableSilkTouch = false;
							ignoreAll = false;
							break;
						}
						
						if(shouldIgnoreBlock(bState, drillPos))
							continue;
						
						ignoreAll = false;
						
						combinedHardness += bState.getBlockHardness(world, drillPos);
					}
				}
			}
			
			if(!ignoreAll) {
				ticksWorked++;
				
				int ticksToWork = (int) Math.ceil(combinedHardness / this.speed);
				
				if(ticksWorked >= ticksToWork) {
					
					if(bedrockOre == null) {
						breakBlocks(ring);
						buildWall(ring + 1, ring == radius && this.enableWalling);
						if(ring == radius)
							mineOresFromWall(ring + 1);
						tryCollect(radius + 1);
					} else {
						collectBedrock(bedrockOre);
					}
					ticksWorked = 0;
				}
				
				return false;
			} else {
				tryCollect(radius + 1);
			}
		}

		buildWall(radius + 1, this.enableWalling);
		ticksWorked = 0;
		return true;
	}
	
	
	
	/* breaks and drops all blocks in the specified ring */
	protected void breakBlocks(int ring) {
		int y = getY();
		
		for(int x = pos.getX() - ring; x <= pos.getX() + ring; x++) {
			for(int z = pos.getZ() - ring; z <= pos.getZ() + ring; z++) {
				
				if(ring == 1 || (x == pos.getX() - ring || x == pos.getX() + ring || z == pos.getZ() - ring || z == pos.getZ() + ring)) {
					
					BlockPos drillPos = new BlockPos(x, y, z);
					IBlockState bState = world.getBlockState(drillPos);
					if(!shouldIgnoreBlock(bState, drillPos)) {
						tryMineAtLocation(bState, drillPos);
					}
				}
			}
		}
	}
	
	public void tryMineAtLocation(IBlockState bState, BlockPos drillPos) {
	
		if(this.enableVeinMiner && this.getInstalledDrill().vein) {
			
			if(isOreDictOre(bState.getBlock())) {
				minX = drillPos.getX();
				minY = drillPos.getY();
				minZ = drillPos.getZ();
				maxX = drillPos.getX();
				maxY = drillPos.getY();
				maxZ = drillPos.getZ();
				breakRecursively(drillPos, 10);
				recursionBrake.clear();
				
				/* move all excavated items to the last drillable position which is also within collection range */
				List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(minX, minY, minZ, maxX + 1, maxY + 1, maxZ + 1));
				for(EntityItem item : items)
					item.setPosition(drillPos.getX() + 0.5, drillPos.getY() + 0.5, drillPos.getZ() + 0.5);
				
				return;
			}
		}
		breakSingleBlock(bState, drillPos);
	}
	
	protected boolean isOreDictOre(Block b) {
		
		/* doing this isn't terribly accurate but just for figuring out if there's OD it works */
		Item blockItem = Item.getItemFromBlock(b);
		
		if(blockItem != null && blockItem != Items.AIR) {
			List<String> names = ItemStackUtil.getOreDictNames(new ItemStack(blockItem));
			
			for(String name : names) {
				if(name.startsWith("ore")) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private HashSet<BlockPos> recursionBrake = new HashSet();
	private int minX = 0, minY = 0, minZ = 0, maxX = 0, maxY = 0, maxZ = 0;
	
	protected void breakRecursively(BlockPos drillPos, int depth) {
		
		if(depth < 0)
			return;
		if(recursionBrake.contains(drillPos))
			return;
		recursionBrake.add(drillPos);
		
		IBlockState bState = world.getBlockState(drillPos);
		Block b = bState.getBlock();
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			BlockPos veinPos = drillPos.add(dir.offsetX, dir.offsetY, dir.offsetZ);
			if(world.getBlockState(veinPos).getBlock() == b) {
				breakRecursively(veinPos, depth - 1);
			}
		}

		breakSingleBlock(bState, drillPos);

		int x = drillPos.getX();
		int y = drillPos.getY();
		int z = drillPos.getZ();

		if(x < minX) minX = x;
		if(x > maxX) maxX = x;
		if(y < minY) minY = y;
		if(y > maxY) maxY = y;
		if(z < minZ) minZ = z;
		if(z > maxZ) maxZ = z;
		
		if(this.enableWalling) {
			world.setBlockState(drillPos, ModBlocks.barricade.getDefaultState());
		}
	}

	@Override
	public DrillType getDrillTier(){
		return DrillType.INDUSTRIAL;
	}

	@Override
	public int getDrillRating(){
		return this.drillRating;
	}

	protected void breakSingleBlock(IBlockState bState, BlockPos drillPos) {
		Block b = bState.getBlock();
		NonNullList<ItemStack> items = NonNullList.create();
  		b.getDrops(items, world, drillPos, bState, this.getFortuneLevel());
		
		if(b == ModBlocks.barricade){
			items.clear();
		} else {
			if(this.canSilkTouch()) {
				
				ItemStack result = new ItemStack(Item.getItemFromBlock(b), 1, b.getMetaFromState(bState));
					
				if(result != null && !result.isEmpty()) {
					items.clear();
					items.add(result.copy());
				}
			} else if(b instanceof IDrillInteraction) {
				IDrillInteraction in = (IDrillInteraction) b;
				if(in.canBreak(world, drillPos.getX(), drillPos.getY(), drillPos.getZ(), bState, this)){
					ItemStack drop = in.extractResource(world, drillPos.getX(), drillPos.getY(), drillPos.getZ(), bState, this);
					
					if(drop != null) {
						items.clear();
						items.add(drop.copy());
					}
				}
			}
			
			if(this.enableCrusher) {
				
				NonNullList<ItemStack> list = NonNullList.create();
	  		
				for(ItemStack stack : items) {
					ItemStack crushed = ShredderRecipes.getShredderResult(stack).copy();
					
					if(crushed.getItem() == ModItems.scrap || crushed.getItem() == ModItems.dust) {
						list.add(stack);
					} else {
						crushed.setCount(crushed.getCount() * stack.getCount());
						list.add(crushed);
					}
				}
				
				items = list;
			}

			if(this.hasNullifier){
				
				NonNullList<ItemStack> goodList = NonNullList.create();

	  			for(ItemStack stack : items) {
	  				if(!ItemMachineUpgrade.scrapItems.contains(stack.getItem())){
	  					goodList.add(stack);
	  				}
	  			}

	  			items = goodList;
			}
		}
		
		for(ItemStack item : items) {
			world.spawnEntity(new EntityItem(world, drillPos.getX() + 0.5, drillPos.getY() + 0.5, drillPos.getZ() + 0.5, item));
		}
		
		world.destroyBlock(drillPos, false);
	}
	
	/* builds a wall along the specified ring, replacing fluid blocks. if wallEverything is set, it will also wall off replacable blocks like air or grass */
	protected void buildWall(int ring, boolean wallEverything) {
		int y = getY();
		
		for(int x = pos.getX() - ring; x <= pos.getX() + ring; x++) {
			for(int z = pos.getZ() - ring; z <= pos.getZ() + ring; z++) {
				
				BlockPos wallPos = new BlockPos(x, y, z);
				IBlockState bState = world.getBlockState(wallPos);
				
				if(x == pos.getX() - ring || x == pos.getX() + ring || z == pos.getZ() - ring || z == pos.getZ() + ring) {
					
					if(bState.getBlock().isReplaceable(world, wallPos) && (wallEverything || bState.getMaterial().isLiquid())) {
						world.setBlockState(wallPos, ModBlocks.barricade.getDefaultState());
					}
				} else {
					
					if(bState.getMaterial().isLiquid()) {
						world.setBlockToAir(wallPos);
						continue;
					}
				}
			}
		}
	}

	protected void mineOresFromWall(int ring) {
		int y = getY();
		
		for(int x = pos.getX() - ring; x <= pos.getX() + ring; x++) {
			for(int z = pos.getZ() - ring; z <= pos.getZ() + ring; z++) {
				
				if(ring == 1 || (x == pos.getX() - ring || x == pos.getX() + ring || z == pos.getZ() - ring || z == pos.getZ() + ring)) {
					
					BlockPos drillPos = new BlockPos(x, y, z);
					IBlockState bState = world.getBlockState(drillPos);
					if(!shouldIgnoreBlock(bState, drillPos) && isOreDictOre(bState.getBlock())) {
						tryMineAtLocation(bState, drillPos);
					}
				}
			}
		}
	}
	
	protected void tryEjectBuffer() {

		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		
		/* try to insert into a valid container */
		TileEntity te = world.getTileEntity(pos.add(dir.offsetX * 4, -3, dir.offsetZ * 4));

		if(te == null || !te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.toEnumFacing()))
			return;
		IItemHandler h = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.toEnumFacing());
		for(int i = 5; i < 14; i++) {
			if(!inventory.getStackInSlot(i).isEmpty() || inventory.getStackInSlot(i).getCount() <= 0) {
				tryFillContainerCap(h, i);
			}
		}
	}

	//Unloads output into chests. Capability version.
    public boolean tryFillContainerCap(IItemHandler chest, int slot) {
        //Check if we have something to output
        if(inventory.getStackInSlot(slot).isEmpty())
            return false;

        return tryInsertItemCap(chest, inventory.getStackInSlot(slot));
    }

	//Unloads output into chests. Capability version.
    public boolean tryInsertItemCap(IItemHandler chest, ItemStack stack) {
        //Check if we have something to output
        if(stack.isEmpty())
            return false;

        for(int i = 0; i < chest.getSlots(); i++) {
            
            ItemStack outputStack = stack.copy();
            if(outputStack.isEmpty() || outputStack.getCount() == 0)
                return true;

            ItemStack chestItem = chest.getStackInSlot(i).copy();
            if(chestItem.isEmpty() || (Library.areItemStacksCompatible(outputStack, chestItem, false) && chestItem.getCount() < chestItem.getMaxStackSize())) {
                int fillAmount = Math.min(chestItem.getMaxStackSize()-chestItem.getCount(), outputStack.getCount());
                
                outputStack.setCount(fillAmount);

                ItemStack rest = chest.insertItem(i, outputStack, true);
                if(rest.getItem() == Item.getItemFromBlock(Blocks.AIR)){
                    stack.shrink(outputStack.getCount());
                    chest.insertItem(i, outputStack, false);
                }
            }
        }

        return false;
    }
	
	/* pulls up an AABB around the drillbit and tries to either conveyor output or buffer collected items */
	protected void tryCollect(int radius) {
		int yLevel = getY();
		
		List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.getX() - radius, yLevel - 1, pos.getZ() - radius, pos.getX() + radius + 1, yLevel + 2, pos.getZ() + radius + 1));
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		
		/* try to insert into a valid container */
		TileEntity tile = world.getTileEntity(pos.add(dir.offsetX * 4, -3, dir.offsetZ * 4));
		supplyContainer(tile, items, dir.getOpposite());
		
		/* collect remaining items in internal buffer */
		for(EntityItem entityItem : items) {
			if(entityItem.isDead) continue;

			ItemStack stack = entityItem.getItem();
			if(stack.getCount() <= 0 || stack.isEmpty()){
				entityItem.setDead();
				continue;
			}
			stack = InventoryUtil.tryAddItemToInventory(inventory, 5, 13, stack);
			if(stack.getCount() <= 0 || stack.isEmpty()){
				entityItem.setDead();
				continue;
			}
			entityItem.setItem(stack);
			chuteTimer = 40;
		}
	}
	
	/* places all items into a connected container, if possible */
	protected void supplyContainer(TileEntity te, List<EntityItem> items, ForgeDirection dir) {
		if(te == null || !te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.toEnumFacing()))
			return;
		IItemHandler h = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.toEnumFacing());
		if(!(h instanceof IItemHandlerModifiable))
			return;
		
		IItemHandlerModifiable inv = (IItemHandlerModifiable)h;
		
		for(EntityItem entityItem : items) {
			if(entityItem.isDead) continue;
			ItemStack stack = entityItem.getItem();
			if(stack.getCount() <= 0 || stack.isEmpty()){
				entityItem.setDead();
				continue;
			}
			stack = InventoryUtil.tryAddItemToInventory(inv, 0, inv.getSlots() - 1, stack);
			chuteTimer = 40;
			if(stack.getCount() <= 0 || stack.isEmpty()){
				entityItem.setDead();
			} else {
				entityItem.setItem(stack);
			}
		}
	}

	protected void collectBedrock(BlockPos pos) {
		if(tank.getFluid() == null) return;
		TileEntity oreTile = world.getTileEntity(pos);
		if(oreTile instanceof TileEntityBedrockOre) {
			TileEntityBedrockOre ore = (TileEntityBedrockOre) oreTile;
			
			if(ore.oreName == null)
				return;
			if(ore.tier > this.getInstalledDrill().tier)
				return;
			if(ore.acidRequirement != null) {
				
				if(ore.acidRequirement.getFluid() != tank.getFluid().getFluid() || ore.acidRequirement.amount > tank.getFluidAmount()) return;
				
				tank.drain(ore.acidRequirement.amount, true);
			}
			ItemStack bedrockOreStack = new ItemStack(ModItems.ore_bedrock, 1, BedrockOreRegistry.getOreIndex(ore.oreName));
			InventoryUtil.tryAddItemToInventory(inventory, 5, 13, bedrockOreStack);
		}
	}
	
	public long getPowerConsumption() {
		return consumption;
	}
	
	public int getFortuneLevel() {
		EnumDrillType type = getInstalledDrill();
		
		if(type != null) return type.fortune;
		return 0;
	}

	public boolean shouldIgnoreBlock(IBlockState block, BlockPos pos) {
		Block b = block.getBlock();
		if(b == Blocks.AIR) return true;
		if(b == Blocks.BEDROCK) return true;
		if(b instanceof BlockGasBase) return true;
		float hardness = block.getBlockHardness(world, pos);
		if(hardness < 0 || hardness > 3_500_000) return true;
		if(block.getMaterial().isLiquid()) return true;
		return false;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("drill")) this.enableDrill = !this.enableDrill;
		if(data.hasKey("walling")) this.enableWalling = !this.enableWalling;
		if(data.hasKey("veinminer")) this.enableVeinMiner = !this.enableVeinMiner;

		//cant have silk and crusher together
		if(data.hasKey("silktouch")){
			if(!this.enableSilkTouch && this.enableCrusher){
				this.enableCrusher = false;
			}
			this.enableSilkTouch = !this.enableSilkTouch;
		}
		if(data.hasKey("crusher")){
			if(!this.enableCrusher && this.enableSilkTouch){
				this.enableSilkTouch = false;
			}
			this.enableCrusher = !this.enableCrusher;
		}
		
		this.markDirty();
	}
	
	public EnumDrillType getInstalledDrill() {
		ItemStack slotItem = inventory.getStackInSlot(4);
        if(slotItem != null && slotItem.getItem() instanceof ItemDrillbit) {
			return ((ItemDrillbit)slotItem.getItem()).drillType;
		}
		
		return null;
	}
	
	public boolean canVeinMine() {
		EnumDrillType type = getInstalledDrill();
		return this.enableVeinMiner && type != null && type.vein;
	}
	
	public boolean canSilkTouch() {
		EnumDrillType type = getInstalledDrill();
		return this.enableSilkTouch && type != null && type.silk;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tank.readFromNBT(nbt);
		if(nbt.hasKey("f")) {
            this.fluidType = FluidRegistry.getFluid(nbt.getString("f"));
        }
		this.enableDrill = nbt.getBoolean("d");
		this.enableCrusher = nbt.getBoolean("c");
		this.enableWalling = nbt.getBoolean("w");
		this.enableVeinMiner = nbt.getBoolean("v");
		this.enableSilkTouch = nbt.getBoolean("s");
		this.targetDepth = nbt.getInteger("t");
		this.power = nbt.getLong("p");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		tank.writeToNBT(nbt);
		if(fluidType != null) {
            nbt.setString("f", fluidType.getName());
        }
		nbt.setBoolean("d", enableDrill);
		nbt.setBoolean("c", enableCrusher);
		nbt.setBoolean("w", enableWalling);
		nbt.setBoolean("v", enableVeinMiner);
		nbt.setBoolean("s", enableSilkTouch);
		nbt.setInteger("t", targetDepth);
		nbt.setLong("p", power);
		return super.writeToNBT(nbt);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineExcavator(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineExcavator(player.inventory, this);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[]{tank.getTankProperties()[0]};
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if(resource != null && (fluidType == null || resource.getFluid() == fluidType) && resource.amount > 0) {
        	fluidType = resource.getFluid();
            return tank.fill(resource, doFill);
        } else {
            return 0;
        }
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
        } else {
            return super.getCapability(capability, facing);
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return true;
        } else {
            return super.hasCapability(capability, facing);
        }
    }

    @Override
	public void recievePacket(NBTTagCompound[] tags) {
		if(tags.length == 1) {
			tank.readFromNBT(tags[0]);
		}
	}
}
