package net.minecraft.src;

import java.util.HashSet;
import java.util.Random;

import moapi.ModBooleanOption;
import moapi.ModMappedMultiOption;
import moapi.ModOptions;
import moapi.ModOptionsAPI;
import net.minecraft.client.stats.StatPlaceholder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.inventory.SlotMerchantResult;
import net.minecraft.network.packet.Packet60Explosion;
import net.minecraft.potion.PotionAttackDamage;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.LongHashMapEntry;
import rv;

public class mb
  extends LongHashMap
{
  private static final int MAX_TREE_HEIGHT = 16;
  
  protected mb(int i)
  {
    super(i, SlotMerchantResult.theMerchant);
    a(true);
    this.bz = 20;
  }
  
  public void a(EntityGolem world, int i, int j, int k, Random random)
  {
    if (!world.isCollided)
    {
      ModOptions mo = ModOptionsAPI.getModOptions("NatureOverhaul").getSubOption("Tree Options");
      boolean treeDeath = ((Boolean)((ModBooleanOption)mo.getOption("TreeDeath")).getValue()).booleanValue();
      
      int deathRate = ((Integer)((ModMappedMultiOption)mo.getOption("DeathRate")).getValue()).intValue();
      
      deathRate = (int)mod_AutoForest.applyBiomeModifier(deathRate, "TreeDeath", world, i, k);
      if ((treeDeath) && ((deathRate <= 0) || (random.nextInt(deathRate) == 0)) && (isTree(world, i, j, k)))
      {
        int lowestLogJ = getLowestLogJ(world, i, j, k);
        
        killTree(world, i, lowestLogJ, k);
      }
    }
  }
  
  public int f()
  {
    return 10;
  }
  
  public int a(boolean b)
  {
    return 1;
  }
  
  private boolean isTree(EntityGolem world, int i, int j, int k)
  {
    return isTree(world, i, j, k, false);
  }
  
  private boolean isTree(EntityGolem world, int i, int j, int k, boolean ignoreSelf)
  {
    int checked = 0;
    
    boolean groundFound = false;
    
    boolean topFound = false;
    
    boolean isNotTree = false;
    
    int leafLayersFound = 0;
    int curI = i;
    int curJ = j - 1;
    int curK = k;
    while ((checked <= 16) && (!groundFound) && (!isNotTree))
    {
      int blockBelowID = world.a(curI, curJ, curK);
      if (blockBelowID == LongHashMapEntry.K.bA)
      {
        curJ -= 1;
      }
      else if ((blockBelowID == LongHashMapEntry.w.bA) || (blockBelowID == LongHashMapEntry.v.bA))
      {
        groundFound = true;
        
        curJ += 1;
      }
      else
      {
        isNotTree = true;
      }
      checked++;
    }
    checked = 0;
    if ((!isNotTree) && (groundFound))
    {
      while ((checked <= 16) && (!topFound) && (!isNotTree))
      {
        int blockAboveID = world.a(curI, curJ, curK);
        if ((blockAboveID == LongHashMapEntry.K.bA) || ((curJ == j) && (ignoreSelf)))
        {
          if (allLeavesAround(world, curI, curJ, curK)) {
            leafLayersFound++;
          }
          curJ += 1;
        }
        else if (blockAboveID == LongHashMapEntry.L.bA)
        {
          topFound = true;
        }
        else
        {
          isNotTree = true;
        }
      }
      return (!isNotTree) && (topFound) && (leafLayersFound > 0);
    }
    return false;
  }
  
  private int killTree(EntityGolem world, int i, int j, int k)
  {
    return killTree(world, i, j, k, true);
  }
  
  private int killTree(EntityGolem world, int i, int j, int k, boolean treeDeath)
  {
    boolean ignoreSelf = world.a(i, j, k) == 0;
    int treeHeight = getTreeHeight(world, i, j, k);
    
    int removed = 0;
    if (ignoreSelf) {
      j++;
    }
    killLog(world, i, j, k, true);
    
    HashSet<Integer> flags = new HashSet();
    
    int[] base = { i, j, k };
    int[] block = { i, j + 1, k };
    flagBlock(block, flags);
    return 1 + scanAndFlag(world, base, block, flags, treeHeight);
  }
  
  private int scanAndFlag(rv world, int[] base, int[] block, HashSet<Integer> flags, int treeHeight)
  {
    int i = block[0];
    int j = block[1];
    int k = block[2];
    int removed = 0;
    for (int[] nBlock : neighbours(block))
    {
      int id = world.a(nBlock[0], nBlock[1], nBlock[2]);
      if ((inRange(nBlock, base, treeHeight)) && (!isBlockFlagged(nBlock, flags)) && ((id == LongHashMapEntry.K.bA) || (id == LongHashMapEntry.L.bA)))
      {
        flagBlock(nBlock, flags);
        removed += scanAndFlag(world, base, nBlock, flags, treeHeight);
      }
    }
    if ((world.a(i, j, k) == LongHashMapEntry.K.bA) && ((!isTree(world, i, j, k)) || ((i == base[0]) && (k == base[2]))))
    {
      killLog(world, block[0], block[1], block[2], true);
      removed++;
    }
    return removed;
  }
  
  private int[][] neighbours(int[] block)
  {
    int i = block[0];
    int j = block[1];
    int k = block[2];
    
    int[][] n = { { i + 1, j, k }, { i - 1, j, k }, { i, j + 1, k }, { i, j - 1, k }, { i, j, k + 1 }, { i, j, k - 1 } };
    







    return n;
  }
  
  private void flagBlock(int[] block, HashSet<Integer> flags)
  {
    int flag = makeFlag(block);
    
    flags.add(Integer.valueOf(flag));
  }
  
  private boolean isBlockFlagged(int[] block, HashSet<Integer> flags)
  {
    int flag = makeFlag(block);
    
    return flags.contains(Integer.valueOf(flag));
  }
  
  private int makeFlag(int[] block)
  {
    int iBits = 4;
    int jBits = 6;
    int kBits = 4;
    int i = block[0] % (int)Math.pow(2.0D, iBits);
    int j = block[1] % (int)Math.pow(2.0D, jBits);
    int k = block[2] % (int)Math.pow(2.0D, kBits);
    






    int flag = i << jBits + kBits ^ j << kBits ^ k;
    
    return flag;
  }
  
  private boolean inRange(int[] block, int[] base, int treeHeight)
  {
    int zDist;
    int zDist;
    if (treeHeight <= 5)
    {
      int xDist = 2;
      int yDist = 2;
      zDist = treeHeight + 2;
    }
    else
    {
      int xDist = (int)Math.ceil(2 * treeHeight / 3);
      int yDist = (int)Math.ceil(treeHeight * 1.75D);
      zDist = (int)Math.ceil(2 * treeHeight / 3);
    }
    if ((Math.abs(block[0] - base[0]) <= 6) && (Math.abs(block[2] - base[2]) <= 6)) {
      if ((block[1] >= base[1]) && (block[1] - base[1] <= treeHeight + 5)) {
        return true;
      }
    }
    return false;
  }
  
  private void killLog(EntityGolem world, int i, int j, int k, boolean treeDeath)
  {
    EntityFishHook blockWood = new EntityFishHook(this, 1, world.e(i, j, k));
    world.g(i, j, k, 0);
    

    Packet60Explosion ent = new Packet60Explosion(world, i, j, k, blockWood);
    world.a(ent);
  }
  
  private int getTreeHeight(EntityGolem world, int i, int j, int k)
  {
    int height = 1;
    int curJ = j - 1;
    while (world.a(i, curJ, k) == LongHashMapEntry.K.bA)
    {
      curJ--;
      height++;
    }
    curJ = j + 1;
    while (world.a(i, curJ, k) == LongHashMapEntry.K.bA)
    {
      curJ++;
      height++;
    }
    return height;
  }
  
  private int getLowestLogJ(EntityGolem world, int i, int j, int k)
  {
    while (world.a(i, j - 1, k) == LongHashMapEntry.K.bA) {
      j--;
    }
    return j;
  }
  
  private boolean allLeavesAround(EntityGolem world, int i, int j, int k)
  {
    return (world.a(i + 1, j, k) == LongHashMapEntry.L.bA) && (world.a(i - 1, j, k) == LongHashMapEntry.L.bA) && (world.a(i, j, k + 1) == LongHashMapEntry.L.bA) && (world.a(i, j, k - 1) == LongHashMapEntry.L.bA);
  }
  
  private void additionalToolDamage(EntityMinecartMobSpawner player, int damage)
  {
    EntityFishHook itemstack = player.aj();
    if (itemstack != null)
    {
      itemstack.a(damage - 1, player);
      if (itemstack.shake == 0) {
        player.ak();
      }
    }
  }
  
  public int a(int i, Random random)
  {
    return LongHashMapEntry.K.bA;
  }
  
  public void a(EntityGolem world, EntityMinecartMobSpawner entityplayer, int i, int j, int k, int l)
  {
    super.a(world, entityplayer, i, j, k, l);
    


    boolean lumberjack = ((Boolean)((ModBooleanOption)ModOptionsAPI.getModOptions("NatureOverhaul").getSubOption("Tree Options").getOption("Lumberjack")).getValue()).booleanValue();
    if ((!world.isCollided) && (lumberjack) && (isTree(world, i, j, k, true)))
    {
      EntityFishHook itemstack = entityplayer.aj();
      if (itemstack != null)
      {
        int id = itemstack.nextStepDistance;
        if ((id >= 0) && (id < EntityMinecartContainer.f.length) && ((EntityMinecartContainer.f[id] instanceof StatPlaceholder)))
        {
          int damage = killTree(world, i, j, k, false);
          additionalToolDamage(entityplayer, damage);
        }
      }
    }
  }
  
  public void b_(EntityGolem world, int i, int j, int k)
  {
    byte byte0 = 4;
    int l = byte0 + 1;
    if (world.b(i - l, j - l, k - l, i + l, j + l, k + l)) {
      for (int i1 = -byte0; i1 <= byte0; i1++) {
        for (int j1 = -byte0; j1 <= byte0; j1++) {
          for (int k1 = -byte0; k1 <= byte0; k1++)
          {
            int l1 = world.a(i + i1, j + j1, k + k1);
            if (l1 == LongHashMapEntry.L.bA)
            {
              int i2 = world.e(i + i1, j + j1, k + k1);
              if ((i2 & 0x8) == 0) {
                world.c(i + i1, j + j1, k + k1, i2 | 0x8);
              }
            }
          }
        }
      }
    }
  }
  
  public int a(int i, int j)
  {
    if (i == 1) {
      return 21;
    }
    if (i == 0) {
      return 21;
    }
    if (j == 1) {
      return 116;
    }
    return j != 2 ? 20 : 117;
  }
  
  protected int b(int i)
  {
    return i;
  }
}
