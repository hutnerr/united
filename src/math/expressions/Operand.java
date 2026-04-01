package math.expressions;

import java.io.Serializable;

import math.units.Unit;

public record Operand(Unit unit, double value) implements Serializable
{

  public boolean isSameUnitType(Operand other)
  {
    return this.unit().getClass().equals(other.unit().getClass());
  }

  public Operand convertUnit(Unit to)
  {
    return new Operand(to, unit.convertTo(to, value));
  }

  @Override
  public String toString()
  {
    return String.format("%f %s", value, unit.getAbbreviation());
  }

}
