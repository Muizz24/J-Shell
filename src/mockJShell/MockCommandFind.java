package mockJShell;
//**********************************************************
//Assignment2:
//Student1: Muizz Uddin Ahmed
//UTORID user_name: ahmed323
//UT Student #: 1004160043
//Author:
//
//Student2: Nicholas Michael Gibson Elliott
//UTORID user_name: ellio232
//UT Student #: 1004416713
//Author:
//
//Student3: Anthony Alaimo
//UTORID user_name: alaimoa1
//UT Student #: 1004421814
//Author:
//
//Student4: Millan Singh Khurana
//UTORID user_name: khuran53
//UT Student #: 1004169259
//Author:
//
//
//Honor Code: I pledge that this program represents my own
//program code and that I have coded on my own. I received
//help from no one in designing and debugging my program.
//I have also read the plagiarism section in the course info
//sheet of CSC B07 and understand the consequences.
//*********************************************************


import java.util.Arrays;
import driver.Directory;
import driver.File;

/**
 * Command that finds if a specific file or directory with name in quotations exists in the given
 * path's directories.
 * 
 * @author ahmed323
 *
 */
public class MockCommandFind extends MockCommand {
  /**
   * String search is a static that is used to concatenate updates in a search.
   */
  private static String search = "";
  /**
   * String that holds the documentation details for the command find
   */
  private static final String documentation =
      (" Description:\n--------------------------------------------\n\t"
          + "Finds if a Directory or File, depending on TYPE, with a matching"
          + "\n\twith a matching given NAME exists in a Directory or its\n\t"
          + "corresponding subdirectories.Whenever a search is succesful, the"
          + "\n\tsearch will provide a path to the File or Directory.\n"
          + " Syntax:\n--------------------------------------------\n\t"
          + "/#: findPATH(s) -type (f or d) -name \"NAME\"\n\tWhere f stands "
          + "for File, d stands for Directory.\n\tAdditionally, find accepts "
          + "ONE OR MORE path(s) only.\n\n\t===Using REDIRECTION SYNTAX:==="
          + " \n\t\tfind PATH(s) -type (f or d) -name \"NAME\" > OUTFILE\n\t\t"
          + "Where OUTFILE is the name of the file.\n\t\t> is for overwritting"
          + " OUTFILE and >> is for Appending into OUTFILE.\n\n Possible "
          + "Exceptions:\n--------------------------------------------\n\tinva"
          + "lidPathException, invalidSyntaxException, invalidNameException");

  /**
   * Helper method that converts the str representation in the find command's syntax into a File
   * class type of Directory class type.
   * 
   * @param typeInStr: The string representation of the type
   * @return the converted string representation that is in now a class type
   */
  private Object getType(String typeInStr) {
    // Instantiate an arbitrary variable if both cases aren't satisfied
    Object type = null;
    if (typeInStr.equals("f")) {
      // Convert the string into a File type if it was f
      type = File.class;
    } else if (typeInStr.equals("d")) {
      // Convert the string into a Directory type if it was d
      type = Directory.class;
    } else {
      // Assume user placed invalid syntax if no cases were satisfied
      MockExceptionHandler.invalidSyntaxException("find");
    }
    // Return the new class type
    return type;
  }

  /**
   * Helper method for run_command that strips the surrounding quotations and accepts string as a
   * name if and only if the name does not contain any special characters other than - or _
   * 
   * @param name: the string representation of the dir or file's name
   * @return stripped trailing quotations name without special characters
   */
  private String getName(String name) {
    // Ensure name does have outer quotations before stripping
    Integer lastCharIndex = name.length() - 1;
    if (name.indexOf("\"") == 0 && name.lastIndexOf("\"") == lastCharIndex) {
      name = name.substring(1, name.length() - 1);
    } else {
      // Assume user placed wrong input syntax for find and call an exception
      MockExceptionHandler.invalidSyntaxException("find");
      return null;
    }
    // Check if name contains no special characters inside before returning it
    if (MockValidator.validNameorPath(name, "[^A-Za-z0-9_-]")) {
      return name;
    } else {
      return null;
    }
  }

  /**
   * Acts as a setter method for the variable search. Concatenates a path when it is found to have
   * the given file or directory we are looking for.
   * 
   * @param dir: The directory that supposedly contains the given file or directory.
   */
  private void updateSearch(Directory dir) {
    /*
     * Edge case. Files are considered null types for directories so we ignore cases where those are
     * found.
     */
    if (dir != null) {
      // Concatenate the path of the directory to the search
      search += "->\t" + fs.tracePath(dir, "") + "\n";
    }
  }

  /**
   * Recursively searches all sub directories starting at a given directory to find if there exists
   * a File or Directory,depending on type, that matches the name given. If matched update the
   * search.
   * 
   * @param dir: Current directory that is being checked for the file or dir.
   * @param type: The type of the File or Directory we are looking for.
   * @param name: The name of the File or Directory we are looking for.
   */
  private void recSearchPath(Directory dir, Object type, String name) {
    /*
     * Base case 1 when the File we are looking for matches the name of a file inside the current
     * directory.
     */
    if (dir.getFile(name) instanceof File && type == File.class) {
      this.updateSearch(dir);
    }
    /*
     * Base case 2 when the Directory we are looking for matches the name of another Directory
     * inside the current directory.
     */
    else if (dir.getSubDirectory(name) != null && type == Directory.class) {
      this.updateSearch(dir);
    }
    /*
     * Recursive case: when there is nothing that matches the parameters given Traverse into the
     * other sub directories inside the current directories Base case 3 is when the Directory
     * contains no directories or when the loop fails to run.
     */
    for (Directory subDir : dir.getSubDirectories()) {
      this.recSearchPath(subDir, type, name);
    }
  }

  /**
   * Acts as the center of the find command's search. calls other helper methods to find and update
   * successful/failed searches per n amount of paths.
   * 
   * @param paths: all the paths listed by the user.
   * @param type: The type associated to the sub File/Directory in search of.
   * @param name: the name associated to the sub File/Directory in search of.
   * @return the end result of the search with all its attempts per valid path.
   */
  private void findPaths(String[] paths, Object type, String name) {
    for (String path : paths) {
      // Find if the given path exists and if it leads to a directory
      Object dirOrFile = fs.pathToDirorFile(path);
      if (dirOrFile instanceof Directory) {
        // Call the helper method to search inside the existing parent dir.
        this.recSearchPath(((Directory) dirOrFile), type, name);
      } else {
        this.updateSearch(null);
      }
    }
  }

  @Override
  /**
   * Runs the Find command to search through given paths
   * 
   * @param input: user input following after the command's name
   */
  public String runCommand(String input) {
    // Trim extra whitespace before splitting by every whitespace
    MockCommandFind.search = "";
    input = input.trim().replaceAll(" +", " ");
    String[] inputList = input.split(" ");
    // Check if the input followed proper syntax for the find command
    if (MockValidator.validateFind(inputList)) {
      // Get name from input and assign it to a variable.
      String name = this.getName(inputList[inputList.length - 1]);
      // Get type from input and assign it to a variable.
      Object type = this.getType(inputList[inputList.length - 3]);
      // Get All paths into a separate ArrayList
      String[] paths = Arrays.copyOfRange(inputList, 0, inputList.length - 4);
      // Call find helper that finds path of type with matching name.
      if (name != null && type != null) {
        this.updateSearchFormat(name, type);
        this.findPaths(paths, type, name);
      }
    } else {
      // Assume user inputed wrong syntax and return a syntax exception.
      MockExceptionHandler.invalidSyntaxException("find");
    }
    // Send string result into RedirectSystem before printing to console.
    return MockCommandFind.search;
  }

  @Override
  public String printDocumentation() {
    // TODO Auto-generated method stub
    return MockCommandFind.documentation;
  }

  /**
   * Creates a format for the search string before starting it to provide better readability to the
   * user.
   * 
   * @param name: The name of the File or Directory we are looking for.
   * @param type: The type of the File or Directory we are looking for.
   */
  private void updateSearchFormat(String name, Object type) {
    // Create a string variable for type to be translated to Dir or File.
    String typeStr;
    if (type == File.class) {
      typeStr = "File";
    } else {
      typeStr = "Directory";
    }
    // Add proper formatting at the start before beginning search.
    MockCommandFind.search = (name + " of type " + typeStr + " found in path("
        + "s):\n--------------------------------------------\n");
  }

}
