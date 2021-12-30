import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Backend{
        static String password= "";      // Enter your password here for mysql account
        static String uname = "";     // Enter your username for mysql account
        static String your_project_name_in_database= "";  // Enter the name of your database
        
  public static String back(String query) throws Exception {// TODO Auto-generated method stub        
        String url = "jdbc:mysql://localhost:3306/"+your_project_name_in_database+"";  // Lines 33-37 are for making connection to our mysql        
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url,uname,password);

        PreparedStatement ps = con.prepareStatement(query);
        ResultSet results = ps.executeQuery();
        results.next();
        try {
            return results.getString(1);
        }catch(Exception e){
            return "Authentication Failed";
        }
    }
    // this method is for my login window for Authentication. (This is for USER login)
    public static List user_Back(String query) throws Exception {// TODO Auto-generated method stub
        String url = "jdbc:mysql://localhost:3306/"+your_project_name_in_database+"";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url,uname,password);
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet results = ps.executeQuery();
        List a = new List();
        for(int i=1; results.next(); i++) {
            a.add(results.getString(1));
        }
        return a;
    }
    // this method is handling query for deleting and adding the database. (ADMIN WINDOW)
    public static void add_del(String query) throws Exception {// TODO Auto-generated method stub
        String url = "jdbc:mysql://localhost:3306/"+your_project_name_in_database+"";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url,uname,password);
        PreparedStatement ps = con.prepareStatement(query);
        ps.execute(query);// weren't able to change the database

    }
    // this method is retrieving the price of medicines from database after passing the queries (USER WINDOW)
    public static String check_cost(String query) throws Exception {// TODO Auto-generated method stub
        String url = "jdbc:mysql://localhost:3306/"+your_project_name_in_database+"";       
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url,uname,password);
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet results = ps.executeQuery();
        results.next();
        try
        {
            return results.getString(1);// weren't able to change the database
        }catch(Exception e){
            return "Enter valid medicine";
        }
    }
}
