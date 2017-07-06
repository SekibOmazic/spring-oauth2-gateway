package com.comsysto.dcc.auth.controller;

import com.comsysto.dcc.auth.role.Role;
import com.comsysto.dcc.auth.role.RoleRepository;
import com.comsysto.dcc.auth.user.User;
import com.comsysto.dcc.auth.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    
    @GetMapping("/list")
    public ModelAndView usersAll(ModelAndView model) {
        model.addObject("users", userRepository.findAll());
        model.setViewName("usersList");
        return model;
    }
    
    @GetMapping("/new")
    public ModelAndView users(ModelAndView model) {
        User user = new User();
        model.addObject("user", user);
        model.setViewName("users");
        return model;
    }
    
    @PostMapping("/save")
    public void usersSubmit(@ModelAttribute User user, HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
        String encodedPassword = encodeUserPassword(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        ControllerHelper.constructRedirectURL("/user/list", request, response);
    }


    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable Long id) {
        return userRepository.findOne(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable Long id) {
        userRepository.delete(id);
    }

    @RequestMapping(value = "/add", method=RequestMethod.POST)
    public User create(@RequestBody User user) {
        String encodedPassword = encodeUserPassword(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public User update(@PathVariable Long id, @RequestBody User user) {
        User fromDB = validate(id);

        String encodedPassword = encodeUserPassword(user.getPassword());
        fromDB.setPassword(encodedPassword);
        fromDB.setEnabled(user.isEnabled());

        // add only updated roles
        fromDB.getRoles().clear();
        fromDB.getRoles().addAll(user.getRoles());

        return userRepository.save(fromDB);
    }

    private String encodeUserPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    private User validate(Long id) {
        return this.userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

}
