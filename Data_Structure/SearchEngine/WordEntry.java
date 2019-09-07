public class WordEntry{

 public String word;
 public MyLinkedList<Position> pos=new MyLinkedList<Position>(); 

 public WordEntry(String str){
   this.word=str;
 }

  public void addPosition(Position position){
     this.pos.addLast(position);
 }

 public void addPositions(MyLinkedList<Position> positions){
   MyLinkedList<Position> prev=this.pos;
   while(positions.size()!=0){
     prev.addLast(positions.removeFirst());
   }
 }

  //the linkedlist of pageentries will be contained by invertedpageindex so check in there. 
 public MyLinkedList<Position> getAllPositionsForThisWord(){
    return this.pos;
 }
}

