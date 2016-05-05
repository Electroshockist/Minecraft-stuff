package net.minecraft.src;

import moapi.ModBooleanOption;
import moapi.ModOptions;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.util.LongHashMapEntry;

public class ItemMushroom
  extends ItemPlantable
{
  public ItemMushroom(int i)
  {
    super(i);
  }
  
  public int func_21012_a(int i)
  {
    return i;
  }
  
  public boolean plantable(EntityGolem world, int i, int j, int k, int belowID, int age)
  {
    boolean shroomGrow = ((Boolean)((ModBooleanOption)mod_AutoForest.shrooms.getOption("ShroomsGrow")).getValue()).booleanValue();
    return (shroomGrow) && (shroomPlant(belowID));
  }
  
  private boolean shroomPlant(int belowID)
  {
    return (belowID != LongHashMapEntry.N.bA) && (belowID != LongHashMapEntry.aU.bA);
  }
  
  public float[] getVelocities(double baseSpeed)
  {
    float[] out = new float[3];
    if (this.br - 256 == 39)
    {
      double circleDist = baseSpeed * 2.0D;
      out[0] = ((float)(circleDist - Math.random() * circleDist) * randSign());
      out[1] = ((float)(baseSpeed * 3.0D));
      out[2] = ((float)(Math.pow(circleDist, 2.0D) - Math.pow(out[0], 2.0D)) * randSign());
    }
    return out;
  }
}
