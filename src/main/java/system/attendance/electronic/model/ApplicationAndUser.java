package system.attendance.electronic.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/10 17:04
 * @email ly@soloist.top
 * @description
 */
public class ApplicationAndUser {

    private String id;

    @JSONField(format = "yyyy-MM-dd")
    private Date beginDate;

    @JSONField(format = "yyyy-MM-dd")
    private Date endDate;

    /**
     * 0 通过 1 允许
     */
    private Byte result;

    /**
     * 1 请假 2 出差 3 加班
     */
    private Byte type;
    
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ApplicationAndUser(Application application) {
        this.id = application.getId();
        this.beginDate = application.getBeginDate();
        this.endDate = application.getEndDate();
        this.result = application.getResult();
        this.type = application.getType();
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Byte getResult() {
        return result;
    }

    public void setResult(Byte result) {
        this.result = result;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
