package ${project.basePackage}.service.impl;

import ${project.basePackage}.service.UserService;
import ${project.basePackage}.command.cmo.AddUserCmd;
import org.springframework.stereotype.Service;
import ${project.basePackage}.dao.UserMapper;
import ${project.basePackage}.converter.UserConverter;
import ${project.basePackage}.dataobject.UserDO;

import org.springframework.beans.factory.annotation.Autowired;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserConverter userConverter;

    @Override
    public void  addUser(AddUserCmd addUserCmd) {
        UserDO userDO= userConverter.cmdToDO(addUserCmd);
        userMapper.insert(userDO);
    }
}
