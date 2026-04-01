package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
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

import math.expression.Number;
import system.constants.Const;
import system.constants.Lang;
import system.constants.UnitEDFont;

/**
 * NumberPad contains all the numbers, sign buttons, clear buttons, reset buttons, and operation
 * Buttons.
 * 
 * @author Team 3D
 * @version 04/01/2024
 */
public class NumberPad extends JFrame implements ActionListener, Serializable
{

  private static final long serialVersionUID = -4492988889527700260L;
  //private static final Color BUTTON_COLOR = Color.WHITE;
  private static final Color BUTTON_COLOR = Color.GREEN;

  protected OperationPad opPad;

  private Container contentPane;
  private JPanel mainPanel;
  private JButton decimalButton;
  private Number number;
  private Calculator calc;
  private ActionMap actionMap;
  private InputMap inputMap;

  /**
   * Creates the NumberPad container.
   * 
   * @param number
   * @param calc
   */
  public NumberPad(final Number number, final Calculator calc)
  {
    super();
    this.number = number;
    this.calc = calc;
    actionMap = ((JPanel) this.getContentPane()).getActionMap();
    setUpLayout();
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
  }

  /**
   * 
   * @return x
   */
  public InputMap getInputMap()
  {
    return this.inputMap;
  }

  /**
   * 
   * @param res
   */
  public void setResult(final boolean res)
  {
    opPad.setResult(res);
  }

  /**
   * Returns the NumberPad Container.
   * 
   * @return the NumberPad Container
   */
  public Container getNumberPad()
  {
    return this.contentPane;
  }

  private void setUpLayout()
  {
    setSize(300, 300);

    contentPane = getContentPane();
    setLayout(new BorderLayout(10, 0));

    mainPanel = new JPanel(new GridLayout(5, 3, 10, 10));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 5)); // padding
    numberPadConstructor();
    inputMapConstructor();

    contentPane.add(mainPanel, BorderLayout.CENTER);

    opPad = new OperationPad(calc);
    contentPane.add(opPad.getOperationPad(), BorderLayout.EAST);
  }

  private void numberPadConstructor()
  {
    String[] buttons = {Const.SIGN, Const.C, Const.R, Const.SEVEN, Const.EIGHT, Const.NINE,
        Const.FOUR, Const.FIVE, Const.SIX, Const.ONE, Const.TWO, Const.THREE, Const.ZERO,
        Lang.NUM_SEPERATOR, Const.BACKSPACE};
    for (int i = 0; i < buttons.length; i++)
    {
      makeButton(buttons[i], i);
    }
  }

  private void makeButton(final String name, final int index)
  {
    JButton currButton = new JButton(name);
    currButton.addActionListener(this);
    currButton.setActionCommand(name);
    currButton.setBackground(BUTTON_COLOR);
    currButton.setFont(UnitEDFont.SS18B);

    // for ± C R and ⌫
    if ((index <= 2) || (index == 14))
      //currButton.setForeground(Color.WHITE);
      currButton.setForeground(Color.RED);
    if (name.equals(Const.PERIOD) || name.equals(Const.COMMA))
      this.decimalButton = currButton;
    actionMap.put(name, new ClickAction(currButton));
    this.mainPanel.add(currButton);
  }

  private void inputMapConstructor()
  {
    inputMap = ((JPanel) this.getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), Const.SIGN);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), Const.C);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), Const.R);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_7, 0), Const.SEVEN);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_8, 0), Const.EIGHT);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_9, 0), Const.NINE);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_4, 0), Const.FOUR);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_5, 0), Const.FIVE);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_6, 0), Const.SIX);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0), Const.ONE);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0), Const.TWO);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0), Const.THREE);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_0, 0), Const.ZERO);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD7, 0), Const.SEVEN);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD8, 0), Const.EIGHT);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD9, 0), Const.NINE);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD4, 0), Const.FOUR);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD5, 0), Const.FIVE);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD6, 0), Const.SIX);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD1, 0), Const.ONE);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD2, 0), Const.TWO);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD3, 0), Const.THREE);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD0, 0), Const.ZERO);

    // This is for the internationalization
    // We allow periods / commas inputs depending on the region
    if (Lang.NUM_SEPERATOR.equals(Const.PERIOD))
      inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, 0), Const.PERIOD);
    else
      inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, 0), Const.COMMA);

    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), Const.BACKSPACE);
  }

  /**
   * 
   * @param enable
   */
  public void enableDecimal(final boolean enable)
  {
    decimalButton.setEnabled(enable);
  }

  private void opButtonEnabler()
  {
    if (!calc.getNeedEquals())
    {
      if (this.number.isEmpty())
      {
        opPad.disableAllOperationButtons();
      }
      else
      {
        opPad.enableOperationButtons(true);
      }
    }
  }

  @Override
  public void actionPerformed(final ActionEvent e)
  {
    String event = e.getActionCommand();
    switch (event)
    {
      case (Const.SIGN):
        number.changeSign();
        break;
      case (Const.C):
        calc.clearOutput();
        break;
      case (Const.R):
        calc.resetOutput();
        break;
      case (Const.BACKSPACE):
        number.backspace();
        if (!number.getDecimal())
          enableDecimal(true);
        opButtonEnabler();
        break;
      case (Const.PERIOD):
        number.add(event);
        opButtonEnabler();
        enableDecimal(false);
        break;
      case (Const.COMMA):
        number.add(event);
        opButtonEnabler();
        enableDecimal(false);
        break;
      default:
        number.add(event);
        opButtonEnabler();
        break;
    }
  }
}
