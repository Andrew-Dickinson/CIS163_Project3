package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/***********************************************************************
 * An simple extension of the MouseListener that's only focused on
 * MouseClick events. Allows other code to be a lot cleaner
 **********************************************************************/
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
