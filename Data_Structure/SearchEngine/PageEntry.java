import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PageEntry{
 public String name;
 public  PageIndex webpage=new PageIndex();
 public int index=1;
 //constructor
 public PageEntry(String str){
  name=str;
  BufferedReader br = null;
		//PageEntry r = new PageEntry(str);

		try {
			String actionString;
			br = new BufferedReader(new FileReader(str));
                        
			while ((actionString = br.readLine()) != null) {
				makePageIndex(actionString);
			}//print();
		} catch (IOException e) {
			//e.printStackTrace();
                    System.out.println("No File Found");
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}  

 }
 //making pageIndex
  public void makePageIndex (String s){
    String connect[]=new String[16];
    connect[0]="a";
    connect[1]="an";connect[2]="the";connect[3]="they";
    connect[4]="these";connect[5]="this";connect[6]="for";connect[7]="is";
    connect[8]="are";connect[9]="was";connect[10]="of";connect[11]="or";
    connect[12]="and";connect[13]="does";connect[14]="will";connect[15]="whose";
 
    String s2="";
    char c1[]=new char[19];
      c1[0]= '{'; c1[1]= '}'; c1[2]='['; c1[3]=']'; c1[4]='<';
      c1[5]='>'; c1[6]= '='; c1[7]='('; c1[8]=')'; c1[9]='.';
      c1[10]=','; c1[11]=';'; c1[12]=(char)34; c1[13]='?'; c1[14]='#';
      c1[15]='!'; c1[16]=':'; c1[17]='-';c1[18]=(char)39; 
    for(int i=0;i<s.length();i++){
        if((int)s.charAt(i)>=65 && (int)s.charAt(i)<=90 )
           s2+=(char)((int)s.charAt(i)+32);
        else{
         int f1=0;
         for(int i1=0;i1<19;i1++)
         if(s.charAt(i)==c1[i1]){
             s2+=" ";
             f1=1;
            break;
          }
           if(f1==0)
            s2+=s.charAt(i);
    }
}
      String s3="";
      s2=s2+" ";
       //System.out.println(s2);
      for(int i=0;i<s2.length();i++){
         if(s2.charAt(i)!=' ')
           s3+=s2.charAt(i);
         else{
              //if(s3.equals("stack")||s3.equals("stacks"))           
               // System.out.println(s3);
           int k=0;
           for(int i1=0;i1<16;i1++)
              if(connect[i1].equals(s3)||s3.equals("")){
                 k=1;
                 index++;
            }
          if(k==0){
           Position p=new Position(this,index++);
           webpage.addPositionForWord(s3,p,webpage.pageindex);
           }
           s3="";
         
      }
    }  
       
  }
         public void print(){
           for(int i=0;i<webpage.pageindex.size();i++)
             if(webpage.pageindex.get(i).word.equals("stack"))
                for(int j=0;j<webpage.pageindex.get(i).pos.size();j++)
                  System.out.println(webpage.pageindex.get(i).pos.get(j).wordIndex);
          }

 public PageIndex getPageIndex(){
    return this.webpage;
 }

}
