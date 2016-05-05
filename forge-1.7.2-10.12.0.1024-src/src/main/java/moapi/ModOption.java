package moapi;

public abstract class ModOption<E>
{
  private String id;
  protected String name;
  protected E value;
  protected E localValue;
  protected boolean global = true;
  protected MOCallback callback = null;
  
  protected ModOption(String id)
  {
    setID(id);
    setName(id);
  }
  
  protected ModOption(String id, String name)
  {
    setID(id);
    setName(name);
  }
  
  private final void setID(String id)
  {
    this.id = id;
  }
  
  protected void setName(String name)
  {
    this.name = name;
  }
  
  public void setValue(E value)
  {
    if (this.global) {
      this.value = value;
    } else {
      this.localValue = value;
    }
  }
  
  public void setValue(E value, boolean scope)
  {
    if (scope) {
      this.value = value;
    } else {
      this.localValue = value;
    }
  }
  
  public void setLocalValue(E value)
  {
    setValue(value, false);
  }
  
  public void setGlobalValue(E value)
  {
    setValue(value, true);
  }
  
  public void setGlobal(boolean global)
  {
    this.global = global;
  }
  
  public void setCallback(MOCallback callback)
  {
    this.callback = callback;
  }
  
  public final String getID()
  {
    return this.id;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public E getValue()
  {
    if (this.global) {
      return this.value;
    }
    return this.localValue;
  }
  
  public E getValue(boolean scope)
  {
    if (this.global) {
      return getGlobalValue();
    }
    return getLocalValue();
  }
  
  public E getGlobalValue()
  {
    return this.value;
  }
  
  public E getLocalValue()
  {
    return this.localValue;
  }
  
  public boolean useGlobalValue()
  {
    return this.global;
  }
  
  public MOCallback getCallback()
  {
    return this.callback;
  }
  
  public boolean hasCallback()
  {
    return this.callback instanceof MOCallback;
  }
}
