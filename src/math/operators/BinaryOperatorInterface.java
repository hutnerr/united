package math.operators;

import java.io.Serializable;

import math.expressions.Operand;
import math.units.Unit;

public interface BinaryOperatorInterface extends Serializable
{

  /**
   * Evaluates the expression of the two operands using the BinaryOperator the method is called on.
   * 
   * @param left
   *          The left operand
   * @param right
   *          The right operand
   * @return the result of the operation
   */
  public String evaluate(final Operand left, final Operand right, final Unit resultUnit)
      throws ArithmeticException;

}
