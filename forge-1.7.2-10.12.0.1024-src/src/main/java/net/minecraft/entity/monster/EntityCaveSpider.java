package net.minecraft.entity.monster;

import java.util.Random;
import moapi.ModBooleanOption;
import moapi.ModMappedMultiOption;
import moapi.ModOptions;
import moapi.ModOptionsAPI;
import net.minecraft.command.CommandShowSeed;
import net.minecraft.crash.CallableJVMFlags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.INpc;
import net.minecraft.potion.PotionHealth;
import net.minecraft.server.dedicated.CallableType;
import net.minecraft.server.dedicated.DedicatedPlayerList;
import net.minecraft.src.Biome;
import net.minecraft.src.mod_AutoForest;
import net.minecraft.util.Icon;
import net.minecraft.util.LongHashMapEntry;

public class EntityCaveSpider
  extends Icon
{
  protected EntityCaveSpider(int i, int j)
  {
    super(i, j);
    float f = 0.4F;
    a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
  }
  
  public void a(EntityGolem world, int i, int j, int k, Random random)
  {
    if (world.isCollided) {
      return;
    }
    ModOptions options = ModOptionsAPI.getModOptions("NatureOverhaul").getSubOption("Tree Options");
    ModMappedMultiOption mo = (ModMappedMultiOption)options.getOption("TreeGrowthRate");
    
    int bound = ((Integer)mo.getValue()).intValue();
    
    super.a(world, i, j, k, random);
    if ((world.n(i, j + 1, k) >= 9) && ((bound == 0) || (random.nextInt(bound * 6) == 0)))
    {
      int l = world.e(i, j, k);
      if (((l & 0x8) == 0) && (bound > 0)) {
        world.f(i, j, k, l | 0x8);
      } else {
        growTree(world, i, j, k, random, false);
      }
    }
  }
  
  public int a(int i, int j)
  {
    j &= 0x3;
    if (j == 1) {
      return 63;
    }
    if (j == 2) {
      return 79;
    }
    return super.a(i, j);
  }
  
  public void c(EntityGolem world, int i, int j, int k, Random random)
  {
    growTree(world, i, j, k, random, true);
  }
  
  private void growTree(EntityGolem world, int i, int j, int k, Random random, boolean ignoreDeath)
  {
    ModOptions mo = ModOptionsAPI.getModOptions("NatureOverhaul");
    ModOptions saps = mo.getSubOption("Sapling Options");
    ModBooleanOption sapDeathOp = (ModBooleanOption)saps.getOption("SaplingDeath");
    

    int bigTreeRate = mod_AutoForest.getBiomeModifier(mod_AutoForest.getBiomeName(i, k), "BigTree");
    


    Object obj = null;
    


    int type = world.e(i, j, k) & 0x3;
    if (type == 1)
    {
      if (random.nextInt(3) == 0) {
        obj = new CallableType();
      } else {
        obj = new PotionHealth();
      }
    }
    else if (type == 2) {
      obj = new CallableJVMFlags();
    } else if (random.nextInt(100) < bigTreeRate) {
      obj = new INpc();
    } else if (mod_AutoForest.getBiomeAt(i, k) == Biome.SWAMPLAND) {
      obj = new CommandShowSeed();
    } else {
      obj = new EntityHanging();
    }
    world.g(i, j, k, 0);
    if (ignoreDeath)
    {
      if (!((DedicatedPlayerList)obj).a(world, random, i, j, k)) {
        world.b(i, j, k, this.bA, type);
      }
    }
    else if ((!starvedSapling(world, i, j, k)) && (!randomDeath(world, i, j, k, random)) && (!((DedicatedPlayerList)obj).a(world, random, i, j, k)) && (!((Boolean)sapDeathOp.getValue()).booleanValue())) {
      world.b(i, j, k, this.bA, type);
    }
  }
  
  protected int b(int i)
  {
    return i & 0x3;
  }
  
  private boolean starvedSapling(EntityGolem world, int i, int j, int k)
  {
    ModOptions mo = ModOptionsAPI.getModOptions("NatureOverhaul");
    ModOptions saps = mo.getSubOption("Sapling Options");
    boolean sapDeathOp = ((Boolean)((ModBooleanOption)saps.getOption("SaplingDeath")).getValue()).booleanValue();
    if (sapDeathOp)
    {
      int dist = mod_AutoForest.getBiomeModifier(mod_AutoForest.getBiomeName(i, k), "TreeGap");
      for (int x = i - dist; x <= dist + i; x++) {
        for (int z = k - dist; z <= dist + k; z++) {
          if (world.a(x, j, z) == LongHashMapEntry.K.bA) {
            return true;
          }
        }
      }
    }
    return false;
  }
  
  private boolean randomDeath(EntityGolem world, int i, int j, int k, Random random)
  {
    ModOptions mo = ModOptionsAPI.getModOptions("NatureOverhaul");
    ModOptions saps = mo.getSubOption("Sapling Options");
    boolean sapDeathOp = ((Boolean)((ModBooleanOption)saps.getOption("SaplingDeath")).getValue()).booleanValue();
    String biomeName = mod_AutoForest.getBiomeName(i, k);
    int deathRate = mod_AutoForest.getBiomeModifier(biomeName, "SaplingDeath");
    if ((sapDeathOp) && (random.nextInt(100) >= 100 - deathRate)) {
      return true;
    }
    return false;
  }
}
