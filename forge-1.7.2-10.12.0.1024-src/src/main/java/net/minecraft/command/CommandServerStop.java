package net.minecraft.command;

import moapi.gui.ModMenu;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.nbt.NBTTagIntArray;

public class CommandServerStop
  extends EntityAISit
{
  public CommandServerStop(EntityTameable par1EntityTameable) {
		super(par1EntityTameable);
		// TODO Auto-generated constructor stub
	}

private int a;
  private int b;
  

  
  public void a()
  {
    this.a = 0;
    this.o.clear();
    byte byte0 = -16;
    this.o.add(new ContainerChest(1, this.m / 2 - 100, this.n / 4 + 120 + byte0, "Save and quit to title"));
    if (this.l.l()) {
      ((ContainerChest)this.b.get(0)).numRows = "Disconnect";
    }
    this.o.add(new ContainerChest(4, this.m / 2 - 100, this.n / 4 + 24 + byte0, "Back to game"));
    this.o.add(new ContainerChest(0, this.m / 2 - 100, this.n / 4 + 96 + byte0, "Options..."));
    this.o.add(new ContainerChest(5, this.m / 2 - 100, this.n / 4 + 48 + byte0, 98, 20, CallableTagCompound2.a("gui.achievements")));
    this.o.add(new ContainerChest(6, this.m / 2 + 2, this.n / 4 + 48 + byte0, 98, 20, CallableTagCompound2.a("gui.stats")));
    



    this.o.add(new ContainerChest(30, this.m / 2 - 100, this.n / 4 + 148 + byte0, "Mod World Options"));
  }
  
  protected void a(ContainerChest guibutton)
  {
    if (guibutton.windowId == 0) {
      this.l.a(new IBehaviorDispenseItem(this, this.l.z));
    }
    if (guibutton.windowId == 1)
    {
      this.l.J.a(Packet201PlayerInfo.j, 1);
      if (this.l.l()) {
        this.l.f.g();
      }
      this.l.a(null);
      this.l.a(new EntityWolf());
    }
    if (guibutton.windowId == 4)
    {
      this.l.a(null);
      this.l.g();
    }
    if (guibutton.windowId == 5) {
      this.l.a(new EnumEntitySize(this.l.J));
    }
    if (guibutton.windowId == 6) {
      this.l.a(new NBTTagIntArray(this, this.l.J));
    }
    if (guibutton.windowId == 30) {
      if (this.l.l())
      {
        String[] parts = this.l.z.F.split("_");
        String name = parts[0];
        this.l.a(new ModMenu(this, name, true));
      }
      else
      {
        String name = this.l.f.x().j();
        this.l.a(new ModMenu(this, name, false));
      }
    }
  }
  
  public void p_()
  {
    super.p_();
    this.b += 1;
  }
  
  public void a(int i, int j, float f)
  {
    k();
    boolean flag = !this.l.f.c(this.a++);
    if ((flag) || (this.b < 20))
    {
      float f1 = (this.b % 10 + f) / 10.0F;
      f1 = Packet32EntityLook.a(f1 * 3.141593F * 2.0F) * 0.2F + 0.8F;
      int k = (int)(255.0F * f1);
      b(this.q, "Saving level..", 8, this.n - 16, k << 16 | k << 8 | k);
    }
    a(this.q, "Game menu", this.m / 2, 40, 16777215);
    super.a(i, j, f);
  }
}
