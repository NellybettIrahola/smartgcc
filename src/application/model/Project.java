package application.model;

public class Project {
	
	//Have to contain a space at the end
	String sourceFiles;
	String objectFiles;
	String libraries;
	
	//no space at the end
	String name;
	String projectLocation;
	
	public Project(String name, String sourceFiles, String objectFiles, String libraries,String projectLocation) {
		this.name=name;
		this.sourceFiles=sourceFiles;
		this.libraries=libraries;
		this.objectFiles=objectFiles;
		this.projectLocation=projectLocation;
	}
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSourceFiles() {
		return sourceFiles;
	}



	public void setSourceFiles(String sourceFiles) {
		this.sourceFiles = sourceFiles;
	}



	public String getObjectFiles() {
		return objectFiles;
	}



	public void setObjectFiles(String objectFiles) {
		this.objectFiles = objectFiles;
	}



	public String getLibraries() {
		return libraries;
	}



	public void setLibraries(String libraries) {
		this.libraries = libraries;
	}



	public String getProjectLocation() {
		return projectLocation;
	}



	public void setProjectLocation(String projectLocation) {
		this.projectLocation = projectLocation;
	}

}
