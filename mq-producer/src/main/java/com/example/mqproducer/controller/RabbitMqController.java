package com.example.mqproducer.controller;

import com.example.mqproducer.service.RabbitMqSendService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMqController {

	@Autowired
	private RabbitMqSendService rabbitMqSendService;

	@ApiOperation("发送MQ消息")
	@GetMapping(value = "/send-mq-message")
	public String getLoanInfo(String message) {
		return rabbitMqSendService.sendMessage(message);
	}

	@ApiOperation("发送MQ消息")
	@GetMapping(value = "/send-ttl-message")
	public String ttlMessage(String message) {
		return rabbitMqSendService.ttlMessageQueue(message);
	}

	@ApiOperation("发送MQ消息")
	@GetMapping(value = "/send-dead-ttl-message")
	public String deadTTLMessageQueue(String message) {
		return rabbitMqSendService.deadTTLMessageQueue(message);
	}




}
