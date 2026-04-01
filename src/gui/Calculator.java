package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import math.expression.Number;
import math.expressions.ExpressionParser;
import math.operators.BinaryOperator;
import system.constants.Const;
import system.constants.Lang;
import system.constants.UnitEDFilepath;
import system.constants.UnitEDFont;
import system.files.CustomUnitOperations;
import system.files.FileSelector;
import system.files.Serialization;
import system.settings.UserSetting;

/**
 * Main calculator window for the GUI.
 * 
 * @author Team3D
 * @version 4/3/2024
 */
public class Calculator extends JFrame implements ActionListener, Serializable
{
  private static final long serialVersionUID = 5731352674437381785L;
  private static final String LOGOFILEPATH = UnitEDFilepath.LOGO;
  private static final Font TEXTFONT = UnitEDFont.SS22B;
  private static final Font RESULTBOXFONT = UnitEDFont.SS18B;
  // private final String EXIT = "Exit";
  public static int instanceCount = 0;
  private static final String CALCULATOR = "calculator";
  private static final String SPLITBY = "\\.";

  private static final String FRACTIONAL = "1/x";
  private static final String FRACTIONALOUTPUT = "1 / ";

  private static final String PERIODFORMAT = "#,##0.";
  private static final String COMMAFORMAT = "#.##0,";

  private Container contentPane = getContentPane();
  private SessionHistory history;
  private FormatDialog fd;
  private JTextField numOutput;
  private JTextField numDisplay;
  private NumberPad buttons;
  private JComboBox<String> units;
  private ResultUnitsBox resultUnits;
  private JFrame frame;
  private HelpFileFormat menuBar;
  private boolean selected = false;
  private UserSetting setting;
  private DecimalFormat formatter;

  // keyboard shortcut stuff
  private ActionMap menuBarActionMap;
  private KeyboardShortcutMenu ksm;
  // private KeyListener listener;
  private HashMap<Character, String> shortcutMap;
  private InputMap inputMap;

  private boolean needEquals = false;
  private boolean result = false;
  private boolean isRelation = false;
  private boolean isExponentOperation = false;

  private Number number;

  // ##########################################################
  // Create the Calculator
  // ##########################################################

  /**
   * Constructor.
   */
  public Calculator() // .get().add
  {
    // setup the base calc
    super();
    frame = this;
    instanceCount++;
    inputMap = ((JPanel) this.getContentPane()).getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
    setup();
    setWindowIcon();
    number = new Number(this);

    formatter = new DecimalFormat(PERIODFORMAT + Const.ZERO.repeat(setting.getDecimalLength()));
    // if (Lang.NUM_SEPERATOR.equals(Const.COMMA))
    // {
    // formatter = new DecimalFormat(
    // COMMAFORMAT + Const.ZERO.repeat(setting.getDecimalLength()));
    // }

    // setup and build the output / input area
    JPanel outputPanel = buildOutputPanel();
    makeCalculationDisplay(outputPanel);
    JPanel operandPanel = buildCurrentOperandPanel();
    buildOperandTextDisplay(operandPanel);
    buildUnitDropDownBox(operandPanel, outputPanel);
    buildNumberPad(outputPanel);

    // setup menu bar / logo
    JPanel topPanel = addTeamLogo();
    addMenuBar(topPanel);

    history = new SessionHistory();
    int startX = frame.getLocation().x + frame.getWidth();
    int startY = frame.getLocation().y;

    history.setLocation(startX, startY);

    this.addComponentListener(new ComponentAdapter()
    {
      @Override
      public void componentMoved(final ComponentEvent e)
      {
        if (history.isVisible())
        {
          history.setLocation(frame.getLocation().x + 390, frame.getLocation().y);
        }
        else
        {
          history.setLocation(frame.getLocation().x, frame.getLocation().y);
        }
      }
    });
  }

  // ###########################
  // Setting up the Base Calc
  // ###########################

  /**
   * Sets up the Calculator frame, direct call sets up the frame itself.
   */
  private void setup()
  {
    setSize(400, 600);
    setTitle(Lang.CALCULATOR);
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    addWindowListener(new WindowAdapter()
    {
      @Override
      public void windowClosing(final WindowEvent e)
      {
        if (instanceCount - 1 > 0)
        {
          dispose();
          history.dispose();
          // history.dispose();
          instanceCount--;
        }
        else
        {
          System.exit(0);
        }
      }
    });

    setFocusable(true);
    setResizable(false);
    setAlwaysOnTop(true);
    contentPane.setLayout(new BorderLayout());
    contentPane.setBackground(Color.RED);

    this.setting = UserSetting.readSettingsFromFile();
    setFormat(setting);

    System.out.println(setting.getKeyboardShortcuts());
    if (setting.getKeyboardShortcuts() != null)
    {
      System.out.println("hi");
      this.shortcutMap = setting.getKeyboardShortcuts();
      updateShortcuts(setting.getKeyboardShortcuts());
    }
    else
    {
      System.out.println("hi bad");
      this.shortcutMap = new HashMap<>();
    }
  }

  /**
   * Sets the icon of the application itself.
   */
  private void setWindowIcon()
  {
    try
    {
      setIconImage(ImageIO.read(new File(LOGOFILEPATH)));
    }
    catch (IOException e)
    {
      // no icon is set
    }
  }

  // ###########################
  // Builds the Display and Current Operand
  // ###########################

  /**
   * This builds the panel that holds the Display and CurrentOperand.
   * 
   * @return the build panel
   */
  private JPanel buildOutputPanel()
  {
    JPanel ioPanel = new JPanel();
    ioPanel.setLayout(new GridLayout(2, 1, 7, 20));
    ioPanel.setBorder(BorderFactory.createEmptyBorder(1, 20, 1, 5)); // padding
    return ioPanel;
  }

  /**
   * Makes the display that stores the output (the red one), not the current operand.
   * 
   * @param pan
   *          The panel to add to
   */
  private void makeCalculationDisplay(final JPanel pan)
  {
    numOutput = new JTextField();
    numOutput.setFont(TEXTFONT); // Sample font
    numOutput.setBackground(new Color(255, 127, 127));
    numOutput.setFocusable(false);
    numOutput.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
    numOutput.setPreferredSize(new Dimension(150, numOutput.getPreferredSize().height));

    resultUnits = new ResultUnitsBox();
    resultUnits.setPreferredSize(new Dimension(60, numOutput.getPreferredSize().height));
    resultUnits.setFont(RESULTBOXFONT);

    JPanel horizontalPanel = new JPanel();
    horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.X_AXIS));
    horizontalPanel.add(numOutput);
    horizontalPanel.add(Box.createRigidArea(new Dimension(10, 0)));

    horizontalPanel.add(resultUnits);

    pan.add(horizontalPanel);
  }

  /**
   * Builds the panel that holds the TextArea for the Current Operand as well as the UnitDropdown
   * Box.
   * 
   * @return The panel that was built
   */
  private JPanel buildCurrentOperandPanel()
  {
    JPanel inputs = new JPanel();
    inputs.setLayout(new BorderLayout());
    inputs.setBorder(null);
    return inputs;
  }

  /**
   * Builds the TextDispay the contains the data of the CurrentOperand the user is entering.
   * 
   * @param pan
   *          The panel we want to add it to
   */
  private void buildOperandTextDisplay(final JPanel pan)
  {
    numDisplay = new JTextField();
    numDisplay.setFont(TEXTFONT);
    numDisplay.setHorizontalAlignment(SwingConstants.RIGHT);
    numDisplay.setFocusable(false);
    pan.add(numDisplay, BorderLayout.CENTER);
  }

  // ###########################
  // Builds user input interactions.
  // ###########################

  /**
   * Builds the Unit DropDownBox to select the units.
   * 
   * @param pan
   *          The panel to add to
   * @param parentPanel
   *          The panel we want to add the panel to
   */
  private void buildUnitDropDownBox(final JPanel pan, final JPanel parentPanel)
  {
    units = new UnitComboBox();
    units.setFont(TEXTFONT);
    pan.add(units, BorderLayout.EAST);
    parentPanel.add(pan);
  }

  /**
   * Builds the NumberPad for the user to enter input.
   * 
   * @param pan
   *          The panel to add to
   */
  private void buildNumberPad(final JPanel pan)
  {
    buttons = new NumberPad(number, this);
    buttons.opPad.disableAllOperationButtons();
    JPanel ioFormat = new JPanel();
    ioFormat.setLayout(new BorderLayout());
    ioFormat.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // padding
    ioFormat.add(pan, BorderLayout.NORTH);
    ioFormat.add(buttons.getNumberPad(), BorderLayout.CENTER);
    contentPane.add(ioFormat);
  }

  // ###########################
  // Logo and the Menu Bar
  // ###########################

  /**
   * Adds the Logo to the top of the calculator.
   * 
   * @return the panel to add the menubar with
   */
  private JPanel addTeamLogo()
  {
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    try
    {
      Image logo = ImageIO.read(new File(LOGOFILEPATH));
      logo = logo.getScaledInstance(90, 30, Image.SCALE_SMOOTH); // resize logo
      JLabel logoLabel = new JLabel(new ImageIcon(logo));
      panel.add(logoLabel, BorderLayout.WEST); // left align the logo
    }
    catch (IOException ioe)
    {
      // do nothing
    }
    return panel;
  }

  /**
   * Add the menubar.
   * 
   * @param pan
   *          the panel to add to
   */
  private void addMenuBar(final JPanel pan)
  {

    // IM SO SORRY i MADE THIS METHOD SO UGLY
    // BUT FOR PEOPLE THAT COME LATER I HAVE TO MAKE EACH JMENUITEM A PART OF A
    // ACTIONMAP WHICH
    // MEANS I GOTTA CREATE A ACTION FOR EACH OF THEM THIS WOULD BE FINE BUT SOME OF
    // THESE CALL
    // METHODS OF THESE ARE ACTIONCOMMADS SO I HAVE TO DO IT MANUALLY
    // I AM VERY TIRED THOUGH SO MAYBE IM JUST BEING STUPID
    // once again sorry

    // Action map and action variable
    menuBarActionMap = ((JPanel) this.getContentPane()).getActionMap();
    Action thingHappened;

    menuBar = new HelpFileFormat(null, this);

    JMenuItem newCalc = new JMenuItem(Lang.NEW);
    newCalc.addActionListener(this);
    thingHappened = new AbstractAction()
    {
      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(final ActionEvent e)
      {
        Calculator calc;
        // I JUST COPIED THIS FROM ACTIONPERFORMED MIGHT NEED TWEAKING IN THE FUTURE
        try // separate main method instance
        {
          Serialization.write(history, CALCULATOR);
          calc = new Calculator();
          calc.setHistory((SessionHistory) Serialization.open(CALCULATOR));
          calc.setVisible(true);
          setLocation(calc.getLocation().x + calc.getWidth(), calc.getLocation().y);
        }
        catch (IOException ioe)
        {
          calc = new Calculator();
          calc.setVisible(true);
          setLocation(calc.getLocation().x + calc.getWidth(), calc.getLocation().y);
        }
      }
    };
    menuBarActionMap.put(Lang.NEW_WINDOW, thingHappened);
    menuBar.fileMenuAdd(newCalc, 0);

    JMenuItem exitB = new JMenuItem(Lang.EXIT);
    exitB.addActionListener(this);
    thingHappened = new AbstractAction()
    {
      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(final ActionEvent e)
      {
        if (Calculator.instanceCount - 1 > 0)
        {
          dispose();
          // history.dispose();
          // instanceCount--;
        }
        else
        {
          System.exit(0);
        }
      }
    };
    menuBarActionMap.put(Lang.EXIT, thingHappened);
    menuBar.fileMenuAdd(exitB);

    JMenu customUnitMenu = new JMenu(Lang.CUSTOM_UNIT);
    JMenuItem customUnitItem = new JMenuItem(Lang.ADD);
    JMenuItem resetCustomUnits = new JMenuItem(Lang.RESET);
    JMenuItem refreshCustomUnits = new JMenuItem(Lang.REFRESH);
    customUnitMenu.add(customUnitItem);
    customUnitMenu.add(resetCustomUnits);
    customUnitMenu.add(refreshCustomUnits);
    menuBar.add(customUnitMenu);
    customUnitItem.addActionListener(e -> addCustomUnit());
    resetCustomUnits.addActionListener(e -> resetCustomUnits());
    refreshCustomUnits.addActionListener(e -> refreshCustomUnits());

    thingHappened = new AbstractAction()
    {
      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(final ActionEvent e)
      {
        addCustomUnit();
      }
    };
    menuBarActionMap.put(Lang.ADD_CUSTOM_UNITS, thingHappened);

    thingHappened = new AbstractAction()
    {
      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(final ActionEvent e)
      {
        resetCustomUnits();
      }
    };
    menuBarActionMap.put(Lang.RESET_CUSTOM_UNITS, thingHappened);

    thingHappened = new AbstractAction()
    {
      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(final ActionEvent e)
      {
        refreshCustomUnits();
      }
    };
    menuBarActionMap.put(Lang.REFRESH_CUSTOM_UNITS, thingHappened);

    // preferences tab
    JMenu preferencesMenu = new JMenu(Lang.PREFERENCES);
    JMenuItem open = new JMenuItem(Lang.OPEN); // open preferences saved from a file
    open.addActionListener(this);

    JMenuItem thousandSeperator = new JMenuItem(Lang.THOUSAND_SEPERATOR);
    thousandSeperator.addActionListener(e -> flipThousandSeperator());
    thingHappened = new AbstractAction()
    {
      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(final ActionEvent e)
      {
        flipThousandSeperator();
      }
    };
    menuBarActionMap.put(Lang.THOUSAND_SEPERATOR, thingHappened);
    JMenu keyboardShortcut = new JMenu(Lang.KEYBOARD_SHORTCUT);
    JMenuItem keyboardShortcutAdd = new JMenuItem(Lang.ADD);
    keyboardShortcutAdd.addActionListener(e -> addKeyboardShortcutPopup());
    thingHappened = new AbstractAction()
    {
      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(final ActionEvent e)
      {
        addKeyboardShortcutPopup();
      }
    };
    menuBarActionMap.put(Lang.ADD_NEW_SHORTCUT, thingHappened);

    JMenuItem keyboardShortcutReset = new JMenuItem(Lang.RESET);
    keyboardShortcutReset.addActionListener(e -> resetShortCuts());
    thingHappened = new AbstractAction()
    {
      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(final ActionEvent e)
      {
        resetShortCuts();
      }
    };
    menuBarActionMap.put(Lang.RESET, thingHappened);

    keyboardShortcut.add(keyboardShortcutAdd);
    keyboardShortcut.add(keyboardShortcutReset);

    JMenuItem resultFormatter = new JMenuItem(Lang.RESULT_FORMATTER);
    resultFormatter.addActionListener(this);

    JMenu edit = new JMenu(Lang.EDIT); // edit preferences currently being used

    edit.add(thousandSeperator);
    edit.add(keyboardShortcut);
    edit.add(resultFormatter);

    JMenuItem save = new JMenuItem(Lang.SAVE); // save the current preferences
    save.addActionListener(this);

    JMenuItem saveAs = new JMenuItem(Lang.SAVE_AS); // save the current preferences to a file
    saveAs.addActionListener(this);

    JMenuItem reset = new JMenuItem(Lang.RESET);
    reset.setActionCommand("RESET-SETTINGS");
    reset.addActionListener(this);

    preferencesMenu.add(edit);
    preferencesMenu.add(open);
    preferencesMenu.add(save);
    preferencesMenu.add(saveAs);
    preferencesMenu.add(reset);
    menuBar.add(preferencesMenu);

    JMenuItem historyItem = new JMenuItem(Const.GREATERTHAN);
    historyItem.addActionListener(this);
    menuBar.add(historyItem);

    pan.add(menuBar, BorderLayout.NORTH);
    contentPane.add(pan, BorderLayout.NORTH);
  }

  /**
   * Helper method that allows custom user settings to be serailized. Allows decimals to be
   * formatted for max or fixed lengths. Allows big numbers to be separated by commas or not (Soon).
   * 
   * @param settings
   *          The user settings to serialize
   */
  public void setFormat(final UserSetting settings)
  {
    if (settings.getMaxOrFixed())
    {
      BinaryOperator.formatMaximum(settings.getDecimalLength());
    }
    else
    {
      BinaryOperator.formatFixed(settings.getDecimalLength());
    }
  }

  // ##########################################################
  // Number Methods
  // ##########################################################

  /**
   * 
   */
  public void resetShortCuts()
  {
    this.shortcutMap = new HashMap<>();
    setting.setKeyboardShorcuts(shortcutMap);
    this.inputMap.clear();
  }

  /**
   * 
   * @return x
   */
  public UserSetting getSetting()
  {
    return this.setting;
  }

  /**
   * 
   * @param newSetting
   */
  public void setSetting(final UserSetting newSetting)
  {
    this.setting = newSetting;
  }

  /**
   * 
   * @return x
   */
  public Number getNumber()
  {
    return number;
  }

  /**
   * 
   * @return final
   */
  public SessionHistory getHistory()
  {
    return history;
  }

  /**
   * 
   * 
   * @param history
   */
  public void setHistory(final SessionHistory history)
  {
    this.history = history;
  }

  // ##########################################################
  // Update Methods
  // ##########################################################

  private void flipThousandSeperator()
  {
    this.setting.setThousandsSep(!setting.isThousandsSep());
  }

  /**
   * Method to update the current display.
   * 
   * @param update
   */
  public void updateCurrent(final String update)
  {
    numDisplay.setText(update);
  }

  /**
   * Method to update the output.
   * 
   * @param operation
   */
  public void updateOutput(final String operation)
  {
    // Hello Dr.Bernstein if you're reading this method I'm sorry. This method was creating in a 2
    // hours long session 2 hours before the first sprint. It has since been builded upon to a point
    // where I have no idea what I was trying to do or wanted to do. This is in my humble and
    // embarrassed opinion a example of how SCRUM can lead to poorly written code that then snow
    // balls out of control.

    String updated = numOutput.getText();
    String tempStuff = "";

    if (setting.isThousandsSep() && Lang.NUM_SEPERATOR.equals(Const.PERIOD))
    {
      updated = updated.replace(Const.COMMA, "");
    }
    else if (setting.isThousandsSep() && Lang.NUM_SEPERATOR.equals(Const.COMMA))
    {
      updated = updated.replace(Const.PERIOD, "");
    }

    buttons.opPad.enableOperationButtons(needEquals);
    if (operation.equals(Const.LESSTHAN) || operation.equals(Const.GREATERTHAN)
        || operation.equals(Const.DEFEQUALS))
    {
      isRelation = true;
    }
    if (operation.equals(Const.POWER))
    {
      isExponentOperation = true;
    }

    if (result) // means that there is a result in the display
    {
      if (operation.equals(FRACTIONAL))
      {
        numOutput.setText("");
        updated = ExpressionParser.parseUnary(numDisplay.getText(), operation,
            (String) units.getSelectedItem());
        result = true;
        buttons.setResult(true);
        needEquals = false;
        updateHistory(FRACTIONALOUTPUT + numDisplay.getText() + " = " + updated);
      }
      else if (!number.isEmpty() && !operation.equals(Const.EQUALS)) // number is not empty
      {
        updated = numDisplay.getText() + Const.SPACE + units.getSelectedItem() + Const.SPACE
            + operation + Const.SPACE;
        updateResultComboBox(units.getSelectedItem());

        // if (Lang.NUM_SEPERATOR.equals(Const.COMMA))a
        // updated = updated.replace(',', '.'); // swap , with . so it can be calculated

        result = false;
        buttons.setResult(false);
        needEquals = true;
      }
      else if (!operation.equals(Const.EQUALS)) // because there is already a result in the display
                                                // and we
      // are not pressing equals reset display for new operation
      {
        numOutput.setText("");
        numDisplay.setText("");

        // if (Lang.NUM_SEPERATOR.equals(Const.COMMA))
        // updated = updated.replace(',', '.'); // swap , with . so it can be calculated

        if (units.getSelectedItem().equals(""))
        {
          // if (Lang.NUM_SEPERATOR.equals(Const.COMMA))
          // updated = updated.replace(',', '.');
          System.out.println(updated);
          updated = ExpressionParser.parseUnitless(updated)
              .toString();
          if (updated.charAt(updated.length() - 1) == '.'
              || updated.charAt(updated.length() - 1) == ',')
          {
            updated = updated.substring(0, updated.length() - 1);
          }
          updated += Const.SPACE + operation + Const.SPACE;
        }
        else
        {
          updated = ExpressionParser.parse(updated, (String) resultUnits.getSelectedItem())
              .toString();
          if (updated.charAt(updated.length() - 1) == '.'
              || updated.charAt(updated.length() - 1) == ',')
          {
            updated = updated.substring(0, updated.length() - 1);
          }
          updated += Const.SPACE + operation + Const.SPACE;
        }
        result = false;
        buttons.setResult(false);
        needEquals = true;
      }
    }
    else if (operation.equals(FRACTIONAL))
    {
      updated += ExpressionParser.parseUnary(numDisplay.getText(), operation,
          (String) units.getSelectedItem());
      result = true;
      buttons.setResult(true);
      needEquals = false;
      if (updated.charAt(updated.length() - 1) == '.'
          || updated.charAt(updated.length() - 1) == ',')
      {
        updated = updated.substring(0, updated.length() - 1);
      }
      updateHistory(FRACTIONALOUTPUT + numDisplay.getText() + " = " + updated);
      updated = FRACTIONALOUTPUT + numDisplay.getText() + " = " + updated;
    }

    // unitless
    else if ((operation.equals(Const.EQUALS) && needEquals)
        && !(numOutput.getText().equals(tempStuff)) && (units.getSelectedItem().equals("")))
    {
      updated += numDisplay.getText() + Const.SPACE + operation + Const.SPACE;

      // if (Lang.NUM_SEPERATOR.equals(Const.COMMA))
      // updated = updated.replace(',', '.'); // swap , with . so it can be calculated

      try
      {
        updated += ExpressionParser.parseUnitless(updated).toString();
        tempStuff = ExpressionParser.parseUnitless(updated).toString();
        // if (Lang.NUM_SEPERATOR.equals(Const.COMMA))
        // updated = updated.replace(',', '.'); // swap , with . so it can be calculated
        if (updated.charAt(updated.length() - 1) == '.'
            || updated.charAt(updated.length() - 1) == ',')
        {
          updated = updated.substring(0, updated.length() - 1);
        }
        updateHistory(updated);
      }
      catch (ArithmeticException e)
      {
        updated = Lang.DIVIDE_BY_ZERO_ERROR;
      }
      catch (NullPointerException n)
      {
        updated = Lang.ERROR;
      }

      if (isRelation)
      {
        resetOutput();
        new RelationalResultPopup(this, updated, operation);
      }
      else if (isExponentOperation)
      {
        updated = ExpressionParser.unitlessPowerReformat(updated);
        isExponentOperation = false;
        result = true;
        buttons.setResult(true);
        needEquals = false;
      }

      else
      {
        result = true;
        buttons.setResult(true);
        needEquals = false;
      }
    }
    else if ((operation.equals(Const.EQUALS) && needEquals) && (!numOutput.getText().equals("")))
    {
      // updated = numOutput.getText() + Const.SPACE;
      updated += numDisplay.getText() + Const.SPACE + units.getSelectedItem() + Const.SPACE
          + operation + Const.SPACE;

      // if (Lang.NUM_SEPERATOR.equals(Const.COMMA))
      // updated = updated.replace(',', '.'); // swap , with . so it can be calculated
      try
      {
        updated += ExpressionParser.parse(updated, (String) resultUnits.getSelectedItem())
            .toString();
        tempStuff = ExpressionParser.parse(updated, (String) resultUnits.getSelectedItem())
            .toString();

        // if (Lang.NUM_SEPERATOR.equals(Const.COMMA))
        // updated = updated.replace(',', '.'); // swap , with . so it can be calculated
        if (updated.charAt(updated.length() - 1) == '.'
            || updated.charAt(updated.length() - 1) == ',')
        {
          updated = updated.substring(0, updated.length() - 1);
        }
        System.out.println("before update :" + updated);
        updateHistory(updated);

      }
      catch (ArithmeticException i)
      {
        updated = Lang.DIVIDE_BY_ZERO_ERROR;
      }
      catch (NullPointerException n)
      {
        updated = Lang.ERROR;
      }

      if (isRelation)
      {
        resetOutput();
        new RelationalResultPopup(this, updated, operation);
      }
      else if (isExponentOperation)
      {
        updated = ExpressionParser.unitPowerReformat(updated);
        isExponentOperation = false;
        result = true;
        buttons.setResult(true);
        needEquals = false;
      }

      else
      {
        result = true;
        buttons.setResult(true);
        needEquals = false;
      }

    }
    else if (needEquals && !operation.equals(Const.EQUALS))
    {

      numOutput.setText(Lang.ERROR);

      return;
    }
    else if ((operation.equals(Const.EQUALS) && !needEquals))
    {
      clearOutput();
      return;
    }
    else if (!needEquals)
    {
      updated += numDisplay.getText() + Const.SPACE + units.getSelectedItem() + Const.SPACE
          + operation + Const.SPACE;
      updateResultComboBox(units.getSelectedItem());
      needEquals = true;
    }

    // System.out.println(setting.isThousandsSep());
    if (setting.isThousandsSep() && !operation.equals(Const.EQUALS))
    {
      System.out.println("!!!");
      String temp;
      if (!number.isEmpty())
      {
        temp = formatter.format(number.getDouble());
        if (temp.charAt(temp.length() - 1) == '.' || temp.charAt(temp.length() - 1) == ',')
        {
          temp = temp.substring(0, temp.length() - 1);
        }
      }
      else
      {
        String[] updatedArray = updated.split(Const.SPACE);
        temp = updatedArray[0];
        temp = temp.replace(",", ".");
        temp = formatter.format(Double.parseDouble(temp));
        if (temp.charAt(temp.length() - 1) == '.' || temp.charAt(temp.length() - 1) == ',')
        {
          temp = temp.substring(0, temp.length() - 1);
        }
      }
      updated = temp + Const.SPACE + units.getSelectedItem() + Const.SPACE + operation
          + Const.SPACE;
      // System.out.println(updated);
    }
    // black magic
    if (setting.isThousandsSep() && operation.equals(Const.EQUALS))
    {
      if (Lang.NUM_SEPERATOR.equals(Const.PERIOD))
      {
        updated = removeCommasHelper(updated);
      }
      else if (Lang.NUM_SEPERATOR.equals(Const.COMMA))
      {
        updated = replaceCommas(updated);
      }
      System.out.println("???");
      if (number.getDecimal())
      {
        System.out.println("in decimal");
        String[] updatedArray = updated.split(Const.SPACE);
        String front = updatedArray[0]; // first operator
        front = formatter.format(Double.parseDouble(front)); // make it look pretty
        if (front.charAt(front.length() - 1) == '.' || front.charAt(front.length() - 1) == ',')
        {
          front = front.substring(0, front.length() - 1);
        }
        front += Const.SPACE;
        front += updatedArray[2]; // gets the operation
        String[] tempArray = number.getNumber().split(SPLITBY);
        String temp = tempArray[0];
        if (!temp.isEmpty())
        {
          // System.out.println(temp);
          temp = formatter.format(Double.parseDouble(temp));
        }
        temp = temp.split(SPLITBY)[0];
        if (Lang.NUM_SEPERATOR.equals(Const.PERIOD))
        {
          temp += Const.PERIOD + tempArray[1] + units.getSelectedItem() + Const.SPACE + operation;
        }
        else if (Lang.NUM_SEPERATOR.equals(Const.COMMA))
        {
          temp += Const.COMMA + tempArray[1] + units.getSelectedItem() + Const.SPACE + operation;
        }
        if (!tempStuff.isEmpty() && !units.getSelectedItem().equals(""))
        {
          String[] tempStuffArray = tempStuff.split(Const.SPACE);
          String tempTemp = tempStuffArray[0];
          tempStuff = formatter.format(Double.parseDouble(tempTemp));
          System.out.println(tempStuff.charAt(tempStuff.length() - 1) + "~~~~~~~~");
          if (tempStuff.charAt(tempStuff.length() - 1) == '.'
              || tempStuff.charAt(tempStuff.length() - 1) == ',')
          {
            tempStuff = tempStuff.substring(0, tempStuff.length() - 1);
          }
          tempStuff += tempStuffArray[1];
        }
        else
        {
          tempStuff = formatter.format(Double.parseDouble(tempStuff));
          System.out.println(tempStuff.charAt(tempStuff.length() - 1) + "~~~~~~~~");
          if (tempStuff.charAt(tempStuff.length() - 1) == '.'
              || tempStuff.charAt(tempStuff.length() - 1) == ',')
          {
            tempStuff = tempStuff.substring(0, tempStuff.length() - 1);
          }
        }
        updated = front + Const.SPACE + temp + Const.SPACE + tempStuff;
      }
      else
      {
        System.out.println("in else");
        String[] updatedArray = updated.split(Const.SPACE);
        String front = updatedArray[0];
        front = formatter.format(Double.parseDouble(front));
        String temp = formatter.format(number.getDouble());
        if (temp.charAt(temp.length() - 1) == '.' || temp.charAt(temp.length() - 1) == ',')
        {
          temp = temp.substring(0, temp.length() - 1);
        }
        temp = temp + Const.SPACE + units.getSelectedItem();
        System.out.println("temp: " + temp + " updated: " + updated);
        if (!tempStuff.isEmpty() && !units.getSelectedItem().equals(""))
        {
          String[] tempStuffArray = tempStuff.split(Const.SPACE);
          String tempTemp = tempStuffArray[0];
          tempStuff = formatter.format(Double.parseDouble(tempTemp));
          if (tempStuff.charAt(tempStuff.length() - 1) == '.'
              || tempStuff.charAt(tempStuff.length() - 1) == ',')
          {
            tempStuff = tempStuff.substring(0, tempStuff.length() - 1);
          }
          tempStuff += tempStuffArray[1];
        }
        else
        {
          tempStuff = tempStuff.replace(",", ".");
          tempStuff = formatter.format(Double.parseDouble(tempStuff));
        }
        System.out.println("temp: " + temp);
        if (front.charAt(front.length() - 1) == '.' || front.charAt(front.length() - 1) == ',')
        {
          front = front.substring(0, front.length() - 1);
        }
        if (tempStuff.charAt(tempStuff.length() - 1) == '.'
            || tempStuff.charAt(tempStuff.length() - 1) == ',')
        {
          tempStuff = tempStuff.substring(0, tempStuff.length() - 1);
        }
        updated = front + Const.SPACE + updatedArray[2] + Const.SPACE + temp + Const.SPACE
            + operation + Const.SPACE + tempStuff;
        /* System.out.println("updated at the end: " + updated); */
      }
    }
    if (updated.charAt(updated.length() - 1) == '.' || updated.charAt(updated.length() - 1) == ',')
    {
      updated = updated.substring(0, updated.length() - 1);
    }

    // if (Lang.NUM_SEPERATOR.equals(Const.COMMA))
    // {
    // updated = updated.replace(',', '.'); // swap . with , so the commas are in
    // decimal again
    // }

    if (isRelation && !needEquals)

    {
      resetOutput();
      isRelation = false;
    }
    else
    {
      numOutput.setText(updated);
      number.clearNumber();
      numDisplay.setText("");
      buttons.enableDecimal(true);
    }
  }

  private String removeCommasHelper(final String s)
  {
    String temp = "";
    char[] array = s.toCharArray();
    for (int i = 0; i < array.length; i++)
    {
      if (array[i] != ',')
        temp += array[i];
    }
    return temp;
  }

  private String replaceCommas(final String s)
  {
    String temp = "";
    char[] array = s.toCharArray();
    for (int i = 0; i < array.length; i++)
    {
      if (array[i] == ',')
      {
        temp += '.';
      }
      else
      {
        temp += array[i];
      }
    }
    return temp;
  }

  // ##########################################################
  // Clear Methods
  // ##########################################################

  /**
   * Reset both the display as well as the current operand.
   */
  public void resetOutput()
  {
    numOutput.setText("");
    numDisplay.setText("");
    needEquals = false;
    result = false;
    buttons.setResult(false);
    buttons.enableDecimal(true);
    // buttons.opPad.disableAllOperationButtons();
    number.clearNumber();
  }

  /**
   * Reset only the current operand.
   */
  public void clearOutput()
  {
    numDisplay.setText("");
    buttons.enableDecimal(true);
    number.clearNumber();
  }

  /**
   * 
   * @return x
   */
  public boolean getNeedEquals()
  {
    return this.needEquals;
  }

  // ##########################################################
  // Action
  // ##########################################################

  @Override
  public void actionPerformed(final ActionEvent e)
  {
    String command = e.getActionCommand();
    if (command.equals(Lang.EXIT))
    {
      System.exit(0);
    }

    else if (command.equals(">"))
    {

      history.display();
    }

    else if (command.equals(Lang.NEW)) // this is still messed up
    {
      Calculator calc;

      try // do all object serialization here, especially the settings ones
      {
        history.changeButtonStatus(true);
        Serialization.write(history, CALCULATOR);
        calc = new Calculator();
        calc.setHistory((SessionHistory) Serialization.open(CALCULATOR));
        calc.setVisible(true);
        setLocation(this.getLocation().x + this.getWidth(), this.getLocation().y);
        if (setting.getKeyboardShortcuts() != null)
        {
          this.shortcutMap = setting.getKeyboardShortcuts();
          updateShortcuts(this.shortcutMap);
        }
      }
      catch (IOException ioe)
      {
        calc = new Calculator();
        calc.setVisible(true);
        setLocation(this.getLocation().x + this.getWidth(), this.getLocation().y);
      }
      // history.changeButtonStatus(false);
    }

    // changed to result formatter but what is the difference between open and edit
    // in the preferences menu
    else if (command.equals(Lang.RESULT_FORMATTER))
    {
      fd = new FormatDialog(this);
      fd.setVisible(true);
      String s = fd.getInput();
      this.formatter = new DecimalFormat(PERIODFORMAT + "0".repeat(Integer.parseInt(s))); // if you
      // close the
      // formatter
      // it breaks
      setting.setDecimalLength(Integer.parseInt(s));
    }
    else if (command.equals(Lang.APPLY))
    {
      String format = fd.getInput();
      try
      {
        int f = Integer.parseInt(format);
        System.out.println("Selected: " + selected);
        setting = new UserSetting(f, selected, setting.isThousandsSep());
        BinaryOperator.pickFormatter(true);
        setFormat(setting);
      }
      catch (NumberFormatException nfe)
      {
        // Do nothing?
        return;
      }
      fd.setVisible(false);
    }

    else if (command.equals(Lang.MAXIMUM))
    {
      selected = true;
    }

    else if (command.equals(Lang.FIXED))
    {
      selected = false;
    }

    else if (command.equals(Lang.SAVE))
    {
      try
      {
        Serialization.write(setting, "UserSettings");
      }
      catch (IOException e1)
      {
        // System.out.println("IOError?");
        e1.printStackTrace();
      }
    }
    else if (command.equals("RESET-SETTINGS"))
    {
      setting.resetSettings();
      setFormat(setting);
    }

    else if (command.equals(Lang.SAVE_AS))
    {
      try
      {
        Serialization.write(setting, FileSelector.saveFilePicker());
      }
      catch (IOException e1)
      {
        // System.out.println("IOError?");
        e1.printStackTrace();
      }
    }

    else if (command.equals(Lang.OPEN))
    {
      try
      {
        setting = (UserSetting) Serialization.open(FileSelector.openFilePicker());

        if (setting == null)
          setting = new UserSetting();

        System.out.println("Decimal Length: " + setting.getDecimalLength());
        setFormat(setting);
        this.shortcutMap = setting.getKeyboardShortcuts();
        if (setting.getKeyboardShortcuts() != null)
          updateShortcuts(this.shortcutMap);
      }
      catch (IOException e1)
      {
        System.out.println("IOError?");
        e1.printStackTrace();
      }
      catch (NullPointerException e1)
      {
      }
    }
  }

  /**
   * Adds a custom unit to the dialog.
   */
  public void addCustomUnit()
  {
    new CustomUnitDialog();
    updateComboBox();
  }

  /**
   * 
   */
  public void resetCustomUnits()
  {
    CustomUnitOperations.resetCustomUnits();
    updateComboBox();
  }

  /**
   * 
   */
  public void addKeyboardShortcutPopup()
  {
    ksm = new KeyboardShortcutMenu(buttons.getInputMap(), shortcutMap);
    updateShortcuts(ksm.getMap()); // to make all the new shortcut valid
    setting.setKeyboardShorcuts(this.shortcutMap);
    for (Character e : setting.getKeyboardShortcuts().keySet())
    {
      System.out.print(e + Const.SPACE + setting.getKeyboardShortcuts().get(e));
    }
  }

  /**
   * 
   * @param map
   */
  public void updateShortcuts(final HashMap<Character, String> map)
  {
    System.out.println("WE IN EHRE");
    for (Character e : map.keySet()) // refreshed the map so the shortcut is now there
    {
      inputMap.put(KeyStroke.getKeyStroke(e), map.get(e));
      // System.out.println("WE GOT THIS FAR");
    }
  }

  /**
   * This is here for potential problems with multiple instances of calculators. What happens if on
   * one instance you refresh, and on the other you try to use that type? The easiest solution would
   * probably be throw a custom error like MissingUnitException and then have it say please refresh
   */
  public void refreshCustomUnits()
  {
    updateComboBox();
  }

  /**
   * Updates the entires of the combo box with the new entry.
   */
  public void updateComboBox()
  {
    List<String> customUnits = CustomUnitOperations.readCustomUnits();

    String[] cUnits = new String[customUnits.size()];

    for (int i = 0; i < customUnits.size(); i++)
    {
      String unit = customUnits.get(i).split(Const.COMMA)[1];
      cUnits[i] = unit;
    }

    DefaultComboBoxModel<String> units1 = new DefaultComboBoxModel<>(cUnits);
    units.setModel(units1);
  }

  /**
   * 
   * @param unit
   */
  public void updateResultComboBox(final Object unit)
  {
	  
    String unitName = (String) unit;
    

    if (unitName.isEmpty())
    {
      resultUnits.setModel(new DefaultComboBoxModel<>());
      return;
    }
    else if (resultUnits.containsItem(unitName))
    {
      return;
    }

    resultUnits.updateBox(unitName);
  }

  /**
   * 
   * @param text
   */
  private void updateHistory(final String text)
  {
    if (isRelation)
    {
      return;
    }
    history.updateText(text);
  }
}
