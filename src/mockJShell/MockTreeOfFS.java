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

import driver.Directory;

/**
 * Class responsible for the command 'tree' which takes in no input other than the command name
 * itself and prints a tree of the given FileSystem.
 * 
 * @author ahmed323
 *
 */
public class MockTreeOfFS extends MockCommand {
  /**
   * tree_str concatenates the directories of the FileSystem.
   */
  private static String treeStr;
  /**
   * String that holds the documentation details for the command find
   */
  private static final String documentation =
      (" Description:" + "\n--------------------------------------------\n\t"
          + "Prints the entire FileSystem in the format of a tree.\n Syntax:"
          + "\n--------------------------------------------\n\t/#: tree\n\tTre"
          + "e command accepts no input parameters.\n\n\t===REDIRECTION SYNTAX"
          + "===\n\t\t/#: tree > OUTFILE\n\t\tWhere OUTFILE is the name of"
          + " the file.\n\t\t> OVERWRITES text in OUTFILE and >> APPENDS text"
          + " into OUTFILE.\n Example:\n--------------------------------------"
          + "------\n\t/#: tree\n\t/\n\t\tUsers\n\t\t\tBob\n\t\tSystem\n\t\t"
          + "\n\tPrograms\n Possible Exceptions:\n---------------------------"
          + "-----------------\n\tinvalidSyntaxException");

  /**
   * Helper method that ensures each directory gets the right amount of indentation depending on the
   * level the directory resides in.
   * 
   * @param number_of_repeats: the level of the directory.
   * @return a tab replicated by directory level amount of times.
   */
  private String repeatTab(Integer numberOfRepeats) {
    // Instantiate variable;
    String tabs = "";
    // Add an indent number_of_repeats times onto the instantiated variable.
    for (int numOfTabs = 0; numOfTabs < numberOfRepeats; numOfTabs++) {
      tabs += "\t";
    }
    // return the level of indent.
    return tabs;
  }

  /**
   * Grabs the root of the FileSystem and recursively lists out all directories with their
   * corresponding indentation.
   * 
   * @param dir: the current directory of the traversal.
   * @param Indent_lvl: the level of indent analogous to level of directory.
   */
  private void getTree(Directory dir, Integer IndentLvl) {
    // Get the indent level first
    String indentBy = repeatTab(IndentLvl);
    /*
     * Put current directory name inside tree and recursively do the same for current directory's
     * sub directories.
     */
    MockTreeOfFS.treeStr += indentBy + dir.getName() + "\n";
    for (Directory subDir : dir.getSubDirectories()) {
      this.getTree(subDir, IndentLvl + 1);
    }
  }

  /**
   * Caller for the command tree that prints out the entire FileSystem.
   * 
   * @param input: the input followed after tree has been written.
   */
  @Override
  public String runCommand(String input) {
    // Ensure if input follows proper syntax before getting tree.
    if (input.trim().equals("")) {
      // Recursively get tree of FileSystem and update it to tree_str variable.
      MockTreeOfFS.treeStr = "";
      this.getTree(fs.getRoot(), 0);
    } else {
      // Assume user inputed wrong syntax, call an exception and return nothing.
      MockExceptionHandler.invalidSyntaxException("tree");
      return "";
    }
    // return the FileSystem's toString equivalent.
    return MockTreeOfFS.treeStr;
  }

  @Override
  public String printDocumentation() {
    // TODO Auto-generated method stub
    return MockTreeOfFS.documentation;
  }

}
