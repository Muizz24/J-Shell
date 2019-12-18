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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
/**
 * A Command in JShell that loads up a save file that represents a FileSystem,
 * so that the user can continue from where they last left off. 
 * 
 * 
 * @author Millan Singh Khurana
 * 
 */
public class Loader extends Command{
  /**
   * Stores the documentation for the given command Load.
   */
  private String documentation = "\n Description: \n\n Loads a save file"
      + " from the desired path on the user's computer, to resume the state"
      + " of the FileSystem from where the user left off. Can only be called"
      + " as the first command call, and not once the FileSystem has been"
      + " initialized. \n\n Examples:\n\n load /Users/jdoe/Desktop/"
      + "savefile.txt \n\n load /Users/wbilal/Desktop/Documents/filesystem.txt"
      + "\n\n Possible Exceptions:\n\n invalidComputerFileName, "
      + "invalidCommand, invalidLoadCall, unreadableFile\n";
  /**
   * Runs the command of load from JShell by checking the previous command
   * history and calling the helper function loadCommands depending on
   * whether the FileSystem has technically been initialized or not.
   * 
   * @param input: The input String that represents the path of the 
   * text file on their computer, that the user wants to load.
   * 
   * @return "": An empty string
   */
  @Override
  public String runCommand(String input){
    /*
     * Checks if no commands have been called (meaning the FileSystem has
     * not technically been instantiated) so load can still be called.
     */
    if(CommandHistory.getCommandHistory().size() == 0){
      // Calls the helper function to load the commands from the save file.
      loadCommands(input);
    }else{
      /*
       *  Calls an exception because the user has tried to call load when the
       *  FileSystem has already been instantiated.
       */
      ExceptionHandler.invalidLoadCall();
    }
    // Returns an empty string for command formatting purposes.
    return "";
  }
  /**
   * Reads the commands from the save file, then runs them to get the
   * FileSystem to its previously saved state.
   * 
   * @param input: The input String that represents the path of the 
   * text file on their computer, that the user wants to load.
   * 
   * @return None
   */
  private void loadCommands(String input){
    // Creates a file object for the file at the user's given path.
    File file = new File(input);
    try {
      // Sets up a BufferedReader to read the text from the save file.
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String command;
      /*
       * Saves the current PrintStream, then creates another PrintStream called
       * temporaryStream that doesn't write anything when called.
       */
      PrintStream originalStream = System.out;
      PrintStream temporaryStream = new PrintStream(new OutputStream(){
        public void write(int b) {
        }
      });
      /*
       * Sets the temporaryStream to the out stream so nothing is printed
       * to the console when running the commands to be loaded.
       */
      System.setOut(temporaryStream);
      // Runs a loop that goes through each line of text in the save file.
      while ((command = reader.readLine()) != null){
        /*
         *  Checks if the command is Save, which means there is no need
         *  to re-run the command as it will mess up the save file.
         */
        if (command.split(" ")[0].equals("save")){
          /*
           *  Doesn't run the save command but adds it to the history as it
           *  occurred at some point.
           */
          CommandHistory.addHistory(command);
        }else {
          /*
           * Creates a new Validator and sends the command to it, so that it
           * will get parsed and properly run.
           */
          Validator validator = new Validator(command);
        }
      }
      /*
       * Sets the out stream back to the original stream so that printing to
       * the console can continue.
       */
      System.setOut(originalStream);
    } catch (FileNotFoundException e) {
      // Calls an exception if the path the user has given isn't valid.
      ExceptionHandler.invalidComputerFileName(input);
    } catch (IOException e) {
      // Calls an exception if the file at the path is unreadable.
      ExceptionHandler.unreadableFile();
    }
  }

  /**
   * Prints the documentation String for this Command, that holds all of the 
   * necessary information to be printed when man load is called.
   * 
   * @param None
   * 
   * @return documentation: A string that contains off all of the required
   * documentation for the load method.
   */
  @Override
  public String printDocumentation() {
    return documentation;
  }
}
