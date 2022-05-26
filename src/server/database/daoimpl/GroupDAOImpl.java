package server.database.daoimpl;

import server.database.dao.BaseDAO;
import server.database.dao.GroupDAO;
import server.database.data.Message;

import java.sql.ResultSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 * @className: GroupDAOImpl
 * @description: GroupDAO接口的具体实现
 * @author: HMX
 * @date: 2022-05-26 14:15
 */
public class GroupDAOImpl extends BaseDAO implements GroupDAO
{

    @Override
    public List<String> findUsersByGroup(String groupName) throws Exception
    {
        String sql=String.format("select uname from group_relationship where gname='%s'",groupName);
        ResultSet rs= statement.executeQuery(sql);
        List<String> users=new Vector<>();
        while (rs.next()){
            users.add(rs.getString("uname"));
        }
        return users;
    }

    @Override
    public List<String> findGroupsbyUser(String username) throws Exception
    {
        List<String> groups=new Vector<>();
        String sql=String.format("select gname from group_relationship where uname='%s'",username);
        ResultSet rs=statement.executeQuery(sql);
        while (rs.next()){
            groups.add(rs.getString("gname"));
        }
        return groups;
    }

    @Override
    public void insertGroup(List<String> users, String groupName) throws Exception
    {
        System.out.println("开始插入数据");
        //插入群名
        String sql=String.format("INSERT into `group` (group_name) values ('%s')",groupName);
        statement.executeUpdate(sql);
        //插入群关系
        for (String user:users)
        {//要执行的sql语句
            sql = String.format("INSERT into group_relationship (gname,uname) VALUES ('%s','%s')", groupName,user);
            statement.executeUpdate(sql);
        }

    }

    @Override
    public void insertGroupMsg(Message msg) throws Exception
    {

    }

    public static void main(String[] args) throws Exception
    {
        GroupDAO groupDAO=new GroupDAOImpl();
        List<String> list=new Vector<>();
        list.add("a");
        list.add("b");
        groupDAO.insertGroup(list,"新群聊");
    }
}
