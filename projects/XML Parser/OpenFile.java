//package finalFile;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASP
 */
public class OpenFile {

    
    JFileChooser chooser;
    JTextArea textArea;
    JFrame mainFrame;
    File file;
    int statusOpen = 0;
    public OpenFile(){
        statusOpen = 1;
    }
    
    public int getStatusOpened(){
        //statusOpen = 1;
        return statusOpen;
    }
    
    public OpenFile(JFrame mainFrame1, JTextArea textArea1, JFileChooser chooser1){
           this.chooser = chooser1;
           this.textArea = textArea1;
           this.mainFrame = mainFrame1;
           statusOpen = 1; 
           int open = chooser.showOpenDialog(mainFrame);
           try{
               this.file = chooser.getSelectedFile();
               mainFrame.setTitle(file.getAbsolutePath() + " - XML Verifier");
               FileInputStream fin = new FileInputStream(file);
               DataInputStream din = new DataInputStream(fin);
               String s = "";
               if (open == chooser.APPROVE_OPTION){
                    s = din.readLine();
                    while(s != null){
                         textArea.append(s + "\n");
                         s = din.readLine();
                     }
               }
               else if(open != chooser.APPROVE_OPTION){
                    System.out.println("Open Failure");
               }
               fin.close();
           }
           catch (Exception ex){
           }
     }
    
     public File getOpenFile(){
         return file;
     }
}



                