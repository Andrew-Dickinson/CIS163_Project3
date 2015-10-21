package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/***********************************************************************
 * Displays the GUI for interacting with the banking program
 **********************************************************************/
//TODO: Create the gui :P
// In the BankGUI class, set the BankModel as the model for the
// JList in the BankGUI class using the setModel() method of
// the JList class.

public class BankPanel extends JFrame {
	private JFrame frame; 
	private JPanel panel;
	private JMenu file;
	private JMenu sort;
	private JMenuItem quit;
	private JMenuItem saveToB;
	private JMenuItem loadFromB;
	private JMenuItem saveToT;
	private JMenuItem loadFromT;
	private JMenuItem saveToX;
	private JMenuItem loadFromX;
	private JTable table;
	private JMenuBar menuBar;

	public BankPanel(){
		ButtonListener butListener = new ButtonListener();
		frame = new JFrame("Bank Application"); 
		menuBar = new JMenuBar();
		file = new JMenu("File");
		sort = new JMenu("Sort");
		quit = new JMenuItem("Quit");
		panel = new JPanel();
		saveToB = new JMenuItem("Save To Binary");
		loadFromB = new JMenuItem("Load From Binary");
		saveToT = new JMenuItem("Save To Txt");
		loadFromT = new JMenuItem("Load From Txt");
		saveToX = new JMenuItem("Save To XML");
		loadFromX = new JMenuItem("Load From XML");
		table = new JTable();
		
		quit.addActionListener(butListener);
		saveToB.addActionListener(butListener);
		loadFromB.addActionListener(butListener);
		saveToT.addActionListener(butListener);
		loadFromT.addActionListener(butListener);
		saveToX.addActionListener(butListener);
		loadFromX.addActionListener(butListener);
		
		file.add(saveToB);
		file.add(loadFromB);
		file.addSeparator();
		file.add(saveToT);
		file.add(loadFromT);
		file.addSeparator();
		file.add(saveToX);
		file.add(loadFromX);
		file.addSeparator();
		file.add(quit);
		menuBar.add(file);
		menuBar.add(sort);
		
		panel.add(table);
		
		frame.add(panel);
		frame.setJMenuBar(menuBar);
		frame.setSize(500,500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new BankPanel();
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if(quit == event.getSource()){
				System.exit(0);
			}
			
			if(saveToB == event.getSource()){
				//TODO: save to binary
			}
			
			if(loadFromB == event.getSource()){
				//TODO: load from binary
			}
			
			if(saveToT == event.getSource()){
				//TODO: save to text
			}
			
			if(loadFromT == event.getSource()){
				//TODO: load from text
			}
			
			if(saveToX == event.getSource()){
				//TODO: save to XML
			}
			
			if(loadFromX == event.getSource()){
				//TODO: load from XML
			}
			
		}
	}
}
