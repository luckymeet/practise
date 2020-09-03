package com.spring.transction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author yuminjun yuminjun@lexiangbao.com
 * @version 1.00
 * @date 2020/9/3 14:10
 * @record <pre>
 * version  author      date      desc
 * -------------------------------------------------
 * 1.00     yuminjun    2020/9/3   新建
 * -------------------------------------------------
 * </pre>
 */
public class TransactionUtil {

    private static final int MAX_CONNECTIONS = 100;
    private static final int TIME_OUT = 10;
    private static final ThreadLocal<Connection> synchronousConnection = new ThreadLocal<>();
    private static LinkedBlockingDeque<Connection> connectionCache = new LinkedBlockingDeque<Connection>(MAX_CONNECTIONS);

    static {
        for (int i = 0; i < MAX_CONNECTIONS; i++) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://39.105.58.23:3306/test?serverTimezone=GMT%2B8", "root", "root.ycw");
                connection.setAutoCommit(false);
                connectionCache.add(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static Connection startTransaction() throws Exception {
        Connection connection = synchronousConnection.get();
        if (connection == null) {
            connection = connectionCache.pollFirst(TIME_OUT, TimeUnit.SECONDS);
        }
        if (connection == null) {
            throw new TimeoutException("连接超时");
        }
        synchronousConnection.set(connection);
        return connection;
    }

    public static int execute(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = startTransaction();
            preparedStatement = connection.prepareStatement(sql);
            if (args != null) {
                for (int i = 1; i < args.length + 1; i++) {
                    preparedStatement.setObject(i, args[i - 1]);
                }
            }
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void commit() {
        Connection connection = synchronousConnection.get();
        try {
            connection.commit();
            synchronousConnection.remove();
            connectionCache.putLast(connection);
            System.out.println("事务提交");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void rollback() {
        Connection connection = synchronousConnection.get();
        try {
            connection.rollback();
            synchronousConnection.remove();
            connectionCache.putLast(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
