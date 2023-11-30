package trabalho.de.web.web.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import trabalho.de.web.web.user.UserService;

@RestController
public class LoginController {
     

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    
    public LoginController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

  
    @PostMapping("/api/login")
	public ResponseEntity<Void> login( @RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse) {
        System.err.println(loginRequest.getPassword() + loginRequest.getUsername());
		Authentication authenticationRequest =
			UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUsername(), loginRequest.getPassword());
		Authentication authenticationResponse =
			this.authenticationManager.authenticate(authenticationRequest);
            System.out.println(authenticationResponse.isAuthenticated());
        SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
		if (authenticationResponse.isAuthenticated()) {

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(403).build();
        }
        
	}
    

    @GetMapping("/logout")
public ResponseEntity<Void> logout() {
    try {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    } catch (Exception e) {
        return ResponseEntity.badRequest().build();
    }
    
}

    
}
