public class Myset{
   public MobilePhone mobile;
   public Myset link;
  // to check if set is empty 
   public boolean IsEmpty(){
     if(this==null)
        return true;
     else
        return false;
    }
  // to check if mobileect is a member of it or not
   public boolean IsMember(MobilePhone o){
      Myset q=this;
      while(q!=null){
         if(q.mobile==o)
           return true;
         q=q.link;
      }
     return false;
    }
  //insert mobileect in set
   public void Insert(MobilePhone o){
      Myset q=this;
      
      if(q.mobile!=null){
      while(q.link!=null){
        q=q.link;}
      Myset p=new Myset();
      p.mobile=o;
      p.link=null;
      q.link=p;
     }
    
     else{
      this.mobile=o;
      this.link=null;
     }
  }
  //delete mobile in set
  public void Delete(MobilePhone o){
     Myset p=this;
     //Myset m99=p;
                     //while(m99!=null){System.out.println(m99.mobile.number+"ftjfgh");m99=m99.link;}
                     
     //System.out.println(this.mobile.number);
     Myset q=p;
     int flag=0;
      if(p.mobile==o){
       p=p.link;
      flag=1;
    }else{
      if(p!=null){     
      while(p.link!=null){
       if(p.link.mobile==o){
         if(p.link!=null)
           p.link=p.link.link;
         else
          p.link=null;
         break;
      }   q=p;
          p=p.link;
     } 
       if(p.mobile==o)
         q.link=null;
    }
   }
  }
 //union of sets
  public Myset Union(Myset a){
     Myset b=this;
     Myset c=a;
     Myset d=b;
     while(d.link!=null)
       d=d.link;
     while(c!=null){
       if(!contains(b,c.mobile)){
         Myset p=new Myset();
         p.mobile=c.mobile;
         p.link=null;
         d.link=p; 
       }
       c=c.link;
     }
     return b;
   }
   
   //intersection of sets
  public Myset Intersection(Myset a){
    Myset b=this;
     Myset c=a;
     Myset d=b;
     while(d.link!=null)
       d=d.link;
     while(c!=null){
       if(!contains(b,c.mobile)){
           c.Delete(c.mobile); 
       }
       c=c.link;
     }
     return c;
   }
  // function to check if it contains mobile
   public boolean contains(Myset a,MobilePhone o){
       Myset p=a;
       while(p!=null){
         if(p.mobile==o)
          return true;
         p=p.link;
       }
       return false;
   }
}

 class MobilePhoneSet extends Myset{      
}











