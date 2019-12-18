package test;
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


import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import org.junit.*;

import driver.*;
import mockJShell.*;

public class CopyTest {
	private MockFileSystem fs;
	private static Directory dir1;
	private static Directory dir2;
	private static Directory dir3;
	private static File file1;
	private static File file2;
	private static File file3;
	private static MockValidator valid;
	private static MockJShell js = new MockJShell();
	private final ByteArrayOutputStream outContent = new
			ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	
	@Before
	public void setUpClass() throws Exception {
		fs = MockFileSystem.getInstance();
		dir1 = new Directory("John");
		dir2 = new Directory("Paul");
		dir3 = new Directory("Paul");
		file1 = new File("Bob", "Hello");
		file2 = new File("Joe", "Goodbye");
		file3 = new File("Bob", "Hey");
		fs.setCurDir(fs.getRoot());
		fs.getRoot().addDirectory(dir1);
		fs.getRoot().addDirectory(dir2);
		dir1.addDirectory(dir3);
		dir3.addFile(file1);
		dir2.addFile(file2);
		dir2.addFile(file3);
	}
	
	@Before
	public void setUpStream() {
	    // Make a new scanner before every test case
	    System.setOut(new PrintStream(outContent));
	}
	
	@After
	public void tearDown() throws Exception {
		// Destroy the old scanner after every test case
	    System.setOut(originalOut);
	    Field field = (fs.getClass().getDeclaredField("fs"));
	    field.setAccessible(true);
	    field.set(null, null);
	}
	
	@AfterClass
	public static void tearDownClass() throws Exception {
		MockCommandHistory.clearHistory();
	}

	@Test
	public void copyFileToFile() {
		/* Set the String input to be the command cp on John/Paul/Bob and
		 * Paul/Joe, which in memory, is /dir1/dir3/file1 and /dir2/file2
		 */
		String input = "cp John/Paul/Bob Paul/Joe";
		// Create new validator with the String input as its parameter
		valid = new MockValidator(input);
		/* Make sure the text of file2, which is /Paul/Bob is Hello, which is
		 * the text of /John/Paul/Bob, as desired
		 */
		assertTrue(file2.getText().equals("Hello"));
	}
	
	@Test
	public void copyFileToDir() {
		/* Set the String input to be the command cp on John/Paul/Bob and
		 * Paul, which in memory, is /dir1/dir3/file1 and /dir2
		 */
		String input = "cp John/Paul/Bob Paul";
		// Create new validator with String input as its parameter
		valid = new MockValidator(input);
		/* Make sure that the dir2, which is /Paul, has a File named Bob, and
		 * that that File's text is Hello, which is the text of /John/Paul/Bob,
		 * as desired
		 */
		assertTrue(dir2.getFile("Bob").getText().equals("Hello"));
	}
	
	@Test
	public void copyFileToDirReplace() {
		/* Set the String input to be the command cp on Paul/Bob and
		 * John/Paul, which in memory, is /dir2/file3 and /dir1/dir2
		 */
		String input = "cp Paul/Bob John/Paul";
		// Create new validator with String input as its parameter
		valid = new MockValidator(input);
		/* Make sure that dir 3, which is /John/Paul, has a file named
		 * Bob, whose text is Hey, which is the same as Paul/Bob, as
		 * desired
		 */
		assertTrue(dir3.getFile("Bob").getText().equals("Hey"));
	}
	
	@Test
	public void copyDirToDir() {
		/* Set the String input to be the command cp on John/Paul and
		 * Paul, which in memory, is /dir1/dir3 and /dir2
		 */
		String input = "cp John/Paul Paul";
		// Create new validator with String input as its parameter
		valid = new MockValidator(input);
		/* Make sure that the dir2, which is /Paul, has a subdirectory named
		 * Paul, and that that Directory's has a File named Bob, and that that
		 * File's text is Hello, which is the text of /John/Paul/Bob, showing
		 * that this method recursively copies files and directories correctly
		 */
		assertTrue(dir2.getSubDirectory("Paul").getFile("Bob").getText()
				.equals("Hello"));
	}
	
	@Test
	public void copyDirToDirReplace() {
		/* Set the String input to be the command cp on Paul and
		 * John, which in memory, is /dir2 and /dir1
		 */
		String input = "cp Paul John";
		// Create new validator with String input as its parameter
		valid = new MockValidator(input);
		/* Make sure that the dir1, which is John, has a subdirectory named
		 * Paul, which has a file named Joe, whose text is Goodbye, which is
		 * the same as /Paul/Joe, showing that this method replaces the
		 * original Paul Directory in John, and recursively copies files and
		 * directories correctly
		 */
		assertTrue(dir1.getSubDirectory("Paul").getFile("Joe").getText()
				.equals("Goodbye"));
	}
	
	@Test
	public void copyInvalidPath() {
		/* Set the String input to be the command cp on John/Paul and
		 * John/Jones, which has an invalid path Paul/Jones
		 */
		String input = "cp John/Paul Paul/Jones";
		// Create new validator with String input as its parameter
		valid = new MockValidator(input);
		/* Check that an the error message corresponding to the path Paul/Jones
		 * being invalid has printed
		 */
		Assert.assertEquals("Error: Paul/Jones is not a valid path." + System
				.getProperty("line.separator"), outContent.toString());
	}
	
	@Test
	public void copyDirToFileError() {
		/* Set the String input to be the command cp on John/Paul and John/Joe,
		 * which would be cp DIR FILE, which is not valid syntax for cp
		 */
		String input = "cp John/Paul Paul/Joe";
		// Create new validator with String input as its parameter
		valid = new MockValidator(input);
		/* Check that an the error message corresponding to the invalid syntax
		 * of the command call has printed
		 */
		Assert.assertEquals("Error: Invalid syntax for cp. Check man cp"
		+ System.getProperty("line.separator"), outContent.toString());
	}
	
	@Test
	public void copyOldPathParentOfNew() {
		String input = "cp John John/Paul";
		valid = new MockValidator(input);
		Assert.assertEquals("Error: /John/ is a parent path of /John/Paul/."
			+ " Cannot perform cp/mv." + System.getProperty("line.separator"),
			outContent.toString());
	}

}
