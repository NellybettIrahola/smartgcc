package application.model;

import static org.junit.Assert.*;

import java.util.LinkedList;
import org.junit.Test;

public class SmartModelTest {

  @Test
  public void getProjectTest() {

    Project expectedOutput = new Project("Name1", "Location1");
    Project param1 = new Project("Name1", "Location1");
    String param2 = "Name1";
    SmartModel s1 = new SmartModel();
    s1.addProject(param1);
    assertEquals(expectedOutput, s1.getProject(param2));
  }

  @Test
  public void getProjectByPathtest() {

    Project expectedOutput = new Project("Name1", "Location1");
    Project param1 = new Project("Name1", "Location1");
    String param2 = "Location1";
    SmartModel s1 = new SmartModel();
    s1.addProject(param1);
    assertEquals(expectedOutput, s1.getProjectByPath(param2));
  }

  @Test
  public void updateProjectTest() {

    SmartModel s1 = new SmartModel();

    LinkedList<String> sources = new LinkedList<String>();
    sources.add("Source1");

    LinkedList<String> objects = new LinkedList<String>();
    objects.add("Object1");

    Project expectedOutput = new Project("Name1", "Location1");
    expectedOutput.setSourceFiles(sources);
    expectedOutput.setObjectFiles(objects);

    Project param1 = new Project("Name1", "Location1");

    s1.addProject(param1);

    String param2 = "Name1";

    assertEquals(expectedOutput, s1.updateProject(param2, sources, objects));
  }

  @Test
  public void getListProjects() {

    SmartModel s1 = new SmartModel();
    LinkedList<Project> expectedOutput = new LinkedList<Project>();

    Project p1 = new Project("Name1", "Location1");
    Project p2 = new Project("Name2", "Location2");
    Project p3 = new Project("Name3", "Location3");

    expectedOutput.add(p1);
    expectedOutput.add(p2);
    expectedOutput.add(p3);

    s1.addProject(p1);
    s1.addProject(p2);
    s1.addProject(p3);

    assertEquals(expectedOutput, s1.getListProjects());
  }
}
