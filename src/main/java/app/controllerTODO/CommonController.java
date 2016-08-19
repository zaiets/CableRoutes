package app.controllerTODO;

import app.dto.common.UserDto;
import app.exceptionsTODO.EmailExistsException;
import app.repository.entities.common.User;
import app.service.common.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/")
public class CommonController {

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

    @Autowired
    AuthenticationTrustResolver authenticationTrustResolver;

    //USER
    //CREATE USER
    @RequestMapping(value = "/user", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody  @Valid UserDto userDto, UriComponentsBuilder ucBuilder) {
        User registered = createUserAccount(userDto);
        if (registered == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(registered.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    //GET USER
    @RequestMapping(value = "/user/{login}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser (@PathVariable("login") String login) {
        User user = userService.findByLogin(login);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //GET ALL USERS
    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> userList = userService.findAllUsers();
        if(userList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //test marker
        System.out.println("get all users = " + userList);

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    //UPDATE USER
    @RequestMapping(value = "/user/{login}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@PathVariable("login") String login, @RequestBody @Valid UserDto userDto) {

        User currentUser = userService.findByLogin(login);

        if (currentUser==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentUser.setEmail(userDto.getEmail());
        currentUser.setFirstName(userDto.getFirstName());
        currentUser.setLastName(userDto.getLastName());
        currentUser.setPatronymic(userDto.getPatronymic());
        currentUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userService.updateUser(currentUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //DELETE USER
    @RequestMapping(value = "/user/{login}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
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
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        if (isCurrentAuthenticationAnonymous()) {
            return "login";
        } else {
            return "redirect:/";
        }
    }

    /**
     * This method handles logout requests.
     */
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/login?logout";
    }

    /**
     * This method returns the principal[user-name] of logged-in user.
     */
    //TODO test this
    @RequestMapping(value="/currentuser", method = RequestMethod.GET)
    private String getPrincipal(){
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    /**
     * This method returns true if users is already authenticated [logged-in], else false.
     */
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }


    private User createUserAccount(UserDto newUser) {
        User registered;
        try {
            registered = userService.registerNewUserAccount(newUser);
        } catch (EmailExistsException e) {
            return null;
        }
        return registered;
    }
}

