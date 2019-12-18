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


import driver.Directory;
import driver.File;

/**
 * The main system that redirects output either to the console or to an OUTFILE
 * 
 * @author ahmed323
 *
 */
public class MockRedirectSystem {
  /**
   * Variable reference just to call method redirectToFile
   */
  private static MockRedirectSystem callRedirect = new MockRedirectSystem();

  /**
   * Acts as the main hub for redirection. Decides where output goes to and whether or not a file
   * gets output appended or overwritten.
   * 
   * @param input: the original input of the user with separation on the Command's syntax and the >
   *        or >> to OUTFILE.
   * @param output: the result from the user's input command.
   */
  public static void redirectTo(String[] input, String output) {
    /*
     * depending on which redirect is being used, a flag is set to appropriately decide to append or
     * overwrite the text in the file that is going to be searched Additionally if the result is the
     * empty string then this means the output did not want anything redirected to an OUTFILE.
     */
    if (output.equals("")) {
    } else if (input[1].contains(">>")) {
      // get file name
      String fileName = input[1].substring(input[1].lastIndexOf(">>") + 3);
      // Call EchoAppend when syntax contains only >>
      callRedirect.redirectToFile(output, fileName, true);
    } else if (input[1].contains(">")) {
      String fileName = input[1].substring(input[1].lastIndexOf(">") + 2);
      // Call EchoFile when syntax contains only >
      callRedirect.redirectToFile(output, fileName, false);
    } else {
      // Print to console when no redirection is found
      System.out.println(output);
    }
  }

  /**
   * static separating method that appropriately finds the > or >> that calls for the redirection of
   * a command.
   * 
   * @param input: the user input that hasnt been separated yet
   * @return the separated user input depending on if > or >> exists.
   */
  public static String[] redirectionSeperator(String input) {
    // Initialize variables.
    String[] separator = {"", ""};
    String[] inputSplit = input.replaceAll(" +", " ").split(" ");
    // Get the redirecting string > or >>
    String redirector = inputSplit[Math.max(0, inputSplit.length - 2)];
    // Get the input and redirector separated at >'s or >>'s index.
    if (redirector.equals(">>") || redirector.equals(">")) {
      separator[1] = redirector + " " + inputSplit[inputSplit.length - 1];
      int endIndx = Math.max(0, input.length() - separator[1].length());
      separator[0] = input.substring(0, endIndx);
    } else if (redirector.matches("[^A-Za-z0-9_\\/-]*>{3,}")) {
      return null;
    } else {
      // No separation when > or >> does not exist in the input.
      separator[0] = input;
    }
    // Validate OUTFILE name if it follows correct naming conventions.
    Integer startOfSubString = Math.max(0, separator[1].lastIndexOf(" ") + 1);
    String path = separator[1].substring(startOfSubString);
    if (!(MockValidator.validNameorPath(path, "[^A-Za-z0-9_\\/-]"))) {
      // return null whenever OUTFILE avoids correct name conventions.
      return null;
    }
    /*
     * Get OUTFILE's parent path and Validate if path leads to a Directory in the FileSystem. If not
     * then cancel the command
     */
    String parentPath = path.substring(0, Math.max(0, path.lastIndexOf("/")));
    if (!(MockValidator.validatePathTo(parentPath))) {
      return null;
    }
    // Return the separation back to Parser.
    return separator;
  }

  /**
   * This method takes two strings, one representating a path or file name and text for that file,
   * and overwrites or that files current text with the given text.
   * 
   * @param text, a string representing the wanted text of a file
   * @param path, a string representing the path to or just the name of a file, which will be
   *        mutated
   * @param appendFlag, a condition statement that decides if text will append whenever appendFlag
   *        is true or overwrite whenever appendFlag is false into OUTFILE.
   */
  private void redirectToFile(String text, String path, boolean appendFlag) {
    /*
     * If a path is given we need to first see if the parent directory of the file exist
     */
    String parentPath = path.substring(0, path.lastIndexOf("/") + 1);
    Object parentDir = MockFileSystem.getInstance().pathToDirorFile(
        parentPath);
    /*
     * if the directory exist, we then check to see if the file exist. if the file exist we set or
     * append its current text with the provided text depending on the appendFlag. otherwise a new
     * file is created inside the parent directory, with the given file name and provided text.
     */
    if (parentDir instanceof Directory) {
      String name = path.substring(path.lastIndexOf("/") + 1);
      File outfile = ((Directory) parentDir).getFile(name);
      if (outfile instanceof File) {
        if (appendFlag) {
          String currentStr = outfile.getText();
          outfile.setText(currentStr + "\n" + text);
        } else {
          outfile.setText(text);
        }
      } else {
        File newfile = new File(name, text);
        ((Directory) parentDir).addFile(newfile);
      }
    }
  }

}
