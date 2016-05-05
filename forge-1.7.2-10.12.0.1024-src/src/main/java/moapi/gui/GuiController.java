package moapi.gui;

import java.util.Hashtable;
import java.util.LinkedList;

import moapi.ModBooleanOption;
import moapi.ModKeyOption;
import moapi.ModMappedMultiOption;
import moapi.ModMultiOption;
import moapi.ModOption;
import moapi.ModSliderOption;
import moapi.ModTextOption;

public class GuiController
{
  private LinkedList<String> wide = new LinkedList();
  private Hashtable<ModOption, LinkedList<DisplayStringFormatter>> formatters;
  
  public GuiController()
  {
    this.formatters = new Hashtable();
  }
  
  public void setWide(String name)
  {
    this.wide.add(name);
  }
  
  public void setWide(ModOption option)
  {
    setWide(option.getID());
  }
  
  public boolean isWide(ModOption o)
  {
    if ((o instanceof ModTextOption)) {
      return true;
    }
    return this.wide.contains(o.getID());
  }
  
  public void setFormatter(ModOption option, DisplayStringFormatter formatter)
  {
    LinkedList<DisplayStringFormatter> newList = new LinkedList();
    newList.add(formatter);
    this.formatters.put(option, newList);
  }
  
  public void addFormatter(ModOption option, DisplayStringFormatter formatter)
  {
    LinkedList<DisplayStringFormatter> list;
    if (this.formatters.containsKey(option))
    {
      list = (LinkedList)this.formatters.get(option);
    }
    else
    {
      list = new LinkedList();
      this.formatters.put(option, list);
    }
    list.add(formatter);
  }
  
  public String getDisplayString(ModOption o)
  {
    return getDisplayString(o, false);
  }
  
  public String getDisplayString(ModOption o, boolean localMode)
  {
    String value = "Problem loading value";
    if ((localMode) && (o.useGlobalValue()))
    {
      value = "GLOBAL";
    }
    else if ((o instanceof ModSliderOption))
    {
      ModSliderOption o2 = (ModSliderOption)o;
      value = Float.toString(((Float)o2.getValue(!localMode)).floatValue());
    }
    else if ((o instanceof ModMappedMultiOption))
    {
      ModMappedMultiOption o2 = (ModMappedMultiOption)o;
      value = o2.getStringValue((Integer)o2.getValue(!localMode));
    }
    else if ((o instanceof ModMultiOption))
    {
      value = (String)((ModMultiOption)o).getValue(!localMode);
    }
    else if ((o instanceof ModBooleanOption))
    {
      ModBooleanOption o2 = (ModBooleanOption)o;
      value = o2.getStringValue(((Boolean)o2.getValue(!localMode)).booleanValue());
    }
    else if ((o instanceof ModTextOption))
    {
      value = (String)((ModTextOption)o).getValue(!localMode);
    }
    else if ((o instanceof ModKeyOption))
    {
      value = ModKeyOption.getKeyName((Integer)((ModKeyOption)o).getValue(!localMode));
    }
    else
    {
      value = o.getValue(!localMode).toString();
    }
    if ((!this.formatters.containsKey(o)) || ((localMode) && (o.useGlobalValue())))
    {
      if ((o instanceof ModKeyOption))
      {
        DisplayStringFormatter s = StdFormatters.noFormat;
        
        return s.manipulate(o, value);
      }
      DisplayStringFormatter s = StdFormatters.defaultFormat;
      return s.manipulate(o, value);
    }
    for (DisplayStringFormatter s : this.formatters.get(o)) {
      value = s.manipulate(o, value);
    }
    return value;
  }
}
