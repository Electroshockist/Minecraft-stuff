package net.minecraft.util;

import java.util.Random;

import moapi.ModBooleanOption;
import moapi.ModMappedMultiOption;
import moapi.ModOptions;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.inventory.SlotMerchantResult;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.src.BlockMortal;
import net.minecraft.src.mod_AutoForest;

public class Icon
  extends BlockMortal
{
  protected Icon(int i, int j)
  {
    super(i, SlotMerchantResult.j);
    this.bz = j;
    a(true);
    float f = 0.2F;
    a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
  }
  
  public boolean e(EntityGolem world, int i, int j, int k)
  {
    return (super.e(world, i, j, k)) && (b_(world.a(i, j - 1, k)));
  }
  
  protected boolean b_(int i)
  {
    return (i == LongHashMap.v.bA) || (i == LongHashMap.w.bA) || (i == LongHashMap.aB.bA);
  }
  
  public void a(EntityGolem world, int i, int j, int k, int l)
  {
    super.a(world, i, j, k, l);
    func_268_h(world, i, j, k);
  }
  
  protected String growthModifierType = "FlowerSpawn";
  
  public void a(EntityGolem world, int i, int j, int k, Random random)
  {
    if ((!world.isCollided) && (!(this instanceof ServerConfigurationManager)))
    {
      ModOptions flowers = mod_AutoForest.flowers;
      boolean grow = ((Boolean)((ModBooleanOption)flowers.getOption("FlowersGrow")).getValue()).booleanValue();
      if (grow)
      {
        double growthRate = 1.0D / ((Integer)((ModMappedMultiOption)flowers.getOption("FlowerGrowthRate")).getValue()).intValue();
        
        attemptGrowth(world, i, j, k, growthRate);
      }
      boolean death = ((Boolean)mod_AutoForest.flowerDeath.getValue()).booleanValue();
      double deathProb = 1.0D / (1.5D * ((Integer)((ModMappedMultiOption)flowers.getOption("FlowerDeathRate")).getValue()).intValue());
      if ((death) && (hasDied(world, i, j, k, deathProb))) {
        death(world, i, j, k);
      }
    }
    if (world.a(i, j, k) != 0) {
      func_268_h(world, i, j, k);
    }
  }
  
  protected final void func_268_h(EntityGolem world, int i, int j, int k)
  {
    if (!g(world, i, j, k))
    {
      g(world, i, j, k, world.e(i, j, k));
      world.g(i, j, k, 0);
    }
  }
  
  public boolean g(EntityGolem world, int i, int j, int k)
  {
    return ((world.m(i, j, k) >= 8) || (world.l(i, j, k))) && (b_(world.a(i, j - 1, k)));
  }
  
  public EntityAnimal c(EntityGolem world, int i, int j, int k)
  {
    return null;
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
