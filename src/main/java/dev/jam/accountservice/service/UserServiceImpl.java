package dev.jam.accountservice.service;

import dev.jam.accountservice.dao.entities.User;
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
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public void addCompanyToUser(long userId, Company company) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setCompany(company);
            userRepository.save(user);
        }
        throw new RuntimeException("User not found");
    }

    @Override
    public void removeCompanyFromUser(long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            if(user.getCompany() == null)
                throw new RuntimeException("User has no company");

            user.setCompany(null);
            return;
        }
        throw new RuntimeException("User not found");
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found") );
    }
}
