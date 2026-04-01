package gui;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 * 
 */
public class RelationalResultPopup extends JDialog
{

  private static final long serialVersionUID = -6926563363954145615L;
  JTextPane textPane;
  String expression;
  String operation;

  /**
   * 
   * @param owner
   * @param expression
   * @param operation
   */
  public RelationalResultPopup(final JFrame owner, final String expression, final String operation)
  {
    setSize(300, 100);
    setResizable(false);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setAlwaysOnTop(true);
    setModal(true);
    this.expression = expression.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    this.operation = operation;
    display();
    setLocationRelativeTo(owner);
    setVisible(true);

  }

  private void display()
  {
    textPane = new JTextPane();
    textPane.setEditable(false);
    textPane.setContentType("text/html");
    textPane.setSize(this.getSize());
    int fontSize = (int) (Math.max(textPane.getWidth(), textPane.getHeight()) / 13);

    String text = "<html><body style='text-align: center; font-family: SansSerif; "
        + "font-weight: bold; font-size: " + fontSize + "px;'>" + expression + "</body></html>";

    textPane.setText(text);

    this.add(new JScrollPane(textPane), BorderLayout.CENTER);
  }

}
