// **********************************************************
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
package driver;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * A Command in JShell that saves the entire state of the current FileSystem 
 * as a file on the user's actual computer at the desired path.
 * 
 * @author Millan Singh Khurana
 *
 */
public class Saver extends Command {
  /**
   * Stores the documentation for the given command Save.
   */
  private String documentation = "\n Description: \n\n Saves"
      + " the entire current state of the FileSystem as a text file on the "
      + "user's computer so that it can be loaded up later."
      + " The save file is saved at the inputted path, with the given"
      + " name.\n\n Examples:\n\n save /Users/jdoe/Desktop/savefile.txt \n\n "
      + "save /Users/wbilal/Desktop/Documents/filesystem.txt "
      + "\n\n Possible Exceptions:\n\n invalidComputerPathException, "
      + "invalidCommand, invalidEncodingException\n";
  /**
   * A string that represents the user's desired file name for the save file.
   */
  private String fileName;
  /**
   * A string that represents the user's desired path on their computer for 
   * the save file.
   */
  private String filePath;
  /**
   * A list of strings that represents the various path locations from the
   * user's input.
   */
  private String[] paths;
  /**
   * An arraylist that represents the command history of this FileSystem.
   */
  private static ArrayList<String> commandHistory;

  /**
   * Runs the command of save from JShell by creating a new text file on the
   * user's computer at the desired path. 
   * 
   * @param input: Takes in the input String that represents the path and
   * name of the text file that the user wants to create on their computer.
   * 
   * @return "": An empty string
   */
  @Override
  public String runCommand(String input) {
    /*
     *  Checks if the inputted path is correctly formatted, as a proper 
     *  checkable path would have at least one "/"
     */
    if (!(input.contains("/"))){
      // Calls an exception as the user has not entered a usable path
      ExceptionHandler.invalidComputerPathException(input);
    }else {
      /*
       * Splits the input at each /, as the final text from this input
       * represents the file name and the rest represents the desired path 
       * location
       */
      paths = input.split("/");
      fileName = paths[paths.length-1];
      filePath = input.substring(0, input.length() - fileName.length() - 1);
      // Creates a file object on the computer with the desired name and path
      File saveFile = new File(filePath, fileName);
      try {
        // Sets up the file on the user's computer 
        saveFile.createNewFile();
        saveWriter(input);
      } catch (IOException e1) {
        ExceptionHandler.invalidComputerPathException(input);
      }
    }
    // Returns an empty string for command formatting purposes
    return "";
  }
  /**
   * Fills in the save file on the computer with the command history of
   * this FileSystem.
   * 
   * @param input: Takes in the input String that represents the path and
   * name of the text file that the user want's to create on their computer.
   * 
   * @return None
   */
  private void saveWriter(String input) {
    try {
      // Creates a new PrintWriter that will be used to write text in the file
      PrintWriter writer = new PrintWriter(input, "UTF-8");
      /*
       *  Gets the FileSystem's command history and loops through the whole
       *  list, then using the PrintWriter to write each of the corresponding
       *  commands to a new line on the save file
       */
      commandHistory = CommandHistory.getCommandHistory();
      for (String command : commandHistory) {
        writer.println(command);
      }
      writer.close();
    } catch (FileNotFoundException e) {
      // Calls an exception as the user has not entered a usable path
      ExceptionHandler.invalidComputerPathException(input);
    } catch (UnsupportedEncodingException e) {
      // Calls an exception as the user does not have UTF-8 set up
      ExceptionHandler.invalidEncodingException("UTF-8 encoding");
    }
  }
  /**
   * Prints the documentation String for this Command, that holds all of the 
   * necessary information to be printed when man save is called.
   * 
   * @param None
   * 
   * @return documentation: A string that contains off all of the required
   * documentation for the save method.
   */
  @Override
  public String printDocumentation() {
    return documentation;
  }
}
