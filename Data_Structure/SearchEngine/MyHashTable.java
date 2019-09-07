public class MyHashTable{
  public String name;
  public MyLinkedList<WordEntry> hash[]=new MyLinkedList[100];
  //constructor
  public MyHashTable(){
     name=""; 
  }
  //hashfunction
  public int getHashIndex(String str){
    int sum=0;
   //System.out.println(str);
    for(int i=0;i<str.length();i++){
       int ascii=(int)str.charAt(i)-97;
        sum+=ascii;
    }
    sum=Math.abs(sum);
     sum%=100;
       return Math.abs(sum);
  }
  //adding word to the Hashtable
  public void addPositionsForWord(WordEntry w){
       int k=getHashIndex(w.word);
       //System.out.println(" word= "+w.word+"  "+k);
       //MyLinkedList<WordEntry> p=this.hash[k];
       if(this.hash[k]==null)
         this.hash[k]=new MyLinkedList<WordEntry>();
       this.hash[k].add(w);
  }
}
