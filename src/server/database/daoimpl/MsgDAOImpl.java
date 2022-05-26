package server.database.daoimpl;

import server.database.dao.BaseDAO;
import server.database.dao.MsgDAO;
import server.database.data.Message;

import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

/**
 * @className: MsgDAOImpl
 * @description: MsgDAO接口实现
 * @author: HMX
 * @date: 2022-05-22 19:50
 */
public class MsgDAOImpl extends BaseDAO implements MsgDAO
{
    @Override
    public List<Message> findByUsername(String username) throws Exception
    {
        String sql=String.format("SELECT * FROM message where src_name='%s' or dst_name='%s' ORDER by id ASC",username,username);
        ResultSet rs=statement.executeQuery(sql);
        List<Message> messages=new Vector<>();
        while (rs.next()){
            String srcName=rs.getString("src_name"),
                    dstName=rs.getString("dst_name"),
                    content=rs.getString("content");
            messages.add(new Message(srcName,dstName,content));
        }
        return messages;
    }

    @Override
    public List<Message> findByTwoUsername(String user1, String user2) throws Exception
    {
        String sql=String.format("SELECT * FROM message where (src_name='%s' and dst_name='%s') or (src_name='%s' and dst_name='%s')ORDER by id ASC",user1,user2,user2,user1);
        ResultSet rs=statement.executeQuery(sql);
        List<Message> messages=new Vector<>();
        while (rs.next()){
            String srcName=rs.getString("src_name"),
                    dstName=rs.getString("dst_name"),
                    content=rs.getString("content");
            messages.add(new Message(srcName,dstName,content));
        }
        return messages;
    }

    @Override
    public List<Message> findByGroupName(String groupName) throws Exception
    {
        String sql=String.format("SELECT * FROM message where  dst_name='%s' ORDER by id ASC",groupName);
        ResultSet rs=statement.executeQuery(sql);
        List<Message> messages=new Vector<>();
        while (rs.next()){
            String srcName=rs.getString("src_name"),
                    dstName=rs.getString("dst_name"),
                    content=rs.getString("content");
            messages.add(new Message(srcName,dstName,content));
        }
        return messages;
    }

    @Override
    public void insert(Message msg) throws Exception
    {
        String sql=String.format("insert into message (src_name,dst_name,content) values ('%s','%s','%s')",
                msg.getSrcName(),msg.getDstName(),msg.getContent());
        statement.executeUpdate(sql);

    }

    public static void main(String[] args) throws Exception
    {
        MsgDAO msgDAO=new MsgDAOImpl();
//        msgDAO.insert(new Message("a","b","hello b"));
//        msgDAO.insert(new Message("b","a","hello a"));
        List<Message> messages=new Vector<>();
        messages=msgDAO.findByUsername("a");
        for (Message msg : messages)
        {
            System.out.println(msg.getSrcName()+" 向 " + msg.getDstName()+" 说 ："+msg.getContent());
        }
    }
}
