package gui;

import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import math.expressions.ExpressionParser;
import math.units.Unit;

/**
 * Box that stores the potential units allowed for the output of a result.
 * 
 * @author Team3D
 */
public class ResultUnitsBox extends JComboBox<String>
{

  private static final long serialVersionUID = 6040870939140666833L;

  /**
   * Constructor.
   */
  public ResultUnitsBox()
  {
    super();
  }

  /**
   * Checks if the item is contained.
   * 
   * @param item
   *          The item we're looking for
   * @return True if it contains the item, false otherwise
   */
  public boolean containsItem(final String item)
  {
    ComboBoxModel<String> model = this.getModel();
    for (int i = 0; i < model.getSize(); i++)
    {
      if (item.equals(model.getElementAt(i)))
      {
        System.out.println("Found");
        return true;
      }
    }
    return false;
  }

  /**
   * Updates the ComboBox which lets you select your output unit.
   * 
   * @param unitName
   */
  public void updateBox(final String unitName)
  {
    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
    Unit unitType = ExpressionParser.searchAllUnits(unitName);
    List<String> resultUnitList = unitType.getUnitList();

    model.addElement(unitType.getAbbreviation());
    for (String unit : resultUnitList)
    {
      if (!unit.equals(unitType.getAbbreviation()))
      {
        model.addElement(unit);
      }
    }
    setModel(model);
  }

}
