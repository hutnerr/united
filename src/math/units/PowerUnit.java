package math.units;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import system.constants.Lang;

public enum PowerUnit implements Unit
{

  WATT("w", 1), //
  KILOWATT("kw", 1000);

  private String abbr;
  private double factor;

  private PowerUnit(final String abbreviation, final double conversionFactor)
  {
    this.abbr = abbreviation;
    this.factor = conversionFactor;
  }

  public static PowerUnit findByAbbreviation(String abbreviation)
  {
    for (PowerUnit unit : PowerUnit.values())
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

  public List<String> getUnitList()
  {
    List<String> unitList = new ArrayList<>();

    for (PowerUnit unit : PowerUnit.values())
    {
      unitList.add(unit.getAbbreviation());
    }

    return unitList;
  }

}
