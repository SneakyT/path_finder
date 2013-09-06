import java.util.Vector;
import javax.swing.JOptionPane;

//This class is used to perform Dijkstra Search

public class Dijkstra extends java.lang.Object implements Runnable
{
    public final int NO_PATH=-1,NOT_FOUND=0,FOUND=1;
    
    protected Vector<GridCell> edge;
    protected Vector<GridCell> searched;
    protected Map map;
    static int speed = 20;//the speed of the search
    private int maxSteps = 2500;// sets how many steps the simulator can do, set ot maximums
    static public boolean running=false;
    
    Thread loop;
    
    public GridCell[] findPath(Map map)
    {
    	running=true;//used to check if the search is running
    	AStar.running=true;//used to check if the search is running
    	
        this.map = map;
        GridCell.reset();
        edge = new Vector<GridCell>();
        searched = new Vector<GridCell>();
        System.out.println("calculating route");
        if(GridCell.getStartCell() == null)//if there is no start point
        {
        	JOptionPane.showMessageDialog(null,"No Start Point has been set");
            System.out.println("No start point set");
        	AStar.running=false;
        	running=false;
        	GridCell.startmove=true;
            return null;
        }
        if(GridCell.getgoalCell() == null)//if there is no goal
        {
        	JOptionPane.showMessageDialog(null,"No goal Point has been set");
            System.out.println("No goal point set");
        	AStar.running=false;
        	System.out.println(AStar.running);
        	running=false;
        	GridCell.startmove=true;
            return null;
        }

        loop = new Thread(this);
        loop.start();//runs
        return null;
    }
    
    public void run()
    {
        edge.addElement(GridCell.getStartCell());
        int attempts =0;
        int state=NOT_FOUND;
        while(state==NOT_FOUND && attempts<maxSteps)
        {
            attempts++;//counts the attempts
            state = step();//sets state to output of step
            
            try
            {
                Thread.sleep(speed*100);//sleep 
            }
            catch(InterruptedException e){}
            
        }
        if(state==FOUND)//when the path is found
        {
            try 
            {
				Path.setPath(map);//runs the path follower
			} catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        else
        {
        	System.out.println("No Path Found");
        	JOptionPane.showMessageDialog(null,"No Path Can Be Found");
        	AStar.running=false;
        	Dijkstra.running=false;
    	}
    }
        
    
    public int step()
    {
        boolean found=false;
        boolean growth=false;//used to check growth of the search
        GridCell goal = GridCell.getgoalCell();
        @SuppressWarnings("unchecked")
		Vector<GridCell> temp = (Vector<GridCell>) edge.clone();
        for(int i=0;i<temp.size();i++)//runs as many times as the edge vector
        {
            GridCell now = (GridCell)temp.elementAt(i); 
            GridCell next[] = map.getsurrounding(now);//creates an array of cells around the now cell
            for(int j=0;j<8;j++)//runs 8 times, once for each cell int he array
            {
                if(next[j]!=null )//checks for emptyness
                {
                    if(next[j]==goal)//checks if for goal node
                    {
                    	found=true;
                    }
                    next[j].addToPathFromStart(now.getDistFromStart());//adds to path with distance
                    
                    if(!next[j].isblock() && !edge.contains(next[j]))//if the cell isnt in either its added to edge and growth set to true
                    {
                    	edge.addElement(next[j]);
                    	growth=true;
                    }
                }
            }
            if(found)
            {
            	return FOUND;
            }
            searched.addElement(now);
        }
        map.repaint();
        
        if(!growth)//if growth isnt set to true the algorithms cannot continue
        {
        	return NO_PATH;
        }
        return NOT_FOUND;
    }

    
	
	
        
    
}