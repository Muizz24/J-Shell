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

import java.util.Arrays;
import java.util.List;
import driver.Directory;
import driver.File;


/**
 * Command in Jshell that prints a Dir's contents in the FileSystem
 * 
 * @author Muizz Uddin Ahmed
 * @version 1.0
 */
public class MockDirectoryList extends MockCommand {
  /**
   * Stores the documentation for the given command ls
   */
  private String documentation =
      (" Description:\n--------------------------------------------\n\tPrin"
          + "ts the contents of the given directory (or the current directory "
          + "if the user does not input one).\n\n Syntax:\n--------------"
          + "------------------------------\n\t/#: ls [-R] [PATH(S)]\n\t Where"
          + " -R acts as the signal to recursively list inner directories and"
          + " files \n\t and PATH(S) show content in one or more paths dependi"
          + "ng on\n\thow many the user desires to give.\n\n Examples:\n------"
          + "------------------------------------------\n\t/#: ls\n\t/: photos"
          + " songs games \n\t/#: ls /photos/2018\n\t 2018: beach cottage\n\n"
          + "Possible Exceptions:\n-------------------------------------------"
          + "-----\n invalidPathException \n");

  /**
   * Prints all directories and files inside the given directory.
   * 
   * @param dirOrFile: contains the directory thats going to print all of its dirs and its files.
   */
  private String ls(Object dirOrFile) {
    String contents = "";
    if (dirOrFile instanceof File) {
      contents = ((File) dirOrFile).getName();
    } else if (dirOrFile instanceof Directory) {
      // get the list of all directories and list of all files
      List<File> dirFiles = ((Directory) dirOrFile).getAllFiles();
      List<Directory> subDirs = ((Directory) dirOrFile).getSubDirectories();
      // Append each file and directory to the string dir_contents
      for (File file : dirFiles) {
        contents = contents + " " + file.getName();
      }
      for (Directory directory : subDirs) {
        contents = contents + " " + directory.getName();
      }
      // Print the string with all of the directory contents
      return (((Directory) dirOrFile).getName() + ":" + contents + "\n");
    }
    return contents;
  }
  
  /**
   * Recursively calls ls to go through all sub-directory contents inside the given directory
   * and to get the string of all Directories and Files inside the given Dir/File.
   * @param dirOrFile: The directory that contains sub-directories and Files to be traversed
   *                    and added to the string.
   * @return A string containing the directory and all of its sub-directory and files names
   *            
   */
  private String recLs(Object dirOrFile) {
    // Instantiate variable
    String list = "";
    // Base Case when traversal leads to a File (end of path in this case)
    if (dirOrFile instanceof File) {
      return ((File) dirOrFile).getName();
    } else {
      // get the list of all directories and list of all files
      Directory dir = ((Directory) dirOrFile);
      // Concatenate path of directory inside the parent directory
      list = list + fs.tracePath(dir, "") + ":";
      // Concatenate all file's names inside the current directory
      for (File file : dir.getAllFiles()) {
        list = list + " " + file.getName();
      }
      // Get the next directory and continue traversal (Recursive Step)
      for (Directory subDir : dir.getSubDirectories()) {
        list = list + "\n^" + recLs(subDir);
      }
    }
    // Return concatenated string of all directories and files names
    return list;
  }

  /**
   * Runs the command whenever ls is called from Jshell
   * 
   * @param Input: Takes in the input from after the command.
   */
  public String runCommand(String input) {
    // Remove whitespace from both front and back then split into diff paths
    String[] paths = input.trim().replaceAll(" +", " ").split(" ");
    String list = "";
    // check if input is empty or not
    if (input.length() == 0) {
      // Print current directory contents if no path is given
      list = this.ls(fs.getCurDir());
    } else if (paths[0].matches("-[Rr]") && paths.length == 1) {
      // Print current directory content's and its subDir contents recursively
      list = this.recLs(fs.getCurDir());
    } else {
      // Check if the Recursive flag was inputed before starting loop
      if (paths[0].matches("-[Rr]")) {
        /* Get new array of paths and start recursively traversing each path
         * along with their sub Directory's contents.
         */
        paths = Arrays.copyOfRange(paths, 1, paths.length);
        for (String path : paths) {
          Object dirOrFile = fs.pathToDirorFile(path);
          list = list + this.recLs(dirOrFile) + "\n";
        }
      } else {
        // loop through each path until all paths have been addressed
        for (String path : paths) {
          Object dirOrFile = fs.pathToDirorFile(path);
          list = list + this.ls(dirOrFile);
        }
      }
    }
    // Return output to Redirection System before printing to console
    return list;
  }

  @Override
  public String printDocumentation() {
    // return documentation of the command ls
    return (documentation);
  }
}
