package application.model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SmartModel {
	LinkedList<Project> listProjects;
	
	
	public SmartModel() {
		this.listProjects=new LinkedList<Project>();
	}
	
	public int addProject(Project pr) {
		
		if(this.listProjects!=null) {
			List<Project> filteredPr=this.listProjects.stream().filter(proj-> proj.name.equals(pr.name)).collect(Collectors.toList());
			if(filteredPr.size()!=0)
				return -1;
			else
				this.listProjects.add(pr);
		}
		return 0;
	}
	
	public void deleteProject(String name) {
		for(int i=0;i<this.listProjects.size();i++) {
			if(this.listProjects.get(i).name.equals(name))
				this.listProjects.remove(i);
		}
	}
	
	public Project getProject(String name) {
		for(int i=0;i<this.listProjects.size();i++) {
			if(this.listProjects.get(i).name.equals(name))
				return this.listProjects.get(i);
		}
		return null;
	}
}
