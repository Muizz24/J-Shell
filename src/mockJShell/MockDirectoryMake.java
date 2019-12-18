package mockJShell;
// **********************************************************
// Assignment2:
// Student1: Muizz Uddin Ahmed
// UTORID user_name: ahmed323
// UT Student #: 1004160043
// Author:
//
// Student2: Nicholas Michael Gibson Elliott
// UTORID user_name: ellio232
// UT Student #: 1004416713
// Author:
//
// Student3: Anthony Alaimo
// UTORID user_name: alaimoa1
// UT Student #: 1004421814
// Author:
//
// Student4: Millan Singh Khurana
// UTORID user_name: khuran53
// UT Student #: 1004169259
// Author:
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************

import driver.Directory;
import driver.FileSystem;

/**
 * Command in Jshell that makes new Directories in the FileSystem
 * 
 * @author Muizz Uddin Ahmed
 * @version 1.0
 */
public class MockDirectoryMake extends MockCommand {
  /**
   * Stores the documentation for the given command mkdir
   */
  private String documentation =
      ("\n Description: \n\n Creates a directory(s) with the given name(s), th"
          + "ey can be either a relative or full path. \n\n Possible Exceptio"
          + "ns: \n\n invalidPathException," + " DirNameExistsException \n");

  /**
   * Takes an ArrayList of all directories' and modifies the FileSystem with respect to the desired
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
    if (MockValidator.validNameorPath(name, "[^A-Za-z0-9_-]")) {
      // Condition for if path traverses multiple directories
      if (path.indexOf("/") != -1) {
        // Condition for whenever parent_dir is root
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
      // Check if the parent_dir given is a Directory
      if (prntDir instanceof Directory) {
        // Make sure Directory doesn't exist or else return an exception.
        if (MockValidator.validDir((Directory) prntDir, name)) {
          MockExceptionHandler.nameExistsException(name);
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
      MockExceptionHandler.invalidSyntaxException("mkdir");
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
