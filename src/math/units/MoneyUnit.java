package math.units;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import system.constants.Lang;

public enum MoneyUnit implements Unit
{

  CENT("c", 1.0), //
  DOLALR("$", 100.0); //

  private String abbr;
  private double factor;

  private MoneyUnit(final String abbreviation, final double conversionFactor)
  {
    this.abbr = abbreviation;
    this.factor = conversionFactor;
  }

  public static MoneyUnit findByAbbreviation(String abbreviation)
  {
    for (MoneyUnit unit : MoneyUnit.values())
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

    for (MoneyUnit unit : MoneyUnit.values())
    {
      unitList.add(unit.getAbbreviation());
    }

    return unitList;
  }

}
