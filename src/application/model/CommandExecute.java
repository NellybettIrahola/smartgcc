package application.model;

import application.controller.MainController;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class CommandExecute {
  boolean isWindows;
  String files;
  String libraries;
  String flags;
  ProcessBuilder builder;
  String inputRun;
  MainController main;

  public CommandExecute(MainController main) {
    this.isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
    this.main = main;
    this.inputRun = "";
  }

  public static String generateStringFromList(LinkedList<String> list) {
    String generated = "";
    for (String str : list) {
      generated = generated + str + "\n";
    }
    return generated;
  }

  public void setInputRun(String inputRun) {
    this.inputRun = inputRun;
  }

  public void concatenateFlag(String otherFlag) {
    this.flags = this.flags + " " + otherFlag;
  }

  public String individualFlags(String flags) {
    return "gcc " + flags;
  }

  public static String generateStringFromListSpace(LinkedList<String> list) {
    String generated = "";
    for (String str : list) {
      generated = generated + str + " ";
    }
    return generated;
  }

  public String[] buildProject(Project project, String flags, String libraries)
      throws IOException, InterruptedException {
    // System.out.println("gcc "+flags+project.sourceFiles+project.objectFiles+project.libraries+"-o
    // "+project.projectLocation+project.name);
    String sourceFiles = CommandExecute.generateStringFromListSpace(project.sourceFiles);
    String objectFiles = CommandExecute.generateStringFromListSpace(project.objectFiles);
    // String libraries = CommandExecute.generateStringFromListSpace(project.libraries);
    return buildExecution(
        "gcc "
            + flags
            + sourceFiles
            + objectFiles
            + libraries
            + "-o "
            + project.projectLocation
            + File.separator
            + project.name);
  }

  public String[] buildExecution(String command) throws IOException, InterruptedException {
    this.builder = new ProcessBuilder();
    String[] resultReturn = new String[3];
    String result = "";
    String resultError = "";
    System.out.println(command);
    builder.directory(new File(System.getProperty("user.home")));

    if (this.isWindows) {
      builder.command("cmd.exe", "/C", command);
    } else {
      builder.command("bash", "-c", command);
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
    if (resultError.contentEquals("")) resultError = "compilation success\n";
    if (result.contentEquals("")) result = "No available output\n";

    int exitCode = process.waitFor();
    assert exitCode == 0;
    resultReturn[0] = command;
    resultReturn[1] = resultError;
    resultReturn[2] = result;
    return resultReturn;
  }

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
