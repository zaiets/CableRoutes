package app.service.common.impl;

import app.service.common.IUserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import app.repository.dao.common.IUserProfileDao;
import app.repository.entities.common.UserProfile;

import java.util.Set;


@Service
@Transactional
public class UserProfileServiceImpl implements IUserProfileService {
	
	@Autowired
	IUserProfileDao dao;
	
	public UserProfile findById(int id) {
		return dao.findById(id);
	}

	public UserProfile findByRole(String type){
		return dao.findByRole(type);
	}

	public Set<UserProfile> findAll() {
		return dao.findAll();
	}
}
