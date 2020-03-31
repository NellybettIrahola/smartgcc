package application.model;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SmartModel {
	private LinkedList<Project> listProjects;
	
	
	public SmartModel() {
		this.setListProjects(new LinkedList<Project>());
	}
	
	public int createProject(String name,LinkedList<String> sourceFiles, LinkedList<String> objectFiles, LinkedList<String> libraries, String projectDirectory) {
		Project pr=new Project(name,projectDirectory);
		pr.updateProject(sourceFiles, objectFiles,libraries);
		return this.addProject(pr);
	}
	
	public Project updateProject(String name, LinkedList<String> source,LinkedList<String> object,LinkedList<String> libraries) {
		Project pr=null;
		for(int i=0;i<this.getListProjects().size();i++) {
			if(this.getListProjects().get(i).name.equals(name)) {
				pr=this.getListProjects().get(i);
				pr.updateProject(source, object, libraries);
			}
		}
		return pr;
	}
	
	public int addProject(Project pr) {
		
		if(this.getListProjects()!=null) {
			List<Project> filteredPr=this.getListProjects().stream().filter(proj-> proj.name.equals(pr.name)).collect(Collectors.toList());
			if(filteredPr.size()!=0)
				return -1;
			else
				this.getListProjects().add(pr);
		}
		return 0;
	}
	
	public void deleteProject(String name) {
		Project pr=null;
		for(int i=0;i<this.getListProjects().size();i++) {
			if(this.getListProjects().get(i).name.equals(name))
				pr=this.getListProjects().get(i);
		}
		this.getListProjects().remove(pr);
	}
	
	public Project getProject(String name) {
		for(int i=0;i<this.getListProjects().size();i++) {
			if(this.getListProjects().get(i).name.equals(name))
				return this.getListProjects().get(i);
		}
		return null;
	}

	public LinkedList<Project> getListProjects() {
		return listProjects;
	}

	public void setListProjects(LinkedList<Project> listProjects) {
		this.listProjects = listProjects;
	}
	
	public Project getProjectByPath(String path) {
		for(Project pr:this.listProjects) {
			if(path.equals(pr.getProjectLocation())) {
				return pr;
			}
		}
		return null;
	}
}
