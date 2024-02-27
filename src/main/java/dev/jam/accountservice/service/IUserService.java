package dev.jam.accountservice.service;

import dev.jam.accountservice.dao.entities.Company;
import dev.jam.accountservice.dao.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface IUserService extends UserDetailsService {

    User loadUserByUsername(String username) throws UsernameNotFoundException;

    User addUser(User user);
    void deleteUserById(long id);
    User updateUser(User user);

    User getUserById(long id);
    List<User> getAllUsers();

    User getUserByEmail(String email);
    void addCompanyToUser(long userId, Company company);
    void removeCompanyFromUser(long userId);

}
