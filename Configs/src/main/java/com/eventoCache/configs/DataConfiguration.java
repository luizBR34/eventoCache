package com.eventoCache.configs;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.util.StringUtils;

import com.eventoApp.models.Event;
import com.eventoApp.models.User;

@Configuration
@EnableRedisHttpSession
public class DataConfiguration extends AbstractHttpSessionApplicationInitializer {
	
	@Value("${spring.redis.host}")
	private String host;
	@Value("${spring.redis.password}")
	private String password;
	@Value("${spring.redis.port}")
	private int port;
	
	@Value("${spring.redis.jedis.pool.max-total}")
	private int maxTotal;
	@Value("${spring.redis.jedis.pool.max-idle}")
	private int maxIdle;
	@Value("${spring.redis.jedis.pool.min-idle}")
	private int minIdle;
	
	
	//Cria o cliente jedis com a configuração de pool de conecçoes
	@Bean
	public JedisClientConfiguration getJedisClientConfiguration() {
		JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisPoolingClientConfigurationBuilder = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
		GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
		genericObjectPoolConfig.setMaxTotal(maxTotal);
		genericObjectPoolConfig.setMaxIdle(maxIdle);
		genericObjectPoolConfig.setMinIdle(minIdle);
		return jedisPoolingClientConfigurationBuilder.poolConfig(genericObjectPoolConfig).build();
		// https://commons.apache.org/proper/commons-pool/apidocs/org/apache/commons/pool2/impl/GenericObjectPool.html
	}

	//Cria a fabrica de conexoes do jedis
	@Bean
	public JedisConnectionFactory getJedisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(host);
		if (!StringUtils.isEmpty(password)) {
			redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
		}
		redisStandaloneConfiguration.setPort(port);
		return new JedisConnectionFactory(redisStandaloneConfiguration, getJedisClientConfiguration());
	}

	//Cria o RedisTemplate para persistir chaves/valores no dataset
	@Bean
	public RedisTemplate redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(getJedisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		return redisTemplate;
	}
	

	@Bean
	@Qualifier("eventOperations")
	public ValueOperations<Long, Event> eventOperations(RedisTemplate<Long, Event> redisTemplate) {
		return redisTemplate.opsForValue();
	}
	

    @Bean
    @Qualifier("hashUserOperations")
    public HashOperations<String, Integer, User> hashUserOperations(RedisTemplate<String, Object>  redisTemplate) {
        return redisTemplate.opsForHash();
    }
    
	@Bean
	@Qualifier("listOperations")
	public ListOperations<String, Event> listOperations(RedisTemplate<String, Event> redisTemplate) {
		return redisTemplate.opsForList();
	}
}
