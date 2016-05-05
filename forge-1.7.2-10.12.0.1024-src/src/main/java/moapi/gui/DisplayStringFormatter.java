package moapi.gui;

import moapi.ModOption;

public abstract interface DisplayStringFormatter
{
  public abstract String manipulate(ModOption paramModOption, String paramString);
}
