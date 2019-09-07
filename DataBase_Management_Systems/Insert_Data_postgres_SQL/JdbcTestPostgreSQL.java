import java.sql.*;
import java.io.*;
class temp {
   public static void main (String args[]) {
      try {
         Class.forName("org.postgresql.Driver");
      }
      catch (ClassNotFoundException e) {
         System.err.println(e);
         System.exit(-1);
      }
      String query="";
      try {
          //Connecting to the database;
          Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "admin", "admin");
           String fileName = "temp.txt";

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
       
         Statement statement = connection.createStatement();
         ResultSet rs = statement.executeQuery(query);
         connection.close();
      }
      catch (java.sql.SQLException e) {
         System.err.println(e);
         System.exit(-1);
      }
   }
}