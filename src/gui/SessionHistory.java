package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import system.constants.Const;
import system.constants.Lang;
import system.constants.UnitEDColor;
import system.constants.UnitEDFont;

/**
 * A JDialog used to track various outputs provided by another object on a text field. Provides
 * capability to implement a sliding animation.
 * 
 * @author Griffin
 * @version 4/12/2024
 */
public class SessionHistory extends JDialog implements ActionListener, Serializable
{
  private static final long serialVersionUID = 2308776953275460561L;
  private static final Font TEXT_FONT = UnitEDFont.SS12B;
  private static final Color COLOR = UnitEDColor.GRAY;
  Container contentPane = this.getContentPane();
  JTextArea output;
  JButton toggle;
  boolean canDisplay = true;

  /**
   * Create the Dialog and add components.
   */
  public SessionHistory()
  {
    setup();
    createTextBox();
    createOpenToggle();
    setAlwaysOnTop(false);
    // updateText("Initial session text to test visibility and updating
    // mechanism.");

    addWindowListener(new WindowAdapter()
    {
      @Override
      public void windowClosing(final WindowEvent e)
      {
        closeHistoryWindow();
      }
    });

  }

  /**
   * Prepares the Dialog for the component additions.
   */
  public void setup()
  {
    setSize(300, 550);
    setTitle(Lang.CALCULATON_HISTORY);
    setLayout(new BorderLayout());
    setFocusable(false);
    setResizable(false);
    setVisible(false);
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
  }

  /**
   * Create the text area and add it to the pane.
   */
  public void createTextBox()
  {
    output = new JTextArea();
    output.setFont(TEXT_FONT);
    output.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    output.setBackground(COLOR);
    output.setEditable(false);
    contentPane.add(output, BorderLayout.CENTER);
  }

  /**
   * Create the close button and add to the pane.
   */
  private void createOpenToggle()
  {
    toggle = new JButton(Const.LESSTHAN);
    toggle.setFont(TEXT_FONT);
    toggle.addActionListener(this);

    JPanel frameToggle = new JPanel();

    frameToggle.setBorder(BorderFactory.createEmptyBorder(250, 0, 250, 0));
    frameToggle.add(toggle);
    contentPane.add(frameToggle, BorderLayout.EAST);
  }

  public void changeButtonStatus(boolean b)
  {
    canDisplay = b;
  }

  /**
   * Uses a timer to repeatedly call the move method until the history dialog is visible, moves in a
   * sliding animation.
   */
  public void display()
  { // fix the slide
    if (this.isActive())
    {
      canDisplay = false;
    }

    if (canDisplay)
    {
      toggle.setEnabled(false);
      this.setVisible(true);
      Timer timer = new Timer();
      TimerTask timerTask = new TimerTask()
      {
        private static int count = 0;

        @Override
        public void run()
        {
          if (count >= 390)
          {
            timer.cancel();
            timer.purge();
            toggle.setEnabled(true);
            count = 0;
            return;
          }
          move(1);
          count++;
        }
      };
      timer.scheduleAtFixedRate(timerTask, 0, 1);
      canDisplay = false;
    }
    else
      return;
  }

  /**
   * Helper method to be used by other objects, resets the output text.
   */
  public void clearText()
  {
    output.setText("");
  }

  /**
   * Helper method that returns the current calculation history.
   * 
   * @return the Text
   */
  public String getText()
  {
    String text = output.getText();
    System.out.println("Returning text: " + text);
    return text;
  }

  /**
   * Helper method to be used by other objects to edit the output. If there is no text directly
   * prints the message, else adds it to the next line.
   * 
   * @param message
   *          The message to write
   */
  public void updateText(final String message)
  {
    String current = output.getText();
    String updated;

    if (!current.isEmpty())
    {
      updated = current + Const.NEWLINE + message;
    }
    else if (!current.equals(""))
    {
      updated = current + Const.NEWLINE + message;
    }
    else
    {
      updated = message;
    }
    output.setText(updated);
    System.out.println("updateText called: " + updated);
  }

  /**
   * Shifts the window to the left or right by 1 unit/pixel.
   * 
   * @param direction
   *          Right if positive, left if negative
   */
  public void move(final int direction)
  {
    Rectangle rect = this.getBounds();
    if (direction > 0)
    {
      rect.x++;
    }
    else
    {
      rect.x--;
    }
    this.setBounds(rect);
  }

  /**
   * Allows the canDisplay parameter to be reset.
   */
  public void reverseCanDisplay()
  {
    canDisplay = !canDisplay;
    // canDisplay = true;
  }

  /**
   * Moves the window to the left back to its original position in a sliding animation.
   */
  @Override
  public void actionPerformed(final ActionEvent e)
  {
    String command = e.getActionCommand();
    if (command.equals(Const.LESSTHAN))
    {
      closeHistoryWindow();
    }
  }

  private void closeHistoryWindow()
  {
    toggle.setEnabled(false);
    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask()
    {
      private static int count = 0;

      @Override
      public void run()
      {
        if (count >= 390)
        {
          timer.cancel();
          timer.purge();
          setVisible(false);
          count = 0;
          return;
        }
        move(-1);
        count++;
      }
    };
    timer.scheduleAtFixedRate(timerTask, 0, 1);
    canDisplay = true;
  }
}
