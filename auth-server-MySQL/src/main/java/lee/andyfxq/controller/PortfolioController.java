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
@RequestMapping("/PORT")
public class PortfolioController {
	private static final Logger logger = LoggerFactory.getLogger(PortfolioController.class);
	final String targetUrl = "http://localhost:4986/PORT";
	
	@PostMapping("/create")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Portfolio> createPortfolio(@RequestHeader("Authorization") String jwt, @RequestBody Portfolio portf) {
		RestTemplate restTemp = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Portfolio> request = new HttpEntity<>(portf, headers);
		
		String ext = "/create";
		ResponseEntity<Portfolio> response = restTemp.exchange(targetUrl + ext, HttpMethod.POST, request, Portfolio.class, portf);
		
		return response;
	}
	
	@GetMapping("/list")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<Portfolio> requestPortfolios(@RequestHeader("Authorization") String jwt) {
		RestTemplate restTemp = new RestTemplate();
		String ext = "/list";
		ResponseEntity<Portfolio[]> response = restTemp.getForEntity(targetUrl + ext, Portfolio[].class);
		return Arrays.asList(response.getBody());
	}
	
	@GetMapping("/id/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public Optional<Portfolio> requestById(@RequestHeader("Authorization") String jwt, @PathVariable String id) {
		RestTemplate restTemp = new RestTemplate();
		String ext = "/id/" + id;
		ResponseEntity<Optional> response = restTemp.getForEntity(targetUrl + ext, Optional.class);
		return response.getBody();
	}
	
	@GetMapping("/cid/{cid}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<Portfolio> requestByCid(@RequestHeader("Authorization") String jwt, @PathVariable String cid) {
		RestTemplate restTemp = new RestTemplate();
		String ext = "/cid/" + cid;
		ResponseEntity<Portfolio[]> response = restTemp.getForEntity(targetUrl + ext, Portfolio[].class);
		return Arrays.asList(response.getBody());
	}
	
	@GetMapping("/pid/{pid}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<Stock> requestByPid(@RequestHeader("Authorization") String jwt, @PathVariable String pid) {
		RestTemplate restTemp = new RestTemplate();
		String ext = "/pid/" + pid;
		ResponseEntity<Stock[]> response = restTemp.getForEntity(targetUrl + ext, Stock[].class);
		List<Stock> res = Arrays.asList(response.getBody());
		for (Stock s:res) logger.info(s.toString());
		return res;
	}
	
	@PutMapping("/edit/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Portfolio> editPortfolio(@RequestHeader("Authorization") String jwt, @PathVariable String id, @RequestBody Portfolio portf) {
		RestTemplate restTemp = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Portfolio> request = new HttpEntity<>(portf, headers);
		
		String ext = "/edit/" + id;
		ResponseEntity<Portfolio> response = restTemp.exchange(targetUrl + ext, HttpMethod.PUT, request, Portfolio.class, portf);
		
		return response;
	}
	
	@DeleteMapping("/edit/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> deletePortfolio(@RequestHeader("Authorization") String jwt, @PathVariable String id) {
		RestTemplate restTemp = new RestTemplate();
		String ext = "/edit/" + id;
		HttpEntity<String> request = new HttpEntity<String>(new HttpHeaders());
		ResponseEntity<HttpStatus> response = restTemp.exchange(targetUrl + ext, HttpMethod.DELETE, request, HttpStatus.class);
		
		return response;
	}
	
	//get stocks in portfolio
	@GetMapping("/list/{pid}")
//	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<Stock> requestStocks(@RequestHeader("Authorization") String jwt, @PathVariable Long pid) {
		RestTemplate restTemp = new RestTemplate();
		String ext = "/list/" + pid;
		ResponseEntity<Stock[]> response = restTemp.getForEntity(targetUrl + ext, Stock[].class);
		return Arrays.asList(response.getBody());
	}
	
	@PutMapping("/rel/add/{sid}")
	public ResponseEntity<HttpStatus> addRelationship(@RequestHeader("Authorization") String jwt, @PathVariable Long sid, @RequestBody Portfolio portf) {
		RestTemplate restTemp = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		String ext = "/rel/add/" + sid;
		HttpEntity<Portfolio> request = new HttpEntity<>(portf, headers);
		ResponseEntity<HttpStatus> response = restTemp.exchange(targetUrl + ext, HttpMethod.PUT, request, HttpStatus.class, portf);
		
		return response;
	}
	
	@PutMapping("/rel/del/{sid}")
	public ResponseEntity<HttpStatus> removeRelationship(@RequestHeader("Authorization") String jwt, @PathVariable Long sid, @RequestBody Portfolio portf) {
		RestTemplate restTemp = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		String ext = "/rel/del/" + sid;
		HttpEntity<Portfolio> request = new HttpEntity<>(portf, headers);
		ResponseEntity<HttpStatus> response = restTemp.exchange(targetUrl + ext, HttpMethod.PUT, request, HttpStatus.class, portf);
		
		return response;
	}
}
