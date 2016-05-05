package moapi;

public class ModTextOption
  extends ModOption<String>
{
  private int maxLength = 0;
  
  public ModTextOption(String name)
  {
    this(name, name);
  }
  
  public ModTextOption(String id, String name)
  {
    this(id, name, 0);
  }
  
  public ModTextOption(String name, Integer maxLen)
  {
    this(name, name, maxLen.intValue());
  }
  
  public ModTextOption(String id, String name, Integer maxLen)
  {
    this(id, name, maxLen.intValue());
  }
  
  public ModTextOption(String name, int maxLen)
  {
    this(name, name, maxLen);
  }
  
  public ModTextOption(String id, String name, int maxLen)
  {
    super(id, name);
    setGlobalValue("");
    setMaxLength(maxLen);
  }
  
  public void setMaxLength(int maxlen)
  {
    if (maxlen < 0) {
      maxlen = 0;
    }
    this.maxLength = maxlen;
  }
  
  public void setMaxLength(Integer maxlen)
  {
    if (maxlen.intValue() < 0) {
      maxlen = new Integer(0);
    }
    this.maxLength = maxlen.intValue();
  }
  
  public int getMaxLength()
  {
    return this.maxLength;
  }
}
