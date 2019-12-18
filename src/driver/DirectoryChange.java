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
package driver;

/**
 * This class corresponds to the cd method, which changes the current working directory to the
 * directory DIR, when an input in the format, "cd DIR", is entered.
 * 
 * @author Nicholas Michael Gibson Elliott
 */
public class DirectoryChange extends Command {
  /**
   * Stores the documentation for the given command mkdir
   */
  private String documentation =
      (" Description:\n--------------------------------------------\n\tChanges"
          + " the current directory to the given directory (can be either a re"
          + "lative or full path).\n Syntax:\n--------------------------------"
          + "------------\n\t/#: cd NEWPATH\n\t Where NEWPATH is a path that c"
          + "an lead to a directory or a file.\n\n\t===REDIRECTION===\n\t/#: c"
          + "d NEWPATH [> OUTFILE]\n\t/#: cd NEWPATH [>> OUTFILE]\n\tWhere > i"
          + "s for overwritting text in OUTFILE while >> appends text into OUT"
          + "FILE\n Possible Exceptions:\n------------------------------------"
          + "--------\n\tinvalidPathException, invalidSyntaxException\n");

  /**
   * This method calls the changeDirectory method in this class, DirectoryChange, with the String
   * input as its only parameter.
   * 
   * @param input The input entered by the user to be used for the cd command.
   * @return Blank String
   */
  public String runCommand(String input) {
    // Removes all spaces in the input string
    input = input.trim().replaceAll(" +", "");
    // If the input is non-empty
    if (input != "") {
      // Call the changeDirectory method with the given input
      this.changeDirectory(input);
    }
    // Otherwise, meaning the input is empty
    else {
      // Call invalid syntax exception in exception handler
      ExceptionHandler.invalidSyntaxException("cd");
    }
    // Proceed to redirection before ending execute
    return "";
  }

  /**
   * This method changes the current working directory to the directory indicated by the String
   * input, whether relative to the current directory, or to the root.
   * 
   * @param input A String representation of the desired new current working directory.
   */
  private void changeDirectory(String input) {
    /*
     * Find the desired directory by calling the pathToDirORFile method with the inputed path as its
     * parameter
     */
    Object newDir = fs.pathToDirorFile(input);
    // If newDir is a Directory, as desired
    if (newDir instanceof Directory) {
      /*
       * Set the current working directory to the new directory found by pathToDirORFile
       */
      fs.setCurDir((Directory) newDir);
    }
    // Otherwise if newDir is a File, which is not valid
    else if (newDir instanceof File) {
      // Call invalid syntax exception
      ExceptionHandler.invalidSyntaxException("cd");
    }
    // Otherwise, do nothing, the exception text has already been handled
  }

  /**
   * This method returns the documentation corresponding to the cd command, explaining the necessary
   * syntax of inputs and what the command does.
   * 
   * @return A String representation of command documentation for cd
   */
  @Override
  public String printDocumentation() {
    // TODO Auto-generated method stub
    return (documentation);
  }
}
