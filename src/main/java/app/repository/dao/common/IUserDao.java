package app.repository.dao.common;

import app.repository.entities.common.User;

import java.util.List;


public interface IUserDao {

	User findById(int id);
	
	User findByLogin(String login);

	User findByEmail(String email);

	User save(User user);
	
	void deleteByLogin(String login);
	
	List<User> findAllUsers();

}

