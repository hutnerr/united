package system.constants;

import java.util.MissingResourceException;
import java.util.ResourceBundle;



/**
 * Helper class to store the text that is internationalized.
 * 
 * @author Hunter Baker
 * @version 5/6/2024
 */
public class Lang
{
  public static final ResourceBundle STRINGS = ResourceBundle
      .getBundle(UnitEDFilepath.INTERNATIONALIZATION);

  // This order SHOULD BE the same as it is setup in the String bundle files

  public static final String FILE = STRINGS.getString("FILE");
  public static final String NEW = STRINGS.getString("NEW");
  public static final String PRINT_SESSION = STRINGS.getString("PRINT_SESSION");
  public static final String EXIT = STRINGS.getString("EXIT");
  public static final String HELP = STRINGS.getString("HELP");
  public static final String ABOUT = STRINGS.getString("ABOUT");
  public static final String CUSTOM_UNIT = STRINGS.getString("CUSTOM_UNIT");
  public static final String ADD = STRINGS.getString("ADD");
  public static final String RESET = STRINGS.getString("RESET");
  public static final String REFRESH = STRINGS.getString("REFRESH");
  public static final String HISTORY = STRINGS.getString("HISTORY");
  public static final String PRINT = STRINGS.getString("PRINT");
  public static final String PREVIEW = STRINGS.getString("PREVIEW");
  public static final String PRINT_SETUP = STRINGS.getString("PRINT_SETUP");
  public static final String OK = STRINGS.getString("OK");
  public static final String CANCEL = STRINGS.getString("CANCEL");
  public static final String ERROR_MESSAGE = STRINGS.getString("ERROR_MESSAGE");
  public static final String ERROR = STRINGS.getString("ERROR");
  public static final String CALCULATON_HISTORY = STRINGS.getString("CALCULATON_HISTORY");
  public static final String CALCULATOR = STRINGS.getString("CALCULATOR");
  public static final String NUM_SEPERATOR = STRINGS.getString("NUM_SEPERATOR");
  public static final String OPERATOR_ERROR = STRINGS.getString("OPERATOR_ERROR");
  public static final String UNIT_NOT_FOUND_ERROR = STRINGS.getString("UNIT_NOT_FOUND_ERROR");
  public static final String CANNOT_CONVERT_ERROR = STRINGS.getString("CANNOT_CONVERT_ERROR");
  public static final String ABOUT_MESSAGE = STRINGS.getString("ABOUT_MESSAGE");
  public static final String UNIT_NAME = STRINGS.getString("UNIT_NAME");
  public static final String HELP_PAGE = STRINGS.getString("HELP_PAGE");
  public static final String PREFERENCES = STRINGS.getString("PREFERENCES");
  public static final String OPEN = STRINGS.getString("OPEN");
  public static final String SAVE = STRINGS.getString("SAVE");
  public static final String SAVE_AS = STRINGS.getString("SAVE_AS");
  public static final String EDIT = STRINGS.getString("EDIT");
  public static final String FORMAT = STRINGS.getString("FORMAT");
  public static final String THOUSAND_SEPERATOR = STRINGS.getString("THOUSAND_SEPERATOR");
  public static final String KEYBOARD_SHORTCUT = STRINGS.getString("KEYBOARD_SHORTCUT");
  public static final String RESULT_FORMATTER = STRINGS.getString("RESULT_FORMATTER");
  public static final String ADD_NEW_SHORTCUT = STRINGS.getString("ADD_NEW_SHORTCUT");
  public static final String NUMBER_OF_DIGITS = STRINGS.getString("NUMBER_OF_DIGITS");
  public static final String MAXIMUM = STRINGS.getString("MAXIMUM");
  public static final String FIXED = STRINGS.getString("FIXED");
  public static final String DIVIDE_BY_ZERO_ERROR = STRINGS.getString("DIVIDE_BY_ZERO_ERROR");
  public static final String APPLY = STRINGS.getString("APPLY");
  public static final String PRESS_A_KEY = STRINGS.getString("PRESS_A_KEY");
  public static final String NEW_WINDOW = STRINGS.getString("NEW_WINDOW");
  public static final String ADD_CUSTOM_UNITS = STRINGS.getString("ADD_CUSTOM_UNITS");
  public static final String RESET_CUSTOM_UNITS = STRINGS.getString("RESET_CUSTOM_UNITS");
  public static final String HISTORYERROR = STRINGS.getString("HISTORYERROR");
  public static final String UNIT_TYPE = "Unit Type";
  public static final String REFRESH_CUSTOM_UNITS = STRINGS.getString("REFRESH_CUSTOM_UNITS");

}
