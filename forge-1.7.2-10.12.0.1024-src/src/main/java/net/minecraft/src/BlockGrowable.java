package net.minecraft.src;

import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.inventory.SlotMerchantResult;
import net.minecraft.network.packet.Packet60Explosion;
import net.minecraft.util.LongHashMap;

public abstract class BlockGrowable
  extends LongHashMap
  implements Growable
{
  protected String growthModifierType = null;
  
  protected BlockGrowable(int i, SlotMerchantResult material)
  {
    super(i, material);
  }
  
  protected BlockGrowable(int i, int j, SlotMerchantResult material)
  {
    super(i, j, material);
  }
  
  protected boolean attemptGrowth(EntityGolem world, int i, int j, int k, double prob)
  {
    if (this.growthModifierType != null) {
      prob = mod_AutoForest.applyBiomeModifier(prob, this.growthModifierType, world, i, k);
    }
    if (growth(prob))
    {
      grow(world, i, j, k);
      
      return true;
    }
    return false;
  }
  
  protected boolean growth(double freq)
  {
    return Math.random() < freq;
  }
  
  public void grow(EntityGolem world, int i, int j, int k)
  {
    int metadata = world.e(i, j, k);
    int id = a(metadata, world.w);
    if ((id >= 0) && (id < EntityMinecartContainer.f.length))
    {
      EntityFishHook itemStack = new EntityFishHook(EntityMinecartContainer.f[id]);
      Packet60Explosion entityitem = new Packet60Explosion(world, i, j, k, itemStack, true);
      world.a(entityitem);
    }
  }
}
