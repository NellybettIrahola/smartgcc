package application.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class CommandExecute {
	boolean isWindows;
	String files;
	String libraries;
	String flags;
	ProcessBuilder builder;
	public CommandExecute() {
			this.isWindows=System.getProperty("os.name").toLowerCase().startsWith("windows");	
	}
	
	public void concatenateFlag(String otherFlag) {
		this.flags=this.flags+" "+otherFlag;
	}
	
	public void compileExecution() throws IOException, InterruptedException {
		this.builder=new ProcessBuilder();
		String result="";
	
		
		if(this.isWindows) {
			builder.command("cmd.exe","-c","gcc --version");
		}else {
			builder.command("bash","-c","gcc --version");
		}
		
		builder.directory(new File(System.getProperty("user.home")));
		Process process = builder.start();

		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {	
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line+"\n");
			}
		}
	 
		result=stringBuilder.toString();
		System.out.println(result);
		
		int exitCode = process.waitFor();
		assert exitCode == 0;
		
	}
	
}
