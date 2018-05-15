package comm.demo.utils.cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis 工具类
 * @author 小K
 * @since 2018/4/27.
 */
@Component
public class RedisUtils {

  @Autowired
  private RedisTemplate redisTemplate;

  //********************对string类型的数据进行操作的api*************************//
  /**
   * 读取缓存
   *
   * @param key
   * @return
   */
  public Object get(final String key) {
    Object result;
    ValueOperations operations = redisTemplate.opsForValue();
    result = operations.get(key);
    return result;
  }

  /**
   *   写入缓存(默认2小时)
   * @param key
   * @param value
   * @return
   */
  public boolean set(String key, String value) {
    boolean result = false;
    try {
      ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
      operations.set(key, value);
      //redisTemplate.
      redisTemplate.expire(key,7200L, TimeUnit.SECONDS);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 写入缓存(设置过期时间,默认是秒)
   * @param key
   * @param value
   * @param expireTime
   * @return
   */
  public boolean set(final String key,Object value,Long expireTime){
    boolean result = false;
    try {
      ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
      operations.set(key, value);
      redisTemplate.expire(key,expireTime, TimeUnit.SECONDS);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  public boolean set(String key, String value, Long expireTime, TimeUnit times) {
    boolean result = false;
    try {
      ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
      operations.set(key, value);
      redisTemplate.expire(key,expireTime, times);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 设置过期时间
   * @param key
   * @param expireTime
   */
  public void expire(String key,Long expireTime){
    redisTemplate.expire(key,expireTime, TimeUnit.SECONDS);
  }

  public void expire(String key, Long expireTime, TimeUnit times) {
    redisTemplate.expire(key,expireTime, times);
  }

  /**
   * 批量删除对应的value
   * @param keys
   */
  public void remove(final String... keys){
    for(String key : keys){
      remove(key);
    }
  }

  /**
   * 批量删除key
   * @param pattern
   */
  public void removePattern(final String pattern){
    Set<Serializable> keys = redisTemplate.keys(pattern);
    if (keys.size() > 0)
      redisTemplate.delete(keys);
  }


  /**
   * 删除对应的value
   *
   * @param key
   */
  public void remove(final String key) {
    if (exists(key)) {
      redisTemplate.delete(key);
    }
  }

  /**
   * 判断缓存中是否有对应的value
   *
   * @param key
   * @return
   */
  public boolean exists(final String key) {
    return redisTemplate.hasKey(key);
  }


  /**
   * 递增 steps
   * @param key
   * @param step
   * @return
   */
  public Long incrementByLongWithStep(String key,Long step){
    return redisTemplate.opsForValue().increment(key,step);
  }

  /**
   * 递增 1 step
   * @param key
   * @return
   */
  public Long incrementByLongOneStep(String key){
    return redisTemplate.opsForValue().increment(key,1L);
  }



}
