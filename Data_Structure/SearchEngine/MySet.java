import java.util.LinkedList;

public class MySet<E> extends LinkedList<E>{

    public MySet<E> Set=new MySet<E>();
    
    //public MySet<E>(){
      //Set =new Myset<E>();
    //}
    //adding to the set
   // public void addLast(E element){
     //this.addLast(element);   
    //}
     
    public int size(){
      return this.size();
    }
    //union
    public MySet<E> union(MySet<E> otherSet) {
      MySet<E> prev=this;
      while(otherSet!=null){
        MySet<E> temp=new MySet();
         temp.addLast(otherSet.removeFirst()); 
        if(!prev.contains(temp))
          prev.addLast(temp);
      }
      return prev;  
    }
    //intersection
   public MySet<E> intersection(MySet<E> otherSet) {
      MySet<E> prev=this;
      MySet<E> newone=new MySet<E>();
      while(otherSet!=null){
        MySet<E> temp=new MySet();
          temp.addLast(otherSet.removeFirst()); 
        if(prev.contains(temp))
          newone.addLast(temp);
      }
      return newone;  
    }
   
    public E get(int i){
     return this.get(i);  
   }

}
