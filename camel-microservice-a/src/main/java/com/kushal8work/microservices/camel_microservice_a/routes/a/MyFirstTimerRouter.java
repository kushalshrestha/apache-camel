package com.kushal8work.microservices.camel_microservice_a.routes.a;

import java.time.LocalDateTime;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyFirstTimerRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		// listen -> transform -> target; here: timer -> transform -> log
//		Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]
		from("timer:first-timer")
//		.transform().constant("Hello Kushal!")
		.transform().constant("Time now is " + LocalDateTime.now())
		.to("log:first-timer");

	}

}
