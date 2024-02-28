package dev.jam.accountservice.service;

import dev.jam.accountservice.dao.entities.UserAccount;
import dev.jam.accountservice.dao.entities.Company;
import dev.jam.accountservice.dao.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserServiceImpl implements IUserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserAccount addUser(UserAccount userAccount) {
        return userRepository.save(userAccount);
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserAccount updateUser(UserAccount userAccount) {
        return userRepository.save(userAccount);
    }

    @Override
    public UserAccount getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserAccount> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public UserAccount getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public void addCompanyToUser(long userId, Company company) {
        UserAccount userAccount = userRepository.findById(userId).orElse(null);
        if (userAccount != null) {
            userAccount.setCompany(company);
            userRepository.save(userAccount);
        }
        throw new RuntimeException("User not found");
    }

    @Override
    public void removeCompanyFromUser(long userId) {
        UserAccount userAccount = userRepository.findById(userId).orElse(null);
        if (userAccount != null) {
            if(userAccount.getCompany() == null)
                throw new RuntimeException("User has no company");

            userAccount.setCompany(null);
            return;
        }
        throw new RuntimeException("User not found");
    }

    @Override
    public UserAccount loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("reach for user with email: " + email);
        return userRepository.findByEmail(email)
                .orElse(null);
    }
}
