package gui;

import java.io.Serializable;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import system.files.CustomUnitOperations;

/**
 * UnitComboBox is used to create the dropDown box for the units.
 * 
 * @author Team 3D
 * @version 03/04/2024
 */
public class UnitComboBox extends JComboBox<String> implements Serializable
{
  private static final long serialVersionUID = 4361891780069056157L;

  /**
   * Constructor.
   */
  public UnitComboBox()
  {
    super();
    String[] cUnits = readUnits();
    setModel(new DefaultComboBoxModel<String>(cUnits));
  }

  /**
   * Helper method to read the custom_units.csv file to populate the entries.
   * 
   * @return an array of the units.
   */
  public static String[] readUnits()
  {
	  List<String> customUnits = CustomUnitOperations.readCustomUnits();

	  String[] cUnits = new String[customUnits.size()];

	  for (int i = 0; i < customUnits.size(); i++)
	  {
	    String[] splitUnit = customUnits.get(i).split(",");
	    if (splitUnit.length > 1) {
	      cUnits[i] = splitUnit[1]; // Ensure that there are at least two elements
	    } else {
	      cUnits[i] = "Unknown"; // Or handle the case appropriately if there's no second element
	    }
	  }

	  return cUnits;
  }
}
