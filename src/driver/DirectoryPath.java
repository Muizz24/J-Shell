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
 * Command in Jshell that prints current working dir in the fs
 * 
 * @author Muizz Uddin Ahmed
 * @version 1.0
 */
public class DirectoryPath extends Command {
  /**
   * Stores the documentation for the given command pwd
   */
  private String documentation = ("\n Description: \n\n Prints the current"
      + " working directory (including the whole path).\n\n Examples:\n\n pwd"
      + "\n\n Possible Exceptions: \n\n invalidSyntaxException \n");
  private static String curDirPath = "";

  /**
   * Prints current working Directory
   */
  private String printCurrentPath() {
    return fs.tracePath(fs.getCurDir(), "");
  }

  /**
   * Runs the DirectoryPath whenever pwd is called from the Jshell
   * 
   * @param Input: Takes input of whatever is taken after 'pwd'
   */
  @Override
  public String runCommand(String input) {
    // Make sure there is no input given after pwd is written.
    if (input.length() == 0) {
      // call helper method printCurrentPath to print the path of curDir
      DirectoryPath.curDirPath = this.printCurrentPath();
    } else {
      // Call an exception if pwd has more input after the command itself
      ExceptionHandler.invalidSyntaxException("pwd");
      return "";
    }
    return DirectoryPath.curDirPath;
  }

  @Override
  public String printDocumentation() {
    // TODO Auto-generated method stub
    return (documentation);
  }

}
