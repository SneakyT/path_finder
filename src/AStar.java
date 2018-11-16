import java.awt.Point;
import java.util.Vector;

import javax.swing.JOptionPane;

//This class is used to perform A-Star Search this is a test

public class AStar extends java.lang.Object implements Runnable
{
    double minCost;// stores the minimum cost 
    public final int NO_PATH=-1,NOT_FOUND=0,FOUND=1;
    
    protected Vector<GridCell> edge;
    protected Vector<GridCell> searched;
    protected Map map;
    static int speed = 20;//speed the algorithm runs
    private int maxSteps = 2500;// sets how many steps the simulator can do, set ot maximum 
    public GridCell paint;
    GridCell repaint;
    static public boolean running=false;
    
    Thread loop;    

    public double ManhattanDistance(Point a,Point b,double low) // calculates the distance from the start node to the end node
    {       
    	double score;
    	score = low * (Math.abs(a.x-b.x)+Math.abs(a.y-b.y));// returns the absolute value of the x and y of both points multiplied by the low value
    	return score;
    }
    
    public GridCell[] findPath(Map map)//runs the pathfinder
    {
    	running=true;//used to check if the search is running
    	Dijkstra.running=true;//used to check if the search is running
    	
    	System.out.println(running);
        minCost = Double.MAX_VALUE;
        for(int i=0;i<50;i++)// runs 50 as the grid is 50x50
        {
            for(int j=0;j<50;j++)//runs 50 as the grid is 50x50
            {
                minCost = Math.min(map.gridCell[j][i].getCost(),minCost);//returns the lowest value of the two
                //sets the min cost used in the heuristic
            }
        }
        
        this.map = map;
        GridCell.reset();
        edge = new Vector<GridCell>();
        searched = new Vector<GridCell>();
        if(GridCell.getStartCell() == null)//if there is no start point
        {
        	JOptionPane.showMessageDialog(null,"No Start Point has been set");
            System.out.println("No start point set");
        	running=false;
        	Dijkstra.running=false;
        	GridCell.startmove=true;
            return null;
        }
        
        if(GridCell.getgoalCell() == null)//if there is no goal
        {
        	JOptionPane.showMessageDialog(null,"No goal Point has been set");
            System.out.println("No goal point set");
        	running=false;
        	Dijkstra.running=false;
        	GridCell.startmove=true;
            return null;
        }
        
        loop = new Thread(this);
        loop.start();// runs
        return null;
    	
    }
    
    public void run()
    {
        edge.addElement(GridCell.getStartCell());
        int attempts =0;// variable to count how many attempts
        int state=NOT_FOUND;
        while(state==NOT_FOUND && attempts<maxSteps)//while a path hasnt been found and the whole grid hasnt been checked
        {
        	attempts++;
            state = step();//sets state to output of step

            try
            {
                Thread.sleep(speed*10);//sleep 
            }
            catch(InterruptedException e){}
            
        }
        
        if(state==FOUND)//if a path has been found
        {
            try 
            {
				Path.setPath(map);//runs the find path method in the path class
			} 
            catch (Exception e) 
            {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        else
        {
        	System.out.println("No Path Found");
        	JOptionPane.showMessageDialog(null,"No Path Can Be Found");
        	AStar.running=false;// sets the booleans to false to let the searches run again
        	Dijkstra.running=false;
        	GridCell.startmove=false;
        }
    }
    
    public int step()
    {

        boolean found = false;
        GridCell goal = GridCell.getgoalCell(); // sets goal to the goal cell
        Point end = goal.getPosition();// sets up a point to hold the goal 
        @SuppressWarnings("unchecked")
		Vector<GridCell> temp = (Vector<GridCell>) edge.clone();// re-uses the edge vector
        double min = Double.MAX_VALUE;
        double score;
        GridCell best = (GridCell)temp.elementAt(temp.size()-1); ;
        GridCell now;
        
        for(int i=0;i<temp.size();i++)
        {
            now = (GridCell)temp.elementAt(i);
            if(!searched.contains(now))
            {
                //score =now.getDistFromStart();
                
                score =now.getDistFromStart();
                score += ManhattanDistance(now.getPosition(),end,minCost);// adds the distance from the start to the manhattan distance
                if(score<min)// if the score is greater than the min
                {
                    min = score; // sets the new score to min
                    best = now;// sets the best cell to now

                }
            }
        }
        

        now = best;//sets the now cell to the best cell


        //System.out.println(now.getPosition()+" Selected for expansion");
        edge.removeElement(now);//removes now
        searched.addElement(now);
        GridCell next[] = map.getsurrounding(now); //creates an array of cells around the now cell
        for(int cell=0;cell<8;cell++)//runs 8 times, one for each cell
        {
            if(next[cell]!=null)// checks the cell isnt empty
            {
                if(next[cell]==goal)//checks the cell for the goal cell
                {
                	found=true;//finish search
                }
                if(!next[cell].isblock())//if the cell isnt blocked
                {
                    next[cell].addToPathFromStart(now.getDistFromStart());//adds cell to the path with distance

                    if(!edge.contains(next[cell]) && !searched.contains(next[cell]))
                    {
                    	edge.addElement(next[cell]);
                    }//if the cell isnt in edge or searched its added to edge
                }
            }
            if(found)
            {
            	return FOUND;
            }
        }
        map.repaint();
        if(edge.size()==0)
        {
        	return NO_PATH;
        }
           
        return NOT_FOUND;
    }
            
        
}