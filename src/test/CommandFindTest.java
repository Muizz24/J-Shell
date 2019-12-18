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
import driver.*;
import mockJShell.MockCommand;
import mockJShell.MockCommandHistory;
import mockJShell.MockDirectoryMake;
import mockJShell.MockFileSystem;
import mockJShell.MockJShell;
import mockJShell.MockValidator;
import mockJShell.MockViewFile;

public class CommandFindTest {
  private static MockJShell miniShell = new MockJShell();
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
  public void testForSearchingDirectoryFromRoot() {
    output = ((MockCommand) MockCommand.execute("find")).runCommand("find"
        + " / -type d -name \"Desktop\"");
    expectedOutput = "Desktop of type Directory found in path(s):\n" + 
        "--------------------------------------------\n" + 
        "->\t/Users/Muizz/\n";
    Assert.assertEquals(expectedOutput, output);
  }
  
  @Test
  public void testForSearchingFileFromRoot() {
    output = ((MockCommand) MockCommand.execute("find")).runCommand("find"
        + " / -type f -name \"TESTFILE\"");
    expectedOutput = "TESTFILE of type File found in path(s):\n" + 
        "--------------------------------------------\n" + 
        "->\t/Users/\n";
    Assert.assertEquals(expectedOutput, output);
  }
  
  @Test
  public void testForInvalidSyntax() {
    ((MockCommand) MockCommand.execute("find")).runCommand("find"
        + " / -tope g -none \"TESTFILE\"");
    expectedOutput = ("Error: Invalid syntax for find. Check man find" +
        System.getProperty("line.separator")); 
    output = outContent.toString();
    Assert.assertEquals(expectedOutput, output);
  }
  
  @Test
  public void testForFailedFind() {
    output = ((MockCommand) MockCommand.execute("find")).runCommand("find"
        + " / -type f -name \"BOB\"");
    expectedOutput = "BOB of type File found in path(s):\n" + 
        "--------------------------------------------\n";
    Assert.assertEquals(expectedOutput, output);
  }
  
  @Test
  public void testForAppendingResultIntoTESTFILE() {
    new MockValidator("find / -type f -name \"TESTFILE\" >> /Users/TESTFILE");
    output = ((MockViewFile) MockCommand.execute("cat")).runCommand("/"
        + "Users/TESTFILE");
    expectedOutput = "TESTFILE: test\nTESTFILE of type File found in path(s)" +
        ":\n--------------------------------------------\n" + 
        "->\t/Users/\n";
    Assert.assertEquals(expectedOutput, output);
  }
  
  @Test
  public void testForOverWritingResultIntoTESTFILE() {
    new MockValidator("find / -type d -name \"Desktop\" > /Users/TESTFILE");
    output = ((MockViewFile) MockCommand.execute("cat")).runCommand(""
        + "/Users/TESTFILE");
    expectedOutput = "TESTFILE: Desktop of type Directory found in path(s)" +
        ":\n--------------------------------------------\n" + 
        "->\t/Users/Muizz/\n";
    Assert.assertEquals(expectedOutput, output);
  }
  

}
