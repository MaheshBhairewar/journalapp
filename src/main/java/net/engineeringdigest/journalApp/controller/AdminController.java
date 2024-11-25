package net.engineeringdigest.journalApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/all-users")
	public ResponseEntity<?> getAll() {
		List<User> all = userService.getAll();
		if(null!=all & !all.isEmpty()) {
			return new ResponseEntity<List<User>>(all, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/create-admin-user")
	public void createAdminUser(@RequestBody User user) {
		userService.saveNewAdminUser(user);
	}
}
