package server.database.data;

/**
 * @className: UserRelationship
 * @description: 用户好友关系的数据实体类，作为用户对用户，多对多的关系中间表
 * @author: HMX
 * @date: 2022-05-19 15:41
 */
public class UserRelationship
{
    private int sid;
    private int pid;

    public UserRelationship(int sid, int pid)
    {
        this.sid = sid;
        this.pid = pid;
    }

    public int getSid()
    {
        return sid;
    }

    public void setSid(int sid)
    {
        this.sid = sid;
    }

    public int getPid()
    {
        return pid;
    }

    public void setPid(int pid)
    {
        this.pid = pid;
    }
}
