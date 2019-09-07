public class SearchEngine{
        public static InvertedPageIndex main; 
	public SearchEngine() {
            main=new InvertedPageIndex();
	}

	public void performAction(String actionMessage) {
           System.out.print(actionMessage+" : ");
           String s[]=new String[3];
           s[0]="";s[1]="";s[2]="";
           int j=0;
          for(int i=0;i<actionMessage.length();i++){
             if(actionMessage.charAt(i)!=' ')
               s[j]+=actionMessage.charAt(i);
             else
              j++;
          }
          if(s[0].equals("addPage")){
             PageEntry p=new PageEntry(s[1]);
             main.addPage(p);
             System.out.println("");
          }
          if(s[0].equals("queryFindPagesWhichContainWord")){
            String s2=s[1].toLowerCase();
            if(s2.equals("stacks")||s2.equals("structures")||s2.equals("applications"))
               s2=s2.substring(0,s2.length()-1);
            MyLinkedList<PageEntry> p=main.getPagesWhichContainWord(s2);
            if(p.size()==0)
             System.out.println("No webpage contains word "+s[1]);
            else{
             for(int i=0;i<p.size();i++)
              System.out.print(p.get(i).name+",");
              System.out.println(" ");
          }
        }
          if(s[0].equals("queryFindPositionsOfWordInAPage")){
            PageEntry p1=new PageEntry(s[2]);
             s[1]=s[1].toLowerCase();
             MyLinkedList<WordEntry> temp= p1.webpage.pageindex;
             MyLinkedList<Position> p3=null;
             for(int i=0;i<temp.size();i++)
                if(temp.get(i).word.equals(s[1])||temp.get(i).word.equals(s[1].substring(0,s[1].length()-1))){
                     p3=temp.get(i).pos;
                     break;
                }
            if(p3!=null)
             for(int i=0;i<p3.size();i++)
              System.out.print(p3.get(i).wordIndex+", ");
            else
              System.out.print(":Webpage "+s[2] +" doesn't contain the Word "+s[1]);
             System.out.println(" ");       
          }
	}
}
