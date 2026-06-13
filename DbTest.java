import java.sql.Connection;
import java.sql.DriverManager;

public class DbTest {
    public static void main(String[] args) {
        String[] regions = {
            "ap-south-1", "us-east-1", "eu-central-1", "ap-southeast-1",
            "us-west-1", "us-west-2", "ap-northeast-1", "ap-northeast-2",
            "eu-west-1", "eu-west-2", "sa-east-1", "ca-central-1"
        };
        
        String pass = "Aman99ralhan@";
        String user = "postgres.dfgtepnkibqzaysemdmb";
        
        for (String r : regions) {
            String url = "jdbc:postgresql://aws-0-" + r + ".pooler.supabase.com:6543/postgres?sslmode=require";
            System.out.println("Trying " + url + " ...");
            try {
                Connection conn = DriverManager.getConnection(url, user, pass);
                if (conn != null) {
                    System.out.println("SUCCESS! Region is: " + r);
                    conn.close();
                    return;
                }
            } catch (Exception e) {
                // Ignore
            }
        }
        System.out.println("Could not connect to any region.");
    }
}
