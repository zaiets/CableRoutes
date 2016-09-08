package app.controller;

import app.converter.ModelVsDtoConverter;
import app.dto.common.UserDto;
import app.exceptionsTODO.EmailExistsException;
import app.repository.entities.common.User;
import app.service.common.IUserProfileService;
import app.service.common.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/")
public class CommonController {
    static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private IUserService userService;
    @Autowired
    private IUserProfileService userProfileService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

    @Autowired
    AuthenticationTrustResolver authenticationTrustResolver;

    //USER
    //CREATE USER
    @RequestMapping(value = "/admin/user", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody  @Valid UserDto userDto) {
        User registered = createUserAccount(userDto);
        if (registered == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //GET USER
    @RequestMapping(value = "/admin/user/{login}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUser (@PathVariable("login") String login) {
        User user = userService.findByLogin(login);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserDto userDto = ModelVsDtoConverter.transformUser(user, user.getUserProfile().getRole());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    //GET ALL USERS (ADMIN)
    @RequestMapping(value = "/admin/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> listAllUsers() {
        List<User> userList = userService.findAllUsers();
        if(userList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<UserDto> userDtoList = new ArrayList<>();
        userList.stream().forEachOrdered(u -> userDtoList.add(ModelVsDtoConverter.transformUser(u, u.getUserProfile().getRole())));
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    //UPDATE USER (ADMIN)
    @RequestMapping(value = "/admin/user/{login}", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateUser(@PathVariable("login") String login, @RequestBody @Valid UserDto userDto) {

        User currentUser = userService.findByLogin(login);

        if (currentUser==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentUser.setEmail(userDto.getEmail());
        currentUser.setFirstName(userDto.getFirstName());
        currentUser.setLastName(userDto.getLastName());
        currentUser.setPatronymic(userDto.getPatronymic());
        currentUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        currentUser.setUserProfile(userProfileService.findByRole(userDto.getRole()));
        userService.updateUser(currentUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //DELETE USER (ADMIN)
    @RequestMapping(value = "/admin/user/{login}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("login") String login) {
        User user = userService.findByLogin(login);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.deleteUserByLogin(login);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /**
     * This method handles login GET requests.
     * If users is already logged-in and tries to goto login page again, will be redirected to list page.

    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> loginPage() {
        logger.info("CommonController called for loginPage()");
        if (isCurrentAuthenticationAnonymous()) {
            return new ResponseEntity<>("/login", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("redirect:/", HttpStatus.OK);
        }
    }
    */
    /**
     * This method handles logout requests.
     */
    @RequestMapping(value="/logout", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> logoutPage (HttpServletRequest request, HttpServletResponse response){
        logger.info("CommonController called for logoutPage()");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This method returns the principal[user-name] of logged-in user.
     */
    @RequestMapping(value="/current", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getPrincipal(){
        logger.info("CommonController called for getPrincipal()");
        return new ResponseEntity<>(getCurrentUserName(), HttpStatus.OK);
    }

    /**
     * This method returns true if users is already authenticated [logged-in], else false.
     */
    private boolean isCurrentAuthenticationAnonymous() {
        logger.info("CommonController called for isCurrentAuthenticationAnonymous()");
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }


    private User createUserAccount(UserDto newUser) {
        User registered;
        try {
            registered = userService.registerNewUserAccount(newUser, userProfileService.findByRole(newUser.getRole()));
        } catch (EmailExistsException e) {
            return null;
        }
        return registered;
    }

    private String getCurrentUserName() {
        String name;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            name = ((UserDetails)principal).getUsername();
            logger.info(name);
            return name;
        } else {
            name = principal.toString();
            logger.info(name);
            return name;
        }
    }
}

