package constant;

/**
 * @className: constant.MyConstant
 * @description: 一些必要的常量
 * @author: HMX
 * @date: 2022-05-20 19:44
 */
public interface MyConstant
{
    //socket中传送的数据类型
    public final String TYPE_LOGOUT="LOGOUT";
    public final String TYPR_MESSAGE="MESSAGE";
    public final String TYPE_USER_LOGIN ="USERLOGIN";
    public final String TYPE_USER_LOGOUT ="USERLOGOUT";
    public final String TYPE_GROUP_CREATE="GROUPGREATE";

    //聊天记录的类型
    public final String MSGTYPE_USER="user";
    public final String MSGTYPE_GROUP="group";
}
