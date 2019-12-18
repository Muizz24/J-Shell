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
 * A command in JShell that exits the entire program and stops requesting input from the user once
 * it is called.
 * 
 * @author Millan Singh Khurana
 *
 */
public class Exit extends Command {
  /**
   * Stores the documentation for the given command Exit.
   */
  private String documentation = ("\n Description: \n\n Quits the program."
      + "\n\n Examples:\n\n exit \n\n Possible Exceptions:\n\n No possible"
      + " exceptions.\n");

  /**
   * A method that is called whenever the user inputs "exit" and returns true to confirm and allow
   * the user to exit the program.
   * 
   * @return true: Returns true every time it is called.
   */
  public boolean exitCalled() {
    return true;
  }

  /**
   * Runs the command of exit from JShell.
   * 
   * @param input: Takes in the input String that represents the Command the user is trying to exit.
   */
  @Override
  public String runCommand(String input) {
    if (input.trim().equals("")) {
      System.exit(0);
    } else {
      ExceptionHandler.invalidSyntaxException("exit");
    }
    return "";
  }

  /**
   * Prints the documentation String for this Command, that holds all of the necessary information
   * to be printed when man exit is called.
   */
  @Override
  public String printDocumentation() {
    return (documentation);
  }
}
