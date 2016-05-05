package moapi.gui;

import moapi.ModOption;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.network.rcon.RConConsoleSource;

public abstract class TextInputField
  extends ContainerChest
{
  protected ModOption option;
  protected boolean global;
  protected final RConConsoleSource fontRenderer;
  private boolean isFocused = false;
  protected EntityAISit parentGuiScreen;
  private int cursorCounter;
  protected GuiController gui;
  
  public TextInputField(int i, int j, int k, RConConsoleSource r, GuiController gui)
  {
    super(i, j, k, "");
    this.gui = gui;
    this.isFocused = true;
    this.fontRenderer = r;
  }
  
  public abstract void textboxKeyTyped(char paramChar, int paramInt);
  
  public void a(Minecraft minecraft, int i, int j)
  {
    super.a(minecraft, i, j);
  }
  
  protected int getCursorCounter()
  {
    return this.cursorCounter;
  }
  
  public void updateCursorCounter()
  {
    this.cursorCounter += 1;
  }
  
  public void setFocused(boolean flag)
  {
    if ((flag) && (!this.isFocused)) {
      this.cursorCounter = 0;
    }
    this.isFocused = flag;
  }
  
  public boolean isFocused()
  {
    return this.isFocused;
  }
  
  public ModOption getOption()
  {
    return this.option;
  }
}
