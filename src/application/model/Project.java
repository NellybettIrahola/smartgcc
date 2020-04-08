package application.model;

import java.io.Serializable;
import java.util.LinkedList;

/** Class that represents a project, it is serializable so it can be saved */
public class Project implements Serializable {

  private static final long serialVersionUID = 1L;

  // Have to contain a space at the end
  LinkedList<String> sourceFiles;
  LinkedList<String> objectFiles;
  LinkedList<String> libraries;

  // flags
  String allFlags;

  // no space at the end
  String name;
  String projectLocation;

  /**
   * The class constructor
   *
   * @param name The name of the project
   * @param projectLocation The project location
   */
  public Project(String name, String projectLocation) {
    this.name = name;
    this.projectLocation = projectLocation;
    sourceFiles = new LinkedList<String>();
    objectFiles = new LinkedList<String>();
    libraries = new LinkedList<String>();
    this.allFlags = "";
  }

  /**
   * Assign other attributes to the project
   *
   * @param sourceFiles source files selected by the user
   * @param objectFiles object files selected by the user
   * @param libraries libraries included for the project
   */
  public void updateProject(
      LinkedList<String> sourceFiles,
      LinkedList<String> objectFiles,
      LinkedList<String> libraries) {
    this.allFlags = "";
    this.sourceFiles = sourceFiles;
    this.libraries = libraries;
    this.objectFiles = objectFiles;
  }

  /**
   * Getter of name of the project
   *
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * Setter of name of the project
   *
   * @param name the name of the project
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Getter of the list of source files
   *
   * @return the list of source files
   */
  public LinkedList<String> getSourceFiles() {
    return sourceFiles;
  }

  /**
   * Setter of the list of source files
   *
   * @param sourceFiles the list of source files
   */
  public void setSourceFiles(LinkedList<String> sourceFiles) {
    this.sourceFiles = sourceFiles;
  }

  /**
   * Getter of the list of object files
   *
   * @return the list of object files
   */
  public LinkedList<String> getObjectFiles() {
    return objectFiles;
  }

  /**
   * Setter of the list of object files
   *
   * @param objectFiles the list of object files
   */
  public void setObjectFiles(LinkedList<String> objectFiles) {
    this.objectFiles = objectFiles;
  }

  /**
   * Getter of the libraries
   *
   * @return the list of libraries
   */
  public LinkedList<String> getLibraries() {
    return libraries;
  }

  /**
   * Setter of the libraries
   *
   * @param libraries the list of libraries
   */
  public void setLibraries(LinkedList<String> libraries) {
    this.libraries = libraries;
  }

  /**
   * Getter of the project location (directory)
   *
   * @return the directory
   */
  public String getProjectLocation() {
    return projectLocation;
  }

  /**
   * Setter of the project location (directory)
   *
   * @param projectLocation the directory
   */
  public void setProjectLocation(String projectLocation) {
    this.projectLocation = projectLocation;
  }

  /**
   * Setter of the project's flags
   *
   * @param allFlags the flags of the project
   */
  public void setAllFlags(String allFlags) {
    this.allFlags = allFlags;
  }

  /**
   * Getter of the project's flags
   *
   * @return the project flags
   */
  public String allFlags() {
    return this.allFlags;
  }
}
