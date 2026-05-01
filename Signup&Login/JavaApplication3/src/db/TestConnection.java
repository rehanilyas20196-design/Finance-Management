package db;

import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {

        try (Connection conn = DBConnection.connect()) {

            if (conn != null) {
                System.out.println("✅ Connected to Supabase successfully!");
            }

        } catch (Exception e) {

            System.out.println("❌ Connection failed!");
            e.printStackTrace(); // IMPORTANT: shows real error
        }
    }
}