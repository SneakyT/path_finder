
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

//This class is used to display and control the main application

public class Main extends java.applet.Applet implements ItemListener,ActionListener
{

	private static final long serialVersionUID = 1L;
	CheckboxGroup group;
    Checkbox blocks,start,goal,person;
    Choice MapChoice = new Choice();
    Button aStar,clear,crowdsimulator, builder, dijkstra, Send,robotslow,robotfast,searchslow,searchfast, update, setupserv,info,noinfo;
	//volatile Person personCell;
	JTextArea robotspeed, Aspeed, path,no;
	int speednum=5;
	int aSpeed=20;
	int dspeed=20;
	ArrayList<?> output;
	JScrollPane scroll;

	public void init()
	{
		
		setLayout(new BorderLayout(0,0));
		//setSize(1000,600);
		setBackground(Color.WHITE);

		
		
		Panel top = new Panel(); // panel for title
		top.add(new Label("Use The Grid To Find The Shortest Possible Route To A Goal")); // prints label to top panel
		Panel left = new Panel();// panel for speed buttons, server button and print zone
		robotspeed = new JTextArea("Robot Speed: 5");
		//speed.setSize(1, 1);
		robotspeed.setSize(100, 100);
		Aspeed = new JTextArea("Search Speed: 20");
		//speed.setSize(1, 1);
		Aspeed.setSize(100, 100);
		path = new JTextArea("test");
		//speed.setSize(1, 1);
		path.setSize(100, 1100);
		path.setLineWrap(true);
		path.setEditable(false);
		no = new JTextArea();
		//speed.setSize(1, 1);
		no.setSize(100, 100);
		no.setEditable(false);
		
		
		
		Panel speedbutton = new Panel();
		speedbutton.setLayout(new GridLayout(2,2,20,10));
		Panel Abutton = new Panel();
		Abutton.setLayout(new GridLayout(2,2,20,10));
		Panel path2 = new Panel();
		path2.setLayout(new GridLayout(2,1,20,10));
		Panel infop = new Panel();
		infop.setLayout(new GridLayout(2,2,20,10));

		add(top,"North");
		add(left,"West");
		left.setLayout(new GridLayout(5,2));
        Panel p = new Panel();
        p.setLayout(new GridLayout(4,1));
        Panel b = new Panel();
        b.setLayout(new GridLayout(2,1));

        
        group = new CheckboxGroup();
        blocks = new Checkbox("Add Blocks",group,true);
        goal = new Checkbox("Set Robot Start",group,false);
        person = new Checkbox("Set Person Start",group,false);
        start = new Checkbox("Set Goal node",group,false);
        blocks.addItemListener(this);
        start.addItemListener(this);
        goal.addItemListener(this);
        person.addItemListener(this);
        b.add(blocks);       
        b.add(person);

        
        p.add(b);
        p.add(start);
        p.add(goal);
        add(p,"East");
        Panel g = new Panel();
        
        g.setLayout(new GridLayout(3,1,2,10));
        
        aStar = new Button("A Star");
        clear = new Button("Clear");
        crowdsimulator = new Button("Move People");
        builder = new Button("Map Builder");
        dijkstra = new Button("Dijkstra");
        Send = new Button("Send Path");
        robotslow = new Button("Reduce Robot Speed");
        robotfast = new Button("Increase Robot Speed");
        searchslow = new Button("Reduce \nSearch Speed");
        searchfast = new Button("Increase \nSearch Speed");
        update = new Button("Print Path below");
        setupserv = new Button("Set up the Server");
        info = new Button("Add Map Detail");
        noinfo = new Button("Remove Map Detail");


        
        g.add(aStar);
        g.add(dijkstra);
        g.add(clear);
        g.add(crowdsimulator);
        g.add(builder);
        g.add(Send);
        
        speedbutton.add(robotslow); 
        speedbutton.add(robotfast);
        speedbutton.add(robotspeed);
        left.add(speedbutton);
        
        Abutton.add(searchslow); 
        Abutton.add(searchfast);
        Abutton.add(Aspeed);
        left.add(Abutton);
        
        infop.add(info); 
        infop.add(noinfo);
        infop.add(no);
        left.add(infop);
        
        path2.add(setupserv);
        path2.add(update);
        
        left.add(path2);
        left.add(scroll=new JScrollPane(path));// sets up the scroll bar on the textarea

        p.add(g);
        
        aStar.addActionListener(this);
        clear.addActionListener(this);
        crowdsimulator.addActionListener(this);
        builder.addActionListener(this);
        dijkstra.addActionListener(this);
        Send.addActionListener(this);
        robotslow.addActionListener(this);
        robotfast.addActionListener(this);
        searchslow.addActionListener(this);
        searchfast.addActionListener(this);
        update.addActionListener(this);
        setupserv.addActionListener(this);
        info.addActionListener(this);
        noinfo.addActionListener(this);


    	Thread t = new Thread(new helpGUI());
    	t.start();
        maploader(null);// runs the map loader on launch
	}
	
    Map map = new Map(); // creates a new instance of map
    AStar Astar = new AStar(); // creates a new instance of astar
    Dijkstra Dijk = new Dijkstra();// creates a new instance of dijkstra
    
		public void itemStateChanged(ItemEvent e)// sets up the checkboxes
		{

		    Checkbox box = group.getSelectedCheckbox();
		    
		    if(box == blocks)
		    {
		    	GridCell.setcontrol(GridCell.SET_BLOCKS); // allows users to set click nodes and turn them to blocked cells
		    	return;
		    }
		    if(box == start)
		    {
		    	GridCell.setcontrol(GridCell.SET_START);//  allows users to set start nodes
		    	return;
		    }
		    if(box == goal)
		    {
		    	GridCell.setcontrol(GridCell.SET_goal); // allows users to set goal nodes
		    	return;
		    }
		    if(box == person)
		    {
		    	GridCell.setcontrol(GridCell.SET_PERSON);// allows users to set a person start node
		    	return;
		    }
		}
		
		
		public void actionPerformed(ActionEvent e)
		{
		    if(e.getSource() == aStar)// runs the astar search algorithm
		    {
		    	if(GridCell.startmove==false) // if the start cell and goal cell arent on top of each other
		    	{
		    		if(AStar.running != true || Dijkstra.running != true) // if the searches arent already running
		    		{
		    			Astar.findPath(map);// run the algorithm
		    			System.out.println("Path Finder Started");
		    		}
		    	}
		    	else
		    	{
		    		JOptionPane.showMessageDialog(null,"Please move either the Start or Goal node");
		    	}

		    }
		    
		    if(e.getSource() == clear) // clears the grid of all cells with a cost
		    {
		        GridCell.clearAll();

		        map.repaint();
		        System.out.println("Grid Cleared");
		    }

		    if(e.getSource() == crowdsimulator)//runs the crowd simulation 100 times
			 { 
		    	int i = 0;
		    	while (i<100)
		    	{
		    		GridCell.crowdsim(map);
		    		i++;
		    	}
		    	repaint();
		    	map.repaint();
		    	
			 }
		    
		    if(e.getSource() == Send)// sends the  array list of directions to the robot simulation
			 { 
		    	if(Path.ipaddress==null) // checks if the server has been set up within the simulation
		    	{
		    		JOptionPane.showMessageDialog(null,"Please input the robots IP address using the server setup button");
		    	}
		    	else
		    	{
		    		Path.sendpath();// sends path
		    	}

			 }
		    
		    if(e.getSource() == robotfast)//speeds up the robots movement
			 { 
		    	if (Path.robbiespeed > 49)// checks if the robot isnt over its speed limit
		    	{
		    		Path.robbiespeed = Path.robbiespeed-50; // minuses 50 from the speed variable
		    	}
		    	
		    	robotspeed.setText(""); // clears the text area
		    	
		    	if (speednum<15) // checks the speednum varible
		    	{
		    		speednum++;
		    	}
		    	String convert2 = Integer.toString(speednum);// converts the int to a string
		    	//speed.append(convert);
		    	robotspeed.append("Robot Speed: "+convert2); // prints to the text area
		    	

			 }
		    
		    if(e.getSource() == robotslow)//slows down the robots movement
			 { //1750
		    	if (Path.robbiespeed<1750)
		    	{
		    		Path.robbiespeed = Path.robbiespeed+50;
		    	}
		    	
		    	robotspeed.setText("");
		    	
		    	if (speednum>-20)
		    	{
		    		speednum--;
		    	}
		    	
		    	String convert2 = Integer.toString(speednum);
		    	//speed.append(convert);
		    	robotspeed.append("Robot Speed: "+convert2);
		    	//speed.append(convert);
			 }
		    
		    if(e.getSource() == searchfast)//speeds up the search algorithms
			 { 
		    	if (Dijkstra.speed > 1)// checkks if the dijkstra speed varibale is above 1
		    	{
		    		Dijkstra.speed = Dijkstra.speed-1;// minuses one from the variable
		    	}
		    	
		    	if (AStar.speed > 1)// checks if the astar speed variable is above 1
		    	{
		    		AStar.speed = AStar.speed-1;// minuses one from the variable
		    	}
			    Aspeed.setText("");
			    if (aSpeed<40)
			    {
			    	aSpeed++;
			    }
			    String convert2 = Integer.toString(aSpeed);
			    //speed.append(convert);
			    Aspeed.append("Search Speed: "+convert2);
			    	
		    	

			 }
		    if(e.getSource() == searchslow)//if the save button is pressed
			 {
		    	if (Dijkstra.speed <40)
		    	{
		    		Dijkstra.speed = Dijkstra.speed+1;
		    	}
		    	if (AStar.speed <40)
		    	{
		    		AStar.speed = AStar.speed+1;	
		    	}
			    Aspeed.setText("");
			    if (aSpeed>0)
			    {
			    	aSpeed--;
			    }
			    String convert2 = Integer.toString(aSpeed);
			    //speed.append(convert);
			    Aspeed.append("Search Speed: "+convert2);
			 }

			 
		    if(e.getSource() == builder)// loads the map builder application
			 { 
				 System.out.println("Loading MapBuilder application");
				 MapBuilder.main(null);
				 
				 System.out.println("Started MapBuilder application");
			 }
		    if(e.getSource() == dijkstra)// when the builder button has been pressed
			 { 
		    	if(GridCell.startmove==false)// checks if the start and goal cell are on top of one another
		    	{
		    		if(AStar.running != true || Dijkstra.running != true) // checks if the searches are already running
		    		{
		    			Dijk.findPath(map);// runs algorithm
		    			System.out.println("Path Finder Started");
		    		}
		    	}
		    	else
		    	{
		    		JOptionPane.showMessageDialog(null,"Please move either the Start or Goal node");
		    	}

			 }
		    
		    if(e.getSource() == update)// updates the print map zone
			 { 
		    	path.setText("");//removes text in text area
		    	output = Path.getdirections();// sets the array list to the arraylist of directions
		    	//String t = 
		    	try
		    	{
			    	for (int i = 0; i < output.size(); i++) 
			    	{
			    		 path.append(" "+output.get(i)+"\n");// print each element of the array list with a new line
			    	}
		    	}
		    	catch(Throwable ex)
		    	{
		    		path.append("no path to display");// if the array list is empty print this
		    	}

		    		 
		    	//path.append(output);
			 }
		    
		    if(e.getSource() == setupserv)// sets up the server for sending
			 { 
		    	  String str = JOptionPane.showInputDialog(null, "Enter the IP Address of the Robot Simulation: ", "193.62.172.");
		    	  //opens a dialog box for a user to enter the ip of the robot simulation, this is then stored and used

		    	  System.out.println(str);
		    	  Path.ipaddress = str;//sets variable to the input

			 }
		    
		    if(e.getSource() == info)// prints more information to the map
			 { 
		    	if (GridCell.information != true)
		    	{
		    		GridCell.information = true;// sets the variable true and reprints the map including the detail
		    	}

		    	map.repaint();
		    	System.out.println(GridCell.information);
			 }
		    
		    if(e.getSource() == noinfo)// removes information from the map
			 { 

		    	if (GridCell.information != false)
		    	{
		    		GridCell.information = false;
		    	}

		    	map.repaint();
		    	System.out.println(GridCell.information);
			 }
		    	  

		}
		

		@SuppressWarnings("deprecation")
		private void maploader(String args[])// used to load a map whent he program is executed
		{ 
		    try
		        {      
		        	FileDialog loader = new FileDialog(new Frame(),"Select Map to Load",FileDialog.LOAD);// loads a file dialog window for loading
		        	loader.setDirectory("Maps");//sets directory to the maps folder
		        	loader.setFilenameFilter(new FilenameFilter(){public boolean accept(File f,String n){return n.endsWith("grd");}}); // only load grid files
		        	loader.show();
		            File file = new File(loader.getDirectory(),loader.getFile());// sets the file to the user selected file
		            System.out.println("Map Chosen: "+file);
		            Panel j = new Panel();
		            j.add(new Label("map loaded:" +file)); // prints label to panel
		            add(j,"South");
		            FileInputStream fis = new FileInputStream(file);// creates an input stream using the file
		            ObjectInputStream ois = new ObjectInputStream(fis); // creates an object input stream using the file input stream
		            map = (Map)ois.readObject();// the file is read and stored as map
		            

		            add(map,"Center");// the map is added to the center of the application
		            ois.close();
		        }
		        catch(Throwable ex)
		        {
		            System.err.println("Load failed "+ex);
		            JOptionPane.showMessageDialog(null,"Load Failed");
		            System.exit(1);//closes the program if it fails to load
		        }
		}

        public static void main(String args[])
        {
            Main simulation = new Main();
            simulation.init();//initiates the simulation
            
            
            JFrame frame = new JFrame();
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);  //sets the frame to full screen
            Toolkit tk = Toolkit.getDefaultToolkit();   
            int xSize = ((int) tk.getScreenSize().getWidth());   
            int ySize = ((int) tk.getScreenSize().getHeight());   
            frame.setSize(xSize,ySize);   
            //frame.setSize(1250,700); // comment out this line to set full screen, not working on windows atm
            frame.setResizable(false);
            //frame.setLocation(0, 500);
            //frame.setBackground(Color.cyan);
            frame.setBackground(Color.red);// sets a loading colour, the frame will not be red on boot if the app has failed

            frame.setVisible(true);
            frame.setLayout(new BorderLayout());
            frame.add(simulation,"Center");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            
            simulation.start();
            
        }    
        
}