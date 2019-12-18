package driver;
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

/**
 * Class responsible for the command 'tree' which takes in no input other than the command name
 * itself and prints a tree of the given fs.
 * 
 * @author ahmed323
 *
 */
public class TreeOfFS extends Command {
  /**
   * treeStr concatenates the directories of the fs.
   */
  private static String treeStr;
  /**
   * String that holds the documentation details for the command find
   */
  private static final String documentation =
      (" Description:" + "\n--------------------------------------------\n\t"
          + "Prints the entire fs in the format of a tree.\n Syntax:"
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
   * @param numberOfRepeats: the level of the directory.
   * @return a tab replicated by directory level amount of times.
   */
  private String repeatTab(Integer numberOfRepeats) {
    // Instantiate variable;
    String tabs = "";
    // Add an indent numberOfRepeats times onto the instantiated variable.
    for (int numOfTabs = 0; numOfTabs < numberOfRepeats; numOfTabs++) {
      tabs += "\t";
    }
    // return the level of indent.
    return tabs;
  }

  /**
   * Grabs the root of the fs and recursively lists out all directories with their
   * corresponding indentation.
   * 
   * @param dir: the current directory of the traversal.
   * @param indentLvl: the level of indent analogous to level of directory.
   */
  private void getTree(Directory dir, Integer indentLvl) {
    // Get the indent level first
    String indentBy = repeatTab(indentLvl);
    /*
     * Put current directory name inside tree and recursively do the same for current directory's
     * sub directories.
     */
    TreeOfFS.treeStr += indentBy + dir.getName() + "\n";
    for (Directory subDir : dir.getSubDirectories()) {
      this.getTree(subDir, indentLvl + 1);
    }
  }

  /**
   * Caller for the command tree that prints out the entire fs.
   * 
   * @param input: the input followed after tree has been written.
   */
  @Override
  public String runCommand(String input) {
    // Ensure if input follows proper syntax before getting tree.
    if (input.trim().equals("")) {
      // Recursively get tree of fs and update it to treeStr variable.
      TreeOfFS.treeStr = "";
      this.getTree(fs.getRoot(), 0);
    } else {
      // Assume user inputed wrong syntax, call an exception and return nothing.
      ExceptionHandler.invalidSyntaxException("tree");
      return "";
    }
    // return the fs's toString equivalent.
    return TreeOfFS.treeStr;
  }

  @Override
  public String printDocumentation() {
    // TODO Auto-generated method stub
    return TreeOfFS.documentation;
  }

}
