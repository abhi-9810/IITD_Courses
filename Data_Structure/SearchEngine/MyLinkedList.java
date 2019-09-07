import java.util.NoSuchElementException;
import java.util.LinkedList;

public class MyLinkedList<E> extends LinkedList<E>{
 
  public MyLinkedList<E> link=null;
   
  // public int size(){
    //if(this==null)
     //return 0;
     //return this.size();  
  //}

  public boolean isEmpty(){
    if(this.size()==0)
      return true;
    else
      return false;
  }
  
  public E getFirst(){
    return this.getFirst();
  }
  
  public E removeFirst(){
     return this.removeFirst();
  }
   
 // public E get1(int i){
   // return (E)this.get(i);  
  //}

 // public void addLast(E obj){
   // this.addLast(obj);
  //}

}
