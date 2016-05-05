package moapi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import moapi.gui.DisplayStringFormatter;
import moapi.gui.GuiController;
import net.minecraft.client.Minecraft;

public class ModOptions
{
  private LinkedHashMap<String, ModOption> options = new LinkedHashMap();
  private LinkedHashMap<String, ModOptions> subOptions = new LinkedHashMap();
  private static final GuiController gui = new GuiController();
  private String id;
  private String name;
  private ModOptions parent = null;
  private boolean multiplayer = false;
  private boolean singleplayer = true;
  private static final Pattern illegalNamePattern = Pattern.compile("[/\\?%*:|\"<>]+");
  
  public ModOptions(String name)
  {
    this(name, name);
  }
  
  public ModOptions(String name, ModOptions p)
  {
    this(name, name);
    this.parent = p;
  }
  
  public ModOptions(String id, String name)
  {
    setID(id);
    this.name = name;
  }
  
  public ModOption addOption(ModOption option)
  {
    this.options.put(option.getID(), option);
    return option;
  }
  
  public ModOption addTextOption(String name)
  {
    ModTextOption option = new ModTextOption(name);
    return addOption(option);
  }
  
  public ModOption addTextOption(String name, String value)
  {
    ModTextOption option = new ModTextOption(name);
    option.setGlobalValue(value);
    return addOption(option);
  }
  
  public ModOption addTextOption(String name, int maxlen)
  {
    ModTextOption option = new ModTextOption(name, maxlen);
    return addOption(option);
  }
  
  public ModOption addTextOption(String name, String value, int maxlen)
  {
    ModTextOption option = new ModTextOption(name, maxlen);
    option.setGlobalValue(value);
    return addOption(option);
  }
  
  public ModOption addTextOption(String name, String value, Integer maxlen)
  {
    return addTextOption(name, value, maxlen.intValue());
  }
  
  public ModOption addKeyBinding(String name)
  {
    ModKeyOption option = new ModKeyOption(name);
    return addOption(option);
  }
  
  public ModOption addKeyOption(String name)
  {
    ModKeyOption option = new ModKeyOption(name);
    return addOption(option);
  }
  
  public ModOption addMultiOption(String name, String[] values)
  {
    ModMultiOption option = new ModMultiOption(name, values);
    return addOption(option);
  }
  
  public ModOption addMappedMultiOption(String name, Integer[] keys, String[] values)
    throws IndexOutOfBoundsException
  {
    if (keys.length != values.length) {
      throw new IndexOutOfBoundsException("Arrays are not same length");
    }
    ModMappedMultiOption option = new ModMappedMultiOption(name);
    for (int x = 0; x < keys.length; x++) {
      option.addValue(keys[x], values[x]);
    }
    return addOption(option);
  }
  
  public ModOption addMappedMultiOption(String name, int[] keys, String[] values)
    throws IndexOutOfBoundsException
  {
    if (keys.length != values.length) {
      throw new IndexOutOfBoundsException("Arrays are not same length");
    }
    ModMappedMultiOption option = new ModMappedMultiOption(name);
    for (int x = 0; x < keys.length; x++) {
      option.addValue(new Integer(keys[x]), values[x]);
    }
    return addOption(option);
  }
  
  public ModOption addToggle(String name)
  {
    ModBooleanOption option = new ModBooleanOption(name);
    return addOption(option);
  }
  
  public ModOption addSlider(String name)
  {
    ModSliderOption option = new ModSliderOption(name);
    return addOption(option);
  }
  
  public ModOption addSlider(String name, int low, int high)
  {
    ModSliderOption option = new ModSliderOption(name, low, high);
    return addOption(option);
  }
  
  public ModOptions addSubOptions(ModOptions m)
  {
    m.setParent(this);
    this.subOptions.put(m.getID(), m);
    
    return this;
  }
  
  public boolean containsSubOptions(String id)
  {
    return this.subOptions.containsKey(id);
  }
  
  public ModOptions[] getSubOptions()
  {
    Set<Map.Entry<String, ModOptions>> s = this.subOptions.entrySet();
    ModOptions[] m = new ModOptions[s.size()];
    int i = 0;
    for (Map.Entry<String, ModOptions> e : s)
    {
      m[i] = ((ModOptions)e.getValue());
      i++;
    }
    return m;
  }
  
  public ModOptions[] getMultiplayerSubOptions()
  {
    Set<Map.Entry<String, ModOptions>> s = this.subOptions.entrySet();
    LinkedList<ModOptions> m = new LinkedList();
    for (Map.Entry<String, ModOptions> e : s) {
      if (((ModOptions)e.getValue()).isMultiplayerMod()) {
        m.add(e.getValue());
      }
    }
    return (ModOptions[])m.toArray(new ModOptions[0]);
  }
  
  public ModOptions[] getSingleplayerSubOptions()
  {
    Set<Map.Entry<String, ModOptions>> s = this.subOptions.entrySet();
    LinkedList<ModOptions> m = new LinkedList();
    for (Map.Entry<String, ModOptions> e : s) {
      if (((ModOptions)e.getValue()).isSingleplayerMod()) {
        m.add(e.getValue());
      }
    }
    return (ModOptions[])m.toArray(new ModOptions[0]);
  }
  
  public ModOptions getSubOption(String id)
  {
    return (ModOptions)this.subOptions.get(id);
  }
  
  public void globalReset(boolean global)
  {
    if (((ModOptionsAPI.isMultiplayerWorld()) && (this.multiplayer)) || ((!ModOptionsAPI.isMultiplayerWorld()) && (this.singleplayer)))
    {
      ModOption[] options = getOptions();
      for (ModOption option : options) {
        option.setGlobal(global);
      }
      ModOptions[] subMenus = new ModOptions[0];
      if ((!ModOptionsAPI.isMultiplayerWorld()) && (this.singleplayer))
      {
        subMenus = getSingleplayerSubOptions();
        for (ModOptions sub : subMenus) {
          sub.globalReset(global);
        }
      }
      if ((ModOptionsAPI.isMultiplayerWorld()) && (this.multiplayer))
      {
        subMenus = getMultiplayerSubOptions();
        for (ModOptions sub : subMenus) {
          sub.globalReset(global);
        }
      }
    }
  }
  
  public ModOption getOption(String id)
  {
    return (ModOption)this.options.get(id);
  }
  
  public String getOptionValue(String id)
  {
    ModOption option = (ModOption)this.options.get(id);
    if ((option instanceof ModSliderOption)) {
      return Integer.toString(((Float)((ModSliderOption)option).getValue()).intValue());
    }
    return option.getValue().toString();
  }
  
  public String getTextValue(String id)
    throws NoSuchOptionException
  {
    ModOption option = (ModOption)this.options.get(id);
    if (option == null) {
      throw new NoSuchOptionException("No option identified by " + id);
    }
    if (!(option instanceof ModTextOption)) {
      throw new IncompatibleOptionTypeException("Option " + id + " is not a text option");
    }
    return (String)((ModTextOption)option).getValue();
  }
  
  public boolean getToggleValue(String id)
    throws NoSuchOptionException
  {
    ModOption option = (ModOption)this.options.get(id);
    if (option == null) {
      throw new NoSuchOptionException("No option identified by " + id);
    }
    if (!(option instanceof ModBooleanOption)) {
      throw new IncompatibleOptionTypeException("Option " + id + " is not a toggle option");
    }
    return ((Boolean)((ModBooleanOption)option).getValue()).booleanValue();
  }
  
  public float getSliderValue(String id)
    throws NoSuchOptionException
  {
    ModOption option = (ModOption)this.options.get(id);
    if (option == null) {
      throw new NoSuchOptionException("No option identified by " + id);
    }
    if (!(option instanceof ModSliderOption)) {
      throw new IncompatibleOptionTypeException("Option " + id + " is not a slider option");
    }
    return ((Float)((ModSliderOption)option).getValue()).floatValue();
  }
  
  public int getMappedValue(String id)
    throws NoSuchOptionException
  {
    ModOption option = (ModOption)this.options.get(id);
    if (option == null) {
      throw new NoSuchOptionException("No option identified by " + id);
    }
    if (!(option instanceof ModMappedMultiOption)) {
      throw new IncompatibleOptionTypeException("Option " + id + " is not a mapped multi option");
    }
    return ((Integer)((ModMappedMultiOption)option).getValue()).intValue();
  }
  
  public ModOptions setOptionValue(String id, boolean value)
  {
    ModOption m = getOption(id);
    if (m == null) {
      throw new NoSuchOptionException();
    }
    if ((m instanceof ModBooleanOption))
    {
      ModBooleanOption bo = (ModBooleanOption)m;
      bo.setGlobalValue(Boolean.valueOf(value));
    }
    else
    {
      throw new IncompatibleOptionTypeException();
    }
    return this;
  }
  
  public ModOptions setOptionValue(String id, Integer value)
  {
    ModOption m = getOption(id);
    if (m == null) {
      throw new NoSuchOptionException();
    }
    if ((m instanceof ModSliderOption)) {
      ((ModSliderOption)m).setGlobalValue(value.intValue());
    } else if ((m instanceof ModMappedMultiOption)) {
      ((ModMappedMultiOption)m).setGlobalValue(value);
    } else if ((m instanceof ModKeyOption)) {
      ((ModKeyOption)m).setGlobalValue(value);
    } else {
      throw new IncompatibleOptionTypeException();
    }
    return this;
  }
  
  public ModOptions setOptionValue(String id, int value)
  {
    return setOptionValue(id, new Integer(value));
  }
  
  public ModOptions setOptionValue(String id, String value)
  {
    ModOption m = getOption(id);
    if (m == null) {
      throw new NoSuchOptionException();
    }
    if ((m instanceof ModMultiOption))
    {
      ModMultiOption mo = (ModMultiOption)m;
      mo.setGlobalValue(value);
    }
    else if ((m instanceof ModTextOption))
    {
      ((ModTextOption)m).setGlobalValue(value);
    }
    else
    {
      throw new IncompatibleOptionTypeException();
    }
    return this;
  }
  
  public ModOption[] getOptions()
  {
    Set<Map.Entry<String, ModOption>> s = this.options.entrySet();
    ModOption[] m = new ModOption[s.size()];
    int i = m.length - 1;
    for (Map.Entry<String, ModOption> e : s)
    {
      m[i] = ((ModOption)e.getValue());
      i--;
    }
    return m;
  }
  
  public ModOptions setWideOption(String id)
  {
    gui.setWide(id);
    
    return this;
  }
  
  public ModOptions setWideOption(ModOption option)
  {
    return setWideOption(option.getID());
  }
  
  public ModOptions setOptionStringFormat(String id, DisplayStringFormatter formatter)
  {
    gui.setFormatter(getOption(id), formatter);
    
    return this;
  }
  
  public ModOptions addOptionFormatter(String id, DisplayStringFormatter formatter)
  {
    gui.addFormatter(getOption(id), formatter);
    
    return this;
  }
  
  public ModOptions setOptionStringFormat(ModOption option, DisplayStringFormatter formatter)
  {
    gui.setFormatter(option, formatter);
    
    return this;
  }
  
  public ModOptions addOptionFormatter(ModOption option, DisplayStringFormatter formatter)
  {
    gui.addFormatter(option, formatter);
    
    return this;
  }
  
  public String getID()
  {
    return this.id;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public ModOptions getParent()
  {
    return this.parent;
  }
  
  public ModOptions setParent(ModOptions o)
  {
    this.parent = o;
    
    return this;
  }
  
  public GuiController getGuiController()
  {
    return gui;
  }
  
  private void setID(String id)
  {
    Matcher m = illegalNamePattern.matcher(id);
    if (m.matches()) {
      throw new PatternSyntaxException("(ModOptions) Please do not use special characters for ID", "", 0);
    }
    this.id = id;
  }
  
  public ModOptions setMultiplayerMode(boolean multi)
  {
    this.multiplayer = multi;
    
    return this;
  }
  
  public ModOptions setSingleplayerMode(boolean single)
  {
    this.singleplayer = single;
    
    return this;
  }
  
  public boolean isSingleplayerMod()
  {
    return this.singleplayer;
  }
  
  public boolean isMultiplayerMod()
  {
    return this.multiplayer;
  }
  
  public ModOptions loadValues()
  {
    loadValues("", false);
    
    return this;
  }
  
  public ModOptions loadValues(String worldName, boolean multi)
  {
    for (ModOptions child : getSubOptions()) {
      child.loadValues(worldName, multi);
    }
    File file = getFile(worldName, multi);
    try
    {
      BufferedReader reader = new BufferedReader(new FileReader(file));
      HashMap map = new HashMap();
      String line;
      while ((line = reader.readLine()) != null)
      {
        String[] parts = line.split(":", 2);
        String id = parts[0];
        String value = parts[1].replace(":", "");
        map.put(id, value);
      }
      for (ModOption o : getOptions()) {
        try
        {
          if (map.containsKey(o.getID()))
          {
            String val = (String)map.get(o.getID());
            boolean global = worldName.length() == 0;
            if ((o instanceof ModSliderOption))
            {
              ModSliderOption s = (ModSliderOption)o;
              s.setValue(Float.valueOf(Float.parseFloat(val)), global);
            }
            else if ((o instanceof ModMultiOption))
            {
              ModMultiOption t = (ModMultiOption)o;
              t.setValue(val, global);
            }
            else if ((o instanceof ModBooleanOption))
            {
              ModBooleanOption b = (ModBooleanOption)o;
              b.setValue(Boolean.valueOf(val), global);
            }
            else if ((o instanceof ModMappedMultiOption))
            {
              ModMappedMultiOption t = (ModMappedMultiOption)o;
              t.setValue(Integer.valueOf(Integer.parseInt(val)), global);
            }
            else if ((o instanceof ModKeyOption))
            {
              ModKeyOption k = (ModKeyOption)o;
              Integer key = Integer.valueOf(Integer.parseInt(val));
              if (key != ModKeyOption.defaultVal) {
                try
                {
                  k.setValue(key, global);
                }
                catch (KeyAlreadyBoundException e)
                {
                  System.out.println("(MOAPI) Key + " + val.charAt(0) + " already bound, please rebind in options");
                }
              }
            }
            else
            {
              o.setValue(val, global);
            }
            o.setGlobal(global);
          }
        }
        catch (NumberFormatException e)
        {
          System.err.println("(ModOptionsAPI): Could not load option value");
        }
      }
    }
    catch (FileNotFoundException e) {}catch (IOException e)
    {
      System.out.println("(ModOptionsAPI): IOException occured: " + e.getMessage());
    }
    return this;
  }
  
  public ModOptions save(String name, boolean multiplayer)
  {
    boolean global = name.length() == 0;
    
    File file = getFile(name, multiplayer);
    file.delete();
    try
    {
      PrintWriter printwriter = new PrintWriter(new FileWriter(file));
      for (ModOption o : getOptions())
      {
        Object obj = o.getValue(global);
        if ((obj != null) && ((global) || (!o.useGlobalValue()))) {
          printwriter.println(o.getID().replace(":", "") + ":" + obj.toString());
        }
      }
      printwriter.close();
    }
    catch (IOException e)
    {
      System.err.println("(ModOptionsAPI): Could not save options to " + name);
    }
    return this;
  }
  
  public ModOptions save()
  {
    save("", false);
    
    return this;
  }
  
  private String getDir()
  {
    String subDir = getID();
    ModOptions p = this.parent;
    while (p != null)
    {
      subDir = p.getID() + "/" + subDir;
      p = p.getParent();
    }
    return Minecraft.getMinecraft() + "/ModOptions/" + subDir;
  }
  
  private File getFile(String name, boolean multiplayer)
  {
    String prefix = "";
    if (name.length() > 0) {
      if (multiplayer) {
        prefix = name + ".server.";
      } else {
        prefix = name + ".world.";
      }
    }
    String subDir = getDir();
    
    File dir = new File(subDir);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    return new File(subDir + "/" + prefix + "settings.ini");
  }
}
