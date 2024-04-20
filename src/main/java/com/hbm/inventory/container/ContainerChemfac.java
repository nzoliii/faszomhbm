package com.hbm.inventory.container;

import com.hbm.inventory.SlotUpgrade;
import com.hbm.tileentity.machine.TileEntityMachineChemfac;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ContainerChemfac extends Container {
	private final TileEntityMachineChemfac chemfac;

	public ContainerChemfac(InventoryPlayer playerInv, TileEntityMachineChemfac tile) {
		this.chemfac = tile;

		this.addSlotToContainer(new SlotItemHandler(tile.inventory, 0, 234, 79));

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				this.addSlotToContainer(new SlotUpgrade(tile.inventory, 1 + i * 2 + j, 217 + j * 18, 172 + i * 18));
			}
		}

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 2; j++) {

				for (int k = 0; k < 2; k++) {
					for (int l = 0; l < 2; l++) {
						this.addSlotToContainer(new SlotItemHandler(tile.inventory, this.inventorySlots.size(), 7 + j * 110 + l * 16, 14 + i * 38 + k * 16));
					}
				}

				for (int k = 0; k < 2; k++) {
					for (int l = 0; l < 2; l++) {
						this.addSlotToContainer(new SlotItemHandler(tile.inventory, this.inventorySlots.size(), 69 + j * 110 + l * 16, 14 + i * 38 + k * 16));
					}
				}

				this.addSlotToContainer(new SlotItemHandler(tile.inventory, this.inventorySlots.size(), 51 + j * 110, 30 + i * 38));
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 34 + j * 18, 174 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(playerInv, i, 34 + i * 18, 232));
		}
	}

	@Override
	public boolean canInteractWith(@Nonnull EntityPlayer playerIn) {
		return chemfac.isUseableByPlayer(playerIn);
	}

	@Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2)
    {
		ItemStack var3 = ItemStack.EMPTY;
		Slot var4 = (Slot) this.inventorySlots.get(par2);
		
		if (var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			
            if (par2 <= 3) {
				if (!this.mergeItemStack(var5, 4, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(var5, 0, 3, false))
			{
				return ItemStack.EMPTY;
			}
			
			if (var5.getCount() == 0)
			{
				var4.putStack(ItemStack.EMPTY);
			}
			else
			{
				var4.onSlotChanged();
			}
		}
		
		return var3;
    }
}
