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
import driver.File;
import driver.Validator;

/*
 * This is the corresponding class for the command cp, which copies the
 * contents from a given OLDPATH into a given NEWPATH
 * 
 * @author Nicholas Michael Gibson Elliott
 */
public class MockMover extends MockCommand {

	/**
	   * Stores the documentation for the given command mkdir
	   */
	private String documentation = 
	    (" Description:\n--------------------------------------------\n\tLike"
			+ " cp, but takes File or Directory from OLDPATH and moves it\n\t"
	    	+ "to NEWPATH. If OLDPATH is a directory, move"
			+ "the directory into\n\tNEWPATH\n Syntax:\n--------------"
	    	+ "------------------------------\n\t/#: mv OLDPATH NEWPATH\n\t"
			+ "Where OLDPATH & NEWPATH are paths that leads to a File or\n\tDi"
	    	+ "rectory where the content gets moved.\n\n\t===REDIRECTION===\n"
			+ "\t/#: mv OLDPATH NEWPATH [> OUTFILE]\n\tWhere mv output text ov"
	    	+ "erwrites OUTFILE's text.\n\t/#: mv OLDPATH NEWPATH [>> OUTFILE]"
			+ "\n\tWhere mv output text appends to OUTFILE.\n Examples:\n----"
	    	+ "----------------------------------------\n\t/#: mv /Users /Pro"
			+ "grams\n\t/#: mv /File.txt /Documents\n\t/#: mv /File1.txt /File"
	    	+ "2.txt\n Possible Exceptions:\n---------------------------------"
			+ "-----------\n\t invalidSyntaxException, invalidPathException, p"
	    	+ "arentPathException");

	  /*
	   * This method will, if given two valid paths as inputs, call a helper
	   * function depending on the types of the paths given, which will in
	   * turn complete the requirements for command mv
	   * 
	   * @param input The input entered by the user to be used for mv
	   * @return Blank String
	   */
	  public String runCommand(String input) {
	    // Remove extra spaces from input
	    input = input.trim().replaceAll(" +", " ");
	    // Split the input into OLDPATH and NEWPATH at the space
	    String[] inputs = input.split(" ");
	    // If there are more than two paths
	 	if (inputs.length != 2) {
	 		// Call an invalid syntax exception
	 		MockExceptionHandler.invalidSyntaxException("mv");
	 		// Return an empty String
	 		return "";
	 	}
	    // Find the desired Directory/File for OLDPATH
	    Object oldpath = fs.pathToDirorFile(inputs[0]);
	    // Find the desired Directory/File for NEWPATH
	    Object newpath = fs.pathToDirorFile(inputs[1]);
	    // If OLDPATH is a File and NEWPATH is a Directory
	    if (oldpath instanceof File & newpath instanceof Directory) {
	      /*
	       * Call the findDirForFile method to find the Directory that OLDPATH
	       * is located inside of
	       */
	      Directory oldDir = findDirForFile(inputs[0]);
	      /*
	       * Call the move(File, Directory) method with OLDPATH, NEWPATH and
	       * the Directory that OLDPATH is located in as its parameters
	       */
	      move((File) oldpath, (Directory) newpath, oldDir);
	    }
	    // Otherwise, if OLDPATH is a Directory and NEWPATH is a Directory
	    else if (oldpath instanceof Directory & newpath instanceof Directory) {
	      /*
	       * Call the move(Directory, Directory) method with OLDPATH and
	       * NEWPATH as its parameters
	       */
	      move((Directory) oldpath, (Directory) newpath);
	    }
	    // Otherwise, if OLDPATH is File and NEWPATH is a Directory
	    else if (oldpath instanceof File & newpath instanceof File) {
	      /*
	       * Call the findDirForFile method to find the Directory that OLDPATH
	       * is located inside of
	       */
	      Directory oldDir = findDirForFile(inputs[0]);
	      /*
	       * Call the move(File, File) method with OLDPATH, NEWPATH and the
	       * Directory that OLDPATH is located in as its parameters
	       */
	      move((File) oldpath, (File) newpath, oldDir);
	    }
	    /*
	     * Otherwise, meaning OLDPATH is a Directory and NEWPATH is a File,
	     * which is not valid
	     */
	    else if (oldpath instanceof Directory & newpath instanceof File) {
	      MockExceptionHandler.invalidSyntaxException("mv");
	    }
	    /*
	     * Otherwise, do nothing, the exception has already been handled
	     * 
	     */
	    return "";
	  }

	  /*
	   * This method will move the Directory OLDPATH into NEWPATH, replacing a
	   * Directory with the same name as OLDPATH in NEWPATH if one exists, as
	   * long as OLDPATH is not a parent of NEWPATH
	   * 
	   * @param oldpath The Directory which corresponds to the given OLDPATH
	   * @param newpath The Directory which corresponds to the given NEWPATH
	   */
	  public void move(Directory oldpath, Directory newpath) {
		// Validate that OLDPATH is not a parent of NEWPATH
		if (MockValidator.validateParent(oldpath, newpath)) {
			// Return from this method
			return;
		}
	    // Create a new Directory and set it to OLDPATH
	    Directory dir = oldpath;
	    // Delete OLDPATH
	    oldpath.deleteDirectory();
	    // Find the Directory in NEWPATH with the same name as OLDPATH
	    Directory oldInNew = newpath.getSubDirectory(dir.getName());
	    // If it does not exist
	    if (oldInNew == null) {
	      // Add dir as subdirectory of NEWPATH
	      newpath.addDirectory(dir);
	    }
	    // Otherwise, meaning it must be overwritten
	    else {
	      // Remove the Directory in NEWPATH with the same name as OLDPATH
	      oldInNew.deleteDirectory();
	      // Add dir as subdirectory of NEWPATH
	      newpath.addDirectory(dir);
	    }
	  }

	  /*
	   * This method moves the File OLDPATH into Directory NEWPATH, replacing a
	   * File with the same name as OLDPATH in NEWPATH if one exists
	   * 
	   * @param oldpath The File which corresponds to the given OLDPATH
	   * @param newpath The Directory which corresponds to the given NEWPATH
	   */
	  public void move(File oldpath, Directory newpath, Directory oldDir) {
	    // Create a new File with the name and text of OLDPATH
	    File newFile = new File(oldpath.getName(), oldpath.getText());
	    // Delete OLDPATH
	    oldDir.deleteFile(oldpath.getName());
	    // Find the File in NEWPATH with the same name as OLDPATH
	 	File oldInNew = newpath.getFile(newFile.getName());
	 	// If it does not exist
	 	if (oldInNew == null) {
	 		// Set the newfile to be inside newpath
	 		newpath.addFile(newFile);
	 	}
	 	// Otherwise, meaning it must be overwritten
	 	else {
	 		// Delete the file with the same name in NEWPATH
	 		newpath.deleteFile(newFile.getName());
	 		// Set the newfile to be inside newpath
	 		newpath.addFile(newFile);
	 	}
	  }

	  /*
	   * This method will overwrite the text of NEWPATH with that of OLDPATH,
	   * then deletes OLDPATH
	   * 
	   * @param oldpath The Directory which corresponds to the given OLDPATH
	   * @param newpath The Directory which corresponds to the given NEWPATH
	   */
	  public void move(File oldpath, File newpath, Directory oldDir) {
	    // Append oldpath's text onto newpath
	    newpath.setText(oldpath.getText());
	    // Delete OLDPATH in it original location
	    oldDir.deleteFile(oldpath.getName());
	  }

	  /*
	   * This method finds the direct parent Directory (one step up) of a given
	   * File using its path String representation. Necessary for deleting a
	   * File
	   * 
	   * @param input A String representation of the path of a File
	   * @return The Directory with the File represented by input as one of its
	   * Files
	   */
	  public Directory findDirForFile(String input) {
	    // Create a new Directory
	    Directory dir;
	    // If the String input does not contain a /
	    if (input.contains("/") == false) {
	      // The Directory that the File is inside of is the cwd
	      dir = fs.getCurDir();
	    }
	    // Otherwise
	    else {
	      // Store the index of the last / in a new int
	      int lastSlash = input.lastIndexOf("/");
	      // Find the String up to that last /
	      String newInput = input.substring(0, lastSlash);
	      // If the length of the newInput is 0, meaning its blank
	      if (newInput.length() == 0) {
	        // The Directory that the File is inside is the root
	        dir = fs.getRoot();
	      }
	      // Otherwise
	      else {
	        /*
	         * Call the pathToDirorFile method in fs, with the result
	         * casted to type Directory in order to find the Directory that the
	         * File is located inside
	         */
	        dir = (Directory) fs.pathToDirorFile(newInput);
	      }
	    }
	    // Return the found Directory
	    return dir;
	  }

	  /*
	   * This method returns the documentation of mv, for use by man
	   * 
	   * @return A String representation of mv's documentation
	   */
	  @Override
	  public String printDocumentation() {
	    return (documentation);
	  }
}
