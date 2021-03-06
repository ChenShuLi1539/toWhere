package com.gangoffive.project.demo.tool.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class shiroConfig {
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/loginRegist/login");
        shiroFilterFactoryBean.setSuccessUrl("/loginRegist/login");
//        shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");
         shiroFilterFactoryBean.setUnauthorizedUrl("/loginRegist/login");
//        shiroFilter.setLoginUrl("");//身份认证失败，则跳转到登录页面的配置 没有登录的用户请求需要登录的页面时自动跳转到登录页面，不是必须的属性，不输入地址的话会自动寻找项目web项目的根目录下的”/login.jsp”页面。
//        shiroFilter.setSuccessUrl("");//登录成功默认跳转页面，不配置则跳转至”/”。如果登陆前点击的一个需要登录的页面，则在登录自动跳转到那个需要登录的页面。不跳转到此。
//        shiroFilter.setUnauthorizedUrl("");//没有权限默认跳转的页面
//        shiroFilter.setFilterChainDefinitions("");//filterChainDefinitions的配置顺序为自上而下,以最上面的为准
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/loginRegist/**", "anon");
//        filterChainDefinitionMap.put("/index/**", "anon");
//        filterChainDefinitionMap.put("/detail/**", "anon");
//        filterChainDefinitionMap.put("/detail/**", "anon");
//        filterChainDefinitionMap.put("/Goods/**", "authc");
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截 剩余的都需要认证
//        filterChainDefinitionMap.put("/**", "authc");
//        filterChainDefinitionMap.put("/detail/getclass","perms[user:student]");
//        filterChainDefinitionMap.put("/detail/addlabei","perms[user:admin]");
        // 1、创建过滤器Map，用来装自定义过滤器
//        LinkedHashMap<String, Filter> map = new LinkedHashMap<>();

        // 2、将自定义过滤器放入map中，如果实现了自定义授权过滤器，那就必须在这里注册，否则Shiro不会使用自定义的授权过滤器
//        map.put("perms", new MyPermsFilter());

        // 3、将过滤器Ma绑定到shiroFilterFactoryBean上
//        shiroFilterFactoryBean.setFilters(map);

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
    @Bean
    public SecurityManager securityManager(CustomRealm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        return  defaultWebSecurityManager;
    }
    @Bean
    public CustomRealm customRealm() {
        CustomRealm customRealm = new CustomRealm();
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return customRealm;
    }
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 使用md5 算法进行加密
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 设置散列次数： 意为加密几次
        hashedCredentialsMatcher.setHashIterations(2);

        return hashedCredentialsMatcher;
    }
}
