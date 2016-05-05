package net.minecraft.src;

import net.minecraft.entity.monster.EntityGolem;

public abstract interface Plantable
{
  public abstract boolean plantable(EntityGolem paramEntityGolem, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
  
  public abstract int getPlantBlockID();
  
  public abstract float[] getVelocities(double paramDouble);
}
