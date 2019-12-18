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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mockJShell.*;
import driver.Directory;
import driver.File;

public class MoverTest {
	private MockFileSystem fs;
	private static Directory dir1;
	private static Directory dir2;
	private static Directory dir3;
	private static File file1;
	private static File file2;
	private static File file3;
	private static MockValidator v;
	private static MockJShell js = new MockJShell();
	private final ByteArrayOutputStream outContent = new
			ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;

	@Before
	public void setUp() throws Exception {
		fs = fs.getInstance();
		dir1 = new Directory("John");
		dir2 = new Directory("Cam");
		dir3 = new Directory("John");
		file1 = new File("Ryan", "All");
		file2 = new File("Pete", "Ball");
		file3 = new File("Ryan", "Call");
		fs.setCurDir(fs.getRoot());
		fs.getRoot().addDirectory(dir1);
		fs.getRoot().addDirectory(dir2);
		fs.getRoot().addFile(file3);
		dir2.addDirectory(dir3);
		dir1.addFile(file1);
		dir3.addFile(file2);
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
	
	@Test
	public void moveFileToFile() {
		String input = "mv John/Ryan Cam/John/Pete";
		v = new MockValidator(input);
		boolean test1 = file2.getText().equals("All");
		boolean test2 = dir1.getFile("Ryan") == null;
		assertTrue(test1 & test2);
	}

	@Test
	public void moveFileToDir() {
		String input = "mv Cam/John/Pete John";
		v = new MockValidator(input);
		boolean test1 = dir1.getFile("Pete").getText().equals("Ball");
		boolean test2 = dir3.getFile("Pete") == null;
		assertTrue(test1 & test2);
	}
	
	@Test
	public void moveFileToDirReplace() {
		String input = "mv Ryan John";
		v = new MockValidator(input);
		boolean test1 = dir1.getFile("Ryan").getText().equals("Call");
		boolean test2 = fs.getRoot().getFile("Ryan") == null;
		assertTrue(test1 & test2);
	}
	
	@Test
	public void moveDirToDir() {
		String input = "mv Cam John";
		v = new MockValidator(input);
		boolean test1 = dir1.getSubDirectory("Cam").getSubDirectory("John")
				.getFile("Pete").getText().equals("Ball");
		boolean test2 = fs.getRoot().getSubDirectory("Cam") == null;
		assertTrue(test1 & test2);
	}
	
	@Test
	public void moveDirToDirReplace() {
		String input = "mv John Cam";
		v = new MockValidator(input);
		boolean test1 = dir2.getSubDirectory("John").getFile("Ryan").getText()
				.equals("All");
		boolean test2 = fs.getRoot().getSubDirectory("John") == null;
		assertTrue(test1 & test2);
	}
	
	@Test
	public void moveInvalidPath() {
		String input = "mv John/Ryan James";
		v = new MockValidator(input);
		Assert.assertEquals("Error: James is not a valid path." + System
				.getProperty("line.separator"), outContent.toString());
	}
	
	@Test
	public void moveDirToFileError() {
		String input = "mv Cam/John John/Ryan";
		v = new MockValidator(input);
		Assert.assertEquals("Error: Invalid syntax for mv. Check man mv"
				+ System.getProperty("line.separator"), outContent.toString());
	}
	
	@Test
	public void moveOldPathParentOfNew() {
		String input = "mv Cam Cam/John";
		v = new MockValidator(input);
		Assert.assertEquals("Error: /Cam/ is a parent path of /Cam/John/."
			+ " Cannot perform cp/mv." + System.getProperty("line.separator"),
			outContent.toString());
	}
}
