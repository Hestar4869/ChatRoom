package server.database.dao;

import server.database.data.User;

import java.util.List;

/**
 * @className: UserDAO
 * @description: 用户信息及用户关系的存储查询抽象类
 * @author: HMX
 * @date: 2022-05-19 17:02
 */
public interface UserDAO
{
    //查询
    public List<User> getAll() throws Exception;
    public String findPasswordByUsername(String username) throws Exception;

    //插入
    public void insert(User user) throws Exception;
    public void insertRelationship(String srcId,String dstId) throws Exception;
}
