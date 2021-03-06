package com.gangoffive.project.demo.tool.config;


import com.gangoffive.project.demo.biz.GetUserBiz;
import com.gangoffive.project.demo.entity.Administer;
import com.gangoffive.project.demo.entity.Student;
import com.gangoffive.project.demo.entity.Teacher;
import com.gangoffive.project.demo.mapper.loginMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomRealm extends AuthorizingRealm {
    @Autowired
    loginMapper loginMapper;
    @Autowired
    GetUserBiz getUserBiz;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> stringSet = new HashSet<>();
//        if (indexBiz.getsatte(username).equals("老师")){
//            System.out.println("老师");
//            stringSet.add("user:teacher");
//            stringSet.add("user:admin");
//        }else {
//            System.out.println("学生");
//            stringSet.add("user:teacher");
//        }
        info.setStringPermissions(stringSet);
        return info;
    }

    /**
     * 这里可以注入userService,为了方便演示，我就写死了帐号了密码
     * private UserService userService;
     * <p>
     * 获取即将需要认证的信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        System.out.println("-------身份认证方法--------");
        String userName = (String) authenticationToken.getPrincipal();
        String dbPwd = "";
        String role;
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(userName);
        int id=0;
        if (userName==null||userName.equals("")||userName.equals(" ")){
            throw new AccountException("用户名为空");
        }



        if(!isNum.matches()){
            role=loginMapper.LoginByIEmail(userName);
            if (role == null || role.equals("")) {
                throw new UnknownAccountException("用户名不存在");
            }
            id=getUserBiz.getIdByEmail(userName);
            System.out.println("Id:"+id);
        }else {
            role=loginMapper.LoginById(Integer.parseInt(userName));
            if (role == null || role.equals("")) {
                throw new UnknownAccountException("用户名不存在");
            }
            id=Integer.parseInt(userName);
            System.out.println("Id2:"+id);
        }


        if (role.equals("学生")){
            Student student=getUserBiz.getStudentById(id);
            dbPwd=student.getPwd();
        }else if (role.equals("老师")){
            Teacher teacher=getUserBiz.getTeacherById(id);
            dbPwd=teacher.getPwd();
        }else if (role.equals("教务老师")){
            Administer administer=getUserBiz.getAdminsterById(id);
            dbPwd=administer.getPwd();
        }else if(role==null||role.equals("")){
            throw new AccountException("用户名错误");
        }

        System.out.println("id:"+id);
        System.out.println("dbPwd:"+dbPwd);
        return new SimpleAuthenticationInfo(id,dbPwd, ByteSource.Util.bytes(id + "salt"),getName());
    }

}
