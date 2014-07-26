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
public class SaveFile {
    
    JFileChooser chooser;
    JTextArea textArea;
    JFrame mainFrame;
    File file;
    int statusOpen = 0;
    
    public SaveFile(JFrame mainFrame1, JTextArea textArea1, JFileChooser chooser1){
         this.chooser = chooser1;
         this.textArea = textArea1;
         this.mainFrame = mainFrame1;
         statusOpen = 1; 
         int save = chooser.showSaveDialog(mainFrame);
         try{
          File file = chooser.getSelectedFile();
          mainFrame.setTitle(file.getAbsolutePath() + " - XML Verifier");
          String name = file.toString();
          FileOutputStream fout = new FileOutputStream(name, false);
          if (save == chooser.APPROVE_OPTION){
           fout.write(("\n" + textArea.getText()).getBytes());
          }
          else if(save != chooser.APPROVE_OPTION){
           System.out.println("Saving Canceled");
          }
          fout.close();
         }
         catch (Exception ex){
         }
    }
    
}
