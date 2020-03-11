/*
 *  Copyright 2018 original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import java.util.HashMap;
import java.util.function.Function;

/**
 * @author Dmitry Solomakha
 */
@SpringBootConfiguration
@EnableAutoConfiguration
public class Uppercase {
	public static void main(String[] args) {
		SpringApplication.run(Uppercase.class, args);
	}

	@Bean
	public Function<Message<Foo>, Message<Bar>> function() {
		return (foo -> new GenericMessage<>(
				new Bar(foo.getPayload().getValue().toUpperCase()), new HashMap<>()));
	}
}

class Foo {

	private String value;

	Foo() {
	}

	Foo(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

class Bar {

	private String value;

	Bar() {
	}

	Bar(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}

