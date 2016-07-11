package app.security;

import org.springframework.security.core.userdetails.UserDetails;
import app.repository.entities.common.PersistentLogin;

public interface MyAuthenticationService {
    PersistentLogin authenticate(String login, String password);

    boolean checkToken(String token);

    void logout(String token);

    UserDetails currentUser();
}
