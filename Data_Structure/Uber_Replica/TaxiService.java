import java.util.LinkedList;

public class TaxiService{
       public LinkedList<String> nodes;
       public static Graph graph=new Graph();
       public LinkedList<taxi> taxies=new LinkedList<taxi>();
       public TaxiService() {
           nodes=new LinkedList<String>();
	}

	public void performAction(String actionMessage) {
           System.out.println("action to be performed: " + actionMessage);
           String s[]=new String[4];
           for(int i=0;i<4;i++)
             s[i]="";
            int j=0;
           for(int i=0;i<actionMessage.length();i++){
             if(actionMessage.charAt(i)!=' ')
               s[j]+=actionMessage.charAt(i);
             else
               j++;   
           }
           //adding edge
           if(s[0].equals("edge")){
            int flag1=0;
            int flag2=0;
            Node temp;
            Node temp1;
             if(!nodes.contains(s[1])){
                temp=new Node(s[1],null);
               nodes.add(s[1]);
               flag1=1;
             }
             else
               temp=graph.givenode(s[1]);
              if(!nodes.contains(s[2])){
                temp1=new Node(s[2],null);
               nodes.add(s[2]);
               flag2=1;
             }
             else
               temp1=graph.givenode(s[2]);
             Edge e=new Edge(temp,temp1,Integer.parseInt(s[3]));
             if(temp!=null)
              temp.e.add(e);
              temp1.e.add(e);
             if(flag1==1)
              graph.g.add(temp);
             if(flag2==1)
              graph.g.add(temp1); 
           }
          //adding taxi
          if(s[0].equals("taxi")){
             taxi temp=new taxi(s[1],graph.givenode(s[2]),true);
             taxies.add(temp);
          }
          //giving position of taxi
          if(s[0].equals("printTaxiPosition")){
            for(int i=0;i<taxies.size();i++)
              if(Integer.parseInt(s[1])>=taxies.get(i).busy)
                System.out.println("taxi "+taxies.get(i).cab+": "+taxies.get(i).position.name);
          }
         //giving path to the customer
          if(s[0].equals("customer")){
            try{
             Node v1=graph.givenode(s[1]);
             graph.Dijkstra(v1);
             //graph.print();
             System.out.println("Available taxis:");
             int min=Integer.MAX_VALUE;
             int index=0;
             int flag=0;
             for(int i=0;i<taxies.size();i++){
               if(Integer.parseInt(s[3])>=taxies.get(i).busy){
                  System.out.print("Path of taxi "+taxies.get(i).cab+": ");
                  flag=1;
                  LinkedList<Node> temp=graph.givepath(v1,taxies.get(i).position);
                  for(int i1=0;i1<temp.size();i1++)
                    System.out.print(temp.get(temp.size()-1-i1).name+", ");
                  System.out.println(":: time taken is "+taxies.get(i).position.dist+" units");
                  if(taxies.get(i).position.dist<min){
                    min=taxies.get(i).position.dist;
                    index=i; 
                  } 
               }
             }
           if(flag==1){
            System.out.println("** Chose taxi "+taxies.get(index).cab+" to service the customer request ***");  
            LinkedList<Node> temp1=new LinkedList<Node>();
            temp1=graph.givepath(v1,graph.givenode(s[2]));
            System.out.print("Path of customer: ");
            for(int i=0;i<temp1.size();i++)
              System.out.print(temp1.get(i).name+", ");
             System.out.println(":: time taken is "+graph.givenode(s[2]).dist+" units");
             taxies.get(index).busy=Integer.parseInt(s[3])+graph.givenode(s[2]).dist;
             taxies.get(index).position=graph.givenode(s[2]);
          }
          else
            System.out.println("No Taxies Available");
        
       }catch(Exception e){
          System.out.println("Position not found");
        }
     }
   }
}
