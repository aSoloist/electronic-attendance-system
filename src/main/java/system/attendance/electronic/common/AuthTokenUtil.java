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
public class AuthTokenUtil implements TokenManager<String, String> {

    @Autowired
    private CacheManager cacheManager;

    @Override
    public AuthToken getToken(String userId) {
        String token = UUID.randomUUID().toString().replace("-", "");
        AuthToken authToken = new AuthToken(userId, token);
        Cache<String, String> cache = cacheManager.getCache("token", String.class, String.class);
        cache.put(authToken.getUserId(), authToken.getToken());
        return authToken;
    }

    @Override
    public String checkToken(String token) {
        Cache<String, String> cache = cacheManager.getCache("token", String.class, String.class);
        for (Cache.Entry<String, String> next : cache) {
            if (next != null && next.getValue().equals(token)) {
                return next.getKey();
            }
        }
        
        return null;
    }

    @Override
    public void deleteToken(String userId) {
        Cache<String, String> token = cacheManager.getCache("token", String.class, String.class);
        token.remove(userId);
    }
}
