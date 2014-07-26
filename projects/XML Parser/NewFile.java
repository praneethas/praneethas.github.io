/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//package finalFile;

import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 *
 * @author ASP
 */
public class NewFile {
    
    JTextArea textArea = new JTextArea();
    String title = "Untitled - XML Parser";
    public NewFile(JTextArea textarea, JFrame mainframe){
        this.textArea = textarea;
        mainframe.setTitle(this.title);
    }
    
}
