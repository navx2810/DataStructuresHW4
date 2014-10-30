import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;


@SuppressWarnings("serial")
public class MainWindow extends JFrame 
{

	JList<String> rq1, rq2, rq3, rq4, active, suspendedQ, terminateQ, eventIDQ;
	DefaultListModel<String> rq1Model, rq2Model, rq3Model, rq4Model, activeModel, suspendedModel, terminateModel, eventIDModel;
	
	JPanel top, bottom;
	
	public MainWindow(ProcessManager pManager)
	{
		super("Process Simulation");
		setSize(600,600);
		setLayout(new GridLayout(2,1));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		top = new JPanel(new GridLayout(1, 4));
		bottom = new JPanel(new GridLayout(1, 4));
		
		rq1Model = new DefaultListModel<String>();		
		rq2Model = new DefaultListModel<String>();
		rq3Model = new DefaultListModel<String>();	
		rq4Model = new DefaultListModel<String>();		
		activeModel = new DefaultListModel<String>();
		suspendedModel = new DefaultListModel<String>();
		terminateModel = new DefaultListModel<String>();
		eventIDModel = new DefaultListModel<String>();
	
		
		rq1 = new JList<String>(rq1Model);
		rq2 = new JList<String>(rq2Model);
		rq3 = new JList<String>(rq3Model);
		rq4 = new JList<String>(rq4Model);
		
		active = new JList<String>(activeModel);
		suspendedQ = new JList<String>(suspendedModel);
		terminateQ = new JList<String>(terminateModel);
		eventIDQ = new JList<String>(eventIDModel);
		
		rq1.setBorder(new TitledBorder("Ready Queue 1"));
		rq2.setBorder(new TitledBorder("Ready Queue 2"));
		rq3.setBorder(new TitledBorder("Ready Queue 3"));
		rq4.setBorder(new TitledBorder("Ready Queue 4"));
		
		active.setBorder(new TitledBorder("Active Process"));
		suspendedQ.setBorder(new TitledBorder("Suspended Queue"));
		terminateQ.setBorder(new TitledBorder("Terminated Queue"));
		eventIDQ.setBorder(new TitledBorder("Event IDs"));
		
//		add(rq1);
//		add(rq2);
//		add(rq3);
//		add(rq4);
		
		
		top.add(rq1);
		top.add(rq2);
		top.add(rq3);
		top.add(rq4);
		
		bottom.add(active);
		bottom.add(suspendedQ);
		bottom.add(terminateQ);
		bottom.add(eventIDQ);
		
		add(top);
		add(bottom);
		
		setVisible(true);
				
	}
	
	public void resetWindow(ProcessManager pManager)
	{
		rq1Model.clear();
		for(String s : pManager.getReadyQueues(0))
			rq1Model.addElement(s);
		
		rq2Model.clear();
		for(String s : pManager.getReadyQueues(1))
			rq2Model.addElement(s);
		
		rq3Model.clear();
		for(String s : pManager.getReadyQueues(2))
			rq3Model.addElement(s);
		
		rq4Model.clear();
		for(String s : pManager.getReadyQueues(3))
			rq4Model.addElement(s);
		
		activeModel.clear();
		activeModel.addElement(pManager.getActive()[0]);
		
		suspendedModel.clear();
		for(String s : pManager.getSuspendedQueue())
			suspendedModel.addElement(s);
		
		terminateModel.clear();
		for(String s : pManager.getTerminatedQueue())
			terminateModel.addElement(s);
		
		eventIDModel.clear();
		for(String s : pManager.getEventIDs())
			eventIDModel.addElement(s);
		
	}
	
}
