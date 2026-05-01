package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // IMPORTANT: JDBC format ONLY (no username/password in URL)
    private static final String URL =
        "jdbc:postgresql://aws-1-ap-southeast-1.pooler.supabase.com:5432/postgres?sslmode=require";

    private static final String USER =
        "postgres.fgsjzhhtwdnafvlkjxoh";

    private static final String PASSWORD =
        "QMJN4P+jk$#+SiS";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}