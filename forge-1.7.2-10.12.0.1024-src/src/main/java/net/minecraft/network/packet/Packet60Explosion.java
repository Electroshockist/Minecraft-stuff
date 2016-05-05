package net.minecraft.network.packet;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTrackerEntry;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.inventory.SlotMerchantResult;
import net.minecraft.network.TcpReaderThread;
import net.minecraft.network.rcon.RConThreadQuery;
import net.minecraft.server.gui.StatsComponent;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Plantable;
import net.minecraft.util.LongHashMapEntry;

public class Packet60Explosion
  extends RConThreadQuery
{
  public EntityFishHook explosionX;
  private int chunkPositionRecords;
  public int explosionY;
  public int explosionZ;
  private int ap;
  public float explosionSize;
  
  public Packet60Explosion(EntityGolem world, double d, double d1, double d2, EntityFishHook itemstack)
  {
    this(world, d, d1, d2, itemstack, false);
  }
  
  public Packet60Explosion(EntityGolem world, double d, double d1, double d2, EntityFishHook itemstack, boolean grown)
  {
    super(world);
    this.explosionY = 0;
    this.ap = 5;
    this.explosionSize = ((float)(Math.random() * 3.141592653589793D * 2.0D));
    a(0.25F, 0.25F);
    this.H = (this.J / 2.0F);
    d(d, d1, d2);
    this.explosionX = itemstack;
    this.u = ((float)(Math.random() * 360.0D));
    this.r = ((float)(Math.random() * 0.2000000029802322D - 0.1000000014901161D));
    this.s = 0.2000000029802322D;
    this.t = ((float)(Math.random() * 0.2000000029802322D - 0.1000000014901161D));
    if ((!world.isCollided) && (grown)) {
      setInitialVelocity();
    }
  }
  
  protected boolean f()
  {
    return false;
  }
  
  public Packet60Explosion(EntityGolem world)
  {
    super(world);
    this.explosionY = 0;
    this.ap = 5;
    this.explosionSize = ((float)(Math.random() * 3.141592653589793D * 2.0D));
    a(0.25F, 0.25F);
    this.H = (this.J / 2.0F);
  }
  
  protected void b() {}
  
  public void w_()
  {
    super.w_();
    if (this.explosionZ > 0) {
      this.explosionZ -= 1;
    }
    this.l = this.o;
    this.m = this.p;
    this.n = this.q;
    if (!ModLoader.getMinecraftInstance().f.isCollided) {
      setNextYSpeed(this.explosionX.nextStepDistance);
    } else {
      this.s -= 0.03999999910593033D;
    }
    if (this.k.f(Packet32EntityLook.b(this.o), Packet32EntityLook.b(this.p), Packet32EntityLook.b(this.q)) == SlotMerchantResult.h)
    {
      this.s = 0.2000000029802322D;
      this.r = ((this.U.nextFloat() - this.U.nextFloat()) * 0.2F);
      this.t = ((this.U.nextFloat() - this.U.nextFloat()) * 0.2F);
      this.k.a(this, "random.fizz", 0.4F, 2.0F + this.U.nextFloat() * 0.4F);
    }
    h(this.o, (this.y.b + this.y.e) / 2.0D, this.q);
    b(this.r, this.s, this.t);
    float f = 0.98F;
    if (this.z)
    {
      f = 0.5880001F;
      int i = this.k.a(Packet32EntityLook.b(this.o), Packet32EntityLook.b(this.y.b) - 1, Packet32EntityLook.b(this.q));
      if (i > 0) {
        f = LongHashMapEntry.m[i].bO * 0.98F;
      }
      if (!ModLoader.getMinecraftInstance().f.isCollided) {
        attemptPlant(i);
      }
    }
    this.r *= f;
    this.s *= 0.9800000190734863D;
    this.t *= f;
    if (this.z) {
      this.s *= -0.5D;
    }
    this.chunkPositionRecords += 1;
    this.explosionY += 1;
    if (this.explosionY >= 6000) {
      v();
    }
  }
  
  private void setInitialVelocity()
  {
    double baseSpeed = 0.2000000029802322D;
    EntityMinecartContainer item = EntityMinecartContainer.f[this.explosionX.nextStepDistance];
    if ((item instanceof Plantable))
    {
      float[] motion = ((Plantable)item).getVelocities(baseSpeed);
      this.r = motion[0];
      this.s = motion[1];
      this.t = motion[2];
    }
    else
    {
      this.r = ((float)(Math.random() * baseSpeed - baseSpeed / 2.0D));
      this.s = baseSpeed;
      this.t = ((float)(Math.random() * baseSpeed - baseSpeed / 2.0D));
    }
  }
  
  private void setNextYSpeed(int id)
  {
    EntityMinecartContainer item = EntityMinecartContainer.f[id];
    if ((item instanceof Plantable))
    {
      if (this.s > -0.3999999910593033D) {
        this.s -= 0.03999999910593033D;
      }
    }
    else {
      this.s -= 0.03999999910593033D;
    }
  }
  
  private void attemptPlant(int belowID)
  {
    int i = Packet32EntityLook.b(this.o);
    int j = Packet32EntityLook.b(this.y.b);
    int k = Packet32EntityLook.b(this.q);
    
    int curBlockID = this.k.a(i, j, k);
    if ((this.explosionY > 1200) && ((curBlockID == 0) || (curBlockID == LongHashMapEntry.aT.bA) || (curBlockID == LongHashMapEntry.Y.bA)) && ((EntityMinecartContainer.f[this.explosionX.nextStepDistance] instanceof Plantable)))
    {
      Plantable pItem = (Plantable)EntityMinecartContainer.f[this.explosionX.nextStepDistance];
      if (pItem.plantable(this.k, i, j, k, belowID, this.explosionY))
      {
        this.k.d(i, j, k, pItem.getPlantBlockID(), this.explosionX.i());
        
        v();
      }
    }
  }
  
  public boolean initQuerySystem()
  {
    return this.k.a(this.y, SlotMerchantResult.g, this);
  }
  
  protected void a(int i)
  {
    a(StatsComponent.field_120040_a, i);
  }
  
  public boolean a(StatsComponent damagesource, int i)
  {
    D();
    this.ap -= i;
    if (this.ap <= 0) {
      v();
    }
    return false;
  }
  
  public void b(CreativeTabBlock nbttagcompound)
  {
    nbttagcompound.a("Health", (short)(byte)this.ap);
    nbttagcompound.a("Age", (short)this.explosionY);
    nbttagcompound.a("Item", this.explosionX.b(new CreativeTabBlock()));
  }
  
  public void a(CreativeTabBlock nbttagcompound)
  {
    this.ap = (nbttagcompound.d("Health") & 0xFF);
    this.explosionY = nbttagcompound.d("Age");
    CreativeTabBlock nbttagcompound1 = nbttagcompound.k("Item");
    this.explosionX = EntityFishHook.a(nbttagcompound1);
    if (this.explosionX == null) {
      v();
    }
  }
  
  public void a_(EntityMinecartMobSpawner entityplayer)
  {
    if (this.k.isCollided) {
      return;
    }
    int i = this.explosionX.shake;
    if ((this.explosionZ == 0) && (entityplayer.as.a(this.explosionX)))
    {
      if (this.explosionX.nextStepDistance == LongHashMapEntry.K.bA) {
        entityplayer.a(TcpReaderThread.g);
      }
      if (this.explosionX.nextStepDistance == EntityMinecartContainer.aG.br) {
        entityplayer.a(TcpReaderThread.t);
      }
      ModLoader.OnItemPickup(entityplayer, this.explosionX);
      this.k.a(this, "random.pop", 0.2F, ((this.U.nextFloat() - this.U.nextFloat()) * 0.7F + 1.0F) * 2.0F);
      entityplayer.b(this, i);
      if (this.explosionX.shake <= 0) {
        v();
      }
    }
  }
}
