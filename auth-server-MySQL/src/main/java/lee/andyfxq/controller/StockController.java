package lee.andyfxq.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lee.andyfxq.model.Portfolio;
import lee.andyfxq.model.Stock;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/STOCK")
public class StockController {
	private static final Logger logger = LoggerFactory.getLogger(StockController.class);
	
	final String targetUrl = "http://localhost:4985/STOCK";
	
	@GetMapping("/list")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<Stock> requestStocks(@RequestHeader("Authorization") String jwt) {
		RestTemplate restTemp = new RestTemplate();
		String ext = "/list";
		ResponseEntity<Stock[]> response = restTemp.getForEntity(targetUrl + ext, Stock[].class);
		return Arrays.asList(response.getBody());
	}
	
	@GetMapping("/id/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public Optional<Stock> requestById(@RequestHeader("Authorization") String jwt, @PathVariable String id) {
		RestTemplate restTemp = new RestTemplate();
		String ext = "/id/" + id;
		ResponseEntity<Optional> response = restTemp.getForEntity(targetUrl + ext, Optional.class);
		return response.getBody();
	}

	@GetMapping("/cid/{cid}")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<Stock> requestByCid(@RequestHeader("Authorization") String jwt, @PathVariable String cid) {
		RestTemplate restTemp = new RestTemplate();
		String ext = "/cid/" + cid;
		ResponseEntity<Stock[]> response = restTemp.getForEntity(targetUrl + ext, Stock[].class);
		List<Stock> res = Arrays.asList(response.getBody());
//		for (Stock s:res) logger.info(s.toString());
		return res;
	}
	
	@PostMapping("/create")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Stock> createStock(@RequestHeader("Authorization") String jwt, @RequestBody Stock stock) {
		RestTemplate restTemp = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Stock> request = new HttpEntity<>(stock, headers);
		
		String ext = "/create";
		ResponseEntity<Stock> response = restTemp.exchange(targetUrl + ext, HttpMethod.POST, request, Stock.class, stock);
		
		return response;
	}
	
	@PutMapping("edit/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Stock> editStock(@RequestHeader("Authorization") String jwt, @PathVariable Long id, @RequestBody Stock stock) {
		RestTemplate restTemp = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Stock> request = new HttpEntity<>(stock, headers);
		
		String ext = "/edit/" + id;
		ResponseEntity<Stock> response = restTemp.exchange(targetUrl + ext, HttpMethod.PUT, request, Stock.class, stock);
		
		return response;
	}
	
	@DeleteMapping("edit/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> deleteStock(@RequestHeader("Authorization") String jwt, @PathVariable String id) {
		logger.info("Deleting stock with id : {}", id);
		RestTemplate restTemp = new RestTemplate();
		String ext = "/edit/" + id;
		HttpEntity<String> request = new HttpEntity<String>(new HttpHeaders());
		ResponseEntity<HttpStatus> response = restTemp.exchange(targetUrl + ext, HttpMethod.DELETE, request, HttpStatus.class);
		
		return response;
	}
	
	
}
