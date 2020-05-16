package com.example.wx.demo.ServicesImpl;

import com.example.wx.demo.Config.UserConfig;
import com.example.wx.demo.Entity.UserEntity;
import com.example.wx.demo.Mapper.UserInfoMapper;
import com.example.wx.demo.Models.BackInfo;
import com.example.wx.demo.Models.UserInfo;
import com.example.wx.demo.Respontory.UserRepository;
import com.example.wx.demo.Services.UserInfoServices;
import com.example.wx.demo.Utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Component
@Transactional
public class UserImplement implements UserInfoServices {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public BackInfo loginByUserNameAndUserPassword(String userName, String password) {
        BackInfo backInfo = new BackInfo();
        UserEntity userEntity = userRepository.userLoginByUserNameAndPassword(userName, password);
        if (userEntity == null) {
            backInfo.setCode(UserConfig.USER_NOT_FOUND);
            backInfo.setMessage("用户不存在，请注册");
            backInfo.setUserEntity(null);
            return backInfo;
        } else {
            backInfo.setCode(UserConfig.LOGIN_SUCCESS);
            backInfo.setMessage("登录成功");
            backInfo.setUserEntity(userEntity);
            return backInfo;
        }
    }

    @Override
    public BackInfo registeredUserInfo(UserInfo userInfo) {
        BackInfo backInfo = new BackInfo();
        UserEntity userEntity = userInfoMapper.ModelToEntity(userInfo);
        // 查询用户是否已经存在
        UserEntity user = userRepository.findUseInfo(userInfo.getUserName(),userInfo.getPhone());
        if(user != null){
            backInfo.setUserEntity(user);
            backInfo.setCode(UserConfig.REGISTER_ERROR);
            backInfo.setMessage("用户已经存在");
            return backInfo;
        }
        userEntity.setId(UUID.randomUUID().toString());
        UserEntity result = userRepository.save(userEntity);
        backInfo.setUserEntity(result);
        backInfo.setCode(UserConfig.REGISTER_SUCCESS);
        backInfo.setMessage("注册成功");
        return backInfo;
    }

    @Override
    public Boolean upDataUserInfo(String id, UserInfo userInfo) {
        UserEntity userEntity = userInfoMapper.ModelToEntity(userInfo);
        userEntity.setId(id);
        userRepository.saveAndFlush(userEntity);
        return true;
    }

    @Override
    public PageBean getUserInfoList(int page, int size) {
        //  Sort.Direction.DESC, "roleId" 表示根据那个字段排序--倒叙
        PageBean pageBean = new PageBean();
        Pageable pageable = PageRequest.of(page-1, size, Sort.Direction.DESC, "roleId");
        Page result = userRepository.findAll(pageable);
        pageBean.setList(result.getContent());
        pageBean.setCurrentPage(result.getTotalPages());
        pageBean.setCurrentSize(result.getSize());
        pageBean.setTotal(result.getTotalElements());
        return pageBean;
    }

    @Override
    public UserEntity getUserInfoMessage(String id) {
        UserEntity userEntity = userRepository.getUserInfoById(id);
        return userEntity;
    }

    @Override
    public UserEntity findUserInfo(String userName, String phone) {
        UserEntity userEntity = userRepository.findUseInfo(userName, phone);
        return userEntity;
    }

    @Override
    public PageBean findUserInfoByUserName(String username, int page, int size) {
        PageBean pageBean = new PageBean();
        Pageable pageable = PageRequest.of(page-1, size);
        Page list = userRepository.findUserByUserName("%"+ username + "%", pageable);
        pageBean.setList(list.getContent());
        pageBean.setTotal(list.getTotalElements());
        pageBean.setCurrentSize(list.getSize());
        pageBean.setCurrentPage(list.getTotalPages());
        return pageBean;
    }

    @Override
    public Boolean deleteUser(String id) {
        if(id.isEmpty()) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }
}