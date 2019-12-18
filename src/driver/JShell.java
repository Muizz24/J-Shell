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
 * Acts as the driver for the entire program to run.
 * 
 * @author Millan Singh Khurana, Muizz Ahmed
 *
 */
public class JShell {
  /**
   * Sets up all of the necessary elements of the Command, FileSystem, and Parser so that the
   * program can initiate.
   * 
   * @param args
   */
  public JShell() {
    /*
     * Adds all of the applicable commands to the Command class hashtable so that the user's inputs
     * can be properly cast into Commands and run.
     */
    Command.addCommand("mkdir", new DirectoryMake());
    Command.addCommand("ls", new DirectoryList());
    Command.addCommand("cd", new DirectoryChange());
    Command.addCommand("history", new CommandHistory());
    Command.addCommand("pushd", new DirectoryPush());
    Command.addCommand("popd", new DirectoryPop());
    Command.addCommand("cat", new ViewFile());
    Command.addCommand("pwd", new DirectoryPath());
    Command.addCommand("man", new Man());
    Command.addCommand("echo", new EchoFile());
    Command.addCommand("exit", new Exit());
    Command.addCommand("tree", new TreeOfFS());
    Command.addCommand("find", new CommandFind());
    Command.addCommand("save", new Saver());
    Command.addCommand("get", new Getter());
    Command.addCommand("load", new Loader());
    Command.addCommand("cp", new Copy());
    Command.addCommand("mv", new Mover());
  }

  public static void main(String[] args) {
    // Initialize JShell system
    JShell startUp = new JShell();
    /*
     * Creates a Parser that will get input from the user and sort it into readable lists for the
     * Validator to use.
     */
    Parser parser = new Parser();
  }
}
