package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import app.dto.users.UserDto;
import app.exceptions.EmailExistsException;
import app.repository.entities.common.User;
import app.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/")
public class RestControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        //test marker
        System.out.println("get user = " + user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //GET ALL USERS
    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userService.findAllUsers();
        if(users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //test marker
        System.out.println("get all users = " + users);

        return new ResponseEntity<>(users, HttpStatus.OK);
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
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
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

