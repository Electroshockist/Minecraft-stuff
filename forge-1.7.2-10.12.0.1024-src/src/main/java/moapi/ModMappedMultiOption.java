package moapi;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @deprecated
 */
public class ModMappedMultiOption
  extends ModOption<Integer>
{
  private LinkedHashMap<Integer, String> values = new LinkedHashMap();
  
  public ModMappedMultiOption(String name)
  {
    this(name, name);
  }
  
  public ModMappedMultiOption(String name, Integer[] keys, String[] labels)
  {
    this(name, name, keys, labels);
  }
  
  public ModMappedMultiOption(String name, int[] keys, String[] labels)
  {
    this(name, name, keys, labels);
  }
  
  public ModMappedMultiOption(String id, String name, Integer[] keys, String[] labels)
  {
    this(id, name);
    if (keys.length != labels.length) {
      throw new IndexOutOfBoundsException("Keys and labels must have same # of entries");
    }
    for (int x = 0; x < keys.length; x++) {
      addValue(keys[x], labels[x]);
    }
  }
  
  public ModMappedMultiOption(String id, String name, int[] keys, String[] labels)
  {
    this(id, name);
    if (keys.length != labels.length) {
      throw new IndexOutOfBoundsException("Keys and labels must have same # of entries");
    }
    for (int x = 0; x < keys.length; x++) {
      addValue(new Integer(keys[x]), labels[x]);
    }
  }
  
  public ModMappedMultiOption(String id, String name)
  {
    super(id, name);
  }
  
  public void addValue(Integer key, String value)
  {
    if (this.values.size() == 0)
    {
      this.value = key;
      this.localValue = key;
    }
    this.values.put(key, value);
  }
  
  public void addValue(int intKey, String value)
  {
    addValue(new Integer(intKey), value);
  }
  
  public String getStringValue(Integer key)
  {
    return (String)this.values.get(key);
  }
  
  public String getStringValue(int key)
  {
    return (String)this.values.get(new Integer(key));
  }
  
  public Integer getNextValue(Integer i)
  {
    Integer cur = null;
    boolean found = false;
    boolean written = false;
    
    boolean firstFound = false;
    Integer firstKey = null;
    

    Set<Map.Entry<Integer, String>> s = this.values.entrySet();
    for (Map.Entry<Integer, String> entry : s)
    {
      if (!firstFound)
      {
        firstKey = (Integer)entry.getKey();
        firstFound = true;
      }
      if (!written)
      {
        if (found)
        {
          cur = (Integer)entry.getKey();
          written = true;
        }
        if (((Integer)entry.getKey()).equals(i)) {
          found = true;
        }
      }
    }
    if (!written) {
      cur = firstKey;
    }
    return cur;
  }
  
  public Integer getNextValue(int i)
  {
    return getNextValue(new Integer(i));
  }
}
