package com.banblue;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

public class FireFoxAudit {
	static String path = "";

    @SuppressWarnings("resource")
	public static void main(String[] args) {

        try {
        	final String pathPF = "C:\\Program Files\\Mozilla Firefox\\";
        	final String path86 = "C:\\Program Files (x86)\\Mozilla Firefox\\";
        	final String pathiOS = "/Applications/Firefox.app/Contents/MacOS";
        	final String firefox = "firefox.exe";
        	
    	    boolean locationPF = findFile(firefox, new File(pathPF));
    	    boolean location86 = findFile(firefox, new File(path86));
    	    boolean locationiOS = findFile(firefox, new File(pathiOS));

    	    if(location86) {
    	    	System.out.println("Firefox.exe found in " + path86);
    	    	path = path86;
    	    } else if (locationPF) {
    	    	System.out.println("Firefox.exe found in " + pathPF);
    	     	path = pathPF;
    	    } else if (locationiOS) {
    	    	System.out.println("Firefox.exe found in " + pathPF);
    	     	path = pathPF;
    	    }
    	    
    	    if(path.equals("")) {
    	    	System.out.println("##FIREFOX NOT FOUND ON SYSTEM, ABORT##");
    	    	System.exit(1);
    	    }
    	            	
        	
        	System.out.println("####FIRE FOX AUDIT AND CONFIG TOOL####");
        	System.out.println("####CONFORMS TO FIREFOX STIG v4 r9#####");
        	System.out.println("AUDIT - 1, CONFIGURE - 2, Exit - 3");
        	Scanner scanner = new Scanner(System.in); 
        	String selection = scanner. nextLine();
        	

        	switch(selection) {
        	
	        	case "1":
	        		audit();
	        		break;
	        	
	        	case "2":
	        		configure();
	        		break;
	        	
	        	case "3":
	        		break;
        	}
        	
        	scanner.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * This test to see if file exists, and if not, puts files into place
     * 
     */
    static void configure() {

    	try {
    		File mozillaFileTest = new File(path + "mozilla.cfg");
    		if(!mozillaFileTest.exists() && !mozillaFileTest.isDirectory()) { 
    			Files.copy(new File("mozilla.txt").toPath(), new File(path + "mozilla.cfg").toPath());
    		} else {
    			System.out.println("WARNING: MOZILLA.CFG EXISTS");
    			System.out.println("Replace - 1, Appened - 2, Nothing -3");
    			Scanner scanner = new Scanner(System.in); 
            	String selection = scanner.nextLine();
            	
            	switch(selection) {
            	
    	        	case "1":
    	        		//make backup of old file
    	        		Files.copy(new File(path + "mozilla.cfg").toPath(), new File(path + "mozilla.cfg.old").toPath());
    	        		Files.copy(new File("mozilla.txt").toPath(), new File(path + "mozilla.cfg").toPath());
    	        		System.out.println("Orignal Mozilla.cfg changed Mozilla.cfg.old, new Mozilla.cfg successfully installed");
    	        		break;
    	        	
    	        	case "2":
    	        		String contentToAppend = "mozilla.txt";
    	        		Files.write(Paths.get(path + "mozilla.cfg"), contentToAppend.getBytes(), StandardOpenOption.APPEND);
    	        		System.out.println("Mozilla.cfg successfully appended");
    	        		break;
    	        		
    	        	case "3":
    	        		break;
            	}//switch(selection) {
            	
            	scanner.close();
    		}//if(!mozillaFileTest.exists() && !mozillaFileTest.isDirectory()) { 
    		
    		
    		File autoconfigFileTest = new File(path + "\\defaults\\pref\\autoconfig.js");
    		if(!autoconfigFileTest.exists() && !autoconfigFileTest.isDirectory()) { 
    			Files.copy(new File("autoconfig.js").toPath(), new File(path + "\\defaults\\pref\\autoconfig.js").toPath());
    		} else {
    			System.out.println("WARNING: autoconfig.js EXISTS");
    			System.out.println("Replace - 1, Appened - 2, Nothing -3");
    			Scanner scanner = new Scanner(System.in); 
            	String selection = scanner.nextLine();
            	
            	switch(selection) {
            	
    	        	case "1":
    	        		//make backup of old file
    	        		Files.copy(new File(path + "\\defaults\\pref\\autoconfig.js").toPath(), new File(path + "autoconfig.js.old").toPath());
    	        		Files.copy(new File("autoconfig.js").toPath(), new File(path + "\\defaults\\pref\\autoconfig.js").toPath());
    	        		break;
    	        	
    	        	case "2":
    	        		String contentToAppend = "autoconfig.js";
    	        		Files.write(Paths.get(path + "autoconfig.js"), contentToAppend.getBytes(), StandardOpenOption.APPEND);
    	        		break;
    	        		
    	        	case "3":
    	        		break;
            	}//switch(selection) {
            	
            	scanner.close();
    		}//if(!autoconfigFileTest.exists() && !autoconfigFileTest.isDirectory()) { 

			System.out.println("configured");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Audits the mozilla.cfg file, assuming it's plain text
     */
    static void audit() {
    	try {
    		ArrayList<String> testfile = new ArrayList<String>();
        	ArrayList<String> configfile = new ArrayList<String>();
        	     
        	System.out.println("Directory = " + System.getProperty("user.dir"));
            System.out.print("Enter Firefox CONFIG File : ");
            
        	Scanner configInput = new Scanner(System.in);
        	File configFile = new File(configInput.nextLine());
        	configInput = new Scanner(configFile);
        	
        	//load config file
        	while (configInput.hasNextLine()) {
              String line = configInput.nextLine();
              configfile.add(line);
        	}
        	
        	System.out.print("Enter Firefox TEST File : ");
         	Scanner testinput = new Scanner(System.in);
         	File tFile = new File(testinput.nextLine());
         	testinput = new Scanner(tFile);
         	//load test file
         	while (testinput.hasNextLine()) {
               String line = testinput.nextLine();
               testfile.add(line);
         	}//while (testinput.hasNextLine()) {
            
         	//compare config file to test file
            for(int n = 0; n < configfile.size(); n++) {
            	String current = configfile.get(n);
            	if (!current.trim().equals("") && current.substring(0, 3).equals("loc")) {
	            	for(int c = 0; c < testfile.size(); c++) {
	            		//System.out.println(current + " TO " + testfile.get(c));
	            		if(current.equalsIgnoreCase(testfile.get(c))) {
	            			testfile.remove(c);
	            			break;
	            		}//f(current.equalsIgnoreCase(testfile.get(c))) {
	            	}//for(int c = 0; c < testfile.size(); c++) {
            	}//if (!current.trim().equals("") && current.substring(0, 3).equals("loc")) {
            }//for(int n = 0; n < configfile.size(); n++) {
            
            if(!testfile.isEmpty()) {
            	for(int t = 0; t < testfile.size(); t++) {
            		System.out.println(testfile.get(t));
            	}
            } else {
            	System.out.println("No missing strings");
            }//if(!testfile.isEmpty()) {
            
            configInput.close();
            testinput.close();
    	  
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }//audit
    
    /**
     * 
     * @param name
     * @param file
     * @return true if file found
     */
    static boolean findFile(String name,File file) {
        File[] list = file.listFiles();
        if(list!=null)
        for (File fil : list) {
            if (fil.isDirectory()) {
                findFile(name,fil);
            } else if (name.equalsIgnoreCase(fil.getName())) {
                return true;
            } //if (fil.isDirectory()) {
        }//for (File fil : list)
        
        return false;
    }//findFile

}
