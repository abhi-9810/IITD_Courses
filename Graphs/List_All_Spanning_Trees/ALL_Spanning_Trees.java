import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.util.LinkedList;
public class main {
    public static LinkedList<edge>edges=new LinkedList<edge>();
    public static LinkedList<LinkedList<edge>> mst = new LinkedList<>();
    public static LinkedList<partition> partitions = new LinkedList<partition>();
    public static int v;
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Please Enter the no. of vetices of the graph");
        v=s.nextInt();
        System.out.println("Please Enter the no. of edges of the graph");
        int e=s.nextInt();
        System.out.println("Please Enter the value of k");
        int k1=s.nextInt();
        partition p=new partition();
        for(int i=0;i<e;i++) {
        	int f=s.nextInt();
        	int l=s.nextInt();
        	double w=s.nextDouble();
        	edge e1=new edge(f,l,w);
        	edges.add(e1);
        	p.open.add(e1);
        }
        s.close();
        if(connected(p)) { 
           allst(p);
            mst = sortmst();
          System.out.println("The value of "+k1+"th Minimum Spanning Tree is:");
            LinkedList<edge>temp1=mst.get(k1-1);
        	  for(int i1=0;i1<temp1.size();i1++) {
        		  System.out.println(temp1.get(i1).first+"  "+temp1.get(i1).last+"  "+temp1.get(i1).weight);
        	  }
            System.out.println("The Weight of The Tree is:: "+valuemst(temp1));
            System.out.println("The Other Mst's are-----------\n\n");
          for(int i=0;i<mst.size();i++) {
        	  LinkedList<edge>temp=mst.get(i);
        	  System.out.println("The "+(i+1)+"th Spanning Tree iS:");
        	  for(int i1=0;i1<temp.size();i1++) {
        		  System.out.println(temp.get(i1).first+"  "+temp.get(i1).last+"  "+temp.get(i1).weight);
        	  }
        	 System.out.println("The Weight of The Tree is:: "+valuemst(temp));
          }   
        }
        else
        	System.out.println("The Graph is not Connected");
    }
      //returning all the spanning trees of the graph
      public static void allst(partition p){
    	  LinkedList<edge> temp1=new LinkedList<edge>();
    	  temp1=createmst(p);
    	  if(temp1.size()==0)
    		  return ;
    	  p.mst=temp1;
    	  p.weightmst=valuemst(p.mst);
    	  partitions.add(p);
    	  int i=0;
    	  while(partitions.size()!=0) {
    		  partition temp=partitions.get(0);
    		  //System.out.println(i);
    		  i++;
    		  //System.out.println(partitions.size()+"size");
    		  mst.add(temp.mst);
    		  partitions.remove(0);
    		  partition1(temp,temp.mst);
    	  }
    	  
      }
      //returning the mst of a partition
      public static LinkedList<edge> createmst(partition p){
    	  LinkedList<edge> temp=new LinkedList<edge>();
    	  int edges=0;
    	  for(int i=0;i<p.included.size();i++) {
    		  temp.add(p.included.get(i));
    		  edges++;  
    	  }
    	  if(iscycle(temp))
    		 return new LinkedList<edge>();
         // for(int i=0;i<temp.size();i++){
           //   System.out.println(temp.get(i).weight+"  before");
          //}
    	  p.open=sort(p.open);
          
    	  for(int i=0;i<p.open.size();i++) {
    		  temp.add(0,p.open.get(i));
    		  if(iscycle(temp)) {
    			  temp.remove(0);
    		  }
    		  else
    			  edges++;
    		 if(edges==v-1)
    			 break;
    	  }
         // for(int i=0;i<temp.size();i++){
           //   System.out.println(temp.get(i).weight+"  after");
          //}
    	  if(edges==v-1)
    	    return temp;
    	  else
    		return new LinkedList<edge>();
      }
      //sorting the linkedlist of edges
      public static LinkedList<edge> sort(LinkedList<edge> e){
    	  LinkedList<edge> temp=e;
    	  List<edge> list =new ArrayList<>();
    	  for(int i=0;i<temp.size();i++)
    		  list.add(temp.get(i));
    	  Collections.sort(list, new Comparator<edge>() {
    		    @Override
    		    public int compare(edge o1, edge o2) {
    		    	int k=0;
    		    	if(o2.weight - o1.weight>0)
    		    		k=1;
    		        return k;
    		    }
    		});
    	  LinkedList<edge>temp1=new LinkedList<edge>();
    	  for(int i=0;i<temp.size();i++)
    		  temp1.add(list.get(i));
    	  return temp1;
      }
      //public static sort msts
      public static LinkedList<LinkedList<edge>> sortmst(){
          LinkedList<LinkedList<edge>> mst1 = new LinkedList<>();
          int l=mst.size();
          while(mst.size()!=0){
    	   int v=0;
           double value=111111111.0;   
           for(int i=0;i<mst.size();i++){ 
    		  double temp=valuemst(mst.get(i));
              if(temp<value){
                  value=temp;
                  v=i;
              }
              
           }
                 
              mst1.addLast(mst.get(v));
              mst.remove(v);
           
          }
        
    	  return mst1;
      }
      //checking for the cycle in the graph
      public static boolean iscycle(LinkedList<edge> e) {
    	  LinkedList<Integer> adj[]=new LinkedList[v];
    	  for(int i=0;i<v;i++)
    		  adj[i]=new LinkedList<Integer>();
    	  for(int i=0;i<e.size();i++) {
    		  int first=e.get(i).first;
    		  int last=e.get(i).last;
    		  adj[first].add(last);
    		  adj[last].add(first);
    	  }
    	  if(isCyclic(adj))
    		  return true;
    	  return false;
      }
      public static boolean  isCyclicUtil(int v, boolean visited[], int parent,LinkedList<Integer> adj[])
      {
          // Mark the current node as visited
          visited[v] = true;
          Integer i;
   
          // Recur for all the vertices adjacent to this vertex
          Iterator<Integer> it = adj[v].iterator();
          while (it.hasNext())
          {
              i = it.next();
   
              // If an adjacent is not visited, then recur for that
              // adjacent
              if (!visited[i])
              {
                  if (isCyclicUtil(i, visited, v,adj))
                      return true;
              }
   
              // If an adjacent is visited and not parent of current
              // vertex, then there is a cycle.
              else if (i != parent)
                  return true;
          }
          return false;
      }
   
      // Returns true if the graph contains a cycle, else false.
      public static boolean isCyclic(LinkedList<Integer> adj[])
      {
          // Mark all the vertices as not visited and not part of
          // recursion stack
          boolean visited[] = new boolean[v];
          for (int i = 0; i < v; i++)
              visited[i] = false;
   
          // Call the recursive helper function to detect cycle in
          // different DFS trees
          for (int u = 0; u < v; u++)
              if (!visited[u]) // Don't recur for u if already visited
                  if (isCyclicUtil(u, visited, -1,adj))
                      return true;
   
          return false;
      }
      //checking of the partition is connected or not
      public static boolean connected(partition p) {
    	  int a[]=new int[v];
    	  for(int i=0;i<p.included.size();i++) {
    		  a[p.included.get(i).first]++;
    		  a[p.included.get(i).last]++;
    	  }
    	  for(int i=0;i<p.open.size();i++) {
    		  a[p.open.get(i).first]++;
    		  a[p.open.get(i).last]++;
    	  }
    	  for(int i=0;i<v;i++) {
    		  if(a[i]==0)
    			  return false;
    	  }
    	  return true;
      }
      //creating the partition
      public static void partition1(partition p,LinkedList<edge> e) {
    	    partition p1=new partition();
          partition p2=new partition();
          for(int i=0;i<p.included.size();i++){
            p1.included.add(p.included.get(i));
            p2.included.add(p.included.get(i));
          }
          for(int i=0;i<p.excluded.size();i++){
            p1.excluded.add(p.excluded.get(i));
            p2.excluded.add(p.excluded.get(i));
          }
          for(int i=0;i<p.open.size();i++){
            p1.open.add(p.open.get(i));
            p2.open.add(p.open.get(i));
          }
    	  for(int i=0;i<e.size();i++) {
    		  edge temp=e.get(i);
    		  if(temp.ispart(p.open, temp)) {
    			  p1.open.remove(temp);
    			  if(!p1.excluded.contains(temp))
                      p1.excluded.add(temp);
    			  p2.open.remove(temp);
            if(!p2.included.contains(temp))
    			      p2.included.add(temp);
    			  //calculate mst of p1 and put that in sorted order in the list
    			  p1.mst=createmst(p1);
    			  p1.weightmst=valuemst(p1.mst);
    			  if(connected(p1) && p1.mst.size()!=0) {
    				  addList(p1);
    			  }
                  partition temp1=new partition();
                  for(int i1=0;i1<p2.included.size();i1++){
                    temp1.included.add(p2.included.get(i1));
                  }
                  for(int i1=0;i1<p2.excluded.size();i1++){
                    temp1.excluded.add(p2.excluded.get(i1));
                  }
                  for(int i1=0;i1<p2.open.size();i1++){
                    temp1.open.add(p2.open.get(i1));
                  }
    			  p1=temp1;
    		  }
    	  }
      }
      public static double valuemst(LinkedList<edge> e) {
    	  double sum=0;
    	     for(int i=0;i<e.size();i++) {
    	    	 sum+=e.get(i).weight;
    	     }
    	  return sum;
      }
      public static void addList(partition p) {
    	 int i=0;
    	 if(partitions.size()==0) {
    		 partitions.add(0,p);
    		 return ;
    	 }
    		 
    	  while(p.weightmst>partitions.get(i).weightmst) {
    		  i++;
    		  if(i==partitions.size())
    			  break;
    	  }
    	  partitions.add(i,p);
      }
} 




class edge {
 public int first;
 public int last;
 public double weight;
  public edge(int a,int b,double c) {
	  this.first=a;
	  this.last=b;
	  this.weight=c;
  }
  public boolean ispart(LinkedList<edge> e1,edge e) {
	  for(int i=0;i<e1.size();i++) {
		  edge temp=e1.get(i);
		  if(temp.first==e.first && temp.last==e.last && temp.weight==e.weight)
			  return true;
	  }
	  return false;
  }
}


class partition {
	public LinkedList<edge>included;
	public LinkedList<edge>excluded;
	public LinkedList<edge>open;
	public LinkedList<edge>mst;
	public double weightmst;
	
	public partition() {
		included=new LinkedList<edge>();
		excluded=new LinkedList<edge>();
		open=new LinkedList<edge>();
		mst =new LinkedList<edge>();
		weightmst=0.0;
	}
}
