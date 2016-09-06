package app.repository.dao.common.impl;

import app.repository.dao.AbstractDao;
import app.repository.dao.common.IUserDao;
import app.repository.entities.common.User;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements IUserDao {

	static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	
	public User findById(int id) {
		logger.info("ID : {}", id);
		User user = getByKey(id);
		if(user!=null){
			Hibernate.initialize(user.getUserProfile());
		}
		return user;
	}

	public User findByLogin(String login) {
		logger.info("Login : {}", login);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("login", login));
		User user = (User)crit.uniqueResult();
		if(user!=null){
			Hibernate.initialize(user.getUserProfile());
		}
		return user;
	}

	@Override
	public User findByEmail(String email) {
		logger.info("Email : {}", email);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("email", email));
		User user = (User)crit.uniqueResult();
		if(user!=null){
			Hibernate.initialize(user.getUserProfile());
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("firstName"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
		List<User> users = (List<User>) criteria.list();
		
		// No need to fetch userProfiles since we are not showing them on list page. Let them lazy load. 
		// Uncomment below lines for eagerly fetching of userProfiles if you want.
		for(User user : users){
			Hibernate.initialize(user.getUserProfile());
		}
		//uncommented temporary
		return users;
	}

	public User save(User user) {
		persist(user);
		return findByLogin(user.getLogin());
	}

	public void deleteByLogin(String login) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("login", login));
		User user = (User)crit.uniqueResult();
		delete(user);
	}

}
