
import java.util.Random;

import javax.swing.JOptionPane;

public class Driver 
{
	
	public static void main(String[] args) {
		
		Random random = new Random();
		ProcessManager pManager = new ProcessManager();
		
		
		String[] processNames = {"iTunes", "Mail", "Chrome", "IE", "FacebookMessenger", "Eclipse", "CodeBlocks", "VS2013", "Sublime Text 3", "SourceTree", "World of Warcraft", "GIMP", "MODO 801", "MAYA", "qBittorrent", "Photoshop"};
		
		Process[] processes = new Process[processNames.length];
		
		for(int x = 0; x < processes.length; x++)
			processes[x] = new Process(processNames[x], random.nextInt(5)+1 ); 
		
		
		pManager.populateCreateQueue(processes);
		
		JOptionPane.showMessageDialog(null, "This program uses threads to simulate processes over time. \nIf it appears like the program is not running, check the Console.", "WARNING", JOptionPane.WARNING_MESSAGE);
		
		pManager.run();
		
		
 
	}
}
