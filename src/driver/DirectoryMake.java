package driver;
// **********************************************************
//
// Teammate1: Muizz Uddin Ahmed
//
// Teammate2: Nicholas Michael Gibson Elliott
//
// Teammate3: Anthony Alaimo
//
// Teammate4: Millan Singh Khurana
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************



/**
 * Command in Jshell that makes new Directories in the fs
 * 
 * @author Muizz Uddin Ahmed
 * @version 1.0
 */
public class DirectoryMake extends Command {
  /**
   * Stores the documentation for the given command mkdir
   */
  private String documentation =
      ("\n Description: \n\n Creates a directory(s) with the given name(s), th"
          + "ey can be either a relative or full path. \n\n Possible Exceptio"
          + "ns: \n\n invalidPathException," + " DirNameExistsException \n");

  /**
   * Takes an ArrayList of all directories' and modifies the fs with respect to the desired
   * paths (relative, full or none) by creating directories at the specified path.
   * 
   * @param dirs: Takes a String, with each index containing a path and the directory that needs to
   *        be made at the end.
   * @return None
   * 
   */
  private void mkdir(String path) {
    // get name of the new directory and check if it is a valid name
    String name = path.substring(path.lastIndexOf("/") + 1);
    Object prntDir = null;
    if (Validator.validNameorPath(name, "[^A-Za-z0-9_-]")) {
      // Condition for if path traverses multiple directories
      if (path.indexOf("/") != -1) {
        // Condition for whenever prntDir is root
        if (path.lastIndexOf("/") == 0) {
          prntDir = fs.getRoot();
        } else {
          // Get parent directory along with its path
          String prntPath = path.substring(0, path.lastIndexOf("/"));
          prntDir = fs.pathToDirorFile(prntPath);
        }
      }
      // Condition for when parent directory is the current directory
      else {
        // Add to current directory
        prntDir = fs.getCurDir();
      }
      // Check if the prntDir given is a Directory
      if (prntDir instanceof Directory) {
        // Make sure Directory doesn't exist or else return an exception.
        if (Validator.validDir((Directory) prntDir, name)) {
          ExceptionHandler.nameExistsException(name);
        } else {
          ((Directory) prntDir).addDirectory(new Directory(name));
        }
      }
    }
  }


  /**
   * Runs the command of the mkdir from Jshell by looping through each path and adds there
   * corresponding directory by using mkdir method.
   * 
   * @param Input: Takes in the input after the command input
   */
  @Override
  public String runCommand(String input) {
    // Remove whitespace from both front and back then split into diff paths
    String[] paths = input.trim().replaceAll(" +", " ").split(" ");
    // Check if the syntax was proper for given command
    if (paths[0] == "") {
      ExceptionHandler.invalidSyntaxException("mkdir");
    } else {
      // loop through each path until all paths have been addressed
      for (String path : paths) {
        this.mkdir(path);
      }
    }
    return "";
  }

  @Override
  public String printDocumentation() {
    // TODO Auto-generated method stub
    return (documentation);
  }
}
