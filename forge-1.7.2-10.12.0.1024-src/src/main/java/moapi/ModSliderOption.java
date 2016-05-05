package moapi;

public class ModSliderOption
  extends ModOption<Float>
{
  private int low = 0;
  private int high = 100;
  
  public ModSliderOption(String name)
  {
    this(name, name);
  }
  
  public ModSliderOption(String name, int low, int high)
  {
    this(name, name, low, high);
  }
  
  public ModSliderOption(String id, String name, int low, int high)
  {
    this(id, name);
    this.low = low;
    this.high = high;
  }
  
  public ModSliderOption(String id, String name)
  {
    super(id, name);
    this.value = Float.valueOf(1.0F);
    this.localValue = Float.valueOf(1.0F);
  }
  
  public int getHighVal()
  {
    return this.high;
  }
  
  public int getLowVal()
  {
    return this.low;
  }
  
  private float getBoundedValue(float value, int lower, int upper)
  {
    if (value < lower) {
      return lower;
    }
    if (value > upper) {
      return upper;
    }
    return value;
  }
  
  public void setValue(Float value)
  {
    super.setValue(Float.valueOf(getBoundedValue(value.floatValue(), this.low, this.high)));
  }
  
  public void setValue(int value)
  {
    setValue(Float.valueOf(value));
  }
  
  public void setLocalValue(Float value)
  {
    super.setLocalValue(Float.valueOf(getBoundedValue(value.floatValue(), this.low, this.high)));
  }
  
  public void setLocalValue(int value)
  {
    setLocalValue(Float.valueOf(value));
  }
  
  public void setGlobalValue(Float value)
  {
    super.setGlobalValue(Float.valueOf(getBoundedValue(value.floatValue(), this.low, this.high)));
  }
  
  public void setGlobalValue(int value)
  {
    setGlobalValue(Float.valueOf(value));
  }
  
  public float transformValue(float value, int lower, int upper)
  {
    value = getBoundedValue(value, this.low, this.high);
    
    float base = (value - this.low) / (this.high - this.low);
    
    float out = base * (upper - lower) + lower;
    return out;
  }
  
  public float untransformValue(float value, int lower, int upper)
  {
    value = getBoundedValue(value, lower, upper);
    
    float base = (value - lower) / (upper - lower);
    
    float out = value * (this.high - this.low) + this.low;
    
    return out;
  }
}
