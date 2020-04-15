package application.model;

import static org.junit.Assert.*;

import application.controller.MainController;
import java.io.IOException;
import java.util.LinkedList;
import org.junit.Test;

public class CommandExecuteTest {

  @Test
  public void generateStringFromListTest() {

    MainController mc = new MainController();
    CommandExecute ce = new CommandExecute(mc);

    LinkedList<String> param = new LinkedList<String>();
    param.add("String1");
    param.add("String2");

    String expectedOutput = "String1\nString2\n";
    assertEquals(expectedOutput, ce.generateStringFromList(param));
  }

  @Test
  public void generateStringFromListSpaceTest() {

    MainController mc = new MainController();
    CommandExecute ce = new CommandExecute(mc);

    LinkedList<String> param = new LinkedList<String>();
    param.add("String1");
    param.add("String2");

    String expectedOutput = "String1 String2 ";
    assertEquals(expectedOutput, ce.generateStringFromListSpace(param));
  }

  @Test
  public void individualFlagsTest() {

    MainController mc = new MainController();
    CommandExecute ce = new CommandExecute(mc);
    String param = "flag";
    String expectedOutput = "gcc flag";
    assertEquals(expectedOutput, ce.individualFlags(param));
  }

  @Test
  public void buildProjectTest() throws IOException, InterruptedException {

    MainController mc = new MainController();
    CommandExecute ce = new CommandExecute(mc);
    Project param1 = new Project("Name1", "Location1");
    String param2 = "flag";
    String param3 = "library";
    String[] receivedOutput = ce.buildProject(param1, param2, param3);
  }

  @Test
  public void buildExecutionTest() throws IOException, InterruptedException {

    MainController mc = new MainController();
    CommandExecute ce = new CommandExecute(mc);
    String param = "abcd";
    String[] receivedOutput = ce.buildExecution(param);
  }

  @Test
  public void runProgramTest() throws IOException, InterruptedException {

    MainController mc = new MainController();
    CommandExecute ce = new CommandExecute(mc);
    Project param = new Project("Name", "Location");
    String[] receivedOutput = ce.runProgram(param);
  }

  @Test
  public void test() {

    MainController mc = new MainController();

    CommandExecute ce = new CommandExecute(mc);

    LinkedList<String> list1 = new LinkedList<String>();
    list1.add("String1");
    list1.add("String2");

    String comp1 = "String1\nString2\n";
    String comp2 = "String1 String2 ";

    assertEquals(comp1, ce.generateStringFromList(list1));
    assertEquals(comp2, ce.generateStringFromListSpace(list1));

    assertEquals("gcc flag1", ce.individualFlags("flag1"));

    fail("Not yet implemented");
  }
}
