package math.units;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import system.constants.Lang;

/**
 * 
 */
public enum WeightUnit implements Unit
{

  OUNCE("oz", 1.0), //
  POUND("lb", 16.0), //
  TON("ton", 32000.0); //

  private String abbr;
  private double factor;

  private WeightUnit(final String abbreviation, final double conversionFactor)
  {
    this.abbr = abbreviation;
    this.factor = conversionFactor;
  }

  /**
   * 
   * @param abbreviation
   * @return
   */
  public static WeightUnit findByAbbreviation(String abbreviation)
  {
    for (WeightUnit unit : WeightUnit.values())
    {
      if (abbreviation.equals(unit.getAbbreviation()))
      {
        return unit;
      }
    }
    throw new NoSuchElementException(String.format(Lang.UNIT_NOT_FOUND_ERROR, abbreviation));
  }

  @Override
  public String getAbbreviation()
  {
    return abbr;
  }

  @Override
  public double getConversionFactor()
  {
    return factor;
  }

  @Override
  public List<String> getUnitList()
  {
    List<String> unitList = new ArrayList<>();

    for (WeightUnit unit : WeightUnit.values())
    {
      unitList.add(unit.getAbbreviation());
    }

    return unitList;
  }

}
