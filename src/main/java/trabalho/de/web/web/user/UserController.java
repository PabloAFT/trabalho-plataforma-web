package trabalho.de.web.web.user;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            return ResponseEntity.ok().body(userService.getAllUsers());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "userId") Long userId) {
        try {
            return ResponseEntity.ok().body(userService.getUserById(userId));
        } catch (NoSuchElementException e) {
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    

    @PostMapping()
    public ResponseEntity<String> createUser (@RequestBody User user) {
        try {
            return ResponseEntity.created(new URI("users/" + user.getId())).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
