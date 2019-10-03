package com.siotman.wos.yourpaper.service;

import com.siotman.wos.yourpaper.domain.entity.BasicSecurityUser;
import com.siotman.wos.yourpaper.domain.entity.User;
import com.siotman.wos.yourpaper.repo.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BasicUserDetailService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    Log logger = LogFactory.getLog(BasicUserDetailService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(username);
        }

        return new BasicSecurityUser(user.get());
    }
}
