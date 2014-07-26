/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//package finalFile;

import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 *
 * @author Praneeth
 */
public class SaveAsFile {
    
    JFileChooser chooser;
    JTextArea textArea;
    JFrame mainFrame;
    File file;
    int statusOpen = 0;
    
    public SaveAsFile(JFrame mainFrame1, JTextArea textArea1, JFileChooser chooser1){
         this.chooser = chooser1;
         this.textArea = textArea1;
         this.mainFrame = mainFrame1;
         statusOpen = 1; 
         int saas = chooser.showDialog(mainFrame, "Save As...");
         try{
          File file = chooser.getSelectedFile();
          String name = file.toString();
          FileOutputStream fout = new FileOutputStream(name, true);
          if (saas == chooser.APPROVE_OPTION){
        fout.write(("\n" + textArea.getText()).getBytes());
          }
          else if(saas != chooser.APPROVE_OPTION){
           System.out.println("Saving Canceled");
          }
          fout.close();
         }
         catch (Exception ex){
         }
    }
    
}
