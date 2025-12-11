package pacman.plantmarket.config.web;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pacman.plantmarket.entity.web.User;
import pacman.plantmarket.repository.web.UserRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PasswordMigrationRunner implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        List<User> users = userRepository.findAll();
        for(User u : users){
            String pass = u.getPassword();
            if(!pass.startsWith("$2a$")){
                u.setPassword(passwordEncoder.encode(pass));
                userRepository.save(u);
            }
        }
    }
}
