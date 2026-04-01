package testing.units;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import math.units.WeightUnit;

/**
 * WeightUnit testing class.
 */
class WeightUnitTest
{

  /**
   * Test method for {@link units.WeightUnit#getAbbreviation()}.
   */
  @Test
  void testGetAbbreviation()
  {
    assertEquals("oz", WeightUnit.OUNCE.getAbbreviation());
    assertEquals("lb", WeightUnit.POUND.getAbbreviation());
    assertEquals("ton", WeightUnit.TON.getAbbreviation());
  }

  /**
   * Test method for {@link units.WeightUnit#getConversionFactor()}.
   */
  @Test
  void testGetConversionFactor()
  {
    assertEquals(1.0, WeightUnit.OUNCE.getConversionFactor());
    assertEquals(16.0, WeightUnit.POUND.getConversionFactor());
    assertEquals(32000.0, WeightUnit.TON.getConversionFactor());
  }

  /**
   * Test method for {@link units.Unit#convertTo(units.Unit, double)}.
   */
  @Test
  void testUnitConversion()
  {
    WeightUnit[] values = WeightUnit.values();

    for (WeightUnit unitA : values)
    {
      for (WeightUnit unitB : values)
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
