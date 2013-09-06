import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Random;

//This class is used to build the Map of cells

public class Map extends java.awt.Panel implements Serializable
{
	private static final long serialVersionUID = -4346946366819702498L;
    int w = 50;// sets the width of the grid
    int h = 50;// sets the height of the grid
    transient Image buffer;

    
    GridCell gridCell[][] = new GridCell[w][h];// creates a new instance of gridcell
	
    public Map()
	{
		setLayout(new GridLayout(w,h));//creates a new grid based on the width and height
        for(int y=0;y<w;y++)// loops as many times as the width is set to
        {
            for(int x=0;x<h;x++)//loops as many times as the height is set to
            {
               gridCell[x][y] = new GridCell();// creates a new gridcell
               gridCell[x][y].setPosition(new Point(x,y));// sets the position of the gridcell
               add(gridCell[x][y]); // adds the gridcell
            }
        }
    }
    
    public void paint(Graphics g)
    {
        if(buffer == null)
        {
        	buffer = createImage(getBounds().width,getBounds().height);
        }
        Graphics bg = buffer.getGraphics();
        super.paint(bg);
        bg.setColor(Color.red);
        g.drawImage(buffer,0,0,null);
    }
    
    public void update(Graphics g)
    {
        paint(g);
    }
        


    public GridCell[] getsurrounding(GridCell g)// gets the eight cells

    {
        GridCell next[] = new GridCell[8];// creates an array of 8 gridcells
        Point p = g.getPosition();// gets the position of the given cell
        if(p.y!=0)// makes sure the y isnt outside the grid array
        {
        	next[0]=gridCell[p.x][p.y-1];//north
        	
        }
        if(p.x!=w-1)// makes sure the x isnt outside the grid array
        {
        	next[1]=gridCell[p.x+1][p.y];//east
        	
        }
        
        if(p.y!=h-1)// makes sure the y isnt outside the grid array
        {
        	next[2]=gridCell[p.x][p.y+1];//south

        	}
        if(p.x!=0)// makes sure the x isnt outside the grid array
        {
        	next[3]=gridCell[p.x-1][p.y];//west

        	}
        if(p.y!=0)// makes sure the y isnt outside the grid array
        {
            if(p.x!=w-1)// makes sure the x isnt outside the grid array
            {
            	next[4]=gridCell[p.x+1][p.y-1];//north east

            }

        }
        if(p.y!=0)// makes sure the y isnt outside the grid array
        {
            if(p.x!=0)// makes sure the x isnt outside the grid array
            {
            	next[5]=gridCell[p.x-1][p.y-1];//north west

            }

        }
        if(p.y!=h-1)// makes sure the y isnt outside the grid array
        {
            if(p.x!=w-1)// makes sure the x isnt outside the grid array
            {
            	next[6]=gridCell[p.x+1][p.y+1];//south east

            }

        }
        if(p.y!=h-1)// makes sure the y isnt outside the grid array
        {
            if(p.x!=0)// makes sure the x isnt outside the grid array
            {
            	next[7]=gridCell[p.x-1][p.y+1];//south west

            }

        }
        

        return next;//returns the cell
    }
    
    public GridCell getlowestsurrounding(GridCell g)//gets the best next cell
    {
        GridCell next[] = getsurrounding(g);
        GridCell small = next[0];
        double dist = Double.MAX_VALUE;
        double nextDist = Double.MAX_VALUE;
        for(int i=0;i<8;i++)
        {
        	
            if(next[i]!=null)
            {
                nextDist = next[i].getDistFromStart();//sets the next distance to the cells distance from the start cell
              
                if(nextDist<dist && nextDist>=0)
                {
                	small = next[i];
                	
                    dist = next[i].getDistFromStart();

                    //if(nextDist==olddist){System.out.println("FUCK");}
                }
                
            }
        }
       // Point 



        return small;

        
    }
    public GridCell[] nextperson(GridCell g)// gets the 8 surrounding cells for the crowd sim

    {
        GridCell next[] = new GridCell[11];// creates an array of 8 gridcells
        Point p = g.getPosition();// gets the position of the given cell
        if(p.y!=1)// makes sure the y isnt outside the grid array
        {
        	next[0]=gridCell[p.x][p.y-1];//south

        }
        else
        {
        	next[0]=gridCell[p.x][p.y];//same
        }
        	
        if(p.x!=w-2)// makes sure the x isnt outside the grid array
        {
        	next[1]=gridCell[p.x+1][p.y];//east

        }
        else
        {
        	next[1]=gridCell[p.x][p.y];//same

        }
        
        if(p.y!=h-2)// makes sure the y isnt outside the grid array
        {
        	next[2]=gridCell[p.x][p.y+1];//north

        	}
     
            else
            {
            	next[2]=gridCell[p.x][p.y];//same

            }
        
        if(p.x!=1)// makes sure the x isnt outside the grid array
        {
        	next[3]=gridCell[p.x-1][p.y];//west

        	}
        else
        {
        	next[3]=gridCell[p.x][p.y];//same

        }
        if(p.y!=1)// makes sure the y isnt outside the grid array
        {
            if(p.x!=w-2)// makes sure the x isnt outside the grid array
            {
            	next[4]=gridCell[p.x+1][p.y-1];//south east

            }
            else
            {
            	next[4]=gridCell[p.x][p.y];//same

            }

        }
        else
        {
        	next[4]=gridCell[p.x][p.y];//same

        }
        
        if(p.y!=1)// makes sure the y isnt outside the grid array
        {
            if(p.x!=1)// makes sure the x isnt outside the grid array
            {
            	next[5]=gridCell[p.x-1][p.y-1];//south west

            }
            else
            {
            	next[5]=gridCell[p.x][p.y];//same

            }

        }
        else
        {
        	next[5]=gridCell[p.x][p.y];//same

        }
        if(p.y!=h-2)// makes sure the y isnt outside the grid array
        {
            if(p.x!=w-2)// makes sure the x isnt outside the grid array
            {
            	next[6]=gridCell[p.x+1][p.y+1];//north east

            }
            else
            {
            	next[6]=gridCell[p.x][p.y];//same

            }//right

        }
        else
        {
        	next[6]=gridCell[p.x][p.y];//same

        }
        
        if(p.y!=h-2)// makes sure the y isnt outside the grid array
        {
            if(p.x!=1)// makes sure the x isnt outside the grid array
            {
            	next[7]=gridCell[p.x-1][p.y+1];//north west

            }
            else
            {
            	next[7]=gridCell[p.x][p.y];//same

            }

        }
        else
        {
        	next[7]=gridCell[p.x][p.y];//same

        }
        next[8]=gridCell[p.x][p.y];//same
        next[9]=gridCell[p.x][p.y];//same
        next[10]=gridCell[p.x][p.y];//same
        

        return next;//returns the cell
    }
    
    public GridCell getnextperson(GridCell g) //gets the next person cell 
    {
        GridCell next[] = nextperson(g);
        GridCell small = next[0];

		Random finishcell = new Random();
		int x = finishcell.nextInt(11);

        int num = 0;
        if (x==0){num=0;}
        if (x==1){num=1;}
        if (x==2){num=2;}
        if (x==3){num=3;}
        if (x==4){num=4;}
        if (x==5){num=5;}
        if (x==6){num=6;}
        if (x==7){num=7;}
        if (x==8){num=8;}//three chances for it to stay in the same place
        if (x==9){num=9;}
        if (x==10){num=10;}

                	small = next[num];
                	if (small.getCost()==GridCell.BLOCK)
                	{
                		small=g;
                	}
                	
        return small;   
    }
    
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException
    {
        GridCell.tidy=false;
        ois.defaultReadObject();
        GridCell.setShowPath(false);
    }


}