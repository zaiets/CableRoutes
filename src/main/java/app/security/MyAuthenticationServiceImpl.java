package app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;
import app.repository.dao.common.IUserDao;
import app.repository.entities.common.PersistentLogin;

@Service
public class MyAuthenticationServiceImpl implements MyAuthenticationService {

    @Autowired
    private PersistentTokenRepository tokenRepository;

    @Autowired
    private IUserDao userDao;

    @Override
    public PersistentLogin authenticate(String login, String password) {
        return null;
    }

    @Override
    public boolean checkToken(String token) {
        return true;
    }

    @Override
    public void logout(String token) {

    }

    @Override
    public UserDetails currentUser() {
        return null;
    }
}
