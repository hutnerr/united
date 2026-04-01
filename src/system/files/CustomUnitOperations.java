package system.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gui.components.PopupErrorDialog;
import system.constants.UnitEDFilepath;
import system.exceptions.CustomUnitExistsException;

/*
 * CSV File Format
 * unit_type,unit_name,unit_weight
 * distance,mm,1.0
 */

/**
 * Helper class for working with the Custom Unit files.
 * 
 * @author Hunter Baker
 * @version 5/6/2024
 */
public class CustomUnitOperations
{
  public static final String OFFICIAL_UNITS_FILEPATH = "src/assets/customUnits/occifial_units.csv";
  public static final String CUSTOM_UNITS_FILEPATH = "src/assets/customunits/custon_units.csv";

  /**
   * Reads the current file of Custom Units.
   * 
   * @return the list of everything in CustomUnits csv file
   */
  public static List<String> readCustomUnits()
  {
    List<String> units = new ArrayList<>();

    // read the file and add to the array list
    try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOM_UNITS_FILEPATH)))
    {
      String line;
      while ((line = reader.readLine()) != null)
      {
        units.add(line);
      }
    }
    catch (IOException e)
    {
      System.out.println("CUSTOM UNITS READER - READING FAILED");
      // Reading failed, shouldn't happen since we have control of our file
    }
    return units;
  }

  /**
   * Writes the new addition to the file.
   * 
   * @param unit
   * @throws CustomUnitExistsException
   */
  public static void writeCustomUnits(final String type, final String unit, final String weight)
      throws CustomUnitExistsException
  {
    if (unitExists(unit))
      throw new CustomUnitExistsException(unit);

    double temp;
    try
    {
      temp = Double.parseDouble(weight);
    }
    catch (NumberFormatException e)
    {
      new PopupErrorDialog("Enter a valid number for the weight");
      return;
    }

    if (temp < 0)
    {
      new PopupErrorDialog("Enter a non-negative number for the weight");
      return;
    }

    // buildUnit turns the string entry into a proper csv entry
    // EX: frog -> frog_type,frog,1
    String builtUnit = buildUnit(type, unit, temp);

    // opens the file in "append" mode so we can just add the line to the end.
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOM_UNITS_FILEPATH, true)))
    {
      writer.write(builtUnit);
      writer.newLine();
    }
    catch (IOException e)
    {
      System.out.println("CUSTOM UNITS READER - WRITING FAILED");
    }
  }

  /**
   * Reset the custom units by simply copying over our "static" official units file.
   */
  public static void resetCustomUnits()
  {
    Path official = Paths.get(OFFICIAL_UNITS_FILEPATH);
    Path custom = Paths.get(CUSTOM_UNITS_FILEPATH);

    // the resetting is done by just coping over the "template" of the official units
    try
    {
      Files.copy(official, custom, StandardCopyOption.REPLACE_EXISTING);
    }
    catch (IOException e)
    {
      System.out.println("CUSTOM UNITS READER - RESETTING FAILED");
    }
  }

  public static String[] readUnitTypes()
  {
    List<String> currentUnits = readCustomUnits();
    Set<String> set = new HashSet<>();

    for (int i = 0; i < currentUnits.size(); i++)
    {
      set.add(currentUnits.get(i).split(",")[0].trim());
    }

    String[] temp = new String[set.size() + 1];

    temp[0] = "";

    int index = 1;
    for (String s : set)
    {
      temp[index++] = s;
    }
    return temp;
  }

  /**
   * Checks if the string exist in a list of entries.
   * 
   * @param name
   *          The string to check
   * @return True if it exists, false otherwise
   */
  public static boolean unitExists(final String name)
  {
    List<String> currentUnits = readCustomUnits();

    for (String unit : currentUnits)
    {
      // index 1 because the csv file is type,unit,weight
      String unitName = unit.split(",")[1].trim();

      if (name.trim().equalsIgnoreCase(unitName))
        return true;
    }
    return false;
  }

  /**
   * Builds the unit for the CSV File addition.
   * 
   * Would build the addition of Frog as: frog_type,frog,1
   * 
   * @param unit
   *          The unit name we're adding
   * @return The built addition
   */
  private static String buildUnit(final String type, final String unit, final double weight)
  {
    return String.format("%s,%s,%f", type, unit, weight);
  }
}
