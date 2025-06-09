package guru.bonacci.wtb20;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class GraphHopperClient implements CommandLineRunner {

	private final RestClient restClient;
	@Value("${GRAPH_HOPPER_API_KEY}")
	private String apiKey;
	
	public GraphHopperClient(RestClient.Builder builder) {
		this.restClient = 
				builder
					.baseUrl("https://graphhopper.com")
					.build();
	}

	
	@Override
	public void run(String... args) throws Exception {
//		travelTimeInMin(new Point(-36.843, 174.766), new Point(-41.278,174.776));
		
	}


	public long travelTimeInMin(Point from, Point to) {
		var response =
			restClient
				.get()
				.uri(uriBuilder -> uriBuilder
					.path("/api/1/route")
					.queryParam("point", from.latitude + "," + from.longitude)
			    .queryParam("point", to.latitude + "," + to.longitude)
			    .queryParam("profile", "car")
			    .queryParam("locale", "de")
			    .queryParam("calc_points", "false")
			    .queryParam("key", apiKey)
			    .build())
			  .retrieve()
			  .body(GraphHopperResponse.class);
			
	    
		System.out.println(response);
		
		long travelTimeInMs = response.paths().get(0).time();
		return TimeUnit.MILLISECONDS.toMinutes(travelTimeInMs);
	}

	record Point(double latitude, double longitude) {}
	record GraphHopperResponse(List<Path> paths) {}
	record Path(double distance, long time) {}
}

//
//jeffreyvanhelden@MC625174 true-hallucinations % curl "https://graphhopper.com/api/1/route?point=-36.843,174.766&point=-41.278,174.776&profile=car&locale=de&calc_points=false&key=abc" | jq 
//% Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
//                               Dload  Upload   Total   Spent    Left  Speed
//100   373  100   373    0     0    346      0  0:00:01  0:00:01 --:--:--   347
//{
//"hints": {
//  "visited_nodes.sum": 443,
//  "visited_nodes.average": 443.0
//},
//"info": {
//  "copyrights": [
//    "GraphHopper",
//    "OpenStreetMap contributors"
//  ],
//  "took": 10,
//  "road_data_timestamp": "2025-05-21T22:00:00Z"
//},
//"paths": [
//  {
//    "distance": 644674.717,
//    "weight": 36872.714893,
//    "time": 27026167,
//    "transfers": 0,
//    "points_encoded": true,
//    "points_encoded_multiplier": 100000.0,
//    "snapped_waypoints": "x{z_Fo~ti`@beaZc{@"
//  }
//]
//}