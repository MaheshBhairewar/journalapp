package net.engineeringdigest.journalApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.engineeringdigest.journalApp.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	UserRepository userRepo;
	
	
	@ParameterizedTest
	@ValueSource(strings = {
			"RAM","SAM"
	})
	public void findByUserNameTest(String username) {
		assertNotNull(userRepo.findByUsername(username));
	}
	
	
	@ParameterizedTest
	@CsvSource({
		"2,2,4"
	})
	public void test(int a, int b,int expected) {
		assertEquals(expected, a+b);
	}
	
}
