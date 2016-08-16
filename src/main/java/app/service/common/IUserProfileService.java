package app.service.common;

import app.repository.entities.common.UserProfile;

import java.util.Set;


public interface IUserProfileService {

	UserProfile findById(int id);

	UserProfile findByRole(String type);
	
	Set<UserProfile> findAll();
	
}
