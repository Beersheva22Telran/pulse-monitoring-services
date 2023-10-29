package telran.monitoring.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.mvc.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import telran.spring.exceptions.NotFoundException;
@Service
@Slf4j
public class ProxyServiceImpl implements ProxyService {
@Value("#{${app.routed.urls}}")
	Map<String, String> urlsMap;
	@Override
	public ResponseEntity<byte[]> proxyRouting(ProxyExchange<byte[]> proxy, HttpServletRequest request) {
		String routedURI = getRoutedURI(request);
		log.trace("routed URI {}", routedURI);
		return proxy.uri(routedURI).get();
	}

	private String getRoutedURI(HttpServletRequest request) {
		String receivedURI = request.getRequestURI();
		String firstURN = receivedURI.split("/+")[1];
		log.trace("firstURN is {}", firstURN);
		if(!urlsMap.containsKey(firstURN)) {
			throw new NotFoundException(firstURN + " Not found" );
		}
		String routedURL = urlsMap.get(firstURN);
		log.trace("routedURL {}", routedURL);
		String queryString = request.getQueryString();
		if(queryString == null) {
			queryString = "";
		}
		
		return String.format("%s%s?%s", routedURL, receivedURI, queryString);
	}
	@PostConstruct
	void mapLogging() {
		log.debug("mapping of routed URLs is {}", urlsMap);
	}

}
