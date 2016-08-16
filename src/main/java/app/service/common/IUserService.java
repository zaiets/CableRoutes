package app.service.common;

import app.dto.common.UserDto;
import app.exceptions.EmailExistsException;
import app.repository.entities.common.User;

import java.util.List;


public interface IUserService {
	
	User findById(int id);
	
	User findByLogin(String login);
	
	User saveUser(User user);
	
	void updateUser(User user);
	
	void deleteUserByLogin(String login);

	List<User> findAllUsers(); 
	
	boolean isUserLoginUnique(Integer id, String login);

	User registerNewUserAccount(UserDto userDto) throws EmailExistsException;

}