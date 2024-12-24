package com.learning.journalApp.controller;

import com.learning.journalApp.entity.User;
import com.learning.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUser()
    {
        return userService.getAllUsers();
    }

    @PostMapping
    public void createUser(@RequestBody User user)
    {
         userService.createUser(user);
         return;
    }

    @PutMapping("/{userName}")
    public void updateUser(@RequestBody User user,@PathVariable String userName)
    {
        userService.updateUser(user,userName);
        return;
    }

}
