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


import java.io.ByteArrayOutputStream;
import mockJShell.*;
import java.io.PrintStream;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import driver.*;

public class TreeOfFSTest {
  private static MockJShell mockJShell = new MockJShell();
  private static String output = "";
  private static String expectedOutput = "";
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private MockFileSystem<Directory> fs;

  @Before
  public void setUp() throws Exception {
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
  public void tearDown() throws Exception {
    // Destroy the old scanner after every test case meant for error catching
    System.setOut(originalOut);
    MockCommandHistory.clearHistory();
    Field field = (fs.getClass()).getDeclaredField("fs");
    field.setAccessible(true);
    field.set(null, null); //setting the ref parameter to null
  }

  @Test
  public void testForCorrectOutput() {
    output = ((MockTreeOfFS) MockCommand.execute("tree")).runCommand("");
    expectedOutput = "/\n\tUsers\n\t\tMuizz\n\t\t\tDesktop\n\tPrograms\n";
    Assert.assertEquals(expectedOutput, output);
  }
  
  @Test
  public void testForInvalidSyntax() {
    output = ((MockTreeOfFS) MockCommand.execute("tree")).runCommand(" "
        + "10 times");
    expectedOutput = "Error: Invalid syntax for tree. Check man tree"
        + System.getProperty("line.separator");
    Assert.assertEquals(expectedOutput, outContent.toString());
  }
  
  @Test
  public void testForTrailingWhiteSpaceInput() {
    output = ((MockTreeOfFS) MockCommand.execute("tree")).runCommand("     ");
    expectedOutput = "/\n\tUsers\n\t\tMuizz\n\t\t\tDesktop\n\tPrograms\n";
    Assert.assertEquals(expectedOutput, output);
  }
  
  @Test
  public void testForAppendingToTESTFILE() {
    new MockValidator("tree >> /Users/TESTFILE");
    output = ((MockViewFile) MockCommand.execute("cat")).runCommand(""
        + "/Users/TESTFILE");
    expectedOutput = "TESTFILE: test\n/\n\tUsers\n\t\tMuizz\n\t\t\tDesktop\n"
        + "\tPrograms\n";
    Assert.assertEquals(expectedOutput, output);
  }
  
  @Test
  public void testForCreatingNEWFILEWithTree() {
    new MockValidator("tree > /Users/NEWFILE");
    output = ((MockViewFile) MockCommand.execute("cat")).runCommand("/Users"
        + "/NEWFILE");
    expectedOutput = "NEWFILE: /\n\tUsers\n\t\tMuizz\n\t\t\tDesktop\n"
        + "\tPrograms\n";
    Assert.assertEquals(expectedOutput, output);
  }
}
