package net.minecraft.inventory;

import moapi.ModBooleanOption;
import moapi.ModOptionsAPI;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.src.Plantable;
import net.minecraft.util.LongHashMap;
import net.minecraft.world.World;

public class ContainerDispenser
  extends EntityMinecartContainer
  implements Plantable
{
  private int tileEntityDispenser;
  
  public ContainerDispenser(World i, LongHashMap block)
  {
    super(i);
    this.tileEntityDispenser = block.bA;
  }
  
  public boolean a(EntityFishHook itemstack, EntityMinecartMobSpawner entityplayer, EntityGolem world, int i, int j, int k, int l)
  {
    int i1 = world.a(i, j, k);
    if (i1 == LongHashMap.aT.bA)
    {
      l = 0;
    }
    else if (i1 != LongHashMap.bv.bA)
    {
      if (l == 0) {
        j--;
      }
      if (l == 1) {
        j++;
      }
      if (l == 2) {
        k--;
      }
      if (l == 3) {
        k++;
      }
      if (l == 4) {
        i--;
      }
      if (l == 5) {
        i++;
      }
    }
    if (!entityplayer.e(i, j, k)) {
      return false;
    }
    if (itemstack.ticksExisted == 0) {
      return false;
    }
    if (world.a(this.tileEntityDispenser, i, j, k, false, l))
    {
      LongHashMap block = LongHashMap.m[this.tileEntityDispenser];
      if (world.g(i, j, k, this.tileEntityDispenser))
      {
        if (world.a(i, j, k) == this.tileEntityDispenser)
        {
          LongHashMap.m[this.tileEntityDispenser].c(world, i, j, k, l);
          LongHashMap.m[this.tileEntityDispenser].a(world, i, j, k, entityplayer);
        }
        world.a(i + 0.5F, j + 0.5F, k + 0.5F, block.bL.d(), (block.bL.b() + 1.0F) / 2.0F, block.bL.c() * 0.8F);
        itemstack.ticksExisted -= 1;
      }
    }
    return true;
  }
  
  public boolean plantable(EntityGolem world, int i, int j, int k, int belowID, int age)
  {
    boolean reedGrow = ((Boolean)((ModBooleanOption)ModOptionsAPI.getModOptions("NatureOverhaul").getSubOption("Plants Options").getSubOption("Reed Options").getOption("ReedsGrow")).getValue()).booleanValue();
    


    return (reedGrow) && ((belowID == 2) || (belowID == 3)) && ((world.f(i - 1, j - 1, k) == SlotMerchantResult.g) || (world.f(i + 1, j - 1, k) == SlotMerchantResult.g) || (world.f(i, j - 1, k - 1) == SlotMerchantResult.g) || (world.f(i, j - 1, k + 1) == SlotMerchantResult.g));
  }
  
  public int getPlantBlockID()
  {
    return this.tileEntityDispenser;
  }
  
  public float[] getVelocities(double baseSpeed)
  {
    float[] out = new float[3];
    
    out[0] = ((float)(Math.random() * baseSpeed * randSign()));
    out[1] = ((float)(baseSpeed + baseSpeed * Math.random() * 1.5D));
    out[2] = ((float)(Math.random() * baseSpeed * randSign()));
    
    return out;
  }
  
  private int randSign()
  {
    return (int)Math.pow(-1.0D, (int)Math.round(Math.random()) + 1) * 2;
  }

@Override
public int getSizeInventory() {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public int getMinecartType() {
	// TODO Auto-generated method stub
	return 0;
}
}
