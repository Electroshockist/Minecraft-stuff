package net.minecraft.item;

import java.util.Random;

import moapi.ModBooleanOption;
import moapi.ModMappedMultiOption;
import moapi.ModOptions;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.inventory.SlotMerchantResult;
import net.minecraft.src.BlockMortal;
import net.minecraft.src.mod_AutoForest;

public class ItemLilyPad
  extends BlockMortal
{
	private static final String __OBFID = "CL_00000074";

    public ItemLilyPad(Block p_i45357_1_)
    {
        super(p_i45357_1_, false);
    }
  
  public void a(EntityGolem world, int i, int j, int k, Random random)
  {
    if (world.i(i, j + 1, k))
    {
      for (int l = 1; world.a(i, j - l, k) == this.bA; l++) {}
      if (l < 3)
      {
        int i1 = world.e(i, j, k);
        if (i1 == 15)
        {
          world.g(i, j + 1, k, this.bA);
          world.f(i, j, k, 0);
        }
        else
        {
          world.f(i, j, k, i1 + 1);
        }
      }
    }
    if (!world.isCollided)
    {
      ModOptions reed = mod_AutoForest.reed;
      boolean grow = ((Boolean)((ModBooleanOption)reed.getOption("ReedsGrow")).getValue()).booleanValue();
      if (grow)
      {
        double growthRate = 1.0D / ((Integer)((ModMappedMultiOption)reed.getOption("ReedGrowthRate")).getValue()).intValue();
        
        attemptGrowth(world, i, j, k, growthRate);
      }
      boolean death = ((Boolean)mod_AutoForest.reedDeath.getValue()).booleanValue();
      
      double deathProb = 1.0D / (4.5D * ((Integer)((ModMappedMultiOption)reed.getOption("ReedDeathRate")).getValue()).intValue());
      if ((death) && (hasDied(world, i, j, k, deathProb))) {
        death(world, i, j, k);
      }
    }
  }
  
  protected String growthModifierType = "ReedSpawn";
  protected String deathModifierName = "ReedDeath";
  
  public void death(EntityGolem world, int i, int j, int k)
  {
    int y = j;
    while (world.a(i, y + 1, k) == this.bA) {
      y += 1;
    }
    while (world.a(i, y, k) == this.bA)
    {
      world.g(i, y, k, 0);
      y--;
    }
  }
  
  public boolean e(EntityGolem world, int i, int j, int k)
  {
    int l = world.a(i, j - 1, k);
    if (l == this.bA) {
      return true;
    }
    if ((l != LongHashMapEntry.v.bA) && (l != LongHashMapEntry.w.bA) && (l != LongHashMapEntry.F.bA)) {
      return false;
    }
    if (world.f(i - 1, j - 1, k) == SlotMerchantResult.g) {
      return true;
    }
    if (world.f(i + 1, j - 1, k) == SlotMerchantResult.g) {
      return true;
    }
    if (world.f(i, j - 1, k - 1) == SlotMerchantResult.g) {
      return true;
    }
    return world.f(i, j - 1, k + 1) == SlotMerchantResult.g;
  }
  
  public void a(EntityGolem world, int i, int j, int k, int l)
  {
    h(world, i, j, k);
  }
  
  protected final void h(EntityGolem world, int i, int j, int k)
  {
    if (!g(world, i, j, k))
    {
      g(world, i, j, k, world.e(i, j, k));
      world.g(i, j, k, 0);
    }
  }
  
  public boolean g(EntityGolem world, int i, int j, int k)
  {
    return e(world, i, j, k);
  }
  
  public EntityAnimal c(EntityGolem world, int i, int j, int k)
  {
    return null;
  }
  
  public int a(int i, Random random)
  {
    return EntityMinecartContainer.aK.br;
  }
  
  public boolean a()
  {
    return false;
  }
  
  public boolean b()
  {
    return false;
  }
  
  public int d()
  {
    return 1;
  }
}
