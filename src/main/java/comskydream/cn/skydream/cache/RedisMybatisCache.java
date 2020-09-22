package comskydream.cn.skydream.cache;


import comskydream.cn.skydream.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.HashOperations;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author Jayson
 * @date 2020/9/22 16:30
 */
@Slf4j
public class RedisMybatisCache implements Cache {
    private final HashOperations<String, String, Object> operations = SpringContextUtils.getApplicationContext().getBean(HashOperations.class);

    private final String id;

    public RedisMybatisCache(final String id) {

        if (StringUtils.isNotEmpty(id)) {
            log.info("Redis Cache hash id: " + id);
            this.id = id;
        } else {
            throw new IllegalArgumentException("Cache instances require an ID");
        }

    }

    @Override
    public String getId() {
        return id;
    }

    private String cacheKeyToString(Object key) {
        return key.toString();
    }

    /**
     * @param key
     * @param value
     */
    @Override
    public void putObject(final Object key, final Object value) {

        final String keyStr = cacheKeyToString(key);
        Optional.ofNullable(key).ifPresent(e -> operations.put(id, keyStr, value));
        log.debug("put cache " + keyStr + " success!");

    }

    @Override
    public Object getObject(final Object key) {
        Object o = null;
        try {
            if (Objects.nonNull(key)) {
                o = operations.get(id, cacheKeyToString(key));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            o = null;
        } finally {
            return o;
        }
    }

    @Override
    public Object removeObject(Object key) {
        String keyStr = cacheKeyToString(key);
        long update = operations.delete(id, keyStr);
        log.debug("delete cache " + keyStr + " success!");
        return update;
    }

    @Override
    public void clear() {

        operations.getOperations().delete(getId());

    }

    @Override
    public int getSize() {
        return operations.size(getId()).intValue();
    }

    /**
     * 内核已不调用此方法
     *
     * @return
     */
    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
