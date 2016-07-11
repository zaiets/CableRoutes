package app.repository.dao.common;

import app.repository.entities.common.UserProfile;

import java.util.Set;


public interface UserProfileDao {

	Set<UserProfile> findAll();
	
	UserProfile findByRole(String role);
	
	UserProfile findById(int id);
}
