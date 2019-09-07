public class Position{ 
  public  PageEntry p;
  public int wordIndex;
  
  public Position (PageEntry p1,int w1){
     this.p=p1;
     this.wordIndex=w1;
  }
 
  public PageEntry getPageEntry(){ 
     return this.p;
  }
  
  public int getWordIndex(){
    return this.wordIndex;
  }
} 
