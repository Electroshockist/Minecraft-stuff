package net.minecraft.src;

import java.util.HashMap;

public enum Biome
{
  SWAMPLAND(0),  FOREST(1),  TAIGA(2),  DESERT(3),  PLAINS(4),  HELL(5),  OCEAN(6),  HILLS(7),  RIVER(8),  SKY(9);
  
  private static HashMap<String, Biome> BiomeTranslate;
  private int i;
  
  private Biome(int index)
  {
    this.i = index;
  }
  
  public int getIndex()
  {
    return this.i;
  }
  
  public static Biome getBiomeFromString(String name)
  {
    if (!BiomeTranslate.containsKey(name)) {
      throw new NullPointerException("No such Biome " + name);
    }
    return (Biome)BiomeTranslate.get(name);
  }
  
  static
  {
    BiomeTranslate = new HashMap();
    


























    BiomeTranslate.put("Swampland", SWAMPLAND);
    BiomeTranslate.put("Forest", FOREST);
    BiomeTranslate.put("Taiga", TAIGA);
    BiomeTranslate.put("Desert", DESERT);
    BiomeTranslate.put("Plains", PLAINS);
    BiomeTranslate.put("Hell", HELL);
    BiomeTranslate.put("Ocean", OCEAN);
    BiomeTranslate.put("Extreme Hills", HILLS);
    BiomeTranslate.put("River", RIVER);
    BiomeTranslate.put("Sky", SKY);
  }
}
