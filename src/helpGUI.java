import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

//This Class is used to display a map of the university on execution

public class helpGUI extends java.applet.Applet implements Runnable
{
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	private JPanel panel = new JPanel(new GridLayout(4,2)) 
	{
		private static final long serialVersionUID = 1L;
		protected void paintComponent(Graphics g)//paints the image as the background of the frame
		{
			super.paintComponent(g);
			if(image != null)
			{
				g.drawImage(image, 0, 0, this);
			}
		}
	};
	
	public helpGUI()
	{
		try
		{
			File file=new File("FinalMap2.png");// gets the file
			image = ImageIO.read(file);//reads
			Dimension imageSize = new Dimension(image.getWidth(), image.getHeight());
			panel.setPreferredSize(imageSize);

		}
		catch(Exception e)
		{
		
		}
	}
	
	public void run() 
	{
		System.out.println("tee");
		helpGUI gui = new helpGUI();
        gui.init();
    	JFrame frame = new JFrame("University Map ");
    	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	frame.getContentPane().add(new helpGUI().getPanel());

    	frame.setSize(1070,450);
    	frame.setLocation(600,400);
    	frame.setVisible(true);
    	gui.start();	
	}

	public JPanel getPanel(){
	return panel;
	}

        public static void main(String args[])
        {
            helpGUI gui = new helpGUI();
            gui.init();
        	JFrame frame = new JFrame("University Map ");
        	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        	frame.getContentPane().add(new helpGUI().getPanel());

        	frame.setSize(1070,450);
        	frame.setLocation(600,400);
        	frame.setVisible(true);
        	gui.start();

        }






}