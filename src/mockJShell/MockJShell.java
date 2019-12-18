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
package mockJShell;

import java.util.Scanner;
import driver.Directory;
import driver.Parser;
/**
 * Acts as the driver for the entire program to run.
 * @author Millan Singh Khurana, Muizz Ahmed
 *
 */
public class MockJShell {
  /**
   * Sets up all of the necessary elements of the Command, FileSystem, and
   * Parser so that the program can initiate.
   * @param args
   */
  public MockJShell() {
    /*
     * Adds all of the applicable commands to the Command class hashtable so
     * that the user's inputs can be properly cast into Commands and run.
     */
    MockCommand.addCommand("mkdir", new MockDirectoryMake());
    MockCommand.addCommand("history", new MockCommandHistory());
    MockCommand.addCommand("cat", new MockViewFile());
    MockCommand.addCommand("echo", new MockEchoFile());
    MockCommand.addCommand("find", new MockCommandFind());
    MockCommand.addCommand("ls", new MockDirectoryList());
    MockCommand.addCommand("cd", new MockDirectoryChange());
    MockCommand.addCommand("pwd", new MockDirectoryPath());
    MockCommand.addCommand("tree", new MockTreeOfFS());
    MockCommand.addCommand("man", new MockMan());
    MockCommand.addCommand("cp", new MockCopy());
    MockCommand.addCommand("mv", new MockMover());
    MockCommand.addCommand("popd", new MockDirectoryPop());
    MockCommand.addCommand("pushd", new MockDirectoryPush());
    MockCommand.addCommand("save", new MockSaver());
    MockCommand.addCommand("get", new MockGetter());
    MockCommand.addCommand("load", new MockLoader());
    
    
    MockFileSystem<Directory> fs = new MockFileSystem<>();
  }
  
  public static void main(String[] args) {
    // Initialize JShell system
    MockJShell startUp = new MockJShell();
    /*
     *  Creates a Parser that will get input from the user and sort it into
     *  readable lists for the Validator to use.
     */
    Parser parser = new Parser();
  }
}