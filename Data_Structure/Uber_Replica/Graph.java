import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.util.LinkedList;

public class Graph{
   public LinkedList<Node> g=new LinkedList<Node>(); 
   //Shortest Path
   //Dijkstra Algorithm
  public void Dijkstra(Node v){
      LinkedList<Node> temp=this.g;
     for(int i=0;i<temp.size();i++){
        if(temp.get(i)!=v){
          temp.get(i).dist=Integer.MAX_VALUE;
          temp.get(i).marked=false;
         }
        else{
          temp.get(i).dist=0;
          temp.get(i).marked=false;
        }
     }
     int i1=0;
     while(temp.size()!=i1){
       Node u=min(temp);
        u.marked=true;     
    
       for(int i=0;i<u.e.size();i++){
         int alt=0;
         int flag=0; 
         if(u.e.get(i).first!=u)
            alt=u.dist;
         else{
           alt=u.dist;
           flag=1;
         }
          alt+=u.e.get(i).value;
         if(flag==1){
            if(u.e.get(i).second.dist>alt){
              u.e.get(i).second.dist=alt;
              u.e.get(i).second.previous=u;
            }
         }
         else{
           if(u.e.get(i).first.dist>alt){
              u.e.get(i).first.dist=alt; 
              u.e.get(i).first.previous=u;
            }        
         }
     }
       i1++;
     }
       //temp1.print();
  }
    //give path
    public LinkedList<Node> givepath(Node v1,Node v2){
      Graph temp=this;
      LinkedList<Node> temp1=new LinkedList<Node>();
      Node temp3=v2;
      while(temp3!=v1){
       temp1.addFirst(temp3);
        //System.out.println(temp3.name+"  "+temp3.dist);
       temp3=temp3.previous;
      }
      temp1.addFirst(v1);
     return temp1;
    }
   //node in q with smallest dist
   public Node min(LinkedList<Node> head1){
     LinkedList<Node>head=new LinkedList<Node>();
     for(int i=0;i<head1.size();i++)
       if(!head1.get(i).marked)
        head.add(head1.get(i));
     Node temp=head.get(0);
     int min=head.get(0).dist;
     for(int i=0;i<head.size();i++)
       if(head.get(i).dist<min){
         min=head.get(i).dist;
         temp=head.get(i); 
       }
      return temp;
   }
  //give node with name
  public Node givenode(String name){
    for(int i=0;i<this.g.size();i++){
      if(this.g.get(i).name.equals(name))
        return this.g.get(i);
    }
    return null;
  }
  public void print(){
    for(int i=0;i<this.g.size();i++){
      System.out.print(this.g.get(i).name+"    Dist. are "+this.g.get(i).dist+" Previous are "+ this.g.get(i).previous.name+"   Edges are");
       for(int i1=0;i1<this.g.get(i).e.size();i1++)
         System.out.print(this.g.get(i).e.get(i1).first.name+" - "+this.g.get(i).e.get(i1).second.name+" & ");
       System.out.println("");
    } 
      
  }   
}

//Node Class
class Node{
  public String name="";
  public LinkedList<Edge> e;
  public int dist=0;
  public Node previous;
  public boolean marked=false;  
  //constructor
  public Node(String s,Edge e1){
    this.name=s;
    e=new LinkedList<Edge>();
    if(e1!=null)
     this.e.add(e1);
     this.previous=this;
  }
}

//Edge Class
class Edge{
  public Node first;
  public Node second;
  public int value;
     
  //construcor
  public Edge(Node f,Node s,int price){
    this.first=f;
    this.second=s;
    this.value=price;
  }

}






