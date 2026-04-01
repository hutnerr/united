package testing.units;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import math.units.TimeUnit;

/**
 * TimeUnit testing class.
 */
class TimeUnitTest
{

  /**
   * Test method for {@link units.TimeUnit#getAbbreviation()}.
   */
  @Test
  void testGetAbbreviation()
  {
    assertEquals("sec", TimeUnit.SECOND.getAbbreviation());
    assertEquals("min", TimeUnit.MINUTE.getAbbreviation());
    assertEquals("hr", TimeUnit.HOUR.getAbbreviation());
  }

  /**
   * Test method for {@link units.TimeUnit#getConversionFactor()}.
   */
  @Test
  void testGetConversionFactor()
  {
    assertEquals(1.0, TimeUnit.SECOND.getConversionFactor());
    assertEquals(60.0, TimeUnit.MINUTE.getConversionFactor());
    assertEquals(3600.0, TimeUnit.HOUR.getConversionFactor());
  }

  /**
   * Test method for {@link units.Unit#convertTo(units.Unit, double)}.
   */
  @Test
  void testUnitConversion()
  {
    TimeUnit[] values = TimeUnit.values();

    for (TimeUnit unitA : values)
    {
      for (TimeUnit unitB : values)
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
