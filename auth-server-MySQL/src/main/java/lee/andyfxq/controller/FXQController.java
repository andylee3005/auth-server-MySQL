package lee.andyfxq.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lee.andyfxq.model.FXQuote;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/FXQ")
public class FXQController {
//	private final static Logger logger = LoggerFactory.getLogger(FXQController.class );
	
	final String targetUrl = "http://localhost:4983/FXQ";
	
	@GetMapping("/list")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<FXQuote> requestFXQuote() {
		RestTemplate restTemp = new RestTemplate();
		String ext = "/list";
		ResponseEntity<FXQuote[]> response = restTemp.getForEntity(targetUrl + ext, FXQuote[].class);
		return Arrays.asList(response.getBody());
	}
	
	@GetMapping("/symbol/{symbol}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<FXQuote> requestFXQuote(@PathVariable String symbol) {
		RestTemplate restTemp = new RestTemplate();
		String ext = "/symbol/" + symbol;
		ResponseEntity<FXQuote[]> response = restTemp.getForEntity(targetUrl + ext, FXQuote[].class);
		return Arrays.asList(response.getBody());
	}
	
	@GetMapping("/symbol/{symbol}/tenor/{tenor}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<FXQuote> requestFXQuote(@PathVariable String symbol, @PathVariable String tenor) {
		RestTemplate restTemp = new RestTemplate();
		String ext = "/symbol/" + symbol + "/tenor/" + tenor;
		ResponseEntity<FXQuote[]> response = restTemp.getForEntity(targetUrl + ext, FXQuote[].class);
		return Arrays.asList(response.getBody());
	}

	//functions to create data
	@GetMapping("/slist")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<FXQuote> requestFXQuoteSorted() {
		RestTemplate restTemp = new RestTemplate();
		String ext = "/slist";
		ResponseEntity<FXQuote[]> response = restTemp.getForEntity(targetUrl + ext, FXQuote[].class);
		return Arrays.asList(response.getBody());
	}
	
	@GetMapping("/ssymbol/{symbol}")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<FXQuote> requestFXQuoteSorted(@PathVariable String symbol) {
		RestTemplate restTemp = new RestTemplate();
		String ext = "/ssymbol/" + symbol;
		ResponseEntity<FXQuote[]> response = restTemp.getForEntity(targetUrl + ext, FXQuote[].class);
		return Arrays.asList(response.getBody());
	}
	
	@GetMapping("/ssymbol/{symbol}/tenor/{tenor}")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<FXQuote> requestFXQuoteSorted(@PathVariable String symbol, @PathVariable String tenor) {
		RestTemplate restTemp = new RestTemplate();
		String ext = "/ssymbol/" + symbol + "/tenor/" + tenor;
		ResponseEntity<FXQuote[]> response = restTemp.getForEntity(targetUrl + ext, FXQuote[].class);
		return Arrays.asList(response.getBody());
	}
	
	@GetMapping("/recent/{symbol}/{tenor}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public FXQuote requestMostRecentFXQuote(@PathVariable String symbol, @PathVariable String tenor) {
		RestTemplate restTemp = new RestTemplate();
		String ext = "/recent/" + symbol + "/" + tenor;
		ResponseEntity<FXQuote> response = restTemp.getForEntity(targetUrl + ext, FXQuote.class);
		return response.getBody();
	}
}
