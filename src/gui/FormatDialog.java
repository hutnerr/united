package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import system.constants.Lang;
import system.constants.UnitEDColor;
import system.constants.UnitEDFont;

/**
 * Class that allows user to specify preferences for decimal length.
 * 
 * @author Team3D
 */
public class FormatDialog extends JDialog
{
  private static final long serialVersionUID = -4114841283361734532L;
  private static final Font TEXT_FONT = UnitEDFont.SS12B;
  private static final Color COLOR = UnitEDColor.GRAY;
  private static final String LINE = "_________";

  Container contentPane = getContentPane();
  JTextArea input;
  JButton apply;
  ActionListener l;

  /**
   * Construct a format dialog.
   * 
   * @param l the action listener
   */
  public FormatDialog(final ActionListener l)
  {
    this.l = l;
    setup();
    createTexts();
    createButtons();
  }

  /**
   * Setup the dialog.
   */
  public void setup()
  {
    setModalityType(ModalityType.APPLICATION_MODAL);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setSize(450, 70);
    setAlwaysOnTop(true);
    setResizable(false);
    contentPane.setLayout(new GridLayout(0, 2));

    addWindowListener(new WindowAdapter()
    {

      @Override
      public void windowClosing(final WindowEvent e)
      {
        try
        {
          Integer.parseInt(getInput());
        }
        catch (NumberFormatException nfe)
        {
          input.setText("2");
        }
        apply.doClick();
      }
    });

  }

  /**
   * Create the textboxes, the right textbox defaults to ___ when
   *  unselected and it contains no text.
   */
  public void createTexts()
  {
    JLabel label = new JLabel();
    label.setLayout(new FlowLayout());
    JTextArea j = new JTextArea();

    j.setText(Lang.NUMBER_OF_DIGITS);
    j.setFocusable(false);
    j.setBackground(COLOR);
    j.setFont(TEXT_FONT);

    input = new JTextArea();

    input.addFocusListener(new FocusListener()
    {
      @Override
      public void focusGained(final FocusEvent e)
      {
        // Clear the default text when JTextArea gains focus
        if (input.getText().equals(LINE))
        {
          input.setText("");
        }
      }

      @Override
      public void focusLost(final FocusEvent e)
      {
        // Add back the default text if JTextArea loses focus and is empty
        if (input.getText().isEmpty())
        {
          input.setText(LINE);
        }
      }
    });

    input.setBackground(COLOR);
    input.setFont(TEXT_FONT);

    label.add(j);
    label.add(input);
    label.setVisible(true);
    contentPane.add(label);
  }

  /**
   * Create the 2 buttons used by the dialog, max, min, and apply.
   * Max is a maximum number of decimal places but they dont need to be filled.
   * Fixed means that number of decimals will always appear, even if unnecessary.
   */
  public void createButtons()
  {
    JLabel label = new JLabel();
    label.setLayout(new GridLayout(0, 3));
    label.setBackground(COLOR);
    ButtonGroup b = new ButtonGroup();
    JRadioButton max = new JRadioButton(Lang.MAXIMUM);
    max.setFont(TEXT_FONT);
    max.addActionListener(l);
    JRadioButton fixed = new JRadioButton(Lang.FIXED);
    fixed.setFont(TEXT_FONT);
    max.addActionListener(l);

    b.add(max);
    b.add(fixed);

    label.add(max);
    label.add(fixed);

    apply = new JButton(Lang.APPLY);
    apply.addActionListener(l);
    label.add(apply);

    contentPane.add(label);
  }

  /**
   * 
   * @return The Input
   */
  public String getInput()
  {
    return input.getText();
  }
}
