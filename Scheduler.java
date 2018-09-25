import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Scheduler {
	private CircularLinkedList<Process> readyQueue = new CircularLinkedList<Process>();
	private File file;

	Scheduler(File inputFile) {

		file = inputFile;

	}

	public void createReadyQueue() {
		try{
			Scanner sc = new Scanner(file); //creates Scanner object to read file

    		while (sc.hasNextInt()) 
    		{
    			int id = sc.nextInt();
    			int arrivalTime = sc.nextInt();
    			int cpuBurst = sc.nextInt();
		
    			Process process = new Process(id, arrivalTime, cpuBurst);
    			readyQueue.add(process);
		
    		}
    		
    		sc.close();
		}
		catch (FileNotFoundException e){
			System.out.println("File Not Found! Either you misspelled it, or didn't put it in the correct place.");
		}
		

	}

	public void SRTF() 
	{
		sortQueue(); //sorts queue based on arrival time
		
		int time=0;
		int count =0; //this is used to count unique starting times to compute response time later
		Process process; // Process object

		int size = readyQueue.size();
		float turnaroundTime=0;
		float waitTime = 0; 
		float responseTime = 0;
		
		float cpuBurstTotal = 0; //total cpu burst time
		float arrivalTimeTotal=0; //total arrival time

		int[] idArray = new int[size]; //this is used to help with determining response time
		
		
		
		//this loop will help get the total cpu burst time, total arrival time, and make an array for unique process start times
		for(int i=0;i<size;i++)
		{				
			cpuBurstTotal += readyQueue.get(i).getCpuBurst();
			arrivalTimeTotal += readyQueue.get(i).getArrivalTime();
			idArray[i]= i+1;
		}

		System.out.println("Scheduling algorithm: Smallest Remaining Time First)");
		System.out.println("============================================================");
		
		int position =0;
		int minCpuBurstTime;
		
		while (!(readyQueue.isEmpty())) 
		{	minCpuBurstTime=999999999; //resets the minimum cpuBurst. A large number like this makes sure the next thing will be lower. 
			
			//This picks which Process from the readyQueue will be used
			for(int i=0;i<readyQueue.size();i++)
			{
				if(readyQueue.get(i).getArrivalTime()<=time)// checks if process has arrived
				{
					if(readyQueue.get(i).getCpuBurst()<minCpuBurstTime)// checks if process has 
					{
						position = i;
						minCpuBurstTime=readyQueue.get(i).getCpuBurst();
					}
				}
			}
			
			process=readyQueue.get(position);
			
			//used to determine response time later
			if(count<size)
			{
				for(int i=0;i<size;i++)
				{
					if(idArray[i]==process.getId())
					{
						responseTime+=time;
						count++;
						idArray[i]=-1;
					}
				}							
			}
			
			System.out.println("<system time    " + time + "> " + "process    " + process.getId() + " is running    ");
			process.setCpuBurst(process.getCpuBurst()-1);
			
			time++;
			
			if(process.getCpuBurst()==0)
			{
				System.out.println("<system time    " + time + "> " + "process    " + process.getId() + " finished....");
				turnaroundTime += (time - process.getArrivalTime());
				waitTime += (time - process.getArrivalTime());
				
				readyQueue.remove(position);
			}
			
		}
		
		System.out.println("<system time    " + time + "> All processes finished......");
		
		waitTime = (turnaroundTime - cpuBurstTotal)/size;
		responseTime= (responseTime- arrivalTimeTotal)/size;
		turnaroundTime = (turnaroundTime)/size;
		
		printStatistics(waitTime, responseTime, turnaroundTime);
		
	}

	public void FCFS() {
		
		sortQueue(); //sorts queue based on arrival time
		
		int time = 0;
		Process process;
		
		int size = readyQueue.size();
		float turnaroundTime=0;
		float waitTime = 0; 
		float responseTime = 0;//in FCFS, wait time = response time
		
		System.out.println("Scheduling algorithm: First Come First Serve");
		System.out.println("============================================================");
		
		while (!(readyQueue.isEmpty())) 
		{
			process = readyQueue.remove(0);
			for (int i = 0; i < process.getCpuBurst(); i++) 
			{
				System.out.println("<system time    " + time + "> " + "process    " + process.getId() + " is running");
				time++;
			}
			System.out.println("<system time    " + time + "> " + "process    " + process.getId() + " finished....");
			turnaroundTime += time - process.getArrivalTime();
			
			if( (process.getId()>1) && (process.getId()<size) )
				waitTime += time - process.getArrivalTime();
			
		}
		System.out.println("<system time    " + time + "> All processes finished......");
		
		waitTime = waitTime/size;
		responseTime = waitTime;
		turnaroundTime = turnaroundTime/size;
		
		printStatistics(waitTime, responseTime, turnaroundTime);
		
	}

	public void RR(int quantum) {
		
		sortQueue(); //sorts queue based on arrival time
		
		int q=1; //represents quantum
		int time=0;
		int count =0; //this is used to count unique starting times to compute response time later
		Process process; // Process object

		int size = readyQueue.size();
		float turnaroundTime=0;
		float waitTime = 0; 
		float responseTime = 0;
		
		float cpuBurstTotal = 0; //total cpu burst time
		float arrivalTimeTotal=0; //total arrival time

		int[] idArray = new int[size]; //this is used to help with determining response time
		
		boolean justRemoved = false;// this is used to know if we just removed from the readyQueue. If we did, then we wont rotate
		
		//this loop will help get the total cpu burst time, total arrival time, and make an array for unique process start times
		for(int i=0;i<size;i++)
		{				
			cpuBurstTotal += readyQueue.get(i).getCpuBurst();
			arrivalTimeTotal += readyQueue.get(i).getArrivalTime();
			idArray[i]= i+1;
		}

		System.out.println("Scheduling algorithm: Round Robin (Time Quantum = " + quantum + ")");
		System.out.println("============================================================");
				
		while(!(readyQueue.isEmpty()))
		{
			//time quantum rotation and check.
			if(q>quantum && readyQueue.size()>1)
				{
					if(!justRemoved)
						{readyQueue.rotate();}
					q=1;
					if(count<size-1)
					{
						for(int i=0;i<size;i++)
						{
							if(idArray[i]==readyQueue.get(0).getId())
							{
								responseTime+=time;
								count++;
								idArray[i]=-1;
							}
						}							
					}
				}
			
			//assigns process
			justRemoved = false;
			process=readyQueue.get(0);
			
			System.out.println("<system time    " + time + "> " + "process    " + process.getId() + " is running    ");
			process.setCpuBurst(process.getCpuBurst()-1);
			
			time++;
			
			//if the cpu burst becomes 0, we can remove that process.
			if(process.getCpuBurst()==0)
			{
				System.out.println("<system time    " + time + "> " + "process    " + process.getId() + " finished....");
				turnaroundTime += (time - process.getArrivalTime());
				waitTime += (time - process.getArrivalTime());
				
				readyQueue.remove(0);
				justRemoved = true;
			}
			
			
			q++; //increment integer to help with time quantum
		}

		System.out.println("<system time    " + time + "> All processes finished......");
		
		waitTime = (turnaroundTime - cpuBurstTotal)/size;
		responseTime= (responseTime- arrivalTimeTotal)/size;
		turnaroundTime = (turnaroundTime)/size;

		printStatistics(waitTime, responseTime, turnaroundTime);		
	}

	//Used to print the statistics. Needs waitTime, responseTime, and turnaroundTime
	public void printStatistics(float waitTime, float responseTime, float turnaroundTime)
	{
		float averageUsage = responseTime/waitTime * 100;
		System.out.println("============================================================");
		System.out.printf("Average CPU usage:        " + "%.2f",averageUsage);
		System.out.print("%");
		System.out.printf("\nAverage waiting time:      " + "%.2f",waitTime); 
		System.out.printf("\nAverage response time:     " + "%.2f",responseTime); 
		System.out.printf("\nAverage turnaround time:   " + "%.2f",turnaroundTime); 
		System.out.println("\n============================================================");
		
	}

	//sorts readyQueue based on arrival time. 
	private void sortQueue()
	{
		CircularLinkedList<Process> newReadyQueue = new CircularLinkedList<Process>();
		Process process = readyQueue.get(0);
		int largestTime = -1;
		int id = readyQueue.size()+1;
		int position =0;
		
		while(!(readyQueue.isEmpty()))
		{
			for(int i=0;i<readyQueue.size();i++)
			{
				process=readyQueue.get(i);
				if( (process.getArrivalTime()>largestTime))
					{
						largestTime= process.getArrivalTime();
						id=process.getId();
						position = i;
					}
				else if((process.getArrivalTime()==largestTime)&&(process.getId()>id))
					{
						largestTime= process.getArrivalTime();
						id=process.getId();
						position = i;
				}
			}
			
			newReadyQueue.add(0,process);
			readyQueue.remove(position);
	        
			largestTime = -1;
			id = readyQueue.size()+1;
			position =0;
		}
		
		readyQueue = newReadyQueue;
	}
	
}
