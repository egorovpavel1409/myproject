package org.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final StringRedisTemplate redisTemplate;

    public KafkaConsumer(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @KafkaListener(topics = "spring-messages", groupId = "my-group")
    public void consume(String message) {
        System.out.println("📥 Received: " + message);

        // сохраняем сообщение в Redis (список messages)
        redisTemplate.opsForList().leftPush("messages", message);
    }
}
