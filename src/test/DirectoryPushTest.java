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
package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import org.junit.*;

import mockJShell.*;
import driver.Directory;
import driver.File;
import driver.DirectoryStack;

public class DirectoryPushTest {
	private static MockFileSystem<Directory> fs = MockFileSystem.getInstance();
	private static Directory dir1;
	private static Directory dir2;
	private static Directory dir3;
	private static MockValidator valid;
	private static MockJShell js = new MockJShell();
	private final ByteArrayOutputStream outContent = new
			ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		dir1 = new Directory("John");
		dir2 = new Directory("Jack");
		dir3 = new Directory("Joe");
	}
	
	@Before
	public void setUpStream() {
	    // Make a new scanner before every test case
	    System.setOut(new PrintStream(outContent));
		fs.getRoot().addDirectory(dir1);
		fs.getRoot().addDirectory(dir2);
		dir1.addDirectory(dir3);
	  }

	@After
	public void tearDown() throws Exception{
		// Set current working directory to root after each test
		fs.setCurDir(fs.getRoot());
		// Destroy the old scanner after every test case
	    System.setOut(originalOut);
	    MockCommandHistory.clearHistory();
        Field field = (fs.getClass()).getDeclaredField("fs");
        field.setAccessible(true);
        field.set(null, null);
	}
	
	// Test pushd on a relative path to see if the cwd has changed
	@Test
	public void testPushdChangeRelative() {
		fs.setCurDir(dir1);
		String input = "pushd Joe";
		valid = new MockValidator(input);
		assertTrue(dir3 == fs.getCurDir());
	}
	
	/* Test pushd on a relative path to see if the inputed directory has been
	 * pushed to the stack
	 */
	@Test
	public void testPushdStackRelative() {
		fs.setCurDir(dir1);
		String input = "pushd Joe";
		valid = new MockValidator(input);
		assertTrue(dir1 == DirectoryStack.pop());
	}

	// Test pushd on a full path to see if the cwd has changed
	@Test
	public void testPushdChangeFullPath() {
		fs.setCurDir(dir2);
		String input = "pushd /John/Joe";
		valid = new MockValidator(input);
		assertTrue(dir3 == fs.getCurDir());
	}
	
	/* Test pushd on a full path to see if the inputed directory has been
	 * pushed to the stack
	 */
	@Test
	public void testPushdStackFullPath() {
		fs.setCurDir(dir2);
		String input = "pushd /John/Joe";
		valid = new MockValidator(input);
		assertTrue(dir2 == DirectoryStack.pop());
	}
	
	// Test an invalid path input on pushd command for the exception handling
	@Test
	public void testInvalidPathException() {
		fs.setCurDir(dir1);
		String input = "pushd /Joe/Jim";
		valid = new MockValidator(input);
		Assert.assertEquals("Error: /Joe/Jim is not a valid path."
		+ System.getProperty("line.separator"), outContent.toString());
	}
	
	/* Test a pushd call with no directory, which gives a
	 * invalidSyntaxException
	 */
	@Test
	public void testInvalidSyntaxException() {
		fs.setCurDir(dir1);
		String input = "pushd";
		valid = new MockValidator(input);
		Assert.assertEquals("Error: Invalid syntax for pushd. Check man pushd"
		+ System.getProperty("line.separator"), outContent.toString());
	}
}
