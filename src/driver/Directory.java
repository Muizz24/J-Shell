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

import java.util.ArrayList;
import java.util.List;
import driver.File;

/**
 * Handles everything to do with its Directory
 * 
 * @author Muizz Ahmed
 *
 */
public class Directory {
  /**
   * directories stores all the sub directories that are contained in this dir.
   */
  private List<Directory> directories;
  /**
   * files stores all the files inside this directory.
   */
  private List<File> files;
  /**
   * parent is a special directory where this dir is the sub dir of another dir.
   */
  private Directory parent;
  /**
   * name holds the string representation of this directory.
   */
  private String name;

  /**
   * Create a working directory
   * 
   * @param name: String representation of the new directory.
   */
  public Directory(String name) {
    this.files = new ArrayList<>();
    this.directories = new ArrayList<>();
    this.parent = null;
    this.name = name;
  }

  /**
   * Setter for the private instance variable parent.
   * 
   * @param parent the directory that is this directory is a sub directory to.
   */
  public void setParent(Directory parent) {
    // Sets the Parent directory to the current directory
    this.parent = parent;
  }

  /**
   * Getter for the private instance variable parent.
   * 
   * @return The parent of this directory
   */
  public Directory getParent() {
    // Returns reference to the parent Directory of current Directory
    return this.parent;
  }

  /**
   * Setter for the private instance variable files
   * 
   * @param file: An object that is going to be added into the files list.
   */
  public void addFile(File file) {
    // Append file to list of files in this directory
    this.files.add(file);
  }

  /**
   * Setter for the private instance variable directories.
   * 
   * @param dir: An object that is going to be added into the directories list.
   */
  public void addDirectory(Directory dir) {
    // Append directory to this directory
    this.directories.add(dir);
    dir.setParent(this);
  }

  /**
   * A getter method for the private instance method directories. Gets one directory depending on
   * which string representation the user gives. Returns null if no such string representation
   * exists.
   * 
   * @param dirName: String representation of the directory we are looking for.
   * @return Returns the found directory or null if not found in the list
   */
  public Directory getSubDirectory(String dirName) {
    // Traverse the Directory list to find the appropriate directory
    for (Directory directory : this.directories) {
      if (directory.getName().equals(dirName)) {
        return directory;
      }
    }
    // Return null object if the directory does not exist
    return null;
  }

  /**
   * Getter for the private instance variable files Gets a specific file from the list depending on
   * the string representation.
   * 
   * @param fileName: The string representation of the file the user wants to get.
   * @return Returns the found file or null if file doesn't exist in files list.
   */
  public File getFile(String fileName) {
    // Traverse the files list to find the appropriate file in directory
    for (File file : this.files) {
      if (file.getName().equals(fileName)) {
        return file;
      }
    }
    // Return null object if file does not exist
    return null;
  }

  /**
   * Another getter method for the private instance variable files.
   * 
   * @return Entire private instance variable files for this directory.
   */
  public List<File> getAllFiles() {
    return this.files;
  }

  /**
   * Another getter method for the private instance variable directories.
   * 
   * @return Entire private instance variable directory for this directory.
   */
  public List<Directory> getSubDirectories() {
    return this.directories;
  }

  /**
   * getter for the private instance variable name
   * 
   * @return the string representation of this directory
   */
  public String getName() {
    // Gets the name of the referenced Directory
    return this.name;
  }

  /**
   * Unlink's the given directory from it's parent. with no connection to the FileSystem the
   * directory pretty much does not exist anymore in the FileSystem.
   * 
   */
  public void deleteDirectory() {
    // Get the parent directory and remove this directory from its sub dirs.
    Directory parentDir = this.getParent();
    parentDir.getSubDirectories().remove(this);
    // Unlink this directory from its parent directory.
    this.setParent(null);
  }

  /**
   * Removes the given fileName from this directory essentially unlinking it from the FileSystem
   * 
   * @param fileName: String representation of the file's name.
   */
  public void deleteFile(String fileName) {
    // Get the file residing in this directory and remove it from the files list
    File subFile = this.getFile(fileName);
    this.getAllFiles().remove(subFile);
  }

}
