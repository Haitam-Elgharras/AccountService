package dev.jam.accountservice.service;

import dev.jam.accountservice.dao.entities.Company;
import dev.jam.accountservice.dao.entities.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface IUserService extends UserDetailsService {

    UserAccount loadUserByUsername(String username) throws UsernameNotFoundException;

    UserAccount addUser(UserAccount userAccount);
    void deleteUserById(long id);
    UserAccount updateUser(UserAccount userAccount);

    UserAccount getUserById(long id);
    List<UserAccount> getAllUsers();

    UserAccount getUserByEmail(String email);
    void addCompanyToUser(long userId, Company company);
    void removeCompanyFromUser(long userId);

}
