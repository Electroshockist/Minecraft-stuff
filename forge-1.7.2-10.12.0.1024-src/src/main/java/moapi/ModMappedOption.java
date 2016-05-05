package moapi;

/**
 * @deprecated
 */
public class ModMappedOption
  extends ModMappedMultiOption
{
  public ModMappedOption(String name)
  {
    super(name, name);
  }
  
  public ModMappedOption(String name, Integer[] keys, String[] labels)
  {
    super(name, name, keys, labels);
  }
  
  public ModMappedOption(String name, int[] keys, String[] labels)
  {
    super(name, name, keys, labels);
  }
  
  public ModMappedOption(String id, String name, Integer[] keys, String[] labels)
  {
    super(id, name, keys, labels);
  }
  
  public ModMappedOption(String id, String name, int[] keys, String[] labels)
  {
    super(id, name, keys, labels);
  }
  
  public ModMappedOption(String id, String name)
  {
    super(id, name);
  }
}
