package com.example.wx.demo.Controller;
import com.example.wx.demo.Entity.UserEntity;
import com.example.wx.demo.Models.BackInfo;
import com.example.wx.demo.Models.UserInfo;
import com.example.wx.demo.Services.UserInfoServices;
import com.example.wx.demo.Utils.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api(description = "用户信息类接口",value = "/api/v1")
@RequestMapping(value = "/api/v1")
@CrossOrigin
public class UserInfoController {

    @Autowired
    private UserInfoServices userInfoServices;

    /**
     * 用户登录
     * @param userName
     * @param password
     * @return
     */
    @GetMapping("/login/{userName}/{password}")
    @ApiOperation(value = "用户名密码登录", notes = "getUserInfo", produces = "application/json;charset=UTF-8")
    public ResponseEntity<BackInfo> loginByUserNameAndPassword(@PathVariable("userName") String userName,
                                                               @PathVariable("password") String password) {
        BackInfo result = userInfoServices.loginByUserNameAndUserPassword(userName,password);
        HttpStatus status = result.getUserEntity() != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
       return new ResponseEntity<BackInfo>(result, status);
    }


    @PostMapping(value = "/userInfo")
    @ApiOperation(value = "新用户注册", notes = "registered", produces = "application/json;charset=UTF-8")
    public ResponseEntity<BackInfo> newUserInfoRegistered(@RequestBody UserInfo userInfo) {
        BackInfo result = userInfoServices.registeredUserInfo(userInfo);
        HttpStatus status = result != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<BackInfo>(result, status);
    }

    @PutMapping(value = "/updateUserInfo/{id}")
    @ApiOperation(value = "更新用户个人信息", notes = "upDataUserInfoMessage", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Boolean>UpdateUserInfoMessage(@RequestParam String id, @RequestBody UserInfo userInfo) {
        Boolean result = userInfoServices.upDataUserInfo(id, userInfo);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/userInfo/{page}/{size}")
    @ApiOperation(value = "用户信息分页", notes = "getUserInfoByPageAndSize", produces = "application/json;charset=UTF-8")
    public ResponseEntity<PageBean<UserEntity>> getUerInfoByPageSize(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                     @RequestParam(value = "size", defaultValue = "5") int size) {
        PageBean<UserEntity> result = userInfoServices.getUserInfoList(page,size);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/gerUserById/personId/{id}}")
    @ApiOperation(value = "根据用户id获取用户信息", notes = "getUserInoByid", produces = "application/json;charset=UTF-8")
    public ResponseEntity<UserEntity> getUserInfoByPersonId(@RequestParam String id) {
        UserEntity result = userInfoServices.getUserInfoMessage(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/getBackUserInfo/{userName}/{phone}")
    @ApiOperation(value = "根据用户用户名字以及手机号找回用户信息", notes = "getBackUserInfo", produces = "application/json;charset=UTF-8")
    public ResponseEntity<UserEntity> getBackUserInfoByUserNameAndPassword(@PathVariable("userName") String userName,
                                                                            @PathVariable("phone") String phone) {
        UserEntity result = userInfoServices.findUserInfo(userName, phone);
        HttpStatus status = phone != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<UserEntity>(result, status);
    }
    @GetMapping("/findByUserName/{userName}/{page}/{size}")
    @ApiOperation(value = "根据用户用户名字模糊查询用户信息", notes = "getBackUserInfo", produces = "application/json;charset=UTF-8")
    public ResponseEntity<PageBean<UserEntity>> findUserByUserName(@PathVariable("userName") String userName,
                                                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                                                   @RequestParam(value = "size", defaultValue = "5") int size) {
        PageBean result = userInfoServices.findUserInfoByUserName(userName, page, size);
        HttpStatus status = userName != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<PageBean<UserEntity>>(result, status);

    }
    @DeleteMapping("/deleteUser/{id}")
    @ApiOperation(value = "根据用户用户Id注销用户", notes = "DeleteUserInfoMessage", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Boolean> deleteUserInfo(@PathVariable("id") String id) {
        Boolean result = userInfoServices.deleteUser(id);
        HttpStatus status = result ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<Boolean>(result, status);

    }
}
