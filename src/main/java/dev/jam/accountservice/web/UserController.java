package dev.jam.accountservice.web;

import dev.jam.accountservice.dao.entities.User;
import dev.jam.accountservice.exceptions.RequiredFieldMissingException;
import dev.jam.accountservice.exceptions.UserNotFoundException;
import dev.jam.accountservice.service.IUserService;
import dev.jam.accountservice.service.UserServiceImpl;
import dev.jam.accountservice.service.dtos.UserDto;
import dev.jam.accountservice.service.mappers.UserMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Transactional
@EnableMethodSecurity
@EnableWebSecurity
public class UserController {

    private final IUserService userService;
    private final UserMapper userMapper;

    public UserController(IUserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")  
    public List<UserDto> getAllUsers() {
        return userMapper.usersToUserDtos(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        // check if user exists
        User user = userService.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        return user;
    }

    @PostMapping
    public UserDto addUser(@RequestBody User user) {
        // check if the required fields are not empty
        if (user.getName() == null || user.getEmail() == null || user.getPassword() == null) {
            // custom exception with the name RequiredFieldMissingException
            throw new RequiredFieldMissingException("Required fields are missing");
        }
        if(user.getRole() != null)
            user.setRole(null);

        return userMapper.userToUserDto(userService.addUser(user));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        // check if user exists
        User user = userService.getUserById(id);
        if (user == null) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        userService.deleteUserById(id);
    }
}