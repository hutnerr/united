package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.Serializable;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import system.constants.Const;
import system.constants.UnitEDFont;

/**
 * OperationPad is a portion of the number Pad and is used to store the operations and equals
 * buttons.
 * 
 * @author Team 3D
 * @version 04/01/2024
 */
public class OperationPad extends JFrame implements ActionListener, Serializable
{

  private static final long serialVersionUID = 7781432527522688735L;

  // Style Variables
  private static final Color BUTTONCOLOR = Color.WHITE;
  private static final Font BUTTONFONT = UnitEDFont.SS16B;

  // Constants
  private final String[] buttonArray = new String[] {Const.ADDITION, Const.SUBTRACTION,
      Const.MULTIPLICATION, Const.DIVISION, Const.EQUALS};
  private final String[] operationButtonArray = new String[] {};

  // Instance Variables
  private Calculator calc;
  private InputMap inputMap;
  private Container contentPane;
  private JPanel opPad;
  private JPanel powerInvertPanel;
  private JPanel relationPanel;
  private boolean result = false;

  /**
   * Create the OperationPad Container.
   * 
   * @param calc
   */
  public OperationPad(final Calculator calc)
  {
    super();
    this.calc = calc;
    setUpLayout();
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
  }

  /**
   * Returns the OperationPad Container.
   * 
   * @return the current OperationPad so it can be used elsewhere
   */
  public Container getOperationPad()
  {
    // this is for the Main Display
    return this.contentPane;
  }

  /**
   * Setter for the result variable.
   * 
   * @param res
   *          True if it is a result, false otherwise
   */
  public void setResult(final boolean res)
  {
    this.result = res;
  }

  private void setUpLayout()
  {
    setSize(300, 300);
    contentPane = getContentPane();
    setLayout(new BorderLayout());

    JPanel centerPanel = new JPanel(new BorderLayout());

    opPad = new JPanel(new GridLayout(5, 1, 10, 10));
    opPad.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5)); // padding
    operationButtonConstructor();
    inputMapConstructor();

    // make the other two things here

    // centerPanel.add(opPad, BorderLayout.WEST);)
    centerPanel.add(opPad, BorderLayout.WEST);
    powerInvertPanel = newButtons();
    centerPanel.add(powerInvertPanel, BorderLayout.CENTER);

    contentPane.add(centerPanel, BorderLayout.CENTER);
  }

  private void inputMapConstructor()
  {
    inputMap = ((JPanel) this.getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, KeyEvent.SHIFT_DOWN_MASK),
        Const.ADDITION);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0), Const.SUBTRACTION);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_8, KeyEvent.SHIFT_DOWN_MASK),
        Const.MULTIPLICATION);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0), Const.DIVISION);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), Const.EQUALS);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ADD, 0), Const.ADDITION);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, 0), Const.SUBTRACTION);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_MULTIPLY, 0), Const.MULTIPLICATION);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DIVIDE, 0), Const.DIVISION);
  }

  private void operationButtonConstructor()
  {
    ActionMap actionMap = ((JPanel) this.getContentPane()).getActionMap();
    for (int i = 0; i < buttonArray.length; i++)
    {
      JButton button = new JButton(buttonArray[i]);
      button.addActionListener(this);
      button.setActionCommand(buttonArray[i]);
      button.setFont(BUTTONFONT);
      button.setBackground(BUTTONCOLOR);
      BorderFactory.createEmptyBorder(10, 20, 10, 20);
      actionMap.put(buttonArray[i], new ClickAction(button));
      this.opPad.add(button, i);
    }
  }

  private JPanel newButtons()
  {
    JPanel moreButtons = new JPanel(new GridLayout(5, 1, 10, 10));
    moreButtons.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

    JButton button = new JButton(Const.POWER);
    button.addActionListener(this);
    button.setActionCommand(Const.POWER);
    button.setFont(BUTTONFONT);
    button.setBackground(BUTTONCOLOR);
    BorderFactory.createEmptyBorder(10, 20, 10, 20);
    moreButtons.add(button);

    button = new JButton(Const.INVERT);
    button.addActionListener(this);
    button.setActionCommand(Const.INVERT);
    button.setFont(UnitEDFont.SS12B);
    button.setBackground(BUTTONCOLOR);
    BorderFactory.createEmptyBorder(10, 20, 10, 20);
    moreButtons.add(button);

    button = new JButton(Const.LESSTHAN);
    button.addActionListener(this);
    button.setActionCommand(Const.LESSTHAN);
    button.setFont(BUTTONFONT);
    button.setBackground(BUTTONCOLOR);
    BorderFactory.createEmptyBorder(10, 20, 10, 20);
    moreButtons.add(button);

    button = new JButton(Const.GREATERTHAN);
    button.addActionListener(this);
    button.setActionCommand(Const.GREATERTHAN);
    button.setFont(BUTTONFONT);
    button.setBackground(BUTTONCOLOR);
    BorderFactory.createEmptyBorder(10, 20, 10, 20);
    moreButtons.add(button);

    button = new JButton(Const.DEFEQUALS);
    button.addActionListener(this);
    button.setActionCommand(Const.DEFEQUALS);
    button.setFont(BUTTONFONT);
    button.setBackground(BUTTONCOLOR);
    BorderFactory.createEmptyBorder(10, 20, 10, 20);
    moreButtons.add(button);
    return moreButtons;
  }

  /**
   * Disables the operation buttons.
   */
  public void disableAllOperationButtons()
  {
    for (int i = 0; i < buttonArray.length; i++)
    {
      this.opPad.getComponent(i).setEnabled(false);
    }

    for (Component comp : powerInvertPanel.getComponents())
    {
      JButton button = (JButton) comp;
      button.setEnabled(false);
    }
  }

  /**
   * Enables the operation buttons.
   * 
   * @param enable
   *          The status of the buttons.
   */
  public void enableOperationButtons(final boolean enable)
  {
    for (int i = 0; i < buttonArray.length; i++)
    {
      this.opPad.getComponent(i).setEnabled(enable);
    }
    this.opPad.getComponent(buttonArray.length - 1).setEnabled(!enable);

    for (Component comp : powerInvertPanel.getComponents())
    {
      JButton button = (JButton) comp;
      button.setEnabled(enable);
    }
  }

  // This is a better and neated way to do this but I couldn't get it to work quickly and we out of
  // time :(

  // private boolean contains(final String s)
  // {
  //
  // System.out.println(s);
  //
  // for (String temp : buttonArray)
  // {
  // if (temp.equals(s))
  // return true;
  // }
  //
  // for (String temp : operationButtonArray)
  // {
  // if (temp.equals(s))
  // return true;
  // }
  // return false;
  // }
  //
  // @Override
  // public void actionPerformed(final ActionEvent e)
  // {
  // String action = e.getActionCommand();
  // if ((contains(action)) && ((result) || (!calc.getNumber().isEmpty())))
  // {
  // System.out.println("MADE IT IN");
  // calc.updateOutput(action);
  // }
  // }

  @SuppressWarnings("static-access") // stupid eclipse thinks non static method is static
  @Override
  public void actionPerformed(final ActionEvent e)
  {
    switch (e.getActionCommand())
    {
      case (Const.ADDITION):
        // updateDisplay("+"); NEED UNITS BETWEEN OPERAND AND OPERATION
        if (result || !calc.getNumber().isEmpty())
        { // means that there is actually a number there
          calc.updateOutput(Const.ADDITION);
        }
        break;
      case (Const.SUBTRACTION):
        if (result || !calc.getNumber().isEmpty())
        { // means that there is actually a number there
          calc.updateOutput(Const.SUBTRACTION);
        }
        break;
      case (Const.MULTIPLICATION):
        if (result || !calc.getNumber().isEmpty())
        { // means that there is actually a number there
          calc.updateOutput(Const.MULTIPLICATION);
        }
        break;
      case (Const.DIVISION):
        if (result || !calc.getNumber().isEmpty())
        { // means that there is actually a number there
          calc.updateOutput(Const.DIVISION);
        }
        break;
      case (Const.EQUALS):
        if (result || !calc.getNumber().isEmpty())
        { // means that there is actually a number there
          calc.updateOutput(Const.EQUALS);
        }
        break;
      case (Const.POWER):
        if (result || !calc.getNumber().isEmpty())
        { // means that there is actually a number there
          calc.updateOutput(Const.POWER);
        }
        break;
      case (Const.INVERT):
        if (result || !calc.getNumber().isEmpty())
        { // means that there is actually a number there
          calc.updateOutput(Const.INVERT);
        }
        break;
      case (Const.LESSTHAN):
        if (result || !calc.getNumber().isEmpty())
        { // means that there is actually a number there
          calc.updateOutput(Const.LESSTHAN);
        }
        break;
      case (Const.GREATERTHAN):
        if (result || !calc.getNumber().isEmpty())
        { // means that there is actually a number there
          calc.updateOutput(Const.GREATERTHAN);
        }
        break;
      case (Const.DEFEQUALS):
        if (result || !calc.getNumber().isEmpty())
        { // means that there is actually a number there
          calc.updateOutput(Const.DEFEQUALS);
        }
        break;
      default:
        break;
    }
  }
}
