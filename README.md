# CPU-Scheduler-Assignment
Circular Linked List simulates CPU process execution. Uses scheduling algorithms such as First Come First Serve, Round Robin, Shortest Remaining Time First.

Multilevel Feedback Queue Scheduling Assignment
Overview
One of the main tasks of an operating system is scheduling processes to run on the CPU. In this assignment, you will build a program which schedules simulated CPU processes. Your simulator program will implement the Multilevel Feedback Queue Scheduling algorithm discussed in this course for its CPU Scheduler. Therefore, the simulator obtains a process to run from the ready queue of the Multilevel Feedback Queue Scheduling algorithm. Since the assignment intends to simulate a CPU scheduler, it does not require any actual process creation or execution. When the CPU scheduler chooses the next process, the simulator will simply print out which process was selected to run at that time. The simulator output is similar to the Gantt chart style.

Design:
1. Write a class called Process which stores the ID, arrival time, and CPU burst length of a process, all are integers. You can also add data members to keep track of information in order to compute the statistics about the process such as its wait time, response time, and turnaround time. The methods of the Process class are the get and set methods for each data member, the constructor for the class, and any method needed to compute the statistics for that process.
2. Download the files Queue.java, LinkedQueue.java and Scheduler.java needed for the assignment. A Scheduler class simulates a CPU scheduler for an operating system. The Scheduler class in Scheduler.java is an abstract class and provides the base functionality for the two CPU scheduling algorithms needed for the Multilevel Feedback Queue Scheduling algorithm. The Scheduler class contains the ready queue, and the operations isEmpty, add, and remove. The isEmpty operation is already implemented in the Scheduler class. The add operation adds a process into its appropriate spot within the ready queue according to the CPU scheduling algorithm implemented. The remove operation removes a process from the ready queue according to the CPU scheduling algorithm implemented. The Scheduler class declares the operations add and remove as abstract. You cannot modify the Queue.java and LinkedQueue.java code.
3. You’ll need to implement the CPU scheduling algorithms First Come First Serve and Round Robin. Therefore, you’ll write a class for First Come First Serve CPU Scheduling which extends the Scheduler class and a class for Round Robin CPU Scheduling which extends the Scheduler class. This is a perfect use of inheritance!
4. You’ll write a class to implement the Multilevel Feedback Queue Scheduling algorithm as described in chapter 6 of the course textbook. This algorithm uses for the first level an instance of the Round Robin class whose time quantum is 4, uses for the second level an instance of the Round Robin class whose time quantum is 8, and uses for the third level an instance of the First Come First Serve class.
5. You cannot add any nested classes to any of the scheduling classes. You cannot add any additional data members to the Scheduler class. None of the scheduling classes perform input, output, computing any values for processes, and computing any statistics for processes. The only operations a scheduler does are the ones necessary to add a process to a ready queue and remove a process from a ready queue.
6. Tip: Make your program as modular as possible, not placing all your code in one .java file. You can create as many classes as you need in addition to the classes described above. Methods should be reasonably small following the guidance that "A function should do one thing, and do it well."
7. Do NOT use your own packages in your program. If you see the keyword package on the top line of any of your .java files then you created a package. Create every .java file in the src folder of your Eclipse project.
8. Do NOT use any graphical user interface code in your program!
9. Create a driver class and make the name of the driver class Assignment1 and it should only contain only one method:
public static void main(String args[]).
The main method opens the file assignment1.txt reading in the entire set of processes and initiates execution of the simulator program. Assume there is only a single processor with only one core. The main method itself should be fairly short.
10. The input to your program will be read from a plain text file called assignment1.txt. This is the statement you’ll use to open the file:
FileInputStream fstream = new FileInputStream("assignment1.txt");
Assuming you’re using Eclipse to create your project, you will store the input file assignment1.txt in the parent directory of your source code (.java files) which happens to be the main directory of your project in Eclipse. If you’re using some other development environment, you will have to figure out where to store the input file.
Each line in the file represents a process, 3 integers separated by spaces. The process information includes the process ID, arrival time, and CPU burst length. Arrival time is the time at which the scheduler receives the process and places it in the ready queue. You can assume arrival times of the processes in the input file are in non-decreasing order. Process IDs are unique. Arrival times may be duplicated, which means multiple processes may arrive at the same time. The following table is an example of a three process input file. The text in the top row of the table is just to label the value in each column and would not appear in the input file. Remember, the integers on each line are separated by a space or spaces.

Process ID  Arrival Time  CPU Burst Length
1 | 0 | 10
2 | 0 | 20
3 | 3 | 5


11. For the output, the example below best describes what your program should produce. Your program will not be tested on this sample input but a different sample input.
Here is the example input:
1 0 10
2 0 19
3 3 5
4 7 4
5 10 6
6 10 17

Here is the output produced for the above example input:
Multilevel Feedback Queue Scheduling algorithm
============================================================
<system time 0> process 1 is running
<system time 1> process 1 is running
<system time 2> process 1 is running
<system time 3> process 1 is running
<system time 4> process 2 is running
<system time 5> process 2 is running
<system time 6> process 2 is running
<system time 7> process 2 is running
<system time 8> process 3 is running
Page 4 of 7
<system time 9> process 3 is running
<system time 10> process 3 is running
<system time 11> process 3 is running
<system time 12> process 4 is running
<system time 13> process 4 is running
<system time 14> process 4 is running
<system time 15> process 4 is running
<system time 16> process 4 is finished....
<system time 16> process 5 is running
<system time 17> process 5 is running
<system time 18> process 5 is running
<system time 19> process 5 is running
<system time 20> process 6 is running
<system time 21> process 6 is running
<system time 22> process 6 is running
<system time 23> process 6 is running
<system time 24> process 1 is running
<system time 25> process 1 is running
<system time 26> process 1 is running
<system time 27> process 1 is running
<system time 28> process 1 is running
<system time 29> process 1 is running
<system time 30> process 1 is finished....
<system time 30> process 2 is running
<system time 31> process 2 is running
<system time 32> process 2 is running
<system time 33> process 2 is running
<system time 34> process 2 is running
<system time 35> process 2 is running
<system time 36> process 2 is running
<system time 37> process 2 is running
<system time 38> process 3 is running
<system time 39> process 3 is finished....
<system time 39> process 5 is running
<system time 40> process 5 is running
<system time 41> process 5 is finished....
<system time 41> process 6 is running
<system time 42> process 6 is running
<system time 43> process 6 is running
<system time 44> process 6 is running
<system time 45> process 6 is running
<system time 46> process 6 is running
<system time 47> process 6 is running
<system time 48> process 6 is running
<system time 49> process 2 is running
<system time 50> process 2 is running
<system time 51> process 2 is running
<system time 52> process 2 is running
<system time 53> process 2 is running
<system time 54> process 2 is running
<system time 55> process 2 is running
<system time 56> process 2 is finished....
<system time 56> process 6 is running
<system time 57> process 6 is running
<system time 58> process 6 is running
<system time 59> process 6 is running
<system time 60> process 6 is running
<system time 61> process 6 is finished....
<system time 61> All processes finished......
============================================================
Average CPU usage: 100.00%
Average waiting time: 25.33
Average response time: 5.00
Average turnaround time: 35.50
============================================================

Due Date: Submit this assignment by Thursday, April 12, 2018.
