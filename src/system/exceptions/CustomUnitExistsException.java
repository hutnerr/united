package system.exceptions;

import system.constants.Lang;

/**
 * Custom exception class for Custom Units that already exist.
 * 
 * @author Hunter Baker
 * @version 5/6/2024
 */
public class CustomUnitExistsException extends Exception
{

  private static final long serialVersionUID = 4301377501590406497L;

  /**
   * Default constructor.
   */
  public CustomUnitExistsException()
  {
    super("ERROR - Custom Unit Already Exists");
  }

  /**
   * Overloaded constructor with message.
   * 
   * @param unit
   *          The Unit that already exists.
   */
  public CustomUnitExistsException(final String unit)
  {
    // The unit ( %s ) already exists.
    // The %s is replaced by the unit that is passed in
    super(String.format(Lang.ERROR_MESSAGE, unit));
  }
}
