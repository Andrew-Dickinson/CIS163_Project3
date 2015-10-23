package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

import javax.swing.*;

/***********************************************************************
 * Launches the BankingProgram GUI so that the user can interact
 **********************************************************************/
public class Driver {
    public static void main(String[] args){
        JFrame frame = new JFrame("Bank Application");

        JPanel panel = new BankPanel(frame);

        frame.add(panel);
        frame.setSize(500,500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
