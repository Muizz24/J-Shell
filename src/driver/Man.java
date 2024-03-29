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
 * A Command in JShell that prints the documentation, examples, and possible exceptions for the
 * given command.
 * 
 * @author Millan Singh Khurana
 *
 */
public class Man extends Command {
  /**
   * Stores the documentation for the given command Man.
   */
  private String documentation = "\n Description: \n\n Prints the"
      + " documentation, examples, and possible exceptions for the given "
      + "command.\n\n Examples:\n\n man history \n\n Description: "
      + "\n\n Shows all or a desired amount of recent commands the user has "
      + "inputted.\n\n Examples:\n\n history\n\n 1.mkdir photos\n 2.mkdir "
      + "songs\n 3.man mkdir\n 4.history\n\n history 3\n 2.mkdir songs\n "
      + "3.man mkdir\n 4.history 3\n\n Possible Exceptions:\n\n "
      + "invalidSyntaxException \n\n Possible Exceptions:\n\n invalidCommand"
      + "\n";

  /**
   * Runs the command of man from JShell by creating an instance of the given Command (determined by
   * the user's input) and calling that Command's print_documentation method.
   * 
   * @param input: Takes in the input String that represents the Command the user is trying to print
   *        the documentation for.
   */
  @Override
  public String runCommand(String input) {
    // A boolean used to track if the user's input represents a usable Command.
    boolean commandCheck = false;
    String output = "";
    /*
     * Loops through the commandList to check if the user's input correlates to one of the usable
     * Commands.
     */
    for (String name : Command.getCommands()) {
      if (input.equals(name)) {
        /*
         * Casts the input String (that represents a usable command) into an instance of that actual
         * Command, and then calls the printDocumentation method to print all of the necessary
         * information.
         */
        commandCheck = true;
        output = ((Command) Command.execute(name)).printDocumentation();
      }
    }
    /*
     * Calls an invalidCommand exception if the user's input doesn't represent a usable Command.
     */
    if (!commandCheck) {
      ExceptionHandler.invalidCommand(input);
      return "";
    }
    return output;
  }

  /**
   * Prints the documentation String for this Command, that holds all of the necessary information
   * to be printed when man man is called.
   */
  @Override
  public String printDocumentation() {
    return (documentation);
  }

}
