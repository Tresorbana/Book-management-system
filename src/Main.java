import java.awt.*;
import java.sql.*;


public class Main {
    private static final String URL = "jdbc:postgresql://localhost:5432/online_book_store";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Tresor@2008";
    public static void main(String[] args){
     try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)){
         System.out.println("Connection to postgreSQL");
         addCustomer(conn, "Brian", "Muhanga, Rwanda", "brian@gmail.com");
         listCustomers(conn);
         updateCustomer(conn, 1, "test@example.com");
         deleteCustomer(conn, 4);
     }catch (SQLException e){
         System.out.println("Connection is unsuccessful: " + e.getMessage());
     }
    }
    public static void addCustomer(Connection conn, String name, String address, String email){
        String query = "INSERT INTO customers(full_name, address, email) VALUES (?, ?, ?)";
        try(PreparedStatement pstmt = conn.prepareStatement(query)){
          pstmt.setString(1, name);
          pstmt.setString(2, address);
          pstmt.setString(3, email);
          int affected = pstmt.executeUpdate();
          if(affected > 0){
              System.out.println("Customer created successfully!");
          }else{
              System.out.println("Customer was not created!");
          }
        }catch (SQLException e){
            System.out.println("Something went wrong!");
        }
    }
    public static void listCustomers(Connection conn){
        String query = "SELECT * FROM customers";
        try(Statement pstmt = conn.createStatement()){
         ResultSet list = pstmt.executeQuery(query);
         System.out.println("\nCustomers : ");
         while (list.next()){
             int id = list.getInt("customer_id");
             String name = list.getString("full_name");
             String email = list.getString("email");
             System.out.println(id + " | " + name + " | " + email);
         }
        }catch (SQLException e){
            System.out.println("Something went wrong!");
        }
    }
    public static void updateCustomer(Connection conn, Integer customerId, String newEmail){
        String query = "UPDATE customers SET email = ? WHERE customer_id = ?";
        try(PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, newEmail);
            stmt.setInt(2, customerId);
            int rows = stmt.executeUpdate();
            if(rows > 0){
                System.out.println("Update is complete");
            }else{
                System.out.println("Update is not done");
            }
        }catch (SQLException e){
            System.out.println("Something went wrong");
        }
    }
    public static void deleteCustomer(Connection conn, Integer customerId){
        String query = "DELETE FROM customers WHERE customer_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, customerId);
            int rows = stmt.executeUpdate();
            if(rows > 0){
                System.out.println("Customer deleted successfully");
            }
        }catch (SQLException e){
            System.out.println("Something went wrong!");
        }
    }
}