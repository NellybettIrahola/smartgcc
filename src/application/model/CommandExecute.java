package application.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class CommandExecute {
	boolean isWindows;
	String files;
	String libraries;
	String flags;
	ProcessBuilder builder;
	ProcessBuilder buildRun;
	String inputRun;
	
	public CommandExecute() {
			this.isWindows=System.getProperty("os.name").toLowerCase().startsWith("windows");	
			this.inputRun="";
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
		this.builder=new ProcessBuilder();
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
	
	public static boolean isAlive(Process p) {
	    try {
	      p.exitValue();
	      return false;
	    }
	    catch (IllegalThreadStateException e) {
	      return true;
	    }
	  }

	public void runProgram(Project pr) throws IOException {
		ProcessBuilder builder = new ProcessBuilder();
	    builder.redirectErrorStream(true); // so we can ignore the error stream
	    builder.directory(new File(pr.getProjectLocation()));
		
	    if(this.isWindows) {
			builder.command("cmd.exe","-c",pr.name);
		}else {
			builder.command("bash","-c","./"+pr.name);
		}
	    Process process = builder.start();
	    InputStream out = process.getInputStream();
	    OutputStream in = process.getOutputStream();

	    byte[] buffer = new byte[4000];
	    while (isAlive(process)) {
	      int no = out.available();
	      if (no > 0) {
	        int n = out.read(buffer, 0, Math.min(no, buffer.length));
	        System.out.println(new String(buffer, 0, n));
	      }

	      if (!this.inputRun.contentEquals("")) {
	    	  byte[] b = this.inputRun.getBytes(Charset.forName("UTF-8"));
	    	  in.write(b,0,this.inputRun.length());
	    	  in.flush();
	      }

	      try {
	        Thread.sleep(10);
	      }
	      catch (InterruptedException e) {
	      }
	    }

	    System.out.println(process.exitValue());
	  }
	

}
