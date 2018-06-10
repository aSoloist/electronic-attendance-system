package system.attendance.electronic.model;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/6/10 17:04
 * @email ly@soloist.top
 * @description
 */
public class ApplicationAndUser {
    
    private Application application;
    
    private User user;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
