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

import java.util.HashMap;
import driver.Directory;
import java.util.Set;
/**
 * The Abstract class Command handles where commands get sent to in a general
 * pattern.
 * @author Muizz Ahmed
 * @version 1.0
 */
public abstract class MockCommand {
  protected MockFileSystem<Directory> fs = MockFileSystem.getInstance();
  /**
   * commands HashMap stores the command name for each object along with their
   * Reference to their specific class.
   */
  private static HashMap<String, Object> commands = new HashMap<>();
  
  /**
   * Sets the associated command name to its Object instance in the HashMap.
   * Acts as a setter.
   * @param command : This is the string representation of the command's name.
   * @param instance : This is the Object representation of the command.
   */
  public static void addCommand(String command, Object instance) {
    commands.put(command, instance);
  }
  
  public static Set<String> getCommands() {
    return commands.keySet();
  }
  
  /**
   * Acts as a getter to get the specified *command* from the HashMap to
   * return its appropriate Object reference
   * @param command: The string representation of the given command
   * @return returns the command's instance variable that is associated to it
   *         in the HashMap
   */
  public static Object execute(String command) {
    return (commands.get(command));
  }

  public abstract String runCommand(String input);
  
  public abstract String printDocumentation();

}
