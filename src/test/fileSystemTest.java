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
import driver.FileSystem;
import driver.Directory;
import driver.ExceptionHandler;
import org.junit.*;
import driver.File;
import java.lang.reflect.Field;

public class fileSystemTest {
  // Instantiate the output scanners
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private FileSystem<Directory> fs;
  
  @Before
  public void setUp() throws Exception{
    // Make a new scanner before every test case
    System.setOut(new PrintStream(outContent));
    // Make a mini file System.
    fs = fs.getInstance();
    String[] dir_names = {"Users", "Muizz", "Desktop", "Documents"};
    for (String name: dir_names) {
      fs.getCurDir().addDirectory(new Directory(name));
      fs.setCurDir(fs.getCurDir().getSubDirectory(name));
    }
    // Make a new file at current directory
    fs.getCurDir().addFile(new File("Hello_World", "hello world"));
  }
  
  @After
  public void restoreStreams() throws Exception {
    // Destroy the old scanner after every test case
      System.setOut(originalOut);
      Field field = (fs.getClass()).getDeclaredField("fs");
      field.setAccessible(true);
      field.set(null, null); //setting the ref parameter to null
  }
  
  @Test
  public void testTracePathForRoot() {
    Assert.assertEquals("/", fs.tracePath(fs.getRoot(), ""));
  }
  
  @Test
  public void testNewCurrentDirectory() {
    Assert.assertTrue(fs.getCurDir().getName().equals("Documents"));
  }
  
  
  @Test
  public void testTracePathFromDocuments() {
    String tracedpath = fs.tracePath(fs.getCurDir(), "");
    Assert.assertEquals(tracedpath, "/Users/Muizz/Desktop/Documents/");
  }
  
  @Test
  public void testPathToDirectory() {
    Object dir_to_find = fs.pathToDirorFile("/Users/Muizz/Desktop");
    Directory expected = fs.getCurDir().getParent();                                                            
    Assert.assertTrue(dir_to_find == expected);
  }
  
  @Test
  public void testPathToFile() {
    Object file_to_find = fs.pathToDirorFile(
        "/Users/Muizz/Desktop/Documents/Hello_World");
    File expected = fs.getCurDir().getFile("Hello_World");
    Assert.assertTrue(file_to_find == expected);
  }
  
  @Test
  public void testExceptionPathToDirOrFileFullPath() {
    fs.pathToDirorFile("/Sam");
    Assert.assertEquals("Error: /Sam is not a valid path."
        + System.getProperty("line.separator"), outContent.toString());
  }
  
  @Test
  public void testExceptionPathToDirOrFileRelativePath() {
    fs.pathToDirorFile("Muizz/Schleepy");
    Assert.assertEquals("Error: Muizz/Schleepy is not a valid path."
        + System.getProperty("line.separator"), outContent.toString());
  }
  
}
