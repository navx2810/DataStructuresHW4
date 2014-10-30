import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;

import javax.swing.JOptionPane;


public class ProcessManager
{
	Random random = new Random();

	ArrayList< LinkedList<Process> > readyQueues;
	LinkedList<Process> createQueue, terminatedQueue, suspendedQueue;
	LinkedList<String> suspendedEventIDs;
	volatile boolean isRunning;
	Process currentProcess;

	MainWindow window;
	
	public ProcessManager() 
	{
		readyQueues = new ArrayList< LinkedList<Process> >();
		createQueue = new LinkedList<Process>();
		terminatedQueue = new LinkedList<Process>();
		suspendedQueue = new LinkedList<Process>();
		suspendedEventIDs = new LinkedList<String>();
		
		isRunning = true;
		
		for(int i = 0; i < 4; i++)
			readyQueues.add(new LinkedList<Process>());
	}
	
	public void populateCreateQueue(Process[] processes)
	{
		for(int i = 0; i < processes.length; i++)
			createQueue.push(processes[i]);
	}

	boolean isCreateQueueEmpty() {
		if ( createQueue.size() == 0 )
			return true;
		return false;
	}
	
	boolean isSuspendedQueueEmpty() {
		if ( suspendedQueue.size() == 0 )
			return true;
		return false;
	}
	
	boolean areReadyQueuesEmpty() {
		
		for ( LinkedList<Process> rQueue : readyQueues )
			if ( rQueue.size() > 0 )
				return false;
		return true;
	}
	
	LinkedList<Process> findLowestReadyQueue() 
	{
			LinkedList<Process> x = readyQueues.get(0);
			for(LinkedList<Process> rQueue : readyQueues)
				if ( rQueue.size() < x.size() )
					return rQueue;
			return x;
	}
	
	LinkedList<Process> findHighestReadyQueue() 
	{
		LinkedList<Process> x = readyQueues.get(0);
		for(LinkedList<Process> rQueue : readyQueues)
			if ( rQueue.size() > x.size() )
				x = rQueue;
		return x;
	}
	
	void processProcess( Process process )
	{
		int activityPercentage = random.nextInt(10);
		
		process.state = Process.States.RUNNING;
		process.run();
		
		if( activityPercentage < 2 )
			suspendProcess(process);
		else if ( activityPercentage < 6 )
			terminateProcess(process);
		else
		{
			addProcessToReadyQueue(process);
			System.out.println(String.format("\n| %s | is being pushed BACK into a ready queue (40%% Chance)\n", process));
		}
			
	}
	
	void addProcessToReadyQueue( Process process )
	{
		process.state = Process.States.CREATED;
		findLowestReadyQueue().push(process);
	}
	
	void terminateProcess( Process process )
	{
		process.state = Process.States.TERMINATED;
		process.terminated = LocalTime.now();
		terminatedQueue.push(process);
		
		System.out.println(String.format("\n| %s | is being TERMINATED (40%% Chance)\n", process));
	}
	
	void suspendProcess( Process process )
	{
		process.PID = UUID.randomUUID().toString();
		process.state = Process.States.SUSPENDED;
		suspendedEventIDs.push(process.PID);
		suspendedQueue.push(process);
		
		System.out.println(String.format("\n| %s | is being SUSPENDED (20%% Chance)\n", process));
	}
		
	Process unsuspendRandomProcess() 
	{
		int randomPopPos = random.nextInt(suspendedQueue.size());
		Process p = suspendedQueue.remove(randomPopPos);
		suspendedEventIDs.remove(randomPopPos);
		p.state = Process.States.RESUMED;
		
		System.out.println(String.format("\n| %s | is being RESUMED\n", p));
		
		return p;
	}
	
	public void run() 
	{
		for(int x = 0; x < createQueue.size(); x++)
			findLowestReadyQueue().push(createQueue.poll());
	
		window = new MainWindow(this);
		
		while( isRunning )
			if( !areReadyQueuesEmpty() )
			{
	
			if( currentProcess == null )
				currentProcess = findHighestReadyQueue().poll();
			
			window.resetWindow(this);
			processProcess(currentProcess);
			
			currentProcess = null;
			
			} else if ( !isSuspendedQueueEmpty() )
			{
				findLowestReadyQueue().push(unsuspendRandomProcess());
			}
			else
				isRunning = false;	
		
		JOptionPane.showMessageDialog(null, "Simulation Complete");
	}
	
	public String[] getReadyQueues(int queue)
	{
		LinkedList<Process> q = readyQueues.get(queue);
		String[] s = new String[q.size()];
		
		for(int x = 0; x < q.size(); x++)
			s[x] = q.get(x).toString(); 
		
		return s;
	}
	
	public String[] getActive() 
	{
		String[] s = new String[1];
		s[0] = currentProcess.toString();
		
		return s;
	}
	
	public String[] getSuspendedQueue()
	{
		String[] s = new String[suspendedQueue.size()];
		
		for(int x = 0; x < suspendedQueue.size(); x++)
			s[x] = suspendedQueue.get(x).toString();
		
		return s;
	}
	
	public String[] getTerminatedQueue()
	{
		String[] s = new String[terminatedQueue.size()];
		
		for(int x = 0; x < terminatedQueue.size(); x++)
			s[x] = terminatedQueue.get(x).toString();
		
		return s;
	}
	
	public String[] getEventIDs()
	{
		String[] s = new String[suspendedEventIDs.size()];
		
		for(int x = 0; x < suspendedEventIDs.size(); x++)
			s[x] = suspendedEventIDs.get(x); 
		
		return s;
	}
	
	public void UNIT_TEST()
	{
		for(int x = 0; x < createQueue.size(); x++)
			findLowestReadyQueue().push(createQueue.poll());
	
		window = new MainWindow(this);
		
		System.out.println("PROCESS DONE");
		
		while( isRunning )
			if( !areReadyQueuesEmpty() )
			{
	
			if( currentProcess == null )
				currentProcess = findHighestReadyQueue().poll();
			
			window.resetWindow(this);
			processProcess(currentProcess);
			
			currentProcess = null;
			
			} else if ( !isSuspendedQueueEmpty() )
			{
				findLowestReadyQueue().push(unsuspendRandomProcess());
			}
			else
				isRunning = false;
			
		
		System.out.println(areReadyQueuesEmpty());
		System.out.println(readyQueues);
		System.out.println(suspendedQueue);
		System.out.println("I finished");
			
	}
	
}
