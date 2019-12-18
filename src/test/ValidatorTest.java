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
import org.junit.Test;
import junit.framework.Assert;
import driver.*;

public class ValidatorTest {
  private static final String pathRegex = "[^A-z/_-]"; 
  private static final String nameRegex = "[^A-z_-]";
  private final ByteArrayOutputStream outContent = 
      new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @Test
  public void testForInvalidPath() {
    String path = "/Users/Fake!/Pa___th";
    Assert.assertFalse(Validator.validNameorPath(path, pathRegex));
  }
  
  @Test
  public void testForValidPath() {
    String path = "/Users/Legit/Pa___th";
    Assert.assertTrue(Validator.validNameorPath(path, pathRegex));
  }
  
  @Test
  public void testForInvalidName() {
    String name = "/Users/NotLegit/Name";
    Assert.assertFalse(Validator.validNameorPath(name, nameRegex));
  }
  
  @Test
  public void testForValidName() {
    String name = "Legit_Name";
    Assert.assertTrue(Validator.validNameorPath(name, nameRegex));
  }
  
  @Test
  public void testForValidFindInputSyntax() {
    String[] inputSyntax = "/Path -type d -name \"test\"".split(" ");
    Assert.assertTrue(Validator.validateFind(inputSyntax));
  }
  
  @Test
  public void testForInvalidFindInputSyntax() {
    String[] inputSyntax = "/Path -tyap q -nume \"test\"".split(" ");
    Assert.assertFalse(Validator.validateFind(inputSyntax));
  }
  
  @Test
  public void testForInvalidFile() {
    Directory dir = new Directory("myDir");
    Assert.assertFalse(Validator.validFile(dir, "WrongFileName"));
  }
  
  @Test
  public void testForValidFile() {
    Directory dir = new Directory("myDir");
    File file = new File("testfile", "text");
    dir.addFile(file);
    Assert.assertTrue(Validator.validFile(dir, "testfile"));
  }
  
  @Test
  public void testForInvalidDirectory() {
    Directory parentDir = new Directory("myDir");
    Assert.assertFalse(Validator.validDir(parentDir, "WrongDirName"));
  }
  
  @Test
  public void testForValidDirectory() {
    Directory parentDir = new Directory("myDir");
    Directory subDir = new Directory("bob");
    parentDir.addDirectory(subDir);
    Assert.assertTrue(Validator.validDir(parentDir, "bob"));
  }
  
  @Test
  public void testForCommandWithLeadingWhiteSpace() {
    String input = "    mkdir bob";
    Assert.assertFalse(Validator.validateLeadingWhiteSpace(input));
  }
  
  @Test
  public void testForValidatingParentPath() {
    Directory parentDir = new Directory("myDir");
    Directory subDir = new Directory("bob");
    parentDir.addDirectory(subDir);
    Assert.assertFalse(Validator.validatePathTo("bob/dir"));
  }
  
  @Test
  public void testForValidatingParentOLDPATHforNEWPATH() {
    Directory parentDir = new Directory("myDir");
    Directory subDir = new Directory("bob");
    parentDir.addDirectory(subDir);
    Assert.assertTrue(Validator.validateParent(parentDir, subDir));
  }

}
