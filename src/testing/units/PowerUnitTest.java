package testing.units;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import math.units.PowerUnit;

/**
 * PowerUnit testing class.
 */
class PowerUnitTest
{

  /**
   * Test method for {@link units.PowerUnit#getAbbreviation()}.
   */
  @Test
  void testGetAbbreviation()
  {
    assertEquals("w", PowerUnit.WATT.getAbbreviation());
    assertEquals("kw", PowerUnit.KILOWATT.getAbbreviation());
  }

  /**
   * Test method for {@link units.PowerUnit#getConversionFactor()}.
   */
  @Test
  void testGetConversionFactor()
  {
    assertEquals(1.0, PowerUnit.WATT.getConversionFactor());
    assertEquals(1000.0, PowerUnit.KILOWATT.getConversionFactor());
  }

  /**
   * Test method for {@link units.Unit#convertTo(units.Unit, double)}.
   */
  @Test
  void testUnitConversion()
  {
    PowerUnit[] values = PowerUnit.values();

    for (PowerUnit unitA : values)
    {
      for (PowerUnit unitB : values)
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
