package com.eventoCache.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/* Docker
	docker build -t luizpovoa/eventocache:0.0.1-SNAPSHOT .
	docker run -p 8585:8585 -d -e REDIS_HOST=redis-17294.c93.us-east-1-3.ec2.cloud.redislabs.com -e REDIS_PORT=17294 -e REDIS_PASSWORD=jTg6lvzKF0LTvbeTUUewJWaaVrgoWNZ8 -e RABBITMQ_HOST=172.18.0.3 -e RABBITMQ_PORT=5672 -e RABBITMQ_USERNAME=eventoapp -e RABBITMQ_PASSWORD=segredo -e RESPONSE_EXCHANGE=eventoapp.integration.exchange -e RESPONSE_ROUTING_KEY=routingKey_eventoapp -e EVENTO_WS_URI=http://172.18.0.11:9090 --network eventoapp-network --name EventoCache luizpovoa/eventocache:0.0.1-SNAPSHOT
*/

@SpringBootApplication
@ComponentScan({"com.eventoCache", "com.eventoApp.models"})
public class EventoCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventoCacheApplication.class, args);
	}
}
