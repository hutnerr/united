package math.units;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import system.constants.Lang;

public enum LengthUnit implements Unit
{

  MILLIMETER("mm", 1.0), //
  CENTIMETER("cm", 10.0), //
  METER("m", 1000.0), //
  KILOMETER("km", 1000000.0), //
  INCH("in", 25.4), //
  FOOT("ft", 304.8), //
  YARD("yd", 914.4), //
  MILE("mi", 1610000.0); //

  private String abbr;
  private double factor;

  private LengthUnit(String abbreviation, double conversionFactor)
  {
    this.abbr = abbreviation;
    this.factor = conversionFactor;
  }

  public static LengthUnit findByAbbreviation(String abbreviation)
  {
    for (LengthUnit unit : LengthUnit.values())
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

    for (LengthUnit unit : LengthUnit.values())
    {
      unitList.add(unit.getAbbreviation());
    }

    return unitList;
  }

}
