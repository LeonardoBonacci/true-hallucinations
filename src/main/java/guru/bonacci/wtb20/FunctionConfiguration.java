package guru.bonacci.wtb20;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

@Configuration
public class FunctionConfiguration {

	private final TrafficConfigProperties props;

	public FunctionConfiguration(TrafficConfigProperties props) {
		this.props = props;
	}

	@Bean
	@Description("Get the travel time in minutes between two locations.")
	Function<TrafficService.Request, Long> travelTimeFunction() {
		return new TrafficService(props);
	}

}