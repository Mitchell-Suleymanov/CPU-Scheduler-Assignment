/**
 * Mitchell Suleymanov
 * CSCI 340
 * Assignment2.java
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner; 



public class Assignment2 {

	public static void main(String[] args) throws FileNotFoundException {
		int quantum = 0;
		String line = "";
		
		Scanner reader = new Scanner(System.in);  
    	System.out.println("Enter the name of the file. (assignment2.txt). Also make sure you put the file in the right place!");
    	String fileName = reader.nextLine();

    	File file = new File(fileName); //FIX ABOVE
    	Scheduler scheduler = new Scheduler(file);

    	while(!line.equals("END"))
    	{
    		System.out.println("Enter the name of the CPU Scheduling Process you want to see.");
    		System.out.println("Options (remove '' marks): 'FCFS 'SRTF' 'RR (Number for Quantum)' 'END'");
    		
    		line = reader.nextLine();
    		
    		if(line.equals("END")) {break;}
    		
    		if(line.equals("FCFS"))// Create FCFS
    		{
    	    	scheduler.createReadyQueue();
    	    	scheduler.FCFS();
    		}
    		else if(line.equals("SRTF"))// Create SRTF
    		{
    	    	scheduler.createReadyQueue();
    	    	scheduler.SRTF();
    		}	
    		else if(line.contains("RR ")) // Create RR (Time Quantum)
    		{
    	    	quantum = Integer.parseInt(line.substring(3));
    	    	scheduler.createReadyQueue();
    	    	scheduler.RR(quantum);
    		}
    		
    	}    	
		
    	reader.close();
	}
}
