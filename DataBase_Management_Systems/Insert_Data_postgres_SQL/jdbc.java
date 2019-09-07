import java.sql.*;
import java.io.*;
class jdbc {
   public static void main (String args[]) {
      try {
         Class.forName("org.postgresql.Driver");
      }
      catch (ClassNotFoundException e) {
         System.err.println(e);
         System.exit(-1);
      }
      String query="";
      String fileName = "E:\\temp.txt";

      // This will reference one line at a time
         String line = null;

        try {
          // FileReader reads text files in the default encoding.
          FileReader fileReader = 
              new FileReader(fileName);

          // Always wrap FileReader in BufferedReader.
          BufferedReader bufferedReader = 
              new BufferedReader(fileReader);
         
          while((line = bufferedReader.readLine()) != null) {
             query+=line;
          }   

          // Always close files.
          bufferedReader.close();         
      }
      catch(FileNotFoundException ex) {
          System.out.println(
              "Unable to open file '" + 
              fileName + "'");                
      }
      catch(IOException ex) {
          System.out.println(
              "Error reading file '" 
              + fileName + "'");                  
          // Or we could just do this: 
          // ex.printStackTrace();
      }
       //System.out.println(query); 
      try {
          //Connecting to the database;
           Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5433/intellify", "postgres", "admin");
          
       
         Statement statement = connection.createStatement();
         //ResultSet rs = statement.executeQuery(query);
         statement.executeUpdate(query);
         connection.close();
      }
      catch (java.sql.SQLException e) {
         System.err.println(e);
         System.exit(-1);
      }
   }
}