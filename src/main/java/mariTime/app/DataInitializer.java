package mariTime.app;

import mariTime.app.entity.Role;
import mariTime.app.entity.User;
import mariTime.app.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("user").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("USER");

            User adminUser = new User();
            adminUser.setUsername("user");
            adminUser.setPassword(passwordEncoder.encode("123"));
            adminUser.getRoles().add(adminRole);

            userRepository.save(adminUser);
        }
    }
}
