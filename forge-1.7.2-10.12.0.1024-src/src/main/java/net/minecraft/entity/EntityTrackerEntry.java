package net.minecraft.entity;

import java.util.Random;
import moapi.ModBooleanOption;
import moapi.ModMappedMultiOption;
import moapi.ModOptions;
import net.minecraft.command.CommandSpreadPlayersPosition;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.network.packet.Packet19EntityAction;
import net.minecraft.potion.PotionAttackDamage;
import net.minecraft.src.adg;
import net.minecraft.src.mod_AutoForest;
import net.minecraft.util.Icon;
import net.minecraft.util.LongHashMapEntry;

public class EntityTrackerEntry
  extends Icon
{
  protected String growthModifierType = "GrassSpawn";
  
  protected EntityTrackerEntry(int i, int j)
  {
    super(i, j);
    float f = 0.4F;
    a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
    a(true);
  }
  
  public int a(int i, int j)
  {
    if (j == 1) {
      return this.bz;
    }
    if (j == 2) {
      return this.bz + 16 + 1;
    }
    if (j == 0) {
      return this.bz + 16;
    }
    return this.bz;
  }
  
  public int i()
  {
    double d = 0.5D;
    double d1 = 1.0D;
    return EntityWitch.a(d, d1);
  }
  
  public int c(int i)
  {
    if (i == 0) {
      return 16777215;
    }
    return Packet19EntityAction.c();
  }
  
  public void a(EntityGolem world, int i, int j, int k, Random random)
  {
    if (!world.isCollided)
    {
      ModOptions grass = mod_AutoForest.grass;
      boolean grow = ((Boolean)((ModBooleanOption)grass.getOption("GrassGrows")).getValue()).booleanValue();
      if (grow)
      {
        double growthRate = 1.0D / ((Integer)((ModMappedMultiOption)grass.getOption("GrassGrowthRate")).getValue()).intValue();
        
        attemptGrowth(world, i, j, k, growthRate);
      }
      boolean death = ((Boolean)mod_AutoForest.grassDeath.getValue()).booleanValue();
      double deathProb = 1.0D / (0.75D * ((Integer)((ModMappedMultiOption)grass.getOption("GrassDeathRate")).getValue()).intValue());
      if ((death) && (hasDied(world, i, j, k, deathProb))) {
        death(world, i, j, k);
      }
    }
  }
  
  public void grow(EntityGolem world, int i, int j, int k)
  {
    int scanSize = 2;
    int metadata = world.e(i, j, k);
    int id = a(metadata, world.w);
    if ((id >= 0) && (id < EntityMinecartContainer.f.length)) {
      for (int x = i - scanSize; x <= i + scanSize; x++) {
        for (int y = j - scanSize; y <= j + scanSize; y++) {
          for (int z = k - scanSize; z <= k + scanSize; z++) {
            if ((world.a(x, y, z) == 0) && (world.a(x, y - 1, z) == LongHashMapEntry.v.bA))
            {
              world.d(x, y, z, this.bA, 1);
              return;
            }
          }
        }
      }
    }
  }
  
  public int b(adg iblockaccess, int i, int j, int k)
  {
    int l = iblockaccess.e(i, j, k);
    if (l == 0) {
      return 16777215;
    }
    long l1 = i * 3129871 + k * 6129781 + j;
    l1 = l1 * l1 * 42317861L + l1 * 11L;
    i = (int)(i + ((l1 >> 14 & 0x1F) - 16L));
    j = (int)(j + ((l1 >> 19 & 0x1F) - 16L));
    k = (int)(k + ((l1 >> 24 & 0x1F) - 16L));
    double d = iblockaccess.a().b(i, k);
    double d1 = iblockaccess.a().c(i, k);
    return EntityWitch.a(d, d1);
  }
  
  public int a(int i, Random random)
  {
    if (random.nextInt(8) == 0) {
      return EntityMinecartContainer.T.br;
    }
    return -1;
  }
  
  public void a(EntityGolem world, EntityMinecartMobSpawner entityplayer, int i, int j, int k, int l)
  {
    if ((!world.isCollided) && (entityplayer.aj() != null) && (entityplayer.aj().nextStepDistance == EntityMinecartContainer.bf.br))
    {
      entityplayer.a(net.minecraft.network.packet.Packet201PlayerInfo.C[this.bA], 1);
      a(world, i, j, k, new EntityFishHook(LongHashMapEntry.Y, 1, l));
    }
    else
    {
      super.a(world, entityplayer, i, j, k, l);
    }
  }
}
