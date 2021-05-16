//package com.github.whitechen233.at.service;
//
//import org.springframework.cglib.beans.BeanCopier;
//
//import com.github.whitechen233.at.dao.dataobject.UserDO;
//import com.github.whitechen233.at.api.UserService;
//import com.github.whitechen233.at.api.model.UserModel;
//import com.github.whitechen233.at.dao.mapper.UserRespository;
//import com.github.whitechen233.at.demos.web.User;
//
///**
// * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
// */
////@Component
//public class UserServiceImpl implements UserService {
//
////    @Autowired
//    private UserRespository userRespository;
//
//    private static final BeanCopier copier = BeanCopier.create(UserModel.class, UserDO.class, false);
//
//    public String getUserName(Long id) {
//        User userDO = userRespository.getOne(id);
//        return userDO != null ? userDO.getName() : null;
//    }
//
//    public UserModel addUser(UserModel user) {
//        UserDO userDO = new UserDO();
//        copier.copy(user, userDO, null);
//
//        Long id = userRespository.save(userDO);
//        user.setId(id);
//        return user;
//    }
//}
