import java.time.LocalTime;
import java.util.ArrayList;


public class Process extends Thread {
	
	class Event {} // Event is never touched on in directions. No clue on how to use it
	
	public String name;
	public String PID = null;
	public int cycleTime;
	public States state = States.CREATED;
	public LocalTime created, terminated;
	public ArrayList<Event> eventList;
	
	public static enum States { CREATED, RUNNING, SUSPENDED, TERMINATED, RESUMED }
	
	public Process(String name, int cycleTime) 
	{
		this.name = name;
		this.cycleTime = cycleTime;
		created = LocalTime.now();

	}



	public void run(){
		
		this.state = States.RUNNING;
		System.out.println(this);
		
		for(int time = 0; time < this.cycleTime; time++)
			try {
				System.out.println(String.format("%s %s secs", this, time+1));
				Thread.sleep(1000);
				
			} catch (Exception e){}
	}

	@Override
	public String toString() {
		return String.format("[%s] : %s", name, state);
	}
	

}
