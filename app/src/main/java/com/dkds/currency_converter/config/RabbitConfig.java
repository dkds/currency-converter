package com.dkds.currency_converter.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUE_NAME = "requests.queue";
    public static final String EXCHANGE_NAME = "requests.exchange";
    public static final String ROUTING_KEY = "requests.key";

    @Bean
    public Queue requestQueue() {
        return new Queue(QUEUE_NAME, true); // durable queue
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue requestQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(requestQueue)
                .to(directExchange)
                .with(ROUTING_KEY);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(1);          // Single-thread
        factory.setMaxConcurrentConsumers(2);         // Up to 2
        factory.setPrefetchCount(1);          // Only 1 message at a time per thread
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }
}
