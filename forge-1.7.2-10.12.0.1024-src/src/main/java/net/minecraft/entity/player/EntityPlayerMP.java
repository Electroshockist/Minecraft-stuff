package net.minecraft.entity.player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import moapi.ModOptionsAPI;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemFirework;
import net.minecraft.item.ItemLeaves;
import net.minecraft.item.ItemMapBase;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.world.demo.DemoWorldServer;

public class EntityPlayerMP
  extends EntityAISit
{
  public EntityPlayerMP(EntityAISit guiscreen)
  {
    this.mcServer = "Select world";
    this.managedPosX = false;
    this.k = new String[2];
    this.playerNetServerHandler = guiscreen;
  }
  
  public void a()
  {
    ItemMapBase stringtranslate = ItemMapBase.a();
    this.mcServer = stringtranslate.a("selectWorld.title");
    this.ping = stringtranslate.a("selectWorld.world");
    this.playerConqueredTheEnd = stringtranslate.a("selectWorld.conversion");
    this.k[0] = stringtranslate.a("gameMode.survival");
    this.k[1] = stringtranslate.a("gameMode.creative");
    g();
    this.playerInventoryBeingManipulated = new DispenserBehaviorArrow(this);
    this.playerInventoryBeingManipulated.a(this.o, 4, 5);
    startExecuting();
  }
  
  private void g()
  {
    IUpdatePlayerListBox isaveformat = this.l.c();
    this.destroyedItemsNetCache = isaveformat.b();
    Collections.sort(this.destroyedItemsNetCache);
    this.loadedChunks = -1;
  }
  
  protected String a(int i)
  {
    return ((ProfilerResult)this.destroyedItemsNetCache.get(i)).a();
  }
  
  protected String b(int i)
  {
    String s = ((ProfilerResult)this.destroyedItemsNetCache.get(i)).b();
    if ((s == null) || (Packet32EntityLook.a(s)))
    {
      ItemMapBase stringtranslate = ItemMapBase.a();
      s = stringtranslate.a("selectWorld.world") + " " + (i + 1);
    }
    return s;
  }
  
  public void startExecuting()
  {
    ItemMapBase stringtranslate = ItemMapBase.a();
    this.o.add(this.u = new ContainerChest(1, this.m / 2 - 154, this.n - 52, 150, 20, stringtranslate.a("selectWorld.select")));
    this.o.add(this.t = new ContainerChest(6, this.m / 2 - 154, this.n - 28, 70, 20, stringtranslate.a("selectWorld.rename")));
    this.o.add(this.v = new ContainerChest(2, this.m / 2 - 74, this.n - 28, 70, 20, stringtranslate.a("selectWorld.delete")));
    this.o.add(new ContainerChest(3, this.m / 2 + 4, this.n - 52, 150, 20, stringtranslate.a("selectWorld.create")));
    this.o.add(new ContainerChest(0, this.m / 2 + 4, this.n - 28, 150, 20, stringtranslate.a("gui.cancel")));
    this.u.h = false;
    this.t.h = false;
    this.v.h = false;
  }
  
  protected void a(ContainerChest guibutton)
  {
    if (!guibutton.h) {
      return;
    }
    if (guibutton.field_94536_g == 2)
    {
      String s = b(this.loadedChunks);
      if (s != null)
      {
        this.s = true;
        ItemMapBase stringtranslate = ItemMapBase.a();
        String s1 = stringtranslate.a("selectWorld.deleteQuestion");
        String s2 = "'" + s + "' " + stringtranslate.a("selectWorld.deleteWarning");
        String s3 = stringtranslate.a("selectWorld.deleteButton");
        String s4 = stringtranslate.a("gui.cancel");
        DemoWorldServer guiyesno = new DemoWorldServer(this, s1, s2, s3, s4, this.loadedChunks);
        this.l.a(guiyesno);
      }
    }
    else if (guibutton.field_94536_g == 1)
    {
      c(this.loadedChunks);
    }
    else if (guibutton.field_94536_g == 3)
    {
      this.l.a(new EntityPig(this));
    }
    else if (guibutton.field_94536_g == 6)
    {
      this.l.a(new EntityEnderPearl(this, a(this.loadedChunks)));
    }
    else if (guibutton.field_94536_g == 0)
    {
      this.l.a(this.playerNetServerHandler);
    }
    else
    {
      this.playerInventoryBeingManipulated.a(guibutton);
    }
  }
  
  public void c(int i)
  {
    this.l.a(null);
    if (this.managedPosX) {
      return;
    }
    this.managedPosX = true;
    int j = ((ProfilerResult)this.destroyedItemsNetCache.get(i)).f();
    if (j == 0) {
      this.l.c = new ItemFirework(this.l);
    } else {
      this.l.c = new ItemLeaves(this.l);
    }
    String s = a(i);
    if (s == null) {
      s = "World" + i;
    }
    ModOptionsAPI.selectedWorld(b(i));
    


    this.l.a(s, b(i), null);
    this.l.a(null);
  }
  
  public void a(boolean flag, int i)
  {
    if (this.s)
    {
      this.s = false;
      if (flag)
      {
        IUpdatePlayerListBox isaveformat = this.l.c();
        isaveformat.c();
        isaveformat.c(a(i));
        g();
      }
      this.l.a(this);
    }
  }
  
  public void a(int i, int j, float f)
  {
    this.playerInventoryBeingManipulated.a(i, j, f);
    a(this.q, this.mcServer, this.m / 2, 20, 16777215);
    super.a(i, j, f);
  }
  
  static List a(EntityPlayerMP guiselectworld)
  {
    return guiselectworld.destroyedItemsNetCache;
  }
  
  static int a(EntityPlayerMP guiselectworld, int i)
  {
    return guiselectworld.loadedChunks = i;
  }
  
  static int b(EntityPlayerMP guiselectworld)
  {
    return guiselectworld.loadedChunks;
  }
  
  static ContainerChest c(EntityPlayerMP guiselectworld)
  {
    return guiselectworld.u;
  }
  
  static ContainerChest d(EntityPlayerMP guiselectworld)
  {
    return guiselectworld.t;
  }
  
  static ContainerChest e(EntityPlayerMP guiselectworld)
  {
    return guiselectworld.v;
  }
  
  static String f(EntityPlayerMP guiselectworld)
  {
    return guiselectworld.ping;
  }
  
  static DateFormat g(EntityPlayerMP guiselectworld)
  {
    return guiselectworld.theItemInWorldManager;
  }
  
  static String h(EntityPlayerMP guiselectworld)
  {
    return guiselectworld.playerConqueredTheEnd;
  }
  
  static String[] i(EntityPlayerMP guiselectworld)
  {
    return guiselectworld.k;
  }
  
  private final DateFormat theItemInWorldManager = new SimpleDateFormat();
  protected EntityAISit playerNetServerHandler;
  protected String mcServer;
  private boolean managedPosX;
  private int loadedChunks;
  private List destroyedItemsNetCache;
  private DispenserBehaviorArrow playerInventoryBeingManipulated;
  private String ping;
  private String playerConqueredTheEnd;
  private String[] k;
  private boolean s;
  private ContainerChest t;
  private ContainerChest u;
  private ContainerChest v;
}
