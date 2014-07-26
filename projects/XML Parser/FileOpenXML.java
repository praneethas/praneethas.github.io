/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//package finalFile;

/**
 *
 * @author Praneeth
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//package xml;

/**
 *
 * @author ASP
 */

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileOpenXML {

    public FileOpenXML() {
        initUI();
    }
    
    public static int error = -1; // Error status checker

    public static final JFrame mainFrame = new JFrame(); // Opens MainFrame  window of te software
    public static final JFileChooser chooser = new JFileChooser(); //Filechooser for selecting files
    public static final FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter("xml files (*.xml)", "xml");
    public static final FileNameExtensionFilter textfilter = new FileNameExtensionFilter("text files (*.txt)", "txt");
                
    public static JMenuBar menubar = new JMenuBar();
        
    public static final JTextArea textArea1 = new JTextArea();  // Opens XML file in this text area
    public static final JTextArea textArea2 = new JTextArea();  // Shows Compilation Status 
    public static final JTextArea textArea3 = new JTextArea();  // Shows Document Tree
    public static final JScrollPane scrollPane1 = new JScrollPane();
    public static final JScrollPane scrollPane2 = new JScrollPane();
    public static final JScrollPane scrollPane3 = new JScrollPane();
        
    public static void addComponents(JFrame mainFrame, JScrollPane scrollPane1, JScrollPane scrollPane2, JScrollPane scrollPane3, Container frame){
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
	//c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 0;
	c.gridy = 0;
        //c.gridheight = 100;
        //c.gridwidth = 100;
        frame.add(scrollPane1, c);
        /*c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 0;
	c.gridy = 100;
        frame.add(scrollPane2);
        c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 0;
	c.gridy = 3;
        frame.add(scrollPane3);*/
        System.out.println(mainFrame.getHeight() + " " + mainFrame.getWidth());
    }
    
    public static void add1(JFrame mainframe, Container pane){
        mainframe.setPreferredSize(new Dimension(1366, 768));
        textArea1.setLineWrap(true);
        textArea1.setEditable(true);
        textArea2.setLineWrap(true);
        textArea2.setEditable(true);
        textArea3.setLineWrap(true);
        textArea3.setEditable(true);
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
	//c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = -1;
	c.gridy = -1;
        scrollPane1.add(textArea1);
        scrollPane1.setViewportView(textArea1);
        scrollPane1.setPreferredSize(new Dimension(400, 650));
        //c.gridheight = 100;
        //c.gridwidth = 100;
        pane.add(scrollPane1, c);
        c.gridx = 10;
	c.gridy = 0;
        scrollPane2.add(textArea2);
        scrollPane2.setViewportView(textArea2);
        scrollPane2.setPreferredSize(new Dimension(400, 650));
        //c.gridheight = 100;
        //c.gridwidth = 100;
        pane.add(scrollPane2, c);
        c.gridx = 20;
	c.gridy = 0;
        scrollPane3.add(textArea3);
        scrollPane3.setViewportView(textArea3);
        scrollPane3.setPreferredSize(new Dimension(400, 650));
        //c.gridheight = 100;
        //c.gridwidth = 100;
        pane.add(scrollPane3, c);
        mainframe.pack();
        
    }
    
    public static final void initUI() {

        chooser.setFileFilter(xmlfilter);
        chooser.setFileFilter(textfilter);
        chooser.setDialogTitle("Open XML,TXT file");
        // set selected filter
        
        try {

            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

        } 
        catch (Exception e) {
           // handle exception
        }
        
        //FileOpenXML.addComponents(mainFrame, scrollPane1, scrollPane2, scrollPane3, mainFrame.getContentPane());
        FileOpenXML.add1(mainFrame, mainFrame.getContentPane());
        
        JMenu file = new JMenu("File");
        JMenu options = new JMenu("Options");
        JMenu about = new JMenu("About");
        file.setMnemonic(KeyEvent.VK_F);
        options.setMnemonic(KeyEvent.VK_O);
        about.setMnemonic(KeyEvent.VK_A);
        
        //file.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        
        JMenuItem eMenuItem1 = new JMenuItem("New");//, icon);
        eMenuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        eMenuItem1.setToolTipText("New text File");
        eMenuItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event11) {
                NewFile newfile = new NewFile(textArea1, mainFrame);
            }
        });
        
        JMenuItem eMenuItem2 = new JMenuItem("Open");//, icon);
        eMenuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        eMenuItem2.setToolTipText("Opens New XML,TXT file");
        eMenuItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event12) {
                OpenFile open = new OpenFile(mainFrame, textArea1, chooser);
            }
        });
        
        JMenuItem eMenuItem3 = new JMenuItem("Save");//, icon);
        eMenuItem3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        eMenuItem3.setToolTipText("Saves Current File");
        eMenuItem3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event13) {
                SaveFile save = new SaveFile(mainFrame, textArea1, chooser);
            }
        });
        
        JMenuItem eMenuItem4 = new JMenuItem("Save As...");//, icon);
        eMenuItem4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        eMenuItem4.setToolTipText("Saves current file in different format");
        eMenuItem4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event14) {
                SaveAsFile saveas = new SaveAsFile(mainFrame, textArea1, chooser);
            }
        });
        
        JMenuItem eMenuItem5 = new JMenuItem("Quit");//, icon);
        eMenuItem5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        eMenuItem5.setToolTipText("Quit application");
        eMenuItem5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        
        JMenuItem oMenuItem1 = new JMenuItem("Compile XML File");//, icon);
        oMenuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        oMenuItem1.setToolTipText("Compiles XML for Errors");
        oMenuItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event21) {
                Compile compile = new Compile(textArea2);
                compile.compile(textArea1.getText());
            }
        });

        JMenuItem oMenuItem2 = new JMenuItem("Build Document Tree");//, icon);
        oMenuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        oMenuItem2.setToolTipText("Builds Document Tree");
        oMenuItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event22) {
                DocumentTree dtree = new DocumentTree(textArea3);
                dtree.makeDTree(textArea1.getText());
            }
        });
        
        JMenuItem oMenuItem3 = new JMenuItem("Build GUI Tree");//, icon);
        oMenuItem3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        oMenuItem3.setToolTipText("Builds Tree in GUI");
        oMenuItem3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event23) {
                TreeGUI1 gui = new TreeGUI1(textArea1.getText());
                JFrame frame = gui.makeFrame ();
                frame.show ();
            }
        });
        
        file.add(eMenuItem1);
        file.add(eMenuItem2);
        file.add(eMenuItem3);
        file.add(eMenuItem4);
        file.add(eMenuItem5);
        options.add(oMenuItem1);
        options.add(oMenuItem2);
        options.add(oMenuItem3);
        
        menubar.add(file);
        menubar.add(options);
        mainFrame.setVisible(true);
        mainFrame.setTitle("XML Parser");
        mainFrame.setJMenuBar(menubar);

        //mainFrame.setSize(300, 200);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    }
    
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                FileOpenXML ex = new FileOpenXML();
                //ex.setVisible(true);
            }
        });
    }
}
