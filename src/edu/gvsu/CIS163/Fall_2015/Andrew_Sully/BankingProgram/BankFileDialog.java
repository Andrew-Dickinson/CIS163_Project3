package edu.gvsu.CIS163.Fall_2015.Andrew_Sully.BankingProgram;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Andrew on 10/27/15.
 */
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
     ******************************************************************/
    public BankModel openDialog() {
        int status = super.showOpenDialog(parent);

        if (status == JFileChooser.APPROVE_OPTION) {
            File fileToSave = super.getSelectedFile();
            FileFilter extFilter = super.getFileFilter();
            String filePath = "" + fileToSave.getAbsolutePath();

            try {
                if (extFilter.equals(txtFilter)) {
                    if (!filePath.endsWith(txtFilter.getExtensions()[0])) {
                        filePath += txtFilter.getExtensions()[0];
                    }

                    //Generate and return the appropriate BankModel
                    BankModel bm = new BankModel();
                    bm.loadFromTextFile(filePath);
                    return bm;
                } else if (extFilter.equals(bnkFilter)) {
                    if (!filePath.endsWith(bnkFilter.getExtensions()[0])) {
                        filePath += bnkFilter.getExtensions()[0];
                    }

                    //Generate and return the appropriate BankModel
                    BankModel bm = new BankModel();
                    bm.loadFromBinaryFile(filePath);
                    return bm;
                } else if (extFilter.equals(xmlFilter)) {
                    if (!filePath.endsWith(xmlFilter.getExtensions()[0])) {
                        filePath += xmlFilter.getExtensions()[0];
                    }

                    //Generate and return the appropriate BankModel
                    BankModel bm = new BankModel();
                    bm.loadFromXMLFile(filePath);
                    return bm;
                }
            } catch (IOException | IllegalArgumentException e){
                //There was a problem with loading the file
                return null;
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
        int status = super.showSaveDialog(parent);

        if (status == JFileChooser.APPROVE_OPTION) {
            File fileToSave = super.getSelectedFile();
            FileFilter extFilter = super.getFileFilter();
            String filePath = "" + fileToSave.getAbsolutePath();

            try {
                if (extFilter.equals(txtFilter)) {
                    if (!filePath.endsWith(txtFilter.getExtensions()[0])) {
                        filePath += txtFilter.getExtensions()[0];
                    }

                    bm.saveToTextFile(filePath);
                } else if (extFilter.equals(bnkFilter)) {
                    if (!filePath.endsWith(bnkFilter.getExtensions()[0])) {
                        filePath += bnkFilter.getExtensions()[0];
                    }

                    bm.saveToBinaryFile(filePath);
                } else if (extFilter.equals(xmlFilter)) {
                    if (!filePath.endsWith(xmlFilter.getExtensions()[0])) {
                        filePath += xmlFilter.getExtensions()[0];
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
