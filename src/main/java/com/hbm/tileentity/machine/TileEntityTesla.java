package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.mob.EntityCyberCrab;
import com.hbm.entity.mob.EntityNuclearCreeper;
import com.hbm.entity.mob.EntityTaintCrab;
import com.hbm.entity.mob.EntityTeslaCrab;
import com.hbm.handler.ArmorUtil;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TETeslaPacket;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyUser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityTesla extends TileEntityMachineBase implements ITickable, IEnergyUser {

	public long power;
	public static final long maxPower = 100000;
	
	public static int range = 10;
	public static double offset = 1.75;
	
	public List<double[]> targets = new ArrayList<double[]>();
	
	public TileEntityTesla() {
		super(0);
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public void update() {
		if(!world.isRemote) {
			this.updateStandardConnections(world, pos);
			this.targets.clear();
			
			if(world.getBlockState(pos.down()).getBlock() == ModBlocks.meteor_battery)
				power = maxPower;
			
			if(power >= 5000) {
				power -= 5000;

				double dx = pos.getX() + 0.5;
				double dy = pos.getY() + offset;
				double dz = pos.getZ() + 0.5;
				
				this.targets = zap(world, dx, dy, dz, range, null);
			}
			
			PacketDispatcher.wrapper.sendToAllAround(new TETeslaPacket(pos, targets), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 100));
		}
	}

	public static List<double[]> zap(World worldObj, double x, double y, double z, double radius, Entity source) {

		List<double[]> ret = new ArrayList<double[]>();
		
		List<EntityLivingBase> targets = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius));
		
		for(EntityLivingBase e : targets) {
			
			if(e instanceof EntityOcelot || e == source)
				continue;
			
			Vec3 vec = Vec3.createVectorHelper(e.posX - x, e.posY + e.height / 2 - y, e.posZ - z);
			
			if(vec.lengthVector() > range)
				continue;

			if(Library.isObstructed(worldObj, x, y, z, e.posX, e.posY + e.height / 2, e.posZ))
				continue;
			
			if(e instanceof EntityTaintCrab) {
				ret.add(new double[] {e.posX, e.posY + 1.25, e.posZ});
				e.heal(15F);
				continue;
			}
			
			if(e instanceof EntityTeslaCrab) {
				ret.add(new double[] {e.posX, e.posY + 1, e.posZ});
				e.heal(10F);
				continue;
			}
			
			if(e instanceof EntityCyberCrab) {
				ret.add(new double[] {e.posX, e.posY + e.height / 2, e.posZ});
				e.heal(0.1F);
				continue;
			}
			
			if(!(e instanceof EntityPlayer && ArmorUtil.checkForFaraday((EntityPlayer)e)))
				if(e.attackEntityFrom(ModDamageSource.electricity, MathHelper.clamp(0.5F * e.getMaxHealth() / (float)targets.size(), 3, 20)))
					worldObj.playSound(null, e.posX, e.posY, e.posZ, HBMSoundHandler.tesla, SoundCategory.BLOCKS, 1.0F, 1.0F);
			
			if(e instanceof EntityCreeper) {
				e.onStruckByLightning(new EntityLightningBolt(worldObj, e.posX, e.posY, e.posZ, true));
			}
			
			if(e instanceof EntityNuclearCreeper) {
				((EntityNuclearCreeper)e).getDataManager().set(EntityNuclearCreeper.POWERED, true);
			}
			
			double offset = 0;
			
			if(source != null && e instanceof EntityPlayer && worldObj.isRemote)
				offset = e.height;
			
			ret.add(new double[] {e.posX, e.posY + e.height / 2 - offset, e.posZ});
		}
		
		return ret;
	}
	
	@Override
	public void setPower(long i) {
		power = i;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
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

}
