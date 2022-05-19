package server.database.data;

/**
 * @className: Group
 * @description: 群组信息的数据实体类
 * @author: HMX
 * @date: 2022-05-19 15:42
 */
public class Group
{
    private int id;
    private String name;

    //构造函数
    public Group(int id, String name){
        this.id = id;
        this.name = name;
    }

    //setter and getter
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
}
