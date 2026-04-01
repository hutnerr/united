package app;

import java.io.Serializable;
import java.util.Locale;

import gui.AboutDialog;
import gui.Calculator;
import gui.FormatDialog;
import gui.PrintDialog;
import gui.SessionHistory;
import gui.components.PopupErrorDialog;
import system.constants.Lang;

/**
 * Driver method for testing our calculator.
 */
public class TestDriver implements Serializable
{

  // ##########################################################
  // Main Method
  // ##########################################################

  private static final long serialVersionUID = 1L;

  /**
   * Main method that is ran for testing.
   * 
   * @param args
   *          command line arguments
   */
  public static void main(final String[] args)
  {

    // Locale.setDefault(new Locale("es", "US")); // SPANISH
    // Locale.setDefault(new Locale("en", "US")); // ENGLISH
    // Locale.setDefault(new Locale("de", "DE")); // German


    runCalculator();

    //setLanguage("german");

	  //if (args.length > 0) { // Check if there is at least one command line argument
	    //    setLanguage(args[0].toLowerCase()); // Set the language based on the first argument
	  //  } else {
	      //  setLanguage("german"); // Default to German if no arguments are provided
	   // }

  
//
//  testInternationalzation();
//
//	  if (args.length > 0) { // Check if there is at least one command line argument
//	        setLanguage(args[0].toLowerCase()); // Set the language based on the first argument
//	    } else {
//	        setLanguage("german"); // Default to German if no arguments are provided
//	    }

   // testInternationalzation();

    // testInternationalzation();


//    testInternationalzation();
//
//     testInternationalzation();
  }

  
  /**
   * Helper method designed to test the locale defaults
   * 
   * @param lang The language to change to
   */
  private static void setLanguage(String lang)
  {
    if (lang.equals("spanish"))
    {
      Locale.setDefault(new Locale("es", "US")); // SPANISH
    }
    else if (lang.equals("german"))
    {
      Locale.setDefault(new Locale("de", "DE")); // German
    }
    else
    {
      Locale.setDefault(new Locale("de", "DE")); // ENGLISH
    }
  }

  // ##########################################################
  // Window Run Methods
  // ##########################################################

  /**
   * Runs the calculator which is the main window.
   */
  private static void runCalculator()
  {
    Calculator c = new Calculator();
    c.setVisible(true);
  }
  
  
  /**
   * Executes the change of the locale
   */
  private static void testInternationalzation()
  {
    System.out.println(Lang.CANCEL);
  }
}
