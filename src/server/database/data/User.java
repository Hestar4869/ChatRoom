package server.database.data;

/**
 * @className: User
 * @description: 用户信息的数据实体类
 * @author: HMX
 * @date: 2022-05-19 15:17
 */
public class User
{
    private String username;
    private String password;

    //构造函数
    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    //getter and setter
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
}
