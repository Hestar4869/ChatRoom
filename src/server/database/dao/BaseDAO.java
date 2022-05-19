package server.database.dao;

import java.sql.*;

/**
 * @className: BaseDAO
 * @description: 所有DAO实现类的基类，负责初始化数据库连接以及解释器的生成
 * @author: HMX
 * @date: 2022-05-19 16:00
 */
public class BaseDAO
{
    //JDBC数据库驱动名
    private static String driver = "com.mysql.jdbc.Driver";
    //数据库地址
    private static String url = "jdbc:mysql://127.0.0.1:3306/chat_room?serverTimezone=GMT%2B8";
    //连接数据库的账号密码
    private static String user = "root";
    private static String password = "root";

    public Connection connection;
    public Statement statement;
    public BaseDAO(){
        try
        {
            connection=getConnection();
            statement=connection.createStatement();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

    }

    static
    {
        try
        {
            Class.forName(driver);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(url, user, password);
    }

    public static void closeAll(Connection conn, Statement stmt, ResultSet rs) throws SQLException
    {
        if (rs != null)
        {
            rs.close();
        }
        if (stmt != null)
        {
            stmt.close();
        }
        if (conn != null)
        {
            conn.close();
        }
    }

}
