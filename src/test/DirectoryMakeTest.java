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
import driver.*;
import mockJShell.MockCommandHistory;
import mockJShell.MockFileSystem;
import mockJShell.MockJShell;
import mockJShell.MockValidator;

public class DirectoryMakeTest {
  private static MockJShell mockJShell = new MockJShell();
  private MockFileSystem<Directory> fs;
  //Instantiate the output scanners
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  
  @Before
  public void setUp() throws Exception {
    // Make a new scanner before every test case
    System.setOut(new PrintStream(outContent));
    // Create temporary singleton instance and cd into root.
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
  public void testMakeOneDirectoryWithRelativePath() {
    String input = "mkdir bob";
    new MockValidator(input);
    Assert.assertTrue(fs.getCurDir().getSubDirectory("bob") !=
        null);
  }
  
  @Test
  public void testMakeMultipleDirectoryFullPaths() {
    // Test for all cases.
    String input = "mkdir /Users /Programs /Users/Muizz /Users/Muizz/Desktop";
    new MockValidator(input);
    Assert.assertTrue(fs.pathToDirorFile("/Users") 
        instanceof Directory);
    Assert.assertTrue(fs.pathToDirorFile("/Programs") 
        instanceof Directory);
    Assert.assertTrue(fs.pathToDirorFile("/Users/Muizz") 
        instanceof Directory);
    Assert.assertTrue(fs.pathToDirorFile("/Users/Muizz/Desktop") 
        instanceof Directory);
  }
  
  @Test
  public void testMakeMultipleDirectoriesWithException() {
    String input = "mkdir /System32 /Will/Sam /Users";
    new MockValidator(input);
    Assert.assertTrue(fs.pathToDirorFile("/System32") 
        instanceof Directory);
    Assert.assertEquals("Error: /Will is not a valid path."
        + System.getProperty("line.separator"), outContent.toString());
    Assert.assertTrue(fs.pathToDirorFile("/Users")
        instanceof Directory);
  }
  
  @Test
  public void testMakeNoDirectoryException() {
    String input = "mkdir";
    new MockValidator(input);
    Assert.assertEquals("Error: Invalid syntax for mkdir. Check man mkdir"
        + System.getProperty("line.separator"), outContent.toString());
    
  }
  
  @Test
  public void testDuplicateDirectoryMakeException() {
    String input = "mkdir /Carl /Carl";
    new MockValidator(input);
    Assert.assertEquals("Error: Carl already exists in given path."
        + System.getProperty("line.separator"), outContent.toString());
  }
  
  @Test
  public void testDuplicateDirectoryInAnotherDirectory() {
    String input = "mkdir /Carl /Carl/Carl";
    new MockValidator(input);
    Assert.assertTrue(fs.pathToDirorFile("/Carl") 
        instanceof Directory);
    Assert.assertTrue(fs.pathToDirorFile("/Carl/Carl") 
        instanceof Directory);
    
  }
  
  @Test
  public void testForUpperCaseCommandCall() {
    String input = "MKDIR /Andy";
    new MockValidator(input);
    Assert.assertTrue(fs.pathToDirorFile("/Andy") 
        instanceof Directory);
  }

}
