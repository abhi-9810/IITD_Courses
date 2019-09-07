import java.util.*;
import java.util.LinkedList;
import java . io .*;
import java.lang.Object;
import java.util.Timer; 
import java.util.TimerTask;
class Reminder {
    Timer timer;
    
     long startTime1;
    int t;
    public queue q;
   
    public queue buy1=new queue();
    public queue sell1=new queue();     
    String a[]=new String[9];
    public Reminder(int seconds,String s[],long time,queue q1) {
        timer = new Timer();
        timer.schedule(new RemindTask(), seconds*1000);
        t=seconds;
        q=q1;
        startTime1=time;
        for(int i=0;i<8;i++)
          a[i]=s[i];
	}
	 
	  
    class RemindTask extends TimerTask {
        public void run() { 
              
              long currenttime= System.currentTimeMillis();
             q.enqueue(a,currenttime,q);
                
              String s[]=new String[8];a[8]=Long.toString(currenttime);
          for(int i=0;i<8;i++)
             s[i]=a[i];
          if(a[3].equals("buy")){
          buy1.enqueue(s,Long.parseLong(a[8]),buy1);
          
         }
        else
        sell1.enqueue(s,Long.parseLong(a[8]),sell1);
        
          exchange Obj =new exchange(buy1,sell1,a);
                Obj.start();
         
             if(currenttime-startTime1<(Integer.parseInt(a[2])+Integer.parseInt(a[0]))*1000){
              try{
              FileOutputStream fs = new FileOutputStream ( " order.out " , true );
                PrintStream p = new PrintStream ( fs );
                
                p.print(currenttime+" "+startTime1+" ");
                for(int i=1;i<8;i++)
                p.print(a[i]+"  ");
                p.println("");            
         }catch ( FileNotFoundException e1 ) {
            System.out.println ( "File not found");
        }
     }
           
              timer.cancel(); //Terminate the timer thread
        
    }
}        
            
}
    class thread extends Thread{
     public String s[]=new String[8]; 
     public int a1=0;
     long startTime= System.currentTimeMillis();
     queue q=new queue();
     
     stock c=new stock();
     public thread(String s1[],int b){     
     for(int i=0;i<8;i++)
     s[i]=s1[i];
     a1=b;     
     }
     
     public void run(){
          
          if(check(s)){
            Reminder t1=new Reminder(a1,s,startTime,q);
            }
          else{
           try{
           FileOutputStream fs = new FileOutputStream ( " order.out " , true );
            PrintStream p = new PrintStream ( fs );
            p.println("EXCEPTION");
            for(int i=0;i<8;i++)
           p.print(s[i]+"  ");
           p.println("");
         }
         catch ( FileNotFoundException e1 ) {
            System.out.println ( "File not found");
        }
     }
  }
      public boolean check(String s[]){
             int d,d1,d2,d3;
         try { 
             d = Integer.parseInt(s[0]);  
           }
          catch(NumberFormatException nfe){  
             return false;  
           }  
            try { 
             d1 = Integer.parseInt(s[2]);  
           }  
          catch(NumberFormatException nfe){  
             return false;  
           }  
          try { 
             d2 = Integer.parseInt(s[4]);  
           }  
          catch(NumberFormatException nfe){  
             return false;  
           }        
          try { 
             d3 = Integer.parseInt(s[6]);  
           }  
          catch(NumberFormatException nfe){  
             return false;  
           }
         if(s[7].compareTo("T")!=0&&s[7].compareTo("F")!=0)
                 return false;
                  if(d<0||d1<0||d2<0||d3<0)
                    return false;
                    return true;
         }
        
 }
 
 
 
 public class test {
      public String a1;
      long startTime= System.currentTimeMillis();
      public test(String s[],int b){
          thread Ram=new thread(s,b);
          Ram.start();
        }
  }
  
  
  
  class Node{
      public String s[]=new String[9];
       Node link;
   }
  class queue{
      Node F=null;
      Node R=null;
      
       public void enqueue(String s1[],long time,queue q){
         Node push=new Node();
         for(int i=0;i<8;i++)
           push.s[i]=s1[i];
           push.s[8]=Long.toString(time);
           push.link=null;
         if(q.F==null && q.R==null){
           q.F=push;
           q.R=push;
          }
          
           push.link=null;
           q.R=push;
  
      }
       public String[] dequeue(queue q){
           String a[]=new String[9];
           if(q.F!=null){
           for(int i=0;i<9;i++)
              a[i]=q.F.s[i];
              q.F=q.F.link;
              return a;
            }
            else
            return null;
       }
  }
  
  class cleanup extends Thread{
    public queue buy1=new queue();
     public queue sell1=new queue();
     public cleanup(queue buy,queue sell){
       buy1.F=buy.F;
       buy1.R=buy.R;
       sell1.F=sell.F;
       sell1.R=sell.R;    
     }
     public cleanup(Node t,queue b){
     Node p=new Node();
     p=b.F;
     String s[]=new String[9];
     s=t.s;
     while(p.link!=t)
     p=p.link;
     p.link=p.link.link;
     System.out.println("Transaction");
     print(s);
     }
     
      public void run(){
        String s[]=new String[9];
         //while(buy1.F!=null){
        Node t=new Node();
        t=buy1.F;
        Node p=new Node();
        p=t;
         while(t!=null){
           long c=System.currentTimeMillis();
           if(c-Long.parseLong(t.s[8])>Integer.parseInt(t.s[2])*1000){
           s=t.s;
           print(s);
           if(p.link!=null){
             p.link=p.link.link;
             t=p.link;
             }
             else
             t=null;  
          }
           else{
              p=t;
            if(t.link!=null)
               t=t.link;
           }     
         
         }
        t=sell1.F;
        p=t;
         while(t!=null){
           long c=System.currentTimeMillis();
           if(c-Long.parseLong(t.s[8])>Integer.parseInt(t.s[2])*1000){
           s=t.s;
           print(s);
           if(p.link!=null){
             p.link=p.link.link;
             t=p.link;
             }
             else
             t=null;  
          }
           else{
              p=t;
            if(t.link!=null)
               t=t.link;
           }     
         
         }
         
 } 
      public void print(String s1[]){
      try{
           FileOutputStream fs = new FileOutputStream ( " cleanup.out " , true );
            PrintStream p = new PrintStream ( fs );
            for(int i=0;i<8;i++)
           p.print(s1[i]+"  ");
           p.println("");
         }
         catch ( FileNotFoundException e1 ) {
            System.out.println ( "File not found"); 
          }
     }   
  }
  class exchange extends Thread{
      public String s1[]=new String[9];
      public long exchangetime=System.currentTimeMillis();
      public queue buy=new queue();
      public queue sell=new queue();
       public boolean b;
       public int flag=0;
      public exchange(queue buy1,queue sell1,String a[]){
         s1=a;
        Exchange e=new Exchange(buy1,sell1,s1);
        b=e.f();
        buy.F=buy1.F;
        buy.R=buy1.R;
         sell.F=sell1.F;
        sell.R=sell1.R;
   
     
       }
        public void run(){
            if(b){
            
          /*String s[]=new String[8];
          for(int i=0;i<8;i++)
             s[i]=s1[i];
          if(s1[3].equals("buy"))
          buy1.enqueue(s,Long.parseLong(s1[8]),buy1);
        else
        sell1.enqueue(s,Long.parseLong(s1[8]),sell1);*/

         try{
           FileOutputStream fs = new FileOutputStream ( " exchange.out " , true );
            PrintStream p = new PrintStream ( fs );
            if(s1[3].equals("buy"))
            p.print("P ");
            else
            p.print("S ");
            for(int i=0;i<8;i++)
           p.print(s1[i]+"  ");
           p.println("");
         }
         catch ( FileNotFoundException e1 ) {
            System.out.println ( "File not found"); 
          }
          //queue buy=new queue();
              
            
            
      cleanup clean=new cleanup(buy,sell);
      clean.start();
      
  }
 }  
} 
  
  
//}
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  

