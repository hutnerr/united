package math.operators;

import java.io.Serializable;

import math.expressions.Operand;

public interface UnaryOperatorInterface extends Serializable
{
  public String evaluate(Operand value) throws ArithmeticException;
}
