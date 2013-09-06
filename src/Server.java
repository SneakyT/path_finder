//this class was used for UDP messages to be sent to the robot, i chose to move from this to TCP IP. 
//i have left the code in to show how i was using this method.

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class Server implements Runnable
{
	private boolean constant;
	private DatagramSocket socket;
	private DatagramSocket socket1;
	private JTextArea test;
	private JTextArea test1;

	
	//buttons for closing the servers, and printing the information being sent
	
	
	
	public Server()
	{
		
		JFrame pathserver = new JFrame();// builds new jframe
	      
		pathserver.setSize(300,300); // sets the size of the jframe
		pathserver.setLocation(1000,0);
		pathserver.setLayout(new BorderLayout(0,0)); //sets the size of the border of the jframe
		pathserver.setTitle("Pathfinder Server"); // sets the title of the jframe
		pathserver.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // closes the program when the the frame is closed
		
		JFrame robbieserver = new JFrame();// builds new jframe
		      
		robbieserver.setSize(300,300); // sets the size of the jframe
		robbieserver.setLocation(1000,300);
		robbieserver.setLayout(new BorderLayout(0,0)); //sets the size of the border of the jframe
		robbieserver.setTitle("Robbie Server"); // sets the title of the jframe
		robbieserver.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // closes the program when the the frame is closed
		  
		  //Panel top = new Panel(); // creates pannel
		  //top.add(new Label("This is a server linked to Robbie2")); // prints label to top panel
		  test = new JTextArea(600,20);
		  test.setLocation(0, 10);
		  test.append("messages from the pathfinder will be shown below!\n");
		  test1 = new JTextArea(20,20);
		  test1.setLocation(0, 70);
		  test1.append("messages from robbie will sbe shown below!\n");
		  //top.add(test);
		  pathserver.add(test);
		  robbieserver.add(test1);
		  
		  pathserver.setVisible(true);
		  pathserver.setResizable(false); // makes the jframe non-resizable
		  robbieserver.setVisible(true);
		  robbieserver.setResizable(false); // makes the jframe non-resizable

	      constant=true; //bool to set the loop forever
	      
	      try 
	      {
			socket = new DatagramSocket(8444);// sets up a socket to the pathfinder
	        socket1 = new DatagramSocket (2010);// sets up a socket to robbie
	      } 
	      catch (SocketException e) 
	      {
				System.err.println("Couldn't make a socket.");
				e.printStackTrace();
	      }
	      
	}
	  


	@Override
	public void run() 
	{
		String received=null;// sets up the recieved string

	    
	    // creates a new datagram socket to link to robbie
	      
	      while (constant) //for sending mesages
	      {  
	    	  byte[] object = new byte[1400];
	    	  DatagramPacket packet = new DatagramPacket(object, object.length);
	         
	    		  
		      try {
					socket.receive(packet);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.err.println("Didn't recieve a valid packet.");
					e1.printStackTrace();
				}// recieves the packet 2010
		    	  received=new String(packet.getData());     // sets up the recieved string with the packet data
		    	  if (received.indexOf("X")!=-1) // if the packet is an order taken 
		    	  {
		    		            //System.out.println("message recieved by server");
		    		            String output = ("Message recieved by server \n");
		    	         	    test.append(output);

		    	    		     //packet = new DatagramPacket(sendBytes, sendBytes.length, ipaddress, 8444);  // sets up a new packet based on the bytes
		    	    		     try {
									socket1.send(packet);// sends the packet to robbie   
								} catch (IOException e) {
									// TODO Auto-generated catch block
									System.err.println("Couldn't send the packet.");
									e.printStackTrace();
								}    	
			    		            String output2 = ("Message sent to Robbie \n");
			    	         	    test.append(output2);

		    	    		     
		    	  }  
		    	  
		    	  else //if the packet is a request for an order
		    	  {
		    		  System.out.println("message not recieved by the server, please try again");
		    	  }
	       
	    	  } 
	    	  
	      	while(constant) // for recieving packets
	    		{
	      		 byte[] object = new byte[1400];
		    	  DatagramPacket packet = new DatagramPacket(object, object.length);
		         
		    		  
			      try 
			      {
						socket1.receive(packet);// try to recieve a packet from robbie
			      } 
			      catch (IOException e1) 
					{
						System.err.println("Didn't recieve a valid packet.");
						e1.printStackTrace();
					}
			      
					if (received.indexOf("X")!=-1) // if the packet is an order taken 
			    	  	{
			    			//System.out.println("message recieved by server");
			    		    String output = ("Robbie has encountered an object and needs a new path");
			    	        test1.append(output);
			    	    		     
			    	  	}  
			    	else //all packets from robbie begin with help, therefor if the first part of the packet isnt help thers an error
			    	  	{
		
			    		  	System.out.println("message not recieved by the server, please try again");

			    	  	}
	    		}
	    	    socket.close();//closes the socket
	    	    socket1.close();//closes the socket
	      }
	}

