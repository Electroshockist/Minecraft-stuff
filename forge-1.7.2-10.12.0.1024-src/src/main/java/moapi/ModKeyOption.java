package moapi;

import java.util.Hashtable;
import org.lwjgl.input.Keyboard;

public class ModKeyOption
  extends ModOption<Integer>
{
  private static Hashtable<Integer, ModOption> bindings = new Hashtable();
  public static final Integer defaultVal = Integer.valueOf(0);
  
  public ModKeyOption(String name)
  {
    this(name, name);
  }
  
  public ModKeyOption(String id, String name)
  {
    super(id, name);
    
    setValue(defaultVal, true);
    setValue(defaultVal, false);
  }
  
  public void setValue(int value)
  {
    setValue(new Integer(value), this.global);
  }
  
  public void setValue(Integer value)
  {
    setValue(value, this.global);
  }
  
  public void setValue(int value, boolean scope)
  {
    setValue(new Integer(value), scope);
  }
  
  public void setValue(Integer value, boolean scope)
  {
    Integer curVal = (Integer)getValue(scope);
    if (value == defaultVal)
    {
      bindings.remove(value);
      super.setValue(value, this.global);
    }
    else if (((getLocalValue() == value) && (!this.global)) || ((getGlobalValue() == value) && (this.global)) || (!isKeyBound(value)))
    {
      if ((curVal != null) && (bindings.containsKey(curVal))) {
        bindings.remove(curVal);
      }
      super.setValue(value, scope);
      bindings.put(value, this);
    }
    else
    {
      throw new KeyAlreadyBoundException(value);
    }
  }
  
  public static boolean isKeyBound(Integer c)
  {
    return (!c.equals(defaultVal)) && (bindings.containsKey(c));
  }
  
  public static String getKeyName(Integer key)
  {
    String val = Keyboard.getKeyName(key.intValue());
    if (val == null) {
      return "INVALID";
    }
    return val;
  }
}
