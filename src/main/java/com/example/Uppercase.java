package com.example;/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.adapter.gcloud.GcloudSpringBootHttpRequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

/**
 * @author Dmitry Solomakha
 */
@SpringBootApplication
public class Uppercase extends GcloudSpringBootHttpRequestHandler implements HttpFunction {

	public static void main(String[] args) {
		SpringApplication.run(Uppercase.class, args);
	}
	// @Bean
	// public Function<String, String> uppercase() {
	// return String::toUpperCase;
	// }
	@Override
	public void service(HttpRequest request, HttpResponse response) throws Exception {
	  System.out.println("ClassLoader is: " + Uppercase.class.getClassLoader().getClass().getName());
	  if (Uppercase.class.getClassLoader() instanceof URLClassLoader) {
	  	URLClassLoader loader = (URLClassLoader) Uppercase.class.getClassLoader();
	  	for (URL url : loader.getURLs()) {
	  	  System.out.println(url.toString());

			}

		}
		handleRequest(request, response);
	}

	@Bean
	public Function<Message<Foo>, Message<Bar>> function() {
		return (foo -> new GenericMessage<>(
				new Bar(foo.getPayload().getValue().toUpperCase()), new HashMap<>()));
	}


	class Foo {

		private String value;

		Foo() {
		}

		Foo(String value) {
			this.value = value;
		}

		public String lowercase() {
			return this.value.toLowerCase();
		}

		public String uppercase() {
			return this.value.toUpperCase();
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

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			Bar bar = (Bar) o;
			return Objects.equals(getValue(), bar.getValue());
		}

		@Override
		public int hashCode() {
			return Objects.hash(getValue());
		}
	}

}
