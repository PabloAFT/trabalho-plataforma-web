package trabalho.de.web.web.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }
    public User createUser(User user) {
        return userRepository.save(user);
    }
    
}
