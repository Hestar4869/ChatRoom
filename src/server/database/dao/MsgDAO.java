package server.database.dao;

import server.database.data.Message;

import java.util.List;

/**
 * @className: MsgDAO
 * @description: 有关消息类的数据库接口
 * @author: HMX
 * @date: 2022-05-22 19:43
 */
public interface MsgDAO
{
    //根据传入的用户名，查询所有该用户发送的，以及发送给该用户的消息
    public List<Message> findByUsername(String username) throws Exception;
    public List<Message> findByTwoUsername(String user1,String user2) throws Exception;
    public List<Message> findByGroupName(String groupName) throws Exception;
    public void insert(Message msg) throws Exception;
}
