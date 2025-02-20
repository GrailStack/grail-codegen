package ${project.basePackage}.service.impl;

import ${project.basePackage}.service.UserService;
import org.springframework.stereotype.Service;
import ${project.basePackage}.dao.UserMapper;
import ${project.basePackage}.dataobject.UserDO;

import org.springframework.beans.factory.annotation.Autowired;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public void  addUser() {

    }
}
