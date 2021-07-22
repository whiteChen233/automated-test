//package com.github.whitechen233.at.web;
//
//import org.springframework.beans.factory.com.github.whitechen233.at.common.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.com.github.whitechen233.at.common.annotation.RequestMapping;
//import org.springframework.web.bind.com.github.whitechen233.at.common.annotation.RequestParam;
//import org.springframework.web.bind.com.github.whitechen233.at.common.annotation.ResponseBody;
//import org.springframework.web.bind.com.github.whitechen233.at.common.annotation.RestController;
//
//import com.github.whitechen233.at.api.repository.UserService;
//import com.github.whitechen233.at.api.domain.UserModel;
//
///**
// * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
// */
//@Component
//@RestController
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @RequestMapping("/username")
//    public String getUserName(@RequestParam("id") Long id) {
//        return userService.getUserName(id);
//    }
//
//    @RequestMapping("/add")
//    @ResponseBody
//    public UserModel addUser(@RequestParam("name") String name, @RequestParam("age") Integer age) {
//        UserModel user = new UserModel();
//        user.setName(name);
//        user.setAge(age);
//        return userService.addUser(user);
//    }
//}