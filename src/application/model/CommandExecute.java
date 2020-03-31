package application.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class CommandExecute {
	boolean isWindows;
	String files;
	String libraries;
	String flags;
	ProcessBuilder builder;
	public CommandExecute() {
			this.isWindows=System.getProperty("os.name").toLowerCase().startsWith("windows");	
	}
	
	public static String generateStringFromList(LinkedList<String> list) {
		String generated="";
		for(String str:list) {
			generated=generated+str+"\n";
		}
		return generated;
	}
	public void concatenateFlag(String otherFlag) {
		this.flags=this.flags+" "+otherFlag;
	}
	
	public String individualFlags(String flags) {
		return "gcc "+flags;
	}
	
	public static String generateStringFromListSpace(LinkedList<String> list) {
		String generated="";
		for(String str:list) {
			generated=generated+str+" ";
		}
		return generated;
	}
	
	public String[] buildProject(Project project, String flags) throws IOException, InterruptedException {
		//System.out.println("gcc "+flags+project.sourceFiles+project.objectFiles+project.libraries+"-o "+project.projectLocation+project.name);
		String sourceFiles=CommandExecute.generateStringFromListSpace(project.sourceFiles);
		String objectFiles=CommandExecute.generateStringFromListSpace(project.objectFiles);
		String libraries=CommandExecute.generateStringFromListSpace(project.libraries);
		return buildExecution("gcc "+flags+sourceFiles+objectFiles+"-lstdc++ "+libraries+"-o "+project.projectLocation+File.separator+project.name);
	}
	
	public String[] buildExecution(String command) throws IOException, InterruptedException {
		this.builder=new ProcessBuilder();
		String[] resultReturn=new String[2];
		String result="";
		System.out.println(command);
		builder.directory(new File(System.getProperty("user.home")));
		
		if(this.isWindows) {
			builder.command("cmd.exe","-c",command);
		}else {
			builder.command("bash","-c",command);
		}
		
		
		Process process = builder.start();

		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		
		try (BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()))){
			while ((line = stdError.readLine()) != null) {
				stringBuilder.append(line+"\n");
			}
		}
		
		//try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {	
		//	while ((line = bufferedReader.readLine()) != null) {
		//		stringBuilder.append(line+"\n");
		//	}
		//}
		
	 
		result=stringBuilder.toString();
		System.out.println(result);
		if(result.contentEquals(""))
			result="compilation success";
		
		int exitCode = process.waitFor();
		assert exitCode == 0;
		resultReturn[0]=command;
		resultReturn[1]=result;
		return resultReturn;
	}
	
}
