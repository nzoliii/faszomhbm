package com.hbm.entity.missile;

import com.hbm.entity.particle.EntitySSmokeFX;
import com.hbm.explosion.ExplosionLarge;

import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBombletTheta extends EntityThrowable {

	double decelY = 0.1D;
	double accelXZ = 0.1D;
	
	public EntityBombletTheta(World worldIn) {
		super(worldIn);
		this.ignoreFrustumCheck = true;
	}

	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;

        Vec3d vector = new Vec3d(motionX, 0, motionZ);
        vector = vector.normalize();
        vector = new Vec3d(vector.x*accelXZ, vector.y, vector.z*accelXZ);
		this.motionY -= decelY;
		this.motionX -= vector.x;
		this.motionZ -= vector.z;
		
		if(motionY < -0.75D && !world.isRemote && rand.nextInt(10) == 0) {
			EntityBombletSelena selena = new EntityBombletSelena(world);
			selena.posX = this.posX;
			selena.posY = this.posY;
			selena.posZ = this.posZ;
			selena.motionX = rand.nextGaussian();
			selena.motionY = rand.nextGaussian();
			selena.motionZ = rand.nextGaussian();
			selena.decelY = this.decelY;
			selena.accelXZ = this.accelXZ;
			world.spawnEntity(selena);
		}
        
        this.rotation();
        
        if(this.world.getBlockState(new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ)).getBlock() != Blocks.AIR)
        {
    		if(!this.world.isRemote)
    		{
    			ExplosionLarge.explodeFire(world, this.posX + 0.5F, this.posY + 0.5F, this.posZ + 0.5F, 50.0F, true, true, true);
    		}
    		this.setDead();
        }

		if(!this.world.isRemote)
			this.world.spawnEntity(new EntitySSmokeFX(this.world, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0));
	}
	
	protected void rotation() {
        float f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

        for (this.rotationPitch = (float)(Math.atan2(this.motionY, f2) * 180.0D / Math.PI) - 90; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
        {
            ;
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
        {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F)
        {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
        {
            this.prevRotationYaw += 360.0F;
        }
	}
	
	@Override
	protected void onImpact(RayTraceResult result) {}
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 25000;
    }

}
