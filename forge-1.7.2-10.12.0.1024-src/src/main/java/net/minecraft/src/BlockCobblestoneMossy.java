package net.minecraft.src;

import java.util.Random;

import moapi.ModBooleanOption;
import moapi.ModMappedMultiOption;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.inventory.SlotMerchantResult;
import net.minecraft.util.LongHashMap;

public class BlockCobblestoneMossy
  extends BlockGrowable
{
  public final int bA;
  protected String growthModifierType = "MossGrowth";
  
  protected BlockCobblestoneMossy(int i, int j)
  {
    super(i + 92, j, SlotMerchantResult.e);
    

    m[i] = this;
    this.bA = i;
    o[i] = a();
    q[i] = (a() ? 'ÿ' : 0);
    r[i] = (!SlotMerchantResult.e.func_111238_b() ? 1 : false);
    p[i] = false;
    n[this.bA] = true;
    
    a(true);
  }
  
  public int f()
  {
    return 30;
  }
  
  public int a(int i, Random random)
  {
    return this.bA;
  }
  
  public void a(EntityGolem world, EntityMinecartMobSpawner entityplayer, int i, int j, int k, int l)
  {
    entityplayer.a(net.minecraft.network.packet.Packet201PlayerInfo.C[this.bA], 1);
    g(world, i, j, k, l);
  }
  
  public static void createInBlockList()
  {
    int id = LongHashMapEntry.ap.bA;
    if (!(LongHashMapEntry.m[id] instanceof BlockCobblestoneMossy)) {
      LongHashMapEntry.m[id] = new BlockCobblestoneMossy(48, 36).c(2.0F).b(10.0F).a(h).a("stoneMoss");
    }
  }
  
  public void a(EntityGolem world, int i, int j, int k, Random random)
  {
    if (!world.isCollided)
    {
      boolean grow = ((Boolean)mod_AutoForest.mossGrows.getValue()).booleanValue();
      if (grow)
      {
        double growthRate = 1.0D / ((Integer)mod_AutoForest.mossGrowthRate.getValue()).intValue();
        attemptGrowth(world, i, j, k, growthRate);
      }
    }
  }
  
  public void grow(EntityGolem world, int i, int j, int k)
  {
    int scanSize = 1;
    int metadata = world.e(i, j, k);
    int id = a(metadata, world.w);
    if ((id >= 0) && (id < EntityMinecartContainer.f.length)) {
      for (int x = i - scanSize; x <= i + scanSize; x++) {
        for (int y = j - scanSize; y <= j + scanSize; y++) {
          for (int z = k - scanSize; z <= k + scanSize; z++) {
            if (world.a(x, y, z) == LongHashMap.x.bA)
            {
              world.g(x, y, z, this.bA);
              return;
            }
          }
        }
      }
    }
  }
}
