package moapi;

public class ModBooleanOption
  extends ModOption<Boolean>
{
  private String onVal = "On";
  private String offVal = "Off";
  
  public ModBooleanOption(String name)
  {
    this(name, name);
  }
  
  public ModBooleanOption(String name, String onVal, String offVal)
  {
    this(name, name, onVal, offVal);
  }
  
  public ModBooleanOption(String id, String name, String onVal, String offVal)
  {
    this(id, name);
    this.onVal = onVal;
    this.offVal = offVal;
  }
  
  public ModBooleanOption(String id, String name)
  {
    super(id, name);
    this.value = Boolean.valueOf(true);
    this.localValue = Boolean.valueOf(true);
  }
  
  public String getStringValue(boolean value)
  {
    if (value) {
      return this.onVal;
    }
    return this.offVal;
  }
}
