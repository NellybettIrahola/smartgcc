package application.model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The model of the application, it interacts with the controller and other classes of the model. It
 * has the list of projects.
 */
public class SmartModel {
  private LinkedList<Project> listProjects;

  /** The constructor */
  public SmartModel() {
    this.setListProjects(new LinkedList<Project>());
  }

  /**
   * It calls the Project class to create a project and add it to the list of projects
   *
   * @param name Name of the project
   * @param sourceFiles Source files associated with the project
   * @param objectFiles Object files associated with the project
   * @param libraries Libraries associated with the project
   * @param projectDirectory The project directory
   * @return 0 success
   */
  public int createProject(
      String name,
      LinkedList<String> sourceFiles,
      LinkedList<String> objectFiles,
      LinkedList<String> libraries,
      String projectDirectory) {
    Project pr = new Project(name, projectDirectory);
    pr.updateProject(sourceFiles, objectFiles, libraries);
    return this.addProject(pr);
  }

  /**
   * Updates the information of a project
   *
   * @param name name of a project
   * @param source source files of the project
   * @param object object files of the project
   * @return the project
   */
  public Project updateProject(String name, LinkedList<String> source, LinkedList<String> object) {
    Project pr = null;
    for (int i = 0; i < this.getListProjects().size(); i++) {
      if (this.getListProjects().get(i).name.equals(name)) {
        pr = this.getListProjects().get(i);
        pr.updateProject(source, object, null);
      }
    }
    return pr;
  }

  /**
   * Adds a project to the list, it verifies if it already exists
   *
   * @param pr the project
   * @return 0 success, -1 error
   */
  public int addProject(Project pr) {

    if (this.getListProjects() != null) {
      List<Project> filteredPr =
          this.getListProjects().stream()
              .filter(proj -> proj.name.equals(pr.name))
              .collect(Collectors.toList());
      if (filteredPr.size() != 0) return -1;
      else this.getListProjects().add(pr);
    }
    return 0;
  }

  /**
   * Deletes a project from the list
   *
   * @param name the name of the project
   */
  public void deleteProject(String name) {
    Project pr = null;
    for (int i = 0; i < this.getListProjects().size(); i++) {
      if (this.getListProjects().get(i).name.equals(name)) pr = this.getListProjects().get(i);
    }
    this.getListProjects().remove(pr);
  }

  /**
   * Gets a project from the list of projects
   *
   * @param name the name of the project
   * @return the project
   */
  public Project getProject(String name) {
    for (int i = 0; i < this.getListProjects().size(); i++) {
      if (this.getListProjects().get(i).name.equals(name)) return this.getListProjects().get(i);
    }
    return null;
  }

  /**
   * Getter for the list of projects
   *
   * @return the list of projects
   */
  public LinkedList<Project> getListProjects() {
    return listProjects;
  }

  /**
   * Setter for the list of projects
   *
   * @param listProjects the list of projects
   */
  public void setListProjects(LinkedList<Project> listProjects) {
    this.listProjects = listProjects;
  }

  /**
   * Gets a project by its directory
   *
   * @param path the directory
   * @return the project
   */
  public Project getProjectByPath(String path) {
    for (Project pr : this.listProjects) {
      if (path.equals(pr.getProjectLocation())) {
        return pr;
      }
    }
    return null;
  }
}
