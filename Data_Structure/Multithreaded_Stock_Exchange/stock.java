import java.util.*;
import java.util.LinkedList;
import java . io .*;
import java.lang.Object;
public class stock{
	//Perform I/O operation
       public long startTime; 
	//t=new Timer();
	//public queue q=new queue();
    public String a[]=new String[8];
    String a1="0";
	//public String a1="0";
	int a3=0;
	void performAction(String actionString){
	
	String s[]=new String[8];
	//int k1=Integer.parseInt(s[0]);
                for(int i=0;i<8;i++)
                     s[i]="";
                  int j=0,flag=0;
                for(int i=0;i<actionString.length();i++){
                  int ascii=(int)actionString.charAt(i);
                    if(ascii!=9)
                        s[j]=s[j]+actionString.charAt(i);
                    else
                        j++;
                }
                  //System.out.println("Jai Sri Ram");
                  try{
               	a3=a3+Integer.parseInt(s[0])-Integer.parseInt(a1);}
               	catch(Exception e){};
               	test t=new test(s,a3);
                a1=s[0];
               startTime= System.currentTimeMillis();
               
        }
}
