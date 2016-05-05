package moapi.gui;

import moapi.ModTextOption;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.network.rcon.RConConsoleSource;

public class TextField
  extends TextInputField
{
  public TextField(int id, EntityAISit guiscreen, RConConsoleSource fontrenderer, int i, int j, ModTextOption op, GuiController gui, boolean global)
  {
    super(id, i, j, fontrenderer, gui);
    this.parentGuiScreen = guiscreen;
    this.windowId = (i - 50);
    this.windowId = j;
    this.a = 300;
    this.b = 20;
    this.option = op;
    this.global = global;
  }
  
  protected void setText(String s)
  {
    int maxlen = ((ModTextOption)this.option).getMaxLength();
    if ((s.length() > maxlen) && (maxlen > 0)) {
      s = s.substring(0, maxlen - 1);
    }
    ((ModTextOption)this.option).setValue(s, this.global);
  }
  
  public String getText()
  {
    return (String)((ModTextOption)this.option).getValue(this.global);
  }
  
  public void textboxKeyTyped(char c, int i)
  {
    String text = getText();
    String s = EntityAISit.h();
    int max = ((ModTextOption)this.option).getMaxLength();
    if ((this.global) && (isFocused()))
    {
      if (c == '\t')
      {
        this.parentGuiScreen.l();
      }
      else if (c == '\026')
      {
        if (s == null) {
          s = "";
        }
        int j = 32 - text.length();
        if (j > s.length()) {
          j = s.length();
        }
        if (j > 0) {
          text = text + s.substring(0, j);
        }
      }
      else if ((i == 14) && (text.length() > 0))
      {
        text = text.substring(0, text.length() - 1);
      }
      else if ((EntitySnowman.a.indexOf(c) >= 0) && ((text.length() < max) || (max == 0)))
      {
        text = text + c;
      }
      setText(text);
    }
  }
  
  public void a(Minecraft minecraft, int i, int j)
  {
    String text = getText();
    String name = this.option.getName();
    
    int maxlen = ((ModTextOption)this.option).getMaxLength();
    int len = text.length();
    int padding = 30;
    
    String counterStr = maxlen > 0 ? "(" + len + "/" + maxlen + ")" : "";
    
    int nameWidth = this.fontRenderer.a(name);
    int textWidth = this.fontRenderer.a(text);
    int counterWidth = this.fontRenderer.a(counterStr);
    if (maxlen <= 0) {
      padding -= 10;
    }
    if (nameWidth + textWidth + counterWidth + padding > this.a) {
      while (nameWidth + textWidth + counterWidth + padding > this.a)
      {
        text = text.substring(1, len - 1);
        len--;
        textWidth = this.fontRenderer.a(text);
      }
    }
    a(this.windowId - 1, this.windowId - 1, this.windowId + this.a + 1, this.windowId + this.b + 1, -6250336);
    a(this.windowId, this.windowId, this.windowId + this.a, this.windowId + this.b, -16777216);
    if (this.h)
    {
      b(this.fontRenderer, this.option.getName(), this.windowId + 4, this.windowId + (this.b - 8) / 2, 7368816);
      boolean flag = (isFocused()) && (getCursorCounter() / 6 % 2 == 0);
      
      b(this.fontRenderer, text + (flag ? "_" : ""), this.windowId + nameWidth + 10, this.windowId + (this.b - 8) / 2, 14737632);
      if (maxlen > 0) {
        b(this.fontRenderer, counterStr, this.windowId + 300 - 5 - counterWidth, this.windowId + (this.b - 8) / 2, 7368816);
      }
    }
    else
    {
      b(this.fontRenderer, getText(), this.windowId + 4, this.windowId + (this.b - 8) / 2, 7368816);
    }
  }
}
