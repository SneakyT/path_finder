import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Vector;

//This class is used to build and maintain cells

public class GridCell extends java.awt.Component implements Serializable
{
	private static final long serialVersionUID = 5810416284057036645L;
    public static final int SET_BLOCKS=0,SET_START=1,SET_goal=2,SET_PERSON=3;
    public static final double NORMAL = 1,BLOCK=Double.MAX_VALUE,PERSON=1.7976931348623157E307;
    private static int control = SET_BLOCKS;
    private static GridCell startCell;
    private static GridCell goalCell;
    private static GridCell personCell;
    private static GridCell personCell2;
    private static GridCell personCell3;
    private static GridCell personCell4;
    private static GridCell personCell5;
    private static GridCell personCell6;
    private static GridCell personCell7;
    private static GridCell personCell8;
    private static GridCell personCell9;
    private static GridCell personCell10;
    private static GridCell personCell11;
    private static GridCell personCell12;
    private static GridCell personCell13;
    private static GridCell personCell14;
    private static GridCell personCell15;
    static boolean nextchoice=false;
    static int w = 50;// sets the width of the grid
    static int h = 50;// sets the height of the grid
    protected static Vector<?> done;
    public static boolean startmove=false;
    
    static GridCell cellarray[][] = new GridCell[w][h];// creates a new instance of gridcell

    static public boolean information = true;
    
    private boolean isStart = false;
    private boolean isgoal = false;
    private boolean isPerson = false;
    
    private static Vector<GridCell> allcells = new Vector<GridCell>();
    
    public static boolean tidy = false;
    
    private double cost = 1;
    private transient int distFromStart = -1;
    private transient int distFromgoal = -1;
   // private boolean totalBlock = false;
    
    private boolean partOfPath = false;
    
    private Point position;
    
    public GridCell()//constructor
    {
    	allcells.addElement(this);
        tidy=true;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        enableEvents(AWTEvent.ADJUSTMENT_EVENT_MASK);
    }
    
    public void setPosition(Point pos)//sets the position of a cell
    {
        position = pos;
    }
    
    
    public Point getPosition()//returns position
    {
        return position;
    }
    //public GridCell[][] getpersonCell()
    //{
    	//return CrowdSimulator.test();
    //}
      
    public static void setcontrol(int mode)//used to change the mode
    {
        control = mode;
    }    
    
    public void processMouseEvent(MouseEvent e)
    {
        super.processMouseEvent(e);
        if(e.getID()==MouseEvent.MOUSE_CLICKED)//on mouse click
        {
            setShowPath(false);//removes the path
            switch(control)
            {
                case(SET_BLOCKS):
                	
                    if(cost!=BLOCK){cost=BLOCK;}
                    else{cost=NORMAL;}
                    repaint();
                    break;
                case(SET_START):
                	if(AStar.running==false && Dijkstra.running==false){
                	if (cost!=BLOCK){
                		if(isgoal==false){
                    setStart(true);
                    startmove=false;
                	}}}
                    break;
                case(SET_goal):
                	if(AStar.running==false && Dijkstra.running==false){
                	if (cost!=BLOCK){
                		if(isStart==false){
                    setgoal(true);
                    startmove=false;
                	}}}
                    break;
                case(SET_PERSON):
                	if (cost!=BLOCK)
                	{
                		try
                		{
	                    	personCell.setCost(NORMAL);personCell.repaint();
	    	                personCell2.setCost(NORMAL);personCell2.repaint();
	    	                personCell3.setCost(NORMAL);personCell3.repaint();
	    	                personCell4.setCost(NORMAL);personCell4.repaint();
	    	                personCell5.setCost(NORMAL);personCell5.repaint();
	    	                personCell6.setCost(NORMAL);personCell6.repaint();
	    	                personCell7.setCost(NORMAL);personCell7.repaint();
	    	                personCell8.setCost(NORMAL);personCell8.repaint();
	    	                personCell9.setCost(NORMAL);personCell9.repaint();
	    	                personCell10.setCost(NORMAL);personCell10.repaint();
	    	                personCell11.setCost(NORMAL);personCell11.repaint();
	    	                personCell12.setCost(NORMAL);personCell12.repaint();
	    	                personCell13.setCost(NORMAL);personCell13.repaint();
	    	                personCell14.setCost(NORMAL);personCell14.repaint();
	    	                personCell15.setCost(NORMAL);personCell15.repaint();
	    	                repaint();

                		}
                		catch(Exception e2)
                		{}
                		
	                	setPerson(true);
	                    personCell=this;
	
	                	//personCell.setpartofCrowd(true);
	                	//personCell.repaint();
	                	personCell4=this;
	                	personCell7=this;
	                	personCell10=this;
	                	personCell13=this;
	                			
	                	//personCell2=this;
	
	                	//personCell2.setLocation(p)
	
	                	//repaint();
	                	//getPreferredSize();
	                	//repaint();
	
	                	//GridCell test = new GridCell();
	                	//test = personCell;

	                	//personCell.MovePerson();

                	}
                   	break;
                    
                
            }
        }
        
    }
    
    
    public static void crowdsim(Map map)//working crowd simulation
    {
    	//uses three cells per person, one to start, one to be the move and one to clear the old cell
    	personCell3=personCell;// sets 3 to 1
    	personCell2=map.getnextperson(personCell); // sets the next cell to the lowest adjacent cell to the current cell
    	if(personCell3.getCost()==BLOCK){personCell3.setCost(NORMAL);}//sets the cell to be normal instead of block
        personCell=personCell2;// sets the current cell to the next cell
        
        personCell.setCost(BLOCK);//sets the cost of the block, this is then coloured seperatly in the paint method.


        personCell.repaint();
        personCell2.repaint();
        personCell3.repaint();
        
    	personCell6=personCell4;
    	personCell5=map.getnextperson(personCell4); // sets the next cell to the lowest adjacent cell to the current cell
    	if(personCell6.getCost()==BLOCK){personCell6.setCost(NORMAL);}
        personCell4=personCell5;// sets the current cell to the next cell
        
        personCell4.setCost(BLOCK);
 
    	personCell9=personCell7;
    	personCell8=map.getnextperson(personCell7); // sets the next cell to the lowest adjacent cell to the current cell
    	if(personCell9.getCost()==BLOCK){personCell9.setCost(NORMAL);}
        personCell7=personCell8;// sets the current cell to the next cell
        
        personCell7.setCost(BLOCK);


        personCell7.repaint();
        personCell8.repaint();
        personCell9.repaint();
        
    	personCell12=personCell10;
    	personCell11=map.getnextperson(personCell10); // sets the next cell to the lowest adjacent cell to the current cell
    	if(personCell12.getCost()==BLOCK){personCell12.setCost(NORMAL);}
        personCell10=personCell11;// sets the current cell to the next cell
        
        personCell10.setCost(BLOCK);


        personCell10.repaint();
        personCell11.repaint();
        personCell12.repaint();
        
    	personCell15=personCell13;
    	personCell14=map.getnextperson(personCell13); // sets the next cell to the lowest adjacent cell to the current cell
    	if(personCell15.getCost()==BLOCK){personCell15.setCost(NORMAL);}
        personCell13=personCell14;// sets the current cell to the next cell
        
        personCell13.setCost(BLOCK);


        personCell13.repaint();
        personCell14.repaint();
        personCell15.repaint();

    }
 
    public static void movingperson()//old code, not used now
    {
 
    	Point t = new Point();
    	t=personCell.getLocation();

    	double x = Math.random();
    	double y = Math.random();
    	System.out.println(x);
    	if(x<0.3)
    	{
    		t.y=t.y+10;

    	}//down
    	else
    	{
    		//t.y=t.y-10;

    	}
    	if(y<0.5)
    	{
    		t.x=t.x+15;

    	}//down
    	else
    	{
    		t.x=t.x-15;

    	}
    	if (t.y>509)
    	{
    		//int point =personCell2.getX();
    		//personCell2.setLocation(point, 19);
    		t.y=19;
    	}            

    	if(t.x>754)
    	{
    		t.x=19;
    	}
    	if(t.x<19)
    	{
    		t.x=754;
    	}


		personCell.setLocation(t);
		
    	
    	
    	
    	//x largest = 754
    	//x small 19?
    	//y 509   19
    }
    
    public void move()//was test code now unused
    {
        GridCell temp = this;
        if(personCell !=null){temp = personCell;temp.setPerson(false);}
        GridCell newer;
        newer=this;
        Point t =newer.getLocation();
        t.x=t.x+15;
        newer.setLocation(t);
        personCell=newer;
        isPerson=true;
        newer.setCost(BLOCK);
        personCell.setCost(BLOCK);


        repaint();
        temp.repaint();
    }
    
    public void addToPathFromStart(int distSoFar)//used to add a cell to a path from the start
    {
        if(distFromStart == -1){
            distFromStart = (int) (distSoFar+cost);
            return;
        }
        if(distSoFar+cost<distFromStart){
            distFromStart = (int) (distSoFar+cost);
        }
    }

    public double getCost()//returns the cost of a cell
    {
        return cost;
    }
    
    
    public void setCost(double c)//sets the cost of a cell
    {
        cost=c;
    }
    
    
    public static GridCell getStartCell()//returns the start cell
    {
        return startCell;
    }
    
    public static GridCell getPersonCell()//returns the original person cell
    {
        return personCell;
    }

    public boolean isPerson()//sets the person cell
    {
        return personCell == this;
    }

    public void setStart(boolean bool)//sets the start cell
    {
            if(bool)
            {
                GridCell temp = this;
                if(startCell !=null)
                {
                	temp = startCell;
                	temp.setStart(false);
                }
                startCell=this;
                isStart=true;
                repaint();
                temp.repaint();
            }
            else
            {
                isStart=false;
            }          
    }

     public static GridCell getgoalCell()//returns the goal cell
     {
        return goalCell;
    }
    
    public void setgoal(boolean bool)// sets the goal node
    {
        if(bool)
        {
            GridCell temp = this;
            if(goalCell!=null)
            {
            	temp=goalCell;
            	temp.setgoal(false);
            }
            goalCell=this;
            isgoal=true;
            repaint();
            temp.repaint();
        }
        else
        {
                isgoal=false;
        }
    }
    
    public void setPerson(boolean bool)//sets a person cell
    {
        if(bool)
        {
            System.out.println(personCell);
            personCell=this;
            isPerson=true;
            repaint();
            personCell.repaint();
        }
        else
        {
                isPerson=false;
        }
    }
    public boolean isblock()
    {
        return cost==BLOCK;
    }
    
    private void resetCell()//used to reset the cell
    {
        setPartOfPath(false);
        distFromStart = distFromgoal = -1;
    }
    
    public static void reset()//used to reset all cells
    {
        for(int i=0;i<allcells.size();i++)
        {
            ((GridCell)allcells.elementAt(i)).resetCell();
        }
    }
    
    private void clearcost()//used to clear the cell cost
    {
        setCost(NORMAL);
    }
    
   public static void clearAll()//used to clear all the cells cost
   {
        for(int i=0;i<allcells.size();i++)
        {
            ((GridCell)allcells.elementAt(i)).clearcost();
        }
   }
    
    public static void setShowPath(boolean bool)
    {
    }

    
    public void setPartOfPath(boolean bool)//sets a cell as part of the path
    {
        partOfPath = bool;
    }
    
    public void setpartofCrowd(boolean bool)//sets a cell as part of the crowd simulation
    {
    	setPerson(true);
    }
    
    public void bestchoice(boolean bool)//sets the cell as the next choice 
    {
        nextchoice = bool;
    }
   
    public int getDistFromStart()
    {
        if(GridCell.startCell == this)
        {
        	return 0;
        	}
        if(isblock())
        {
        	return -1;
        }
        return distFromStart;
    }

    public void paint(Graphics g)// paint container
    {
        g.setColor(Color.white); //sets all cells white
    
            if(cost==BLOCK)//if a cell is blocked its coloured red
            {
            	g.setColor(Color.red);
            }
            if(cost==PERSON)//if the cell has a cost of person its coloured orange
            {
            	g.setColor(Color.orange);
            }

        if(nextchoice)//if the cell is the next choice its coloured pink (removed)
        {
        	g.setColor(Color.pink);
        }
        
        if(partOfPath) // if the cell is part of the path its coloured green
        {
            g.setColor(Color.green);
        }
        
        if(personCell == this)//paints each of the person cells orange
        {
            g.setColor(Color.orange);
        }
        
        if(personCell4 == this)
        {
            g.setColor(Color.orange);
        }
        
        if(personCell7 == this)
        {
            g.setColor(Color.orange);
        }
        
        if(personCell10 == this)
        {
            g.setColor(Color.orange);
        }
        
        if(personCell13 == this)
        {
            g.setColor(Color.orange);
        }

        if(startCell == this)//paints the start cell black
        {
            g.setColor(Color.black);
        }
        
        if(goalCell == this)//paints the goal cell blue
        {
            g.setColor(Color.blue);
        }

        g.fillRect(1,1,30,30);

            
        g.setColor(Color.black);
        if(distFromStart>0 && information && distFromStart!=2.147483647E9)//paints the distance if its above 1 and the information bool is on and if its not a blocked cell
        {
        	double test = distFromStart+distFromgoal+1;

        	String dist = Double.toString(test);

        	g.drawString(dist,1,14);
        }
        if(information) //draws the rectangle if the information bool is on
        {
        	g.drawRect(1,1,30,30);
        }
        
            
    }
    
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException
    {
        if(!tidy)
        {
            
        	allcells = new Vector<GridCell>();
            tidy=true;
        }
        
        ois.defaultReadObject();
        allcells.addElement(this);
        if(isStart)
        {
        	setStart(true);
        }
        
        if(isgoal)
        {
        	setgoal(true);
        }
        
        if(isPerson)
        {
        	setPerson(true);
        }

    }
  
}