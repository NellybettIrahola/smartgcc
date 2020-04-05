package application.model;

import java.io.Serializable;
import java.util.LinkedList;

public class Project implements Serializable {

  /** */
  private static final long serialVersionUID = 1L;

  // Have to contain a space at the end
  LinkedList<String> sourceFiles;
  LinkedList<String> objectFiles;
  LinkedList<String> libraries;

  // flags
  String allFlags;
  LinkedList<String> debugFlags;

  // no space at the end
  String name;
  String projectLocation;

  public Project(String name, String projectLocation) {
    this.name = name;
    this.projectLocation = projectLocation;
    sourceFiles = new LinkedList<String>();
    objectFiles = new LinkedList<String>();
    libraries = new LinkedList<String>();
    this.allFlags = "";
  }

  public void updateProject(
      LinkedList<String> sourceFiles,
      LinkedList<String> objectFiles,
      LinkedList<String> libraries) {
    this.allFlags = "";
    this.sourceFiles = sourceFiles;
    this.libraries = libraries;
    this.objectFiles = objectFiles;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LinkedList<String> getSourceFiles() {
    return sourceFiles;
  }

  public void setSourceFiles(LinkedList<String> sourceFiles) {
    this.sourceFiles = sourceFiles;
  }

  public LinkedList<String> getObjectFiles() {
    return objectFiles;
  }

  public void setObjectFiles(LinkedList<String> objectFiles) {
    this.objectFiles = objectFiles;
  }

  public LinkedList<String> getLibraries() {
    return libraries;
  }

  public void setLibraries(LinkedList<String> libraries) {
    this.libraries = libraries;
  }

  public String getProjectLocation() {
    return projectLocation;
  }

  public void setProjectLocation(String projectLocation) {
    this.projectLocation = projectLocation;
  }

  public void setAllFlags(String allFlags) {
    this.allFlags = allFlags;
  }

  public String allFlags() {
    return this.allFlags;
  }

  public void setDebugFlags(LinkedList<String> debugFlags) {
    this.debugFlags = debugFlags;
  }

  public LinkedList<String> getDebugFlags() {
    return this.debugFlags;
  }
}
