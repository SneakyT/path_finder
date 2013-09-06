	
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
	
	//This class is used by a user to build maps

public class MapBuilder extends java.applet.Applet implements ActionListener
{

	private static final long serialVersionUID = 1L;
	
	    
	    Button save; // creates the save buttons
	    
	    Map map = new Map(); // creates a new blank map from map class
	
		public void init()
		{
			setLayout(new BorderLayout(0,0)); // gives the application a border set up by the two int values
			setSize(1000,600);// sets the size of the window
			
			Panel top = new Panel(); // creates the panel at the top of the application
			top.add(new Label("Create maps to use within the applet")); // prints label to top panel
			add(top,"North"); // puts the top panel a the top of the application
	        add(map,"Center"); // adds a new map to the center of the application
	        
	        Panel east = new Panel(); // creates a new panel to hold the other pannels
	        east.setLayout(new GridLayout(4,1)); // sets how many rows and columns the east panel has
	        Panel box = new Panel(); // creates the panel with the blocks checkbox
	        box.setLayout(new GridLayout(2,1)); // sets how many rows and columns the box panel has

	        east.add(box);// adds the panel box to panel east 
	        add(east,"East");// moves the pannel east to the east of the application
	        
	        Panel button = new Panel(); // creates the button pannel
	        button.setLayout(new GridLayout(2,1,2,30));// sets rows, columns, horizontal gap and vertical gap
	
	        save = new Button("Save");        

	

	        button.add(save);// adds the save button to the button panel
	
	        east.add(button);// adds the button panel to the east panel
	        save.addActionListener(this);// listens for when the save button is pressed
	        
	        GridCell.setcontrol(GridCell.SET_BLOCKS);
		}
			

			
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent event) // used when a button is pressed
		{

			 
			 if(event.getSource() == save)//if the save button is pressed
			 { 
			      
			        try
			        {
			            FileDialog window = new FileDialog(new Frame(),"Save Window",FileDialog.SAVE);// creates a new filedialog fd which displays a window to select a file and sets the mode to save
			            window.setFile("SaveMeInTestMaps.grd");// sets the default filename
			            window.setDirectory("Maps");// sets the folder name
			            window.setFilenameFilter(new FilenameFilter(){public boolean accept(File f,String n){System.out.println("accept requested");return n.endsWith("grd");}});
			            window.show();// shows the window
			            File file = new File(window.getDirectory(),window.getFile());// creates a file based on the parent directory and child file
			            System.out.println("getting directory");
			            FileOutputStream filestream = new FileOutputStream(file); // sets up a new output stream for the object stream
			            //-------------------------------------------------------------------------------------
			            ObjectOutputStream objectstream = new ObjectOutputStream(filestream); // sets up a new output stream for the map
			            objectstream.writeObject(map);//the map is written to the objectoutputstream
			            System.out.println("object written to stream");
			            System.out.println(map);
			            objectstream.close();//closes stream
			            JOptionPane.showMessageDialog(null,"The Map has been Saved!");
			            System.out.println("the grid has been saved");
			        }
			        catch(IOException ex) // catch the fails and print 
			        {
			            System.err.println("the save has failed"+ex);
			        }
			}
		}
	        @SuppressWarnings("deprecation")
			public static void main(String args[])
	        {
	            MapBuilder mapbuild = new MapBuilder(); // creates new instance of mapbuilder
	            mapbuild.init(); // initiates the instance
	            JFrame builder = new JFrame();// builds new jframe
	            
	            builder.setSize(1000,600); // sets the size of the jframe
	            builder.setLayout(new BorderLayout(0,0)); //sets the size of the border of the jframe
	            builder.setTitle("Map Builder"); // sets the title of the jframe
	            builder.add(mapbuild,"Center"); // adds the instance of mapbuilder to the jframe
	            builder.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // closes the program when the the frame is closed
	            
	            
	            builder.setResizable(false); // makes the jframe non-resizable
	            
	            
	            builder.show();// shows the jframe
	        }

	}