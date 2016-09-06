package app.repository.dao.common.impl;

import app.repository.dao.AbstractDao;
import app.repository.dao.common.IUserProfileDao;
import app.repository.entities.common.UserProfile;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository("userProfileDao")
public class UserProfileDaoImpl extends AbstractDao<Integer, UserProfile> implements IUserProfileDao {
	@Override
	public UserProfile findById(int id) {
		return getByKey(id);
	}
	@Override
	public UserProfile findByRole(String role) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("role", role));
		return (UserProfile) crit.uniqueResult();
	}
	@Override
	@SuppressWarnings("unchecked")
	public Set<UserProfile> findAll(){
		Criteria crit = createEntityCriteria();
		Set<UserProfile> set = new HashSet<>();
		set.addAll(crit.list());
		return set;
	}
	
}
