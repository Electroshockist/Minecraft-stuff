package net.minecraft.dispenser;

import moapi.ModBooleanOption;
import moapi.ModOptions;
import moapi.ModOptionsAPI;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.src.ItemPlantable;
import net.minecraft.util.LongHashMapEntry;

public class DispenserBehaviorPotionProjectile
  extends ItemPlantable
{
  public DispenserBehaviorPotionProjectile(int i)
  {
    super(i);
    f(0);
    a(true);
  }
  
  public int b(int i)
  {
    return i;
  }
  
  public int a(int i)
  {
    return LongHashMapEntry.z.a(0, i);
  }
  
  public boolean plantable(EntityGolem world, int i, int j, int k, int belowID, int age)
  {
    boolean saplingGrow = ((Boolean)((ModBooleanOption)ModOptionsAPI.getModOptions("NatureOverhaul").getSubOption("Sapling Options").getOption("AutoSapling")).getValue()).booleanValue();
    

    return (saplingGrow) && ((belowID == 2) || (belowID == 3));
  }
  
  public float[] getVelocities(double baseSpeed)
  {
    float[] out = super.getVelocities(baseSpeed);
    
    out[1] = ((float)(baseSpeed + baseSpeed * Math.random() * 2.0D));
    
    return out;
  }
}
