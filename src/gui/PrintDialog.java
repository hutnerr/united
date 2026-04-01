package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import system.constants.Lang;

/**
 * 
 */
public class PrintDialog extends JDialog implements Printable, ActionListener
{

  private static final long serialVersionUID = -3993285685762168959L;
  private JButton printButton, previewButton, setupButton;
  private SessionHistory sessionHistory;
  private JTextArea printPreviewArea;

  /**
   * 
   * @param owner
   * @param history
   */
  public PrintDialog(final Frame owner, final SessionHistory history)
  {
    super(owner, Lang.PRINT_SESSION, false);
    this.sessionHistory = history;
    setupUI();
    this.setLocale(getLocale());
  }

  private void setupUI()
  {
    printButton = new JButton(Lang.PRINT);
    previewButton = new JButton(Lang.PREVIEW);
    setupButton = new JButton(Lang.PRINT_SETUP);

    printButton.addActionListener(this);
    previewButton.addActionListener(this);
    setupButton.addActionListener(this);

    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(printButton);
    buttonPanel.add(previewButton);
    buttonPanel.add(setupButton);

    printPreviewArea = new JTextArea(20, 40);
    printPreviewArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(printPreviewArea);

    this.setLayout(new BorderLayout());
    this.add(scrollPane, BorderLayout.CENTER);
    this.add(buttonPanel, BorderLayout.SOUTH);
    this.pack();
  }

  /**
   * 
   * @param e
   */
  public void actionPerformed(final ActionEvent e)
  {
    if (e.getSource() == printButton)
    {
      performPrint();
    }
    else if (e.getSource() == previewButton)
    {
      updatePreview();

    }
    else if (e.getSource() == setupButton)
    {
      PrinterJob job = PrinterJob.getPrinterJob();
      job.pageDialog(job.defaultPage());
    }
  }

  private void updatePreview()
  {
    sessionHistory.validate();
    setAlwaysOnTop(true);
    sessionHistory.repaint(); // Ensure the latest GUI changes are rendered
    String sessionText = sessionHistory.getText();
    if (sessionText.isEmpty())
    {
      sessionText = Lang.HISTORYERROR;
    }
    printPreviewArea.setText(sessionText);
    System.out.println("Preview updated with session text: " + sessionText);
  }

  private void performPrint()
  {
    PrinterJob job = PrinterJob.getPrinterJob();
    job.setPrintable(this);
    boolean doPrint = job.printDialog();
    setAlwaysOnTop(true);
    if (doPrint)
    {
      try
      {
        job.print();
        
      }
      catch (PrinterException ex)
      {
        JOptionPane.showMessageDialog(this, "Failed to print: " + ex.getMessage(), "Printing Error",
            JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  /**
   * x.
   * 
   * @param g
   * @param pf
   * @param page
   * @return x
   */
  public int print(final Graphics g, final PageFormat pf, final int page) throws PrinterException
  {
    if (page > 0)
    { /* We have only one page, and 'page' is zero-based */
      return NO_SUCH_PAGE;
    }
    /*
     * User (0,0) is typically outside the imageable area, so we must translate by the X and Y
     * values in the PageFormat to avoid clipping
     */
    Graphics2D g2d = (Graphics2D) g;
    g2d.translate(pf.getImageableX(), pf.getImageableY());
    /* Now we perform our rendering */
    g.drawString(sessionHistory.getText(), 100, 100);

    /* tell the caller that this page is part of the printed document */
    return PAGE_EXISTS;
  }
}
