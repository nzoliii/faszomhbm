package com.hbm.handler;

import java.util.Arrays;
import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class WeightedRandomChestContentFrom1710 extends WeightedRandom.Item
{
    /** The Item/Block ID to generate in the Chest. */
    public ItemStack theItemId;
    /** The minimum chance of item generating. */
    public int theMinimumChanceToGenerateItem;
    /** The maximum chance of item generating. */
    public int theMaximumChanceToGenerateItem;

    public WeightedRandomChestContentFrom1710(Item item, int meta, int minChance, int maxChance, int weight)
    {
        super(weight);
        this.theItemId = new ItemStack(item, 1, meta);
        this.theMinimumChanceToGenerateItem = minChance;
        this.theMaximumChanceToGenerateItem = maxChance;
    }

    public WeightedRandomChestContentFrom1710(ItemStack stack, int minChance, int maxChance, int weight)
    {
        super(weight);
        this.theItemId = stack;
        this.theMinimumChanceToGenerateItem = minChance;
        this.theMaximumChanceToGenerateItem = maxChance;
    }

    public static void generateChestContents(Random p_76293_0_, WeightedRandomChestContentFrom1710[] p_76293_1_, ICapabilityProvider p_76293_2_, int p_76293_3_){
    	if(p_76293_2_ != null && p_76293_2_.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)){
    		IItemHandler inventory = p_76293_2_.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
    		if(inventory instanceof IItemHandlerModifiable){
    			generateChestContents(p_76293_0_, p_76293_1_, (IItemHandlerModifiable)inventory, p_76293_3_);
    		}
    	}
    }
    
    /**
     * Generates the Chest contents.
     */
    public static void generateChestContents(Random p_76293_0_, WeightedRandomChestContentFrom1710[] p_76293_1_, IItemHandlerModifiable p_76293_2_, int p_76293_3_)
    {
        for (int j = 0; j < p_76293_3_; ++j)
        {
            WeightedRandomChestContentFrom1710 weightedrandomchestcontent = (WeightedRandomChestContentFrom1710)WeightedRandom.getRandomItem(p_76293_0_, Arrays.asList(p_76293_1_));
            ItemStack[] stacks = weightedrandomchestcontent.generateChestContent(p_76293_0_, p_76293_2_);

            for (ItemStack item : stacks)
            {
                p_76293_2_.setStackInSlot(p_76293_0_.nextInt(p_76293_2_.getSlots()), item);
            }
        }
    }

    /*public static void generateDispenserContents(Random p_150706_0_, WeightedRandomChestContentFrom1710[] p_150706_1_, TileEntityDispenser p_150706_2_, int p_150706_3_)
    {
        for (int j = 0; j < p_150706_3_; ++j)
        {
            WeightedRandomChestContentFrom1710 weightedrandomchestcontent = (WeightedRandomChestContentFrom1710)WeightedRandom.getRandomItem(p_150706_0_, Arrays.asList(p_150706_1_));
            ItemStack[] stacks = weightedrandomchestcontent.generateChestContent(p_150706_0_, p_150706_2_);
            for (ItemStack item : stacks)
            {
                p_150706_2_.setInventorySlotContents(p_150706_0_.nextInt(p_150706_2_.getSizeInventory()), item);
            }
        }
    }*/

    public static WeightedRandomChestContentFrom1710[] func_92080_a(WeightedRandomChestContentFrom1710[] p_92080_0_, WeightedRandomChestContentFrom1710 ... p_92080_1_)
    {
        WeightedRandomChestContentFrom1710[] aweightedrandomchestcontent1 = new WeightedRandomChestContentFrom1710[p_92080_0_.length + p_92080_1_.length];
        int i = 0;

        for (int j = 0; j < p_92080_0_.length; ++j)
        {
            aweightedrandomchestcontent1[i++] = p_92080_0_[j];
        }

        WeightedRandomChestContentFrom1710[] aweightedrandomchestcontent2 = p_92080_1_;
        int k = p_92080_1_.length;

        for (int l = 0; l < k; ++l)
        {
            WeightedRandomChestContentFrom1710 weightedrandomchestcontent1 = aweightedrandomchestcontent2[l];
            aweightedrandomchestcontent1[i++] = weightedrandomchestcontent1;
        }

        return aweightedrandomchestcontent1;
    }

    // -- Forge hooks
    /**
     * Allow a mod to submit a custom implementation that can delegate item stack generation beyond simple stack lookup
     *
     * @param random The current random for generation
     * @param newInventory The inventory being generated (do not populate it, but you can refer to it)
     * @return An array of {@link ItemStack} to put into the chest
     */
    public ItemStack[] generateChestContent(Random random, IItemHandlerModifiable newInventory)
    {
        return generateStacks(random, theItemId, theMinimumChanceToGenerateItem, theMaximumChanceToGenerateItem);
    }
    
    /**
     * Generates an array of items based on the input min/max count.
     * If the stack can not hold the total amount, it will be split into
     * stacks of size 1.
     *
     * @param rand A random number generator
     * @param source Source item stack
     * @param min Minimum number of items
     * @param max Maximum number of items
     * @return An array containing the generated item stacks
     */
    public static ItemStack[] generateStacks(Random rand, ItemStack source, int min, int max)
    {
        int count = min + (rand.nextInt(max - min + 1));

        ItemStack[] ret;
        if (source.getItem() == null)
        {
            ret = new ItemStack[0];
        }
        else if (count > source.getMaxStackSize())
        {
            ret = new ItemStack[count];
            for (int x = 0; x < count; x++)
            {
                ret[x] = source.copy();
                ret[x].setCount(1);
            }
        }
        else
        {
            ret = new ItemStack[1];
            ret[0] = source.copy();
            ret[0].setCount(count);
        }
        return ret;
    }
}
