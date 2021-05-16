package ua.knu.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static ua.knu.util.Constants.Other.UTILITY_CLASS;

public class ConnectionFactory {

    private ConnectionFactory() {
        throw new IllegalStateException(UTILITY_CLASS);
    }

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/study-system",
                    "postgres",
                    "Tsunami9"
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
