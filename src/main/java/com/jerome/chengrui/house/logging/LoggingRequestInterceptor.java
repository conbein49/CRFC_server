package com.jerome.chengrui.house.logging;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

	final static Logger log = LoggerFactory.getLogger(LoggingRequestInterceptor.class);

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		traceRequest(request, body);
		ClientHttpResponse response = execution.execute(request, body);
		traceResponse(response);
		return response;
	}

	private void traceRequest(HttpRequest request, byte[] body) throws IOException {
		if (log.isTraceEnabled()) {
			log.trace("===========================request begin=============================================");
			log.trace("URI : {}", request.getURI().toString());
			log.trace("Method : {}", request.getMethod().toString());
			log.trace("Headers : {}", request.getHeaders().toString());
			log.trace("Request body: {}", new String(body, "UTF-8"));
			log.trace("==========================request end================================================");
		}
	}

	private void traceResponse(ClientHttpResponse response) throws IOException {
		if (log.isTraceEnabled()) {
			log.trace("============================response begin==========================================");
			log.trace("Status code : {}", response.getStatusCode().toString());
			log.trace("Status text : {}", response.getStatusText());
			log.trace("Headers : {}", response.getHeaders().toString());
			log.trace("=======================response end=================================================");
		}
	}

}
