package math.expressions;

import java.io.Serializable;

import math.operators.BinaryOperator;
import math.units.Unit;

public class Result implements Serializable
{

  private static final long serialVersionUID = -880724371609217437L;
  private BinaryOperator operator;
  private Operand left, right;

  private String result;

  public Result(BinaryOperator operator, Operand left, Operand right, Unit resultUnit)
  {
    this.operator = operator;
    this.left = left;
    this.right = right;
    try
    {
      this.result = operator.evaluate(left, right, resultUnit);
    }
    catch (ArithmeticException e)
    {
      this.result = null;
    }
  }

  public Operand getOperand()
  {
    if (!left.isSameUnitType(right))
    {
      return null;
    }

    if (operator == BinaryOperator.MULTIPLICATION || operator == BinaryOperator.DIVISION)
    {
      return null;
    }

    double value = Double.parseDouble(this.toString());
    return new Operand(left.unit(), value);
  }

  @Override
  public String toString()
  {
    return result;
  }

}
