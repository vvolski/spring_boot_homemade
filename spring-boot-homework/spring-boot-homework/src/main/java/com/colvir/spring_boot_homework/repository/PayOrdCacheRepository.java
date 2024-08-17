package com.colvir.spring_boot_homework.repository;

import com.colvir.spring_boot_homework.model.PayOrd;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class PayOrdCacheRepository {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    public Optional<PayOrd> findById(Integer id) {
        String stringValue = redisTemplate.opsForValue().get(String.valueOf(id));

        if (stringValue == null) {
            return Optional.empty();
        }
        try {
            return Optional.of(objectMapper.readValue(stringValue, PayOrd.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void save(PayOrd payOrd) {
        try {
            redisTemplate.opsForValue().set(String.valueOf(payOrd.getId()), objectMapper.writeValueAsString(payOrd));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(fixedDelayString = "PT02M")
    public void clearAll() {
        try {
            Set<String> keys = redisTemplate.keys("*");
            if ((keys != null ? keys.size() : 0) == 0) {
                return;
            }
            System.out.printf("Очистка кэша, удалено %s записей", keys.size());
            redisTemplate.delete(keys);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
