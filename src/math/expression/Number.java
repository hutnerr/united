package math.expression;

import java.io.Serializable;

import gui.Calculator;
import system.constants.Lang;

/**
 * Stores the Number in the input Area.
 * 
 * @author Team 3D
 * @version 04/01/2024
 */
public class Number implements Serializable // NAME IS WIP
{
  /**
   * 
   */
  private static final long serialVersionUID = 5978922242240456433L;
  private String number = "";
  private boolean positiveOrNegative; // positive = positive negative = negative
  private boolean decimalTrueOrFalse;
  private Calculator calc;
  // STILL NEED TO UPDATE DISPLAY AFTER STUFF IS CHANGED

  private final String SEPERATOR = Lang.NUM_SEPERATOR;

  public Number(Calculator calc)
  {
    positiveOrNegative = true;
    decimalTrueOrFalse = false;
    this.calc = calc;
  }

  /**
   * Adds to the end of number.
   * 
   * @param s
   *          thing to be added to number
   */
  public void add(String s)
  {
    if (decimalTrueOrFalse && s == SEPERATOR)
    {
      // NumberPad.enableDecimal(!decimalTrueOrFalse);
      return;
    }
    else if (s == SEPERATOR)
    {
      decimalTrueOrFalse = !decimalTrueOrFalse;
    }
    number += s;
    calc.updateCurrent(getNumber());
  }

  /**
   * @return the String value of the current number
   */
  public String getNumber()
  {
    return (positiveOrNegative ? "" : "-") + number;
  }

  /**
   * @return the Double Value of the current number
   */
  public Double getDouble()
  {
    return Double.parseDouble(number);
  }

  public boolean getDecimal()
  {
    return decimalTrueOrFalse;
  }

  /**
   * Changes the Sign attached to number.
   */
  public void changeSign()
  {
    positiveOrNegative = !positiveOrNegative;
    calc.updateCurrent(getNumber());
  }

  /**
   * Removes the last value in the String number.
   */
  public void backspace()
  {
    if (number != "")
    {
      if (number.charAt(number.length() - 1) == SEPERATOR.toCharArray()[0])
      {
        number = number.substring(0, number.length() - 1);
        calc.updateCurrent(getNumber());
        decimalTrueOrFalse = !decimalTrueOrFalse;
      }
      else
      {
        number = number.substring(0, number.length() - 1);
        calc.updateCurrent(getNumber());
      }
    }
  }

  /**
   * This method is used to completely clear Number.
   */
  public void clearNumber()
  {
    number = "";
    positiveOrNegative = true;
    decimalTrueOrFalse = false;
    // NumberPad.enableDecimal(true);
  }

  /**
   * returns a boolean true of false of whether or not the Number is empty or not.
   * 
   * @return boolean rep of if the String is Empty or not
   */
  public boolean isEmpty()
  {
    return number.equals("");
  }
}
