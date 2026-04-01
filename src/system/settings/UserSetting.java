package system.settings;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import system.constants.Const;
import system.files.Serialization;

/**
 * Class for the User's Settings.
 * 
 * @author Hunter, Griffin, Shea
 * @version 5/6/2024
 */
public class UserSetting implements Serializable
{
  private static final long serialVersionUID = 1165788150422535658L;

  private String filePath;
  private int decimalLength;
  private boolean maxOrFix;
  private boolean thousandsSep;
  private HashMap<Character, String> shortcutMap;

  // ##########################################################
  // Constructors
  // ##########################################################

  /**
   * Constructor for default settings.
   */
  public UserSetting()
  {
    this(2, true, false);
  }

  /**
   * Constructor for reading from a file.
   * 
   * @param read
   *          If true, reads the settings from the file. If false, use default values.
   */
  public UserSetting(final boolean read)
  {
    this(2, true, false, read);
  }

  /**
   * Constructor for specific values.
   * 
   * @param decimalLength
   *          The decimal length to use
   * @param maxOrFixed
   *          Is the decimalLength max or fixed?
   * @param thousandsSep
   *          Do we use thousands separators?
   */
  public UserSetting(final int decimalLength, final boolean maxOrFixed, final boolean thousandsSep)
  {
    this(decimalLength, maxOrFixed, thousandsSep, false);
  }

  /**
   * Helper constructor to bring the other ones together.
   * 
   * @param decimalLength
   *          The decimal length to use
   * @param maxOrFixed
   *          Is the decimalLength max or fixed?
   * @param thousandsSep
   *          Do we use thousands separators?
   * @param read
   *          If true, reads the settings from the file. If false, use default values.
   */
  private UserSetting(final int decimalLength, final boolean maxOrFixed, final boolean thousandsSep,
      final boolean read)
  {
    if (!read)
    {
      this.decimalLength = decimalLength;
      this.maxOrFix = maxOrFixed;
      this.thousandsSep = thousandsSep;
      this.shortcutMap = null;
    }
    else
    {
      readSettingsFromFile();
    }
  }

  // ##########################################################
  // File I/O
  // ##########################################################

  /**
   * Reads the users settings from a .dat file.
   * 
   * @return The UserSetting object that was read
   */
  public static UserSetting readSettingsFromFile()
  {
    UserSetting settings;
    try
    {

      settings = (UserSetting) Serialization.open("UserSettings");

      // TESTING WITH PRINTS
      System.out.println(settings.getDecimalLength() + Const.SPACE + settings.isThousandsSep()
          + Const.SPACE + settings.getMaxOrFixed());
      if (settings.getKeyboardShortcuts() != null)
      {
        System.out.print("In settings: ");
        for (Character e : settings.getKeyboardShortcuts().keySet())
        {
          System.out.print(e + Const.SPACE + settings.getKeyboardShortcuts().get(e) + Const.SPACE);
        }
      }
      System.out.println();
      return settings;
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return new UserSetting(false); // if we errored out just return default

  }

  /**
   * Set to default.
   */
  public void resetSettings()
  {
    setDecimalLength(2);
    setMaxOrFixed(true);
    setThousandsSep(false);
  }

  // ##########################################################
  // Getters
  // ##########################################################

  /**
   * Getter for the Keyboard Shortcuts.
   * 
   * @return The Keyboard Shortcuts Map
   */
  public HashMap<Character, String> getKeyboardShortcuts()
  {
    return shortcutMap;
  }

  /**
   * Getter for the Decimal Length setting.
   * 
   * @return The Decimal Length
   */
  public int getDecimalLength()
  {
    return decimalLength;
  }

  /**
   * Getter for the Max or Fixed setting.
   * 
   * @return True for max, false if fixed
   */
  public boolean getMaxOrFixed()
  {
    return maxOrFix;
  }

  /**
   * Getter for the ThousandsSeparator boolean setting.
   * 
   * @return True if thousandsSeparator, false if not being used
   */
  public boolean isThousandsSep()
  {
    return thousandsSep;
  }

  // ##########################################################
  // Setters
  // ##########################################################

  /**
   * Setter for the Keyboard Shortcuts Map.
   * 
   * @param shortcuts
   *          The Map of Keyboard Shortcuts
   */
  public void setKeyboardShorcuts(final HashMap<Character, String> shortcuts)
  {
    this.shortcutMap = shortcuts;
  }

  /**
   * Setter for the Decimal Length.
   * 
   * @param decimalLength
   *          The length to set to
   */
  public void setDecimalLength(final int decimalLength)
  {
    this.decimalLength = decimalLength;
  }

  public void setMaxOrFixed(boolean maxOrFixed)
  {
    this.maxOrFix = maxOrFixed;
  }

  /**
   * Setter for the Thousands Separator.
   * 
   * @param thousandsSep
   *          The boolean if we use thousandsSep or not
   */
  public void setThousandsSep(final boolean thousandsSep)
  {
    this.thousandsSep = thousandsSep;
  }

}
