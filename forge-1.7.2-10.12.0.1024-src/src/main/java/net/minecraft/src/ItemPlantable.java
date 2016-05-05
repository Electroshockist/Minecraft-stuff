package net.minecraft.src;

import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.monster.EntityGolem;

public abstract class ItemPlantable
  extends EntityAIOpenDoor
  implements Plantable
{
  protected ItemPlantable(int i)
  {
    super(i);
  }
  
  public int getPlantBlockID()
  {
    return this.br;
  }
  
  public float[] getVelocities(double baseSpeed)
  {
    float[] out = new float[3];
    
    out[0] = ((float)(Math.random() * baseSpeed) * randSign());
    out[1] = ((float)(baseSpeed + baseSpeed * Math.random() * 1.5D));
    out[2] = ((float)(Math.random() * baseSpeed) * randSign());
    
    return out;
  }
  
  protected int randSign()
  {
    return (int)Math.pow(-1.0D, (int)Math.round(Math.random()) + 1) * 2;
  }
  
  public abstract boolean plantable(EntityGolem paramEntityGolem, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
}
