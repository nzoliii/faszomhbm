package com.hbm.tileentity.machine;

import com.hbm.particle.book.ParticleBookCircle;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityBlackBook extends TileEntity implements ITickable {

	public int effectTime;
	public boolean end = true;
	
	@Override
	public void update() {
		if(world.isRemote){
			doEffect();
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void doEffect(){
		if(effectTime >= 0){
			for(int i = -1; i < 2; i ++){
				for(int j = -1; j < 2; j ++){
					if(!world.getBlockState(pos.add(i, -1, j)).isFullBlock()){
						end = true;
						effectTime = -1;
						return;
					}
				}
			}
			end = false;
			effectTime ++;
			if(effectTime == 1){
				ParticleBookCircle b = new ParticleBookCircle(this, pos.getX()+0.5, pos.getY(), pos.getZ()+0.5, 3);
				Minecraft.getMinecraft().effectRenderer.addEffect(b);
			}
			if(effectTime >= 100){
				effectTime = -1;
				end = true;
			}
		}
	}
	
}
