package system.attendance.electronic.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import system.attendance.electronic.model.AuthToken;

import javax.cache.Cache;
import javax.cache.CacheManager;
import java.util.UUID;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/5/26 15:10
 * @email ly@soloist.top
 * @description
 */
@Component
public class AuthTokenUtil implements TokenManager {

    @Autowired
    private CacheManager cacheManager;

    @Override
    public AuthToken getToken(Long userId) {
        String token = UUID.randomUUID().toString().replace("-", "");
        AuthToken authToken = new AuthToken(userId, token);
        Cache<Long, String> cache = cacheManager.getCache("token", Long.class, String.class);
        cache.put(authToken.getUserId(), authToken.getToken());
        return authToken;
    }

    @Override
    public Long checkToken(String token) {
        Cache<Long, String> cache = cacheManager.getCache("token", Long.class, String.class);
        for (Cache.Entry<Long, String> next : cache) {
            if (next != null && next.getValue().equals(token)) {
                return next.getKey();
            }
        }
        
        return null;
    }

    @Override
    public void deleteToken(Long userId) {
        Cache<Long, String> token = cacheManager.getCache("token", Long.class, String.class);
        token.remove(userId);
    }
}
