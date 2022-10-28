package ConnectionPoolRelated;

import Exceptions.CouponSystemException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ConnectionPool {

    private Set<Connection> connections = new HashSet<>();
    public static final int MAX_CONNECTIONS = 5;
    private boolean isActive;
    private String url = "jdbc:mysql://localhost:3306/coupons";
    private String user = "root";
    private String password = "Password";

    private static ConnectionPool instance;

    static {
        try {
            instance = new ConnectionPool();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private ConnectionPool() throws SQLException {

        for (int i = 0; i < MAX_CONNECTIONS; i++) {
            this.connections.add(DriverManager.getConnection(url, user, password));
        }
        isActive = true;
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    public synchronized Connection getConnection() throws CouponSystemException {
        if (!isActive) {
            throw new CouponSystemException(" connection pool not active");
        }
        while (this.connections.isEmpty()) {
            try {
                System.out.println("waiting get ");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Iterator<Connection> it = this.connections.iterator();
        Connection con = it.next();
        it.remove();
        return con;
    }

    public synchronized void restoreConnection(Connection connection) {
        this.connections.add(connection);
        notify();
    }

    public synchronized void closeAllConnections() throws CouponSystemException {
        System.out.println("waiting close");
        this.isActive = false;

        while (this.connections.size() < MAX_CONNECTIONS) {
            try {
                wait();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new CouponSystemException("closeAllConnections failure", e);
            }
        }
    }



}
