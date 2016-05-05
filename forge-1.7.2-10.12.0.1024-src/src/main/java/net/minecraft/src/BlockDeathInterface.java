package net.minecraft.src;

import net.minecraft.entity.monster.EntityGolem;

public abstract interface BlockDeathInterface
{
  public abstract boolean hasDied(EntityGolem paramEntityGolem, int paramInt1, int paramInt2, int paramInt3, double paramDouble);
  
  public abstract boolean hasStarved(EntityGolem paramEntityGolem, int paramInt1, int paramInt2, int paramInt3);
  
  public abstract boolean hasRandomlyDied(EntityGolem paramEntityGolem, int paramInt1, int paramInt2, int paramInt3, double paramDouble);
  
  public abstract void death(EntityGolem paramEntityGolem, int paramInt1, int paramInt2, int paramInt3);
}
