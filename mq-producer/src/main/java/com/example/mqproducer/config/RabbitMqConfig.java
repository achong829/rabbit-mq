package com.example.mqproducer.config;

import com.rabbitmq.client.AMQP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class RabbitMqConfig {

	@Autowired
	private ConnectionFactory connectionFactory;


//
//	@Bean(value = "wuchong001change")
//	public Exchange setExchange(){
//		return ExchangeBuilder.headersExchange("wuchong001change").durable(true).build();
//	}
//
//
//	@Bean(value = "queue001")
//	public Queue setQueue(){
//		return QueueBuilder.durable("queue001").build();
//	}
//
//	@Bean(value = "binding001")
//	public Binding bindQueue(@Qualifier("queue001") Queue queue, @Qualifier("wuchong001change") Exchange exchange){
//		return BindingBuilder.bind(queue).to(exchange).with("").noargs();
//	}
//
//
//	@Bean(value = "wuchong002change")
//	public Exchange setExchange002(){
//		return ExchangeBuilder.directExchange("wuchong002change").durable(true).build();
//	}
//
//
//	@Bean(value = "binding002")
//	public Binding bindQueue002(@Qualifier("queue001") Queue queue, @Qualifier("wuchong002change") Exchange exchange){
//		return BindingBuilder.bind(queue).to(exchange).with("routing.key").noargs();
//	}
//
//	@Bean(value = "ttl_queue_001")
//	public Queue queueTTL(){
//		return QueueBuilder.durable("TTL_QUEUE_001").withArgument("x-message-ttl", 10000).build();
//	}
//
//
//	@Bean(value = "dead_ttl_queue_001")
//	public Queue queueDeadTTL(){
//		return QueueBuilder.durable("DEAD_TTL_QUEUE_001").withArgument("x-message-ttl", 10000).build();
//	}
//
//	@Bean
//	public Binding bindTTLQueue001(@Qualifier("ttl_queue_001") Queue queue, @Qualifier("wuchong002change") Exchange exchange){
//		return BindingBuilder.bind(queue).to(exchange).with("ttl").noargs();
//	}


	/**
	 * ?????????????????????
	 * @return
	 */

	@Bean(value = "order_queue")
	public Queue setOrderQueue(){
		return QueueBuilder.durable("ORDER_QUEUE").build();
	}

	@Bean(value = "order_exchange")
	public Exchange setOrderExchange(){
		return ExchangeBuilder.directExchange("ORDER_EXCHANGE").durable(true).build();
	}

	@Bean
	public Binding bindOrderQueue(@Qualifier("order_queue")Queue queue, @Qualifier("order_exchange") Exchange exchange){
		return BindingBuilder.bind(queue).to(exchange).with("order").noargs();
	}

	/**
	 * ????????????
	 * @return
	 */
	@Bean("delay_order_queue")
	public Queue delayOrderQueue() {
//		Map<String, Object> params = new HashMap<>();
//		// x-dead-letter-exchange ???????????????????????????????????????DLX?????????
//		params.put("x-dead-letter-exchange", "ORDER_EXCHANGE");
//		// x-dead-letter-routing-key ?????????????????????????????????????????? routing-key ?????????
//		params.put("x-dead-letter-routing-key", "order");
//		params.put("x-message-ttl", "10000");
//		return QueueBuilder.durable("DELAY_ORDER_QUEUE").withArguments(params).build();
		return QueueBuilder.durable("DELAY_ORDER_QUEUE")
				.withArgument("x-dead-letter-routing-key", "order")
				.withArgument("x-dead-letter-exchange", "ORDER_EXCHANGE")
				.withArgument("x-message-ttl", 10000)
				.build();
	}

	/**
	 * ???????????????
	 */
	@Bean("delay_order_exchange")
	public DirectExchange orderDelayExchange() {
		return ExchangeBuilder.directExchange("DELAY_ORDER_EXCHANGE").build();
	}

	@Bean
	public Binding binDelayQueue(@Qualifier("delay_order_exchange") Exchange exchange, @Qualifier("delay_order_queue") Queue queue){
		return BindingBuilder.bind(queue).to(exchange).with("deadTTL").noargs();
	}










	@Bean
//	@Scope("prototype")
	public RabbitTemplate rabbitTemplate(){
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		//??????????????????
		rabbitTemplate.setMandatory(true);
		rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
			@Override
			public void confirm(CorrelationData correlationData, boolean b, String s) {
				log.info("confirm??????????????????....");
				if (b){
					log.info("message arrive exchange=====>{}", s);
				}else {
					log.info("message not arrive exchange, need insert mysql=====>{}", s);
				}
			}
		});

		rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
			@Override
			public void returnedMessage(ReturnedMessage returnedMessage) {
				log.info("returnedMessage??????????????????....");
				log.info("???????????????????????????????????????");
				log.info("message not arrive queue======>{}", new String(returnedMessage.getMessage().getBody()));
			}
		});
		log.info("rabbitTemplate bean=======>{}", rabbitTemplate.toString());


		return rabbitTemplate;
	}



}
