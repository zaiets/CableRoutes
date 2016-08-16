package app.repository.dao.common;


import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

public interface ITokenRepositoryDao extends PersistentTokenRepository {
    PersistentRememberMeToken getByToken(String token);
}
