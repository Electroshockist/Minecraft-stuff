package moapi;

import java.util.LinkedList;

public class ModMultiOption
  extends ModOption<String>
{
  private LinkedList<String> values = new LinkedList();
  
  public ModMultiOption(String name)
  {
    this(name, name);
  }
  
  public ModMultiOption(String name, String[] values)
  {
    this(name, name, values);
  }
  
  public ModMultiOption(String id, String name, String[] values)
  {
    this(id, name);
    if (values.length > 0)
    {
      this.value = values[0];
      this.localValue = values[0];
      for (String value : values) {
        this.values.add(value);
      }
    }
  }
  
  public ModMultiOption(String id, String name)
  {
    super(id, name);
  }
  
  public void addValue(String value)
  {
    if (this.values.size() == 0)
    {
      this.value = value;
      this.localValue = value;
    }
    this.values.add(value);
  }
  
  public String getNextValue(String s)
  {
    int index = 0;
    for (int x = 0; x < this.values.size(); x++) {
      if (((String)this.values.get(x)).equals(s)) {
        index = x;
      }
    }
    return (String)this.values.get((index + 1) % this.values.size());
  }
}
