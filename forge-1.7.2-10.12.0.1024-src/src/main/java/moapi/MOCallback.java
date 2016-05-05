package moapi;

public abstract class MOCallback
{
  public abstract boolean onClick(ModOption paramModOption);
  
  public boolean onGlobalChange(boolean newValue, ModOption option)
  {
    return true;
  }
}
