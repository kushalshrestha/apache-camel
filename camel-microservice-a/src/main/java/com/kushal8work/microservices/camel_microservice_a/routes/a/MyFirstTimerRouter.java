package com.kushal8work.microservices.camel_microservice_a.routes.a;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyFirstTimerRouter extends RouteBuilder {
	
	@Autowired
	private GetCurrentTimeBean getCurrentTimeBean; 
	
	@Autowired
	private SimpleLoggingProcessingComponent loggingComponent;

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		// listen -> processing OR transform -> target; here: timer -> transform -> log
//		Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]
		from("timer:first-timer")
		.log("1. ${body}")
		.transform().constant("Hello Kushal!")
		.log("2. ${body}")
//		.transform().constant("Time now is " + LocalDateTime.now())
//		.bean("getCurrentTimeBean") //using Spring Bean to do the transformation
		.bean(getCurrentTimeBean)
		.log("3. ${body}")
		.bean(loggingComponent)
		.log("4. ${body}")
		.process(new SimpleLoggingProcessor())
		.to("log:first-timer");

	}

}


//ideally, should be in different file/package/...
@Component
class GetCurrentTimeBean {
	public String getCurrentTime() {
		return "Time now is " + LocalDateTime.now();
	}
}

@Component
class SimpleLoggingProcessingComponent {
	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);
	public void process(String message) {
		logger.info("SimpleLoggingProcessingComponent {}", message);
	}
	
}

class SimpleLoggingProcessor implements Processor {
	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("SimpleLoggingProcessor {}", exchange.getMessage().getBody());
	}
}

