package net.minecraft.world;

import moapi.ModOptionsAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemCoal;
import net.minecraft.item.ItemMapBase;
import net.minecraft.world.biome.WorldChunkManagerHell;

public class WorldSettings
  extends EntityAISit
{
  private ItemCoal seed;
  private boolean theGameType;
  
  public WorldSettings(Minecraft minecraft, String s, int i)
  {
    ModOptionsAPI.joinedMultiplayerWorld(s);
    


    this.theGameType = false;
    System.out.println("Connecting to " + s + ", " + i);
    minecraft.a(null);
    new WorldChunkManagerHell(this, minecraft, s, i).start();
  }
  
  public void p_()
  {
    if (this.seed != null) {
      this.seed.b();
    }
  }
  
  protected void a(char c, int i) {}
  
  public void a()
  {
    ItemMapBase stringtranslate = ItemMapBase.a();
    this.o.clear();
    this.o.add(new ContainerChest(0, this.m / 2 - 100, this.n / 4 + 120 + 12, stringtranslate.a("gui.cancel")));
  }
  
  protected void a(ContainerChest guibutton)
  {
    if (guibutton.field_94536_g == 0)
    {
      this.theGameType = true;
      if (this.seed != null) {
        this.seed.c();
      }
      this.l.a(new EntityWolf(null));
    }
  }
  
  public void a(int i, int j, float f)
  {
    k();
    ItemMapBase stringtranslate = ItemMapBase.a();
    if (this.seed == null)
    {
      a(this.q, stringtranslate.a("connect.connecting"), this.m / 2, this.n / 2 - 50, 16777215);
      a(this.q, "", this.m / 2, this.n / 2 - 10, 16777215);
    }
    else
    {
      a(this.q, stringtranslate.a("connect.authorizing"), this.m / 2, this.n / 2 - 50, 16777215);
      a(this.q, this.seed.field_111220_a, this.m / 2, this.n / 2 - 10, 16777215);
    }
    super.a(i, j, f);
  }
  
  static ItemCoal a(WorldSettings guiconnecting, ItemCoal netclienthandler)
  {
    return guiconnecting.seed = netclienthandler;
  }
  
  static boolean a(WorldSettings guiconnecting)
  {
    return guiconnecting.theGameType;
  }
  
  static ItemCoal b(WorldSettings guiconnecting)
  {
    return guiconnecting.seed;
  }
}
