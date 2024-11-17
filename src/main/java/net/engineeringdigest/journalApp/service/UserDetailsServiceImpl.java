package net.engineeringdigest.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (null != user) {
			UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
														 .username(user.getUsername())
														 .password(user.getPassword())
														 .roles(user.getRoles().toArray(new String[0]))
														 .build();
			return userDetails;
		}
			throw new UsernameNotFoundException("User Not Found with username: " + username);
	}

}
