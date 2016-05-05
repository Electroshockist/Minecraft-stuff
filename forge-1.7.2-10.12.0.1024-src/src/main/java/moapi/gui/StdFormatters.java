package moapi.gui;

import moapi.ModOption;

public class StdFormatters
{
  public static final DefaultFormat defaultFormat = new DefaultFormat(null);
  public static final IntegerSliderFormat integerSlider = new IntegerSliderFormat();
  public static final NoFormat noFormat = new NoFormat(null);
  
  private static final class DefaultFormat
    implements DisplayStringFormatter
  {
    public String manipulate(ModOption option, String value)
    {
      return option.getName() + ": " + value;
    }
  }
  
  private static final class NoFormat
    implements DisplayStringFormatter
  {
    public String manipulate(ModOption option, String value)
    {
      return value;
    }
  }
  
  public static final class SuffixFormat
    implements DisplayStringFormatter
  {
    private String suffix;
    
    public SuffixFormat(String suffix)
    {
      this.suffix = suffix;
    }
    
    public String manipulate(ModOption option, String value)
    {
      return value + " " + this.suffix;
    }
  }
  
  public static final class IntegerSliderFormat
    implements DisplayStringFormatter
  {
    public String manipulate(ModOption option, String value)
    {
      try
      {
        float f = Float.parseFloat(value);
        int i = (int)f;
        return "" + i;
      }
      catch (NumberFormatException e)
      {
        System.out.println("(MdoOptionsAPI) Could not format " + value + " into an integer");
      }
      return option.getName() + ": " + value;
    }
  }
}
