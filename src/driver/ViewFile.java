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

/**
 * This class represents the cat command, which prints the contents of a file, for the user too view
 * 
 * @author Anthony Alaimo
 */
public class ViewFile extends Command {
  private String documentation = ("\n Description: \n\n Prints the current"
      + " working directory (including the whole path).\n\n "
      + "Examples:\n\n pwd \n\n Possible Exceptions: \n\n "
      + "invalidPathException, invalidFileName ");

  /**
   * This method takes a path to and including a file name or just a file name itself and prints out
   * the contents of the file for the user to view
   * 
   * @param path, a string representing a path to and including a file name or just a file name
   *        itself
   * @return a String containing the name of the file and its contents
   */
  private String cat(String path) {
    Object file = fs.pathToDirorFile(path);
    String result = "";
    /*
     * print the text of the given file if it exist, otherwise its an invalid file
     */
    if (file instanceof File) {
      /*
       * each file should be printed with 3 blank lines after, for appearance
       */
      result = (((File) file).getName()) + ": " + (((File) file).getText()) +
          "\n\n\n";
    } else if (file instanceof Directory) {
      ExceptionHandler.invalidFileName(((Directory) file).getName());
    }
    return result;
  }

  /**
   * This method calls the cat method with just the string input, not the whole cat command
   * 
   * @param path, a string representing the input of a user, trying to use the cat command.
   */
  @Override
  public String runCommand(String input) {
    /*
     * In the case that there are multiple files, we separate them and view them one at a time
     */
    input = input.trim().replaceAll(" +", " ");
    String result = "";
    String[] paths = input.split(" ");
    if (paths[0].equals("")) {
      ExceptionHandler.invalidSyntaxException("cat");
      return "";
    } else {
      for (String path : paths) {
        result = result + this.cat(path);
      }
    }
    return (result.substring(0, Math.max(0, result.length() - 3)));
  }

  @Override
  public String printDocumentation() {
    // TODO Auto-generated method stub
    return (documentation);

  }
}
