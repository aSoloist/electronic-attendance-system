package system.attendance.electronic.common;

import system.attendance.electronic.model.AuthToken;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/5/26 22:21
 * @email ly@soloist.top
 * @description
 */
public interface TokenManager {

    /**
     * 创建一个AuthToken
     *
     * @param userId
     * @return
     */
    public AuthToken getToken(Long userId);

    /**
     * 检查一个Token是否有效
     *
     * @param token
     * @return
     */
    public Long checkToken(String token);

    /**
     * 清除Token
     * @param userId
     */
    public void deleteToken(Long userId);

}
