package math.units;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import system.constants.Lang;

public enum TimeUnit implements Unit
{

  SECOND("sec", 1), //
  MINUTE("min", 60), //
  HOUR("hr", 3600);

  private String abbr;
  private double factor;

  private TimeUnit(final String abbreviation, final double conversionFactor)
  {
    this.abbr = abbreviation;
    this.factor = conversionFactor;
  }

  public static TimeUnit findByAbbreviation(String abbreviation)
  {
    for (TimeUnit unit : TimeUnit.values())
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

    for (TimeUnit unit : TimeUnit.values())
    {
      unitList.add(unit.getAbbreviation());
    }

    return unitList;
  }

}
