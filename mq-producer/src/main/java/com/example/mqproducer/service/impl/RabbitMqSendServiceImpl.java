package com.example.mqproducer.service.impl;

import com.example.mqproducer.service.RabbitMqSendService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class RabbitMqSendServiceImpl implements RabbitMqSendService {

	@Autowired
	private RabbitTemplate rabbitTemplate;



	@Override
	public String sendMessage(String message) {
		Asserts.notBlank(message, "message");
		log.info("rabbitTemplate=======>{}", rabbitTemplate.toString());

		for (int i = 0; i < 2; i ++) {
			try {
				rabbitTemplate.convertAndSend("wuchong002change", "routing.key", i + message + i);
				Thread.sleep(1000);
			} catch (Exception q) {
				log.info("网络出现异常，消息入库");
				q.printStackTrace();
			}
		}

		return "一次下单任务的mq消息发送完毕";
	}

	@Override
	public String ttlMessageQueue(String message) {
		Asserts.notBlank(message, "message");
		log.info("rabbitTemplate ttl=======>{}", rabbitTemplate.toString());

		for (int i = 0; i < 10; i ++) {
			try {
				rabbitTemplate.convertAndSend("wuchong002change", "ttl", i + message + "_ttl" + i);
			} catch (Exception q) {
				log.info("网络出现异常，消息入库");
				q.printStackTrace();
			}
		}

		return "一次ttl消息发送完毕";
	}

	@Override
	public String deadTTLMessageQueue(String message) {
		Asserts.notBlank(message, "message");
		log.info("rabbitTemplate ttl=======>{}", rabbitTemplate.toString());

		for (int i = 0; i < 1; i ++) {
			try {
				rabbitTemplate.convertAndSend("DELAY_ORDER_EXCHANGE", "deadTTL", i + message + "_ttl" + i);
			} catch (Exception q) {
				log.info("网络出现异常，消息入库");
				q.printStackTrace();
			}
		}

		return "一次延迟l消息发送完毕";
	}





}
