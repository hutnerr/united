package app;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.SwingUtilities;

import fileCreator.FileCopier;
import gui.Calculator;

/**
 * This is the official driver for UnitED.
 * 
 * @author Team3D
 * @version 5/6/2024
 */
public class UnitED implements Runnable
{
	
	public static ResourceBundle strings;
    public static Path tempPath;
    public static Path customPath;
    public static Path officialUnits;

  /**
   * Main method. Opens the calculator.
   * 
   * @param args
   *          CLA that might've been passed in.
   */
    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(new UnitED());
    }

  @Override
  public void run()
  {  
    Calculator c = new Calculator();
    c.setVisible(true);
  }
}
