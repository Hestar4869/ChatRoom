package server.database.data;

/**
 * @className: GroupRelationship
 * @description: 群组关系的数据实体类，包含群组和用户之间的多对多关系
 * @author: HMX
 * @date: 2022-05-19 15:58
 */
public class GroupRelationship
{
    private String gname;
    private String uname;

    //构造函数
    public GroupRelationship(String gname, String uname){
        this.gname = gname;
        this.uname = uname;
    }

    //getter and setter
    public String getGname(){
        return gname;
    }
    public void setGname(String gname)
    {
        this.gname = gname;
    }
    public String getUname()
    {
        return uname;
    }
    public void setUname(String uname)
    {
        this.uname = uname;
    }
}
