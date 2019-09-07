public class PageIndex{
 public MyLinkedList<WordEntry> pageindex;
 
 //search in the list if not find add in the list
 public PageIndex(){
    pageindex=new MyLinkedList<WordEntry>();
 }
  public void addPositionForWord(String str, Position p, MyLinkedList<WordEntry> pageindex1){
      int flag=0;
      
      for(int i=0;i<pageindex1.size();i++){
          if(pageindex1.get(i).word.equals(str)||pageindex1.get(i).word.equals(str+"s")){
             pageindex1.get(i).pos.add(p);
             flag=1; 
             return ;
          }
      }
        if(flag==0){
          //System.out.println(str);
           WordEntry w=new WordEntry(str);
            w.pos.add(p);
          //if(pageindex1.size()==0)
           pageindex1.add(w);
          //else
           //pageindex1.addLast(w);
        }
        
  }
 //return linkedlist of words
 public MyLinkedList<WordEntry> getWordEntries(){
     return this.pageindex;
 }

}
