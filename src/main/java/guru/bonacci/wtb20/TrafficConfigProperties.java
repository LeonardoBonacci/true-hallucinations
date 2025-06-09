package guru.bonacci.wtb20;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "traffic")
public record TrafficConfigProperties(String apiKey, String apiUrl) {

}