package com.kitsutsuki.sample.printer;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

@Component
public class HelloWorld {

	@Activate
	public void activate() {
		System.out.println("Activate called");
		System.out.println("Hello World");
	}
}
