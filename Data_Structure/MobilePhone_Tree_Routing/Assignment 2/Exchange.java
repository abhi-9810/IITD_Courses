public class Exchange {
     public int identifier;
     //public Myset m=new Myset();
     public MobilePhoneSet m=new MobilePhoneSet();
     public Exchange siblings;
     public Exchange child;
     public Exchange parent;  
    public Exchange(int number){
       identifier=number;  
     }//return parent of exchange
    public Exchange parent(){
      return  this.parent;
    }
    //returning number of children
   public int numChildren(){
      int i=0;
      Exchange p=this.child;
     while(p!=null){
       i++;
      p=p.siblings;
      }
     return i;
    }//returning ith child
    public Exchange child(int i){
      int j=1;
      Exchange p=this.child;
     while(j!=i){
       j++;
      p=p.siblings;
      }
     return p; 
    }
   //checking root
  public boolean isRoot(){
    Exchange p=this;
    if(p.identifier==0)
      return true;
    else
     return false;
  }
   public Exchange subtree(int i){
     Exchange e=this.child;
    try{ 
       while(i>1){
       e=e.siblings;
         i--;   
       }
      return e;
   }catch(Exception e1){
       System.out.println("Not contains ith child");
       return null;
    }
}
  public Myset residentSet(){
     return this.m;
  }
}













