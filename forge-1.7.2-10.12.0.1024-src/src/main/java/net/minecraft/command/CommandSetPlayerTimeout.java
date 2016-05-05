package net.minecraft.command;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Random;
import moapi.ModMappedMultiOption;
import net.minecraft.creativetab.CreativeTabBlock;
import net.minecraft.entity.NpcMerchant;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.network.packet.Packet32EntityLook;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionAttackDamage;
import net.minecraft.server.gui.StatsComponent;
import net.minecraft.src.mod_AutoForest;
import net.minecraft.util.LongHashMapEntry;

public abstract class CommandSetPlayerTimeout
  extends NpcMerchant
{
  private int reproductionCounter;
  private boolean isFemale = true;
  protected static ModMappedMultiOption reproRate;
  
  public CommandSetPlayerTimeout(EntityGolem world)
  {
    super(world);
    
    reproRate = mod_AutoForest.reproductionRate;
    this.isFemale = (this.k.w.nextInt(2) == 0);
    
    this.reproductionCounter = (-this.k.w.nextInt(((Integer)reproRate.getValue()).intValue()));
  }
  
  protected void o_()
  {
    super.o_();
    if ((!(this instanceof Potion)) && (isFemale())) {
      attemptReproduction();
    }
  }
  
  private void attemptReproduction()
  {
    EntityAnimal bb = EntityAnimal.a(this.o - 5.0D, this.p - 2.0D, this.q - 5.0D, this.o + 5.0D, this.p + 2.0D, this.q + 5.0D);
    
    List entities = this.k.b(this, bb);
    boolean reproduce = false;
    for (Object obj : entities) {
      if ((obj instanceof CommandSetPlayerTimeout))
      {
        CommandSetPlayerTimeout animal = (CommandSetPlayerTimeout)obj;
        if ((!animal.equals(this)) && (getClass() == animal.getClass()) && (animal.isMale())) {
          reproduce = true;
        }
      }
    }
    if (reproduce) {
      if (this.reproductionCounter >= ((Integer)reproRate.getValue()).intValue()) {
        try
        {
          Random rand = this.k.w;
          CommandSetPlayerTimeout baby = (CommandSetPlayerTimeout)getClass().getConstructor(new Class[] { EntityGolem.class }).newInstance(new Object[] { this.k });
          
          baby.c(this.o + rand.nextInt(3) - 1.0D, this.p + 1.0D, this.q + rand.nextInt(3) - 1.0D, rand.nextFloat() * 360.0F, 0.0F);
          if (baby.c())
          {
            bb = EntityAnimal.a(this.o - 16.0D, 0.0D, this.q - 16.0D, this.o + 16.0D, 127.0D, this.q + 16.0D);
            entities = this.k.b(this, bb);
            
            int count = 0;
            for (Object obj : entities) {
              if ((obj instanceof CommandSetPlayerTimeout)) {
                count++;
              }
            }
            if (count < 20)
            {
              this.k.a(baby);
              if ((this instanceof NBTTagByte)) {
                ((NBTTagByte)baby).b(((NBTTagByte)this).n_());
              }
              this.reproductionCounter = (-(((Integer)reproRate.getValue()).intValue() + this.k.w.nextInt(((Integer)reproRate.getValue()).intValue())));
              baby.setReproductionCounter(this.reproductionCounter);
            }
          }
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      } else {
        this.reproductionCounter += 1;
      }
    }
  }
  
  private boolean isFemale()
  {
    return this.isFemale;
  }
  
  private boolean isMale()
  {
    return !this.isFemale;
  }
  
  public void setReproductionCounter(int reproCount)
  {
    this.reproductionCounter = reproCount;
  }
  
  public boolean a(StatsComponent damagesource, int i)
  {
    this.at = 60;
    return super.a(damagesource, i);
  }
  
  protected float a(int i, int j, int k)
  {
    if (this.k.a(i, j - 1, k) == LongHashMapEntry.v.bA) {
      return 10.0F;
    }
    return this.k.c(i, j, k) - 0.5F;
  }
  
  public void b(CreativeTabBlock nbttagcompound)
  {
    super.b(nbttagcompound);
    



    nbttagcompound.a("hasGender", true);
    nbttagcompound.a("isFemale", this.isFemale);
    nbttagcompound.a("reproductionCounter", this.reproductionCounter);
  }
  
  public void a(CreativeTabBlock nbttagcompound)
  {
    super.a(nbttagcompound);
    



    boolean hasGender = nbttagcompound.m("hasGender");
    if (hasGender) {
      this.isFemale = nbttagcompound.m("isFemale");
    }
    this.reproductionCounter = nbttagcompound.e("reproductionCounter");
  }
  
  public boolean c()
  {
    int i = Packet32EntityLook.b(this.o);
    int j = Packet32EntityLook.b(this.y.b);
    int k = Packet32EntityLook.b(this.q);
    return (this.k.a(i, j - 1, k) == LongHashMapEntry.v.bA) && (this.k.m(i, j, k) > 8) && (super.c());
  }
  
  public int d()
  {
    return 120;
  }
  
  protected boolean c_()
  {
    return false;
  }
  
  protected int a(EntityMinecartMobSpawner entityplayer)
  {
    return 1 + this.k.w.nextInt(3);
  }
}
