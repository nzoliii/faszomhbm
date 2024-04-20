package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.capability.HbmLivingCapability.EntityHbmPropsProvider;
import com.hbm.capability.HbmLivingProps;
import com.hbm.potion.HbmPotion;
import com.hbm.util.ContaminationUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityDeconRad extends TileEntity implements ITickable {

	private static float radRemove;
	private static final float decayRate = 0.9998074776F; //30m halflife

	public TileEntityDeconRad() {
		super();
		this.radRemove = 0.5F;
	}

	public TileEntityDeconRad(float rad) {
		super();
		this.radRemove = rad;
	}

	@Override
	public void update() {
		if(!this.world.isRemote) {
			List<Entity> entities = this.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.getX() - 0.5, pos.getY(), pos.getZ() - 0.5, pos.getX() + 1.5, pos.getY() + 2, pos.getZ() + 1.5));

			if(!entities.isEmpty()) {
				for(Entity e : entities) {
					if(e instanceof EntityLivingBase){
						if(((EntityLivingBase)e).isPotionActive(HbmPotion.radiation)){
							((EntityLivingBase)e).removePotionEffect(HbmPotion.radiation);
						}
					}
					if(e.hasCapability(EntityHbmPropsProvider.ENT_HBM_PROPS_CAP, null)){
						if(this.radRemove > 0.0F){
							e.getCapability(EntityHbmPropsProvider.ENT_HBM_PROPS_CAP, null).decreaseRads(this.radRemove);
						}
					}
					if(e instanceof EntityPlayer){
						ContaminationUtil.neutronActivateInventory((EntityPlayer)e, -0.005F, decayRate);
						((EntityPlayer)e).inventoryContainer.detectAndSendChanges();
					}
				}
			}
		}
	}
}
