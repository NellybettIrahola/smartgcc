package application.model;

import java.util.LinkedList;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProjectTest {
	
	@Test
	public void getNameTest(){
		String expectedOutput = "Output1";
		String param = "Output1";
		Project p1 = new Project(param,"Location1");
		assertEquals(expectedOutput,p1.getName());
	}
	
	@Test
	public void getProjectLocationTest(){
		String expectedOutput = "Output1";
		String param = "Output1";
		Project p1 = new Project("Name1",param);
		assertEquals(expectedOutput,p1.getProjectLocation());
	}
	
	@Test
	public void allFlagsTest(){
		String expectedOutput = "Output1";
		String param = "Output1";
		Project p1 = new Project("Name1","Location1");
		p1.setAllFlags(param);
		assertEquals(expectedOutput,p1.getProjectLocation());
	}
	
	@Test
	public void getSourceFilesTest() {
		LinkedList<String> expectedOutput = new LinkedList<String>();
		expectedOutput.add("value1");
		expectedOutput.add("value2");
		
		LinkedList<String> param = new LinkedList<String>();
		param.add("value1");
		param.add("value2"); 
		
		Project p1 = new Project("Name1","Location1");
		p1.setSourceFiles(param);
		
		assertEquals(expectedOutput,p1.getSourceFiles());
		
	}
	
	@Test
	public void getObjectFilesTest() {
		LinkedList<String> expectedOutput = new LinkedList<String>();
		expectedOutput.add("value1");
		expectedOutput.add("value2");
		
		LinkedList<String> param = new LinkedList<String>();
		param.add("value1");
		param.add("value2"); 
		
		Project p1 = new Project("Name1","Location1");
		p1.setObjectFiles(param);
		
		assertEquals(expectedOutput,p1.getObjectFiles());
		
	}
	
	@Test
	public void getLibrariesTest() {
		LinkedList<String> expectedOutput = new LinkedList<String>();
		expectedOutput.add("value1");
		expectedOutput.add("value2");
		
		LinkedList<String> param = new LinkedList<String>();
		param.add("value1");
		param.add("value2"); 
		
		Project p1 = new Project("Name1","Location1");
		p1.setLibraries(param);
		
		assertEquals(expectedOutput,p1.getLibraries());
		
	}
	
	@Test
	public void getDebugFlagsTest() {
		LinkedList<String> expectedOutput = new LinkedList<String>();
		expectedOutput.add("value1");
		expectedOutput.add("value2");
		
		LinkedList<String> param = new LinkedList<String>();
		param.add("value1");
		param.add("value2"); 
		
		Project p1 = new Project("Name1","Location1");
		p1.setDebugFlags(param);
		
		assertEquals(expectedOutput,p1.getDebugFlags());
		
	}
	
}
