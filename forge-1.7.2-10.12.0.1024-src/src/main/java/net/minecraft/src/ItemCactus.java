package net.minecraft.src;

import moapi.ModBooleanOption;
import moapi.ModOptions;
import moapi.ModOptionsAPI;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.inventory.SlotMerchantResult;

public class ItemCactus
  extends ItemPlantable
{
  public ItemCactus(int i)
  {
    super(i);
  }
  
  public int func_21012_a(int i)
  {
    return i;
  }
  
  public boolean plantable(EntityGolem world, int i, int j, int k, int belowID, int age)
  {
    boolean cactiGrow = ((Boolean)((ModBooleanOption)ModOptionsAPI.getModOptions("NatureOverhaul").getSubOption("Plants Options").getSubOption("Cactus Options").getOption("CactiiGrow")).getValue()).booleanValue();
    


    return (cactiGrow) && (belowID == 12) && (!world.f(i - 1, j, k).a()) && (!world.f(i + 1, j, k).a()) && (!world.f(i, j, k - 1).a()) && (!world.f(i, j, k + 1).a());
  }
  
  public float[] getVelocities(double baseSpeed)
  {
    float[] out = super.getVelocities(baseSpeed);
    
    out[1] = ((float)(baseSpeed + baseSpeed * Math.random() * 3.0D));
    
    return out;
  }
}
