//package com.github.white.at.web;
//
//import org.springframework.beans.factory.com.github.white.at.framework.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.com.github.white.at.framework.annotation.RequestMapping;
//import org.springframework.web.bind.com.github.white.at.framework.annotation.RequestParam;
//import org.springframework.web.bind.com.github.white.at.framework.annotation.ResponseBody;
//import org.springframework.web.bind.com.github.white.at.framework.annotation.RestController;
//
//import com.github.white.at.api.UserService;
//import com.github.white.at.api.domain.UserModel;
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
