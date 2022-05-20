package server.database.data;

/**
 * @className: Message
 * @description: TODO 类描述
 * @author: HMX
 * @date: 2022-05-20 10:41
 */
public class Message
{
    private int id;
    private String srcName;
    private String dstName;
    private String content;

    public Message(String srcName, String dstName, String content)
    {
        this.srcName = srcName;
        this.dstName = dstName;
        this.content = content;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getSrcName()
    {
        return srcName;
    }

    public void setSrcName(String srcName)
    {
        this.srcName = srcName;
    }

    public String getDstName()
    {
        return dstName;
    }

    public void setDstName(String dstName)
    {
        this.dstName = dstName;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
}
