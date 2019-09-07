import java.util.*;
import java.util.LinkedList;
import java . io .*;
import java.lang.Object;
import java.util.Timer; 
import java.util.TimerTask;
public class Exchange{
	//match orders
	public int status; 
	public Exchange(queue buy,queue sell,String s1[]){
            Node t=new Node();  
            status=Integer.parseInt(s1[4]);
            String s[]=new String[9];	
            if(s1[3].equals("sell")){
                t=buy.F;
                Node P=new Node();
                int max=0;
                int flag=1;
              while(status>0 && flag!=0){
              flag=0;
              t=buy.F;
               while(t!=null){
                  s=t.s;
                  System.out.println("here"+s[1]);
                  max=0; 
                    if(s[5].equals(s1[5]) && (Integer.parseInt(s[4])<=status||s[7].equals("T"))){
                            if(max<Integer.parseInt(s1[6])-Integer.parseInt(s[6])){
                              max=Integer.parseInt(s1[6])-Integer.parseInt(s[6]); 
                                  P=t; 
                              flag=1;
                              }
                              t=t.link;
                         }
                    else
                      t=t.link;
                      if(flag==1){
                       if(status-Integer.parseInt(s[4])>=0){
                            status=status-Integer.parseInt(s[4]);
                            cleanup clean=new cleanup(P,buy);
                        }
                         else{
                              int v=Integer.parseInt(P.s[4])-status;
                                 status=0;
                                 P.s[4]=Integer.toString(v);
                           }
                        
                        try{
                            FileOutputStream fs = new FileOutputStream ( " exchange.out " , true );
                             PrintStream p = new PrintStream ( fs );
                           p.print("T"+" "+s[8]);
                           for(int i=0;i<8;i++){
                                if(i!=4)
                           p.print(s1[i]+"  ");
                           else
                           p.print(status);
                           }
                            p.println("");
                           }
                       catch ( FileNotFoundException e1 ) {
                       System.out.println ( "File not found"); 
                      }
                        
                        }
                     }
                     if(flag==0)
                       break;
                   }
           
        }
            
            else{
               t=sell.F;
                int max=0;
                Node P=new Node();
                int flag=1;
              while(status>0&& flag!=0){
              flag=0;
              t=sell.F;
              max=0;
               while(t!=null){
                      s=t.s; 
                  if(s[5].equals(s1[5]) && (Integer.parseInt(s[4])<=status||s[7].equals("T"))){
                            if(max<Integer.parseInt(s1[6])-Integer.parseInt(s[6])){
                              max=Integer.parseInt(s1[6])-Integer.parseInt(s[6]); 
                                  P=t; 
                              flag=1;
                              }t=t.link;
                         }
                    else{
                      t=t.link;
                      if(flag==1){
                       if(status-Integer.parseInt(s[4])>=0){
                            status=status-Integer.parseInt(s[4]);
                            cleanup clean=new cleanup(P,sell);
                        }
                         else{
                              int v=Integer.parseInt(P.s[4])-status;
                                 status=0;
                                 P.s[4]=Integer.toString(v);
                           }
                        
                        try{
                            FileOutputStream fs = new FileOutputStream ( " exchange.out " , true );
                             PrintStream p = new PrintStream ( fs );
                           p.print("T"+" "+s[8]);
                           for(int i=0;i<8;i++){
                                if(i!=4)
                           p.print(s1[i]+"  ");
                           else
                           p.print(status);
                           }
                            p.println("");
                           }
                       catch ( FileNotFoundException e1 ) {
                       System.out.println ( "File not found"); 
                      }
                        
                        }
                     }
                    
                   }
                  if(flag==0)
                       break;
                  }
        
                 }
    
  s1[4]=Integer.toString(status);
  }
      public boolean f(){
        if(status==0){
          return false;}
          else{
          return true;
      }
      
   }
      
}
