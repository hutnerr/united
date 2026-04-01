package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import gui.components.PopupErrorDialog;
import system.constants.Lang;
import system.constants.UnitEDFont;
import system.exceptions.CustomUnitExistsException;
import system.files.CustomUnitOperations;

/**
 * Dialog to add Custom Units to the Calculator.
 * 
 * @author Hunter Baker
 */
public class CustomUnitDialog extends JDialog implements ActionListener, Serializable
{

  private static final long serialVersionUID = 3122876712584386897L;

  private static final String OK = "Ok";
  private static final String CANCEL = "Cancel";

  private static final Font TEXT_FONT = UnitEDFont.SS12B;

  Container contentPane = this.getContentPane();
  BorderLayout mainLayout;

  private JTextField unitEnterField;
  private JComboBox<String> unitTypeEnterField;
  private JTextField unitWeightField;

  /**
   * Constructor.
   */
  public CustomUnitDialog()
  {
    super();
    mainLayout = new BorderLayout();
    contentPane.setLayout(mainLayout);
    buildCustomUnitNameTextBox();
    buildButtons();
    setDefaultConfig();
  }

  /**
   * Setup the dialog
   */
  private void setDefaultConfig()
  {
    setModalityType(ModalityType.APPLICATION_MODAL); // pauses the rest of the app until adding is
    // done
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setAlwaysOnTop(true);
    setTitle("Add Unit");
    setSize(250, 250);
    setVisible(true);
  }

  /**
   * Text box that allows the user to input a custom type.
   */
  private void buildCustomUnitNameTextBox()
  {
    JPanel pan = new JPanel();
    pan.setLayout(new GridLayout(3, 1, 5, 5));
    // pan.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    unitTypeEnterField = new JComboBox<>(CustomUnitOperations.readUnitTypes());
    unitTypeEnterField.setEditable(true);
    unitTypeEnterField.setFont(TEXT_FONT);
    Border border = BorderFactory.createTitledBorder(Lang.UNIT_TYPE);
    Border spacingBorder = BorderFactory.createEmptyBorder(3, 20, 5, 20);
    unitTypeEnterField.setBorder(BorderFactory.createCompoundBorder(spacingBorder, border));
    pan.add(unitTypeEnterField);

    unitEnterField = new JTextField();
    unitEnterField.setFont(TEXT_FONT);
    border = BorderFactory.createTitledBorder(Lang.UNIT_NAME);
    spacingBorder = BorderFactory.createEmptyBorder(3, 20, 5, 20);
    unitEnterField.setBorder(BorderFactory.createCompoundBorder(spacingBorder, border));
    pan.add(unitEnterField);

    unitWeightField = new JTextField();
    unitWeightField.setFont(TEXT_FONT);
    border = BorderFactory.createTitledBorder("Weight");
    spacingBorder = BorderFactory.createEmptyBorder(3, 20, 5, 20);
    unitWeightField.setBorder(BorderFactory.createCompoundBorder(spacingBorder, border));
    pan.add(unitWeightField);

    contentPane.add(pan, BorderLayout.CENTER);
  }

  /**
   * Helper to create the buttons.
   */
  private void buildButtons()
  {
    JPanel pan = new JPanel();
    GridLayout grid = new GridLayout(1, 2);
    pan.setLayout(grid);
    makeButton(pan, OK, Lang.OK);
    makeButton(pan, CANCEL, Lang.CANCEL);
    contentPane.add(pan, BorderLayout.SOUTH);
  }

  private void makeButton(final JPanel pan, final String name, final String displayName)
  {
    JButton button = new JButton(displayName);
    button.setActionCommand(name);
    button.addActionListener(this);
    pan.add(button);
  }

  // ######################################
  // Button Logic
  // ######################################

  @Override
  public void actionPerformed(final ActionEvent e)
  {
    String event = e.getActionCommand();
    switch (event)
    {
      // write the new unit to the file
      // if it already exists (only way to get this exception), then it will open a
      // dialog to
      // explain the error
      case (OK):
        try
        {
          CustomUnitOperations.writeCustomUnits((String) unitTypeEnterField.getSelectedItem(),
              unitEnterField.getText(), unitWeightField.getText());
        }
        catch (CustomUnitExistsException cuee)
        {
          new PopupErrorDialog(cuee.getMessage());
        }
        break;
      default:
        // do nothing
        break;
    }
    // close the window if either button was pressed.
    setVisible(false);
    dispose();
  }

  /*
   * // used to populate the units List<Row> entries;
   * 
   * record Row(String type, String unit, double factor) { // can use this without making getters //
   * Row temp = new Row("This", "Example", 1.0); // temp.type() would eval to "This" // temp.unit()
   * would eval to "Example" // etc }
   */

}
