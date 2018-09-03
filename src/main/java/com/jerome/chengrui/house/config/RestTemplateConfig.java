package com.jerome.chengrui.house.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.jerome.chengrui.house.logging.LoggingRequestInterceptor;

@Configuration
public class RestTemplateConfig {

	private static Logger logger = LoggerFactory.getLogger(RestTemplateConfig.class.getName());

	private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 500;
	private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 100;

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
		restTemplate.setInterceptors(getClientHttpRequestInterceptors());
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_PLAIN));
		restTemplate.getMessageConverters().add(converter);
		return restTemplate;
	}

	private List<ClientHttpRequestInterceptor> getClientHttpRequestInterceptors() {
	    List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
	    interceptors.add(new LoggingRequestInterceptor());
//	    interceptors.add(new BasicAuthorizationInterceptor(this.username, this.password));
	    return interceptors;
	}

	private ClientHttpRequestFactory getClientHttpRequestFactory() {

		RequestConfig config = getRequestConfig();

		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		clientBuilder.setDefaultRequestConfig(config);

		clientBuilder.setProxy(config.getProxy());

		clientBuilder.setConnectionManager(getConnectionManager());
		// Optionally: setup background thread to close unused connections:
		clientBuilder.evictIdleConnections(15, TimeUnit.MINUTES);
		clientBuilder.evictExpiredConnections();

		CloseableHttpClient client = clientBuilder.build();
		// 1 client - n reused connections, per request a client contexts

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(client);
		// Read timeout for outgoing http connections (from the broker to another
		// backend) in milliseconds
		factory.setReadTimeout(5000);
		factory.setConnectTimeout(5000);

		return factory;
	}

	/**
	 * Configures the proxy and the timeouts.
	 *
	 * Timeout Properties Explained: Connection Timeout the time to establish the
	 * connection with the target host
	 *
	 * Socket Timeout the time waiting for data â after the connection was
	 * established; maximum time of inactivity between two data packets
	 *
	 * Connection Manager Timeout the time to wait for a connection from the
	 * connection manager/pool
	 */
	private RequestConfig getRequestConfig() {
		RequestConfig.Builder requestConfigBuilder = RequestConfig.custom().setConnectTimeout(3000)
				.setSocketTimeout(4000).setConnectionRequestTimeout(2000);

//		if (proxyHost != null && !proxyHost.trim().isEmpty() && proxyPort != 0) {
//			logger.info("set proxy: " + proxyHost + ":" + String.valueOf(proxyPort));
//			requestConfigBuilder.setProxy(new HttpHost(proxyHost, proxyPort, HttpHost.DEFAULT_SCHEME_NAME));
//		}
		return requestConfigBuilder.build();
	}

	/**
	 * Optimization: reuse connection to the same target host for subsequent
	 * requests.
	 */
	private PoolingHttpClientConnectionManager getConnectionManager() {
        Registry<ConnectionSocketFactory> registry =
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", getSSLConnectionSocketFactory()).build();
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);

		connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
		connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTIONS_PER_ROUTE); // per target host

		return connectionManager;
	}

    private SSLConnectionSocketFactory getSSLConnectionSocketFactory() {
        SSLContextBuilder sslContextBuilder = SSLContextBuilder.create();

        try {
//            if(checkCertificate) {
//                URL trustStore = ResourceUtils.getURL(TRUST_STORE);
//                sslContextBuilder.loadTrustMaterial(trustStore, TRUST_STORE_PASSWORD);
//            } else {
//                // Disable ssl credential check
//                TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
//                    @Override
//                    public boolean isTrusted(X509Certificate[] chain, String authType)
//                            throws CertificateException {
//                        return true;
//                    }
//                };
//                sslContextBuilder.loadTrustMaterial(null, acceptingTrustStrategy);
//            }
            return new SSLConnectionSocketFactory(sslContextBuilder.build());
        } catch (Exception e) {
            logger.error("Failed to build SSL Context. Exception: " + e.getMessage());
            throw new RuntimeException("Build SSL Context failed", e);
        }

    }
}
