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

import java.util.Scanner;

/**
 * Takes input from the user, and then parses it and sends it to Validator. Keeps requesting input
 * from the user until "exit" is inputed.
 * 
 * @author Millan Singh Khurana
 *
 */
public class Parser {

  /**
   * A boolean that represents whether the user has called "exit" or not.
   */
  private boolean exitCheck;

  /**
   * A variable that stores a reference to the validate constructor
   */
  private Validator validateInput;

  /**
   * Uses a scanner to take input from the user, and then parse it into a more easily readable list
   * and sends it to Validator. Keeps requesting input from the user until "exit" is inputed.
   */
  public Parser() {
    boolean exitCheck = false;
    // Creates a new scanner instance that is used to take in user input.
    Scanner scanner = new Scanner(System.in);
    String input = "";
    // Runs a loop while the user hasn't inputed "exit"
    while (exitCheck == false) {
      // Prints out the formatting for the shell before the user's input.
      System.out.print("/#: ");
      /*
       * Sets the input string to the next line of input taken in by the scanner.
       */
      input = scanner.nextLine();
      // Gets rid of any extraneous spaces on the back of the input
      input = input.replaceAll("\\s+$", "");
      /*
       * If the user has inputed exit it creates a new Exit class and calls exitCalled(). exitCheck
       * is then set to the result of exitCalled() which is always true. This means that exitCheck
       * becomes true and results in stopping the while loop on the next run.
       */
      if (input.equals("exit")) {
        Exit exit = new Exit();
        exitCheck = exit.exitCalled();
      } else if (Validator.validateLeadingWhiteSpace(input)) {
        validateInput = new Validator(input);
      }
    }
    scanner.close();
  }
}
