package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/***********************************************************************
 * A custom implementation of JFileChooser for the purpose of having a
 * drop-down with multiple custom file filters and having the parsing be
 * called by methods in this class. Will spit out an appropriate
 * BankModel based on a selected file or will accept a BankModel and
 * output it to an appropriate file
 **********************************************************************/
public class BankFileDialog extends JFileChooser {
    /**
     * The parent JPanel for this dialog
     */
    Component parent;

    /**
     * The extensions that are used for saving and loading
     */
    FileNameExtensionFilter txtFilter = new FileNameExtensionFilter(
            "TXT files", "txt");
    FileNameExtensionFilter bnkFilter = new FileNameExtensionFilter(
            "BNK files", "bnk");
    FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter(
            "XML files", "xml");

    /*******************************************************************
     * Construct (but do not display) the custom file dialog
     * @param parent The parent component to this dialog
     ******************************************************************/
    public BankFileDialog(Component parent){
        this.parent = parent;
        super.setAcceptAllFileFilterUsed(false);
        super.addChoosableFileFilter(txtFilter);
        super.addChoosableFileFilter(bnkFilter);
        super.addChoosableFileFilter(xmlFilter);
    }

    /*******************************************************************
     * Display a file choosing dialog for the purpose of selecting a
     * BankModel to import
     * @return The imported BankModel or null if canceled
     * @throws IOException If there's a problem reading the file
     * @throws IllegalArgumentException if the file's in the wrong format
     ******************************************************************/
    public BankModel openDialog() throws IOException {
        //Display and capture the status of the superclass dialog
        int status = super.showOpenDialog(parent);

        //If the dialog exited smoothly
        if (status == JFileChooser.APPROVE_OPTION) {
            //Get the file to open from the superclass dialog
            File fileToOpen = super.getSelectedFile();
            FileFilter extFilter = super.getFileFilter();
            String filePath = "" + fileToOpen.getAbsolutePath();

            //Figure out what type of file the user selected
            if (extFilter.equals(txtFilter)) {
                //It was a txt file

                //Automatically append the file extension if not done
                //by the user
                if (!filePath.endsWith(txtFilter.getExtensions()[0])) {
                    filePath += txtFilter.getExtensions()[0];
                }

                //Generate and return the appropriate BankModel
                BankModel bm = new BankModel();
                bm.loadFromTextFile(filePath);
                return bm;
            } else if (extFilter.equals(bnkFilter)) {
                //It was a binary file

                //Automatically append the file extension if not done
                //by the user
                if (!filePath.endsWith(bnkFilter.getExtensions()[0])) {
                    filePath += bnkFilter.getExtensions()[0];
                }

                //Generate and return the appropriate BankModel
                BankModel bm = new BankModel();
                bm.loadFromBinaryFile(filePath);
                return bm;
            } else if (extFilter.equals(xmlFilter)) {
                //It was an XML file

                //Automatically append the file extension if not done
                //by the user
                if (!filePath.endsWith(xmlFilter.getExtensions()[0])) {
                    filePath += xmlFilter.getExtensions()[0];
                }

                //Generate and return the appropriate BankModel
                BankModel bm = new BankModel();
                bm.loadFromXMLFile(filePath);
                return bm;
            }
        }

        //The dialog box was likely canceled
        return null;
    }

    /*******************************************************************
     * Display a file choosing dialog for the purpose of selecting a
     * location to export a BankModel to
     * @param bm The BankModel to write out in a file
     * @return The terminating status of the dialog
     ******************************************************************/
    public DialogStatus saveDialog(BankModel bm) {
        //Display and capture the status of the superclass dialog
        int status = super.showSaveDialog(parent);

        //If the dialog exited smoothly
        if (status == JFileChooser.APPROVE_OPTION) {
            //Get the location to save to from the superclass dialog
            File fileToSave = super.getSelectedFile();
            FileFilter extFilter = super.getFileFilter();
            String filePath = "" + fileToSave.getAbsolutePath();

            try {
                if (extFilter.equals(txtFilter)) {
                    //It is a txt file

                    //Automatically append the file extension if not
                    //done by the user
                    if (!filePath.endsWith("."
                            + txtFilter.getExtensions()[0])) {
                        filePath += "." + txtFilter.getExtensions()[0];
                    }

                    bm.saveToTextFile(filePath);
                } else if (extFilter.equals(bnkFilter)) {
                    //It is a binary file

                    //Automatically append the file extension if not
                    //done by the user
                    if (!filePath.endsWith("."
                            + bnkFilter.getExtensions()[0])) {
                        filePath += "." + bnkFilter.getExtensions()[0];
                    }

                    bm.saveToBinaryFile(filePath);
                } else if (extFilter.equals(xmlFilter)) {
                    //It is an XML file

                    //Automatically append the file extension if not
                    //done by the user
                    if (!filePath.endsWith("."
                            + xmlFilter.getExtensions()[0])) {
                        filePath += "." + xmlFilter.getExtensions()[0];
                    }

                    bm.saveToXMLFile(filePath);
                }
            } catch (IOException | IllegalArgumentException e){
                //There was a problem with saving the file
                return DialogStatus.OPERATION_FAILED;
            }

            //No error happened
            return DialogStatus.OPERATION_SUCCESSFUL;

        } else if (status == JFileChooser.CANCEL_OPTION){
            //The user canceled the dialog box
            return DialogStatus.OPERATION_CANCELED;
        }

        //Something bad happened
        return DialogStatus.OPERATION_FAILED;
    }
}
