package gui.components;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JTextField;

import system.constants.Lang;
import system.constants.UnitEDColor;
import system.constants.UnitEDFont;

/**
 * Pop up Error Dialog to be used whenever an error is encountered.
 * 
 * @author Hunter Baker
 * @version 5/6/2024
 */
public class PopupErrorDialog extends JDialog implements ActionListener
{

  private static final long serialVersionUID = 6905737332511922571L;
  private Container contentPane = getContentPane();

  /**
   * Constructor for no custom message.
   */
  public PopupErrorDialog()
  {
    this("ERROR");
  }

  /**
   * Constructor for a custom message.
   * 
   * @param error
   *          The error message you want displayed.
   */
  public PopupErrorDialog(final String error)
  {
    super();
    contentPane.setLayout(new BorderLayout());
    buildErrorTextField(error);
    buildOkButton();
    setDefaultConfig();
  }

  /**
   * Sets "base" aspects of the Dialog.
   */
  private void setDefaultConfig()
  {
    setModalityType(ModalityType.APPLICATION_MODAL); // This makes it so you have to close the error
                                                     // dialog before doing anything else.
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setAlwaysOnTop(true);
    setTitle(Lang.ERROR);
    setSize(400, 125);
    setVisible(true);
  }

  /**
   * Builds the Text Field where the error message will be located.
   * 
   * @param error
   *          The message that will be displayed
   */
  private void buildErrorTextField(final String error)
  {
    JTextField errorField = new JTextField();
    errorField.setText(error);
    errorField.setEditable(false);
    errorField.setFont(UnitEDFont.SS12B);
    errorField.setHorizontalAlignment(JTextField.CENTER);
    errorField.setBackground(UnitEDColor.ERROR_RED);
    contentPane.add(errorField, BorderLayout.CENTER);
  }

  /**
   * Builds the OK Button and adds the functionality for closing.
   */
  private void buildOkButton()
  {
    Button okButton = new Button(Lang.OK);
    okButton.addActionListener(e -> closeWindow());
    okButton.setFont(UnitEDFont.SS12B);
    okButton.setBackground(Color.WHITE);
    contentPane.add(okButton, BorderLayout.SOUTH);
  }

  /**
   * Method to close the window.
   */
  private void closeWindow()
  {
    setVisible(false);
    dispose();
  }

  @Override
  public void actionPerformed(final ActionEvent e)
  {
    // empty because we're just using a Lambda
  }
}
