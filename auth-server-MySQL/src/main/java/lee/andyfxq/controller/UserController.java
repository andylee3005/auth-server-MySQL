package lee.andyfxq.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lee.andyfxq.model.User;
import lee.andyfxq.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/USER")
public class UserController {

	@Autowired
	@Qualifier("userService")
	UserService userService;
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<User>> getAllUsers() {
		try {
			List<User> users = new ArrayList<>();
			
			userService.getAll().forEach(users::add);
			
			if (users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
		Optional<User> userData = userService.getById(id);
		
		if (userData.isPresent()) {
			return new ResponseEntity<>(userData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/edit/{id}")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<User> editUser(@PathVariable("id") Long id, @RequestBody User user) {
		Optional<User> userData = userService.getById(id);
		
		if (userData.isPresent()) {
			User _user = userData.get();
			_user.setFirst(user.getFirst());
			_user.setLast(user.getLast());
			_user.setAddress(user.getAddress());
			_user.setPhone(user.getPhone());
			_user.setRoles(user.getRoles());
			
			return new ResponseEntity<>(userService._save(_user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/edit/profile/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<User> editSelf(@PathVariable("id") Long id, @RequestBody User user) {
		Optional<User> userData = userService.getById(id);
		
		if (userData.isPresent()) {
			User _user = userData.get();
			if (!userService.checkEmail(user.getEmail())) {
				_user.setEmail(user.getEmail());				
			}  //still need to add error message if email is taken.
			_user.setFirst(user.getFirst());
			_user.setLast(user.getLast());
			_user.setAddress(user.getAddress());
			_user.setPhone(user.getPhone());
			
			
			return new ResponseEntity<>(userService._save(_user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/edit/{id}")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
		try {
			userService.removeById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
