package moapi.gui;

import moapi.ModOption;
import moapi.ModOptions;
import net.minecraft.inventory.ContainerChest;

public class Button
  extends ContainerChest
{
  private ModOption option = null;
  private ModOptions options = null;
  
  public Button(int i, int j, int k, ModOption op, GuiController gui, boolean worldMode)
  {
    super(i, j, k, 200, 20, gui.getDisplayString(op, worldMode));
    
    this.option = op;
  }
  
  public Button(int i, int j, int k, int l, int i1, ModOption op, GuiController gui, boolean worldMode)
  {
    super(i, j, k, l, i1, gui.getDisplayString(op, worldMode));
    
    this.option = op;
  }
  
  public Button(int i, int j, int k, ModOptions op)
  {
    super(i, j, k, 200, 20, op.getName());
    
    this.options = op;
  }
  
  public Button(int i, int j, int k, int l, int i1, ModOptions op)
  {
    super(i, j, k, l, i1, op.getName());
    
    this.options = op;
  }
  
  public String getID()
  {
    if (this.option != null) {
      return this.option.getID();
    }
    return this.options.getID();
  }
}
