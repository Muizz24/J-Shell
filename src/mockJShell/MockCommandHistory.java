package mockJShell;
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

import java.util.ArrayList;

/**
 * Prints out all the input the user has been giving, including typos and lines with wrong syntax
 * 
 * @author ahmed323
 *
 */
public class MockCommandHistory extends MockCommand {
  // Stack stores the previously written commands
  private static String history = "";
  /**
   * Stores the string representations of the user input
   */
  private static ArrayList<String> commandHistory = new ArrayList<String>();
  /**
   * Holds documentation for the command history
   */
  private String documentation =
      ("\n Description:\n--------------------------------------------\n\tShows"
          + " all or a given amount of recent commands the user has inputted."
          + "\n Syntax:\n--------------------------------------------\n\t/#: "
          + "history [INTEGER]\n\tWhere INTEGER is an optional parameter that "
          + "shows INTEGER recent input history.\n\tIf no INTEGER is given, hi"
          + "story will show all user input.INTEGER accepts integers only.\n "
          + "Examples:\n--------------------------------------------\n\t/#: "
          + "history\n\t1.mkdir photos\n\t2.mkdir songs\n\t3.man mkdir\n\t"
          + "4.history\n\n\t/#: history 3\n\t2.mkdir songs\n\t3.man mkdir\n\t"
          + "4.history 3\n\n Possible Exceptions:\n--------------------------"
          + "------------------\n\tinvalidSyntaxException");

  
  public static ArrayList<String> getCommandHistory(){
    return commandHistory;
  }
  /**
   * Prints all user input history when no truncating number is given. Acts as the default method
   * for when there is no input other than command
   */
  private void viewHistory() {
    // Shows entire history if no default is given
    for (int i = 0; i < commandHistory.size(); i++) {
      MockCommandHistory.history += String.valueOf(i + 1) + ".";
      MockCommandHistory.history += commandHistory.get(i) + "\n";
    }
  }

  /**
   * Takes in the new index in which will iterate only the most recent items in the history.
   * 
   * @param truncateBy : The amount of history the user wants to see
   */
  private void viewHistory(int truncateBy) {
    // Create new index
    int index = Math.max(0, commandHistory.size() - truncateBy);
    // Shows history at given index
    for (int i = index; i < commandHistory.size(); i++) {
      MockCommandHistory.history += String.valueOf(i + 1) + ".";
      MockCommandHistory.history += commandHistory.get(i) + "\n";
    }
  }

  /**
   * Acts as a setter for the private instance variable command_history
   * 
   * @param input : String representation of what the user wrote
   */
  public static void addHistory(String input) {
    // Adds a user input into history
    commandHistory.add(input);
  }
  
  /** 
   * Empties the commandHistory ArrayList
   */
  public static void clearHistory() {
    commandHistory.clear();
  }

  /**
   * Acts as a getter for the private instance variable command_history Returns an user's input at a
   * specific time.
   * 
   * @param input : the index of the command needed to grab
   */
  public static String getHistory(Integer index) {
    // Return user inputed history at specified index of command_history
    return commandHistory.get(index);
  }

  /**
   * Runs command for whenever the user inputs history and appropriately Assigns the next method to
   * match user's interest.
   * 
   * @param input: Takes in the input right after the command name
   */
  @Override
  public String runCommand(String input) {
    // Check to see if input syntax is correct
    MockCommandHistory.history = "";
    if (input == "") {
      this.viewHistory();
    } else {
      // Check to see if the input contains anything other than integers
      input = input.trim();
      if (input.replaceAll("[^0-9]", "").length() < input.length()) {
        /*
         * return exception if input is anything thats not an integer
         */
        MockExceptionHandler.invalidSyntaxException("history");
        return "";
      } else {
        // Show a truncated version of history
        this.viewHistory(Integer.parseInt(input));
      }
    }
    return MockCommandHistory.history;
  }

  @Override
  public String printDocumentation() {
    // TODO Auto-generated method stub
    return (documentation);
  }
}
