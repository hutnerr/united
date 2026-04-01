package system.files;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import system.constants.Const;

/**
 * Helper class for allowing selection of files.
 * 
 * @author Griffin & Hunter
 * @version 5/6/2024
 */
public class FileSelector
{
  /**
   * Allows you to select where to save and name your .dat file.
   * 
   * @return The File saved
   */
  public static File saveFilePicker()
  {
    JFileChooser fc = new JFileChooser(); // opens dialog to select your filepath
    if (fc.showSaveDialog(fc) == JFileChooser.APPROVE_OPTION)
    {
      // returns the filepath with the .dat extention
      return new File(fc.getSelectedFile().toString() + Const.DAT_EXT);
    }
    return null;
  }

  /**
   * Opens a Dialog which allows you to select a .dat file.
   * 
   * @return The File selected
   */
  public static File openFilePicker()
  {
    JFileChooser fp = new JFileChooser(); // opens dialog to select your filepath

    // this filter makes it so we can only select files with the .dat extention
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Preference files", "dat");
    fp.setFileFilter(filter);

    int returnVal = fp.showOpenDialog(fp);
    if (returnVal == JFileChooser.APPROVE_OPTION)
    {
      return fp.getSelectedFile(); // once we've selected OK, return the filepath to save
    }
    return null;
  }
}
