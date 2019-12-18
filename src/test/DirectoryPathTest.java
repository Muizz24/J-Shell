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
package test;

import static org.junit.Assert.*;
import mockJShell.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.*;
import driver.*;
import mockJShell.MockDirectoryPath;
import java.lang.reflect.Field;

public class DirectoryPathTest {
  private static MockJShell mockJShell = new MockJShell();
  private static String output = "";
  private static String expectedOutput = "";
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private MockFileSystem<Directory> fs;

  @Before
  public void setUp() throws Exception {
    // Make a new scanner before every test case
    System.setOut(new PrintStream(outContent));
    // Make a new scanner before every test case for catching errors
    System.setOut(new PrintStream(outContent));
    // Empty the output
    output = "";
    MockDirectoryMake mkdir = (MockDirectoryMake) MockCommand.execute("mkdir");
    // Create a mini Filesystem
    mkdir.runCommand(" /Users /Programs /Users/Muizz /Users/Muizz/Desktop");
    new MockValidator("echo \"test\" > /Users/TESTFILE");
    // Create temporary singleton instance
    fs = MockFileSystem.getInstance();
  }

  @After
  public void tearDownClass() throws Exception {
    // Destroy the old scanner after every test case
    System.setOut(originalOut);
    // Set current directory back to root and reset NEWFILE's text
    fs.setCurDir(fs.getRoot());
    Field field = (fs.getClass()).getDeclaredField("fs");
    field.setAccessible(true);
    field.set(null, null); // setting the ref parameter to null
  }

  @Test
  public void testForBigPWDPath() {
    // Traverse to the last directory in the FileSystem path for pwd.
    ((MockDirectoryChange) MockCommand.execute("cd")).runCommand("/Users/"
        + "Muizz/Desktop");
    output = ((MockDirectoryPath) MockCommand.execute("pwd")).runCommand("");
    expectedOutput = "/Users/Muizz/Desktop/";
    Assert.assertEquals(expectedOutput, output);
  }

  @Test
  public void testForInvalidPWDInput() {
    output = ((MockDirectoryPath) MockCommand.execute("pwd")).runCommand(""
        + " Un-needed input");
    Assert.assertEquals(
        "Error: Invalid syntax for pwd. Check man pwd" + 
    System.getProperty("line.separator"), outContent.toString());
  }

  @Test
  public void testForRootPWD() {
    fs.setCurDir(fs.getRoot());
    output = ((MockDirectoryPath) MockCommand.execute("pwd")).runCommand("");
    expectedOutput = "/";
    Assert.assertEquals(expectedOutput, output);
  }

  @Test
  public void testForMakeNEWFILEWithPWD() {
    // make a new file with pwd's output as its text
    new MockValidator("pwd > /NEWFILE");
    output = ((MockViewFile) MockCommand.execute("cat")).runCommand(""
        + " /NEWFILE");
    expectedOutput = "NEWFILE: /";
    Assert.assertEquals(expectedOutput, output);
  }

  @Test
  public void testForAppendNEWFILEWithPWD() {
    fs.setCurDir(fs.getRoot()
        .getSubDirectory("Users"));
    // append to NEWFILE with pwd's output as its text
    new MockValidator("pwd >> /Users/TESTFILE");
    output = ((MockViewFile) MockCommand.execute("cat")).runCommand(""
        + " /Users/TESTFILE");
    expectedOutput = "TESTFILE: test\n/Users/";
    Assert.assertEquals(expectedOutput, output);
  }

  @Test
  public void testForInvalidRedirectionSyntax() {
    new MockValidator("pwd >>> /Users/TESTFILE");
    output = ((MockViewFile) MockCommand.execute("cat")).runCommand(" /Users/"
        + "TESTFILE");
    expectedOutput = "TESTFILE: test";
    Assert.assertEquals(expectedOutput, output);
  }

  @Test
  public void testForAnotherInvalidRedirectionSyntax() {
    new MockValidator("pwd != /Users/TESTFILE");
    output = ((MockViewFile) MockCommand.execute("cat")).runCommand(" "
        + "/Users/TESTFILE");
    expectedOutput = "TESTFILE: test";
    Assert.assertEquals("TESTFILE: test", output);
  }

}
