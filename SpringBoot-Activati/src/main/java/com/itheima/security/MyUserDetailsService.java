package com.itheima.security;

import com.itheima.entity.User;
import com.itheima.service.UserService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userService.findOneUserByName(username);

        // springsecurity 注册加密方法
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("admin");
        System.out.println(encode);


        User user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setPassword(encode);
        if(user == null){
            return null;
        }
        log.info("user=={}",user);
        List<GrantedAuthority> authority = new ArrayList<>();
        authority.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authority
                );
    }
}
