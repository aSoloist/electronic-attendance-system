package system.attendance.electronic.model;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/5/26 22:20
 * @email ly@soloist.top
 * @description
 */
public class AuthToken {
    
    private String userId;
    private String token;

    public AuthToken(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
