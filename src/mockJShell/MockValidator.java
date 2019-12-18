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
package mockJShell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import driver.Directory;
import driver.ExceptionHandler;
import driver.File;
import driver.FileSystem;

/**
 * Can take the formatted input from Parser and call the corresponding Command with the user's
 * input. Also contains methods that can be called upon to see if a given path, directory, or name
 * that a user has inputed is valid.
 * 
 * @author Millan Singh Khurana
 *
 */
public class MockValidator {

  /**
   * Takes the formatted input from Parser and creates an instance of the corresponding Command with
   * the user's input, or calls an exception if the user hasn't inputed a valid command.
   * 
   * @param inputList
   * @param input
   */
  public MockValidator(String input) {
    // Adds the inputed command to the command history
    MockCommandHistory.addHistory(input);
    String[] redirectSplit = MockRedirectSystem.redirectionSeperator(input);
    /*
     * Creates an instance of Validator to ensure that the user's input is correctly formatted, and
     * then calls the corresponding Command. Ensures redirection of OUTFILE maintains proper naming
     * conventions before continuing to command execution.
     */
    if (redirectSplit != null && !(input.equals(""))) {
      input = redirectSplit[0];
      String[] inputList = input.split(" ");
      this.validateCmd(inputList, input, redirectSplit);
    }
  }

  private void validateCmd(String[] inputList, String input, String[] redrct) {
    // A boolean used to track if the user's input represents a usable Command.
    boolean commandCheck = false;
    /*
     * Loops through the commandList to check if the user's input correlates to one of the usable
     * Commands.
     */
    for (String name : MockCommand.getCommands()) {
      if (inputList[0].toLowerCase().equals(name)) {
        /*
         * Checks if the user inputed either nothing or only one word, then sets the input to a
         * blank String if that is the case as for this scenario the user would be trying to call a
         * command that doesn't require input (ex. pwd, ls).
         * 
         */
        if (inputList.length <= 1) {
          input = "";
        } else {
          // Removes the command from the input String.
          input = input.substring(name.length() + 1);
        }
        commandCheck = true;
        /*
         * Casts the input String (that represents a usable command) into an instance of that actual
         * Command, and then calls the print_documentation method to print all of the necessary
         * information.
         */
        String output = (((MockCommand) MockCommand.execute(name)).
            runCommand(input));
        // Redirect output before printing to console
        MockRedirectSystem.redirectTo(redrct, output);
        break;
      }
    }
    /*
     * Calls an invalidCommand exception if the user's input doesn't represent a usable Command.
     */
    if (!commandCheck) {
      MockExceptionHandler.invalidCommand(input);
    }
  }

  /**
   * Validate if the given user input contains leading whitespace.
   * 
   * @param input: User given inputed text.
   * @return A boolean which is false if input contains leading Whitespace.
   */
  public static boolean validateLeadingWhiteSpace(String input) {
    // Return false if string contains leading whitespace(s) and true if not.
    if (input.matches("^\\s+(.)*")) {
      MockExceptionHandler.leadingWhiteSpacesException(input);
      return false;
    }
    return true;
  }

  /**
   * Takes in a directory and a file name and returns true if a file with that name exists in the
   * directory, otherwise it returns false.
   * 
   * @param dir_with_file
   * @param file_name
   * @return Returns true if the file exists, otherwise false.
   */
  public static boolean validFile(Directory dirWithFile, String fileName) {
    // Get all files in the given directory
    List<File> dirFiles = dirWithFile.getAllFiles();
    // find if the file name is in the list. if not found, file doesn't exist
    for (File file : dirFiles) {
      if (file.getName().equals(fileName)) {
        return true;
      }
    }
    // Return an Error Message
    return false;
  }

  /**
   * Takes in a directory parent_dir and a directory name dir_name and returns true if a directory
   * with dir_name exists as a child of the parent_dir, otherwise it returns false.
   * 
   * @param parentDir
   * @param dirName
   * @return true if dirName is a child of parentDir, otherwise returns false
   */
  public static boolean validDir(Directory parentDir, String dirName) {
    // Get all sub directories in the parent
    List<Directory> subDirs = parentDir.getSubDirectories();
    // find if the dir name exists in the list. if not, dir doesn't exist.
    for (Directory dir : subDirs) {
      if (dir.getName().equals(dirName)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Takes in a String name_or_path that represents a name or path, and checks if the name or path
   * contain any special characters. Returns true if there are no special character present,
   * otherwise returns false.
   * 
   * @param name_or_path
   * @param regex
   * @return true if no special characters are present, otherwise false
   */
  public static boolean validNameorPath(String nameOrPath, String regex) {
    // Stores the regex formatted version of name_or_path
    String dummy = nameOrPath.replaceAll(regex, "");
    // Check if the new regex formated name_or_path is different
    if (dummy.equals(nameOrPath) == false) {
      // If regex version is different, deduce whether string is path or name
      if (dummy.indexOf("/") != -1) {
        MockExceptionHandler.invalidPathException(nameOrPath);
      } else {
        MockExceptionHandler.invalidNameException(nameOrPath);
      }
    } else {
      // In this case, regex version = name or string thus return True.
      return true;
    }
    // After returning proper exception, return false
    return false;
  }

  /**
   * Finds if the syntax for valid is in proper format. Returns an error if not
   * 
   * @param input_split: The split input of the syntax for find
   * @return a boolean dictating whether or not the syntax is proper or not
   */
  public static boolean validateFind(String[] inputSplit) {
    // Instantiate variable
    boolean validated = false;
    // return true if all conditions below are satisfied.
    if (inputSplit.length >= 5) {
      if (inputSplit[inputSplit.length - 2].equals("-name")
          && inputSplit[inputSplit.length - 4].equals("-type")) {
        // conditions are met and syntax is correct
        validated = true;
      }
    } else {
      // syntax is wrong therefore boolean is false.
      validated = false;
    }
    // return boolean
    return validated;
  }

  /**
   * Checks if path leads somewhere in the FileSystem
   * 
   * @param path
   * @return
   */
  public static boolean validatePathTo(String path) {
    // Validate if parent path leads to a Directory inside the FileSystem
    if (MockFileSystem.getInstance().
        pathToDirorFile(path) instanceof Directory) {
      return true;
    }
    // return false if path does not lead to a Directory in the FileSystem.
    return false;
  }
  
  /**
   * Finds if an oldPath is the parent of newPath for copy and move.
   * 
   * @param oldPath: String representation of the previous path
   * @param newPath: String representation of the new path
   * @return boolean returning true if oldPath is a parent of newPath, false otherwise.
   */
  public static boolean validateParent(Directory oldDir, Directory newDir) {
    // get the full paths of both directories
    String oldPath = MockFileSystem.getInstance().tracePath(oldDir, "");
    String newPath = MockFileSystem.getInstance().tracePath(newDir, "");
    /*
     * oldDir is a parent of newDir whenever the index of oldDir's occurrence is 0 in the path of
     * newDir.
     */
    if (newPath.indexOf(oldPath) == 0) {
      MockExceptionHandler.parentPathException(
          oldPath, newPath);
      return true;
    }
    return false;
  }
}
