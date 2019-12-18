package driver;
//**********************************************************
// Teammate1: Muizz Uddin Ahmed
//
// Teammate2: Nicholas Michael Gibson Elliott
//
// Teammate3: Anthony Alaimo
//
// Teammate4: Millan Singh Khurana
//
//
//Honor Code: I pledge that this program represents my own
//program code and that I have coded on my own. I received
//help from no one in designing and debugging my program.
//I have also read the plagiarism section in the course info
//sheet of CSC B07 and understand the consequences.
//*********************************************************

/*
 * This is the corresponding class for the command cp, which copies the
 * contents from a given OLDPATH into a given NEWPATH
 * 
 * @author Nicholas Michael Gibson Elliott
 */
public class Copy extends Command {
	/**
	 * Stores the documentation for the given command mkdir
	 */
	private String documentation =
	    (" Description:\n--------------------------------------------\n\tLike"
	        + " mv, but takes File or Directory from OLDPATH and copies\n\t"
	        + "to NEWPATH. If OLDPATH is a directory, recursively copy\n\t"
	        + "the directory's contents into NEWPATH\n Syntax:\n--------------"
	        + "------------------------------\n\t/#: cp OLDPATH NEWPATH\n\t"
	        + "Where OLDPATH & NEWPATH are paths that leads to a File or\n\tDi"
	        + "rectory where the content gets copied.\n\n\t===REDIRECTION===\n"
	        + "\t/#: cp OLDPATH NEWPATH [> OUTFILE]\n\tWhere cp output text ov"
	        + "erwrites OUTFILE's text.\n\t/#: cp OLDPATH NEWPATH [>> OUTFILE]"
	        + "\n\tWhere cp output text appends to OUTFILE.\n Examples:\n----"
	        + "----------------------------------------\n\t/#: cp /Users /Pro"
	        + "grams\n\t/#: cp /File.txt /Documents\n\t/#: cp /File1.txt /File"
	        + "2.txt\n Possible Exceptions:\n---------------------------------"
	        + "-----------\n\t invalidSyntaxException, invalidPathException, p"
	        + "arentPathException");

	/*
	 * This method will, if given two valid paths as inputs, call a helper
	 * function depending on the types of the paths given, which will in
	 * turn complete the requirements for command cp
	 * 
	 * @param input The input entered by the user to be used for cp
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
	 		ExceptionHandler.invalidSyntaxException("cp");
	 		// Return an empty String
	 		return "";
	 	}
		// Find the desired Directory/File for OLDPATH
		Object oldpath = fs.pathToDirorFile(inputs[0]);
		// Find the desired Directory/File for NEWPATH
		Object newpath = fs.pathToDirorFile(inputs[1]);
		// If OLDPATH is a File and NEWPATH is a Directory
		if (oldpath instanceof File & newpath instanceof Directory) {
			/* Call the copy(File, Directory) method with OLDPATH and
			 * NEWPATH as its parameters
			 */
			copy((File) oldpath, (Directory) newpath);
		}
		// Otherwise, if OLDPATH is a Directory and NEWPATH is a Directory
		else if (oldpath instanceof Directory & newpath instanceof Directory) {
			// Create directories by casting OLDPATH and NEWPATH to Directory
			Directory oldDir = (Directory) oldpath;
			Directory newDir = (Directory) newpath;
			// Find the Directory in NEWPATH with the same name as OLDPATH
			Directory oldInNew = newDir.getSubDirectory(oldDir.getName());
			// If it does not exist
			if (oldInNew == null) {
				/* Call the copy(Directory, Directory) method with OLDPATH and
				 * NEWPATH as its parameters
				 */
				copy(oldDir, newDir);
			}
			// Otherwise, meaning it must be overwritten
			else {
				/* Call the replace method with OLDPATH, NEWPATH and
				 * oldInNew as its parameters
				 */
				replace(oldDir, newDir, oldInNew);
			}
		}
		// Otherwise, if OLDPATH is File and NEWPATH is a Directory
		else if (oldpath instanceof File & newpath instanceof File) {
			/* Call the copy(File, File) method with OLDPATH and NEWPATH as
			 * its parameters
			 */
			copy((File) oldpath, (File) newpath);
		}
		/* Otherwise, if meaning OLDPATH is a Directory and NEWPATH is a File,
		 * or other such types, which is not valid
		 */
		else if (oldpath instanceof Directory & newpath instanceof File) {
			ExceptionHandler.invalidSyntaxException("cp");
		}
		/* Otherwise, do nothing, the exception has already been handled
		 * 
		 */
		// Return an empty String
		return "";
	}

	/*
	 * This method will, if OLDPATH is not a parent of NEWPATH, call a
	 * recursive helper function which will put all of the contents from the
	 * OLDPATH Directory, into a Directory in NEWPATH, with the same name as
	 * OLDPATH
	 * 
	 * @param oldpath The Directory which corresponds to the given OLDPATH
	 * @param newpath The Directory which corresponds to the given NEWPATH
	 */
	private void copy(Directory oldpath, Directory newpath) {
		// Validate that OLDPATH is not a parent of NEWPATH
		if (Validator.validateParent(oldpath, newpath)) {
			// Return from this method
			return;
		}
		// Create a new Directory with the same name as OLDPATH
		Directory newDir = new Directory(oldpath.getName());
		// Call the helper function, copyDir, with newpath, newdir and oldpath
		copyDir(newpath, newDir, oldpath);
	}
	
	/*
	 * This method is called when a Directory with the same name as OLDPATH is
	 * found in NEWPATH. It deletes that Directory, then calls the
	 * copy(dir,dir) method, which will (through a recursive helper function)
	 * copy the contents of OLDPATH into a folder of the same name in NEWPATH
	 * 
	 * @param oldpath The Directory which corresponds to the given OLDPATH
	 * @param newpath The Directory which corresponds to the given NEWPATH
	 * @param delete The Directory found in NEWPATH, which is to be deleted
	 */
	private void replace(Directory oldpath, Directory newpath, Directory
			delete) {
		/* Remove the Directory to be deleted, which exists in NEWPATH and has
		 * the same name as OLDPATH
		 */
		delete.deleteDirectory();
		/* Call the copy(Directory, Directory) method, which will add OLDPATH
		 * into NEWPATH
		 */
		copy(oldpath, newpath);
	}
	
	/*
	 * This is a recursive helper function for copy(dir, dir) which is used to
	 * populate the NEWPATH with the contents of OLDPATH
	 * 
	 * @param parent This Directory will have child added as a subdirectory
	 * @param child This Directory will have parent added as its parent
	 * directory and this method will recursively call itself with child as the
	 * new parent
	 * @param old This Directory represents the current location of parent in
	 * OLDPATH, which is necessary to copy over the contents, and will move
	 * with parent as it moves
	 */
	private void copyDir(Directory parent, Directory child, Directory old) {
		// Set child to be a subdirectory of parent
		parent.addDirectory(child);
		// For every subdirectory of the old
		for (Directory directory : old.getSubDirectories()) {
			/* Recursively call this method with child, a new directory with
			 * with the same name as the current subdirectory, and the current
			 * subdirectory
			 */
			copyDir(child, new Directory(directory.getName()), directory);
		}
		// For every File of old
		for (File file : old.getAllFiles()) {
			// Call copy(File, Directory) method with the old's Files and child
			copy(file, child);
		}
	}
	
	/*
	 * This method will create File identical to OLDPATH and put it in NEWPATH.
	 * If a File of the same name in NEWPATH exists, this new File will replace
	 * it
	 * 
	 * @param oldpath The File which corresponds to the given OLDPATH
	 * @param newpath The Directory which corresponds to the given NEWPATH
	 */
	private void copy(File oldpath, Directory newpath) {
		// Create a new File with the name and text of OLDPATH
		File newFile = new File(oldpath.getName(), oldpath.getText());
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
	 * This method will overwrite the text of NEWPATH with that of OLDPATH
	 * 
	 * @param oldpath The Directory which corresponds to the given OLDPATH
	 * @param newpath The Directory which corresponds to the given NEWPATH
	 */
	private void copy(File oldpath, File newpath) {
		// Overwrite NEWPATH's text with that of OLDPATH
		newpath.setText(oldpath.getText());
	}
	
	/*
	 * This method returns the documentation of cp, for use by man
	 * 
	 * @return A String representation of cp's documentation
	 */
	@Override
	public String printDocumentation() {
		return documentation;
	}
}
