package net.engineeringdigest.journalApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private User mockUser;

    @BeforeEach
    public void setUp() {
        // Create and set up a mock User with roles
        mockUser = new User();
        mockUser.setUsername("testUser");
        mockUser.setPassword("password123");
        // Assuming roles are stored as a List of Strings
        mockUser.setRoles(List.of("USER", "ADMIN"));
    }

    @Test
    public void testLoadUserByUsername_Success() {
        // Arrange: Mock userRepository to return the mock user when searching by username
        when(userRepository.findByUsername("testUser")).thenReturn(mockUser);

        // Act: Load the user details using the service
        UserDetails userDetails = userDetailsService.loadUserByUsername("testUser");

        // Assert: Verify that the user details are correct
        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Arrange: Mock userRepository to return null when searching by username
        when(userRepository.findByUsername("nonExistingUser")).thenReturn(null);

        // Act & Assert: Verify that a UsernameNotFoundException is thrown
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("nonExistingUser");
        });
    }
}
