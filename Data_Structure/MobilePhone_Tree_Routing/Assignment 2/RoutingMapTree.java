public class RoutingMapTree{ 
          public static Exchange root=new Exchange(0);
         //rejistring mobile
          public void switchOn(MobilePhone a, Exchange b){
             Exchange e=b;
             while(e!=null){
              e.m.Insert(a);
              e=e.parent(); 
             }
          }
          //switchoff mobile a
          public void switchOff(MobilePhone a){
              a.switchOff();
              Exchange e=a.location();
              while(e!=null){
                e.m.Delete(a);
                e=e.parent();
              }   
           }
           //taking argument
	 public void performAction(String actionMessage) {
		System.out.print(actionMessage);
               String s[]=new String[5];
               for(int i=0;i<5;i++)
                  s[i]="";
                 int j=0;
               for(int i=0;i<actionMessage.length();i++){
                  if(actionMessage.charAt(i)!=' ')
                    s[j]=s[j]+actionMessage.charAt(i);
                  else
                    j++;
                  }
           if(s[0].equals("addExchange")){
               Exchange e =give(Integer.parseInt(s[1]));
               Exchange f=new Exchange(Integer.parseInt(s[2]));
                f.parent=e;
                 System.out.println(" ");  
                 if(e==null)
                   System.out.println("No exchange found");
                 else{
                      if(e.child==null)
                         e.child=f;
                      else{
                         try{
                            Exchange e1=e.child;                  
                            while(e1.siblings!=null)
                              e1=e1.siblings;
                            e1.siblings=f;
                         }catch(Exception e2)
                            {System.out.println("No exchange found");}    
                      }                       
                  }
                
               }         
               //switch on mobile and connect to a exchange 
           if(s[0].equals("switchOnMobile")){
               MobilePhone m1=new MobilePhone( Integer.parseInt(s[1]));
                m1.switchOn();
               Exchange e=give(Integer.parseInt(s[2]));
                 System.out.println(" ");
                 try{
                  e.m.Insert(m1);
                    m1.exchange=e;              
                   Exchange e1=e.parent();
                     while(e1!=null){
                       e1.m.Insert(m1);
                       e1=e1.parent(); 
                     }
                   }
                 catch(Exception e3){
                  // e3.printStackTrace();
                    System.out.println("Error-Exchange Doesn't exists");  
                 }
                 
           }  
              //switchoff the mobile
            if(s[0].equals("switchOffMobile")){
                int number=Integer.parseInt(s[1]);
                 Myset m=root.m;
                System.out.println(" ");
                 while(m!=null){
                   if(m.mobile.number==number)
                        break;
                    m=m.link;
                 }try{
                  MobilePhone m1=m.mobile;
                  Exchange e=m.mobile.exchange;
                   while(e!=null){
                     e.m.Delete(m1);
                     e=e.parent();  
                   }
              }catch(Exception e)
               {System.out.println("No Mobile Phone Found");}
           }     
              //give nth child
            if(s[0].equals("queryNthChild")){
               Exchange m=give(Integer.parseInt(s[1]));
               int n=Integer.parseInt(s[2]);
                  Exchange e;
                 if(n==0)
                   e=m;
                 else
                  e=m.child;
               
                try{
                  while(n>1){
                   e=e.siblings;
                   n--;
                  }
                 System.out.println(": "+e.identifier);
               }
                 catch(Exception e1){
                   System.out.println("NO n child exist");
                }
             }
              //print mobiles connected to that exchange
            if(s[0].equals("queryMobilePhoneSet")){
               Exchange m1=give(Integer.parseInt(s[1]));
               Myset m5=new Myset();
               m5=m1.m;
               System.out.print(": ");
               while(m5!=null){
                  System.out.print(m5.mobile.number+" "); 
                   m5=m5.link;
               }
                  System.out.println(" ");  
          }
       } 
         //find the Exchange with a given identifier 
         public Exchange give(int identifier){
            Exchange p=root; 
            if(identifier==0)
              return root;
            Exchange p1=p.child;
            Exchange b=p1;
            while(p1!=null){ 
                if(p1.identifier==identifier)
                    return p1;     
                if(contains(p1.siblings,identifier)){
                   if(p1.siblings.identifier==identifier)
                     return p1.siblings;
                   else{
                    if(contains(p1.siblings.siblings,identifier))
                        p1=p1.siblings.siblings;
                      else
                       p1=p1.siblings.child;
                   }
                }
                if(contains(p1.child,identifier)){
                   if(p1.child.identifier==identifier)
                     return p1.child;
                   else{
                    if(contains(p1.child.siblings,identifier))
                        p1=p1.child.siblings;
                      else
                       p1=p1.child.child;
                   }
                  }
                if(b==p1)
                   return null;
               }
               return null;
         } 
        //contains
         public boolean contains(Exchange root1,int id){
           if(root1==null)
             return false;
              if(root1.identifier==id)
                  return true;
               else
                  return (contains(root1.siblings,id)||contains(root1.child,id)) ;
            }

          } 









