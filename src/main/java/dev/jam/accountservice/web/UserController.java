package dev.jam.accountservice.web;

import dev.jam.accountservice.dao.entities.UserAccount;
import dev.jam.accountservice.exceptions.RequiredFieldMissingException;
import dev.jam.accountservice.exceptions.UserNotFoundException;
import dev.jam.accountservice.service.IUserService;
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
    @PreAuthorize("hasRole('USER')")
    public List<UserAccount> getAllUsers() {
        return userService.getAllUsers().stream().map(
                user -> {
                    user.setPassword(null);
                    return user;
                }
        ).toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public UserAccount getUserById(@PathVariable Long id) {
        // check if user exists
        UserAccount userAccount = userService.getUserById(id);
        if (userAccount == null) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        return userAccount;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public UserDto addUser(@RequestBody UserAccount userAccount) {
        // check if the required fields are not empty
        if (userAccount.getName() == null || userAccount.getEmail() == null || userAccount.getPassword() == null) {
            // custom exception with the name RequiredFieldMissingException
            throw new RequiredFieldMissingException("Required fields are missing");
        }
        if(userAccount.getRole() != null)
            userAccount.setRole(null);

        return userMapper.userToUserDto(userService.addUser(userAccount));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        // check if user exists
        UserAccount userAccount = userService.getUserById(id);
        if (userAccount == null) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        userService.deleteUserById(id);
    }
}