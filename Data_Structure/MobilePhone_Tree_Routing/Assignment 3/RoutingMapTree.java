public class RoutingMapTree{ 
          public static Exchange root=new Exchange(0);
         //rejistring mobile
          public void switchOn(MobilePhone m1, Exchange b){
             Exchange e=b;
             if(e.m==null)
                 e.m=new Myset();
                  e.m.Insert(m1);
                    m1.exchange=e;              
                   Exchange e1=e.parent();
                     while(e1!=null){
                       if(e1.m==null)
                       e1.m=new Myset();
                       e1.m.Insert(m1);
                       e1=e1.parent(); 
                     }
          }
          //switchoff mobile a
          public void switchOff(MobilePhone a){
              a.switchOff();
              Exchange e=a.location();
              while(e!=null){
                     if(e.m.mobile.number==a.number)
                       e.m=e.m.link;
                     else
                     e.m.Delete(a);
                     e=e.parent();  
                 }
              }   
           
         //give  the exchange to which it is connected
          public Exchange findPhone(int m){
            int number=m;
                 Myset m3=root.m;
                try{ 
                while(m3!=null){
                   if(m3.mobile.number==number)
                        break;
                    m3=m3.link;
                 }
                  MobilePhone m1=m3.mobile;
                  Exchange e=m3.mobile.exchange;
                  return e;
                  }catch(Exception e)
                    {System.out.println(": No mobile phone found");
                      return null;
                  }
           }
           //return the lowestRouter 
           public Exchange lowestRouter(Exchange a, Exchange b){
              if(a==b)
                return a;
              else{
                  if(a.level>b.level)
                     while(b.level>a.level)
                        b=b.parent;
                   else
                      while(b.level<a.level)
                        a=a.parent;
                   while(a.identifier!=b.identifier){
                      a=a.parent;
                      b=b.parent;
                   } 
                   return a;
              }        
            }
            //return the callpath
          public ExchangeList routeCall(MobilePhone a, MobilePhone b){
              try{
               ExchangeList e=new ExchangeList();
               ExchangeList f=new ExchangeList();
               Exchange m=a.exchange;
               Exchange m95=lowestRouter(m,b.exchange);
               //System.out.println(m.identifier);
               e.m=m;
               //f=e.link;
               ExchangeList me=e;
               
               while(m!=m95){
                 m=m.parent;
                 ExchangeList s=new ExchangeList();
                 s.m=m;
                 e.link=s;
                 e=e.link;
               }
               //System.out.println(me.link.m.identifier);
               //e.link=me;
               ExchangeList r5=new ExchangeList();
               m=b.exchange;
               r5.m=m;
               ExchangeList mine=r5;
               while(m.parent!=m95){
                 m=m.parent();
                 ExchangeList s=new ExchangeList();
                 s.m=m;
                 r5.link=s;
                 r5=r5.link;
               }
               
              //reversing linked list
              e.link=reverse(mine);
              return me;
             }catch(Exception e){System.out.println(": The number you are trying to reach is out of coverage area");return null;}   
          }
             //function to reverse Linkedlist:
            public ExchangeList reverse(ExchangeList r){
              ExchangeList m=r;
              ExchangeList f=null;
              ExchangeList next=null;
              ExchangeList prev=null;
              while(m!=null){
                 next=m.link;
                 m.link=f;
                 f=m;
                  m=next;
              }
               return f;
           }
            // changing the location of the phone
            public void movephone(MobilePhone a ,Exchange b){
              int number=a.number;
              try{
              Myset m=root.m;
                 while(m!=null){
                   if(m.mobile.number==number)
                        break;
                    m=m.link;
                 }a=m.mobile;
                  Exchange e=m.mobile.exchange;
                   switchOff(a);
                     switchOn(a,b);
 
              }catch(Exception e){System.out.println("No mobile Phone Found");}
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
                 
                 if(e==null)
                   System.out.println("No exchange found");
                 else{
                      if(e.child==null){
                         e.child=f;
                         f.level=e.level+1; 
                        }
                      else{
                         try{
                            Exchange e1=e.child;                  
                            while(e1.siblings!=null)
                              e1=e1.siblings;
                            e1.siblings=f;
                            f.level=e.level+1;
                         }catch(Exception e2)
                            {System.out.println("No exchange found");}    
                      }                       
                  }
                 System.out.println(" "); 
               }         
               //switch on mobile and connect to a exchange 
           if(s[0].equals("switchOnMobile")){
               MobilePhone m1=new MobilePhone( Integer.parseInt(s[1]));
                m1.switchOn();
               Exchange e=give(Integer.parseInt(s[2]));
                 try{
                  if(e.m==null)
                       e.m=new Myset();
                  e.m.Insert(m1);
                    m1.exchange=e;              
                   Exchange e1=e.parent();
                     while(e1!=null){
                       if(e1.m==null)
                       e1.m=new Myset();
                       e1.m.Insert(m1);
                       e1=e1.parent(); 
                     }
                   }
                 catch(Exception e3){
                   //e3.printStackTrace();
                    System.out.println("Error-Exchange Doesn't exists");  
                 }
                 System.out.println(" ");
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
                     if(e.m.mobile==m1)
                       e.m=e.m.link;
                     else{//System.out.println(e.identifier);
                     //Myset m99=e.m;
                     //while(m99!=null){System.out.println(m99.mobile.number+"ftjfgh");m99=m99.link;}
                     e.m.Delete(m1);}
                     e=e.parent();  
                   }
              }catch(Exception e)
               {//e.printStackTrace();
                  System.out.println("No Mobile Phone Found");}
           }  
              //give nth child
            if(s[0].equals("queryNthChild")){
               Exchange m=give(Integer.parseInt(s[1]));
               int n=Integer.parseInt(s[2]);
                int m11=n;
                  Exchange e;
                  e=m.child;
               
                try{
                  while(n>=1){
                   e=e.siblings;
                   n--;
                  }
                 System.out.println(": "+e.identifier);
               }
                 catch(Exception e1){
                   System.out.println(": NO "+m11+"th child exist");
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
          //give the base station to which the phone is connected
          if(s[0].equals("findPhone")){
             int number=Integer.parseInt(s[1]);
              Exchange e=findPhone(number);
              if(e!=null)
                System.out.println(": "+e.identifier);
              else
               System.out.println(" MobilePhone not found"); 
          }
           //gives the lowest common ancestor
          if(s[0].equals("lowestRouter")){
            Exchange a=give(Integer.parseInt(s[1]));
            Exchange b=give(Integer.parseInt(s[2]));
            Exchange c=lowestRouter(a,b);
            if(c!=null)
             System.out.println(": "+c.identifier);
            else
             System.out.println("They don't have any common parent");           
          }
          //gives the path of call
          if(s[0].equals("findCallPath")){
            int n1=Integer.parseInt(s[1]);
            int n2=Integer.parseInt(s[2]);
             Myset m7=root.m;
             try{
                 while(m7!=null){
                   if(m7.mobile.number==n1)
                        break;
                    m7=m7.link;
                 }
                  MobilePhone a=m7.mobile;
               m7=root.m;  
                while(m7!=null){
                   if(m7.mobile.number==n2)
                        break;
                    m7=m7.link;
                 }
                  MobilePhone b=m7.mobile;
           ExchangeList m9=routeCall(a,b);
           System.out.print(": ");
           while(m9!=null){
             System.out.print(m9.m.identifier+", ");
             m9=m9.link;
           }System.out.println(" ");
          }catch(Exception e){System.out.println(": Mobile is already switchOff");}
         }
          //connect the phone to another Exchange
          if(s[0].equals("movePhone")){
             MobilePhone c10=new MobilePhone(Integer.parseInt(s[1]));
             Exchange c11=give(Integer.parseInt(s[2]));
             System.out.println(" "); 
             if(c11==null)
              System.out.println("No such exchange exists");
             else
               movephone(c10,c11);    
          }
       } 
         //find the Exchange with a given identifier 
         public Exchange give(int identifier){
            Exchange p=root;
            int flag=0; 
            if(identifier==0)
              return root;
            Exchange p1=p.child;
            while(p1!=null){
                 //System.out.println("hghukgbk");
                 flag=0; 
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
                   flag=1;
                }
                if(contains(p1.child,identifier)){
                   if(p1.child.identifier==identifier)
                     return p1.child;
                   else{
                    if(contains(p1.child.siblings,identifier))
                        p1=p1.child.siblings;
                      else
                       p1=p1.child.child;
                   }flag=1;
                  }
                
                 if(flag==0)
                   break;
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









