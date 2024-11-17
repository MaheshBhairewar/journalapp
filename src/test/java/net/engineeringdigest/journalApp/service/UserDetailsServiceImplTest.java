package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

   /* @Mock
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
    }*/
}
