package gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;

/**
 * AbstractAction Wrapper Class. Used for the ActionMap.
 * 
 * @author Team3D
 */
public class ClickAction extends AbstractAction
{

  private static final long serialVersionUID = 1L;
  private JButton button;

  /**
   * Makes a new Click.
   * 
   * @param button
   */
  public ClickAction(final JButton button)
  {
    super();
    this.button = button;
  }

  @Override
  public void actionPerformed(final ActionEvent e)
  {
    button.grabFocus();
    button.doClick(50);
  }
}
