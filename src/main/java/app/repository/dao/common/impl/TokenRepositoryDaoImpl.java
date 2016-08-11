package app.repository.dao.common.impl;

import app.repository.dao.AbstractDao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import app.repository.entities.common.PersistentLogin;

import java.util.Date;
import java.util.List;

@Repository("tokenRepositoryDao")
@Transactional
public class TokenRepositoryDaoImpl extends AbstractDao<String, PersistentLogin>
		implements PersistentTokenRepository {

	static final Logger logger = LoggerFactory.getLogger(TokenRepositoryDaoImpl.class);

	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		logger.info("Creating Token for user : {}", token.getUsername());
		PersistentLogin persistentLogin = new PersistentLogin();
		persistentLogin.setLogin(token.getUsername());
		persistentLogin.setSeries(token.getSeries());
		persistentLogin.setToken(token.getTokenValue());
		persistentLogin.setLast_used(token.getDate());
		persist(persistentLogin);

	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		logger.info("Fetch Token if any for seriesId : {}", seriesId);
		try {
			Criteria crit = createEntityCriteria();
			crit.add(Restrictions.eq("series", seriesId));
			PersistentLogin persistentLogin = (PersistentLogin) crit.uniqueResult();

			return new PersistentRememberMeToken(persistentLogin.getLogin(), persistentLogin.getSeries(),
					persistentLogin.getToken(), persistentLogin.getLast_used());
		} catch (Exception e) {
			logger.info("Token not found...");
			return null;
		}
	}

	@Override
	public void removeUserTokens(String login) {
		logger.info("Removing Token if any for user : {}", login);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("login", login));
		List persistentLogins = crit.list();
		if (persistentLogins != null) {
			for (Object o : persistentLogins) {
				logger.info("rememberMe was selected");
				delete((PersistentLogin) o);
			}
		}

	}

	@Override
	public void updateToken(String seriesId, String tokenValue, Date lastUsed) {
		logger.info("Updating Token for seriesId : {}", seriesId);
		PersistentLogin persistentLogin = getByKey(seriesId);
		persistentLogin.setToken(tokenValue);
		persistentLogin.setLast_used(lastUsed);
		update(persistentLogin);
	}

}
