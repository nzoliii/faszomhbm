package com.hbm.saveddata.satellites;

import com.hbm.entity.projectile.EntityTom;
import com.hbm.main.AdvancementManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class SatelliteHorizons extends Satellite {

	boolean used = false;
	public long lastOp;
	
	public SatelliteHorizons() {
		this.satIface = Interfaces.SAT_COORD;
	}

	public void onOrbit(World world, double x, double y, double z) {

		for(EntityPlayer p : world.playerEntities)
			AdvancementManager.grantAchievement(p, AdvancementManager.horizonsStart);
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setBoolean("used", used);
		nbt.setLong("lastOp", lastOp);
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		used = nbt.getBoolean("used");
		lastOp = nbt.getLong("lastOp");
	}
	
	public void onCoordAction(World world, EntityPlayer player, int x, int y, int z) {
		if(used)
			return;
		used = true;
		SatelliteSavedData.getData(world).markDirty();
		
		EntityTom tom = new EntityTom(world);
		tom.setPosition(x + 0.5, 600, z + 0.5);
		
		IChunkProvider provider = world.getChunkProvider();
		provider.provideChunk(x >> 4, z >> 4);
		
		world.spawnEntity(tom);

		for(EntityPlayer p : world.playerEntities)
			AdvancementManager.grantAchievement(p, AdvancementManager.horizonsEnd);
		
		//not necessary but JUST to make sure
		if(!world.isRemote) {
			FMLCommonHandler.instance().getMinecraftServerInstance().sendMessage(new TextComponentTranslation("chat.gerald.detonated"));
		}
	}
}
