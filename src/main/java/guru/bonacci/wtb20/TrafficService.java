package guru.bonacci.wtb20;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClient;

import guru.bonacci.wtb20.GraphHopperClient.GraphHopperResponse;

/*
   Traffic API
   https://www.graphhopper.com/
 */
public class TrafficService implements Function<TrafficService.Request, Long> {

    private static final Logger log = LoggerFactory.getLogger(TrafficService.class);
    private final RestClient restClient;
    private final TrafficConfigProperties trafficProps;

    public TrafficService(TrafficConfigProperties props) {
        this.trafficProps = props;
        log.debug("Traffic API URL: {}", trafficProps.apiUrl());
        log.debug("Traffic API Key: {}", trafficProps.apiKey());
      	this.restClient = 
    				RestClient.builder()
    					.baseUrl(trafficProps.apiUrl())
    					.build();
    }

    @Override
    public Long apply(TrafficService.Request trafficRequest) {
      log.info("Traffic Request: {}", trafficRequest);
      
      try {
	      var fromLat = Math.round(trafficRequest.from().getLatitude() * 1000.0) / 1000.0;
	      var fromLng = Math.round(trafficRequest.from().getLongitude() * 1000.0) / 1000.0;
	      var toLat = Math.round(trafficRequest.to().getLatitude() * 1000.0) / 1000.0;
	      var toLng = Math.round(trafficRequest.to().getLongitude() * 1000.0) / 1000.0;
	      
	      var response =
	    			restClient
	    				.get()
	    				.uri(uriBuilder -> uriBuilder
	    					.path("/api/1/route")
	    					.queryParam("point", fromLat + "," + fromLng)
	    			    .queryParam("point", toLat + "," + toLng)
	    			    .queryParam("profile", "car")
	    			    .queryParam("locale", "de")
	    			    .queryParam("calc_points", "false")
	    			    .queryParam("key", trafficProps.apiKey())
	    			    .build())
	    			  .retrieve()
	    			  .body(GraphHopperResponse.class);
	      			
	      	    
	        log.info("Traffic API Response: {}", response);
	      		
	    		long travelTimeInMs = response.paths().get(0).time();
	    		return TimeUnit.MILLISECONDS.toMinutes(travelTimeInMs);
      } catch(RuntimeException e) {
      	log.warn(e.getMessage());
      	return 0l;
      }
    }

    public static class Point {
      private double latitude;
      private double longitude;

      public Point() {}

      public Point(String input) {
      		System.out.println(input);
          String[] parts = input.split(",");
          if (parts.length != 2) {
              throw new IllegalArgumentException("Invalid lat,lng format");
          }
          this.latitude = Double.parseDouble(parts[0]);
          this.longitude = Double.parseDouble(parts[1]);
      }

      public Point(double latitude, double longitude) {
          this.latitude = latitude;
          this.longitude = longitude;
      }

      public void setLatitude(double lat) { this.latitude = lat; }
      public void setLongitude(double lng) { this.longitude= lng; }

      public double getLatitude() { return latitude; }
      public double getLongitude() { return longitude; }
    }
    public record Request(Point from, Point to) {}
  	public record Response(List<Path> paths) {}
  	public record Path(double distance, long time) {}
}