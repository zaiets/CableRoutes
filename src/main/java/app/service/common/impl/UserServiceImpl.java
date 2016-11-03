package app.service.common.impl;

import app.dto.common.UserDto;
import app.exceptionsTODO.EmailExistsException;
import app.repository.dao.common.IUserDao;
import app.repository.entities.common.User;
import app.repository.entities.common.UserProfile;
import app.service.common.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Service
@Transactional
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao dao;

	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public User findById(int id) {
		return dao.findById(id);
	}

	public User findByLogin(String login) {
		return dao.findByLogin(login);
	}

	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return dao.save(user);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateUser(User user) {
		User entity = dao.findById(user.getId());
		if(entity!=null){
			entity.setLogin(user.getLogin());
			if(!user.getPassword().equals(entity.getPassword())){
				entity.setPassword(
						passwordEncoder.encode(
								user.getPassword()
						)
				);
			}
			entity.setFirstName(user.getFirstName());
			entity.setLastName(user.getLastName());
			entity.setEmail(user.getEmail());
			entity.setUserProfile(user.getUserProfile());
		}
	}

	
	public void deleteUserByLogin(String login) {
		dao.deleteByLogin(login);
	}

	public List<User> findAllUsers() {
		return dao.findAllUsers();
	}


	public boolean isUserLoginUnique(Integer id, String login) {
		User user = findByLogin(login);
		return ( user == null || ((id != null) && (Objects.equals(user.getId(), id))));
	}

	private boolean emailExist(String email) {
		User user = dao.findByEmail(email);
		return user != null;
	}

	@Override
	public User registerNewUserAccount(UserDto userDto, UserProfile userProfile) throws EmailExistsException {
		if (emailExist(userDto.getEmail())) {
			throw new EmailExistsException("There is an account with that email adress: " +
					userDto.getEmail());
		}
		User user = new User();
		user.setLogin(userDto.getLogin());
		user.setEmail(userDto.getEmail());
		user.setLastName(userDto.getLastName());
		user.setFirstName(userDto.getFirstName());
		user.setPatronymic(userDto.getPatronymic());
		user.setPassword(userDto.getPassword());
		user.setUserProfile(userProfile);
		return saveUser(user);
	}
}
