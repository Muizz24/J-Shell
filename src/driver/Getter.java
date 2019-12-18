//**********************************************************
// Teammate1: Muizz Uddin Ahmed
//
// Teammate2: Nicholas Michael Gibson Elliott
//
// Teammate3: Anthony Alaimo
//
// Teammate4: Millan Singh Khurana
//
//
//Honor Code: I pledge that this program represents my own
//program code and that I have coded on my own. I received
//help from no one in designing and debugging my program.
//I have also read the plagiarism section in the course info
//sheet of CSC B07 and understand the consequences.
//*********************************************************

package driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
/**
 * A Command in JShell that reads the text on a given URL, and puts all of that
 * text into a file in the current working directory.
 * 
 * @author Millan Singh Khurana
 */
public class Getter extends Command {
  /**
   * Stores the documentation for the given command Get.
   */
  private String documentation = "\n Description: \n\n Gets"
      + " the text from a given URL and creates a file in the current"
      + " directory with the corresponding name and all of the text. "
      + "\n\n Examples:\n\n get http://www.utsc.utoronto.ca/~nick/cscB36/185"
      + "/lecture.txt \n\n Possible Exceptions:\n\n invalidURLException, "
      + "invalidCommand\n";
  /**
   * A string that stores the contents from the file at the URL
   */
  private String fileContents;
  /**
   * A boolean that keeps a track of whether the givenURL is valid.
   */
  private boolean validURL;
  /**
   * This method calls the readLink helper function to get the contents of
   * the file at the URL, and then creates a file with the correct name
   * in the current directory, that contains the contents of the URL file.
   * 
   * @param input: A string representing the URL path that contains the file
   * 
   * @return "": An empty string
   */
  @Override
  public String runCommand(String input) {
    validURL = true;
    /*
     * Gets the contents of the file at the URL, by calling the readLink helper
     * function.
     */
    fileContents = readLink(input);
    if (validURL) {
      // Gets the desired name of the file.
      String fileName = input.substring(input.lastIndexOf("/")+1, 
          input.length());
      // Creates the file with the desired name and all of the contents.
      File file = new File(fileName, fileContents);
      // Adds this file into the current directory of the FileSystem
      FileSystem.getInstance().getCurDir().addFile(file);
    }
    // Returns an empty string for command formatting purposes
    return "";
  }
  // TODO Auto-generated method stub

  /**
   * This method takes a string input of a URL link and returns back all of the 
   * contents from the text file at the given URL.
   * 
   * @param input: A string representing a URL link
   * 
   * @return fileContents: A string representing the contents retrieved 
   * from the given URL
   */
  private String readLink(String input){
    /*
     * Adds a new line so that the file doesn't print on the same line as
     * the JShell formatting.
     */
    fileContents = "\n";
    URL url;
    try {
      // Creates a new URL object.
      url = new URL(input);
      /*
       *  Connects to a URL link and then creates a BufferedReader to 
       *  read the lines of text at the URL.
       */
      URLConnection connectURL = url.openConnection();
      BufferedReader in = new BufferedReader(new InputStreamReader
          (connectURL.getInputStream()));
      String inputLine;
      /*
       * Runs a loop that sets inputLine to a new line of text from the URL,
       * with each iteration, and runs for the length of the URL contents.
       */
      while ((inputLine = in.readLine()) != null)
        // Adds the current line plus a new line marker to the fileContents.
        fileContents += inputLine + "\n";
      in.close();
      /*
       * Calls an exception if the given URL isn't properly formatted or
       * doesn't contain a readable text file.
       */ 
    } catch (MalformedURLException e) {
      validURL = false;
      ExceptionHandler.invalidURLException(input);
    } catch (IOException e) {
      validURL = false;
      ExceptionHandler.invalidURLException(input);
    }
    return fileContents;
  }

  /**
   * Prints the documentation String for this Command, that holds all of the 
   * necessary information to be printed when man get is called.
   * 
   * @param None
   * 
   * @return documentation: A string that contains off all of the required
   * documentation for the get method.
   */
  @Override
  public String printDocumentation() {
    return documentation;
  }
}
