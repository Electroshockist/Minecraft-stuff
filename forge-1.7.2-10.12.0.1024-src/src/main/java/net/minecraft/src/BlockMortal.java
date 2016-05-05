package net.minecraft.src;

import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.inventory.SlotMerchantResult;

public abstract class BlockMortal
  extends BlockGrowable
  implements BlockDeathInterface
{
  protected String deathModifierName = "StandardDeath";
  
  protected BlockMortal(int i, SlotMerchantResult material)
  {
    super(i, material);
  }
  
  protected BlockMortal(int i, int j, SlotMerchantResult material)
  {
    super(i, j, material);
  }
  
  public boolean hasDied(EntityGolem world, int i, int j, int k, double prob)
  {
    return (hasStarved(world, i, j, k)) || (hasRandomlyDied(world, i, j, k, prob));
  }
  
  public boolean hasStarved(EntityGolem world, int i, int j, int k)
  {
    int radius = getPrivacyRadius(world, i, j, k);
    int maxNeighbours = getMaxNeighbours(world, i, j, k);
    int foundNeighbours = 0;
    if ((radius > 0) && (radius < 10)) {
      for (int x = i - radius; x <= i + radius; x++) {
        for (int y = j - radius; y < j + radius; y++) {
          for (int z = k - radius; z < k + radius; z++) {
            if ((i != x) || (j != y) || (k != z))
            {
              int blockID = world.a(x, y, z);
              if (blockID == this.bA) {
                foundNeighbours++;
              }
            }
          }
        }
      }
    }
    return foundNeighbours > maxNeighbours;
  }
  
  protected int getPrivacyRadius(EntityGolem world, int i, int j, int k)
  {
    return 0;
  }
  
  protected int getMaxNeighbours(EntityGolem world, int i, int j, int k)
  {
    return 9;
  }
  
  public boolean hasRandomlyDied(EntityGolem world, int i, int j, int k, double prob)
  {
    prob = mod_AutoForest.applyBiomeModifier(prob, this.deathModifierName, world, i, k);
    return Math.random() < prob;
  }
  
  public void death(EntityGolem world, int i, int j, int k)
  {
    world.g(i, j, k, 0);
  }
}
