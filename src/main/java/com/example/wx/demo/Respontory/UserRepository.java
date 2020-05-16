package com.example.wx.demo.Respontory;


import com.example.wx.demo.Entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    // SQL语句
    @Query(value = "select c from UserEntity as c where c.userName = :userName and c.password = :password")
    UserEntity userLoginByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);

    @Query(value = "select  c from UserEntity  as c where c.id = :id")
    UserEntity getUserInfoById(@Param("id") String id);

    /**
     * 根据用户手机号以及姓名找回密码;
     * @param phone
     * @param userName
     * @return
     */
    @Query(value = "select c from UserEntity as c where c.userName = :userName and c.phone = :phone")
    UserEntity findUseInfo(@Param("userName") String userName, @Param("phone")String phone);

    /**
     *
     * 根据用户名字整形模糊查询
     * @param username
     * @return
     */
    @Query(value = "select c from UserEntity as c  where c.userName LIKE CONCAT('%',:username,'%')")
    Page findUserByUserName(@Param("username") String username, Pageable pageable);

}
