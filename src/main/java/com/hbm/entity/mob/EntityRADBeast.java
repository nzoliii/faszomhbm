package com.hbm.entity.mob;

import java.util.List;

import com.hbm.interfaces.IRadiationImmune;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.AdvancementManager;
import com.hbm.util.ContaminationUtil;
import com.hbm.saveddata.RadiationSavedData;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityRADBeast extends EntityMob implements IRadiationImmune {

	public static final DataParameter<Integer> TARGET_ID = EntityDataManager.createKey(EntityRADBeast.class, DataSerializers.VARINT);
	
	private float heightOffset = 0.5F;
    private int heightOffsetUpdateTime;
    
	public EntityRADBeast(World worldIn) {
		super(worldIn);
		this.isImmuneToFire = true;
        this.experienceValue = 30;
        this.ignoreFrustumCheck = true;
	}
	
    @Override
    protected void applyEntityAttributes() {
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(120.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(16.0D);
    }
    
    public EntityRADBeast makeLeader() {
    	this.setDropChance(EntityEquipmentSlot.MAINHAND, 1);
    	this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModItems.coin_radiation));
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(360.0D);
        this.heal(this.getMaxHealth());
    	return this;
    }
    
    @Override
    public void onDeath(DamageSource cause) {
    	if(this.getMaxHealth() > 150) {
	        List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(50, 50, 50));

	        for(EntityPlayer player : players) {
	        	AdvancementManager.grantAchievement(player, AdvancementManager.achMeltdown);
	        }
        }
        super.onDeath(cause);
    }
    
    @Override
    protected boolean canDespawn() {
    	return false;
    }
    
    @Override
    protected void entityInit() {
    	this.getDataManager().register(TARGET_ID, 0);
    	super.entityInit();
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
    	return HBMSoundHandler.geigerSounds[rand.nextInt(6)];
    }
    
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    	return SoundEvents.ENTITY_BLAZE_HURT;
    }
    
    @Override
    protected SoundEvent getDeathSound() {
    	return HBMSoundHandler.metalStep;
    }
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float f) {
        return 15728880;
    }

    public float getBrightness(float f) {
        return 1.0F;
    }
    
    @Override
    public int getTotalArmorValue() {
    	return 8;
    }
    
    @Override
    public void onLivingUpdate() {
    	if (!this.world.isRemote) {

            if (this.isWet()) {
                this.attackEntityFrom(DamageSource.DROWN, 1.0F);
            }

            --this.heightOffsetUpdateTime;

            if (this.heightOffsetUpdateTime <= 0) {
                this.heightOffsetUpdateTime = 100;
                this.heightOffset = 0.5F + (float)this.rand.nextGaussian() * 3.0F;
            }

            if (this.getAttackTarget() != null && this.getAttackTarget().posY + (double)this.getAttackTarget().getEyeHeight() > this.posY + (double)this.getEyeHeight() + (double)this.heightOffset) {
                this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
            }

            if(this.getAttackTarget() != null && idleTime < 10) {

            	if(this.dataManager.get(TARGET_ID) != getAttackTarget().getEntityId()){
            		this.dataManager.set(TARGET_ID, getAttackTarget().getEntityId());
            	}
            } else {
            	this.dataManager.set(TARGET_ID, 0);
            }
        }

        if (!this.onGround && this.motionY < 0.0D) {
            this.motionY *= 0.6D;
        }

        if(this.getMaxHealth() <= 150) {

	        for (int i = 0; i < 6; i++) {
	            this.world.spawnParticle(EnumParticleTypes.TOWN_AURA,
	            		this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width * 1.5,
	            		this.posY + this.rand.nextDouble() * (double)this.height,
	            		this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width * 1.5,
	            		0.0D, 0.0D, 0.0D);
	        }

	        if(this.rand.nextInt(6) == 0) {

	            this.world.spawnParticle(EnumParticleTypes.FLAME,
	            		this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width,
	            		this.posY + this.rand.nextDouble() * (double)this.height * 0.75,
	            		this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width,
	            		0.0D, 0.0D, 0.0D);
	        }

        } else {
			this.world.spawnParticle(EnumParticleTypes.LAVA, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height * 0.75, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, 0.0D, 0.0D, 0.0D);
        }

        ContaminationUtil.radiate(world, posX, posY, posZ, 32, 500);
        super.onLivingUpdate();
    }
    
    @Override
    public boolean attackEntityAsMob(Entity target) {
    	boolean flag = false;
    	float dist = (float) this.getDistanceSq(target);
    	if (this.idleTime <= 0 && dist < 4.0F && target.getEntityBoundingBox().maxY > this.getEntityBoundingBox().minY && target.getEntityBoundingBox().minY < this.getEntityBoundingBox().maxY) {
            this.idleTime = 20;
            return super.attackEntityAsMob(target);
        } else if(dist < 30.0F) {

            double deltaX = target.posX - this.posX;
            double deltaZ = target.posZ - this.posZ;

            if (this.idleTime == 0 && getAttackTarget() != null) {

            	RadiationSavedData.incrementRad(world, this.getPosition(), 150, 1000);
            	flag = target.attackEntityFrom(ModDamageSource.radiation, 16.0F);
            	this.swingArm(EnumHand.MAIN_HAND);
            	this.playLivingSound();
            	this.idleTime = 20;
            }
        }
    	return flag;
    }
    
    public Entity getUnfortunateSoul() {
    	int id = this.dataManager.get(TARGET_ID);
    	return world.getEntityByID(id);
    }

    @Override
    public void fall(float distance, float damageMultiplier) {}
    
    @Override
    protected Item getDropItem() {
    	return ModItems.rod_uranium_fuel_depleted;
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int looting, DamageSource source) {
        super.dropLoot(wasRecentlyHit, looting, source);
        if(looting > 0) {
                this.dropItem(ModItems.nugget_polonium, looting);
            }
            
        int count = this.rand.nextInt(3) + 1;
        
        for(int i = 0; i < count; i++) {
            
            int r = this.rand.nextInt(3);
            
            if(r == 0) {
                this.dropItem(this.isWet() ? ModItems.waste_uranium : ModItems.rod_uranium_fuel_depleted, 1);
                
            } else if(r == 1) {
                this.dropItem(this.isWet() ? ModItems.waste_mox : ModItems.rod_mox_fuel_depleted, 1);
                
            } else if(r == 2) {
                this.dropItem(this.isWet() ? ModItems.waste_plutonium : ModItems.rod_plutonium_fuel_depleted, 1);
                
            }
        }
    }
}
