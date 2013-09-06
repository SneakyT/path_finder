import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

//This class is used to follow a path from a search

public class Path 
{
    private static ArrayList<String> movement,directions,directionsend;
    static int stepSpeed = 1;//sets the speed 20 is about average
    static Thread loop;
    static Socket socket = null;
    static PrintWriter out = null;
    static BufferedReader in = null;

    static String typeOfSearch = null;
    static int robbiespeed = 500;
    static String ipaddress;
    
    
	public static void setPath(Map map) throws Exception// ran when a path is found
    {
		
        System.out.println("Path Found");
        GridCell.setShowPath(true);
        boolean finished = false;
        GridCell next; // sets up a cell to hold the next cell
        GridCell finish = GridCell.getgoalCell(); // sets the current cell to start on the goal cell
        GridCell current = GridCell.getStartCell();// sets the end cell on the start cell
        Point last = null;
        Point beforelast=null;
        

        movement = new ArrayList<String>(); // sets up an array list to hold the list of directions
        directions = new ArrayList<String>(); // sets up an array list to hold the list of directions
        directionsend = new ArrayList<String>();		
        
        while(!finished)// while the search is ungoaled
        {
        	
            next=map.getlowestsurrounding(finish); // sets the next cell to the lowest surrounding cell to the current cell
            if(last==next.getPosition())//checks if the cell is blocked by checking it against the last one
            {
            	JOptionPane.showMessageDialog(null,"The path has been blocked \nPlease re-run the pathfinder");            	
            	AStar.running=false;
            	Dijkstra.running=false;
            	break;
        	}
        	beforelast = last;
        	last = finish.getPosition();
            finish=next;// sets the current cell to the next cell

           try
           {
        	   GridCell.crowdsim(map);//runs the crowd simulation
           }
           catch(Exception e)
           {
        	   
           }
            finish.setPartOfPath(true);// adds the current cell to the path
            
            Point t = finish.getPosition();
            		int u = t.x;
            		int i = t.y;
            String output = ("X: "+ u + ", Y: "+ i);
            String direct = null;
            String directsend = null;
            //System.out.println("next: "+er+" finish: "+last);
            int lastX = last.x;
            int lastY = last.y;
            if(lastY>i && lastX>u){direct="Move 50cm NorthWest";directsend="Move 50cm NorthWest. 		Use prolog predicate comands: 	cmd(turnTo northWest) 			cmd(drive 0.5m)";}
            if(lastY<i && lastX<u){direct="Move 50cm SouthEast";directsend="Move 50cm SouthEast. 		Use prolog predicate comands: 	cmd(turnTo southEast) 			cmd(drive 0.5m)";}
            if(lastY<i && lastX>u){direct="Move 50cm SouthWest";directsend="Move 50cm SouthWest. 		Use prolog predicate comands: 	cmd(turnTo southWest) 			cmd(drive 0.5m)";}
            if(lastY>i && lastX<u){direct="Move 50cm NorthEast";directsend="Move 50cm NorthEast. 		Use prolog predicate comands: 	cmd(turnTo northEast) 			cmd(drive 0.5m)";}
            if(lastX>u && lastY==i){direct="Move 50cm West";directsend=    "Move 50cm West.     		Use prolog predicate comands: 	cmd(turnTo west) 			cmd(drive 0.5m)";}
            if(lastX<u && lastY==i){direct="Move 50cm East";directsend=    "Move 50cm East.      		Use prolog predicate comands: 	cmd(turnTo east) 			cmd(drive 0.5m)";}
            if(lastY>i && lastX==u){direct="Move 50cm North";directsend=   "Move 50cm North.     		Use prolog predicate comands: 	cmd(turnTo north)			cmd(drive 0.5m)";}
            if(lastY<i && lastX==u){direct="Move 50cm South";directsend=   "Move 50cm South.     		Use prolog predicate comands: 	cmd(turnTo south) 			cmd(drive 0.5m)";}
            /*if(last.getX()>next.getX()){System.out.println("1?");}
            if(last.getX()<next.getX()){System.out.println("2?");}
            if(last.getY()>next.getY()){System.out.println("3?");}
            if(last.getY()<next.getY()){System.out.println("4?");}*/
            //System.out.println(output);
            
            directions.add(direct);
            directionsend.add(directsend);
            movement.add(output);// adds the output to the arraylist
            finish.setgoal(true);// sets the final goal as the new goal node

            finish.repaint(); // repaints the map
            
            if(finish == current) // the current node is the end node
            {
            	finished = true;
            	AStar.running=false;
            	System.out.println(AStar.running);
            	Dijkstra.running=false;
            	GridCell.startmove=true;
            	
            	//below is the code i had set up to send informtion over a UDP server i 
            	//had initially set up.
            	
          	    /*byte[] sendBytes;//sets up the bytes to be sent
        	    sendBytes=output.getBytes();//converts the output to a sendable object
        	    InetAddress ipaddress = InetAddress.getByName("127.0.0.1");// this is a test ip
        	    DatagramPacket packet; // creates a new datagram packet
    		     packet = new DatagramPacket(sendBytes, sendBytes.length, ipaddress, 8444);  // sets up a new packet based on the bytes
    		     DatagramSocket socket = new DatagramSocket ();// creates a new datagram socket
    		     socket.send(packet);// sends the packet to robbie       	
    		     socket.close();//closes the socket*/
            }
            

            Thread.sleep(robbiespeed); //sleeps for the speed set out at the top   100!!
  

        
        }
        System.out.println("Done");
        
    }
	
	public static ArrayList<String> getmovement()
	{
		return movement;
	}
	
	public static ArrayList<String> getdirections()
	{
		return directions;
	}
	
	public static void sendpath()
	{
		try 
        {
            socket = new Socket(ipaddress, 2010);// sets up the socket. change the IP address when changing computers!!
            System.out.println("check1");
            out = new PrintWriter(socket.getOutputStream(), true);// sets up the print writer based on the socket
            System.out.println("check2");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));// sets up the buffered reader
            System.out.println("check3");
        } 
        catch (IOException e) 
        {
            System.err.println(e);
            
            JOptionPane.showMessageDialog(null,"Robbie Server is not up\n cannot send the informaiton to the robot");

        }
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		try
		{	
			out.println(directionsend);//sends the array list to the robbie simualtion
    	}
    	catch (Exception e) 
    	{
            System.err.println("not sending information to robbie");
            //fails if the information isnt being sent

        }
    	try
    	{
    		//attemps to close the sockets
    	out.close();
        in.close();
        read.close();
        socket.close();
    	}
    	catch (Exception e) 
    	{
            System.err.println("no connection set up");
            //fails if the robbie server isnt up

        }
	}
}
