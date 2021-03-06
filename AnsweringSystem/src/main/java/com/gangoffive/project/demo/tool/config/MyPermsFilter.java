package com.gangoffive.project.demo.tool.config;

import lombok.SneakyThrows;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyPermsFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        System.out.println("isAccessDenied");
        Subject subject = getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;
        System.out.println("subject:"+subject.getPrincipal());
        if (rolesArray == null || rolesArray.length == 0) {
            //no roles specified, so nothing to check - allow access.
            return true;
        }

        for(int i=0;i<rolesArray.length;i++){
            if(subject.isPermitted(rolesArray[i])){//subject.hasRole(rolesArray[i])
                System.out.println("rolealist:"+rolesArray[i]);
                return true;
            }
        }
        return false;
    }




    @SneakyThrows
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        System.out.println("onAccessDenied");
        Subject subject = getSubject(request, response);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        // If the subject isn't identified, redirect to login URL
        if (subject.getPrincipal() == null) {
//            saveRequestAndRedirectToLogin(request, response);     //没登陆就进入重新登陆接口，这里前后端分离不需要
            System.out.println("没登陆");
            String objectStr= "{'name':'没登陆'}";
            JSONObject jsonObject=new JSONObject(objectStr);
            PrintWriter wirte = null;
            wirte = response.getWriter();
            wirte.print(jsonObject);
        } else {
            System.out.println("没权限");
            String objectStr= "{'name':'没权限'}";
            JSONObject jsonObject=new JSONObject(objectStr);
            PrintWriter wirte = null;
            wirte = response.getWriter();
            wirte.print(jsonObject);
//            JSONObject json = new JSONObject();
//            json.put("state","403");
//            json.put("msg","登录已失效，请重新登录！");
//            out.println(json);
//            out.flush();
//            out.close();
        }

        return false;
    }


}
