package system.attendance.electronic.common;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.core.EhcacheManager;
import org.springframework.cache.Cache;
import org.springframework.cache.jcache.JCacheCache;
import org.springframework.cache.jcache.JCacheCacheManager;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Soloist
 * @version 1.0
 * @createtime 2018/5/26 22:07
 * @email ly@soloist.top
 * @description url:http://www.cnblogs.com/holoyong/p/7263604.html
 * 因JCache不允许声明Object之外的key-value，故自定义Manager类
 */
public class JCacheEhcacheManager extends JCacheCacheManager {

    @Override
    protected Collection<Cache> loadCaches() {
        Collection<Cache> caches = new LinkedHashSet<>();
        EhcacheManager ehcacheManager;
        try {
            ehcacheManager = getCacheManager().unwrap(EhcacheManager.class);
        } catch (Exception e) {
            //如果不是Ehcache，则使用原loadCaches
            return super.loadCaches();
        }
        Set<Map.Entry<String, CacheConfiguration<?, ?>>> entries = ehcacheManager.getRuntimeConfiguration().
                getCacheConfigurations().entrySet();
        for (Map.Entry<String, CacheConfiguration<?, ?>> entry : entries) {
            //获取cache配置
            CacheConfiguration<?, ?> cacheConfiguration = entry.getValue();
            //通过cacheName、keyType、valueType获取cache
            javax.cache.Cache jcache = getCacheManager().getCache(entry.getKey()
                    , cacheConfiguration.getKeyType()
                    , cacheConfiguration.getValueType());
            caches.add(new JCacheCache(jcache, isAllowNullValues()));
        }
        return caches;
    }
}
