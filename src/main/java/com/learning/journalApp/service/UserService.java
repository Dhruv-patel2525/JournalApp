package com.learning.journalApp.service;

import com.learning.journalApp.entity.User;
import com.learning.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    public void createUser(User user)
    {
        userRepository.save(user);
        return;
    }
    public void updateUser(User user,String userName)
    {

        User newUser=userRepository.findByUserName(userName);
        if(newUser!=null)
        {
            newUser.setUserName(user.getUserName());
            newUser.setPassword(user.getPassword());
            userRepository.save((newUser));
        }
    }

    public User findByUserName(String userName)
    {
        return userRepository.findByUserName(userName);
    }

}
