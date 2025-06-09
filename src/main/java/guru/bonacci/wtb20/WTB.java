package guru.bonacci.wtb20;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class WTB {

//	record SensorReading(Instant timestamp, double lat, double lon) {}
//	record Breach (boolean breach, String explanation) {}
//
//	private final ChatClient ai;
//	private final Map<String, List<SensorReading>> memory = new ConcurrentHashMap<>();
//  @Value("classpath:/prompts/wtb.st")
//  private Resource wtbPromptTemplate;
//
//	
//  public WTB(ChatClient.Builder builder) {
//  	this.ai = builder.build();
//  }
//
//  int requestCounter = -1;
//	
//  @GetMapping("{numberplate}/{latitude}/{longitude}")
//  public String consult(@PathVariable String numberplate, @PathVariable float latitude, @PathVariable float longitude) {
//  	this.memory.computeIfAbsent(numberplate, key -> new ArrayList<SensorReading>());
//
//   	// let's cheat..
//  	// Approx 1 degree latitude ≈ 111 km
//  	requestCounter++;
//  	double kmSouth = 100;
//  	double approxLatitude = latitude - requestCounter * (kmSouth / 111.0);
//  	Instant ts = Instant.now().plus(requestCounter, ChronoUnit.HOURS); 
//  	this.memory.get(numberplate).add(new SensorReading(ts, approxLatitude, longitude));
//  	System.out.println(memory.get(numberplate));
//  	
//  	String formattedSensorReadings = memory.get(numberplate).stream()
//  	    .map(r -> String.format("%s at %.5f-%.5f", r.timestamp(), r.lat(), r.lon()))
//  	    .collect(Collectors.joining("\n"));
//  	
//  	System.out.println(formattedSensorReadings);
//  	
//  	var promptTemplate = new PromptTemplate(wtbPromptTemplate);
//    var promptParams = new HashMap<String, Object>();
//    promptParams.put("numberplate", numberplate);
//    promptParams.put("readings", formattedSensorReadings);
//
//    var breachResponse = this.ai
//  					.prompt(promptTemplate.create(promptParams))
//  					.advisors(new SimpleLoggerAdvisor())
//  					.call()
//  					.content();
//    
//    System.out.println(breachResponse.toString());
//    return breachResponse;
//  } 
}

