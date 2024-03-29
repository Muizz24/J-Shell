package driver;

import java.util.ArrayList;
import java.util.List;

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
 * Acts as a network for directories to coincide together.
 * 
 * @author Muizz Ahmed
 *
 */
public class FileSystem<T extends Directory> {
  private static FileSystem<Directory> fs;
  /**
   * Directory that acts as the starting point for every other directory. Works like every other
   * directory as well and never has a parent.
   */
  private T root = (T) new Directory("/");
  /**
   * Stores the directory the user is currently in
   */
  private T curDir = root;

  public static FileSystem getInstance() {
    if (fs == null) {
      fs = new FileSystem<>();
    }
    return fs;
  }

  /**
   * Acts as the getter method for the private instance variable root
   * 
   * @return the root directory with unique string representation of "/".
   */
  public Directory getRoot() {
    return FileSystem.getInstance().root;
  }

  /**
   * Getter method for the private instance variable cur_dir
   * 
   * @return returns the pointer reference to the directory the user is in.
   */
  public Directory getCurDir() {
    return FileSystem.getInstance().curDir;
  }

  /**
   * Changes current directory only if directory exists in the file system. In order for a directory
   * to exist in the file system it must be able to be traced down to the root directory.
   * 
   * @param dir: directory that will soon become the new current directory
   */
  public void setCurDir(Directory dir) {
    // Initialize variables for the while loop
    boolean isDir = false;
    Directory tempDir = dir;
    // if the dir is the root, skip the loop. Root will always be in FileSystem
    if (dir == fs.getRoot()) {
      isDir = true;
      fs.curDir = fs.getRoot();
    } else if (dir != null) {
      // Iteratively traverse the File system until root is reached.
      // Return an exception if this fails.
      while (tempDir.getParent() != null && isDir == false) {
        if (tempDir.getParent() == fs.getRoot()) {
          fs.curDir = dir;
          isDir = true;
        } else {
          tempDir = tempDir.getParent();
        }
      }
    }
  }

  /**
   * Finds directory or file at given path. Path can be either relative to the current directory or
   * may be full. if path is wrong or does not exist return none.
   * 
   * @param path: The string representation of the relative/full path.
   */
  public Object pathToDirorFile(String path) {
    // Split dir names to their respective index
    String[] dirNames = path.split("/");
    int pathGiven = 0;
    Directory tempDir = null;
    // Condition for path being a full path.
    if (path.indexOf("/") == 0) {
      tempDir = fs.getRoot();
      pathGiven = 1;
      // If no path is empty, assume current directory is the whole path
    } else if (path.length() == 0) {
      return fs.getCurDir();
      // If not a full path, Continue relative path process
    } else {
      tempDir = fs.getCurDir();
      pathGiven = 0;
    }
    // Compare each dir starting at root/cur_dir by matching dir names.
    for (int nameIndx = pathGiven; nameIndx < dirNames.length; nameIndx++) {
      // Get parent if .. is part of the path
      if (dirNames[nameIndx].equals("..")) {
        if (tempDir.getParent() != null) {
          tempDir = tempDir.getParent();
        }
      } else if (dirNames[nameIndx].equals(".")) {
      } else if (tempDir.getSubDirectory(dirNames[nameIndx]) != null) {
        // Finds if the next directory exists
        tempDir = tempDir.getSubDirectory(dirNames[nameIndx]);
        // If there is no next directory, assume the last name is a file
      } else if (tempDir.getFile(dirNames[nameIndx]) != null) {
        return tempDir.getFile(dirNames[nameIndx]);
        // If all else fails, path is invalid, return null and an exception
      } else {
        ExceptionHandler.invalidPathException(path);
        return null;
      }
    }
    // return the current directory if loop does not get instantiated.
    return tempDir;
  }

  /**
   * Recursively traces path of a given dir to the root. We assume the directory we are tracing in
   * this Method will always exist in the File System.
   * 
   * @param dir : the directory the user is trying to trace its path
   * @param path : an empty path that will change into the entire traced path after the loop
   *        completes.
   * @return the path local variable.
   */
  public String tracePath(Directory dir, String path) {
    // Base case, when directory has no parent like root
    if (dir.getParent() == null) {
      return "/";
    } else {
      // Recursive case, get the parent and add directory to the path string
      path = dir.getName() + "/";
      path = (fs.tracePath(dir.getParent(), path) + path);
    }
    // Return the final path once tracing is complete
    return path;
  }

}
