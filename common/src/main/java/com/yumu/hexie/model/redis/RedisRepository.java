package com.yumu.hexie.model.redis;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.yumu.hexie.model.localservice.HomeCart;
import com.yumu.hexie.model.market.Cart;
import com.yumu.hexie.model.market.car.OrderCarInfo;
import com.yumu.hexie.model.promotion.share.ShareAccessRecord;
import com.yumu.hexie.model.system.SystemConfig;

@Component(value = "redisRepository")
public class RedisRepository {

    @Inject
    private RedisTemplate<String, Cart> cartRedisTemplate;
    @Inject
    private RedisTemplate<String, HomeCart> homeCartRedisTemplate;
    @Inject
    private RedisTemplate<String, ShareAccessRecord> shareAccessRecordTemplate;
    
    @Inject
    private RedisTemplate<String, SystemConfig> systemConfigRedisTemplate;
    
    @Inject
    private RedisTemplate<String, OrderCarInfo> orderCarInfoRedisTemplate;//创建订单之前用户填写的车辆信息
    
    /**
     * 获取订单车辆信息 
     */
    public OrderCarInfo getOrderCarInfo(long userId) {
    	return orderCarInfoRedisTemplate.opsForValue().get(Keys.orderCarInfoKey(userId));
    }
    
    /**
     * 保存订单车辆信息
     * @param carInfo
     */
    public void setOrderCarInfo(OrderCarInfo carInfo) {
    	orderCarInfoRedisTemplate.opsForValue().set(Keys.orderCarInfoKey(carInfo.getUserId()), carInfo);
    }
    
    public SystemConfig getSystemConfig(String key) {
        return systemConfigRedisTemplate.opsForValue().get(Keys.systemConfigKey(key));
    }
    public void setSystemConfig(String key,SystemConfig value) {
        systemConfigRedisTemplate.opsForValue().set(Keys.systemConfigKey(key), value, 5, TimeUnit.MINUTES);
    }

    public void setHomeCart(String key,HomeCart value){
        homeCartRedisTemplate.opsForValue().set(key, value);
    }
    public HomeCart getHomeCart(String key) {
        return homeCartRedisTemplate.opsForValue().get(key);
    }
    
    public void setCart(String key, Cart value) {
        cartRedisTemplate.opsForValue().set(key, value);
    }
    public Cart getCart(String key) {
        return cartRedisTemplate.opsForValue().get(key);
    }
    
    public void removeCart(String key) {
    	cartRedisTemplate.delete(key);
    }
    
    //分享信息保存1天
    public void setShareRecord(String key, ShareAccessRecord value) {
    	shareAccessRecordTemplate.opsForValue().set(key, value, 1, TimeUnit.DAYS);
    }
    public ShareAccessRecord getShareRecord(String key) {
        return shareAccessRecordTemplate.opsForValue().get(key);
    }
    
    public void removeShareRecord(String key) {
    	shareAccessRecordTemplate.delete(key);
    }

//    
//    public void put(String key, String value, Long expireDate) {
//        stringRedisTemplate.opsForValue().set(key, value, expireDate, TimeUnit.MINUTES);
//    }
//
//    public void put(String key, Long value, Long expireDate) {
//        redisTemplate.opsForValue().set(key, value, expireDate, TimeUnit.MILLISECONDS);
//    }
//
//    public void increment(String key) {
//        stringRedisTemplate.opsForValue().increment(key, 1);
//    }
//
//    public void decrement(String key) {
//        stringRedisTemplate.opsForValue().increment(key, -1);
//    }
//
//    public void put(String key, Long value) {
//        redisTemplate.opsForValue().set(key, value);
//    }
//
//    public void put(String key, User value) {
//        redisUserTemplate.opsForValue().set(key, value);
//    }
//
//    public void put(String key,RgroupRule rule) {
//    	redisRgroupRuleTemplate.opsForValue().set(key, rule,1800,TimeUnit.SECONDS);
//    }
//    public String get(String key) {
//        return stringRedisTemplate.opsForValue().get(key);
//    }
//
//    public boolean hasKey(String key) {
//        return stringRedisTemplate.hasKey(key);
//    }
//
//    public Long getLong(String key) {
//        return redisTemplate.opsForValue().get(key);
//    }
//
//    public User getUser(String key) {
//        return redisUserTemplate.opsForValue().get(key);
//    }
//
//    public Object getHash(String key, String hashKey) {
//        return redisTemplate.boundHashOps(key).get(hashKey);
//    }
//
//    public void putHash(String key, String hashKey, Object value) {
//        redisTemplate.opsForHash().put(key, hashKey, value);
//    }
//
//    public boolean exists(String key, Long hashKey) {
//        return redisTemplate.opsForHash().hasKey(key, hashKey);
//    }
//
//    public void addForZSet(String key, Long value) {
//        redisTemplate.opsForZSet().add(key, value, new Date().getTime());
//    }
//
//    public void addForZSetWithScore(String key, Long value, double score) {
//        redisTemplate.opsForZSet().add(key, value, score);
//    }
//
//    public void addForSet(String key, Long value) {
//        redisTemplate.opsForSet().add(key, value);
//    }
//
//    public Set<Long> intersect(String key, Collection<String> otherKeys, String destKey) {
//        redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey);
//        return redisTemplate.opsForZSet().reverseRange(destKey, 0, -1);
//    }
//
//    public Set<Long> difference(String key, String otherKey, String destKey, long start, long end) {
//        redisTemplate.opsForSet().differenceAndStore(key, otherKey, destKey);
//        return redisTemplate.opsForZSet().reverseRange(destKey, start, end);
//    }
//
//    public Set<String> intersect(String key, String otherKey, String destKey) {
//        stringRedisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);
//        return stringRedisTemplate.opsForZSet().reverseRange(destKey, 0, -1);
//    }
//
//    public Set<Long> intersect(String key, Collection<String> otherKeys, String destKey, long start, long end) {
//        redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey);
//        return redisTemplate.opsForZSet().reverseRange(destKey, start, end);
//    }
//
//    public Set<String> intersect(String key, String otherKeys, String destKey, long start, long end) {
//        stringRedisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey);
//        return stringRedisTemplate.opsForZSet().reverseRange(destKey, start, end);
//    }
//
//    public void addForZSet(String key, String value) {
//        stringRedisTemplate.opsForZSet().add(key, value, new Date().getTime());
//    }
//
//    public void addForZSetWithScore(String key, String value, double score) {
//        stringRedisTemplate.opsForZSet().add(key, value, score);
//    }
//
//    public void addForSet(String key, String value) {
//        stringRedisTemplate.opsForSet().add(key, value);
//    }
//
//    public boolean isMemberOfZSet(String key, Long member) {
//        return redisTemplate.opsForZSet().score(key, member) != null;
//    }
//
//    public void removeForZset(String key, Long value) {
//        redisTemplate.opsForZSet().remove(key, value);
//    }
//
//    public void removeForZset(String key, String value) {
//        stringRedisTemplate.opsForZSet().remove(key, value);
//    }
//
//    public void removeForSet(String key, String value) {
//        stringRedisTemplate.opsForSet().remove(key, value);
//    }
//
//    public Set<Long> members(String key) {
//        return redisTemplate.opsForZSet().reverseRange(key, 0, -1);
//    }
//
//    public Set<Long> members(String key, Long member, Long size) {
//        Long rank = redisTemplate.opsForZSet().reverseRank(key, member);
//        return redisTemplate.opsForZSet().reverseRange(key, rank, rank + size);
//    }
//
//    public Long unionAndStore(String key, Collection<String> otherKeys, String destKey) {
//        return redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey);
//    }
//
//    public Set<Long> unionAndStore(String key, String otherKey, String destKey, long start, long end) {
//        redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
//        return redisTemplate.opsForZSet().reverseRange(destKey, start, end);
//    }
//
//    public Set<Long> members(String key, long start, long end) {
//        return redisTemplate.opsForZSet().reverseRange(key, start, end);
//    }
//
//    public Set<Long> membersByScore(String key, long start, long end) {
//        return redisTemplate.opsForZSet().reverseRangeByScore(key, start, end);
//    }
//
//    public Set<Long> membersFromValue(String key, Long value, long size) {
//        Long start = (value == null ? 0 : redisTemplate.opsForZSet().reverseRank(key, value) + 1);
//        Long end = Math.max(0, start + size - 1);
//        return redisTemplate.opsForZSet().reverseRange(key, start, end);
//    }
//
//    public Set<String> membersWithString(String key, long start, long end) {
//        return stringRedisTemplate.opsForZSet().reverseRange(key, start, end);
//    }
//
//    public void removeKey(String key) {
//        stringRedisTemplate.delete(key);
//    }
//
//    public Long countOfZSet(String key) {
//        return redisTemplate.opsForZSet().size(key);
//    }

}
