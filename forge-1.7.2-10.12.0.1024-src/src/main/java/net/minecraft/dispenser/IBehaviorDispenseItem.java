package net.minecraft.dispenser;

import moapi.gui.ModMenu;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemMapBase;
import net.minecraft.network.rcon.IServer;
import net.minecraft.util.Facing;

public class IBehaviorDispenseItem
  extends EntityAISit
{
  private EntityAISit b;
  protected String itemDispenseBehaviorProvider;
  private Packet207SetScore c;
  
  public IBehaviorDispenseItem(EntityAISit guiscreen, Packet207SetScore gamesettings)
  {
    this.itemDispenseBehaviorProvider = "Options";
    this.b = guiscreen;
    this.c = gamesettings;
  }
  
  public void a()
  {
    ItemMapBase stringtranslate = ItemMapBase.a();
    this.itemDispenseBehaviorProvider = stringtranslate.a("options.title");
    int i = 0;
    EntitySilverfish[] aenumoptions = d;
    int j = aenumoptions.length;
    for (int k = 0; k < j; k++)
    {
      EntitySilverfish enumoptions = aenumoptions[k];
      if (!enumoptions.a()) {
        this.o.add(new Facing(enumoptions.c(), this.m / 2 - 155 + i % 2 * 160, this.n / 6 + 24 * (i >> 1), enumoptions, this.c.c(enumoptions)));
      } else {
        this.o.add(new EnumGameType(enumoptions.c(), this.m / 2 - 155 + i % 2 * 160, this.n / 6 + 24 * (i >> 1), enumoptions, this.c.c(enumoptions), this.c.a(enumoptions)));
      }
      i++;
    }
    this.o.add(new ContainerChest(101, this.m / 2 - 100, this.n / 6 + 96 + 12, stringtranslate.a("options.video")));
    this.o.add(new ContainerChest(100, this.m / 2 - 100, this.n / 6 + 120 + 12, stringtranslate.a("options.controls")));
    


    this.o.add(new ContainerChest(301, this.m / 2 - 100, this.n / 6 + 72 + 12, "Mod Options"));
    


    this.o.add(new ContainerChest(200, this.m / 2 - 100, this.n / 6 + 168, stringtranslate.a("gui.done")));
  }
  
  protected void a(ContainerChest guibutton)
  {
    if (!guibutton.h) {
      return;
    }
    if ((guibutton.windowId < 100) && ((guibutton instanceof Facing)))
    {
      this.c.a(((Facing)guibutton).a(), 1);
      guibutton.numRows = this.c.c(EntitySilverfish.a(guibutton.windowId));
    }
    if (guibutton.windowId == 101)
    {
      this.l.z.b();
      this.l.a(new IServer(this, this.c));
    }
    if (guibutton.windowId == 100)
    {
      this.l.z.b();
      this.l.a(new lo(this, this.c));
    }
    if (guibutton.windowId == 200)
    {
      this.l.z.b();
      this.l.a(this.b);
    }
    if (guibutton.windowId == 301)
    {
      this.l.z.b();
      this.l.a(new ModMenu(this));
    }
  }
  
  public void a(int i, int j, float f)
  {
    k();
    a(this.q, this.itemDispenseBehaviorProvider, this.m / 2, 20, 16777215);
    super.a(i, j, f);
  }
  
  private static EntitySilverfish[] d = { EntitySilverfish.a, EntitySilverfish.b, EntitySilverfish.c, EntitySilverfish.d, EntitySilverfish.e, EntitySilverfish.l };
}
