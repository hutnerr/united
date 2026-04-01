package system.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import system.constants.Const;
import system.constants.UnitEDFilepath;

/**
 * Helper class for Serialization.
 * 
 * @author Team3D
 * @version 5/6/2024
 */
public class Serialization
{

  // ##########################################################
  // Write Methods
  // ##########################################################

  /**
   * Saves an Object to a .dat file.
   * 
   * @param obj
   *          The object you're writing
   * @param filename
   *          The file name to write as
   * @throws IOException
   *           Thrown on writing
   */
  public static void write(final Object obj, final String filename) throws IOException
  {
	  String userHome = System.getProperty("user.home"); // Get user home directory
	    File directory = new File(userHome, "YourAppName/data"); // Append your specific path
	    if (!directory.exists()) {
	        directory.mkdirs(); // Create directories if they do not exist
	    }
	    File file = new File(directory, filename + ".dat");
	    write(obj, file); // Call the second write method
  }

  /**
   * Saves an Object to a .dat file.
   * 
   * @param obj
   *          The object you're writing
   * @param file
   *          The file to write as
   * @throws IOException
   *           Thrown on writing
   */
  public static void write(final Object obj, final File file) throws IOException
  {
    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));

    out.writeObject(obj);
    out.flush();
    out.close();
  }

  // ##########################################################
  // Open Methods
  // ##########################################################

  /**
   * Reads an Object from a file. (Must be cast later)
   * 
   * @param stream2
   *          The name of the file to read
   * @return The new Object, null is not found
   * @throws IOException
   *           Thrown on reading
   */
  public static Object open(final String filename) throws IOException {
	    String resourcePath = "/assets/serialized/" + filename + ".dat";
	    InputStream stream = Serialization.class.getResourceAsStream(resourcePath);
	    if (stream != null) {
	        return open(stream);
	    }
	    // If not found in JAR, try reading from the external directory
	    String userHome = System.getProperty("user.home");
	    File file = new File(userHome, "YourAppName/data/" + filename + ".dat");
	    if (!file.exists()) {
	        return null; // File not found, handle this scenario appropriately
	    }
	    return open(file);
	}
  public static Object open(final File file) throws IOException {
	    return open(new FileInputStream(file));
	}
  
  

  /**
   * Reads an Object from a file. (Must be cast later)
   * 
   * @param file
   *          The file to read
   * @return The new Object, null is not found
   * @throws IOException
   *           Thrown on reading
   */
  public static Object open(final InputStream stream) throws IOException {
	    ObjectInputStream in = new ObjectInputStream(stream);
	    Object readFile;
	    try {
	        readFile = in.readObject();
	    } catch (ClassNotFoundException cnfe) {
	        readFile = null;
	        cnfe.printStackTrace();
	    } finally {
	        in.close();
	    }
	    return readFile;
	}
}
