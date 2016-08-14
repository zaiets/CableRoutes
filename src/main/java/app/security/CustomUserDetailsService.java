package app.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import app.repository.entities.common.User;
import app.service.IUserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Autowired
	private IUserService userService;
	
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String login)
			throws UsernameNotFoundException {

		User user = userService.findByLogin(login);
		logger.info("User : {}", user);
		if(user==null){
			logger.info("User not found");
			throw new UsernameNotFoundException("Username not found");
		}
			return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(),
				 true, true, true, true, getAuthorities(user.getUserProfile().getRole().ordinal()));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Integer role){
		return getGrantedAuthorities(getRoles(role));
	}
	private List<String> getRoles(Integer role) {
		List<String> roles = new ArrayList<>();
		if (role == 0) {
			roles.add("ROLE_USER");
			roles.add("ROLE_ADMIN");
		} else if (role == 1) {
			roles.add("ROLE_USER");
		}
		return roles;
	}
	private static List<GrantedAuthority> getGrantedAuthorities (List<String> roles) {
		return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}
}
