package net.minecraft.nbt;

import java.util.Random;
import net.minecraft.command.CommandSetPlayerTimeout;
import net.minecraft.command.CommandSpreadPlayersPosition;
import net.minecraft.creativetab.CreativeTabBlock;
import net.minecraft.enchantment.EnchantmentFireAspect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTrackerEntry;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.network.packet.Packet60Explosion;
import net.minecraft.util.LongHashMapEntry;

public class NBTTagByte
  extends CommandSetPlayerTimeout
{
  public NBTTagByte(EntityGolem world)
  {
    super(world);
    this.bn = "/mob/sheep.png";
    a(0.9F, 1.3F);
  }
  
  protected void b()
  {
    super.b();
    this.af.a(16, new Byte((byte)0));
  }
  
  protected void a(boolean flag)
  {
    if (!q()) {
      a(new EntityFishHook(LongHashMapEntry.ac.bA, 1, n_()), 0.0F);
    }
  }
  
  public void o_()
  {
    super.o_();
    if ((!this.k.isCollided) && 
      (this.k.a((int)this.o, (int)this.p, (int)this.q) == LongHashMapEntry.Y.bA) && (this.k.w.nextInt(12000) == 1))
    {
      b(false);
      this.k.g((int)this.o, (int)this.p, (int)this.q, 0);
    }
  }
  
  protected int m()
  {
    return LongHashMapEntry.ac.bA;
  }
  
  public boolean b(EntityMinecartMobSpawner entityplayer)
  {
    EntityFishHook itemstack = entityplayer.as.b();
    if ((itemstack != null) && (itemstack.nextStepDistance == EntityMinecartContainer.bf.br) && (!q()))
    {
      if (!this.k.isCollided)
      {
        b(true);
        int i = 2 + this.U.nextInt(3);
        for (int j = 0; j < i; j++)
        {
          Packet60Explosion entityitem = a(new EntityFishHook(LongHashMapEntry.ac.bA, 1, n_()), 1.0F);
          entityitem.s += this.U.nextFloat() * 0.05F;
          entityitem.r += (this.U.nextFloat() - this.U.nextFloat()) * 0.1F;
          entityitem.t += (this.U.nextFloat() - this.U.nextFloat()) * 0.1F;
        }
      }
      itemstack.a(1, entityplayer);
    }
    return false;
  }
  
  public void b(CreativeTabBlock nbttagcompound)
  {
    super.b(nbttagcompound);
    nbttagcompound.a("Sheared", q());
    nbttagcompound.a("Color", (byte)n_());
  }
  
  public void a(CreativeTabBlock nbttagcompound)
  {
    super.a(nbttagcompound);
    b(nbttagcompound.m("Sheared"));
    b(nbttagcompound.c("Color"));
  }
  
  protected String j()
  {
    return "mob.sheep";
  }
  
  protected String f_()
  {
    return "mob.sheep";
  }
  
  protected String l()
  {
    return "mob.sheep";
  }
  
  public int n_()
  {
    return this.af.a(16) & 0xF;
  }
  
  public void b(int i)
  {
    byte byte0 = this.af.a(16);
    this.af.b(16, Byte.valueOf((byte)(byte0 & 0xF0 | i & 0xF)));
  }
  
  public boolean q()
  {
    return (this.af.a(16) & 0x10) != 0;
  }
  
  public void b(boolean flag)
  {
    byte byte0 = this.af.a(16);
    if (flag) {
      this.af.b(16, Byte.valueOf((byte)(byte0 | 0x10)));
    } else {
      this.af.b(16, Byte.valueOf((byte)(byte0 & 0xFFFFFFEF)));
    }
  }
  
  public static int a(Random random)
  {
    int i = random.nextInt(100);
    if (i < 5) {
      return 15;
    }
    if (i < 10) {
      return 7;
    }
    if (i < 15) {
      return 8;
    }
    if (i < 18) {
      return 12;
    }
    return random.nextInt(500) != 0 ? 0 : 6;
  }
  
  public static final float[][] data = { { 1.0F, 1.0F, 1.0F }, { 0.95F, 0.7F, 0.2F }, { 0.9F, 0.5F, 0.85F }, { 0.6F, 0.7F, 0.95F }, { 0.9F, 0.9F, 0.2F }, { 0.5F, 0.8F, 0.1F }, { 0.95F, 0.7F, 0.8F }, { 0.3F, 0.3F, 0.3F }, { 0.6F, 0.6F, 0.6F }, { 0.3F, 0.6F, 0.7F }, { 0.7F, 0.4F, 0.9F }, { 0.2F, 0.4F, 0.8F }, { 0.5F, 0.4F, 0.3F }, { 0.4F, 0.5F, 0.2F }, { 0.8F, 0.3F, 0.3F }, { 0.1F, 0.1F, 0.1F } };
}
