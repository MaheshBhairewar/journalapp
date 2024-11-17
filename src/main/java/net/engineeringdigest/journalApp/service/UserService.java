package net.engineeringdigest.journalApp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;

@Service
@Slf4j
public class UserService {

	private static final PasswordEncoder passowordEncoder = new BCryptPasswordEncoder();

	@Autowired
	private UserRepository userRepository;

	public void saveNewUser(User user) {
		try {

			if (findByUsername(user.getUsername()) != null) {
				throw new RuntimeException("User with username '" + user.getUsername() + "' already exists!");
			}
			user.setPassword(passowordEncoder.encode(user.getPassword()));
			user.setRoles(Arrays.asList("USER"));
			userRepository.save(user);
			log.info("New user '{}' has been successfully created.", user.getUsername());

		} catch (RuntimeException e) {
			log.error("An unexpected error occurred while creating user '{}': {}", user.getUsername(), e);
			throw new RuntimeException("Failed to create user. Please try again later.");
		}

	}

	public void saveNewAdminUser(User user) {
		user.setPassword(passowordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList("USER", "ADMIN"));
		userRepository.save(user);
	}

	public void saveUser(User user) {
		userRepository.save(user);
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public Optional<User> findById(ObjectId id) {
		return userRepository.findById(id);
	}

	public void deleteById(ObjectId id) {
		userRepository.deleteById(id);
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
