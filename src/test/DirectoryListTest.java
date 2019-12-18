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
import org.junit.*;
import driver.*;
import mockJShell.MockCommand;
import mockJShell.MockCommandHistory;
import mockJShell.MockDirectoryMake;
import mockJShell.MockFileSystem;
import mockJShell.MockJShell;
import mockJShell.MockValidator;
import java.lang.reflect.Field;

public class DirectoryListTest {
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
    DirectoryListTest.output = "";
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
  public void testForNormalList() {
    /*
     * This test checks that the contents of a directory are listed
     */
    output = ((MockCommand) MockCommand.execute("ls")).runCommand("");
    expectedOutput = "/: Users Programs\n";
    Assert.assertEquals(expectedOutput, output);
  }

  @Test
  public void testForRecursiveListWithPath() {
    /*
     * This test check that if the ls command is used with -R and a path is given all sub
     * directories of the current directory should be listed recursively
     */
    output = ((MockCommand) MockCommand.execute("ls")).runCommand(" -R /"
        + "Users");
    expectedOutput = "/Users/: TESTFILE\n^/Users/Muizz/:\n^/Users/Muizz/"
        + "Desktop/:\n";
    Assert.assertEquals(expectedOutput, output);
  }

  @Test
  public void testForRecursiveListWithNoPath() {
    /*
     * this test checks if -R is present, but no path is given, then the contents of the current
     * directory should be printed with a new line following each.
     */
    output = ((MockCommand) MockCommand.execute("ls")).runCommand(" -R");
    expectedOutput = "/:\n^/Users/: TESTFILE\n^/Users/Muizz/:\n^/Users/Muizz/"
        + "Desktop/:\n^/Programs/:";
    Assert.assertEquals(expectedOutput, output);
  }

  @Test
  public void listRPathFile() {
    /*
     * This test checks if -R and a path are given which specifies a file then the path should be
     * printed
     */
    output = ((MockCommand) MockCommand.execute("ls")).runCommand(" -R /Users/"
        + "TESTFILE");
    expectedOutput = "TESTFILE\n";
    Assert.assertEquals(expectedOutput, output);
  }

  @Test
  public void testListPathDoesntExist() {
    /*
     * This test checks if -R and a path are given which specifies a directory then the path should
     * be print followed by a : and then the contents of the directory with a newline after each.
     */
    new MockValidator("ls /System32");
    Assert.assertEquals("Error: /System32 is not a valid path."
        + System.getProperty("line.separator"), outContent.toString());
  }

  @Test
  public void testListMultipleFullAndRelativePaths() {
    /*
     * This test checks if ls can detect multiple paths and print them
     */
    output = ((MockCommand) MockCommand.execute("ls")).runCommand(" /Users "
        + "Programs ");
    expectedOutput = "Users: TESTFILE Muizz\nPrograms:\n";
    Assert.assertEquals(expectedOutput, output);
  }
  
  @Test
  public void testListMultiplePathsWithError() {
    /*
     * This test checks if ls can detect multiple paths and print them
     */
    output = ((MockCommand) MockCommand.execute("ls")).runCommand(" /Users "
        + "InvalidPath " + "Programs " );
    expectedOutput = "Users: TESTFILE Muizz\nPrograms:\n";
    Assert.assertEquals(expectedOutput, output);
    Assert.assertEquals("Error: InvalidPath is not a valid path."
        + System.getProperty("line.separator"), outContent.toString());
  }
  
  @Test
  public void testRecursivelyListMultipleFullAndRelativePaths() {
    /*
     * This test checks if ls can detect multiple paths and print them
     */
    output = ((MockCommand) MockCommand.execute("ls")).runCommand("-R /Users "
        + "Programs ");
    expectedOutput = "/Users/: TESTFILE\n" + 
        "^/Users/Muizz/:\n^/Users/Muizz/Desktop/:\n/Programs/:\n";
    Assert.assertEquals(expectedOutput, output);
  }
  
  @Test
  public void testAppendListRescursiveToTESTFILE() {
    /*
     * This test checks if ls can detect multiple paths and print them
     */
    new MockValidator("ls -R /Users >> /Users/TESTFILE");
    output = ((MockCommand) MockCommand.execute("cat")).runCommand("/Users/"
        + "TESTFILE");
    expectedOutput = "TESTFILE: test\n/Users/: TESTFILE\n" + 
        "^/Users/Muizz/:\n^/Users/Muizz/Desktop/:\n";
    Assert.assertEquals(expectedOutput, output);
  }
  
  @Test
  public void testOverWriteList() {
    /*
     * This test checks if ls can detect multiple paths and print them
     */
    new MockValidator("ls /Users > /Users/TESTFILE");
    output = ((MockCommand) MockCommand.execute("cat")).runCommand("/Users/"
        + "TESTFILE");
    expectedOutput = "TESTFILE: Users: TESTFILE Muizz\n";
    Assert.assertEquals(expectedOutput, output);
  }
  
  
  
  

}
