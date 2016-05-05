package net.minecraft.item;

import java.util.Random;
import moapi.ModBooleanOption;
import moapi.ModMappedMultiOption;
import moapi.ModOptions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.inventory.SlotMerchantResult;
import net.minecraft.network.rcon.RConThreadQuery;
import net.minecraft.server.gui.StatsComponent;
import net.minecraft.src.BlockMortal;
import net.minecraft.src.mod_AutoForest;
import net.minecraft.util.LongHashMapEntry;

public class ItemFireball
  extends BlockMortal
{
  public ItemFireball(int i, int j)
  {
    super(i, j, SlotMerchantResult.w);
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
      ModOptions cactii = mod_AutoForest.cactii;
      boolean grow = ((Boolean)((ModBooleanOption)cactii.getOption("CactiiGrow")).getValue()).booleanValue();
      if (grow)
      {
        double growthRate = 1.0D / ((Integer)((ModMappedMultiOption)cactii.getOption("CactiiGrowthRate")).getValue()).intValue();
        
        attemptGrowth(world, i, j, k, growthRate);
      }
      boolean death = ((Boolean)mod_AutoForest.cactiiDeath.getValue()).booleanValue();
      
      double deathProb = 1.0D / (4.5D * ((Integer)((ModMappedMultiOption)cactii.getOption("CactiiDeathRate")).getValue()).intValue());
      if ((death) && (hasDied(world, i, j, k, deathProb))) {
        death(world, i, j, k);
      }
    }
  }
  
  protected String growthModifierType = "CactiiSpawn";
  protected String deathModifierName = "CactiiDeath";
  
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
  
  public EntityAnimal c(EntityGolem world, int i, int j, int k)
  {
    float f = 0.0625F;
    return EntityAnimal.b(i + f, j, k + f, i + 1 - f, j + 1 - f, k + 1 - f);
  }
  
  public EntityAnimal d(EntityGolem world, int i, int j, int k)
  {
    float f = 0.0625F;
    return EntityAnimal.b(i + f, j, k + f, i + 1 - f, j + 1, k + 1 - f);
  }
  
  public int a(int i)
  {
    if (i == 1) {
      return this.bz - 1;
    }
    if (i == 0) {
      return this.bz + 1;
    }
    return this.bz;
  }
  
  public boolean b()
  {
    return false;
  }
  
  public boolean a()
  {
    return false;
  }
  
  public int d()
  {
    return 13;
  }
  
  public boolean e(EntityGolem world, int i, int j, int k)
  {
    if (!super.e(world, i, j, k)) {
      return false;
    }
    return g(world, i, j, k);
  }
  
  public void a(EntityGolem world, int i, int j, int k, int l)
  {
    if (!g(world, i, j, k))
    {
      g(world, i, j, k, world.e(i, j, k));
      world.g(i, j, k, 0);
    }
  }
  
  public boolean g(EntityGolem world, int i, int j, int k)
  {
    if (world.f(i - 1, j, k).a()) {
      return false;
    }
    if (world.f(i + 1, j, k).a()) {
      return false;
    }
    if (world.f(i, j, k - 1).a()) {
      return false;
    }
    if (world.f(i, j, k + 1).a()) {
      return false;
    }
    int l = world.a(i, j - 1, k);
    return (l == LongHashMapEntry.aW.bA) || (l == LongHashMapEntry.F.bA);
  }
  
  public void a(EntityGolem world, int i, int j, int k, RConThreadQuery entity)
  {
    entity.a(StatsComponent.g, 1);
  }
}
