package server.database.dao;

import server.database.data.Message;

import java.util.List;

/**
 * @className: GroupDAO
 * @description: 群组信息及关系的存储查询抽象类
 * @author: HMX
 * @date: 2022-05-19 17:19
 */
public interface GroupDAO
{
    //根据群名查找该群内所有用户
    public List<String> findUsersByGroup(String groupName) throws Exception;
    //根据用户名，查找该用户所加入的群组
    public List<String> findGroupsbyUser(String username) throws Exception;
    //插入新群组
    public void insertGroup(List<String> users,String groupName) throws Exception;

    //插入群组信息
    public void insertGroupMsg(Message msg) throws Exception;
}
