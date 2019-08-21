package com.lovecws.mumu.presto.jdbc;

import com.lovecws.mumu.presto.common.jdbc.JdbcConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @program: mumu-presto
 * @description: presto的基本操作
 * @author: 甘亮
 * @create: 2019-08-19 16:39
 **/
public class PrestoJdbcOperation {

    public void showTables() {
        JdbcConfig jdbcConfig = new JdbcConfig("jdbc:presto://172.31.134.225:9001/hive", "com.facebook.presto.jdbc.PrestoDriver", "admin", "");
        Connection connection = jdbcConfig.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("show tables from test");
            resultSet = preparedStatement.executeQuery();
            int columnCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String string = resultSet.getString(i);
                    System.out.println(string);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (Exception ex) {
            }
        }
    }

    public static void main(String[] args) {
        new PrestoJdbcOperation().showTables();
    }
}
