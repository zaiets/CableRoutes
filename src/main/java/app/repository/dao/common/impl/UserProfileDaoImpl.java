package app.repository.dao.common.impl;

import app.repository.dao.AbstractDao;
import app.repository.dao.common.IUserProfileDao;
import app.repository.entities.common.UserProfile;
import app.repository.enumerations.Role;
import org.hibernate.Criteria;
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
		for (UserProfile target : findAll()) {
			if (Role.valueOf(role) == target.getRole()) return target;
		}
		return null;
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
