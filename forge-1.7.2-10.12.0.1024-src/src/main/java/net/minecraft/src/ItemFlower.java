package net.minecraft.src;

import moapi.ModBooleanOption;
import moapi.ModOptions;
import moapi.ModOptionsAPI;
import net.minecraft.entity.monster.EntityGolem;

public class ItemFlower
  extends ItemPlantable
  implements Plantable
{
  public ItemFlower(int i)
  {
    super(i);
  }
  
  public int func_21012_a(int i)
  {
    return i;
  }
  
  public boolean plantable(EntityGolem world, int i, int j, int k, int belowID, int age)
  {
    boolean flowerGrow = ((Boolean)((ModBooleanOption)ModOptionsAPI.getModOptions("NatureOverhaul").getSubOption("Plants Options").getSubOption("Flower Options").getOption("FlowersGrow")).getValue()).booleanValue();
    


    return (flowerGrow) && ((belowID == 2) || (belowID == 3));
  }
}
