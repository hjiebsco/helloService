package com.hji.play.web;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class HelloController {

	private AtomicInteger counter = new AtomicInteger(0);

	@RequestMapping("/")
	@CrossOrigin(origins = "*")
	@HystrixCommand(fallbackMethod = "fallbackMethod")
	public String hello() {
		int count = counter.incrementAndGet();
		if (count % 3 == 0) {
			throw new RuntimeException("Hitting a number that is multiple of three");
		} else {
			return "Hello from Spring Service 8/10. Visting count : " + count;
		}
	}

	public String fallbackMethod() {
		return "Fallback is called because counter = " + counter.get();
	}

	@RequestMapping("/public")
	public Object ok(@RequestHeader HttpHeaders headers) {
		return dumpHeaders(headers);
	}

	@RequestMapping("/private")
	public Object notOK(@RequestHeader HttpHeaders headers) {
		return dumpHeaders(headers);
	}

	private Map<String, List<String>> dumpHeaders(HttpHeaders headers) {
		Map<String, List<String>> map = new TreeMap<>();
		for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}

}
