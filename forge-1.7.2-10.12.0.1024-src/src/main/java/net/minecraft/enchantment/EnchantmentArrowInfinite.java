package net.minecraft.enchantment;

import java.util.Random;
import moapi.ModBooleanOption;
import moapi.ModMappedMultiOption;
import moapi.ModMappedOption;
import moapi.ModOptions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.potion.PotionAttackDamage;
import net.minecraft.src.mod_AutoForest;
import net.minecraft.util.Icon;
import net.minecraft.util.LongHashMapEntry;

public class EnchantmentArrowInfinite
  extends Icon
{
  protected String growthModifierType = "ShroomSpawn";
  protected String deathModifierName = "ShroomDeath";
  
  protected EnchantmentArrowInfinite(int i, int j)
  {
    super(i, j);
    float f = 0.2F;
    a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
    a(true);
  }
  
  public void a(EntityGolem world, int i, int j, int k, Random random)
  {
    boolean keepDefaultSpread = ((Boolean)mod_AutoForest.defaultShroomSpread.getValue()).booleanValue();
    if ((keepDefaultSpread) && (random.nextInt(100) == 0))
    {
      byte byte0 = 4;
      int l = 5;
      for (int i1 = i - byte0; i1 <= i + byte0; i1++) {
        for (int k1 = k - byte0; k1 <= k + byte0; k1++) {
          for (int i2 = j - 1; i2 <= j + 1; i2++) {
            if (world.a(i1, i2, k1) == this.bA)
            {
              l--;
              if (l <= 0) {
                return;
              }
            }
          }
        }
      }
      int j1 = i + random.nextInt(3) - 1;
      int l1 = j + random.nextInt(2) - random.nextInt(2);
      int j2 = k + random.nextInt(3) - 1;
      for (int k2 = 0; k2 < 4; k2++)
      {
        if ((world.i(j1, l1, j2)) && (g(world, j1, l1, j2)))
        {
          i = j1;
          j = l1;
          k = j2;
        }
        j1 = i + random.nextInt(3) - 1;
        l1 = j + random.nextInt(2) - random.nextInt(2);
        j2 = k + random.nextInt(3) - 1;
      }
      if ((world.i(j1, l1, j2)) && (g(world, j1, l1, j2))) {
        world.g(j1, l1, j2, this.bA);
      }
    }
    if (!world.isCollided)
    {
      ModOptions shrooms = mod_AutoForest.shrooms;
      boolean grow = ((Boolean)mod_AutoForest.shroomTreesGrow.getValue()).booleanValue();
      if (grow)
      {
        double growthRate = 1.0D / ((Integer)mod_AutoForest.shroomTreeGrowth.getValue()).intValue();
        attemptGrowth(world, i, j, k, growthRate);
      }
      boolean death = ((Boolean)mod_AutoForest.shroomDeath.getValue()).booleanValue();
      double deathProb = 1.0D / (1.5D * ((Integer)((ModMappedMultiOption)shrooms.getOption("ShroomDeathRate")).getValue()).intValue());
      if ((death) && (hasDied(world, i, j, k, deathProb))) {
        death(world, i, j, k);
      }
    }
  }
  
  public void grow(EntityGolem world, int i, int j, int k)
  {
    c(world, i, j, k, world.w);
  }
  
  protected boolean b_(int i)
  {
    return LongHashMapEntry.o[i];
  }
  
  public boolean g(EntityGolem world, int i, int j, int k)
  {
    if (j >= 0)
    {
      world.getClass();
      if (j < 128) {}
    }
    else
    {
      return false;
    }
    return (world.m(i, j, k) < 13) && (b_(world.a(i, j - 1, k)));
  }
  
  public boolean c(EntityGolem world, int i, int j, int k, Random random)
  {
    int l = world.a(i, j - 1, k);
    if ((l != LongHashMapEntry.w.bA) && (l != LongHashMapEntry.v.bA)) {
      return false;
    }
    int i1 = world.e(i, j, k);
    world.d(i, j, k, 0);
    InventoryBasic worldgenbigmushroom = null;
    if (this.bA == LongHashMapEntry.ag.bA) {
      worldgenbigmushroom = new InventoryBasic(0);
    } else if (this.bA == LongHashMapEntry.ah.bA) {
      worldgenbigmushroom = new InventoryBasic(1);
    }
    if ((worldgenbigmushroom == null) || (!worldgenbigmushroom.a(world, random, i, j, k)))
    {
      world.b(i, j, k, this.bA, i1);
      return false;
    }
    return true;
  }
}
