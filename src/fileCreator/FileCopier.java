package fileCreator;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class FileCopier
{
  /**
   * Copy all of the files from a resources package to a temporary directory on the file system
   * (whether or not the code and resources are in a .jar file or on the file system).
   *
   * This implementation assumes the resource package is a subpackage of the package that contains
   * this class (though that can be changed_.
   *
   * @param id
   *          The ID to use for the temporary directory
   * @param subdir
   *          The name of the subpackage that contains the resources
   * @return The Path of the temporary directory
   * @throws IOException
   *           If something goes wrong
   * @throws URISyntaxException
   *           If something goes wrong
   */
  public static Path copyResourcesToTemp(final String id, final String subdir)
      throws IOException, URISyntaxException
  {

    // Get the location of the ResourceCopier.class file
    String canonicalName = FileCopier.class.getName();
    String packageName = FileCopier.class.getPackageName();
    String className = canonicalName.substring(packageName.length() + 1) + ".class";
    String thisLocation = FileCopier.class.getResource(className).toString();

    // Remove the file name from the location and create a URI
    int fileStart = thisLocation.indexOf(className);
    String rootURL = thisLocation.substring(0, fileStart);
    URI sourceURI = new URI(rootURL + subdir);

    // Get the Path for source files (whether in a .jar file or the file system)
    Path sourcePath;
    FileSystem fileSystem = null;
    if (sourceURI.getScheme().equals("jar"))
    {
      fileSystem = FileSystems.newFileSystem(sourceURI, new HashMap<String, Object>());
      sourcePath = fileSystem.getPath("/" + packageName + "/" + subdir);
    }
    else
    {
      sourcePath = Paths.get(sourceURI);
    }

    // Get a List of all of the files in the source Path
    Stream<Path> files = Files.list(sourcePath);
    List<Path> filesList = files.toList();

    // Create a temporary directory on the local file system
    Path temp = Files.createTempDirectory(id);
    Path destinationPath = Path.of(temp.toString());

    // Copy all of the files from the source Path to the temporary directory
    for (Path file : filesList)
    {
      Path targetFile = Path.of(destinationPath.toString(), file.getFileName().toString());
      Files.copy(file, targetFile);
    }

    files.close();
    if (fileSystem != null)
      fileSystem.close();

    return destinationPath;
  }

  /**
   * Create a file and return its file path.
   * 
   * @param fileName The name of the file
   * @return The path of the newly created file
   */
  public static Path fileCreator(final String fileName)
  {
   
    File file = new File(fileName);
   
    try {
        if (file.createNewFile()) {
            System.out.println("File created: " + file.getAbsolutePath());
        } else {
            System.out.println("File already exists.");
        }
    } catch (IOException e) {
        System.out.println("An error occurred while creating the file.");
        e.printStackTrace();
    }
    File filePath = new File(file.getAbsolutePath());
   
    return Path.of(filePath.getParent());
  }
}
