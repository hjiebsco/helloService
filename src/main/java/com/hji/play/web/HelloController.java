package com.hji.play.web;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class HelloController {

	private AtomicInteger counter = new AtomicInteger(0);

	@RequestMapping("/")
	@HystrixCommand(fallbackMethod = "fallbackMethod")
	public String hello() {
		int count = counter.incrementAndGet();
		if (count % 3 == 0) {
			throw new RuntimeException("Hitting a number that is multiple of three");
		} else {
			return "Hello from Spring Service 8/5 for DEMO. Visting count : " + count;
		}
	}

	public String fallbackMethod() {
		return "Fallback is called because counter = " + counter.get();
	}

}
