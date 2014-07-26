/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//package finalFile;

import java.util.Stack;
import javax.swing.JTextArea;

/**
 *
 * @author ASP
 */
public class DocumentTree {
    
    public static JTextArea text1 = new JTextArea();
    
    public DocumentTree(JTextArea text){
        this.text1 = text;
    }
    
    public DocumentTree(){
        
    }
    
    public void makeDTree(String s){
                System.out.println("String s = " + s);
                Stack st = new Stack();
                String t = "<?xml version = \"1.0\"?>";
		String k = "";
		String[] node = new String[10];
		char[] Array = s.toCharArray();
		String notAllowed = "1234567890";
                int j,l;
		for(j = 0; j < s.length(); j++){
			k += Character.toString(s.charAt(j));
                        System.out.println("k = " + s.charAt(j) + ", k = " + k);
			if(k.equals(t)){
				l = j;
				k = "";
                                System.out.println("Equals");
				break;
			}
		}
		int status = 0;
		for(int i = j; i < s.length(); i++){
			if(s.charAt(i) == '<'){
				i++;
				if(s.charAt(i) == '/'){
					i++;
					while(s.charAt(i) != '>'){
						k += Character.toString(s.charAt(i));
	                    i++;
					}
					if(!(st.empty())) {
						String r = (String)st.pop();
						if(k.equals(r)){
                                                    if(status == 1){
                                                       text1.setText(text1.getText() + "</"+r+">\n");
		                    k = "";
                                    status = 2;
                                                    }
                                                    else{
                                                        text1.setText(text1.getText() + "\n");
                                                        for(int p = 0; p < st.size()+1; p++){
                                                            text1.setText(text1.getText() + "\t");
                                                        }
                                                        text1.setText(text1.getText() + "</"+r+">\n");
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
    			    while(s.charAt(i) != '>'){
    			    	i++;
    			    }
    			    //System.out.println("k = " + k + " value = " + k.substring(0, 1).contains(notAllowed) + " " + k.substring(0, 1));
				    /*if(notAllowed.contains(k.substring(0, 1))){
				    	System.out.println("Error in xml file: " + k);
				    }*/
    			    st.push(k);
                            if(st.size() == 0){
                                text1.setText(text1.getText() + "<"+k+">");
				    k = "";
                                    status = 1;
                            }
				   else{
                                text1.setText(text1.getText() + "\n");
                                        for(int y = 0; y < st.size(); y++){
                                            text1.setText(text1.getText() + "\t");
                                        }
                                        text1.setText(text1.getText() + "<"+k+">");
				    k = "";
                                    status = 1;
                                    }
				    //status = 1;
				}
			}
			
    	}
     
    }
     
}
