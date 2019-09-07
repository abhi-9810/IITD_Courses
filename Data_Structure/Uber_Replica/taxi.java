public class taxi{
  public String cab;
  public Node position;
  public boolean avl;
  public int busy;
  //constructor
  public taxi(String cab,Node Position,boolean avl){
    this.cab=cab;
    this.position=Position;
    this.avl=avl;
    this.busy=0;
  }
  //give avaliablity
  public boolean aval(){
    return this.avl;
  }
 //give position
  public Node Position(){
    return this.position;
  }
}
