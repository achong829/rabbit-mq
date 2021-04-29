package com.example.mqproducer.service;

import io.swagger.annotations.ApiModelProperty;

public interface RabbitMqSendService {

	@ApiModelProperty("处理发送消息")
	String sendMessage(String message);

	@ApiModelProperty("ttl消息队列")
	String ttlMessageQueue(String message);

	@ApiModelProperty("延迟队列")
	String deadTTLMessageQueue(String message);


}
