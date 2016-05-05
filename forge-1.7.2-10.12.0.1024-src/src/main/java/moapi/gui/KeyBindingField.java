package moapi.gui;

import moapi.ModKeyOption;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.network.rcon.RConConsoleSource;

public class KeyBindingField
  extends TextInputField
{
  private boolean wide = false;
private String f;
  
  public KeyBindingField(int id, EntityAISit guiscreen, RConConsoleSource fontrenderer, int i, int j, ModKeyOption op, GuiController gui, boolean global)
  {
    super(id, i, j, fontrenderer, gui);
    this.wide = true;
    this.parentGuiScreen = guiscreen;
    this.windowId = i;
    this.windowId = j;
    this.windowId = 70;
    this.windowId = 20;
    this.option = op;
    this.global = global;
  }
  
  public void setWide(boolean wide)
  {
    wide = true;
  }
  
  private void setKey(Integer c)
  {
    ((ModKeyOption)this.option).setValue(c, this.global);
  }
  
  private Integer getKey()
  {
    return (Integer)((ModKeyOption)this.option).getValue(this.global);
  }
  
  public void textboxKeyTyped(char c, int i)
  {
    Integer val = new Integer(i);
    if ((this.global) && (isFocused())) {
      if (c == '\t') {
        this.parentGuiScreen.l();
      } else if (c != '\026') {
        if (!ModKeyOption.isKeyBound(val))
        {
          setKey(val);
          setFocused(false);
        }
        else if (val == ((ModKeyOption)this.option).getValue(this.global))
        {
          setFocused(false);
        }
      }
    }
  }
  
  public void a(Minecraft minecraft, int i, int j)
  {
    Integer key = getKey();
    String text = this.gui.getDisplayString(this.option, !this.global);
    String name = this.option.getName();
    int nameWidth = this.wide ? this.parentGuiScreen.m / 2 - this.windowId : this.parentGuiScreen.m - this.windowId;
    boolean blink = getCursorCounter() / 6 % 2 == 0;
    if ((!isFocused()) || (blink)) {
      this.f = text;
    } else {
      this.f = ("> " + text + " <");
    }
    super.a(minecraft, i, j);
    
    b(this.fontRenderer, this.option.getName(), this.windowId + 85, this.windowId + (this.windowId - 8) / 2, 16777215);
  }

private void b(RConConsoleSource fontRenderer, String name, int i, int j, int k) {
	// TODO Auto-generated method stub
	
}
}
