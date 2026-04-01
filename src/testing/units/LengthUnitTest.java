package testing.units;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import math.units.LengthUnit;

/**
 * LengthUnit testing class.
 */
class LengthUnitTest
{

  /**
   * Test method for {@link units.LengthUnit#getAbbreviation()}.
   */
  @Test
  void testGetAbbreviation()
  {
    assertEquals("mm", LengthUnit.MILLIMETER.getAbbreviation());
    assertEquals("cm", LengthUnit.CENTIMETER.getAbbreviation());
    assertEquals("m", LengthUnit.METER.getAbbreviation());
    assertEquals("km", LengthUnit.KILOMETER.getAbbreviation());
    assertEquals("in", LengthUnit.INCH.getAbbreviation());
    assertEquals("ft", LengthUnit.FOOT.getAbbreviation());
    assertEquals("yd", LengthUnit.YARD.getAbbreviation());
    assertEquals("mi", LengthUnit.MILE.getAbbreviation());
  }

  /**
   * Test method for {@link units.LengthUnit#getConversionFactor()}.
   */
  @Test
  void testGetConversionFactor()
  {
    assertEquals(1.0, LengthUnit.MILLIMETER.getConversionFactor());
    assertEquals(10.0, LengthUnit.CENTIMETER.getConversionFactor());
    assertEquals(1000.0, LengthUnit.METER.getConversionFactor());
    assertEquals(1000000.0, LengthUnit.KILOMETER.getConversionFactor());
    assertEquals(25.4, LengthUnit.INCH.getConversionFactor());
    assertEquals(304.8, LengthUnit.FOOT.getConversionFactor());
    assertEquals(914.4, LengthUnit.YARD.getConversionFactor());
    assertEquals(1610000.0, LengthUnit.MILE.getConversionFactor());
  }

  /**
   * Test method for {@link units.Unit#convertTo(units.Unit, double)}.
   */
  @Test
  void testUnitConversion()
  {
    LengthUnit[] values = LengthUnit.values();

    for (LengthUnit unitA : values)
    {
      for (LengthUnit unitB : values)
      {
        double measurement = 420.69;
        double ratioA = unitA.getConversionFactor();
        double ratioB = unitB.getConversionFactor();
        double expectedMeasurement = measurement * ratioA / ratioB;
        double delta = 0.01;
        assertEquals(expectedMeasurement, unitA.convertTo(unitB, measurement), delta);
      }

    }
  }

}
