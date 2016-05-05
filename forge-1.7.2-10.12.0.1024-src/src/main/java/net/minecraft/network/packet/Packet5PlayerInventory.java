package net.minecraft.network.packet;

import java.util.Random;
import net.minecraft.enchantment.EnchantmentArrowInfinite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTrackerEntry;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemDye;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.potion.PotionAttackDamage;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.src.Growable;
import net.minecraft.util.Icon;
import net.minecraft.util.LongHashMapEntry;
import net.minecraft.village.VillageDoorInfo;

public class Packet5PlayerInventory
  extends EntityMinecartContainer
{
  public Packet5PlayerInventory(int i)
  {
    super(i);
    a(true);
    f(0);
  }
  
  public int a(int i)
  {
    int j = i;
    return this.bt + j % 8 * 16 + j / 8;
  }
  
  public String a(EntityFishHook itemstack)
  {
    return super.c() + "." + entityID[itemstack.i()];
  }
  
  public boolean a(EntityFishHook itemstack, EntityMinecartMobSpawner entityplayer, EntityGolem world, int i, int j, int k, int l)
  {
    if (!entityplayer.e(i, j, k)) {
      return false;
    }
    if (itemstack.i() == 15)
    {
      int i1 = world.a(i, j, k);
      if (i1 == LongHashMapEntry.z.bA)
      {
        if (!world.isCollided)
        {
          ((EntityCaveSpider)LongHashMapEntry.z).c(world, i, j, k, world.w);
          itemstack.shake -= 1;
        }
        return true;
      }
      if ((i1 == LongHashMapEntry.ag.bA) || (i1 == LongHashMapEntry.ah.bA))
      {
        if ((!world.isCollided) && (((EnchantmentArrowInfinite)LongHashMapEntry.m[i1]).c(world, i, j, k, world.w))) {
          itemstack.shake -= 1;
        }
        return true;
      }
      if ((i1 == LongHashMapEntry.bu.bA) || (i1 == LongHashMapEntry.bt.bA))
      {
        if (!world.isCollided)
        {
          ((ItemDye)LongHashMapEntry.m[i1]).i(world, i, j, k);
          itemstack.shake -= 1;
        }
        return true;
      }
      if (i1 == LongHashMapEntry.aA.bA)
      {
        if (!world.isCollided)
        {
          ((ServerConfigurationManager)LongHashMapEntry.aA).f(world, i, j, k);
          itemstack.shake -= 1;
        }
        return true;
      }
      if (i1 == LongHashMapEntry.v.bA)
      {
        if (!world.isCollided)
        {
          itemstack.shake -= 1;
          label515:
          for (int j1 = 0; j1 < 128; j1++)
          {
            int k1 = i;
            int l1 = j + 1;
            int i2 = k;
            for (int j2 = 0; j2 < j1 / 16; j2++)
            {
              k1 += e.nextInt(3) - 1;
              l1 += (e.nextInt(3) - 1) * e.nextInt(3) / 2;
              i2 += e.nextInt(3) - 1;
              if ((world.a(k1, l1 - 1, i2) != LongHashMapEntry.v.bA) || (world.h(k1, l1, i2))) {
                break label515;
              }
            }
            if (world.a(k1, l1, i2) == 0) {
              if (e.nextInt(10) != 0) {
                world.d(k1, l1, i2, LongHashMapEntry.Y.bA, 1);
              } else if (e.nextInt(3) != 0) {
                world.g(k1, l1, i2, LongHashMapEntry.ae.bA);
              } else {
                world.g(k1, l1, i2, LongHashMapEntry.af.bA);
              }
            }
          }
        }
        return true;
      }
      if (applyBonemeal(world, i, j, k, i1))
      {
        itemstack.shake -= 1;
        return true;
      }
    }
    return false;
  }
  
  private boolean applyBonemeal(EntityGolem world, int i, int j, int k, int id)
  {
    if ((LongHashMapEntry.m[id] instanceof Growable))
    {
      ((Growable)LongHashMapEntry.m[id]).grow(world, i, j, k);
      return true;
    }
    return false;
  }
  
  public void a(EntityFishHook itemstack, SlotCrafting entityliving)
  {
    if ((entityliving instanceof NBTTagByte))
    {
      NBTTagByte entitysheep = (NBTTagByte)entityliving;
      int i = VillageDoorInfo.d(itemstack.i());
      if ((!entitysheep.q()) && (entitysheep.n_() != i))
      {
        entitysheep.b(i);
        itemstack.shake -= 1;
      }
    }
  }
  
  public static final String[] entityID = { "black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white" };
  public static final int[] slot = { 1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 2651799, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320 };
}
