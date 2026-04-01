package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.HashMap;

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.components.PopupErrorDialog;
import system.constants.Lang;

/**
 * 
 */
public class KeyboardShortcutMenu extends JDialog implements ActionListener, Serializable
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  // private String[] forKeyboardShortcuts = new String[] {"New Window", "Exit", "Add Custom Units",
  // "Reset Custom Units", "Refresh Custom Units", "Thousand Seperators", "Keyboard Shortcut Add",
  // "Keyboard Shortcut Reset"};
  private String[] forKeyboardShortcuts = new String[] {Lang.NEW_WINDOW, Lang.EXIT,
      Lang.ADD_CUSTOM_UNITS, Lang.RESET_CUSTOM_UNITS, Lang.REFRESH_CUSTOM_UNITS,
      Lang.THOUSAND_SEPERATOR, Lang.KEYBOARD_SHORTCUT, Lang.RESET};
  private Container contentPane;
  private KeyListener listener;
  private JTextField clickOnToAddShortcut;
  private KeyEvent key;
  private JComboBox<String> box;
  private HashMap<Character, String> currentShortcuts = new HashMap<>();
  // private InputMap numPadInputMap;

  /**
   * 
   * @param inputMap
   * @param map
   */
  public KeyboardShortcutMenu(final InputMap inputMap, final HashMap<Character, String> map)
  {
    super();
    currentShortcuts = map;
    // numPadInputMap = inputMap;
    listener = new KeyListener()
    {
      @Override
      public void keyPressed(final KeyEvent e)
      {
      }

      @Override
      public void keyReleased(final KeyEvent e)
      {
      }

      @Override
      public void keyTyped(final KeyEvent e)
      {
        // puts the Char typed into a JTextField and stores the variable until "OK" is
        // pressed
        String s = "" + e.getKeyChar();
        clickOnToAddShortcut.setText(s);
        key = e;
      }
    };
    this.addKeyListener(listener);
    setupLayout();
    setDefaultConfig();
  }

  /**
   * 
   * @return x
   */
  public HashMap<Character, String> getMap()
  {
    return this.currentShortcuts;
  }

  /**
   * 
   */
  public void resetMap()
  {
    currentShortcuts = new HashMap<>();
    new PopupErrorDialog(Lang.RESET);
  }

  private void setDefaultConfig()
  {
    setModalityType(ModalityType.APPLICATION_MODAL);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setAlwaysOnTop(true);
    this.setTitle(Lang.ADD);
    setSize(300, 200);
    setVisible(true);
  }

  private void setupLayout()
  {
    contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());
    makeContainer();
  }

  private void makeContainer()
  {
    box = new JComboBox<>(forKeyboardShortcuts);
    JButton okButton = new JButton(Lang.OK);
    okButton.addActionListener(this);
    okButton.setActionCommand(Lang.OK);
    clickOnToAddShortcut = new JTextField(Lang.PRESS_A_KEY);
    clickOnToAddShortcut.setHorizontalAlignment(JTextField.CENTER);
    clickOnToAddShortcut.setEditable(false);
    clickOnToAddShortcut.addKeyListener(listener);

    JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
    centerPanel.add(box);
    centerPanel.add(clickOnToAddShortcut);
    centerPanel.add(okButton);
    contentPane.add(centerPanel);
  }

  private void closeWindow()
  {
    setVisible(false);
    dispose();
  }

  /**
   * @param e
   * @return x
   */
  private boolean checkIfEventIsValid(final char e)
  {
    char[] invalidCharacters = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 's', 'r', 'c',
        '^', '*', '/', '+', '='};

    for (char c : invalidCharacters)
    {
      if (c == e)
        return true;
    }
    return false;
  }

  @Override
  public void actionPerformed(final ActionEvent e)
  {
    // TODO Auto-generated method stub
    String event = e.getActionCommand();

    // System.out.println("contains key: " +
    // currentShortcuts.containsKey(key.getKeyChar())
    // + " valid or not: " + checkIfEventIsValid(key.getKeyChar()));

    switch (event)
    {
      case ("OK"):
        if (this.key == null)
        {
          break;
        }
        if (!currentShortcuts.containsKey(key.getKeyChar()))
        {
          if (!checkIfEventIsValid(key.getKeyChar()))
          {
            currentShortcuts.put(key.getKeyChar(), (String) box.getSelectedItem());
            // System.out.println(key.getKeyChar());
            closeWindow();
            break;
          }
        }
        new PopupErrorDialog("Invalid shortcut! Please choose a different button!");
        break;
      default:
        // do nothing
    }
  }
}
