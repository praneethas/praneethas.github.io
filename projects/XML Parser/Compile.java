/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//package finalFile;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import javax.swing.JTextArea;

/**
 *
 * @author ASP
 */
public class Compile {
    public static int errorStatus = 1;
    public static JTextArea text = new JTextArea();
    
    public Compile(JTextArea textArea){
        this.text = textArea;
        //String s = text.getText();
    }
    
    public Compile(){
        
    }
    //public static String s = text.getText();
    
    public static void compile(String s){
        //String s =  "<note><from name = \"Jani\"><!-- Comment -->Rama</from></note>";           
        Map<String, String> AM = new HashMap<String, String>();
        String[] tokens = {"<!--","-->","<","</",">","<?","?>"};
        String comment = ""; text.setForeground(Color.red);
        text.setText("");
                            String getTagName = "";
        String t = "xml version=\"1.0\"";
        char[] length = s.toCharArray();
        String[] attPunc = {"=", "\""};
        String[] punctuations = {",",".",":","\"","?","!","(",")","{","}","[","]","-","_"};
        String[] numbers = {"0","1","2","3","4","5","6","7","8","9"};
        String[] letters = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        ArrayList<String> endNodes = new ArrayList<String>();
        ArrayList<String> sentenceInfo = new ArrayList<String>();
        ArrayList<String> startNodes = new ArrayList<String>();
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> comments = new ArrayList<String>();
        ArrayList<String> attributeVal = new ArrayList<String>();
        ArrayList<String> attributes1 = new ArrayList<String>();
        ArrayList<String> attribute = new ArrayList<String>();
        int status = -1;
        String buff = "";
        Stack openTag = new Stack();
        Stack closeTag = new Stack();
        int i = 0;
            //System.out.println("Length of String = " + length.length);
        if(s.charAt(i) == ' '){
            while(s.charAt(i) == ' '){
                i++;
            }
        }
        if(Arrays.asList(tokens).contains(s.substring(i, i+2)) && s.substring(i, i+2).equals("<?")){
            i += 2;
            //System.out.println("charat = " + s.charAt(i));
            while(!(Arrays.asList(tokens).contains(s.substring(i, i+2)) && s.substring(i, i+2).equals("?>"))){
                buff += s.charAt(i);
                if(!((i+2)<length.length)){
                    break;
                }
                i++;
            }
            System.out.println("open = " + buff);
            //System.out.println("buff = " + buff);
            /*if(!(buff.equals(t))){
                System.err.println("Opening XML tag not proper");
            }*/
            i += 2;
            //checkOpenTag(buff);
        }
        buff = "";
                    while(i < length.length){
                        status = -1;//errorStatus = 1;
                        boolean condition1 = Arrays.asList(tokens).contains(s.substring(i, i+1)) && s.substring(i, i+1).equals("<");
                        boolean condition2 = Arrays.asList(tokens).contains(s.substring(i, i+2)) && s.substring(i, i+2).equals("</");
                        boolean condition3 = Arrays.asList(tokens).contains(s.substring(i, i+2)) && s.substring(i, i+2).equals("<?");
                        boolean condition4 = Arrays.asList(tokens).contains(s.substring(i, i+2)) && s.substring(i, i+2).equals("?>");
                        boolean condition5 = (i+1)<length.length;
                        boolean condition6 = (i+2)<length.length;
                        boolean condition7 = (i+4)<length.length;

                        if((i+4)<length.length){
                            if(Arrays.asList(tokens).contains(s.substring(i, i+4))){
                                //System.out.println("Comment started");
                                i += 4;
                                while(!(Arrays.asList(tokens).contains(s.substring(i, i+3))) && (i+3)<length.length){
                                    comment += s.charAt(i);
                                    //System.out.print(comment + " " + ", i= " + i + ";");
                                    i++;
                                }
                                i+=3;
                                comments.add(comment);
                                //System.out.println("Comment: " + comment + ", i = " + i);
                                comment = "";
                                //i += 3;
                            }
                        }

            //            System.out.println("Comment read " + i);
            //            System.out.println("condition = " + (condition1 && !(condition2) && !(condition3) && condition6));
            //            System.out.println("condition2 = " + condition2 + " substring = " + s.substring(i, i+2) + " cond2 = " + (Arrays.asList(tokens).contains(s.substring(i, i+2)) && s.substring(i, i+2).equals("</")));
            //            
            //            boolean condVerify = Arrays.asList(tokens).contains(s.substring(i, i+1)) && s.substring(i, i+1).equals("<") 
            //                          && !(Arrays.asList(tokens).contains(s.substring(i, i+2)) && s.substring(i, i+2).equals("</")) 
            //                          && !(Arrays.asList(tokens).contains(s.substring(i, i+2)) && s.substring(i, i+2).equals("<?")) 
            //                          && (i+2)<length.length;
            //            System.out.println("condVerify = " + condVerify);
            //            System.out.println("condition1 = " + condition1 + "condition2 = " + condition2 + "condiiton3 = " + condition3 + " " + s.substring(i, i+1));
                        if((i+2)<length.length){
                              if(Arrays.asList(tokens).contains(s.substring(i, i+1)) && s.substring(i, i+1).equals("<") 
                                      && !(Arrays.asList(tokens).contains(s.substring(i, i+2)) && s.substring(i, i+2).equals("</")) 
                                      && !(Arrays.asList(tokens).contains(s.substring(i, i+2)) && s.substring(i, i+2).equals("<?")) 
                                      && (i+2)<length.length){
                                i++;
                                //System.out.println("char = " + s.charAt(i) + " char1 = " + s.charAt(i+1));
                                if(s.charAt(i) == '>'){
                                  errorStatus = 0;
                                              text.append("Error: Empty tag name\n");
                                  i++;
                                  status = 0;
                                }
                                //System.out.println("status = " + status + " buff = " + buff);
                                    if(status != 0){
                                          while(s.charAt(i) != '>'){
                                            buff += s.charAt(i);
                                            i++;
                                          }
                                          System.out.println("char = " + s.charAt(i) + " buff = " + buff);
                                          getTagName = buff;
                                          //System.out.println("attr = " + Arrays.asList(attPunc).contains(buff) + " buff = " + buff);
                                          if(buff.contains("=\"") || buff.contains("=") || buff.contains("\"")){
                                              //System.out.println("It seems that string has attributes");
                                              System.out.println("getTagName before = " + getTagName + ",");
						getTagName = tagName(buff);
                                                System.out.println("getTagName before replace = " + getTagName + "," + "lenf = " + getTagName.length());
						//getTagName.replaceAll("\\s+","");
                                                String sg = "";
                                                //int lenf = getTagName.length();
                                                int r = 0;
                                                while(getTagName.charAt(r) != ' '){
                                                    sg += getTagName.charAt(r);
                                                    r++;
                                                }
                                                getTagName = ""; getTagName += sg;
                                              System.out.println("getTagName after = " + getTagName + "," + "lenf = " + getTagName.length() + "sg = " + sg + ",");
											  buff = buff.substring(getTagName.length());
											  AM = attributeMake(buff);
                                              for (Map.Entry<String, String> entry : AM.entrySet()) {
                                                   String key = entry.getKey();
                                                   String value = entry.getValue();
                                                   //System.out.println("key: " + key + ", values: " + value);
                                              }
                                              /*for(String token : buff.split(" ")){
                                                    //System.out.println(token);
                                                    attributes1.add(token);
                                              }*/
                                              //buff = attributes1.get(0);
                                          System.out.println("errorStatus = " + errorStatus + "st = " + (checkTag(getTagName, numbers, punctuations)));
                                          if(!(checkTag(getTagName, numbers, punctuations))){
                                              status = 2;
                                              errorStatus = 0;
                                              text.append("Error: " + getTagName + "Doesnot follow tag naming rules");
                                              i++;
                                          }}
                                          System.out.println("getTagName before if = " + getTagName + "," + "status = " + status);
						if(status != 2){
                                            System.out.println("getTagName after if = " + getTagName + ",");
						startNodes.add(getTagName);
                                            buff = "";
                                            i++;
                                          }
                                          //System.out.println("next char above = " + s.charAt(i));
                                    }
                               }
                           }

                           if((i+2)<length.length){
                               if(Arrays.asList(tokens).contains(s.substring(i, i+2)) && s.substring(i, i+2).equals("<?") 
                                       && (i+2)<length.length){
                                   errorStatus = 0;
                                    text.append("Error: Extra ? after <\n");
                                    i += 2;
                                }
                           }

                           if((i+2)<length.length){
                               if(Arrays.asList(tokens).contains(s.substring(i, i+2)) && s.substring(i, i+2).equals("?>") && (i+2)<length.length){
                                   errorStatus = 0; 
                                   text.append("Error: Extra ? before >\n");
                                    i += 2;
                                }
                           }

                           if((i+1)<length.length){
                               if((i == 0 || startNodes.size() == 0) && ((Arrays.asList(numbers).contains(s.substring(i, i+1))) || (Arrays.asList(letters).contains(s.substring(i, i+1))))){
                                    while(s.charAt(i) != '<'){
                                        i++;
                                        if(!((i+1)<length.length)){
                                            break;
                                        }
                                    }
                                    errorStatus = 0;
                                    text.append("Error: Expecting opening tag, Document empty\n");
                               }  
                           }

                           if((i+2)<length.length){
                               if(Arrays.asList(tokens).contains(s.substring(i, i+2)) && s.substring(i, i+2).equals("</")){
                                   i += 2;
                                   if(s.charAt(i) == '>'){
                     
                                       errorStatus = 0;status = 0;
                                       text.append("Error: Empty Closing Tag\n");
                                   }
                                   if(status != 0){
                                       buff = "";
                                       while(s.charAt(i) != '>'){
                                           buff += s.charAt(i);
                                           i++;
                                           if(!((i+1)<length.length)){
                                               break;
                                           }
                                       }
                                       System.out.println("buff = " + buff + ", compare = " + startNodes.get(startNodes.size()-1));
                                       if(buff.equals(startNodes.get(startNodes.size()-1))){
                                           endNodes.add(buff);
                                           startNodes.remove(startNodes.get(startNodes.size()-1));
                                       }
                                       else{
                                           errorStatus = 0;
                                           text.append("Error: Closing tag doesnot match opening tag - " + buff + "\n");// + startNodes.get(startNodes.size()-1) + ", " + startNodes.get(endNodes.size()-1) + "buff = " + buff);
                                       }
                                       
                                       //System.out.println("buff endNode = " + buff);
                                       buff = "";
                                       i++;
                                   }
                               }
                               //System.out.println("next char = " + s.charAt(i));
                           }

                           //System.out.println("sent cond = " + (s.charAt(i-1) == '>'));
                           if(i<length.length){
                               if(s.charAt(i-1) == '>'){
                                   while(s.charAt(i) == ' '){
                                       i++;
                                       if(!((i+1)<length.length)){
                                           break;
                                       }
                                   }
                                   buff = "";
                                   while(s.charAt(i) != '<'){
                                       buff += s.charAt(i);
                                       i++;
                                       if(!((i)<length.length)){
                                           break;
                                       }
                                   }
                                   sentenceInfo.add(buff);
                                   //System.out.println("sent = " + buff);
                                   buff = "";
                                }
                           }
                           
            //            System.out.println("Comments:");
            //            printList(comments);
                        System.out.print("\nstartNodes: ");
                       printList(startNodes);
                       System.out.print("\nendNodes: ");
                        printList(endNodes);
                        System.out.println();
            //            System.out.println("\nSentences:");
            //            printList(sentenceNode);
                           
                    }
                    if(errorStatus == 1){
                            text.setForeground(Color.BLACK);
                               text.append("Compilation successful\n");
                           }
                }

                 public static boolean checkTag(String buff, String[] num, String[] punc){
                     int i = 0, len = buff.length();
                     System.out.println("buff = " + buff);
                     System.out.println("len = " + len);
                     if(buff.substring(0,1) == " "){
					     errorStatus = 0;
						 text.append("Error: Invalid element name: Cannot start with space\n");
					 }
					 
                     while(buff.charAt(i) == ' '){
						i++;
					 }
                     if(Arrays.asList(num).contains(buff.charAt(0)) || Arrays.asList(punc).contains(buff.charAt(0))){
                         errorStatus = 0;
                         text.append("Error: Tag cannot start with number or punctuation\n");
                         //return false;
                     }
					 if(!((i+3)<len)){

                     }
                     String xml = "XmL";
                     if(!((i+3)>=len)){
                        if(buff.substring(0,3).equalsIgnoreCase(xml)){
                             errorStatus = 0;
                             text.append("Error: Tag cannot start with " + buff.substring(0,3) + "\n");
                             //return false;
                        }   
                     }
					 if(errorStatus == 0){return false;}
                     else{return true;}
                 }

				 public static String tagName(String s){
					int i = 0;
                                     String buff = "";
					while(s.charAt(i) == ' '){
						buff += s.charAt(i);
						i++;
					 }
					 while(s.charAt(i) != ' '){
						buff += s.charAt(i);
                                                i++;
					 }
					 while(s.charAt(i) == ' '){
						buff += s.charAt(i);
						i++;
					 }
					 return buff;
				 }
				 
                 public static Map attributeMake(String s){
                     Map<String, String> map = new HashMap<String, String>();
                     String buff = "", attr = "", attrName = "";
                     ArrayList<String> attributes = new ArrayList<String>();
                     ArrayList<String> values = new ArrayList<String>();
                     int  i = 0;
                     			System.out.println("aatrMake s = " + s + ", len = " + s.length());
					 while(s.charAt(i) == ' '){
						i++;
					 }
					 while(i < s.length()){
                                             System.out.println("attrMake char = " + s.charAt(i) + ", i = " + i);
                                            /* while((s.charAt(i) != ' ')){// || (s.charAt(i) != '=')){
							buff += s.charAt(i);
                                                        System.out.println("attrMake charin = " + s.charAt(i) + ", i = " + i);
							i++;
						 }
                                             //buff.replaceAll("\\s+","");
						 attributes.add(buff);
                                                 while((s.charAt(i) != '=')){// || (s.charAt(i) != ' ')){
							buff += s.charAt(i);
                                                        System.out.println("attrMake charin = " + s.charAt(i) + ", i = " + i);
							i++;
                                                        if(s.charAt(i) != '='){
                                                            errorStatus = 0;
                                                            text.append("Error: Name value not given to attribute " + "\n");//buff + " i = " + s.charAt(i));
                                                            break;
                                                        }
						 }*/
                                             while(s.charAt(i) != '='){
                                                 buff += s.charAt(i);
                                                 i++;
                                             }
                                             String[] buffNames = buff.split(" ");
                                             if(buffNames.length > 1){
                                                 text.append("Error: Name value not given to attribute " + buffNames[0] + "\n");
                                                 attributes.add(buffNames[buffNames.length - 1]);
                                                 buff = buffNames[buffNames.length - 1];
                                             }
                                             else{
                                                 attributes.add(buffNames[0]);
                                                 buff = buffNames[0];
                                             }
                                             
                                                 System.out.println("aatrMake buff = " + buff);
						 buff = "";
						 if(s.charAt(i) == ' '){
							while(s.charAt(i) == ' '){
								i++;
							}
						}
						if(s.charAt(i) == '='){
							i++;
							 if(s.charAt(i) == ' '){
								while(s.charAt(i) == ' '){
									i++;
								}
							}
							if(s.charAt(i) == '\"'){
								i++;
								while(s.charAt(i) != '\"'){
									buff += s.charAt(i);
									if(i+1 > s.length()){
										errorStatus = 0;
										text.append("Error: Expecting \'\n");
										break;
									}
                                                                        i++;
								}
								values.add(buff);
								System.out.println("aatrval buff = " + buff);
						 buff = "";
							}
							if(s.charAt(i) == '\''){
								i++;
								while(s.charAt(i) != '\''){
									buff += s.charAt(i);
									if(i+1 > s.length()){
										errorStatus = 0;
										text.append("Error: Expecting \'\n");
										break;
									}
                                                                        i++;
								}
								values.add(buff);
								System.out.println("aatrval buff = " + buff);
						 buff = "";
							}
						}
						else{
                                                    errorStatus = 0;
                                                    text.append("Error: no value given to tag " + attributes.get(attributes.size()-1) + "\n");
                                                        values.add(buff);
						}
                                                System.out.println("len i = " + i + "es = " + errorStatus);
						i++;
						map.put(attributes.get(attributes.size()-1),values.get(values.size()-1));
					 }
					 
					 /*for(String token : s.split(" ")){
                        //System.out.println(token);
                        attributes.add(token);
                     }   
                     for(int i = 1; i < attributes.size(); i++){
                         String k = attributes.get(i);
                         int mid = k.indexOf("=\"");
                         attr = k.substring(0, mid);
                         attrName = k.substring(mid+2,k.length()-1);
                         map.put(attr, attrName);
                     }*/
                     return map;
                 }

                 public static void checkOpenTag(String s){
                     System.out.println("s = " + s);
                     int i = 0, vCheck = 0, eCheck = 0, sCheck = 0;
                     String string1 = new String();
                     String string2 = new String();
                     String string3 = new String();
                     String string4 = new String();
                     String string5 = new String();
                     String string6 = new String();
                     String string7 = new String();
                     String string8 = new String();
                     String string9 = new String();
                     String string10 = new String();
                     List<String> encode = Arrays.asList("UTF-8", "UTF-16", "ISO-8859-1", "Windows-1252", "ASCII");
                     if(s.charAt(0) == ' '){
                         errorStatus = 0;
                         System.err.println("Error: Empty target name");
                         while(s.charAt(i) == ' '){
                             i++;
                         }
                     }
                     if(!(s.substring(0,3).equals("xml"))){
                         errorStatus = 0;
                         System.err.println("Error: Invalid PI Name. Expecting xml not " + s.substring(0,3));
                     }
                     i += 3;
                     System.out.println("s(i) = " + s.charAt(i) + ", i = " + i);
                     while(s.charAt(i) == ' '){
                             i++;
                     }
                     while(s.charAt(i) != '='){
                         string1 += s.charAt(i);
                         i++;
                     }
                     System.out.println("string1 = " + string1);
                     string1.replaceAll("\\s+","");
                     if(string1.equalsIgnoreCase("version")){
                         i++;
                         while(s.charAt(i) != '"'){
                             i++;
                         }
                         i++;
                         System.out.println("s(i) = " + s.charAt(i) + ", i = " + i);
                         while(s.charAt(i) != '"'){
                             string2 += s.charAt(i);
                             i++;
                         }
                         i++;
                         System.out.println("string2 = " + string2);
                         if(containsSpace(string2)){
                             errorStatus = 0;
                             System.err.println("Error: Expecting \"");
                             vCheck = 1;
                         }
                         if(vCheck == 0){
                             if(!(string2.equals("1.0"))){
                                 errorStatus = 0;
                                 System.err.println("Error: Unsupported version " + string2);
                             }
                         }
                         while(s.charAt(i) == ' '){
                             i++;
                         }
                         while(s.charAt(i) != '='){
                            string3 += s.charAt(i);
                         }
                        System.out.println("string3 = " + string3);
                        string3.replaceAll("\\s+","");
                        if(string3.equalsIgnoreCase("encoding")){
                             i++;
                             while(s.charAt(i) != '"'){
                                 i++;
                             }
                             while(s.charAt(i) != '"'){
                                 string2 += s.charAt(i);
                                 i++;
                             }
                             if(containsSpace(string3)){
                                 errorStatus = 0;
                                 System.err.println("Error: Expecting \"");
                                 eCheck = 1;
                             }
                             if(eCheck == 0){
                                 if((!(encode.contains(string3)))){
                                     errorStatus = 0;
                                     System.err.println("Error: this encoding format is not supported");
                                 }
                             }
                             while(s.charAt(i) != ' '){
                                 i++;
                             }
                             while(s.charAt(i) != '='){
                                string4 += s.charAt(i);
                             }
                             System.out.println("string4 = " + string4);
                             string4.replaceAll("\\s+","");
                             if(string4.equalsIgnoreCase("standalone")){
                                 i++;
                                 while(s.charAt(i) != '"'){
                                     i++;
                                 }
                                 while(s.charAt(i) != '"'){
                                     string5 += s.charAt(i);
                                     i++;
                                 }
                                 System.out.println("string5 = " + string5);
                                 if(containsSpace(string5)){
                                     errorStatus = 0;
                                     System.err.println("Error: Expecting \"");
                                     sCheck = 1;
                                 }
                                 if(sCheck == 0){
                                     if((!(string5.equals("yes"))) || (!(string5.equals("no")))){
                                         errorStatus = 0;
                                         System.err.println("should be \"yes\" or \"no\"");
                                     }
                             }
                             else{
                                 errorStatus = 0;
                                     System.err.println("Expecting ?>");
                             }
                         }
                             System.out.println("string3 = " + string3);
                         if(string3.equalsIgnoreCase("standalone")){
                             i++;
                             while(s.charAt(i) != '"'){
                                 i++;
                             }
                             while(s.charAt(i) != '"'){
                                 string5 += s.charAt(i);
                                 i++;
                             }
                             if(containsSpace(string5)){
                                 errorStatus = 0;
                                 System.err.println("Error: Expecting \"");
                                 sCheck = 1;
                             }
                             if(sCheck == 0){
                                 if((!(string5.equals("yes"))) || (!(string5.equals("no")))){
                                     errorStatus = 0;
                                     System.err.println("should be \"yes\" or \"no\"");
                                 }
                             }
                         }
                     }
                     }
                     else if(string6.equalsIgnoreCase("encoding")){
                         i++;
                         while(s.charAt(i) != '"'){
                             i++;
                         }
                         while(s.charAt(i) != '"'){
                             string6 += s.charAt(i);
                             i++;
                         }
                         if(containsSpace(string6)){
                             System.err.println("Error: Expecting \"");
                             eCheck = 1;
                         }
                         if(eCheck == 0){
                             if((!(encode.contains(string6)))){
                                 System.err.println("Error: this encoding format is not supported");
                             }
                         }
                         while(s.charAt(i) != ' '){
                             i++;
                         }
                         while(s.charAt(i) != '='){
                            string7 += s.charAt(i);
                         }
                         string7.replaceAll("\\s+","");
                         if(string7.equalsIgnoreCase("standalone")){
                             i++;
                             while(s.charAt(i) != '"'){
                                 i++;
                             }
                             while(s.charAt(i) != '"'){
                                 string8 += s.charAt(i);
                                 i++;
                             }
                             if(containsSpace(string8)){
                                 System.err.println("Error: Expecting \"");
                                 vCheck = 1;
                             }
                             if(sCheck == 0){
                                 if((!(string8.equals("yes"))) || (!(string8.equals("no")))){
                                     System.err.println("should be \"yes\" or \"no\"");
                                 }
                         }
                         else{
                             System.err.println("Expecting ?>");
                         }
                     }
                     }
                     else if(string1.equalsIgnoreCase("standalone")){
                         i++;
                         while(s.charAt(i) != '"'){
                             i++;
                         }
                         while(s.charAt(i) != '"'){
                             string10 += s.charAt(i);
                             i++;
                         }
                         if(containsSpace(string10)){
                             errorStatus = 0;
                             System.err.println("Error: Expecting \"");
                             vCheck = 1;
                         }
                         if(sCheck == 0){
                             if((!(string10.equals("yes"))) || (!(string10.equals("no")))){
                                 errorStatus = 0;
                                 System.err.println("should be \"yes\" or \"no\"");
                             }
                         }
                     }
                     else{
                         while(i != s.length()){
                             i++;
                         }
                         errorStatus = 0;
                         System.err.println("Error: Expecting ?>");
                     }
                 }

                public static boolean containsSpace(String t){
                    if(t != null){
                        for(int i = 0; i < t.length(); i++){
                            if(Character.isWhitespace(t.charAt(i))){
                                return true;
                            }
                        }
                    }
                    return false;
                }
                 
                 public static void printList(ArrayList<String> E){
                     for(String s: E){
                         System.out.print(" " + s);
                     }
                        }
   } 



