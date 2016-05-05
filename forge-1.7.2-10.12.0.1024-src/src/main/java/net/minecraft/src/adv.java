package net.minecraft.src;

public class adv {

}
package net.minecraft.src;

import java.util.Random;
import moapi.ModBooleanOption;
import net.minecraft.command.PlayerSelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTrackerEntry;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.inventory.SlotMerchantResult;
import net.minecraft.util.LongHashMapEntry;

public class adv
  extends LongHashMapEntry
{
  private int[] a;
  private int[] b;
  
  protected adv(int i, int j)
  {
    super(i, j, SlotMerchantResult.n);
    this.a = new int[256];
    this.b = new int[256];
    a(true);
  }
  
  public void l()
  {
    a(LongHashMapEntry.y.bA, 5, 20);
    a(LongHashMapEntry.ba.bA, 5, 20);
    a(LongHashMapEntry.au.bA, 5, 20);
    a(LongHashMapEntry.K.bA, 5, 5);
    a(LongHashMapEntry.L.bA, 30, 60);
    a(LongHashMapEntry.ao.bA, 30, 20);
    a(LongHashMapEntry.an.bA, 15, 100);
    a(LongHashMapEntry.Y.bA, 60, 100);
    a(LongHashMapEntry.ac.bA, 30, 60);
    a(LongHashMapEntry.bv.bA, 15, 100);
  }
  
  private void a(int i, int j, int k)
  {
    this.a[i] = j;
    this.b[i] = k;
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
    return 3;
  }
  
  public int a(Random random)
  {
    return 0;
  }
  
  public int f()
  {
    return 40;
  }
  
  public void a(EntityGolem world, int i, int j, int k, Random random)
  {
    boolean infFire = (!world.isCollided) && (((Boolean)mod_AutoForest.infiniteFire.getValue()).booleanValue());
    


    boolean flag = world.a(i, j - 1, k) == LongHashMapEntry.bc.bA;
    if (!e(world, i, j, k)) {
      world.g(i, j, k, 0);
    }
    if ((!flag) && (world.C()) && ((world.t(i, j, k)) || (world.t(i - 1, j, k)) || (world.t(i + 1, j, k)) || (world.t(i, j, k - 1)) || (world.t(i, j, k + 1))))
    {
      world.g(i, j, k, 0);
      return;
    }
    int l = world.e(i, j, k);
    if ((!infFire) && (l < 15)) {
      world.c(i, j, k, l + random.nextInt(3) / 2);
    } else if ((infFire) && (l < 15)) {
      world.c(i, j, k, l + 1);
    }
    world.a(i, j, k, this.bA, f());
    if ((!flag) && (!h(world, i, j, k)))
    {
      if ((!world.h(i, j - 1, k)) || (l > 3)) {
        world.g(i, j, k, 0);
      }
      return;
    }
    if ((!flag) && (!f(world, i, j - 1, k)) && (l == 15) && (random.nextInt(4) == 0))
    {
      world.g(i, j, k, 0);
      return;
    }
    a(world, i + 1, j, k, 300, random, l);
    a(world, i - 1, j, k, 300, random, l);
    a(world, i, j - 1, k, 250, random, l);
    a(world, i, j + 1, k, 250, random, l);
    a(world, i, j, k - 1, 300, random, l);
    a(world, i, j, k + 1, 300, random, l);
    for (int i1 = i - 1; i1 <= i + 1; i1++) {
      for (int j1 = k - 1; j1 <= k + 1; j1++) {
        for (int k1 = j - 1; k1 <= j + 4; k1++) {
          if ((i1 != i) || (k1 != j) || (j1 != k))
          {
            int l1 = 100;
            if (k1 > j + 1) {
              l1 += (k1 - (j + 1)) * 100;
            }
            int i2 = i(world, i1, k1, j1);
            if (i2 > 0) {
              if (!infFire)
              {
                int j2 = (i2 + 40) / (l + 30);
                if ((j2 > 0) && (random.nextInt(l1) <= j2) && ((!world.C()) || (!world.t(i1, k1, j1))) && (!world.t(i1 - 1, k1, k)) && (!world.t(i1 + 1, k1, j1)) && (!world.t(i1, k1, j1 - 1)) && (!world.t(i1, k1, j1 + 1)))
                {
                  int k2 = l + random.nextInt(5) / 4;
                  if (k2 > 15) {
                    k2 = 15;
                  }
                  world.d(i1, k1, j1, this.bA, k2);
                }
              }
              else if ((random.nextInt(l1) <= i2) && ((!world.C()) || (!world.t(i1, k1, j1))) && (!world.t(i1 - 1, k1, k)) && (!world.t(i1 + 1, k1, j1)) && (!world.t(i1, k1, j1 - 1)) && (!world.t(i1, k1, j1 + 1)))
              {
                world.g(i1, k1, j1, this.bA);
              }
            }
          }
        }
      }
    }
    if ((infFire) && (l == 15))
    {
      a(world, i + 1, j, k, 1, random, l);
      a(world, i - 1, j, k, 1, random, l);
      a(world, i, j - 1, k, 1, random, l);
      a(world, i, j + 1, k, 1, random, l);
      a(world, i, j, k - 1, 1, random, l);
      a(world, i, j, k + 1, 1, random, l);
    }
  }
  
  private void a(EntityGolem world, int i, int j, int k, int l, Random random, int i1)
  {
    boolean infFire = (!world.isCollided) && (((Boolean)mod_AutoForest.infiniteFire.getValue()).booleanValue());
    int j1 = this.b[world.a(i, j, k)];
    if (random.nextInt(l) < j1)
    {
      boolean flag = world.a(i, j, k) == LongHashMapEntry.an.bA;
      if ((!infFire) && (random.nextInt(i1 + 10) < 5) && (!world.t(i, j, k)))
      {
        int k1 = i1 + random.nextInt(5) / 4;
        if (k1 > 15) {
          k1 = 15;
        }
        world.d(i, j, k, this.bA, k1);
      }
      else if ((infFire) && (random.nextInt(2) == 0) && (!world.t(i, j, k)))
      {
        world.g(i, j, k, this.bA);
      }
      else
      {
        world.g(i, j, k, 0);
      }
      if (flag) {
        LongHashMapEntry.an.b(world, i, j, k, 1);
      }
    }
  }
  
  private boolean h(EntityGolem world, int i, int j, int k)
  {
    if (f(world, i + 1, j, k)) {
      return true;
    }
    if (f(world, i - 1, j, k)) {
      return true;
    }
    if (f(world, i, j - 1, k)) {
      return true;
    }
    if (f(world, i, j + 1, k)) {
      return true;
    }
    if (f(world, i, j, k - 1)) {
      return true;
    }
    return f(world, i, j, k + 1);
  }
  
  private int i(EntityGolem world, int i, int j, int k)
  {
    int l = 0;
    if (!world.i(i, j, k)) {
      return 0;
    }
    l = f(world, i + 1, j, k, l);
    l = f(world, i - 1, j, k, l);
    l = f(world, i, j - 1, k, l);
    l = f(world, i, j + 1, k, l);
    l = f(world, i, j, k - 1, l);
    l = f(world, i, j, k + 1, l);
    return l;
  }
  
  public boolean j()
  {
    return false;
  }
  
  public boolean f(adg iblockaccess, int i, int j, int k)
  {
    return this.a[iblockaccess.a(i, j, k)] > 0;
  }
  
  public int f(EntityGolem world, int i, int j, int k, int l)
  {
    int i1 = this.a[world.a(i, j, k)];
    if (i1 > l) {
      return i1;
    }
    return l;
  }
  
  public boolean e(EntityGolem world, int i, int j, int k)
  {
    return (world.h(i, j - 1, k)) || (h(world, i, j, k));
  }
  
  public void a(EntityGolem world, int i, int j, int k, int l)
  {
    if ((!world.h(i, j - 1, k)) && (!h(world, i, j, k)))
    {
      world.g(i, j, k, 0);
      return;
    }
  }
  
  public void a(EntityGolem world, int i, int j, int k)
  {
    if ((world.a(i, j - 1, k) == LongHashMapEntry.aq.bA) && (LongHashMapEntry.bf.a_(world, i, j, k))) {
      return;
    }
    if ((!world.h(i, j - 1, k)) && (!h(world, i, j, k)))
    {
      world.g(i, j, k, 0);
      return;
    }
    world.a(i, j, k, this.bA, f());
  }
  
  public void b(EntityGolem world, int i, int j, int k, Random random)
  {
    if (random.nextInt(24) == 0) {
      world.a(i + 0.5F, j + 0.5F, k + 0.5F, "fire.fire", 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F);
    }
    if ((world.h(i, j - 1, k)) || (LongHashMapEntry.as.f(world, i, j - 1, k)))
    {
      for (int l = 0; l < 3; l++)
      {
        float f = i + random.nextFloat();
        float f6 = j + random.nextFloat() * 0.5F + 0.5F;
        float f12 = k + random.nextFloat();
        world.a("largesmoke", f, f6, f12, 0.0D, 0.0D, 0.0D);
      }
    }
    else
    {
      if (LongHashMapEntry.as.f(world, i - 1, j, k)) {
        for (int i1 = 0; i1 < 2; i1++)
        {
          float f1 = i + random.nextFloat() * 0.1F;
          float f7 = j + random.nextFloat();
          float f13 = k + random.nextFloat();
          world.a("largesmoke", f1, f7, f13, 0.0D, 0.0D, 0.0D);
        }
      }
      if (LongHashMapEntry.as.f(world, i + 1, j, k)) {
        for (int j1 = 0; j1 < 2; j1++)
        {
          float f2 = i + 1 - random.nextFloat() * 0.1F;
          float f8 = j + random.nextFloat();
          float f14 = k + random.nextFloat();
          world.a("largesmoke", f2, f8, f14, 0.0D, 0.0D, 0.0D);
        }
      }
      if (LongHashMapEntry.as.f(world, i, j, k - 1)) {
        for (int k1 = 0; k1 < 2; k1++)
        {
          float f3 = i + random.nextFloat();
          float f9 = j + random.nextFloat();
          float f15 = k + random.nextFloat() * 0.1F;
          world.a("largesmoke", f3, f9, f15, 0.0D, 0.0D, 0.0D);
        }
      }
      if (LongHashMapEntry.as.f(world, i, j, k + 1)) {
        for (int l1 = 0; l1 < 2; l1++)
        {
          float f4 = i + random.nextFloat();
          float f10 = j + random.nextFloat();
          float f16 = k + 1 - random.nextFloat() * 0.1F;
          world.a("largesmoke", f4, f10, f16, 0.0D, 0.0D, 0.0D);
        }
      }
      if (LongHashMapEntry.as.f(world, i, j + 1, k)) {
        for (int i2 = 0; i2 < 2; i2++)
        {
          float f5 = i + random.nextFloat();
          float f11 = j + 1 - random.nextFloat() * 0.1F;
          float f17 = k + random.nextFloat();
          world.a("largesmoke", f5, f11, f17, 0.0D, 0.0D, 0.0D);
        }
      }
    }
  }
}
