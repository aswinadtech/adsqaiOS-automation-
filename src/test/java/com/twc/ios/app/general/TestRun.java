package com.twc.ios.app.general;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;

public class TestRun {

	 //To start appium call this function 
    public static void startAppium() throws Exception {
          String port = getPort();
          String chromePort = getPort();
          String bootstrapPort = getPort();
          String command = "/usr/local/bin/appium --session-override -p " + port + " --chromedriver-port " + chromePort + " -bp "
            + bootstrapPort;
         System.out.println(command);
         String output = runCommand(command); //run command on terminal
         System.out.println(output);
 }

// This function will run command on terminal
public static String runCommand(String command) throws InterruptedException, IOException
{
    Process p = Runtime.getRuntime().exec(command);

    BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));

    String line="";
    String allLine="";
    while((line=r.readLine()) != null){
        allLine=allLine+""+line+"\n";
        System.out.println("lines :"+ allLine.toString());
        if(line.contains("started on"))
        break;
    }
    return allLine;
    
}

//This will check for free ports 
public static String getPort() throws Exception
{
    ServerSocket socket = new ServerSocket(0);
    socket.setReuseAddress(true);
    String port = Integer.toString(socket.getLocalPort()); 
    socket.close();
    return port;
}
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		startAppium();
	}

}
