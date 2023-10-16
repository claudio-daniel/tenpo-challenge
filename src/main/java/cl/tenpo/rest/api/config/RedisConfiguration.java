package cl.tenpo.rest.api.config;

import cl.tenpo.rest.api.model.entity.PercentageValueHistory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(
        enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP,
        basePackages = {"cl.tenpo.rest.api.repository.redis"})
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String, PercentageValueHistory> redisTemplate(RedisConnectionFactory connectionFactory) {
        var redisTemplate = new RedisTemplate<String, PercentageValueHistory>();
        redisTemplate.setConnectionFactory(connectionFactory);

        return redisTemplate;
    }
}