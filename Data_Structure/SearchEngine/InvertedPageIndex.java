public class InvertedPageIndex{
   
   public  MyHashTable websites=new MyHashTable(); 
   public  MyLinkedList<PageEntry> pageentry=new  MyLinkedList<PageEntry>();
    //adding page to the webserver:
   public  void addPage(PageEntry p){
     if(pageentry.size()==0){
        pageentry=new MyLinkedList<PageEntry>();   
     }
     pageentry.add(p);
     MyLinkedList<WordEntry> temp=p.webpage.pageindex;
      //for(int i=0;i<temp.size();i++)
        //System.out.println(temp.get(i).word);
     for(int i=0;i<temp.size();i++)
        websites.addPositionsForWord(temp.get(i));
     }
  //return set of pages which contains the word
   public MyLinkedList<PageEntry> getPagesWhichContainWord(String str){
     MyLinkedList<PageEntry> web=new MyLinkedList();
     int k=websites.getHashIndex(str);
      //System.out.println("k:="+k);
     MyLinkedList<WordEntry> temp=websites.hash[k];
      //for(int i=0;i<temp.size();i++)
        //  System.out.println(temp.get(i).word);
     if(temp==null)
       return null;
     for(int i=0;i<temp.size();i++){
        if(temp.get(i).word.equals(str)){
          MyLinkedList<Position> p=temp.get(i).pos;
          //double temp1=relevance(p.p.webpage,temp.get(i).word);
           //return the linked list in the order of relevance
          for(int i3=0;i3<p.size();i3++){
            if(!web.contains(p.get(i3).p))
            web.addLast(p.get(i3).p);
          //System.out.println(p.get(i3).p.name);
        }
       }
          //System.out.println("Jai Sri Ram");
     }
     return web;
   }
    //gives the relevance of the webpage
    public double relevance(PageIndex p,String str){
     MyLinkedList<WordEntry> temp=p.pageindex;
     for(int i=0;i<temp.size();i++){
        WordEntry w=temp.get(i);
        if(w.word.equals(str))
          return findrelevance(w); 
       }
      return 0.0;
     }
     public double findrelevance(WordEntry w){
        MyLinkedList<Position> temp=w.pos;
        double sum=0.0; 
        for(int i=0;i<temp.size();i++){
          Position p=temp.get(i);
          sum+=(double)Math.pow(p.wordIndex,-2);
         }
        return sum;
     }
    
}




