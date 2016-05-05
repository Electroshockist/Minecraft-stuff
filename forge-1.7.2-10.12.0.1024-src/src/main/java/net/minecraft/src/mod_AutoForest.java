package net.minecraft.src;

import java.io.PrintStream;
import java.util.HashMap;
import moapi.ModBooleanOption;
import moapi.ModMappedMultiOption;
import moapi.ModMappedOption;
import moapi.ModOptions;
import moapi.ModOptionsAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.dispenser.DispenserBehaviorPotionProjectile;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.inventory.SlotBrewingStandPotion;
import net.minecraft.util.Icon;
import net.minecraft.util.LongHashMapEntry;

public class mod_AutoForest
  extends BaseMod
{
  public static final String PLANT_MENU_NAME = "Plants Options";
  public static final String SAPLING_MENU_NAME = "Sapling Options";
  public static final String MENU_NAME = "NatureOverhaul";
  public static final String TREE_MENU_NAME = "Tree Options";
  public static final String CLIMATE_MENU_NAME = "Climate Options";
  public static final String NIGHT_MENU_NAME = "Day and Night Options";
  public static final String FLOWER_MENU_NAME = "Flower Options";
  public static final String CACTI_MENU_NAME = "Cactus Options";
  public static final String REED_MENU_NAME = "Reed Options";
  public static final String SHROOMS_MENU_NAME = "Mushroom Options";
  private static final String GRASS_MENU_NAME = "Grass Options";
  private static final String MISC_MENU_NAME = "Misc Options";
  public static ModOptions options = new ModOptions("NatureOverhaul");
  public static ModOptions saps = new ModOptions("Sapling Options");
  public static ModOptions tree = new ModOptions("Tree Options");
  public static ModOptions plants = new ModOptions("Plants Options");
  public static ModOptions flowers = new ModOptions("Flower Options");
  public static ModOptions cactii = new ModOptions("Cactus Options");
  public static ModOptions reed = new ModOptions("Reed Options");
  public static ModOptions shrooms = new ModOptions("Mushroom Options");
  public static ModOptions grass = new ModOptions("Grass Options");
  public static ModOptions misc = new ModOptions("Misc Options");
  public static ModBooleanOption flowerDeath = new ModBooleanOption("Flowers Die");
  public static ModBooleanOption shroomDeath = new ModBooleanOption("Shrooms Die");
  public static ModBooleanOption reedDeath = new ModBooleanOption("Reeds Die");
  public static ModBooleanOption cactiiDeath = new ModBooleanOption("Cactii Die");
  public static ModBooleanOption grassDeath = new ModBooleanOption("Grass Dies");
  public static ModBooleanOption shroomTreesGrow = new ModBooleanOption("ShroomsTreesGrow");
  public static ModMappedOption shroomTreeGrowth;
  public static ModMappedMultiOption growthType;
  public static ModBooleanOption defaultShroomSpread = new ModBooleanOption("Enable Default Spread");
  public static ModBooleanOption mossGrows = new ModBooleanOption("Moss Grows");
  public static ModMappedMultiOption mossGrowthRate;
  public static ModBooleanOption infiniteFire = new ModBooleanOption("Infinite Fire Spread");
  public static ModBooleanOption waterFix = new ModBooleanOption("Fix Water Bug");
  public static ModMappedMultiOption reproductionRate;
  public static ModOptions climate = new ModOptions("Climate Options");
  private static String[] labels = { "AVERAGE", "FAST", "SUPERFAST", "INSANE", "SUPERSLOW", "SLOW" };
  private static final HashMap<String, byte[]> biomeModifier = new HashMap();
  
  public String Version()
  {
    return "1.3";
  }
  
  public mod_AutoForest()
  {
    createbiomeModifiers();
    setupModOptions();
  }
  
  private void setupModOptions()
  {
    options.addSubOptions(saps);
    options.addSubOptions(plants);
    options.addSubOptions(climate);
    options.addSubOptions(tree);
    options.addSubOptions(shrooms);
    options.addSubOptions(misc);
    

    setupSaplings();
    
    setupTreeOptions();
    
    addFlowers();
    
    setupShroomOptions();
    

    climate.addToggle("BiomeModifiedGrowth");
    climate.setWideOption("biomeModifiedGrowth");
    

    addMiscOptions();
    
    setupItemsAndBlocks();
    
    options.loadValues();
    ModOptionsAPI.addMod(options);
  }
  
  private void setupSaplings()
  {
    String[] growthLabels = { "Both", "Decay", "Growth", "Neither" };
    Integer[] growthValues = { Integer.valueOf(3), Integer.valueOf(2), Integer.valueOf(1), Integer.valueOf(0) };
    
    saps.addToggle("AutoSapling");
    saps.addToggle("SaplingDeath");
    saps.addMultiOption("GrowthRate", labels);
    saps.addMappedMultiOption("Growth Occurs On", growthValues, growthLabels);
    
    growthType = (ModMappedMultiOption)saps.getOption("Growth Occurs On");
    
    saps.setWideOption("Growth Occurs On");
  }
  
  private void setupTreeOptions()
  {
    Integer[] dKeys = { Integer.valueOf(5000), Integer.valueOf(2500), Integer.valueOf(250), Integer.valueOf(5), Integer.valueOf(20000), Integer.valueOf(10000) };
    Integer[] keys = { Integer.valueOf(5), Integer.valueOf(3), Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(9), Integer.valueOf(7) };
    String[] values = { "DEFAULT/AVERAGE", "FAST", "VERY FAST", "INSTANT", "VERY SLOW", "SLOW" };
    tree.addMappedMultiOption("TreeGrowthRate", keys, values);
    tree.addToggle("Lumberjack");
    tree.addMappedMultiOption("DeathRate", dKeys, labels);
    tree.addToggle("TreeDeath");
    

    Integer[] aKeys = { Integer.valueOf(3000), Integer.valueOf(1200), Integer.valueOf(250), Integer.valueOf(5), Integer.valueOf(30000), Integer.valueOf(10000) };
    tree.addMappedMultiOption("CocoaGrowthRate", aKeys, labels);
    tree.addToggle("CocoaGrows");
    tree.addMappedMultiOption("AppleGrowthRate", aKeys, labels);
    tree.addToggle("ApplesGrow");
    
    tree.setWideOption("TreeGrowthRate");
  }
  
  private void setupShroomOptions()
  {
    Integer[] pKeys = { Integer.valueOf(2400), Integer.valueOf(240), Integer.valueOf(30), Integer.valueOf(5), Integer.valueOf(30000), Integer.valueOf(9000) };
    
    shroomTreeGrowth = new ModMappedOption("ShroomTreeGrowthRate", pKeys, labels);
    
    shrooms.addOption(defaultShroomSpread);
    shrooms.addOption(shroomTreeGrowth);
    shrooms.addOption(shroomTreesGrow);
    shrooms.addMappedMultiOption("ShroomDeathRate", pKeys, labels);
    shrooms.addOption(shroomDeath);
    shrooms.addMappedMultiOption("ShroomGrowthRate", pKeys, labels);
    shrooms.addToggle("ShroomsGrow");
    
    defaultShroomSpread.setValue(Boolean.valueOf(false));
  }
  
  private void addFlowers()
  {
    Integer[] pKeys = { Integer.valueOf(2400), Integer.valueOf(240), Integer.valueOf(30), Integer.valueOf(5), Integer.valueOf(30000), Integer.valueOf(9000) };
    plants.addSubOptions(flowers);
    plants.addSubOptions(cactii);
    plants.addSubOptions(reed);
    plants.addSubOptions(grass);
    

    flowers.addMappedMultiOption("FlowerDeathRate", pKeys, labels);
    flowers.addOption(flowerDeath);
    flowers.addMappedMultiOption("FlowerGrowthRate", pKeys, labels);
    flowers.addToggle("FlowersGrow");
    
    cactii.addMappedMultiOption("CactiiDeathRate", pKeys, labels);
    cactii.addOption(cactiiDeath);
    cactii.addMappedMultiOption("CactiiGrowthRate", pKeys, labels);
    cactii.addToggle("CactiiGrow");
    
    reed.addMappedMultiOption("ReedDeathRate", pKeys, labels);
    reed.addOption(reedDeath);
    reed.addMappedMultiOption("ReedGrowthRate", pKeys, labels);
    reed.addToggle("ReedsGrow");
    
    grass.addMappedMultiOption("GrassDeathRate", pKeys, labels);
    grass.addOption(grassDeath);
    grass.addMappedMultiOption("GrassGrowthRate", pKeys, labels);
    grass.addToggle("GrassGrows");
  }
  
  private void addMiscOptions()
  {
    Integer[] pKeys = { Integer.valueOf(2400), Integer.valueOf(240), Integer.valueOf(30), Integer.valueOf(5), Integer.valueOf(30000), Integer.valueOf(9000) };
    
    Integer[] rKeys = { Integer.valueOf(16000), Integer.valueOf(1600), Integer.valueOf(160), Integer.valueOf(16), Integer.valueOf(64000), Integer.valueOf(32000) };
    
    misc.addOption(waterFix);
    misc.addOption(infiniteFire);
    infiniteFire.setGlobalValue(Boolean.valueOf(false));
    misc.addMappedMultiOption("MossGrowthRate", pKeys, labels);
    misc.addOption(mossGrows);
    misc.addMappedMultiOption("Animal Birth Rate", rKeys, labels);
    

    mossGrowthRate = (ModMappedMultiOption)misc.getOption("MossGrowthRate");
    reproductionRate = (ModMappedMultiOption)misc.getOption("Animal Birth Rate");
  }
  
  private void setupItemsAndBlocks()
  {
    EntityMinecartContainer.f[LongHashMapEntry.z.bA] = new DispenserBehaviorPotionProjectile(LongHashMapEntry.z.bA - 256).a("Sapling");
    EntityMinecartContainer.f[LongHashMapEntry.ag.bA] = new ItemMushroom(LongHashMapEntry.ag.bA - 256).a("Mushroom");
    EntityMinecartContainer.f[LongHashMapEntry.ah.bA] = new ItemMushroom(LongHashMapEntry.ah.bA - 256).a("Mushroom");
    EntityMinecartContainer.f[LongHashMapEntry.ae.bA] = new ItemFlower(LongHashMapEntry.ae.bA - 256).a("Flower");
    EntityMinecartContainer.f[LongHashMapEntry.af.bA] = new ItemFlower(LongHashMapEntry.af.bA - 256).a("Flower");
    EntityMinecartContainer.f[LongHashMapEntry.aW.bA] = new ItemCactus(LongHashMapEntry.aW.bA - 256).a("Cactus");
    

    BlockCobblestoneMossy.createInBlockList();
    LongHashMapEntry.m[LongHashMapEntry.ap.bA].l();
    
    EntityMinecartContainer tmp = new EntityAIOpenDoor(LongHashMapEntry.ap.bA - 256);
    EntityMinecartContainer.f[LongHashMapEntry.ap.bA] = tmp;
    EntityMinecartContainer.f[(LongHashMapEntry.ap.bA + 92)] = tmp;
  }
  
  public static byte getBiomeModifier(String biomeName, String name)
  {
    byte[] biomeMod = null;
    Biome biome = null;
    try
    {
      Biome.getBiomeFromString(biomeName);
      biome = Biome.getBiomeFromString(biomeName);
      biomeMod = (byte[])biomeModifier.get(name);
      if (biomeMod != null) {
        return biomeMod[biome.getIndex()];
      }
      System.out.println("biome Mod Type missing: " + name);
      return 0;
    }
    catch (NullPointerException e)
    {
      System.out.println("biome missing: " + biomeName);
    }
    return 0;
  }
  
  private void createbiomeModifiers()
  {
    byte[] saplingSpawn = { -10, 0, 10, -100, -95, -100, 0, -20, 10, 0 };
    byte[] saplingDeath = { 10, 0, 10, 95, 95, 100, 0, 20, 5, 0 };
    byte[] treeDeath = { -10, 0, 0, -90, -90, 100, 0, 20, 10, 0 };
    byte[] bigTree = { 10, 10, 10, 5, 0, 0, 0, 0, 10, 0 };
    byte[] treeGap = { 3, 1, 2, 10, 9, 0, 0, 4, 2, 0 };
    byte[] flowerSpawn = { 25, 10, 0, -100, -90, -100, 0, 0, 50, 0 };
    byte[] cactiSpawn = { -25, 0, -75, 20, -90, -100, 0, -5, 0, 0 };
    byte[] cactiiDeath = { 25, 0, 75, -20, 90, 100, 0, -10, 0, 0 };
    byte[] reedSpawn = { 50, 10, -90, -90, -10, -100, 0, -40, 100, 0 };
    byte[] reedDeath = { -50, -20, 5, 10, 0, 100, 0, 40, -20, 0 };
    byte[] shroomSpawn = { 25, 10, 0, -95, -90, -100, 0, 0, 35, 0 };
    byte[] shroomDeath = { -25, -10, 0, 95, 90, 100, 0, 0, 10, 0 };
    byte[] mossGrowth = { 100, 50, -90, -100, -90, -100, 0, 0, 100, 0 };
    
    biomeModifier.put("SaplingSpawn", saplingSpawn);
    biomeModifier.put("SaplingDeath", saplingDeath);
    biomeModifier.put("TreeDeath", treeDeath);
    biomeModifier.put("BigTree", bigTree);
    biomeModifier.put("TreeGap", treeGap);
    biomeModifier.put("FlowerSpawn", flowerSpawn);
    biomeModifier.put("CactiiSpawn", cactiSpawn);
    biomeModifier.put("DeathSpawn", cactiiDeath);
    biomeModifier.put("ReedSpawn", reedSpawn);
    biomeModifier.put("ReedDeath", reedDeath);
    biomeModifier.put("ShroomSpawn", shroomSpawn);
    biomeModifier.put("ShroomDeath", shroomDeath);
    biomeModifier.put("GrassSpawn", flowerSpawn);
    biomeModifier.put("MossGrowth", mossGrowth);
    



    byte[] standardDeath = { 25, -10, 45, 90, 60, 100, 50, 65, -25, 100 };
    
    biomeModifier.put("StandardDeath", standardDeath);
  }
  
  public void addbiomeModifier(String name, byte[] mods)
  {
    if (mods.length != 12) {
      throw new IndexOutOfBoundsException("Out of bounds. Length must be 11.");
    }
    biomeModifier.put(name, mods);
  }
  
  public static double applyBiomeModifier(double value, String biomeMod, EntityGolem world, int i, int k)
  {
    boolean biomeModsEnabled = ((Boolean)((ModBooleanOption)climate.getOption("BiomeModifiedGrowth")).getValue()).booleanValue();
    if (biomeModsEnabled)
    {
      String name = getBiomeName(i, k);
      
      value += value * 0.01D * getBiomeModifier(name, biomeMod);
    }
    return value;
  }
  
  public static String getBiomeName(int i, int j)
  {
    EntityEggInfo cm = ModLoader.getMinecraftInstance().f.a();
    
    return cm.a(i, j).l;
  }
  
  public static Biome getBiomeAt(int i, int j)
  {
    String name = getBiomeName(i, j);
    
    return Biome.getBiomeFromString(name);
  }
  
  public static void debugOutput(String msg)
  {
    System.out.println("(NatureOverhaul) " + msg);
  }
}
