package com.exmcs.login_service.service.auth;

import com.exmcs.login_service.model.entity.UserLogin;
import com.exmcs.login_service.repository.UserLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserLoginRepository userLoginRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        UserLogin user = userLoginRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("username : " + username + " not found"));
        return UserDetailsImpl.build(user);
    }
}
