package application.model;

import application.controller.MainController;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;

/** This class execute the commands using the console */
public class CommandExecute {
  boolean isWindows;
  String files;
  String libraries;
  String flags;
  ProcessBuilder builder;
  String inputRun;
  MainController main;

  /**
   * In initialize the global elements to run a command
   *
   * @param main
   */
  public CommandExecute(MainController main) {
    this.isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
    this.main = main;
    this.inputRun = "";
  }

  /**
   * It generates a string to include in the command from a list of elements (files)
   *
   * @param list the list of elements
   * @return the string
   */
  public static String generateStringFromList(LinkedList<String> list) {
    String generated = "";
    for (String str : list) {
      generated = generated + str + "\n";
    }
    return generated;
  }

  /**
   * This is the input to run a program
   *
   * @param inputRun
   */
  public void setInputRun(String inputRun) {
    this.inputRun = inputRun;
  }

  /**
   * This represents other flags included
   *
   * @param otherFlag
   */
  public void concatenateFlag(String otherFlag) {
    this.flags = this.flags + " " + otherFlag;
  }

  /**
   * This includes individual flags
   *
   * @param flags
   * @return String with concatenated flag
   */
  public String individualFlags(String flags) {
    return "gcc " + flags;
  }

  /**
   * Generate a string with a space between the elements from a list
   *
   * @param list a list of elements
   * @return a string
   */
  public static String generateStringFromListSpace(LinkedList<String> list) {
    String generated = "";
    for (String str : list) {
      generated = generated + str + " ";
    }
    return generated;
  }

  /**
   * Creates the command from the project information
   *
   * @param project the project
   * @param flags the flags selected by the user
   * @param libraries the libraries added by the user
   * @return a string
   * @throws IOException
   * @throws InterruptedException
   */
  public String[] buildProject(Project project, String flags, String libraries)
      throws IOException, InterruptedException {
    LinkedList<String> flagsSc =
        new LinkedList<String>(Arrays.asList("-c", "-S", "-E", "-save-temps"));
    String sourceFiles = CommandExecute.generateStringFromListSpace(project.sourceFiles);
    String objectFiles = CommandExecute.generateStringFromListSpace(project.objectFiles);
    String finalPart = "-o " + project.projectLocation + File.separator + project.name;

    for (String flg : flagsSc) {
      if (project.allFlagsList.contains(flg)) finalPart = "";
    }

    return buildExecution(
        "gcc " + flags + sourceFiles + objectFiles + libraries + finalPart,
        project.getProjectLocation());
  }

  /**
   * It executes the build the command using the process builder class
   *
   * @param command the command
   * @return A String with the result
   * @throws IOException
   * @throws InterruptedException
   */
  public String[] buildExecution(String command, String dir)
      throws IOException, InterruptedException {
    this.builder = new ProcessBuilder();
    String[] resultReturn = new String[3];
    String result = "";
    String resultError = "";
    System.out.println(command);
    builder.directory(new File(dir));

    if (this.isWindows) {
      builder.command("cmd.exe", "/C", command.strip());
    } else {
      builder.command("bash", "-c", command.strip());
    }

    Process process = builder.start();

    StringBuilder stringBuilder = new StringBuilder();
    StringBuilder resultBuilder = new StringBuilder();
    String line = null;

    try (BufferedReader stdError =
        new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
      while ((line = stdError.readLine()) != null) {
        stringBuilder.append(line + "\n");
      }
    }

    try (BufferedReader bufferedReader =
        new BufferedReader(
            new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
      while ((line = bufferedReader.readLine()) != null) {
        resultBuilder.append(line + "\n");
      }
    }

    resultError = stringBuilder.toString();
    result = resultBuilder.toString();
    System.out.println(result);
    if (resultError.contentEquals("")) resultError = "Success\n";
    if (result.contentEquals("")) result = "No available output\n";

    int exitCode = process.waitFor();
    assert exitCode == 0;
    resultReturn[0] = command;
    resultReturn[1] = resultError;
    resultReturn[2] = result;
    return resultReturn;
  }

  /**
   * It executes the run command
   *
   * @param pr the project to run
   * @return The results from the command
   * @throws IOException
   */
  public String[] runProgram(Project pr) throws IOException {
    ProcessBuilder builderExecute = new ProcessBuilder();
    StringBuilder resultBuilderExecute = new StringBuilder();
    StringBuilder errorBuilderExecute = new StringBuilder();
    String lineExecute = "";
    String[] parameters = this.inputRun.split("\n");
    String[] resultsExecute = new String[2];
    builderExecute.directory(new File(pr.getProjectLocation()));

    if (this.isWindows) {
      builderExecute.command("cmd.exe", "-c", pr.name);
    } else {
      builderExecute.command("bash", "-c", "./" + pr.name);
    }

    Process processExecute = builderExecute.start();
    Writer bufferedWriter = null;
    for (String pra : parameters) {
      bufferedWriter = new OutputStreamWriter(processExecute.getOutputStream(), "UTF-8");
      bufferedWriter.write(pra);
      bufferedWriter.flush();
    }

    try (BufferedReader bufferedReaderExecute =
        new BufferedReader(
            new InputStreamReader(processExecute.getInputStream(), StandardCharsets.UTF_8))) {
      while ((lineExecute = bufferedReaderExecute.readLine()) != null) {
        resultBuilderExecute.append(lineExecute + "\n");
        System.out.println(lineExecute);
      }
    }

    resultsExecute[1] = resultBuilderExecute.toString();
    lineExecute = "";
    try (BufferedReader stdErrorExecute =
        new BufferedReader(new InputStreamReader(processExecute.getErrorStream()))) {
      while ((lineExecute = stdErrorExecute.readLine()) != null) {
        errorBuilderExecute.append(lineExecute + "\n");
        System.out.println(lineExecute);
      }
    }
    resultsExecute[0] = errorBuilderExecute.toString();

    Runtime.getRuntime().exec("kill -2 " + processExecute.pid());
    processExecute.destroyForcibly();

    System.out.println(resultsExecute[0]);
    System.out.println(resultsExecute[1]);
    return resultsExecute;
  }
}
