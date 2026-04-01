package gui;



import java.awt.Desktop;



import java.awt.Frame;

import java.io.File;

import java.io.BufferedWriter;

import java.io.File;

import java.io.FileOutputStream;

import java.io.IOException;

import java.io.InputStream;

import java.io.OutputStreamWriter;

import java.io.Serializable;

import java.net.URISyntaxException;

import java.nio.file.Files;

import java.nio.file.StandardCopyOption;





import javax.swing.JMenu;

import javax.swing.JMenuBar;

import javax.swing.JMenuItem;

import javax.swing.JOptionPane;

import javax.swing.JTextArea;



import system.constants.Lang;

import system.constants.UnitEDFilepath;



/**

 * HelpFileFormat is used to create the menu bar used to store the help file format.

 * 

 * @author Team 3D

 * @version 04/03/2024

 */

public class HelpFileFormat extends JMenuBar implements Serializable

{



  private static final long serialVersionUID = -6899581137775071330L;

  // private final String ENGLISHHELPFILE = UnitEDFilepath.ENGLISH_HELP;

  private JMenuItem printSession;

  private JMenu file;

  private AboutDialog about = new AboutDialog();

  private Calculator calc;



  /**

   * Used to construct the HelpFileFormat.

   * 

   * @param printPreviewArea

   * @param calc

   */

  

  public HelpFileFormat() {

    super();

  }



  public HelpFileFormat(final JTextArea printPreviewArea, final Calculator calc)



  {



    this.calc = calc;

    file = new JMenu(Lang.FILE);

    printSession = new JMenuItem(Lang.PRINT_SESSION);



    file.add(printSession);

    this.add(file);



    JMenu helpMenu = new JMenu(Lang.HELP);

    JMenuItem helpItem = new JMenuItem(Lang.HELP);

    JMenuItem aboutMenu = new JMenuItem(Lang.ABOUT);



    helpMenu.add(helpItem);

    helpMenu.add(aboutMenu);

    this.add(helpMenu);



    helpItem.addActionListener(e -> openHelpFile());

    aboutMenu.addActionListener(e -> about.display());



    printSession.addActionListener(e -> runPrintDialog());

  }



  /**

   * Overloaded helper method that adds a menu item to the file menu. The default position is the

   * last index.

   * 

   * @param item

   *          The menu item to add

   */

  public void fileMenuAdd(final JMenuItem item)

  {

    fileMenuAdd(item, file.getItemCount());

  }



  /**

   * Helper method that adds a menu item to the file menu to the provided index.

   * 

   * @param item

   *          The menu item to add

   * @param index

   *          The index to insert into

   */

  public void fileMenuAdd(final JMenuItem item, final int index)

  {

    file.add(item, index);

  }



  /**

   * Opens the help file.

   */



  public void openHelpFile()

  {

    String helpFilePath = resolveHelpFilePath();  



    InputStream helpFileStream = getClass().getResourceAsStream(helpFilePath);



    if (helpFileStream == null) {



        JOptionPane.showMessageDialog(this, "Help file not found", "Error", JOptionPane.ERROR_MESSAGE);



        return;



    }



    File tempHelpFile = null;



    try {



        // Create a temporary directory for HTML and associated resources



        File tempDirectory = Files.createTempDirectory("helpFiles").toFile();



        tempHelpFile = new File(tempDirectory, "helpfile.html");



        tempHelpFile.deleteOnExit();



        



        // Copy HTML content to the temp file



        try (FileOutputStream fileOutputStream = new FileOutputStream(tempHelpFile);



             InputStream stream = helpFileStream) {



            byte[] buffer = new byte[1024];



            int bytesRead;



            while ((bytesRead = stream.read(buffer)) != -1) {



                fileOutputStream.write(buffer, 0, bytesRead);



            }



        }







        // Copy image files



        String[] imageFiles = {"EngPicOne.png", "EngPicTwo.png","EngPicThree.png","EngPicFour.png", "GermPicOne.png", "GermPicTwo.png","GermPicThree.png","GermPicFour.png"}; // list all images



        for (String imageFile : imageFiles) {



            InputStream imageStream = getClass().getResourceAsStream("/assets/helpfile/" + imageFile);



            if (imageStream != null) {



                File outFile = new File(tempDirectory, imageFile);



                outFile.deleteOnExit();



                Files.copy(imageStream, outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);



                imageStream.close();



            }



        }







        // Open the temporary HTML file in the default browser



        Desktop.getDesktop().browse(tempHelpFile.toURI());



    } catch (IOException e) {



        JOptionPane.showMessageDialog(this, "Error opening help file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);



    }



  }



  private String resolveHelpFilePath()

  {

    String basePath = "/assets/helpfile/";

    switch (Lang.HELP_PAGE) {

      case "Spanish":

        return basePath + "helpfileSpanish.html";

      case "German":

        return basePath + "helpfileGerman.html";

      default:

        return basePath + "helpfile.html";

      

    }

  }



  /**

   * 

   */

  public void runPrintDialog()

  {

    SessionHistory history = calc.getHistory();

    // history.setVisible(true); // This line can be omitted if not needed

    history.updateText(history.getText());



    PrintDialog printDialog = new PrintDialog(Frame.getFrames()[0], calc.getHistory()); // Using the

                                                                                        // first



    printDialog.setAlwaysOnTop(false);

    printDialog.setLocale(getLocale());



    printDialog.setVisible(true);

  }

}