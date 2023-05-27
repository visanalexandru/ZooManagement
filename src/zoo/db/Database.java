package zoo.db;

import java.sql.Connection;
import java.lang.System;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class is a singleton class that manages access to the mysql database.
 */
public class Database {
    /**
     * The connection.
     */
    private final Connection connection;

    /**
     * The database instance.
     */
    private static Database database = null;

    /**
     * Loads the url, username and password from the system environment variables and uses
     * them to initiate the connection.
     *
     * @throws SQLException if the system variables are not correct.
     */
    private Database() throws SQLException {
        String url = System.getenv("DATABASE_URL");
        String username = System.getenv("DATABASE_USR");
        String password = System.getenv("DATABASE_PASS");
        connection = DriverManager.getConnection(url, username, password);
    }

    /**
     * @return the instance of the database, or null if an exception has been thrown.
     */
    public static Database getDatabase() {
        if (database == null) {
            try {
                database = new Database();
            } catch (SQLException exception) {
                System.out.println("Cannot create the database connection: " + exception);
                database = null;
            }
        }
        return database;
    }

    /**
     * @return the database connection.
     */
    public Connection getConnection() {
        return connection;
    }
}
