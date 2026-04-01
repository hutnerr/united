package math.units;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import system.constants.Lang;

public enum VolumeUnit implements Unit
{

  PINT("pt", 473.176), //
  QUART("qt", 946.353), //
  GALLON("gal", 3785.41), //
  LITER("l", 1000.0), //
  CUBIC_CENTIMETER("cc", 1.0); //

  private String abbr;
  private double factor;

  private VolumeUnit(final String abbreviation, final double conversionFactor)
  {
    this.abbr = abbreviation;
    this.factor = conversionFactor;
  }

  public static VolumeUnit findByAbbreviation(String abbreviation)
  {
    for (VolumeUnit unit : VolumeUnit.values())
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

    for (VolumeUnit unit : VolumeUnit.values())
    {
      unitList.add(unit.getAbbreviation());
    }

    return unitList;
  }

}
