package math.operators;

import java.util.NoSuchElementException;

import math.expressions.Operand;
import system.constants.Lang;

public enum UnaryOperators implements UnaryOperatorInterface
{
  INVERT("1/x")
  {
    double val;
    String unit;

    public String evaluate(Operand value) throws ArithmeticException
    {
      if (value.value() == 0)
      {
        throw new ArithmeticException();
      }
      if (value.unit() == null)
      {
        val = 1 / value.value();
        unit = "";
      }
      else
      {
        val = 1 / value.value();
        unit = value.unit().getAbbreviation();
      }

      return formattedResult(unit, val);
    }
  };

  private String id;
  private static String formatted = "%.2f";
  static boolean formatMax = false;

  private UnaryOperators(String id)
  {
    this.id = id;
  }

  public static UnaryOperators findById(String id)
  {
    for (UnaryOperators op : UnaryOperators.values())
    {
      if (id.equals(op.id))
      {
        return op;
      }
    }

    throw new NoSuchElementException(String.format(Lang.OPERATOR_ERROR, id));
  }

  private static String formattedResult(String unit, final double value)
  {
    String format = String.format(formatted + " %s", value, unit);

    return format;
  }

  public static void formatFixed(int adjustment)
  {
    formatted = "%." + adjustment + "f";
    formatMax = false;
  }

  public static void formatMax(int adjustment)
  {
    formatted = "%." + adjustment + "f";
    formatMax = true;
  }
}
