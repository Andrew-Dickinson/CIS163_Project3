package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Andrew on 10/29/15.
 */
public abstract class MouseClickListener implements MouseListener {
    //We only care about the mouseClicked method.
    //Do nothing for everything else
    @Override
    public void mouseEntered(MouseEvent arg0) {}

    @Override
    public void mouseExited(MouseEvent arg0) {}

    @Override
    public void mousePressed(MouseEvent arg0) {}

    @Override
    public void mouseReleased(MouseEvent arg0) {}
}
