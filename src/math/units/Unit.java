package math.units;

import java.io.Serializable;
import java.util.List;

import system.constants.Lang;

/**
 * Interface for the different types of Units.
 * 
 * @author Team3D
 */
public interface Unit extends Serializable
{
  /**
   * Getter for the Abbreviation.
   * 
   * @return The Abbreviation
   */
  public String getAbbreviation();

  /**
   * Getter for the Conversion Factor.
   * 
   * @return The Conversion Factor
   */
  public double getConversionFactor();

  /**
   * Method to Convert one unit into another.
   * 
   * @param unit
   *          The unit we're converting
   * @param value
   *          The value
   * @return The Converted Value
   */
  public default double convertTo(final Unit unit, final double value)
  {
    if (!this.getClass().equals(unit.getClass()))
    {
      throw new IllegalArgumentException(Lang.CANNOT_CONVERT_ERROR);
    }

    return value * this.getConversionFactor() / unit.getConversionFactor();
  }

  /**
   * Getter for the Unit List.
   * 
   * @return The Unit List
   */
  public List<String> getUnitList();

}
