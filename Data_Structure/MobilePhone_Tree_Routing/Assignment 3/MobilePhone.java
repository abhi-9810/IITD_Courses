public class MobilePhone{
  public int number;
  public boolean status;
  public Exchange exchange;
  public MobilePhone(int number1){
    number=number1;
  }
   public int number(){
     return number; 
   }
   public void switchOn(){
      status=true;
   }
   public void switchOff(){
      status=false;
   }
  public boolean status(){
     return status;  
  }
   public Exchange location(){
      if(this.exchange!=null)
         return exchange;
      else
        System.out.println("Error - Mobile phone is currently switched off");
       return null;
   }
}
