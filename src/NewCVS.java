
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class NewCVS {
	
	public JFrame frame;
	public Thread th2;
	public JTextField Start_Time_TextField;
	public JTextField End_Time_TextField;
	
	public NewCVS() {
		run();
	}

	private void run() {
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		
////////////////////////////////////////////////////////////////////////main frame
		frame = new JFrame();
		frame.setBounds(width/2-250, height/2-150, 500, 300);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
////////////////////////////////////////////////////////////////////////button = file import
		JButton btnFileImport = new JButton("File Import");
		btnFileImport.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				Data.original.clear();
				Data.pData.clear();
				Data.flag = 0;
				
				JFileChooser JFC = new JFileChooser("/Users/nnmi2/Desktop/fixation_data");
				JFC.setFileFilter(new FileNameExtensionFilter("CSV", "csv"));
				JFC.setMultiSelectionEnabled(false);
				JFC.showOpenDialog(frame);
				
				Data.sTime = Integer.valueOf(Start_Time_TextField.getText());
				Data.eTime = Integer.valueOf(End_Time_TextField.getText());
				
				saveData(JFC.getSelectedFile());
			}	
		});
		btnFileImport.setBounds(15, 15, 150, 25);
		frame.getContentPane().add(btnFileImport);

////////////////////////////////////////////////////////////////////////button = start analysis
		JButton btnInitAnalysis = new JButton("Draw Map");
		btnInitAnalysis.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				Data.draw.repaint();
				Data.draw.setVisible(true);
			}
		});
		btnInitAnalysis.setBounds(15, 50, 150, 25);
		frame.getContentPane().add(btnInitAnalysis);

		
////////////////////////////////////////////////////////////////////////button = start area analysis		
		JButton btnDetecButton = new JButton("Detect Data");
		btnDetecButton.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				contain();
				Data.flag = 1;
			}
		});
		btnDetecButton.setBounds(15, 85, 150, 25);
		frame.getContentPane().add(btnDetecButton);

////////////////////////////////////////////////////////////////////////label = window size
		JLabel lblWindowSize = new JLabel("Start_Time_TextField");
		lblWindowSize.setBounds(310, 15, 80, 25);
		frame.getContentPane().add(lblWindowSize);

////////////////////////////////////////////////////////////////////////text input field = window size
		Start_Time_TextField = new JTextField();
		Start_Time_TextField.setText("0");
		Start_Time_TextField.setBounds(400, 15, 80, 25);
		frame.getContentPane().add(Start_Time_TextField);
		Start_Time_TextField.setColumns(10);

////////////////////////////////////////////////////////////////////////label = weight
		JLabel lblWeight = new JLabel("End_Time_TextField");
		lblWeight.setBounds(310, 50, 80, 25);
		frame.getContentPane().add(lblWeight);

////////////////////////////////////////////////////////////////////////text input field = weight
		End_Time_TextField = new JTextField();
		End_Time_TextField.setText("0");
		End_Time_TextField.setBounds(400, 50, 80, 25);
		frame.getContentPane().add(End_Time_TextField);
		End_Time_TextField.setColumns(10);


////////////////////////////////////////////////////////////////////////button = start analysis
		JButton btnNorButton = new JButton("Export Data");
		btnNorButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser JFC = new JFileChooser("/Users/nnmi2/Desktop/fixation_data/Export");				
				JFC.setSelectedFile(new File("Exported_"+Data.file_name));
				int retrival = JFC.showSaveDialog(null);
			    exportData(retrival, JFC);
			}
		});
		btnNorButton.setBounds(172, 165, 150, 25);
		frame.getContentPane().add(btnNorButton);
		
////////////////////////////////////////////////////////////////////////button = window size fix to one	
		JButton btnWndAnalysis = new JButton("Window Size one");
		btnWndAnalysis.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				windowSizeOne();
			}
		});
		btnWndAnalysis.setBounds(15, 120, 150, 25);
		frame.getContentPane().add(btnWndAnalysis);
		
////////////////////////////////////////////////////////////////////////button = window size fix to one	
		JButton btnWndvAnalysis = new JButton("Window Size Various");
		btnWndvAnalysis.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				windowSizeVarious();
			}
		});
		btnWndvAnalysis.setBounds(15, 155, 150, 25);
		frame.getContentPane().add(btnWndvAnalysis);
		
	}
	

	
	
////////////////////////////////////////////////////////////////////////save data
	private void saveData(File inputFile) {
		try {
			Data.original.clear();
			Data.pData.clear();
			Data.original = (ArrayList<String>) Files.readAllLines(inputFile.toPath(), Charset.forName("UTF-8"));
			Data.original.remove(0);
			
			inputFile.getName();
			Data.file_name = inputFile.getName();
			
			for(String temp : Data.original) {
				String[] divide = temp.split(",", -1);
				Pupildata eye = new Pupildata();
				
				eye.x = Float.valueOf(divide[4]);
				eye.y = Float.valueOf(divide[5]);
				
				
				Data.pData.add(eye);
			}
			
			if(Data.sTime == 0 && Data.eTime == 0) {
				if(Data.file_name.equalsIgnoreCase("eyeassu_fixation.csv")) {
					Data.sTime = Integer.valueOf(49);
					Data.eTime =  Integer.valueOf(51);
					Data.number = String.valueOf("Participant 6");
				} else if(Data.file_name.equalsIgnoreCase("HaejinPark.csv")) {
					Data.sTime = Integer.valueOf(63);
					Data.eTime = Integer.valueOf(70);
					Data.number = String.valueOf("Participant 10");
				} else if(Data.file_name.equalsIgnoreCase("KyungheeSeo.csv")) {
					Data.sTime = Integer.valueOf(51);
					Data.eTime = Integer.valueOf(57);
					Data.number = String.valueOf("Participant 13");
				} else if(Data.file_name.equalsIgnoreCase("sajida_fixation.csv")) {
					Data.sTime = Integer.valueOf(53);
					Data.eTime = Integer.valueOf(53);
					Data.number = String.valueOf("Participant 9");
				} else if(Data.file_name.equalsIgnoreCase("SliegiKim.csv")) {
					Data.sTime = Integer.valueOf(19);
					Data.eTime = Integer.valueOf(21);
					Data.number = String.valueOf("Participant 5");
				} else if(Data.file_name.equalsIgnoreCase("YoungennKim.csv")) {
					Data.sTime = Integer.valueOf(75);
					Data.eTime = Integer.valueOf(77);
					Data.number = String.valueOf("Participant 7");
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
////////////////////////////////////////////////////////////////////////export data	
	private void exportData(int retrival, JFileChooser JFC) {

		try	{
		    if (retrival == JFileChooser.APPROVE_OPTION) {
				    	
		    	FileWriter fw = new FileWriter(JFC.getSelectedFile());
		    	
//				for(int count = 0; count < Data.pData.size(); count++) {
//				    	fw.write(String.valueOf(Data.pData.get(count).x) + ","
//				    			+ String.valueOf(Data.pData.get(count).y) + "\n");
//			    	}
		    	
		    	for(int count = 0; count < Data.size.length; count++) {
			    	fw.write(String.valueOf(count) + ","
			    			+ String.valueOf(Data.size[count]) + "\n");
		    	}
			    	fw.close();
			    }
			} catch(Exception ex) {
				ex.printStackTrace();
			}
	}
	
////////////////////////////////////////////////////////////////////////analyze data		
	private void contain() {
		Data.realData.clear();
		Data.mainData.clear();
		//distance between two points
		double d = 0;
		
		//determine whether points are in meaningful area or not (big circle - small circle)
		for(int i = 0; i < Data.pData.size(); i++) {
			d = Math.sqrt(((Data.pData.get(i).x - Data.startX)*(Data.pData.get(i).x - Data.startX)) + ((Data.pData.get(i).y - Data.startY)*(Data.pData.get(i).y - Data.startY)));
			if(d < (Data.rBig + Data.rSmall) || d == (Data.rBig + Data.rSmall)) {
				if(d > (Data.rBig - Data.rSmall) || d == (Data.rBig - Data.rSmall)) {
					try{
						Pupildata p = new Pupildata();
						p.x = Float.valueOf(Data.pData.get(i).x);
						p.y = Float.valueOf(Data.pData.get(i).y);
						Data.mainData.add(p);
					} catch (Exception e) {
						
					}
				}
			}
		}
		
		//determine again in specific time 
		for(int times = Data.sTime; times <= Data.eTime + 1; times++) {
			float endX = Data.startX +(float)Math.cos(Math.toRadians(90+Data.change*times)) * -(156f);
			float endY = Data.startY + (float)Math.sin(Math.toRadians(90+Data.change*times)) * -(156f);
			
			float centerX = endX;	//CENTER OF SMALL CIRCLE
			float centerY = endY;
			
			for(int i = 0; i < Data.mainData.size(); i++) {
				d = Math.sqrt(((Data.mainData.get(i).x - centerX)*(Data.mainData.get(i).x - centerX)) + ((Data.mainData.get(i).y - centerY)*(Data.mainData.get(i).y - centerY)));
				if(d < (Data.rSmall + 18.89f) || d == (Data.rSmall + 18.89f)) {
					try{
						Pupildata p = new Pupildata();
						p.x = Float.valueOf(Data.mainData.get(i).x);
						p.y = Float.valueOf(Data.mainData.get(i).y);

						Data.realData.add(p);
					} catch (Exception e) {
						
					}
				}
			}
		}
		
		//delete same points in list
		for(int j = 0; j < Data.realData.size() - 1; j++) {
			for(int z = j + 1; z <  Data.realData.size(); z++) {
				if(Data.realData.get(j).x == Data.realData.get(z).x && Data.realData.get(j).y == Data.realData.get(z).y) {
					Data.realData.remove(z);
				}
			}
		}
		
		System.out.println(Data.sTime);
		System.out.println(Data.eTime);
	}
	
	public void windowSizeOne () {
		
		double d = 0;
		
		for(int window = 1; window <= 91; window++) {
			Data.realData.clear();
			for(int times = window; times <= window+1; times++) {
				float endX = Data.startX +(float)Math.cos(Math.toRadians(90+Data.change*times)) * -(156f);
				float endY = Data.startY + (float)Math.sin(Math.toRadians(90+Data.change*times)) * -(156f);
				
				float centerX = endX;	//CENTER OF SMALL CIRCLE
				float centerY = endY;
				
				for(int i = 0; i < Data.mainData.size(); i++) {
					d = Math.sqrt(((Data.mainData.get(i).x - centerX)*(Data.mainData.get(i).x - centerX)) + ((Data.mainData.get(i).y - centerY)*(Data.mainData.get(i).y - centerY)));
					if(d < (Data.rSmall + 18.89f) || d == (Data.rSmall + 18.89f)) {
						try{
							Pupildata p = new Pupildata();
							p.x = Float.valueOf(Data.mainData.get(i).x);
							p.y = Float.valueOf(Data.mainData.get(i).y);
	
							Data.realData.add(p);
						} catch (Exception e) {
							
						}
					}
					
				}
				
			}
			
			//delete same points in list
			for(int j = 0; j < Data.realData.size() - 1; j++) {
				for(int z = j + 1; z <  Data.realData.size(); z++) {
					if(Data.realData.get(j).x == Data.realData.get(z).x && Data.realData.get(j).y == Data.realData.get(z).y) {
						Data.realData.remove(z);
					}
				}
			}
			Data.size[window] = Data.realData.size();
		}
	}
	
	
	public void windowSizeVarious () {
		
		double d = 0;
		
		int dif = Data.eTime - Data.sTime;
		
		Data.size[0] = dif;
		for(int window = 1; window <= 91; window++) {
			Data.realData.clear();
			for(int times = window; times <= window+dif+1; times++) {
				float endX = Data.startX +(float)Math.cos(Math.toRadians(90+Data.change*times)) * -(156f);
				float endY = Data.startY + (float)Math.sin(Math.toRadians(90+Data.change*times)) * -(156f);
				
				float centerX = endX;	//CENTER OF SMALL CIRCLE
				float centerY = endY;
				
				for(int i = 0; i < Data.mainData.size(); i++) {
					d = Math.sqrt(((Data.mainData.get(i).x - centerX)*(Data.mainData.get(i).x - centerX)) + ((Data.mainData.get(i).y - centerY)*(Data.mainData.get(i).y - centerY)));
					if(d < (Data.rSmall + 18.89f) || d == (Data.rSmall + 18.89f)) {	//0.3cm for error range = 11.33f
						try{														//.5cm = 18.89f
							Pupildata p = new Pupildata();
							p.x = Float.valueOf(Data.mainData.get(i).x);
							p.y = Float.valueOf(Data.mainData.get(i).y);
	
							Data.realData.add(p);
						} catch (Exception e) {
							
						}
					}
					
				}
				
			}
			
			//delete same points in list
			for(int j = 0; j < Data.realData.size() - 1; j++) {
				for(int z = j + 1; z <  Data.realData.size(); z++) {
					if(Data.realData.get(j).x == Data.realData.get(z).x && Data.realData.get(j).y == Data.realData.get(z).y) {
						Data.realData.remove(z);
					}
				}
			}
			Data.size[window] = Data.realData.size();
		}
	}
}
