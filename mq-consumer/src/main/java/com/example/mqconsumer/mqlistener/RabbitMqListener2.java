package com.example.mqconsumer.mqlistener;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@Component
public class RabbitMqListener2 {

	@Transactional
	@RabbitListener(queues = "queue001")
	public void onMessage(Message message, Object entity, Channel channel) throws Exception {
		Long messageId = message.getMessageProperties().getDeliveryTag();
		try {
			log.info("RabbitMqListener2 deal queue001 ============> {}", new String(message.getBody()));
			channel.basicAck(messageId, false);
		} catch (Exception e) {
			e.printStackTrace();
			channel.basicNack(messageId, false, true);
		}
	}


	@RabbitListener(queues = "TTL_QUEUE_001")
	public void listenerTTLQueue001(Message message, Channel channel) throws IOException {
		Long tagId = message.getMessageProperties().getDeliveryTag();
		try {
			log.info("{}=====>listenerTTLQueue001======>{}", tagId, new String(message.getBody()));
		}catch (Exception e){
			e.printStackTrace();
			channel.basicNack(tagId, false, true);
		}
	}


}
