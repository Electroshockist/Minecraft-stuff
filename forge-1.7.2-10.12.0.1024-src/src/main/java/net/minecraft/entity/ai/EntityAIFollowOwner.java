package net.minecraft.entity.ai;

import java.util.Random;
import moapi.ModBooleanOption;
import moapi.ModMappedMultiOption;
import moapi.ModMultiOption;
import moapi.ModOptions;
import moapi.ModOptionsAPI;
import net.minecraft.command.CommandServerSay;
import net.minecraft.command.CommandSpreadPlayersPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.inventory.SlotMerchantResult;
import net.minecraft.network.packet.Packet19EntityAction;
import net.minecraft.network.packet.Packet60Explosion;
import net.minecraft.network.rcon.RConThreadQuery;
import net.minecraft.src.Biome;
import net.minecraft.src.Growable;
import net.minecraft.src.adg;
import net.minecraft.src.mod_AutoForest;
import net.minecraft.util.LongHashMapEntry;

public class EntityAIFollowOwner
  extends CommandServerSay
  implements Growable
{
  private int minDist;
  int[] maxDist;
  
  protected EntityAIFollowOwner(int i, int j)
  {
    super(i, j, SlotMerchantResult.i, false);
    this.minDist = j;
    a(true);
  }
  
  public int i()
  {
    double d = 0.5D;
    double d1 = 1.0D;
    return Packet19EntityAction.a(d, d1);
  }
  
  public int c(int i)
  {
    if ((i & 0x1) == 1) {
      return Packet19EntityAction.getPacketSize();
    }
    if ((i & 0x2) == 2) {
      return Packet19EntityAction.b();
    }
    return Packet19EntityAction.c();
  }
  
  public int b(adg iblockaccess, int i, int j, int k)
  {
    int l = iblockaccess.e(i, j, k);
    if ((l & 0x1) == 1) {
      return Packet19EntityAction.getPacketSize();
    }
    if ((l & 0x2) == 2) {
      return Packet19EntityAction.b();
    }
    double d = iblockaccess.a().b(i, k);
    double d1 = iblockaccess.a().c(i, k);
    return Packet19EntityAction.a(d, d1);
  }
  
  public void b_(EntityGolem world, int i, int j, int k)
  {
    int l = 1;
    int i1 = l + 1;
    if (world.b(i - i1, j - i1, k - i1, i + i1, j + i1, k + i1)) {
      for (int j1 = -l; j1 <= l; j1++) {
        for (int k1 = -l; k1 <= l; k1++) {
          for (int l1 = -l; l1 <= l; l1++)
          {
            int i2 = world.a(i + j1, j + k1, k + l1);
            if (i2 == LongHashMapEntry.L.bA)
            {
              int j2 = world.e(i + j1, j + k1, k + l1);
              world.c(i + j1, j + k1, k + l1, j2 | 0x8);
            }
          }
        }
      }
    }
  }
  
  public void a(EntityGolem world, int i, int j, int k, Random random)
  {
    if (world.isCollided) {
      return;
    }
    int l = world.e(i, j, k);
    if (((l & 0x8) != 0) && ((l & 0x4) == 0))
    {
      byte byte0 = 4;
      int i1 = byte0 + 1;
      byte byte1 = 32;
      int j1 = byte1 * byte1;
      int k1 = byte1 / 2;
      if (this.maxDist == null) {
        this.maxDist = new int[byte1 * byte1 * byte1];
      }
      if (world.b(i - i1, j - i1, k - i1, i + i1, j + i1, k + i1))
      {
        for (int l1 = -byte0; l1 <= byte0; l1++) {
          for (int k2 = -byte0; k2 <= byte0; k2++) {
            for (int i3 = -byte0; i3 <= byte0; i3++)
            {
              int k3 = world.a(i + l1, j + k2, k + i3);
              if (k3 == LongHashMapEntry.K.bA) {
                this.maxDist[((l1 + k1) * j1 + (k2 + k1) * byte1 + (i3 + k1))] = 0;
              } else if (k3 == LongHashMapEntry.L.bA) {
                this.maxDist[((l1 + k1) * j1 + (k2 + k1) * byte1 + (i3 + k1))] = -2;
              } else {
                this.maxDist[((l1 + k1) * j1 + (k2 + k1) * byte1 + (i3 + k1))] = -1;
              }
            }
          }
        }
        for (int i2 = 1; i2 <= 4; i2++) {
          for (int l2 = -byte0; l2 <= byte0; l2++) {
            for (int j3 = -byte0; j3 <= byte0; j3++) {
              for (int l3 = -byte0; l3 <= byte0; l3++) {
                if (this.maxDist[((l2 + k1) * j1 + (j3 + k1) * byte1 + (l3 + k1))] == i2 - 1)
                {
                  if (this.maxDist[((l2 + k1 - 1) * j1 + (j3 + k1) * byte1 + (l3 + k1))] == -2) {
                    this.maxDist[((l2 + k1 - 1) * j1 + (j3 + k1) * byte1 + (l3 + k1))] = i2;
                  }
                  if (this.maxDist[((l2 + k1 + 1) * j1 + (j3 + k1) * byte1 + (l3 + k1))] == -2) {
                    this.maxDist[((l2 + k1 + 1) * j1 + (j3 + k1) * byte1 + (l3 + k1))] = i2;
                  }
                  if (this.maxDist[((l2 + k1) * j1 + (j3 + k1 - 1) * byte1 + (l3 + k1))] == -2) {
                    this.maxDist[((l2 + k1) * j1 + (j3 + k1 - 1) * byte1 + (l3 + k1))] = i2;
                  }
                  if (this.maxDist[((l2 + k1) * j1 + (j3 + k1 + 1) * byte1 + (l3 + k1))] == -2) {
                    this.maxDist[((l2 + k1) * j1 + (j3 + k1 + 1) * byte1 + (l3 + k1))] = i2;
                  }
                  if (this.maxDist[((l2 + k1) * j1 + (j3 + k1) * byte1 + (l3 + k1 - 1))] == -2) {
                    this.maxDist[((l2 + k1) * j1 + (j3 + k1) * byte1 + (l3 + k1 - 1))] = i2;
                  }
                  if (this.maxDist[((l2 + k1) * j1 + (j3 + k1) * byte1 + (l3 + k1 + 1))] == -2) {
                    this.maxDist[((l2 + k1) * j1 + (j3 + k1) * byte1 + (l3 + k1 + 1))] = i2;
                  }
                }
              }
            }
          }
        }
      }
      int j2 = this.maxDist[(k1 * j1 + k1 * byte1 + k1)];
      if (j2 >= 0) {
        world.c(i, j, k, l & 0xFFFFFFF7);
      } else {
        h(world, i, j, k);
      }
    }
    if (!world.isCollided) {
      attemptGrowth(world, i, j, k);
    }
  }
  
  protected boolean attemptGrowth(EntityGolem world, int i, int j, int k, double prob)
  {
    return attemptGrowth(world, i, j, k);
  }
  
  private boolean attemptGrowth(EntityGolem world, int i, int j, int k)
  {
    ModOptions mo = mod_AutoForest.options;
    

    boolean growSaps = ((Integer)mod_AutoForest.growthType.getValue()).intValue() % 2 == 1;
    boolean appleGrowth = ((Boolean)((ModBooleanOption)mo.getSubOption("Tree Options").getOption("ApplesGrow")).getValue()).booleanValue();
    
    boolean cocoaGrowth = ((Boolean)((ModBooleanOption)mo.getSubOption("Tree Options").getOption("CocoaGrows")).getValue()).booleanValue();
    


    double sapFreq = getSaplingFreq(world, i, j, k);
    
    double appleFreq = getAppleFreq(world, i, j, k);
    
    double cocoaFreq = getCocoaFreq(world, i, j, k);
    if ((growSaps) && (growth(sapFreq)))
    {
      if (world.a(i, j + 1, k) == 0)
      {
        emitItem(world, i, j + 1, k, new EntityFishHook(LongHashMapEntry.z, 1, world.e(i, j, k) % 4));
        
        return true;
      }
    }
    else if ((appleGrowth) && (growth(appleFreq)))
    {
      if ((world.a(i, j - 1, k) == 0) && (appleCanGrow(world, i, j, k)))
      {
        emitItem(world, i, j - 1, k, new EntityFishHook(EntityMinecartContainer.k));
        return true;
      }
    }
    else if ((cocoaGrowth) && (growth(cocoaFreq)))
    {
      Biome[] biomes = { Biome.FOREST };
      if ((world.a(i, j - 1, k) == 0) && (canGrow(world, i, j, k, biomes)))
      {
        emitItem(world, i, j - 1, k, new EntityFishHook(EntityMinecartContainer.aX, 1, 3));
        return true;
      }
    }
    return false;
  }
  
  private void emitItem(EntityGolem world, int i, int j, int k, EntityFishHook item)
  {
    Packet60Explosion entityitem = new Packet60Explosion(world, i, j, k, item, true);
    world.a(entityitem);
  }
  
  public void grow(EntityGolem world, int i, int j, int k)
  {
    Random rand = new Random();
    int randInt = rand.nextInt(100);
    
    Biome[] biomes = { Biome.FOREST };
    if ((canGrow(world, i, j, k, biomes)) && (randInt < 10)) {
      emitItem(world, i, j - 1, k, new EntityFishHook(EntityMinecartContainer.aX, 1, 3));
    } else if ((appleCanGrow(world, i, j, k)) && (randInt < 25)) {
      emitItem(world, i, j - 1, k, new EntityFishHook(EntityMinecartContainer.k));
    } else {
      emitItem(world, i, j + 1, k, new EntityFishHook(LongHashMapEntry.z, 1, world.e(i, j, k) % 4));
    }
  }
  
  private boolean canGrow(EntityGolem world, int i, int j, int k, Biome[] biomes)
  {
    Biome curbiome = Biome.getBiomeFromString(mod_AutoForest.getBiomeName(i, k));
    for (Biome biome : biomes) {
      if (curbiome.equals(biome)) {
        return true;
      }
    }
    return false;
  }
  
  private boolean appleCanGrow(EntityGolem world, int i, int j, int k)
  {
    String biome = mod_AutoForest.getBiomeName(i, k);
    
    return (biome.equals("Forest")) || (biome.equals("Swampland")) || (biome.equals("Rainforest")) || (biome.equals("Seasonal Forest")) || (biome.equals("Shrubland"));
  }
  
  protected boolean growth(double freq)
  {
    double tmp = Math.random();
    



    return tmp < freq;
  }
  
  private double getSaplingFreq(EntityGolem world, int i, int j, int k)
  {
    double freq = 0.001D;
    ModMultiOption o = (ModMultiOption)ModOptionsAPI.getModOptions("NatureOverhaul").getSubOption("Sapling Options").getOption("GrowthRate");
    


    String v = (String)o.getValue();
    if (v.equals("SUPERSLOW")) {
      freq = 2.31481481E-005D;
    } else if (v.equals("SLOW")) {
      freq = 9.259259260000001E-005D;
    } else if (v.equals("AVERAGE")) {
      freq = 0.000185185185D;
    } else if (v.equals("FAST")) {
      freq = 0.000555555556D;
    } else if (v.equals("SUPERFAST")) {
      freq = 0.0037037037D;
    } else if (v.equals("INSANE")) {
      freq = 9.5D;
    }
    return mod_AutoForest.applyBiomeModifier(freq, "SaplingSpawn", world, i, k);
  }
  
  private double getAppleFreq(EntityGolem world, int i, int j, int k)
  {
    ModMappedMultiOption o = (ModMappedMultiOption)ModOptionsAPI.getModOptions("NatureOverhaul").getSubOption("Tree Options").getOption("AppleGrowthRate");
    


    double freq = 1.0D / ((Integer)o.getValue()).intValue();
    
    freq = mod_AutoForest.applyBiomeModifier(freq, "SaplingSpawn", world, i, k);
    
    return freq;
  }
  
  private double getCocoaFreq(EntityGolem world, int i, int j, int k)
  {
    ModMappedMultiOption o = (ModMappedMultiOption)ModOptionsAPI.getModOptions("NatureOverhaul").getSubOption("Tree Options").getOption("CocoaGrowthRate");
    


    return 1.0D / ((Integer)o.getValue()).intValue();
  }
  
  private void h(EntityGolem world, int i, int j, int k)
  {
    if (!world.isCollided)
    {
      boolean appleGrowth = ((Boolean)((ModBooleanOption)ModOptionsAPI.getModOptions("NatureOverhaul").getSubOption("Tree Options").getOption("ApplesGrow")).getValue()).booleanValue();
      



      boolean growSaps = ((Integer)mod_AutoForest.growthType.getValue()).intValue() > 1;
      if (growSaps) {
        if (growth(getSaplingFreq(world, i, j, k) * 50.0D)) {
          emitItem(world, i, j, k, new EntityFishHook(LongHashMapEntry.z, 1, world.e(i, j, k) % 4));
        }
      }
      if ((appleGrowth) && 
        (growth(getAppleFreq(world, i, j, k)))) {
        emitItem(world, i, j, k, new EntityFishHook(EntityMinecartContainer.k));
      }
    }
    else
    {
      g(world, i, j, k, world.e(i, j, k));
    }
    world.g(i, j, k, 0);
  }
  
  public int a(Random random)
  {
    return random.nextInt(20) != 0 ? 0 : 1;
  }
  
  public int a(int i, Random random)
  {
    return LongHashMapEntry.z.bA;
  }
  
  public void a(EntityGolem world, EntityMinecartMobSpawner entityplayer, int i, int j, int k, int l)
  {
    if ((!world.isCollided) && (entityplayer.aj() != null) && (entityplayer.aj().nextStepDistance == EntityMinecartContainer.bf.br))
    {
      entityplayer.a(net.minecraft.network.packet.Packet201PlayerInfo.C[this.bA], 1);
      a(world, i, j, k, new EntityFishHook(LongHashMapEntry.L.bA, 1, l & 0x3));
    }
    else
    {
      super.a(world, entityplayer, i, j, k, l);
    }
  }
  
  protected int b(int i)
  {
    return i & 0x3;
  }
  
  public boolean shouldExecute()
  {
    return !this.theWorld;
  }
  
  public int a(int i, int j)
  {
    if ((j & 0x3) == 1) {
      return this.bz + 80;
    }
    return this.bz;
  }
  
  public void b(boolean flag)
  {
    this.theWorld = flag;
    this.bz = (this.minDist + (flag ? 0 : 1));
  }
  
  public void b(EntityGolem world, int i, int j, int k, RConThreadQuery entity)
  {
    super.b(world, i, j, k, entity);
  }
}
