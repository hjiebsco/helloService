package com.hji.play.web;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	private AtomicInteger counter = new AtomicInteger(1);
	
	@RequestMapping("/")
	public String hello() {
		return "Hello from Spring Boot after chaning timezone: " + counter.getAndIncrement();
	}

}
