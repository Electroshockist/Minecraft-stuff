package moapi.gui;

import moapi.ModSliderOption;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.ContainerChest;

import org.lwjgl.opengl.GL11;

public class Slider
  extends ContainerChest
{
  private static final int SMALL_WIDTH = 150;
  private static final int WIDE_WIDTH = 200;
  public boolean dragging;
  private ModSliderOption option;
  private GuiController gui;
  private boolean worldMode;
  
  public Slider(int i, int j, int k, ModSliderOption op, GuiController gui, boolean worldMode)
  {
    super(i, j, k, 150, 20, gui.getDisplayString(op, worldMode));
    this.gui = gui;
    this.worldMode = worldMode;
    this.dragging = false;
    this.option = op;
    this.f = gui.getDisplayString(op, worldMode);
  }
  
  public String getName()
  {
    return this.option.getName();
  }
  
  public void setWide(boolean wide)
  {
    if (wide) {
      this.a = 200;
    } else {
      this.a = 150;
    }
  }
  
  private float getInternalValue(ModSliderOption option)
  {
    float val = 0.0F;
    if (this.worldMode) {
      val = ((Float)option.getLocalValue()).floatValue();
    } else {
      val = ((Float)option.getGlobalValue()).floatValue();
    }
    val = option.transformValue(val, 0, 1);
    return val;
  }
  
  protected void b(Minecraft minecraft, int i, int j)
  {
    if (this.i)
    {
      if (this.dragging)
      {
        float value = (i - (this.c + 4)) / (this.a - 8);
        value = this.option.untransformValue(value, 0, 1);
        if (this.worldMode)
        {
          if (this.option.useGlobalValue()) {
            this.option.setGlobal(false);
          }
          this.option.setLocalValue(Float.valueOf(value));
        }
        else
        {
          this.option.setGlobalValue(Float.valueOf(value));
        }
        updateDisplayString();
      }
      float sliderValue = getInternalValue(this.option);
      
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      b(this.c + (int)(sliderValue * (this.a - 8)), this.windowId, 0, 66, 4, 20);
      b(this.c + (int)(sliderValue * (this.a - 8)) + 4, this.windowId, 196, 66, 4, 20);
    }
  }
  
  public boolean altMousePressed(Minecraft minecraft, int i, int j, boolean rightClick)
  {
    if ((rightClick) && (super.c(minecraft, i, j))) {
      return true;
    }
    if (!rightClick) {
      return c(minecraft, i, j);
    }
    return false;
  }
  
  public boolean c(Minecraft minecraft, int i, int j)
  {
    if (((!this.option.hasCallback()) || (this.option.getCallback().onClick(this.option))) && (super.c(minecraft, i, j)))
    {
      float value = (i - (this.c + 4)) / (this.a - 8);
      value = this.option.untransformValue(value, 0, 1);
      if (this.worldMode)
      {
        if (((!this.option.hasCallback()) || (this.option.getCallback().onGlobalChange(false, this.option))) && (this.option.useGlobalValue())) {
          this.option.setGlobal(false);
        }
        this.option.setLocalValue(Float.valueOf(value));
      }
      else
      {
        this.option.setGlobalValue(Float.valueOf(value));
      }
      updateDisplayString();
      this.dragging = true;
      return true;
    }
    return false;
  }
  
  public void updateDisplayString()
  {
    this.f = this.gui.getDisplayString(this.option, this.worldMode);
  }
  
  public void a(int i, int j)
  {
    this.dragging = false;
  }
  
  protected int a(boolean flag)
  {
    return 0;
  }
}
