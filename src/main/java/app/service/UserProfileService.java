package app.service;

import app.repository.entities.common.UserProfile;

import java.util.Set;


public interface UserProfileService {

	UserProfile findById(int id);

	UserProfile findByType(String type);
	
	Set<UserProfile> findAll();
	
}
