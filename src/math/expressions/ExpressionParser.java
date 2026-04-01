package math.expressions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import math.operators.BinaryOperator;
import math.operators.UnaryOperators;
import math.units.LengthUnit;
import math.units.MoneyUnit;
import math.units.PowerUnit;
import math.units.TimeUnit;
import math.units.Unit;
import math.units.Unitless;
import math.units.VolumeUnit;
import math.units.WeightUnit;
import system.constants.Lang;

public class ExpressionParser implements Serializable
{

  private static final long serialVersionUID = -5161598091253566927L;

  /**
   * Given a potential unit abbreviation this method attempts to return a Unit with the
   * corresponding abbreviation.
   * 
   * @param abbreviation
   *          The potential unit abbreviation
   * @return
   */
  public static Unit searchAllUnits(final String abbreviation)
  {
    try
    {
      return LengthUnit.findByAbbreviation(abbreviation);
    }
    catch (NoSuchElementException e)
    {
    }

    try
    {
      return TimeUnit.findByAbbreviation(abbreviation);
    }
    catch (NoSuchElementException e)
    {
    }

    try
    {
      return WeightUnit.findByAbbreviation(abbreviation);
    }
    catch (NoSuchElementException e)
    {
    }

    try
    {
      return PowerUnit.findByAbbreviation(abbreviation);
    }
    catch (NoSuchElementException e)
    {
    }

    try
    {
      return VolumeUnit.findByAbbreviation(abbreviation);
    }
    catch (NoSuchElementException e)
    {
    }

    try
    {
      return MoneyUnit.findByAbbreviation(abbreviation);
    }
    catch (NoSuchElementException e)
    {
    }

    throw new NoSuchElementException(String.format(Lang.UNIT_NOT_FOUND_ERROR, abbreviation));
  }

  /**
   * @param expression
   * @return
   */
  public static Result parse(final String expression, final String resultUnit)
  {
    Unit leftOpUnit;
    Scanner sc = new Scanner(expression);

    double leftOpValue = sc.nextDouble();

    try
    {
      leftOpUnit = searchAllUnits(sc.next());
    }
    catch (NoSuchElementException e)
    {
      sc.close();
      return null;
    }

    BinaryOperator operator = BinaryOperator.findById(sc.next());

    double rightOpValue = sc.nextDouble();
    Unit rightOpUnit = searchAllUnits(sc.next());

    Operand leftOp = new Operand(leftOpUnit, leftOpValue);
    Operand rightOp = new Operand(rightOpUnit, rightOpValue);

    sc.close();

    return new Result(operator, leftOp, rightOp, searchAllUnits(resultUnit));
  }

  public static Result parseUnitless(final String expression)
  {
    BinaryOperator operator;

    Scanner sc = new Scanner(expression);
    double leftOpValue = sc.nextDouble();

    try
    {
      operator = BinaryOperator.findById(sc.next());
    }
    catch (NoSuchElementException e)
    {
      sc.close();
      return null;

    }

    double rightOpValue = sc.nextDouble();

    Operand leftOp = new Operand(new Unitless(), leftOpValue);
    Operand rightOp = new Operand(new Unitless(), rightOpValue);

    sc.close();
    return new Result(operator, leftOp, rightOp, null);
  }

  public static String parseUnary(final String expression, final String operation,
      final String unit)
  {
    Scanner sc = new Scanner(expression);
    double val = sc.nextDouble();
    String result;

    Unit unitVal;

    if (!unit.isEmpty())
    {
      unitVal = searchAllUnits(unit);
    }
    else
    {
      unitVal = null;
    }

    UnaryOperators operator = UnaryOperators.findById(operation);
    Operand operand = new Operand(unitVal, val);
    sc.close();
    try
    {
      result = operator.evaluate(operand);
    }
    catch (ArithmeticException e)
    {
      result = Lang.DIVIDE_BY_ZERO_ERROR;
    }
    return result;

  }

  public static String unitlessPowerReformat(final String expression)
  {
    Map<Integer, String> superscriptMap = buildExponentMap();
    Scanner sc = new Scanner(expression);
    double base = sc.nextDouble();
    sc.next();
    String formattedExponent = "";
    Integer tempExponent = sc.nextInt();
    if (tempExponent > 9)
    {
      int firstDigit = tempExponent / 10;
      int secondDigit = tempExponent % 10;

      formattedExponent += superscriptMap.get(firstDigit);
      formattedExponent += superscriptMap.get(secondDigit);
    }
    else
    {
      formattedExponent += superscriptMap.get(tempExponent);

    }
    sc.next();
    double result = sc.nextDouble();

    return String.format("%s%s = %.2f", base, formattedExponent, result);

  }

  public static String unitPowerReformat(final String expression)
  {
    Map<Integer, String> superscriptMap = buildExponentMap();
    Scanner sc = new Scanner(expression);
    double base = sc.nextDouble();
    String unit = sc.next();
    sc.next();
    String formattedExponent = "";
    Integer tempExponent = sc.nextInt();
    if (tempExponent > 9)
    {
      int firstDigit = tempExponent / 10;
      int secondDigit = tempExponent % 10;

      formattedExponent += superscriptMap.get(firstDigit);
      formattedExponent += superscriptMap.get(secondDigit);
    }
    else
    {
      formattedExponent += superscriptMap.get(tempExponent);

    }

    sc.next();
    sc.next();
    double result = sc.nextDouble();

    return String.format("%s %s%s = %.2f %s", base, unit, formattedExponent, result, unit);
  }

  public static Map<Integer, String> buildExponentMap()
  {
    Map<Integer, String> superscriptMap = new HashMap<Integer, String>();

    superscriptMap.put(0, "\u2070");
    superscriptMap.put(1, "\u00B9");
    superscriptMap.put(2, "\u00B2");
    superscriptMap.put(3, "\u00B3");
    superscriptMap.put(4, "\u2074");
    superscriptMap.put(5, "\u2075");
    superscriptMap.put(6, "\u2076");
    superscriptMap.put(7, "\u2077");
    superscriptMap.put(8, "\u2078");
    superscriptMap.put(9, "\u2079");

    return superscriptMap;
  }

}
