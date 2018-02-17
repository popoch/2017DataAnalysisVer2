import java.awt.geom.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Drawmap extends JFrame {
	
	private DrawingPanel drawingpanel;

	
	public Drawmap() {
		setResizable(false);
		setBounds(0, 0, 1920, 1080);
		drawingpanel = new DrawingPanel();		
		setContentPane(drawingpanel);
		drawingpanel.repaint();
	}
	
	public class DrawingPanel extends JPanel {
				
		@Override
		protected void paintComponent(Graphics g) {	
			float startX = this.getWidth()/2;
			float startY = this.getHeight()/2;
			float endX = 0;;
			float endY = 0;;
			float centerX;
			float centerY;
			
			Data.startX = startX;
			Data.startY = startY;
			
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());	//background

			g.setColor(Color.WHITE);
			g.drawLine(0, this.getHeight(), this.getWidth(), this.getHeight());
			g.drawLine(0, this.getHeight(), 0, 0);
			
			g.drawString("Source file : " + Data.file_name, 10, this.getHeight()-30);
			
			Graphics2D g2d = (Graphics2D)g; 
			
			g.setColor(Color.RED);
			for(int count = 0 ; count < Data.pData.size() ; count ++) {
				centerX = Float.valueOf(Data.pData.get(count).x) - 5.0f;
				centerY = Float.valueOf(Data.pData.get(count).y) - 5.0f;
				
				Ellipse2D.Float L2D = new Ellipse2D.Float(centerX, centerY, 10, 10); 
				
				g2d.draw(L2D);
			}
			
			centerX = startX - 5.0f;
			centerY	= startY - 5.0f;
			
			g.setColor(Color.BLUE);
			
			g2d.draw(new Ellipse2D.Float(startX-156f, startY-156f, 313.6f, 313.6f));
			//g2d.draw(new Ellipse2D.Float(startX-83.15f, startY-(156f+83.15f), 166.3f, 166.3f));
			
			Ellipse2D.Float L2D = new Ellipse2D.Float(centerX, centerY, 10, 10); //center point
			g2d.draw(L2D);

			//draw again the points that are in certain area
			if(Data.flag == 1) {
				g.setColor(Color.CYAN);
				for(int count = 0 ; count < Data.realData.size() ; count++) {
					centerX = Float.valueOf(Data.realData.get(count).x) - 5.0f;
					centerY = Float.valueOf(Data.realData.get(count).y) - 5.0f;
					Ellipse2D.Float L2D2 = new Ellipse2D.Float(centerX, centerY, 10, 10); 
					
					g2d.draw(L2D2);
				}
				
				for(int times = Data.sTime; times <= Data.eTime + 1; times++) {
					endX = Data.startX +(float)Math.cos(Math.toRadians(90+Data.change*times)) * -(156f);
					endY = Data.startY + (float)Math.sin(Math.toRadians(90+Data.change*times)) * -(156f);
					
					g2d.draw(new Line2D.Float(startX, startY, endX, endY));	//draw line at detected point 
					g2d.draw(new Ellipse2D.Float(endX-83.15f, endY-83.15f, 166.3f, 166.3f));	//draw circle at detected point
				}
			}
			g.setColor(Color.BLACK);
			
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
			g.drawString("Number of detected point : " + Data.realData.size(), 10, this.getHeight()-90);
			g.drawString("Source file : " + Data.file_name, 10, this.getHeight()-60);
			g.drawString("Participant number : " + Data.number, 10, this.getHeight()-30);
			
		}
	}
}
