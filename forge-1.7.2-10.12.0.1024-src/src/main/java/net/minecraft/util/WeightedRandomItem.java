package net.minecraft.util;

import java.util.Random;

import moapi.ModBooleanOption;
import moapi.ModMappedMultiOption;
import moapi.ModOptions;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.inventory.SlotMerchantResult;
import net.minecraft.src.BlockGrowable;
import net.minecraft.src.mod_AutoForest;

public class WeightedRandomItem
  extends BlockGrowable
{
  public WeightedRandomItem(int i, SlotMerchantResult material, int j, int k)
  {
    super(i, j, material);
    this.itemWeight = k;
    a(true);
  }
  
  protected String growthModifierType = "ShroomSpawn";
  protected String deathModifierName = "ShroomDeath";
  private int itemWeight;
  
  public void a(EntityGolem world, int i, int j, int k, Random random)
  {
    if (!world.isCollided)
    {
      ModOptions shrooms = mod_AutoForest.shrooms;
      boolean grow = ((Boolean)((ModBooleanOption)shrooms.getOption("ShroomsGrow")).getValue()).booleanValue();
      if ((grow) && (world.a(i, j + 1, k) == 0))
      {
        double growthRate = 1.0D / ((Integer)((ModMappedMultiOption)shrooms.getOption("ShroomGrowthRate")).getValue()).intValue();
        
        attemptGrowth(world, i, j, k, growthRate);
      }
    }
  }
  
  public int a(int i, int j)
  {
    if ((j == 10) && (i > 1)) {
      return this.itemWeight - 1;
    }
    if ((j >= 1) && (j <= 9) && (i == 1)) {
      return this.itemWeight - 16 - this.itemWeight;
    }
    if ((j >= 1) && (j <= 3) && (i == 2)) {
      return this.itemWeight - 16 - this.itemWeight;
    }
    if ((j >= 7) && (j <= 9) && (i == 3)) {
      return this.itemWeight - 16 - this.itemWeight;
    }
    if (((j == 1) || (j == 4) || (j == 7)) && (i == 4)) {
      return this.itemWeight - 16 - this.itemWeight;
    }
    if (((j == 3) || (j == 6) || (j == 9)) && (i == 5)) {
      return this.itemWeight - 16 - this.itemWeight;
    }
    return this.itemWeight;
  }
  
  public int a(Random b)
  {
    int i = b.nextInt(10) - 7;
    if (i < 0) {
      i = 0;
    }
    return i;
  }
  
  public int a(int i, Random random)
  {
    return LongHashMap.ag.bA + this.itemWeight;
  }
}
