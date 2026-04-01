package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import system.constants.Lang;
import system.constants.UnitEDColor;
import system.constants.UnitEDFilepath;
import system.constants.UnitEDFont;

/**
 * Creates the Dialog window for the About menu item in calculator.
 * 
 * @author Team 3D
 * @version 5/6/2024
 */
public class AboutDialog extends JDialog implements Serializable
{
  private static final long serialVersionUID = -5299809113077168486L;
  private Container contentPane = this.getContentPane();

  /**
   * Constructs the dialog and sets the visuals up.
   */
  public AboutDialog()
  {
    super();
    setup();
    setVisible(false);
  }

  /**
   * Basic setup for the window that doesn't need to be handled by constructor.
   */
  public void setup()
  {
    setSize(450, 300);
    setForeground(Color.GRAY);
    setTitle(Lang.ABOUT);
    setResizable(false);
    setAlwaysOnTop(true);
    addAll();
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
  }

  /**
   * Adds everything into the contentPane.
   */
  public void addAll()
  {
    try
    {
      // scales and displays the image
      //URL url = this.getClass().getResource(UnitEDFilepath.LOGO);
     // ImageIcon icon = new ImageIcon(url);
      BufferedImage logo = ImageIO.read(new File(UnitEDFilepath.LOGO));
      JLabel logoLabel = new JLabel(new ImageIcon(logo.getScaledInstance(120, 60, Image.SCALE_SMOOTH)));
      
      logoLabel.setBorder(BorderFactory.createEmptyBorder(30, 20, 0, 20));

      // create the panel and add the components
      JPanel panel = new JPanel();
      panel.setBackground(UnitEDColor.GRAY);
      panel.setLayout(new BorderLayout());
      panel.add(logoLabel, BorderLayout.NORTH);
      panel.add(addText(), BorderLayout.CENTER);

      // add to the content pane
      contentPane.setLayout(new BorderLayout());
      contentPane.add(panel, BorderLayout.CENTER);
    }
    catch (IOException ioe)
    {
      // do nothing
    }
  }

  /**
   * Helper method that prevents multiple dialogs from being displayed when used with other classes.
   */
  public void display()
  {
    // if no instance opened, we're able to open one.
    if (!isActive())
      setVisible(true);
  }

  /**
   * Adds the text provided by the specs onto the dialog window.
   * 
   * @return the text provided by the specs
   */
  public JTextArea addText()
  {
    JTextArea text = new JTextArea();
    text.setFocusable(false);
    text.setFont(UnitEDFont.MS12B);
    text.setBackground(UnitEDColor.GRAY);
    text.setText(Lang.ABOUT_MESSAGE);
    text.setBounds(0, 100, 450, 150);

    return text;
  }
}
