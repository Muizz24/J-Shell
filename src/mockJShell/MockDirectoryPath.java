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


/**
 * Command in Jshell that prints current working dir in the FileSystem
 * 
 * @author Muizz Uddin Ahmed
 * @version 1.0
 */
public class MockDirectoryPath extends MockCommand {
  /**
   * Stores the documentation for the given command pwd
   */
  private String documentation = ("\n Description: \n\n Prints the current"
      + " working directory (including the whole path).\n\n Examples:\n\n pwd"
      + "\n\n Possible Exceptions: \n\n invalidSyntaxException \n");
  private static String curDirPath= "";

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
      // call helper method printCurrentPath to print the path of cur_dir
      MockDirectoryPath.curDirPath = this.printCurrentPath();
    } else {
      // Call an exception if pwd has more input after the command itself
      MockExceptionHandler.invalidSyntaxException("pwd");
      return "";
    }
    return MockDirectoryPath.curDirPath;
  }

  @Override
  public String printDocumentation() {
    // TODO Auto-generated method stub
    return(documentation);
  }

}
