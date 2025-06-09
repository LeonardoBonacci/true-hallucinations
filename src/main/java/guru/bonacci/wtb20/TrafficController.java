package guru.bonacci.wtb20;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrafficController {

	private final ChatClient chatClient;
	
  public TrafficController(ChatClient.Builder builder) {
		this.chatClient = builder
				.defaultSystem("You are a helpful AI Assistant answering questions about travel times.")
			  .defaultToolNames("travelTimeFunction")
				.build();

  }

  @GetMapping("foo")
	public String home() {
  	var message = 
  			"""
  				What's the travel time between Auckland and Wellington?
  				Remember to accurately determine the latitude and long of each city, and to show these values in the response.
  				If the travelTimeFunction that you are using returns 0, try again a few more times with slightly different latitude and longitude values.
				""";
		var response = chatClient
				        .prompt()
				        .user(message)
				        .call()
				        .content();
		return response;
	}
}

