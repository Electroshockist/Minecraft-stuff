package moapi.gui;

import moapi.ModBooleanOption;
import moapi.ModKeyOption;
import moapi.ModMappedMultiOption;
import moapi.ModMultiOption;
import moapi.ModOption;
import moapi.ModOptions;
import moapi.ModOptionsAPI;
import moapi.ModSliderOption;
import moapi.ModTextOption;
import net.minecraft.command.CommandServerStop;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.inventory.ContainerChest;

import org.lwjgl.input.Keyboard;

public class ModMenu
  extends EntityAISit
{
  protected ContainerChest curButton = null;
  private int sliderMiddle = -1;
  private boolean draggingSlider = false;
  protected String screenTitle;
  private EntityAISit parentScreen;
  private Packet207SetScore options;
  private ModOptions modOptions = null;
  private GuiController gui = null;
  private boolean worldMode = false;
  private boolean multiplayerWorld = false;
  private String worldName;
  
  public ModMenu(EntityAISit guiscreen)
  {
    this(guiscreen, "Mod Options List", false, false);
  }
  
  public ModMenu(CommandServerStop guiscreen, String name, boolean mult)
  {
    this(guiscreen, "World Specific Mod Options", true, mult);
    this.worldName = name;
  }
  
  public ModMenu(EntityAISit guiscreen, ModOptions options, String name, boolean multi)
  {
    this(guiscreen, options.getName() + " Options", true, multi);
    this.modOptions = options;
    this.worldName = name;
    this.gui = this.modOptions.getGuiController();
  }
  
  public ModMenu(EntityAISit guiscreen, ModOptions options)
  {
    this(guiscreen, options.getName() + " Options", false, false);
    this.modOptions = options;
    this.gui = this.modOptions.getGuiController();
  }
  
  private ModMenu(EntityAISit parent, String title, boolean world, boolean mult)
  {
    this.parentScreen = parent;
    this.worldMode = world;
    this.screenTitle = title;
    this.multiplayerWorld = mult;
    if (!Keyboard.areRepeatEventsEnabled()) {
      Keyboard.enableRepeatEvents(true);
    }
  }
  
  public void a()
  {
    if (this.modOptions == null)
    {
      ModOptions[] options = new ModOptions[0];
      if (this.multiplayerWorld) {
        options = ModOptionsAPI.getMultiplayerMods();
      } else if (this.worldMode) {
        options = ModOptionsAPI.getSingleplayerMods();
      } else {
        options = ModOptionsAPI.getAllMods();
      }
      loadModList(options);
    }
    else
    {
      loadModOptions();
    }
  }
  
  private void loadModList(ModOptions[] options)
  {
    int xPos = this.m / 2 - 100;
    for (int i = 0; i < options.length; i++)
    {
      int yPos = this.n / 6 + i * 24 + 12;
      this.o.add(new Button(i, xPos, yPos, options[i]));
    }
    int yPos = this.n / 6 + 168;
    this.o.add(new ContainerChest(200, xPos, yPos, "Done"));
  }
  
  private void loadModOptions()
  {
    this.screenTitle = this.modOptions.getName();
    ModOption[] ops = this.modOptions.getOptions();
    ModOptions[] subOps;
    ModOptions[] subOps;
    if (!this.worldMode)
    {
      subOps = this.modOptions.getSubOptions();
    }
    else
    {
      ModOptions[] subOps;
      if (this.multiplayerWorld) {
        subOps = this.modOptions.getMultiplayerSubOptions();
      } else {
        subOps = this.modOptions.getSingleplayerSubOptions();
      }
    }
    int id = 0;
    
    int pos = 2;
    for (ModOption op : ops)
    {
      addModOptionButton(op, id, pos);
      if (this.gui.isWide(op)) {
        pos = pos + 2 + pos % 2;
      } else {
        pos++;
      }
      id++;
    }
    for (int x = 0; x < subOps.length; x++)
    {
      int xPos = this.m / 2 - 100;
      int yPos = this.n / 6 + 24 * (pos + 1 >> 1);
      this.o.add(new Button(x + 101, xPos, yPos, subOps[x]));
      pos = pos + 2 + pos % 2;
    }
    this.o.add(new ContainerChest(200, this.m / 2 - 100, this.n / 6 + 168, "Done"));
  }
  
  private void addModOptionButton(ModOption op, int id, int pos)
  {
    boolean isWide = this.gui.isWide(op);
    int yPos;
    int xPos;
    int yPos;
    if (!isWide)
    {
      int xPos = this.m / 2 - 155 + pos % 2 * 160;
      yPos = this.n / 6 + 24 * (pos >> 1);
    }
    else
    {
      xPos = this.m / 2 - 100;
      yPos = this.n / 6 + 24 * (pos + 1 >> 1);
    }
    if ((op instanceof ModSliderOption))
    {
      Slider btn = new Slider(id, xPos, yPos, (ModSliderOption)op, this.gui, this.worldMode);
      btn.setWide(isWide);
      this.o.add(btn);
    }
    else if ((op instanceof ModTextOption))
    {
      this.o.add(new TextField(id, this, this.q, xPos, yPos, (ModTextOption)op, this.gui, !this.worldMode));
    }
    else if ((op instanceof ModKeyOption))
    {
      KeyBindingField btn = new KeyBindingField(id, this, this.q, xPos, yPos, (ModKeyOption)op, this.gui, !this.worldMode);
      
      this.o.add(btn);
      
      btn.setWide(isWide);
    }
    else if (!isWide)
    {
      this.o.add(new Button(id, xPos, yPos, 150, 20, op, this.gui, this.worldMode));
    }
    else
    {
      this.o.add(new Button(id, xPos, yPos, op, this.gui, this.worldMode));
    }
  }
  
  public void a(int i, int j, float f)
  {
    k();
    a(this.q, this.screenTitle, this.m / 2, 20, 16777215);
    if (this.sliderMiddle == -1) {
      setInitialSlider();
    } else if (getSliderTop() > getSliderAreaTop()) {
      this.sliderMiddle = getUpperSliderBound();
    } else if (getSliderBottom() < getSliderAreaBottom()) {
      setInitialSlider();
    }
    addButtons(i, j);
    
    int sliderTop = getSliderTop();
    int sliderBottom = getSliderBottom();
    int sliderLeft = getSliderLeft();
    int sliderRight = getSliderRight();
    

    a(sliderLeft, getSliderAreaBottom(), sliderRight, getSliderAreaTop(), -2147483648);
    
    a(sliderLeft, sliderBottom, sliderRight, sliderTop, -3355444);
    sliderDragged(i, j);
  }
  
  private void addButtons(int i, int j)
  {
    int contentTop = getContentTop();
    int contentBottom = getContentBottom();
    int bottom = getSliderAreaBottom();
    int top = getSliderAreaTop();
    for (int k = 0; k < this.o.size(); k++)
    {
      ContainerChest btn = (ContainerChest)this.o.get(k);
      if (btn.field_94536_g != 200)
      {
        int y = btn.windowId;
        btn.windowId = (y - contentBottom);
        if ((btn.windowId > bottom) && (btn.windowId + 20 < top)) {
          btn.a(this.l, i, j);
        }
        btn.windowId = y;
      }
      else
      {
        btn.a(this.l, i, j);
      }
    }
  }
  
  protected void a(int i, int j, int k)
  {
    setCurrentButton(null);
    if (k == 0) {
      if ((i > getSliderLeft()) && (i < getSliderRight()) && (j > getSliderBottom()) && (j < getSliderTop())) {
        setSliderMiddle(j);
      } else if ((i > getSliderLeft()) && (i < getSliderRight()) && (j > getSliderAreaBottom()) && (j < getSliderAreaTop())) {
        setSliderMiddle(j);
      } else {
        for (int l = 0; l < this.o.size(); l++)
        {
          ContainerChest guibutton = (ContainerChest)this.o.get(l);
          if (buttonPressed(guibutton, i, j, false)) {
            setCurrentButton(guibutton);
          }
        }
      }
    }
    if (k == 1) {
      for (int l = 0; l < this.o.size(); l++)
      {
        ContainerChest guibutton = (ContainerChest)this.o.get(l);
        if (buttonPressed(guibutton, i, j, true))
        {
          altActionPerformed(guibutton);
          this.l.B.playSound("random.click", 1.0F, 1.0F);
          setCurrentButton(guibutton);
        }
      }
    }
  }
  
  protected void a(char c, int i)
  {
    if (i == 1) {
      changeScreen(null);
    } else if ((this.curButton instanceof TextInputField)) {
      handleTextAction((TextInputField)this.curButton, c, i);
    }
  }
  
  private void handleTextAction(TextInputField txt, char c, int i)
  {
    txt.textboxKeyTyped(c, i);
  }
  
  protected void a(ContainerChest guibutton)
  {
    if (guibutton.field_94536_g == 200)
    {
      changeScreen(this.parentScreen);
    }
    else if (this.modOptions == null)
    {
      Button btn = (Button)guibutton;
      
      ModOptions modOp = ModOptionsAPI.getModOptions(btn.getID());
      if (this.worldMode) {
        this.l.a(new ModMenu(this, modOp, this.worldName, this.multiplayerWorld));
      } else {
        this.l.a(new ModMenu(this, modOp));
      }
    }
    else if ((guibutton.field_94536_g >= 100) || (!(guibutton instanceof Slider)))
    {
      if ((guibutton.field_94536_g > 100) && (guibutton.field_94536_g < 200))
      {
        Button btn = (Button)guibutton;
        ModOptions modOp = this.modOptions.getSubOption(btn.getID());
        if (this.worldMode) {
          this.l.a(new ModMenu(this, modOp, this.worldName, this.multiplayerWorld));
        } else {
          this.l.a(new ModMenu(this, modOp));
        }
      }
      else if (guibutton.field_94536_g < 100)
      {
        optionButtonPressed(guibutton);
      }
    }
  }
  
  protected void altActionPerformed(ContainerChest guibutton)
  {
    if ((this.modOptions != null) && (guibutton.field_94536_g < 100) && (this.worldMode))
    {
      ModOption[] ops = this.modOptions.getOptions();
      ModOption option = ops[guibutton.field_94536_g];
      
      option.setGlobal(!option.useGlobalValue());
      
      updateDisplayString(option, guibutton);
    }
    else if ((this.worldMode) && ((this.modOptions == null) || (guibutton.field_94536_g < 200)))
    {
      ModOptions modOp = null;
      if (this.modOptions == null) {
        modOp = ModOptionsAPI.getModOptions(guibutton.numRows);
      } else {
        modOp = this.modOptions.getSubOption(guibutton.numRows);
      }
      if (modOp != null) {
        modOp.globalReset(true);
      }
    }
  }
  
  protected void b(int i, int j, int k)
  {
    if ((!(this.curButton instanceof TextInputField)) && (this.curButton != null) && (k == 0))
    {
      this.curButton.a(i, j);
      this.curButton = null;
    }
    else if ((this.draggingSlider) && (k == 0))
    {
      this.draggingSlider = false;
    }
  }
  
  public void p_()
  {
    super.p_();
    for (Object obj : this.o) {
      if ((obj instanceof TextInputField)) {
        ((TextInputField)obj).updateCursorCounter();
      }
    }
  }
  
  public void changeScreen(EntityAISit screen)
  {
    saveChanges();
    this.l.a(screen);
    if (!(screen instanceof ModMenu)) {
      Keyboard.enableRepeatEvents(false);
    }
    if ((this.worldMode) && (screen == null)) {
      this.l.g();
    }
  }
  
  private void sliderDragged(int i, int j)
  {
    if (this.draggingSlider) {
      setSliderMiddle(j);
    }
  }
  
  private void setCurrentButton(ContainerChest btn)
  {
    if ((this.curButton instanceof TextInputField)) {
      ((TextInputField)this.curButton).setFocused(false);
    }
    this.curButton = btn;
    if ((this.curButton instanceof TextInputField)) {
      ((TextInputField)this.curButton).setFocused(true);
    }
    if (this.curButton != null)
    {
      this.l.B.playSound("random.click", 1.0F, 1.0F);
      a(this.curButton);
    }
  }
  
  protected boolean buttonPressed(ContainerChest btn, int i, int j)
  {
    return buttonPressed(btn, i, j, false);
  }
  
  protected boolean buttonPressed(ContainerChest btn, int i, int j, boolean rightClick)
  {
    int contentBottom = getContentBottom();
    int bottom = getSliderAreaBottom();
    int top = getSliderAreaTop();
    boolean flag = false;
    if (btn.field_94536_g != 200)
    {
      int y = btn.windowId;
      btn.windowId = (y - contentBottom);
      if ((btn.windowId > bottom) && (btn.windowId + 20 < top)) {
        if ((btn instanceof Slider)) {
          flag = ((Slider)btn).altMousePressed(this.l, i, j, rightClick);
        } else if (btn.c(this.l, i, j)) {
          flag = true;
        }
      }
      btn.windowId = y;
    }
    else
    {
      flag = btn.c(this.l, i, j);
    }
    return flag;
  }
  
  private void setInitialSlider()
  {
    this.sliderMiddle = getLowerSliderBound();
  }
  
  private void setSliderMiddle(int val)
  {
    if (val > getUpperSliderBound()) {
      this.sliderMiddle = getUpperSliderBound();
    } else if (val < getLowerSliderBound()) {
      this.sliderMiddle = getLowerSliderBound();
    } else {
      this.sliderMiddle = val;
    }
    this.draggingSlider = true;
    setCurrentButton(this.curButton);
  }
  
  private int getUpperSliderBound()
  {
    return getSliderAreaTop() - getSliderHeight() / 2;
  }
  
  private int getLowerSliderBound()
  {
    return getSliderAreaBottom() + getSliderHeight() / 2;
  }
  
  private int getSliderTop()
  {
    return this.sliderMiddle + getSliderHeight() / 2;
  }
  
  private int getSliderBottom()
  {
    return this.sliderMiddle - getSliderHeight() / 2;
  }
  
  private int getSliderHeight()
  {
    int contHeight = getContentHeight();
    int areaHeight = getSliderAreaHeight();
    if (contHeight < areaHeight) {
      return areaHeight;
    }
    return (int)(areaHeight / contHeight * areaHeight);
  }
  
  private int getContentHeight()
  {
    int height = 1;
    int bottom = getSliderAreaBottom();
    for (int k = 0; k < this.o.size(); k++)
    {
      ContainerChest guibutton = (ContainerChest)this.o.get(k);
      if ((guibutton.field_94536_g != 200) && 
        (guibutton.windowId - bottom > height)) {
        height = guibutton.windowId - bottom;
      }
    }
    return height + 100;
  }
  
  private int getContentTop()
  {
    int top = getSliderTop() - getSliderAreaBottom();
    int contHeight = getContentHeight();
    int areaHeight = getSliderAreaHeight();
    double prop;
    double prop;
    if (contHeight < areaHeight) {
      prop = 1.0D;
    } else {
      prop = getContentHeight() / getSliderAreaHeight();
    }
    return (int)(top * prop);
  }
  
  private int getContentBottom()
  {
    int bot = getSliderBottom() - getSliderAreaBottom();
    int contHeight = getContentHeight();
    int areaHeight = getSliderAreaHeight();
    double prop;
    double prop;
    if (contHeight < areaHeight) {
      prop = 1.0D;
    } else {
      prop = getContentHeight() / getSliderAreaHeight();
    }
    return (int)(bot * prop);
  }
  
  private int getSliderLeft()
  {
    return this.m - 20;
  }
  
  private int getSliderRight()
  {
    return this.m - 10;
  }
  
  private int getSliderAreaTop()
  {
    return this.n - 40;
  }
  
  private int getSliderAreaBottom()
  {
    return 40;
  }
  
  private int getSliderAreaHeight()
  {
    int height = getSliderAreaTop() - getSliderAreaBottom();
    return height > 0 ? height : 1;
  }
  
  private void optionButtonPressed(ContainerChest btn)
  {
    ModOption[] ops = this.modOptions.getOptions();
    ModOption option = ops[btn.field_94536_g];
    if ((!option.hasCallback()) || (option.getCallback().onClick(option)))
    {
      if ((this.worldMode) && (option.useGlobalValue()) && ((!option.hasCallback()) || (option.getCallback().onGlobalChange(false, option)))) {
        option.setGlobal(false);
      }
      updateDisplayString(option, btn);
    }
  }
  
  private void updateDisplayString(ModOption option, ContainerChest btn)
  {
    if ((option instanceof ModMultiOption)) {
      btn.numRows = optionPressed((ModMultiOption)option);
    } else if ((option instanceof ModBooleanOption)) {
      btn.numRows = optionPressed((ModBooleanOption)option);
    } else if ((option instanceof ModMappedMultiOption)) {
      btn.numRows = optionPressed((ModMappedMultiOption)option);
    } else if ((btn instanceof Slider)) {
      ((Slider)btn).updateDisplayString();
    }
  }
  
  private String optionPressed(ModMultiOption op)
  {
    if (!this.worldMode)
    {
      String nextVal = op.getNextValue((String)op.getGlobalValue());
      op.setGlobalValue(nextVal);
    }
    else
    {
      String nextVal = op.getNextValue((String)op.getLocalValue());
      op.setLocalValue(nextVal);
    }
    return this.gui.getDisplayString(op, this.worldMode);
  }
  
  private String optionPressed(ModBooleanOption op)
  {
    if (!this.worldMode)
    {
      boolean nextVal = !((Boolean)op.getGlobalValue()).booleanValue();
      op.setGlobalValue(Boolean.valueOf(nextVal));
    }
    else
    {
      boolean nextVal = !((Boolean)op.getLocalValue()).booleanValue();
      op.setLocalValue(Boolean.valueOf(nextVal));
    }
    return this.gui.getDisplayString(op, this.worldMode);
  }
  
  private String optionPressed(ModMappedMultiOption op)
  {
    if (!this.worldMode)
    {
      Integer nextVal = op.getNextValue((Integer)op.getGlobalValue());
      op.setGlobalValue(nextVal);
    }
    else
    {
      Integer nextVal = op.getNextValue((Integer)op.getLocalValue());
      op.setLocalValue(nextVal);
    }
    return this.gui.getDisplayString(op, this.worldMode);
  }
  
  private void saveChanges()
  {
    if (this.modOptions != null) {
      if (this.worldMode) {
        this.modOptions.save(this.worldName, this.multiplayerWorld);
      } else {
        this.modOptions.save();
      }
    }
  }
}
