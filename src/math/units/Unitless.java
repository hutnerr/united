package math.units;

import java.util.List;

public class Unitless implements Unit
{

  /**
   * 
   */
  private static final long serialVersionUID = -3914618146371553899L;

  @Override
  public String getAbbreviation()
  {
    return "";
  }

  @Override
  public double getConversionFactor()
  {
    // TODO Auto-generated method stub
    return 1;
  }

  public List<String> getUnitList()
  {
    return null;
  }

}
