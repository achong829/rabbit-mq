package com.example.mqconsumer.mqlistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.core.Message;

@Slf4j
@Component
public class RabbitMqListener1 {


//	@RabbitListener(queues = "queue001")
//	public void dealQueue01(Message message) {
//		log.info("RabbitMqListener1 deal queue001 ============>{}", new String(message.getBody()));
//	}


}
