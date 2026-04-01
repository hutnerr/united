package math.operators;

import java.util.NoSuchElementException;

import math.expressions.Operand;
import math.units.Unit;
import system.constants.Const;
import system.constants.Lang;

/**
 * An encapsulation of the 4 binary operators supported by the Calculator class.
 */
public enum BinaryOperator implements BinaryOperatorInterface
{

  ADDITION("+")
  {
    @Override
    public String evaluate(final Operand left, final Operand right, final Unit resultUnit)
    {
      if (resultUnit == null)
      {
        double value = left.value() + right.value();
        return formattedResult("", value);
      }
      double value = left.convertUnit(resultUnit).value() + right.convertUnit(resultUnit).value();
      return formattedResult(resultUnit.getAbbreviation(), value);
    }

  },

  SUBTRACTION("-")
  {
    @Override
    public String evaluate(final Operand left, final Operand right, final Unit resultUnit)
    {
      if (resultUnit == null)
      {
        double value = left.value() - right.value();
        return formattedResult("", value);
      }
      double value = left.convertUnit(resultUnit).value() - right.convertUnit(resultUnit).value();
      return formattedResult(resultUnit.getAbbreviation(), value);
    }

  },

  MULTIPLICATION("*")
  {
    @Override
    public String evaluate(final Operand left, final Operand right, final Unit resultUnit)
    {
      String unit;
      double rightValue;
      double leftValue;

      if (resultUnit == null)
      {
        unit = "";
        rightValue = right.value();
        leftValue = left.value();
      }
      else if (left.isSameUnitType(right))
      {
        unit = resultUnit.getAbbreviation() + '\u00B2';
        rightValue = right.convertUnit(resultUnit).value();
        leftValue = left.convertUnit(resultUnit).value();

      }
      else
      {
        unit = left.unit().getAbbreviation() + "-" + right.unit().getAbbreviation();
        rightValue = right.unit().convertTo(left.unit(), right.value());
        leftValue = left.value();
      }

      double value = leftValue * rightValue;
      return formattedResult(unit, value);
    }

  },

  DIVISION("/")
  {
    @Override
    public String evaluate(final Operand left, final Operand right, final Unit resultUnit)
        throws ArithmeticException
    {
      double rightValue;
      double leftValue;
      String unit;
      if (resultUnit == null)
      {
        rightValue = right.value();
        leftValue = left.value();
        unit = "";
      }
      else if (left.isSameUnitType(right))
      {
        unit = resultUnit.getAbbreviation();
        rightValue = right.convertUnit(resultUnit).value();
        leftValue = left.convertUnit(resultUnit).value();
      }
      else
      {
        unit = left.unit().getAbbreviation() + "/" + right.unit().getAbbreviation();
        rightValue = right.value();
        leftValue = left.value();
      }

      if (rightValue == 0)
      {
        throw new ArithmeticException();
      }
      double value = leftValue / rightValue;
      return formattedResult(unit, value);
    }
  },

  POWER("^")
  {
    double value;
    String unit;

    @Override
    public String evaluate(Operand left, Operand right, Unit resultUnit)
    {
      if (resultUnit == null)
      {
        unit = "";
        value = Math.pow(left.value(), right.value());
      }
      else
      {
        unit = resultUnit.getAbbreviation();
        value = Math.pow(left.convertUnit(resultUnit).value(),
            right.convertUnit(resultUnit).value());
      }

      return formattedResult(unit, value);
    }
  },

  LESSTHAN("<")
  {
    boolean result;

    @Override
    public String evaluate(Operand left, Operand right, Unit resultUnit)
    {
      if (resultUnit == null)
      {
        result = left.value() < right.value();
      }
      else
      {
        result = left.convertUnit(resultUnit).value() < right.convertUnit(resultUnit).value();
      }

      if (result)
      {
        return "True";
      }
      return "False";
    }

  },

  GREATERTHAN(">")
  {
    boolean result;

    @Override
    public String evaluate(Operand left, Operand right, Unit resultUnit)
    {
      if (resultUnit == null)
      {
        result = left.value() > right.value();
      }
      else
      {
        result = left.convertUnit(resultUnit).value() > right.convertUnit(resultUnit).value();
      }

      System.out.println(result);
      if (result)
      {
        return "True";
      }
      return "False";
    }

  },

  DEFEQUALS("≝")
  {
    boolean result;

    @Override
    public String evaluate(Operand left, Operand right, Unit resultUnit)
    {
      if (resultUnit == null)
      {
        result = left.value() == right.value();
      }
      else
      {
        result = left.convertUnit(resultUnit).value() == right.convertUnit(resultUnit).value();
      }

      System.out.println(result);
      if (result)
      {
        return "True";
      }
      return "False";
    }
  };

  private String id;
  private static String formatted = "%.2f";
  private static boolean formatMax = false;

  /**
   * Constructor specifying the binary operator to be used.
   * 
   * @param id
   *          The String representation of the desired operator
   */
  private BinaryOperator(String id)
  {
    this.id = id;
  }

  /**
   * Allows for simple representation of the enum as used in other classes to allow a new string to
   * be created that contains the operator and the operand.
   * 
   * @param unit
   *          The operator to be represented
   * @param value
   *          The operand to be represented.
   * @return The formatted string.
   */
  private static String formattedResult(String unit, final double value)
  {
    String format = String.format(formatted + " %s", value, unit);
    if (formatMax)
    {
      format = removeLast(format);
      while (format.endsWith("0"))
      {
        format = removeLast(format);
        if (format.contains("."))
        {
          format = format.replace(".", " ");
        }
      }
    }
    else if (formatted.contains("0"))
    {
      format = removeLast(format);

    }
    return format;
  }

  private static String removeLast(String str)
  {
    str = str.substring(0, str.length() - 1);
    return str;
  }

  /**
   * Helper method to choose whether to format for max or fixed. Max is true, fixed is false.
   * 
   * @param format
   *          The desired format
   */
  public static void pickFormatter(boolean format)
  {
    formatMax = format;
  }

  public static void formatFixed(int adjustment)
  {
    formatted = "%." + adjustment + "f";
    formatMax = false;
  }

  public static void formatMaximum(int adjustment)
  {
    formatted = "%." + adjustment + "f";
    formatMax = true;
  }

  /**
   * Allows for the searching of operands by string representations.
   * 
   * @param id
   *          The String representation of the desired operator
   * @return The corresponding BinaryOperator enum
   */
  public static BinaryOperator findById(String id)
  {
    for (BinaryOperator op : BinaryOperator.values())
    {
      if (id.equals(op.id))
      {
        return op;
      }
    }

    throw new NoSuchElementException(String.format(Lang.OPERATOR_ERROR, id));
  }

}
