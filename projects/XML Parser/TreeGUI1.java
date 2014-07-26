/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//package finalFile;

/**
 *
 * @author ASP
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;

public class TreeGUI1 extends JPanel {

    public Map<String, String> cont = new HashMap<String, String>();
    
    // current selection
    Node selection = null;
    
    // node being edited
    NodeEditor editor = null;
    
    // feedback for link dragging
    Point linkEnd = null;
    Node linkTarget = null;
    
    // font size radio button group
    JComboBox fontSizeCombo;

    // main program
    /*public static void main(String[] args) {
	TreeGUI1 tree = new TreeGUI1 ();
	JFrame frame = tree.makeFrame ();
	frame.show ();
    }*/
    
    // Tree is a panel
    public TreeGUI1 (String s1) {
        // no layout manager
        setLayout (null);
        String s = s1;
        // mouse listeners
        addMouseListener (mouseListener);
        addMouseMotionListener (mouseListener);
        
        // set size
        setPreferredSize (new Dimension (10000, 10000));

        // clear the selection, so that buttons and menu items get disabled
	setSelection (null);

        // add a fake tree
        addDebugData (s);
    }

    // make a frame around the tree panel
    public JFrame makeFrame () {
	JFrame f = new JFrame ("Tree Editor");
		
	// exit on close
	//f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
	// put a scroller around tree
	JScrollPane scroller = new JScrollPane (this);
	scroller.setPreferredSize (new Dimension (400, 300));
      
	// make menu bar
	JMenuBar menubar = new JMenuBar ();
	       
	JMenu fileMenu = new JMenu ("File");
	fileMenu.add (exitAction);
	menubar.add (fileMenu);

	JMenu editMenu = new JMenu ("Edit");
	editMenu.add (deleteAction);
	menubar.add (editMenu);
	        
	f.setJMenuBar (menubar);

	// make toolbar
	Box toolbar = new Box (BoxLayout.X_AXIS);

	// make delete button		
	JButton deleteButton = new JButton (deleteAction);
	deleteButton.setText ("");
	deleteButton.setIcon (new ImageIcon ("delete.gif"));
	toolbar.add (deleteButton);

	// make font size combo box		
	toolbar.add (new JLabel ("         Font size:  "));
			
	fontSizeCombo = new JComboBox (FONT_SIZES);
	fontSizeCombo.setSelectedItem (DEFAULT_FONT_SIZE);
	fontSizeCombo.addActionListener (fontSizeAction);
	fontSizeCombo.setEditable (false);
	fontSizeCombo.setPreferredSize (new Dimension (40, 0));
	toolbar.add (fontSizeCombo);
		
	toolbar.add (toolbar.createGlue());
	toolbar.setBorder (BorderFactory.createEmptyBorder (2, 2, 2, 2));

	// lay out window
	Container contentPane = f.getContentPane ();
	contentPane.setLayout (new BorderLayout ());
	contentPane.add (toolbar, BorderLayout.NORTH);
	contentPane.add (scroller, BorderLayout.CENTER);
	f.pack ();
	return f;
    }
	
    // make a simple tree for debugging
    private void addDebugData (String s) {                    
        int lev=0,le=0;
    	Stack st = new Stack();
        Stack nd=new Stack();  
        Stack con=new Stack();
        //System.out.printf(s);
    	//String s = "<?xml version=\"1.0\"?><catalog><book><author>Gambardella,Matthew</author><title>XML Developer's Guide</title><genre>Computer</genre><price>44.95</price><publish_date>2000-10-01</publish_date><description>An in-depth look at creating applications     with XML.</description>   </book><book><author>Ralls, Kim</author><title>Midnight Rain</title><genre>Fantasy</genre><price>5.95</price><publish_date>2000-12-16</publish_date><description>A former architect battles corporate zombies, an evil sorceress, and her own childhood to become queen of the world.</description></book><book><author>Corets, Eva</author><title>Maeve Ascendant</title><genre>Fantasy</genre><price>5.95</price><publish_date>2000-11-17</publish_date><description>After the collapse of a nanotechnology society in England, the young survivors lay the foundation for a new society.</description></book><book><author>Corets, Eva</author><title>Oberon's Legacy</title> <genre>Fantasy</genre><price>5.95</price><publish_date>2001-03-10</publish_date><description>In post-apocalypse England, the mysterious agent known only as Oberon helps to create a new life for the inhabitants of London. Sequel to Maeve Ascendant.</description></book><book><author>Corets, Eva</author><title>The Sundered Grail</title><genre>Fantasy</genre><price>5.95</price><publish_date>2001-09-10</publish_date><description>The two daughters of Maeve, half-sisters, battle one another for control of England. Sequel to Oberon's Legacy.</description></book><book><author>Randall, Cynthia</author><title>Lover Birds</title><genre>Romance</genre><price>4.95</price><publish_date>2000-09-02</publish_date><description>When Carla meets Paul at an ornithology conference, tempers fly as feathers get ruffled.</description></book><book><author>Thurman, Paula</author><title>Splish Splash</title><genre>Romance</genre><price>4.95</price><publish_date>2000-11-02</publish_date><description>A deep sea diver finds true love twenty thousand leagues beneath the sea.</description></book><book><author>Knorr, Stefan</author><title>Creepy Crawlies</title><genre>Horror</genre><price>4.95</price><publish_date>2000-12-06</publish_date><description>An anthology of horror stories about roaches,centipedes, scorpions  and other insects.</description>   </book>   <book><author>Kress, Peter</author> <title>Paradox Lost</title> <genre>Science Fiction</genre><price>6.95</price><publish_date>2000-11-02</publish_date><description>After an inadvertant trip through a Heisenberg Uncertainty Device, James Salway discovers the problems of being quantum.</description></book><book><author>O'Brien, Tim</author><title>Microsoft .NET: The Programming Bible</title><genre>Computer</genre><price>36.95</price><publish_date>2000-12-09</publish_date><description>Microsoft's .NET initiative is explored in detail in this deep programmer's reference.</description></book><book><author>O'Brien, Tim</author><title>MSXML3: A Comprehensive Guide</title><genre>Computer</genre><price>36.95</price><publish_date>2000-12-01</publish_date><description>The Microsoft MSXML3 parser is covered in detail, with attention to XML DOM interfaces, XSLT processing,SAX and more.</description></book><book><author>Galos, Mike</author><title>Visual Studio 7: A Comprehensive Guide</title><genre>Computer</genre><price>49.95</price><publish_date>2001-04-16</publish_date><description>Microsoft Visual Studio 7 is explored in depth,looking at how Visual Basic, Visual C++, C#, and ASP+ are integrated into a comprehensive development environment.</description> </book></catalog>";
		String t = "<?xml version = \"1.0\"?>";
		String k = "";	
                String str="";
                String z="";

        String[] node = new String[10];
		char[] Array = s.toCharArray();
		String notAllowed = "1234567890";       
		int j,l;
		for(j = 0; j < s.length(); j++){
			k += Character.toString(s.charAt(j));
			if(k.equals(t)){
				l = j;
				k = "";
				break;
			}
		}
		int status = 0;
		for(int i = j; i < s.length(); i++){
			if(s.charAt(i) == '<'){
				i++;
				if(s.charAt(i) == '/'){
                                    con.push(str);
                                    System.out.print(str);
                                    //str="";
					i++;
					while(s.charAt(i) != '>'){
						k += Character.toString(s.charAt(i));
	                    i++;
					}
                                        cont.put(k, str);
                                                    //System.out.printf(z+"hell");
                                                   // z="";
					if(!(st.empty())) {
						String r = (String)st.pop();
                                                
						if(k.equals(r)){                                                    
                                                    Node a=(Node)nd.pop();                                                       
                                                if(!(nd.empty()))a.setParent((Node)nd.peek());  
                                                le-=1;
                                                    if(status == 1){                                                                                                       
                                                       System.out.printf("</"+r+">\n");
		                    k = "";
                                    status = 2;
                                                    }
                                                    else{
                                                        System.out.println();
                                                        for(int p = 0; p < st.size()+1; p++){
                                                            System.out.printf("\t");
                                                        }
                                                        System.out.printf("</"+r+">\n");                                                        
		                    k = "";
                                                    }
							
		                
                                }
						else{
							System.out.println("Error in xml file");
						}
					}
					else {
    					// TODO Auto-generated catch block
						System.out.println("Error");
					}
                                        str="";
				} 
                else{
                    //i++;
                	//j = 0;
                	//System.out.println("i = " + i + " j = " + j);
                	while(s.charAt(i) != '>'){
                		k += Character.toString(s.charAt(i));
                		i++;
                        if(s.charAt(i) == ' '){
                        	break;
                        }
				    }
    			    while(s.charAt(i) != '>'){//z += Character.toString(s.charAt(i));
    			    	i++;
    			    }
    			    //System.out.println("k = " + k + " value = " + k.substring(0, 1).contains(notAllowed) + " " + k.substring(0, 1));
				    /*if(notAllowed.contains(k.substring(0, 1))){
				    	System.out.println("Error in xml file: " + k);
				    }*/
    			    st.push(k);
                            Node n=makeNode (k, new Point (70+(lev*120),20+(le*200)));
                                                    nd.push(n);
                                                    lev+=1;
                                                   le+=1;
                            if(st.size() == 0){
                                System.out.printf("<"+k+">");
                                
				    k = "";
                                    status = 1;
                            }
				   else{
                                System.out.println();
                                        for(int y = 0; y < st.size(); y++){
                                            System.out.printf("\t");
                                        }
                                        System.out.printf("<"+k+">");                                        
				    k = "";
                                    status = 1;
                                    }
				    //status = 1;
				}
			}
                        else str+=s.charAt(i);
			
    	}
                for (Map.Entry<String, String> entry : cont.entrySet()) {
                                                   String key = entry.getKey();
                                                   String value = entry.getValue();
                                                   System.out.println("key: " + key + ", values: " + value);
                                              }
    
    }
    


    // Tree nodes are components, extending JLabels.
    // (Tree links are strokes drawn by paintComponent().)
    public class Node extends JLabel {
        Node parent = null;
        Set children = new HashSet ();
        // invariants:
	//   if parent != null, parent.children contains this
	//   c in children if and only if c.parent = this
	// Preserved by setParent().

        public Node (String text, Point location) {
            super (text);
            setLocation (location);
            setSize (getPreferredSize ());
        }

        public void setText (String text) {
            super.setText (text);
            setSize (getPreferredSize ());
        }
        
        public void setFontSize (int size) {
	    Font f = getFont ();
	    setFont (new Font (f.getName(), f.getStyle (), size));
	    setSize (getPreferredSize ());
	    this.repaint ();
        }
        
        public int getFontSize () {
	    return getFont ().getSize ();
        }
        
        public void setParent (Node newParent) {
            if (parent != null)
                parent.children.remove (this);
            this.parent = newParent;
            if (newParent != null)
                newParent.children.add (this);
        }
        
	// unlinks this node from both parents and children,
	// in preparation for deleting it
        public void removeAllLinks () {
            setParent (null);

	    // change children to a different set,
	    // so that it's safe to iterate even though
	    // children's setParent() will try to mutate it
            Set oldChildren = children;
            children = new HashSet ();
            for (Iterator g = oldChildren.iterator (); g.hasNext (); )
                ((Node) g.next ()).setParent (null);
        }

        // paint node, including possibly selection handles and link hotspot
        public void paint (Graphics g) {
            super.paint (g);

            if (selection == this || linkTarget == this) {
                Dimension d = getSize ();
            	
            	// draw selection handles for selected node
            	// and for link target
                g.setColor (Color.black);           
                g.fillRect(0, 0, 4, 4);
                g.fillRect(d.width - 4, 0, 4, 4);
                g.fillRect(0, d.height - 4, 4, 4);
                g.fillRect(d.width - 4, d.height - 4, 4, 4);
                
                if (selection == this) {
		    // draw link hotspot only for selected node
                    g.setColor (Color.red);
                    g.drawArc(d.width/2 - 4, 0, 8, 8, 0, 360);
                }
            }
        }

        // true if pt (in Tree coordinate system) falls in this node's
	// link hotspot
        public boolean hitsLinkHotspot (Point pt) {
            Rectangle r = getBounds ();
            int x = pt.x - r.x;
            int y = pt.y - r.y;
            x -= (r.width/2 - 4);
            return (-2 < x && x < 10 && -2 < y && y < 10);
        }
        
	// paint this node's link to its parent
	// g is a Graphics for the Tree, not just for this Node.
        public void paintLink (Graphics g) {
            Rectangle myRect = getBounds ();           
            int x1 = myRect.x + myRect.width/2;
            int y1 = myRect.y + 4;

            int x2;
            int y2;
            if (parent != null) {
                Rectangle parentRect = parent.getBounds ();
                x2 = parentRect.x + parentRect.width/2;
                y2 = parentRect.y + parentRect.height;
            } else if (selection == this && linkEnd != null) {
                x2 = linkEnd.x;
                y2 = linkEnd.y;
            } else {
                return;
            } 
            
            g.drawLine (x1, y1, x2, y2);
            
        }

    } // end of Node class


    // Resuming Tree methods.

    // paint links and nodes
    public void paintChildren (Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        int n = getComponentCount ();        
        for (int i = 0; i < n; ++i) {
            Component c = getComponent (i);
            if (c instanceof Node)
                ((Node) c).paintLink (g2);
        }

        super.paintChildren (g);        
    }

    // make a new node
    public Node makeNode (String text, Point pt) {
        Node n = new Node (text, pt);
        add (n);
        return n;
    }
        
    // remove a node
    public void removeNode (Node node) {
	remove (node);
	node.removeAllLinks ();
	repaint ();
    }
    

    // edit in place
    public void startEditing (Node node) {
        editor = new NodeEditor (node);
        add (editor);        
        editor.grabFocus ();
    }

    public void stopEditing () {
        if (editor == null)
            return;

	editor.stop ();
        remove (editor);
        editor = null;
        repaint ();
    }
    // NodeEditor is a standard text box. 
    public class NodeEditor extends JTextField {
    	Node node;        
    	
    	public NodeEditor (Node node) {
	    super (node.getText ());
    		
	    this.node = node;
    		
	    setFont (node.getFont ());
	    addKeyListener (new KeyAdapter () {
		    public void keyTyped(KeyEvent e) {
			switch (e.getKeyChar ()) {
			case '\n':
			case 0x1B:
			    stopEditing ();
			    break;
			default:
			    break;
			}
		    }
    		});
	    selectAll ();

	    Dimension d = getPreferredSize ();
	    d.width += 50;
	    setSize (d);

	    setLocation (node.getLocation ());
    	}
    	
    	public void stop () {
	    if (getText ().length () == 0)
		removeNode (node);
	    else
		node.setText (editor.getText ());
    	}
    }
    
    // Mouse listener
    // for selecting nodes, moving nodes, and dragging out links
    private MouseInputListener mouseListener = new MouseInputAdapter () {
	    boolean dragging = false;
	    int offsetX;
	    int offsetY;
        
	    public void mousePressed(MouseEvent e) {
		Component c = getComponentAt (e.getPoint());
		if (c instanceof Node)
		    setSelection ((Node) c);
		else if (selection == null) {
		    Node n = makeNode ("", e.getPoint ());
		    setSelection (n);
		    startEditing (n);
		} else {
		    setSelection (null);
		}    
                
	    }
        
	    public void mouseClicked (MouseEvent e) {
		if (selection != null && e.getClickCount () > 1) {
		    startEditing (selection);
		}                
                if (selection !=null && (e.getButton() == MouseEvent.BUTTON3)){
                    String a=cont.get(selection.getText ());                    
                    JOptionPane.showMessageDialog(null,a);                    
                }
	    }
        
	    public void mouseDragged(MouseEvent e) {
		if (selection == null)
		    return;
             
		if (dragging) {
		    selection.setLocation (e.getX () + offsetX, 
					   e.getY () + offsetY);
		    repaint ();
		}
		else if (linkEnd != null) {
		    Component c = getComponentAt (e.getPoint());
		    if (c instanceof Node && c != selection)
			linkTarget = (Node) c;
		    else
			linkTarget = null;

		    linkEnd = e.getPoint ();
		    repaint ();
		}
		else if (selection.hitsLinkHotspot (e.getPoint ())) {
		    selection.setParent (null);
		    linkEnd = e.getPoint ();
		    linkTarget = null;
		    repaint ();
		}
		else {
		    dragging = true;
		    offsetX = selection.getX () - e.getX ();
		    offsetY = selection.getY () - e.getY ();
		}
	    }

	    public void mouseReleased (MouseEvent e) {
		if (dragging)
		    dragging = false;
		else if (linkEnd != null) {
		    if (linkTarget != null)
			selection.setParent (linkTarget);
		    linkEnd = null;
		    linkTarget = null;
		    repaint ();
		}
	    }
	};


    // select a node
    public void setSelection (Node n) {
        stopEditing ();
        
        if (selection != null)
            selection.repaint ();
            
        selection = n;
        
        if (selection != null)
            selection.repaint ();

	deleteAction.setEnabled (selection != null);

	if (selection != null) {
	    Integer size = new Integer (selection.getFontSize ());
	    for (int i = 0; i < FONT_SIZES.length; ++i)
		if (size.equals (FONT_SIZES[i]))
		    fontSizeCombo.setSelectedItem (FONT_SIZES[i]);
	}
    }


    // exit program
    private Action exitAction = new AbstractAction ("Exit") {
	    public void actionPerformed(ActionEvent e) {
		System.exit (0);
	    }
	};   
    {
	exitAction.putValue (Action.ACCELERATOR_KEY, 
			     KeyStroke.getKeyStroke(KeyEvent.VK_Q, 
						    KeyEvent.CTRL_MASK));
    }
    
    // delete a node
    private Action deleteAction = new AbstractAction ("Delete") {
	    public void actionPerformed(ActionEvent e) {
		if (selection == null) {
		    // shouldn't happen because action should be disabled,
		    // but reject it anyway 
		    getToolkit ().beep ();
		    return;
		}

		removeNode (selection);
		setSelection (null);
	    }
	};

    {
        deleteAction.putValue (Action.ACCELERATOR_KEY, 
			       KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
    }

    // change font size
    private ActionListener fontSizeAction = new ActionListener () {
	    public void actionPerformed(ActionEvent e) {
		if (selection != null) {
		    int size = 
			((Integer) fontSizeCombo.getSelectedItem ())
			.intValue ();
		    selection.setFontSize (size);
		}
	    }
	};
	
    private static Integer FONT_SIZES[] = { 
	new Integer (8), 
	new Integer (12), 
	new Integer (18), 
	new Integer (30)
    };
	
    private static Integer DEFAULT_FONT_SIZE = FONT_SIZES[1];

}