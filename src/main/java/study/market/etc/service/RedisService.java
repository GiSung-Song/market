package study.market.etc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate redisTemplate;

    public String getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setDataExpire(String key, String value, long seconds) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration time = Duration.ofSeconds(seconds);
        valueOperations.set(key, value, time);
    }

    public boolean matchKey(String key, String value) {
        String getValue = getData(key);

        if (getValue.equals(value)) {
            return true;
        }

        return false;
    }
}
