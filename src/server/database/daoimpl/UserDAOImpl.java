package server.database.daoimpl;

import server.database.dao.BaseDAO;
import server.database.dao.UserDAO;
import server.database.data.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: UserDAOImpl
 * @description: TODO 类描述
 * @author: HMX
 * @date: 2022-05-19 17:03
 */
public class UserDAOImpl extends BaseDAO implements UserDAO
{
    @Override
    public List<User> getAll() throws Exception
    {
        //建立连接
        Connection connection = BaseDAO.getConnection();
        //要执行的sql语句
        String sql = "SELECT * FROM USER";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<User> userList = new ArrayList<>();
        while (resultSet.next())
        {
            //拆分出resultSet中的user类
            User user = new User(resultSet.getString("username"), resultSet.getString("password"));
            userList.add(user);
        }
        return userList;
    }

    /**
     * @param: username
     * @description: 根据传入的用户名返回对应的密码
     * @return: java.lang.String
     * @author: HMX
     * @date: 2022-4-7 17:29
     */
    @Override
    public String findPasswordByUsername(String username) throws Exception
    {
        //要执行的sql语句
        String sql = "SELECT password FROM USER WHERE username=" + "\"" + username + "\"";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next())
        {
            return resultSet.getString("password");
        }
        return "";
    }

    @Override
    public void insert(User user) throws Exception
    {
        //要执行的sql语句
        String sql = String.format("INSERT into USER (username,password) VALUES ('%s','%s')", user.getUsername(), user.getPassword());
        statement.executeUpdate(sql);
    }

    @Override
    public void insertRelationship(String srcName, String dstName) throws Exception
    {
        String sql = String.format("INSERT into user_relationship (src_name,dst_name) VALUES ('%s','%s')",srcName,dstName);
        statement.executeUpdate(sql);
    }

}
